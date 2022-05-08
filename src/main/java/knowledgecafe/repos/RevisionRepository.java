package knowledgecafe.repos;

import knowledgecafe.model.Revision;
import knowledgecafe.model.Student;
import knowledgecafe.model.Subject;
import knowledgecafe.model.Topic;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface RevisionRepository extends JpaRepository<Revision, Long> {
    Set<Revision> getRevisionByRevisionDateEquals(LocalDate revisionDate);

    Set<Revision> getRevisionByRevisionDateIsBetween(LocalDate startDate, LocalDate endDate);
}
