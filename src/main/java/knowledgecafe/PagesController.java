package knowledgecafe;

import knowledgecafe.model.Reservation;
import knowledgecafe.model.Student;
import knowledgecafe.model.Topic;
import knowledgecafe.model.User;
import knowledgecafe.service.ReservationService;
import knowledgecafe.service.TopicService;
import knowledgecafe.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Controller
public class PagesController {

    final UserService userService;
    final ReservationService reservationService;
    final TopicService topicService;

    public PagesController(UserService userService, ReservationService reservationService, TopicService topicService) {
        this.userService = userService;
        this.reservationService = reservationService;
        this.topicService = topicService;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/reservations")
    public String reservations(Model model, HttpSession session) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        User user = userService.getUserByUsername(name);

        // This should always be the case
        if (user != null) {
            session.setAttribute("user", user);

            // Empty reservation object in case the user creates a new reservation
            Reservation reservation = new Reservation();
            model.addAttribute("reservation", reservation);

            return "reservations";
        }

        return "index";
    }

    @PostMapping("/reservations-submit")
    public String reservationsSubmit(@ModelAttribute Reservation reservation, Model model, @SessionAttribute("user") User user) {
        // Save to DB after updating
        assert user != null;
        reservation.setUser(user);
        reservationService.create(reservation);
        Set<Reservation> userReservations = user.getReservations();
        userReservations.add(reservation);
        user.setReservations(userReservations);
        userService.update(user.getId(), user);
        return "redirect:/reservations";
    }

    @PostMapping("/reservations-search")
    public String reservationsSearch(@ModelAttribute Reservation reservation, Model model, @SessionAttribute("user") User user) {
        // Save to DB after updating
        assert user != null;
        //reservation.setUser(user);
        //reservationService.create(reservation);
        List<Reservation> userReservations = reservationService.findReservationsByDate(reservation.getReservationDate());
        model.addAttribute("SearchDate", reservation.getReservationDate());
        model.addAttribute("reservationResult", userReservations);
        userReservations.addAll(userReservations);
        //user.setReservations(userReservations);
        //userService.update(user.getId(), user);
        return "redirect:/resultreservations";
    }

    @PostMapping("/topic-submit")
    public String topicSubmit(@ModelAttribute Topic topic , Model model, @SessionAttribute("user") Student user) {
        // Save to DB after updating
        assert user != null;
        topic.setStudent(user);
        topicService.createTopic(topic, topic.getSubject());
        Set<Topic> studentTopics = user.getTopics();
        return "redirect:/reservations";
    }
}
