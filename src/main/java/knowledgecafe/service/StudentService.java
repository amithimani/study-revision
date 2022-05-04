package knowledgecafe.service;

import knowledgecafe.model.Student;
import knowledgecafe.model.User;
import knowledgecafe.repos.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService (StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public Student get(final Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Student getStudentByUsername(String username) {
        return studentRepository.findStudentByUsername(username);
    }

    public Long create(final Student student) {
        return studentRepository.save(student).getId();
    }

    public void update(final Long id, final Student student) {
        final Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        studentRepository.save(student);
    }

    public void delete(final Long id) {
        studentRepository.deleteById(id);
    }

}
