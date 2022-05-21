package knowledgecafe;

import knowledgecafe.dto.RevisionSearchPojo;
import knowledgecafe.dto.TopicPojo;
import knowledgecafe.model.*;
import knowledgecafe.service.*;
import knowledgecafe.util.DataConversion;
import knowledgecafe.util.SessionMgmt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class RevisionController {

    final StudentService studentService;
    final TopicService topicService;
    final RevisionService revisionService;
    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    public RevisionController(StudentService studentService, RevisionService revisionService, TopicService topicService) {
        this.studentService = studentService;
        this.revisionService = revisionService;
        this.topicService = topicService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("revisionSearchPojo", new RevisionSearchPojo());
        model.addAttribute("topicPojo", new TopicPojo());
        return "index";
    }

    @GetMapping("/revision-today")
    public String revision_today(Model model, HttpSession session) {
        SessionMgmt.populateSessionAttributes(model, session, studentService);
        Set<Revision> todaysRevisionTopic = revisionService.getRevisionTopicsForToday(LocalDate.now());
        session.setAttribute("revisions", todaysRevisionTopic);
        Topic topic = new Topic();
        model.addAttribute("topic", topic);
        session.setAttribute("message", "Today's Revision Schedule...");
        return "revision_form";
    }

    @PostMapping("/topic-update")
    public String topic_update(@Valid @ModelAttribute Topic topic, Model model, HttpSession session) {
        SessionMgmt.populateSessionAttributes(model, session, studentService);

        topicService.updateTopicConfidenceById(topic.getConfidenceLevel(), topic.getId());
        LocalDate searchDate = session.getAttribute("searchDate") == null ? LocalDate.now():
                LocalDate.parse(session.getAttribute("searchDate").toString(), dateTimeFormatter);

            Set<Revision> revisionTopics = revisionService.getRevisionTopicsForToday(searchDate);
            session.setAttribute("revisions", revisionTopics);
            session.setAttribute("searchDate", searchDate.format(dateTimeFormatter));


        session.setAttribute("message", "Updated Revision Schedule for Date: "+ searchDate.format(dateTimeFormatter));
        return "revision_form";

    }


    @PostMapping("/revision-search-date")
    public String revisionSearchByDate(@Valid @ModelAttribute RevisionSearchPojo revision, Model model, HttpSession session) {
        SessionMgmt.populateSessionAttributes(model, session, studentService);
        LocalDate revisionStartDate = revision.getRevisionStartDate();
        LocalDate revisionEndDate = revision.getRevisionEndDate();

        //Below variables will be used by default if revision End Date is blank means user can update confidence level for that date
        String returnViewName = "revision_result";
        String message ;

        if (revisionEndDate == null){
            revisionEndDate = revisionStartDate;
            returnViewName = "revision_form";
            message = "Revision for Date: " + revisionStartDate.format(dateTimeFormatter);
            session.setAttribute("searchDate", revisionStartDate.format(dateTimeFormatter));
        }else {
            message = "Revisions between date " + revisionStartDate.format(dateTimeFormatter) + " and " + revisionEndDate.format(dateTimeFormatter);
        }
        Set<Revision> revisionsByDate = revisionService.getRevisionTopicsBetweenDate(revisionStartDate, revisionEndDate);
        session.setAttribute("revisions", revisionsByDate);
        session.setAttribute("message", message);
        return returnViewName;
    }

    @PostMapping("/revision-subject")
    public String revisionSearchBySubject(@Valid @ModelAttribute RevisionSearchPojo revision, Model model, HttpSession session) {
        SessionMgmt.populateSessionAttributes(model, session, studentService);

        // Save to DB after updating
        Subject searchSubject = revision.getSubject();
        if(searchSubject == null){
            throw new RuntimeException("Subject can't be blank");
        }
        LocalDate revisionStartDate = revision.getRevisionStartDate();
        LocalDate revisionEndDate = revision.getRevisionEndDate();
        if (revisionEndDate == null){
            revisionEndDate = revisionStartDate;
        }
        Set<Revision> revisionsByDate = revisionService.getRevisionsBySubjectBetweenDates(revisionStartDate, revisionEndDate, searchSubject);
        session.setAttribute("revisions", revisionsByDate);
        String message = "Revisions for Subject "+ searchSubject +" between date " + revisionStartDate.format(dateTimeFormatter) + " and " + revisionEndDate.format(dateTimeFormatter);
        session.setAttribute("message", message);

        return "revision_result";
    }

    @PostMapping("/revision-confidence")
    public String revisionSearchByConfidence(@Valid @ModelAttribute RevisionSearchPojo revision, Model model, HttpSession session) {
        SessionMgmt.populateSessionAttributes(model, session, studentService);

        ConfidenceLevel confidenceLevel = revision.getConfidenceLevel();
        // Save to DB after updating
        LocalDate revisionStartDate = revision.getRevisionStartDate();
        LocalDate revisionEndDate = revision.getRevisionEndDate();
        if (revisionEndDate == null){
            revisionEndDate = revisionStartDate;
        }
        Set<Revision> revisionsByDate = revisionService.getRevisionsByConfidenceBetweenDates(revisionStartDate, revisionEndDate, confidenceLevel);
        session.setAttribute("revisions", revisionsByDate);
        String message = "Revisions as per Confidence "+ confidenceLevel+" between date " + revisionStartDate.format(dateTimeFormatter) + " and " + revisionEndDate.format(dateTimeFormatter);
        session.setAttribute("message", message);

        return "revision_result";
    }

    @PostMapping("/study-report")
    public String studyReport(@Valid @ModelAttribute RevisionSearchPojo revision, Model model, HttpSession session) {
        Student student = SessionMgmt.populateSessionAttributes(model, session, studentService);

        // Save to DB after updating
        LocalDate studyStart = revision.getRevisionStartDate();
        LocalDate studyEnd = studyStart.plusMonths(1);
        Set<Topic> monthlyStudy = topicService.getTopicsBetweenStudyDates(studyStart, studyEnd);

        String message = student.getFullName()+ "'s Monthly study report between " + studyStart.format(dateTimeFormatter) + " and  " + studyEnd.format(dateTimeFormatter);
        session.setAttribute("message", message);
        session.setAttribute("topics", monthlyStudy);

        return "topic_result";
    }


    @PostMapping("/topic-submit")
    public String topicSubmit(@Valid @ModelAttribute TopicPojo topicPojo , Model model, HttpSession session) {
        SessionMgmt.populateSessionAttributes(model, session, studentService);

        // Save topic DB
        Student student = (Student) session.getAttribute("student");
        Topic topic = DataConversion.TopicPojoToTopicBean(topicPojo, student);
        topicService.createTopic(topic);

        //Update revision Schedule
        revisionService.createRevisions(topicPojo.getTopicStudyDate(), topic );

        Set<Topic> createDateTopics = topicService.getTopicsBetweenStudyDates(topicPojo.getTopicStudyDate(), topicPojo.getTopicStudyDate());
        session.setAttribute("topics", createDateTopics);

        String message = "Topic: '" + topic.getTopicName() + "' for subject '"+ topic.getSubject()+ "' and Chapter '"+ topic.getChapterName()+ "' created, "+ System.lineSeparator()+" Below is study summary for " + topicPojo.getTopicStudyDate().format(dateTimeFormatter) ;
        session.setAttribute("message", message);
        return "topic_result";
    }

}
