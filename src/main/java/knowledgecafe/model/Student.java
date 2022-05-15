package knowledgecafe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import knowledgecafe.model.Topic;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1,
            initialValue = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(nullable = false, unique = true)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String passwordHash;

    @OneToMany(mappedBy = "student")
    private Set<Topic> topics = new HashSet<>();

    public Student(String fullName, String username, String passwordHash) {
        this.fullName = fullName;
        this.username = username;
        this.passwordHash = passwordHash;
    }
}
