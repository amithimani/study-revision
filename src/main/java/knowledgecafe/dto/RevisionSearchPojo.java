package knowledgecafe.dto;

import knowledgecafe.model.ConfidenceLevel;
import knowledgecafe.model.Subject;
import knowledgecafe.model.Topic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RevisionSearchPojo {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate revisionStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate revisionEndDate;

   // @NotBlank(message = "Subject is mandatory to select")
    private Subject subject;

   // @NotBlank(message = "Topic is mandatory")
    private Topic topic;

   // @NotBlank(message = "Confidence level selection is mandatory")
    private ConfidenceLevel confidenceLevel;

}
