package knowledgecafe.util;

import knowledgecafe.dto.RevisionSearchPojo;
import knowledgecafe.dto.TopicPojo;
import knowledgecafe.model.Student;
import knowledgecafe.service.StudentService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public class SessionMgmt {

    public static void populateSessionAttributes(Model model, HttpSession session, StudentService studentService){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        Student student = studentService.getStudentByUsername(name);
        session.setAttribute("student", student);
        model.addAttribute("revisionSearchPojo", new RevisionSearchPojo());
        model.addAttribute("topicPojo", new TopicPojo());
    }
}
