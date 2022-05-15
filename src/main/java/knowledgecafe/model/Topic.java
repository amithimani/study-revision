package knowledgecafe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints={ @UniqueConstraint(columnNames = {"student_id","topicName", "subject", "chapterName"})})
public class Topic {
    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "topic_sequence",
            sequenceName = "topic_sequence",
            allocationSize = 1,
            initialValue = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false, unique = false)
    private String topicName;

    @Column(nullable = false, unique = false)
    private Subject subject;

    @Column(nullable = false, unique = false)
    private String chapterName;

    @Column(nullable = true, unique = false)
    private String subTopic;

    @Column(nullable = false ) //TODO: default value population
    private ConfidenceLevel confidenceLevel;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate initialStudyDate;
}
