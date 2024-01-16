package educationsystem.example.educationsystem.service;

import educationsystem.example.educationsystem.domain.Course;
import educationsystem.example.educationsystem.domain.Forum;
import educationsystem.example.educationsystem.domain.User;
import educationsystem.example.educationsystem.repository.ForumRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Transactional
@AllArgsConstructor
public class ForumService {

    private final ForumRepository forumRepository;
    private final CourseService courseService;
    private final UserService userService;


    public Forum createForumEntry(Integer userId, Integer courseId, Forum forum){
        User user = userService.findById(userId);
        Course course = courseService.findById(courseId);
        if (user != null && course != null && user.getActivated()){
        Forum forumEntry = new Forum();
        forumEntry.setUser(user);
        forumEntry.setCourse(course);
        forumEntry.setMessage(forum.getMessage());
        forumEntry.setDateOfMessage(forum.getDateOfMessage());
        forumRepository.save(forumEntry);
        return forumEntry;
        }
        return null; //specifikus kivétel kell.
    }


}
