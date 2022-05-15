package knowledgecafe;

import knowledgecafe.model.AmenityType;
import knowledgecafe.model.Capacity;
import knowledgecafe.model.Student;
import knowledgecafe.model.User;
import knowledgecafe.repos.CapacityRepository;
import knowledgecafe.repos.StudentRepository;
import knowledgecafe.repos.UserRepository;
import knowledgecafe.util.LoggingInterceptor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class StudyRevisionSystemApplication implements WebMvcConfigurer {

  private final Map<AmenityType, Integer> initialCapacities =
      new HashMap<>() {
        {
          put(AmenityType.GYM, 20);
          put(AmenityType.POOL, 4);
          put(AmenityType.SAUNA, 1);
        }
      };

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoggingInterceptor()).addPathPatterns("/**");
  }

  public static void main(String[] args) {
    SpringApplication.run(StudyRevisionSystemApplication.class, args);
  }

  @Bean
  public CommandLineRunner loadData(
      UserRepository userRepository,
      StudentRepository studentRepository,
      CapacityRepository capacityRepository) {
    return (args) -> {
      userRepository.save(
          new User("Amit Himani", "admin", bCryptPasswordEncoder().encode("12345")));
      studentRepository.save(
              new Student("Amit Himani", "admin",bCryptPasswordEncoder().encode("12345"))
      );


      for (AmenityType amenityType : initialCapacities.keySet()) {
        capacityRepository.save(new Capacity(amenityType, initialCapacities.get(amenityType)));
      }
    };
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
