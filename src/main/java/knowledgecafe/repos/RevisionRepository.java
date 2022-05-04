package knowledgecafe.repos;

import knowledgecafe.model.Revision;
import knowledgecafe.model.Student;
import knowledgecafe.model.Subject;
import knowledgecafe.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RevisionRepository extends JpaRepository<Revision, Long> {

    //List<Topic> findTopicsForRevisionBySubject(Subject subject);

    //List<Topic> findTopicsForRevisionForDate(LocalDate date);

    //List<Topic> findTopicsForRevisionBetweenDates(LocalDate startDate, LocalDate endDate);

}
