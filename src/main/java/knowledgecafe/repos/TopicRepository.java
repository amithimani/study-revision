package knowledgecafe.repos;

import knowledgecafe.model.ConfidenceLevel;
import knowledgecafe.model.Student;
import knowledgecafe.model.Subject;
import knowledgecafe.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> getTopicsByConfidenceLevelAndInitialStudyDateBetween(ConfidenceLevel confidenceLevel, LocalDate startDate, LocalDate endDate);

    List<Topic> getTopicsByInitialStudyDateIsBetween(LocalDate startDate, LocalDate endDate);

    List<Topic> getTopicsBySubject(Subject subject);

}
