package knowledgecafe.repos;

import knowledgecafe.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Set<Topic> getTopicsByConfidenceLevelAndInitialStudyDateBetween(ConfidenceLevel confidenceLevel, LocalDate startDate, LocalDate endDate);

    Set<Topic> getTopicsByInitialStudyDateIsBetween(LocalDate startDate, LocalDate endDate);

    Set<Topic> getTopicsBySubject(Subject subject);

    Set<Topic> getTopicsByConfidenceLevelAndSubject(ConfidenceLevel confidenceLevel, Subject subject);

    @Modifying(flushAutomatically = true)
    @Query("update Topic t set t.confidenceLevel=:confidenceLevel where t.id=:id")
    int updateTopicConfidenceById(ConfidenceLevel confidenceLevel, Long id);

}
