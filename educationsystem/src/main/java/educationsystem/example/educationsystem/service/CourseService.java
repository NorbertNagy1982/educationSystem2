package educationsystem.example.educationsystem.service;

import educationsystem.example.educationsystem.domain.*;
import educationsystem.example.educationsystem.dto.CourseDto;
import educationsystem.example.educationsystem.dto.UserDto;
import educationsystem.example.educationsystem.mapper.CourseMapper;
import educationsystem.example.educationsystem.mapper.UserMapper;
import educationsystem.example.educationsystem.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseService {

  private final CourseRepository courseRepository;
  private final UserService userService;
  private final ForumService forumService;
  private final UserCourseService userCourseService;
  private final int LENGTH_OF_COURSECODE = 4;
  private final CourseMapper mapper;
  private final UserMapper userMapper;

  public CourseService(CourseRepository courseRepository, @Lazy UserService userService, @Lazy ForumService forumService,
                       CourseMapper mapper, UserMapper userMapper, @Lazy UserCourseService userCourseService) {
    this.courseRepository = courseRepository;
    this.userService = userService;
    this.forumService = forumService;
    this.mapper = mapper;
    this.userMapper = userMapper;
    this.userCourseService = userCourseService;
  }

  public CourseDto createCourse(Integer userId, CourseDto courseDto) {
    User user = userService.findById(userId);
    if (user != null && user.getActivated()) {
      Course course = mapper.convertDtoToCourse(courseDto);
      course.setActivated(true);
      course.setCode(this.codeGenerator());
      course.setForumSet(new HashSet<>());
      course.setNumberOfRegisteredStudents(0);
      course.setUserCourses(new HashSet<>());
      courseRepository.save(course);
      CourseDto dto = mapper.convertCourseToDto(course);
      return dto;
    }
    return null;
  }

  private int studentNumberCalculator(Set<User> userSet) {
    return (int) userSet.stream()
      .filter(x -> x.getUserType().equals(UserType.STUDENT))
      .count();
  }

  public Course findCourseByCode(String code) {
    Optional<Course> courseOptional = courseRepository.findAll().stream()
      .filter(Course::getActivated)
      .filter(x -> x.getCode().equalsIgnoreCase(code))
      .findFirst();
    return courseOptional.orElse(null);
  }

  private String codeGenerator() {
    StringBuilder sb = new StringBuilder();
    List<String> elements = List.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
      "Q", "R", "S", "T", "U", "W", "V", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    for (int i = 0; i < LENGTH_OF_COURSECODE; i++) {
      int randomNumber = RandomNumberGenerator.rng(0, (elements.size() - 1));
      String element = elements.get(randomNumber);
      sb.append(element);
    }
    return sb.toString();
  }

  public CourseDto findById(Integer id) {
    return courseRepository.findById(id)
      .filter(Course::getActivated)
      .map(mapper::convertCourseToDto)
      .orElse(null);
  }

  public Course findByIdCourse(Integer id) {
    return courseRepository.findById(id)
      .filter(Course::getActivated)
      .orElse(null);
  }

  public List<CourseDto> findAllSimple() {
    return courseRepository.findAll().stream()
      .filter(Course::getActivated)
      .map(mapper::convertCourseToDto)
      .collect(Collectors.toList());
  }


  public List<CourseDto> findAll() {
    return courseRepository.findAll().stream()
      .filter(Course::getActivated)
      .map(mapper::convertCourseToDto)
      .peek(x -> x.setNumberOfRegisteredStudents(this.studentNumberCalculator(x.getUserSet())))
      .collect(Collectors.toList());
  }

  public List<CourseDto>findAllInactivated(){
    return courseRepository.findAll().stream()
      .filter(x->!x.getActivated())
      .map(mapper::convertCourseToDto)
      .collect(Collectors.toList());
  }

  public CourseDto registerToCourse(Integer userId, Integer courseId) {
    User user = userService.findById(userId);
    Course course = courseRepository.findById(courseId).orElse(null);
    assert course != null;
    userCourseService.registerUser(user, course);
    CourseDto courseDto = mapper.convertCourseToDto(course);
    return courseDto;
  }

  public CourseDto registerToCourseByCode(String userCode, String courseCode) {
    User user = userService.findAll().stream().filter(x -> x.getUserCode().equals(userCode)).findFirst().orElse(null);
    Course course = courseRepository.findAll().stream().filter(x -> x.getCode().equals(courseCode)).findFirst().orElse(null);
    assert course != null;
    assert user != null;
    userCourseService.registerUser(user, course);
    CourseDto courseDto = mapper.convertCourseToDto(course);
    return courseDto;
  }

  public CourseDto deleteRegistryFromCourse(Integer userId, Integer courseId) {
    User user = userService.findById(userId);
    Course course = courseRepository.findById(courseId).orElse(null);
    assert course != null;
    userCourseService.deletRegistryFromCourse(user, course);
    CourseDto courseDto = mapper.convertCourseToDto(course);
    return courseDto;
  }

  public Course setCourse(Integer userId, Integer courseId, String title, CourseType courseType, String description, ExamType examType,
                          int limit, LocalDate regStart, LocalDate regEnd) {
    User user = userService.findById(userId);
    if (user != null && user.getActivated()) {
      Course course = courseRepository.findById(courseId).orElse(null);
      if (course != null) {
        course.setTitle(title);
        course.setCourseType(courseType);
        course.setCourseDescription(description);
        course.setActivated(true);
        course.setCode(this.codeGenerator());
        course.setForumSet(new HashSet<>());
        course.setExamType(examType);
        course.setLimitOfRegisteredStudents(limit);
        course.setRegistrationStart(regStart);
        course.setRegistrationEnd(regEnd);
        course.setUserCourses(new HashSet<>());
        courseRepository.save(course);
        return course;
      }
    }
    return null;
  }

  public Forum createForumEntry(Integer userId, Integer courseId, Forum forum) {
    User user = userService.findById(userId);
    Course course = this.findByIdCourse(courseId);
    if (user != null && course != null && user.getActivated()) {
      Forum forumEntry = new Forum();
      forumEntry.setUser(user);
      forumEntry.setCourse(course);
      forumEntry.setMessage(forum.getMessage());
      forumEntry.setDateOfMessage(forum.getDateOfMessage());
      courseRepository.save(course);
      forumService.save(forumEntry, userId);
      return forumEntry;
    }
    return null; //specifikus kivétel kell.
  }

  public List<UserDto> findAllRegisteredUser(Integer courseId) {
    List<UserDto> userList = userCourseService.findAllUser(courseId);
    return userList.stream()
      .collect(Collectors.toList());
  }

  public List<UserDto> findAllRegisteredStudent(Integer courseId) {
    List<UserDto> userList = userCourseService.findAllUser(courseId);
    return userList.stream()
      .filter(x -> x.getUserType().equals(UserType.STUDENT))
      .collect(Collectors.toList());
  }

  public CourseDto setCourseDescription(Integer courseId, String text) {
    Course course = this.findByIdCourse(courseId);
    assert course != null;
    course.setCourseDescription(text);
    courseRepository.save(course);
    CourseDto courseDto = mapper.convertCourseToDto(course);
    return courseDto;
  }

  public List<String> getCourseCodes() {
    return courseRepository.findAll().stream()
      .filter(x->x.getActivated())
      .map(x -> x.getCode())
      .collect(Collectors.toList());
  }

  public List<String> getInactivatedCourseCodes() {
    return courseRepository.findAll().stream()
      .filter(x->!x.getActivated())
      .map(x -> x.getCode())
      .collect(Collectors.toList());
  }

  public CourseDto modifyCourseData(String code, CourseDto courseDto) {
    Course courseToModify = courseRepository.findAll().stream()
      .filter(x -> x.getCode().equals(code))
      .filter(x->x.getActivated())
      .findFirst().orElse(null);
    assert courseToModify != null;
    if (courseDto.getTitle() != null) {
      courseToModify.setTitle(courseDto.getTitle());
    }
    if (courseDto.getCourseDescription() != null) {
      courseToModify.setCourseDescription(courseDto.getCourseDescription());
    }
    if (courseDto.getCourseType() != null) {
      courseToModify.setCourseType(courseDto.getCourseType());
    }
    if (courseDto.getRegistrationStart() != null) {
      courseToModify.setRegistrationStart(courseDto.getRegistrationStart());
    }
    if (courseDto.getRegistrationEnd() != null) {
      courseToModify.setRegistrationEnd(courseDto.getRegistrationEnd());
    }
    if (courseDto.getLimitOfRegisteredStudents() != null) {
      courseToModify.setLimitOfRegisteredStudents(courseDto.getLimitOfRegisteredStudents());
    }
    if (courseDto.getExamType() != null) {
      courseToModify.setExamType(courseDto.getExamType());
    }
    courseRepository.save(courseToModify);
    return mapper.convertCourseToDto(courseToModify);
  }

  public CourseDto inactivateCourse(String courseCode) {
    Course course = courseRepository.findAll().stream()
      .filter(x -> x.getCode().equals(courseCode))
      .findFirst().orElse(null);
    course.setActivated(false);
    courseRepository.save(course);
    return mapper.convertCourseToDto(course);
  }

  public CourseDto reactivateCourse(String courseCode) {
    Course course = courseRepository.findAll().stream()
      .filter(x -> x.getCode().equals(courseCode))
      .findFirst().orElse(null);
    course.setActivated(true);
    courseRepository.save(course);
    return mapper.convertCourseToDto(course);
  }

  public List<CourseDto> findAllCoursesByNameExcerpt(String nameExcerpt){
    return courseRepository.findAll().stream()
      .filter(x->x.getTitle().startsWith(nameExcerpt))
      .map(mapper::convertCourseToDto)
      .collect(Collectors.toList());
  }


}
