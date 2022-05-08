package knowledgecafe.repos;

import knowledgecafe.model.AmenityType;
import knowledgecafe.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findReservationsByAmenityType(AmenityType amenityType);

    List<Reservation> findReservationsByReservationDateAndStartTimeBeforeAndEndTimeAfterOrStartTimeBetween
            (LocalDate reservationDate, LocalTime startTime, LocalTime endTime, LocalTime betweenStart, LocalTime betweenEnd);

    Set<Reservation> findReservationByReservationDate(LocalDate reservationDate);
}
