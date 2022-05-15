package knowledgecafe;

import knowledgecafe.dto.TopicPojo;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String reservationsSearch(@ModelAttribute Reservation reservation, Model model, @SessionAttribute("user") User user, RedirectAttributes attr) {
        // Save to DB after updating
        assert user != null;
        reservation.setUser(user);
        Set<Reservation> userReservations = reservationService.findReservationsByDate(reservation.getReservationDate());
        user.setReservations(userReservations);
        return "result";
    }

   /*@GetMapping("/result")
    public String returnResults(@ModelAttribute Reservation reservation, Model model){
        return "result";
    }*/

}
