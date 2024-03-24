package educationsystem.example.educationsystem.service;

import educationsystem.example.educationsystem.domain.Course;
import educationsystem.example.educationsystem.domain.User;
import educationsystem.example.educationsystem.domain.UserCourse;
import educationsystem.example.educationsystem.domain.UserType;
import educationsystem.example.educationsystem.dto.CourseDto;
import educationsystem.example.educationsystem.dto.UserCourseDto;
import educationsystem.example.educationsystem.dto.UserDto;
import educationsystem.example.educationsystem.mapper.CourseMapper;
import educationsystem.example.educationsystem.mapper.UserCourseMapper;
import educationsystem.example.educationsystem.mapper.UserMapper;
import educationsystem.example.educationsystem.repository.UserCourseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserCourseService {

  private final UserCourseRepository userCourseRepository;
  private final UserMapper userMapper;
  private final UserCourseMapper userCourseMapper;
  private final CourseService courseService;
  private final CourseMapper courseMapper;

  public void registerUser(User user, Course course) {
    boolean registered = false;
    List<UserCourse>userCourseList = user.getUserCourses().stream().toList();
    for (int i=0;i<userCourseList.size();i++){
      if (userCourseList.get(i).getCourse().getCourseId() == course.getCourseId()){
        registered = true;
        break;
      }
    }
        if (course.getNumberOfRegisteredStudents() < course.getLimitOfRegisteredStudents()
        && !registered && course.getActivated()) {
         UserCourse userCourse = new UserCourse();
          userCourse.setUser(user);
          userCourse.setCourse(course);
          userCourseRepository.save(userCourse);
        }
    //kivételt kell dobni
  }

  public void deletRegistryFromCourse(User user, Course course){
    boolean registered = false;
    Integer userCourseId = null;
    List<UserCourse>userCourseList = user.getUserCourses().stream().toList();
    for (int i=0;i<userCourseList.size();i++){
      if (userCourseList.get(i).getCourse().getCourseId() == course.getCourseId()){
        registered = true;
        userCourseId = userCourseList.get(i).getId();
        break;
      }
    }
    if (registered){
    userCourseRepository.deleteById(userCourseId);
    }
  }

  public List<UserDto> findAllUser(Integer courseId) {
    return userCourseRepository.findAll().stream()
      .filter(x -> x.getCourse().getCourseId() == courseId)
      .map(UserCourse::getUser)
      .map(userMapper::convertUserToDto)
      .collect(Collectors.toList());
  }

  public List<UserCourseDto> findAllStudent(Integer courseId) {
    return userCourseRepository.findAll().stream()
      .filter(x -> x.getCourse().getCourseId().equals(courseId))
      .filter(x->x.getUser().getUserType().equals(UserType.STUDENT))
      .map(userCourseMapper::convertUserCourseToDto)
      .collect(Collectors.toList());
  }

  public List<UserDto>findAllStudents(Integer courseId){
    return this.findAllUser(courseId).stream()
      .filter(x->x.getUserType().equals(UserType.STUDENT))
      .collect(Collectors.toList());
  }

  public Long getNumberOfStudents(Integer courseId) {
    return userCourseRepository.findAll().stream()
      .filter(x -> x.getCourse().getCourseId() == courseId)
      .map(x -> x.getUser())
      .filter(x -> x.getUserType().equals(UserType.STUDENT))
      .count();
  }

  public List<UserCourse> findAll(){
    return userCourseRepository.findAll().stream()
      .collect(Collectors.toList());
  }


  public UserCourseDto setGrade(Integer courseId, Integer userId, Integer grade){
    UserCourse userCourse = null;
    Optional<UserCourse> userCourseOptional = userCourseRepository.findAll().stream()
      .filter(x -> x.getUser().getUserId().equals(userId) && x.getCourse().getCourseId().equals(courseId))
      .findFirst();
    if (userCourseOptional.isPresent()){
        userCourse = userCourseOptional.get();
        userCourse.setGrade(grade);
        userCourseRepository.save(userCourse);
        UserCourseDto userCourseDto = userCourseMapper.convertUserCourseToDto(userCourse);
        return userCourseDto;
    }
    return null;
  }

  public Integer getGrade(Integer courseId, Integer userId) {
    return userCourseRepository.findAll().stream()
      .filter(x -> x.getUser().getUserId().equals(userId) && x.getCourse().getCourseId().equals(courseId))
      .findFirst()
      .map(UserCourse::getGrade)
      .orElse(null);
  }

  public List<CourseDto>findAllCoursesById(Integer userId){
    return userCourseRepository.findAll().stream()
      .filter(x->x.getUser().getUserId().equals(userId))
      .map(x->x.getCourse())
      .filter(x->x.getActivated())
      .map(courseMapper::convertCourseToDto)
      .collect(Collectors.toList());
  }

  public List<UserCourseDto>findAllById(Integer userId){
    return userCourseRepository.findAll().stream()
      .filter(x->x.getUser().getUserId().equals(userId))
      .map(userCourseMapper::convertUserCourseToDto)
      .collect(Collectors.toList());
  }

  public List<CourseDto> findAvailableCourses(User user){
    List<CourseDto>allCoursesList = courseService.findAllSimple();
    List<CourseDto>allRegisteredCourseById = this.findAllCoursesById(user.getUserId());
    List<CourseDto>allAvailableCourses = new ArrayList<>(allCoursesList);
    allAvailableCourses.removeAll(allRegisteredCourseById);
    return allAvailableCourses;
  }



}
