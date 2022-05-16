package knowledgecafe.service;

import knowledgecafe.model.Student;
import knowledgecafe.repos.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StudentService implements UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Student student = studentRepository.findStudentByUsername(s);
        if (student == null) {
            throw new UsernameNotFoundException(s);
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(
                student.getUsername()).password(student.getPasswordHash()).roles("USER").build();

        return userDetails;
    }
}
