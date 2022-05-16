package knowledgecafe.repos;

import knowledgecafe.model.*;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface RevisionRepository extends JpaRepository<Revision, Long> {
    Set<Revision> getRevisionByRevisionDateEquals(LocalDate revisionDate);

    Set<Revision> getRevisionByRevisionDateIsBetween(LocalDate startDate, LocalDate endDate);

    Set<Revision> getRevisionByRevisionDateIsBetweenAndTopic_SubjectOrderByRevisionDate(LocalDate startDate, LocalDate endDate, Subject subject);

    Set<Revision> getRevisionByRevisionDateIsBetweenAndTopic_ConfidenceLevelOrderByRevisionDate(LocalDate startDate, LocalDate endDate, ConfidenceLevel confidenceLevel);
}
