package knowledgecafe.service;

import knowledgecafe.model.Revision;
import knowledgecafe.model.Topic;
import knowledgecafe.repos.RevisionRepository;
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
}
