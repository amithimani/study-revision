package knowledgecafe.dto;

import knowledgecafe.model.Revision;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopicsListPojo {
    private Set<TopicPojo> topicsToBeUpdated;

    public void addTopicToList(TopicPojo topic){
        topicsToBeUpdated.add(topic);
    }
}
