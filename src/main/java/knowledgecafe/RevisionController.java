package knowledgecafe;

import knowledgecafe.dto.RevisionSearchPojo;
import knowledgecafe.dto.TopicPojo;
import knowledgecafe.model.*;
import knowledgecafe.service.*;
import knowledgecafe.util.DataConversion;
import knowledgecafe.util.SessionMgmt;
import org.apache.tomcat.jni.Local;
import org.dom4j.rule.Mode;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
public class RevisionController {

    final StudentService studentService;
    final TopicService topicService;
    final RevisionService revisionService;


    public RevisionController(StudentService studentService, RevisionService revisionService, TopicService topicService) {
        this.studentService = studentService;
        this.revisionService = revisionService;
        this.topicService = topicService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("revisionSearchPojo", new RevisionSearchPojo());
        return "index";
    }

    @GetMapping("/revision-today")
    public String revision_today(Model model, HttpSession session) {
        SessionMgmt.populateSessionAttributes(model, session, studentService);
        Set<Revision> todaysRevisionTopic = revisionService.getRevisionTopicsForToday(LocalDate.now());

        // This should always be the case
        session.setAttribute("todaysRevisions", todaysRevisionTopic);
        Topic topic = new Topic();
        model.addAttribute("topic", topic);
        return "revision";


       // return "index";
    }


    @PostMapping("/revision-search-date")
    public String revisionSearchByDate(@Valid @ModelAttribute RevisionSearchPojo revision, Model model, HttpSession session) {
        SessionMgmt.populateSessionAttributes(model, session, studentService);

        // Save to DB after updating

        LocalDate revisionStartDate = revision.getRevisionStartDate();
        LocalDate revisionEndDate = revision.getRevisionEndDate();
        if (revisionEndDate == null){
            revisionEndDate = revisionStartDate;
        }
        Set<Revision> revisionsByDate = revisionService.getRevisionTopicsBetweenDate(revisionStartDate, revisionEndDate);
        session.setAttribute("revisionsByDate", revisionsByDate);

        return "revision_result";
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
        session.setAttribute("revisionsByDate", revisionsByDate);

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
        session.setAttribute("revisionsByDate", revisionsByDate);

        return "revision_result";
    }

    @PostMapping("/study-report")
    public String studyReport(@Valid @ModelAttribute RevisionSearchPojo revision, Model model, HttpSession session) {
        SessionMgmt.populateSessionAttributes(model, session, studentService);

        // Save to DB after updating
        LocalDate studyStart = revision.getRevisionStartDate();
        LocalDate studyEnd = studyStart.plusMonths(1);
        Set<Topic> monthlyStudy = topicService.getTopicsBetweenStudyDates(studyStart, studyEnd);

        session.setAttribute("monthlyStudyTopics", monthlyStudy);

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
        revisionService.createRevisions(topicPojo.getTopicStudyDate(),topic );

        model.addAttribute("message", "Topic Created successfully");
        return "revision";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
