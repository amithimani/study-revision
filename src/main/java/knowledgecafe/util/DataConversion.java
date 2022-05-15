package knowledgecafe.util;

import knowledgecafe.dto.TopicPojo;
import knowledgecafe.model.ConfidenceLevel;
import knowledgecafe.model.Student;
import knowledgecafe.model.Subject;
import knowledgecafe.model.Topic;

public class DataConversion {

    public static Topic TopicPojoToTopicBean(TopicPojo topicpojo, Student student){
        Topic topic = new Topic();
        topic.setTopicName(topicpojo.getTopicName());
        topic.setSubject(Subject.valueOf(topicpojo.getSubject()));
        topic.setChapterName(topicpojo.getChapter());
        topic.setStudent(student);
        topic.setConfidenceLevel(ConfidenceLevel.valueOf(topicpojo.getConfidenceLevel()));
        topic.setInitialStudyDate(topicpojo.getTopicStudyDate());
        return topic;
    }
}
