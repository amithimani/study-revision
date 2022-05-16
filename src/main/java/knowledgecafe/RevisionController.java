package knowledgecafe;

import knowledgecafe.dto.RevisionSearchPojo;
import knowledgecafe.dto.TopicPojo;
import knowledgecafe.model.*;
import knowledgecafe.service.*;
import knowledgecafe.util.DataConversion;
import org.apache.tomcat.jni.Local;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
public class RevisionController {

    final StudentService studentService;
    final TopicService topicService;
    final RevisionService revisionService;
    final UserService userService;



    public RevisionController(StudentService studentService, RevisionService revisionService, TopicService topicService, UserService userService) {
        this.studentService = studentService;
        this.revisionService = revisionService;
        this.topicService = topicService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }


    @GetMapping("/revision-today")
    public String revision_today(Model model, HttpSession session) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        User user = userService.getUserByUsername(name);
        Student student = studentService.getStudentByUsername(name);
        Set<Revision> todaysRevisionTopic = revisionService.getRevisionTopicsForToday(LocalDate.now());

        // This should always be the case
            session.setAttribute("student", name);
            session.setAttribute("user", user);
            model.addAttribute("revisionSearchPojo", new RevisionSearchPojo());
            model.addAttribute("topicPojo", new TopicPojo());
            session.setAttribute("todaysRevisions", todaysRevisionTopic);
            Topic topic = new Topic();
            model.addAttribute("topic", topic);
            return "revision";


       // return "index";
    }


    @PostMapping("/revision-search-date")
    public String revisionSearchByDate(@Valid @ModelAttribute RevisionSearchPojo revision, Model model, HttpSession session) {
        // Save to DB after updating

        LocalDate revisionStartDate = revision.getRevisionStartDate();
        LocalDate revisionEndDate = revision.getRevisionEndDate();
        if (revisionEndDate == null){
            revisionEndDate = revisionStartDate;
        }
        Set<Revision> revisionsByDate = revisionService.getRevisionTopicsBetweenDate(revisionStartDate, revisionEndDate);
        session.setAttribute("revisionsByDate", revisionsByDate);
        model.addAttribute("topicPojo", new TopicPojo());
        model.addAttribute("revisionSearchPojo", new RevisionSearchPojo());

        return "revision_result";
    }
/*
    @PostMapping("/reservations-search")
    public String reservationsSearch(@ModelAttribute Reservation reservation, Model model, @SessionAttribute("user") User user, RedirectAttributes attr) {
        // Save to DB after updating
        assert user != null;
        reservation.setUser(user);
        Set<Reservation> userReservations = reservationService.findReservationsByDate(reservation.getReservationDate());
        user.setReservations(userReservations);
        return "result";
    }

   */
/*@GetMapping("/result")
    public String returnResults(@ModelAttribute Reservation reservation, Model model){
        return "result";
    }*/

    @PostMapping("/topic-submit")
    public String topicSubmit(@Valid @ModelAttribute TopicPojo topicPojo , Model model, HttpSession session) {
        // Save topic DB
        Student student = studentService.getStudentByUsername(session.getAttribute("student").toString());
        Topic topic = DataConversion.TopicPojoToTopicBean(topicPojo, student);
        topicService.createTopic(topic);

        //Update revision Schedule
        revisionService.createRevisions(topicPojo.getTopicStudyDate(),topic );

        model.addAttribute("message", "Topic Created successfully");
        model.addAttribute("revisionSearchPojo", new RevisionSearchPojo());
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
