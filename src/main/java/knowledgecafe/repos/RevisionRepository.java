package knowledgecafe.repos;

import knowledgecafe.model.*;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Set;

public interface RevisionRepository extends JpaRepository<Revision, Long> {
    Set<Revision> getRevisionByRevisionDateEquals(LocalDate revisionDate);

    Set<Revision> getRevisionByRevisionDateIsBetweenOrderByRevisionDate(LocalDate startDate, LocalDate endDate);

    Set<Revision> getRevisionByRevisionDateIsBetweenAndTopic_SubjectOrderByRevisionDate(LocalDate startDate, LocalDate endDate, Subject subject);

    Set<Revision> getRevisionByRevisionDateIsBetweenAndTopic_ConfidenceLevelOrderByRevisionDate(LocalDate startDate, LocalDate endDate, ConfidenceLevel confidenceLevel);

    Set<Revision> getRevisionByRevisionDateIsBetweenAndStatusOrderByRevisionDate(LocalDate startDate, LocalDate endDate, boolean status);

    Set<Revision> getRevisionByRevisionDateEqualsAndAndTopic_Id(LocalDate revisionDate, Long topicID);

    @Modifying(flushAutomatically = true)
    @Query("update Revision r set r.status=:status where r.id=:id")
    int updateRevisionStatusById(boolean status, Long id);

}
