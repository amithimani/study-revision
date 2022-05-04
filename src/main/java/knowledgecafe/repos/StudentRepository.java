package knowledgecafe.repos;

import knowledgecafe.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findStudentByUsername(String username);
}
