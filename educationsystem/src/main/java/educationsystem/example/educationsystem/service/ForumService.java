package educationsystem.example.educationsystem.service;

import educationsystem.example.educationsystem.domain.Course;
import educationsystem.example.educationsystem.domain.Forum;
import educationsystem.example.educationsystem.domain.User;
import educationsystem.example.educationsystem.dto.CourseDto;
import educationsystem.example.educationsystem.repository.ForumRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ForumService {

    private final ForumRepository forumRepository;
    private final CourseService courseService;



public void save(Forum forum){
    forumRepository.save(forum);
}

public List<Forum> findAll(Integer courseId) {
    CourseDto course = courseService.findById(courseId);
    if (course != null) {
      //  return course.getForumSet().stream()
        //        .collect(Collectors.toList());
    }
return null;
}




}
