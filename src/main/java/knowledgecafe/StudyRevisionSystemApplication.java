package knowledgecafe;

import knowledgecafe.model.Student;
import knowledgecafe.repos.StudentRepository;
import knowledgecafe.util.LoggingInterceptor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@SpringBootApplication
public class StudyRevisionSystemApplication extends SpringBootServletInitializer {



  public static void main(String[] args) {
    SpringApplication.run(StudyRevisionSystemApplication.class, args);
  }

  @Bean
  public CommandLineRunner loadData(
          StudentRepository studentRepository) {
    return (args) -> {
      if(studentRepository.findStudentByUsername("krish") == null) {
        studentRepository.save(
                new Student("Krish Himani", "krish", "$2a$10$kc9i7dLtroyOkcFICEiAJ.HOlRtsg2A2tApz/vpMxx5.bsaA3jJOi"));
      }
    };
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(StudyRevisionSystemApplication.class);
  }

}
