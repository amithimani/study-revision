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

    private Subject subject;
    private Topic topic;
    private ConfidenceLevel confidenceLevel;

}
