package knowledgecafe.repos;

import knowledgecafe.model.ConfidenceLevel;
import knowledgecafe.model.Student;
import knowledgecafe.model.Subject;
import knowledgecafe.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Set<Topic> getTopicsByConfidenceLevelAndInitialStudyDateBetween(ConfidenceLevel confidenceLevel, LocalDate startDate, LocalDate endDate);

    Set<Topic> getTopicsByInitialStudyDateIsBetween(LocalDate startDate, LocalDate endDate);

    Set<Topic> getTopicsBySubject(Subject subject);

    Set<Topic> getTopicsByConfidenceLevelAndSubject(ConfidenceLevel confidenceLevel, Subject subject);

}
