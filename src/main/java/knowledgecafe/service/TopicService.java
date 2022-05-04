package knowledgecafe.service;

import knowledgecafe.model.Reservation;
import knowledgecafe.model.Student;
import knowledgecafe.model.Subject;
import knowledgecafe.model.Topic;
import knowledgecafe.repos.RevisionRepository;
import knowledgecafe.repos.StudentRepository;
import knowledgecafe.repos.TopicRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    public Long createTopic(Topic topic, Subject subject) {
        List<Topic> existingTopics = topicRepository.getTopicsBySubject(subject);
        if(existingTopics.contains(topic)){
            //update Topic
            return topic.getId();
        } else {
            return topicRepository.save(topic).getId();
        }
    }


}
