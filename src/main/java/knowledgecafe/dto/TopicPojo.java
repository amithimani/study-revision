package knowledgecafe.dto;

import knowledgecafe.model.ConfidenceLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopicPojo {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate topicStudyDate;

    private String subject;

    private String chapter;

    private String confidenceLevel;

    private String topicName;

}
