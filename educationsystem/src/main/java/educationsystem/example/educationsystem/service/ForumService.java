package educationsystem.example.educationsystem.service;

import educationsystem.example.educationsystem.domain.Course;
import educationsystem.example.educationsystem.domain.Forum;
import educationsystem.example.educationsystem.domain.User;
import educationsystem.example.educationsystem.dto.CourseDto;
import educationsystem.example.educationsystem.dto.ForumDto;
import educationsystem.example.educationsystem.mapper.ForumMapper;
import educationsystem.example.educationsystem.repository.ForumRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ForumService {

  private final ForumRepository forumRepository;
  private final CourseService courseService;
  private final UserService userService;
  private final ForumMapper forumMapper;


  public Forum save(Forum forum, Integer userId) {
    User user = userService.findById(userId);
    forum.setEnabled(true);
    forumRepository.save(forum);
    user.getForumSet().add(forum);
    userService.save(user);
    return forum;
  }

  public List<ForumDto> findAllByCourse(Integer courseId) {
    Course course = courseService.findByIdCourse(courseId);
    if (course != null) {
      return course.getForumSet().stream()
        .filter(Forum::getEnabled)
        .sorted(Comparator.comparing(Forum::getForumId))
        .map(forumMapper::convertForumToDto)
        .collect(Collectors.toList());
    }
    return null;
  }

  public List<ForumDto> findAllByUser(Integer userId) {
    User user = userService.findById(userId);
    return user.getForumSet().stream()
      .filter(Forum::getEnabled)
      .sorted(Comparator.comparing(Forum::getForumId))
      .map(forumMapper::convertForumToDto)
      .collect(Collectors.toList());
  }

  public ForumDto disableForumEntry(Integer forumId, String familyName) {
    return forumRepository.findById(forumId)
      .map(forum -> {
        if (forum.getUser().getFamilyName().equals(familyName)) {
          forum.setEnabled(false);
          forumRepository.save(forum);
          return forumMapper.convertForumToDto(forum);
        }
        return null;
      })
      .orElse(null);
  }


}
