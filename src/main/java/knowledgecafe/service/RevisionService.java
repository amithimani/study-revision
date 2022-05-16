package knowledgecafe.service;

import knowledgecafe.model.ConfidenceLevel;
import knowledgecafe.model.Revision;
import knowledgecafe.model.Subject;
import knowledgecafe.model.Topic;
import knowledgecafe.repos.RevisionRepository;
import knowledgecafe.util.RevisionConstants;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class RevisionService {
    private final RevisionRepository revisionRepository;

    public RevisionService(RevisionRepository revisionRepository){
        this.revisionRepository = revisionRepository;
    }


    public Set<Revision> getRevisionTopicsForToday(LocalDate revisionDate){
        return revisionRepository.getRevisionByRevisionDateEquals(revisionDate);
    }

    public Set<Revision> getRevisionTopicsBetweenDate(LocalDate startDate, LocalDate endDate){
        return revisionRepository.getRevisionByRevisionDateIsBetween(startDate, endDate);
    }

    public void createRevisions(LocalDate studyDate, Topic topic){
        //Create First revision After 1 Day
        revisionRepository.save(revisionBuilder(topic,studyDate.plusDays(RevisionConstants.FIRST_REVISION_DAYS), 1));
        //Create Second revision After 7 Day
        revisionRepository.save(revisionBuilder(topic,studyDate.plusDays(RevisionConstants.SECOND_REVISION_DAYS), 2));
        //Create Third revision After 21 Day
        revisionRepository.save(revisionBuilder(topic,studyDate.plusDays(RevisionConstants.THIRD_REVISION_DAYS), 3));
    }

    public Set<Revision> getRevisionsBySubjectBetweenDates(LocalDate startDate, LocalDate endDate, Subject subject){
        return revisionRepository.getRevisionByRevisionDateIsBetweenAndTopic_SubjectOrderByRevisionDate(startDate, endDate, subject);
    }

    public Set<Revision> getRevisionsByConfidenceBetweenDates(LocalDate startDate, LocalDate endDate, ConfidenceLevel confidenceLevel){
        return revisionRepository.getRevisionByRevisionDateIsBetweenAndTopic_ConfidenceLevelOrderByRevisionDate(startDate, endDate, confidenceLevel);
    }
    private Revision revisionBuilder(Topic topic, LocalDate revisionDate, int revisionNumber){
        Revision revision = new Revision();
        revision.setTopic(topic);
        revision.setRevisionDate(revisionDate);
        revision.setRevisionNumber(revisionNumber);
        return revision;
    }
}
