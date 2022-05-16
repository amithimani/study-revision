package knowledgecafe.service;

import knowledgecafe.model.*;
import knowledgecafe.repos.RevisionRepository;
import knowledgecafe.repos.StudentRepository;
import knowledgecafe.repos.TopicRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final StudentRepository studentRepository;
    private final RevisionRepository revisionRepository;

    public TopicService(TopicRepository topicRepository, StudentRepository studentRepository, RevisionRepository revisionRepository){
        this.topicRepository = topicRepository;
        this.studentRepository = studentRepository;
        this.revisionRepository = revisionRepository;
    }

    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

    public Topic get(final Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long createTopic(Topic topic) {
        Set<Topic> existingTopics = topicRepository.getTopicsBySubject(topic.getSubject());
        if(existingTopics.contains(topic)){
            //update Topic
            return topic.getId();
        } else {
            return topicRepository.save(topic).getId();
        }
    }

    public Set<Topic> getTopicsByConfidenceLevelAndSubject(ConfidenceLevel confidenceLevel, Subject subject){
        return topicRepository.getTopicsByConfidenceLevelAndSubject(confidenceLevel, subject);
    }

    public Set<Topic> getTopicsBetweenStudyDates(LocalDate startDate, LocalDate endDate){
        return topicRepository.getTopicsByInitialStudyDateIsBetween(startDate, endDate);
    }
}
