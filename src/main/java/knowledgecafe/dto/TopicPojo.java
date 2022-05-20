package knowledgecafe.dto;

import knowledgecafe.model.ConfidenceLevel;
import knowledgecafe.model.Subject;
import knowledgecafe.model.Topic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopicPojo {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate topicStudyDate;

    @NotBlank(message = "Subject is mandatory to select")
    @Size(max=40)
    private String subject;

    @NotBlank(message = "Topic is mandatory")
    @Size(max=100)
    private String topicName;

    @NotBlank(message = "Chapter is mandatory")
    @Size(max=40)
    private String chapter;

    @NotBlank(message = "Confidence level selection is mandatory")
    @Size(max=10)
    private String confidenceLevel;

    private Long topicID;
}
