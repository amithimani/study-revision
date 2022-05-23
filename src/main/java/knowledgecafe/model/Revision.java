package knowledgecafe.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Revision {
    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "revision_sequence",
            sequenceName = "revision_sequence",
            allocationSize = 1,
            initialValue = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "revision_sequence"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate revisionDate;

    @Column(nullable = false)
    private Integer revisionNumber;

    @ColumnDefault("false")
    private boolean status;
}
