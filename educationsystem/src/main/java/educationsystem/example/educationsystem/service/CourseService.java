package educationsystem.example.educationsystem.service;

import educationsystem.example.educationsystem.domain.*;
import educationsystem.example.educationsystem.dto.CourseDto;
import educationsystem.example.educationsystem.dto.UserDto;
import educationsystem.example.educationsystem.mapper.CourseMapper;
import educationsystem.example.educationsystem.mapper.UserMapper;
import educationsystem.example.educationsystem.repository.CourseRepository;
import educationsystem.example.educationsystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
    private final int LENGTH_OF_COURSECODE = 4;
    private final CourseMapper mapper;
    private final UserMapper userMapper;

    public CourseService(CourseRepository courseRepository, UserService userService, @Lazy ForumService forumService,
                         CourseMapper mapper, UserMapper userMapper) {
        this.courseRepository = courseRepository;
        this.userService = userService;
        this.forumService = forumService;
        this.mapper = mapper;
        this.userMapper = userMapper;
    }

    public CourseDto createCourse(Integer userId, CourseDto courseDto) {
        User user = userService.findById(userId);
        if (user != null && user.getActivated()) {
            Course course = mapper.convertDtoToCourse(courseDto);
            course.setActivated(true);
            course.setCode(this.codeGenerator());
            course.setForumSet(new HashSet<>());
            course.setUserSet(new HashSet<>());
            course.getUserSet().add(user);
            user.getCourseSet().add(course);
            course.setNumberOfRegisteredStudents(this.studentNumberCalculator(course.getUserSet()));
            courseRepository.save(course);
            CourseDto dto = mapper.convertCourseToDto(course);
            return dto;
        }
        return null;
    }

    private int studentNumberCalculator(Set<User> userSet) {
        return (int) userSet.stream()
                .filter(x -> x.getUserType() == UserType.STUDENT)
                .count();
    }

    public Course findCourseByCode(String code) {
        Optional<Course> courseOptional = courseRepository.findAll().stream()
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
      CourseDto courseDto = null;
      Optional courseOptional = courseRepository.findById(id);
      if (courseOptional.isPresent()){
        Course course = (Course)courseOptional.get();
        courseDto = mapper.convertCourseToDto(course);
      }
        return courseDto;
    }

    public List<CourseDto>findAll(){
        return courseRepository.findAll().stream()
                .map(mapper::convertCourseToDto)
          .peek(x->x.setNumberOfRegisteredStudents(this.studentNumberCalculator(x.getUserSet())))
                .collect(Collectors.toList());
    }

    public CourseDto registerToCourse(Integer userId, Integer courseId) {
        User user = userService.findById(userId);
        Course course = courseRepository.findById(courseId).orElse(null);
        course.getUserSet().add(user);
        course.setNumberOfRegisteredStudents(course.getNumberOfRegisteredStudents()+1);
        courseRepository.save(course);
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
                course.setUserSet(new HashSet<>());
                course.getUserSet().add(user);
                user.getCourseSet().add(course);
                course.setNumberOfRegisteredStudents(this.studentNumberCalculator(course.getUserSet()));
                courseRepository.save(course);
                return course;
            }
        }
        return null;
    }

    public Forum createForumEntry(Integer userId, Integer courseId, Forum forum){
        User user = userService.findById(userId);
        Course course = courseRepository.findById(courseId).orElse(null);
        System.out.println(user);
        System.out.println(course);
        if (user != null && course != null && user.getActivated()){
            Forum forumEntry = new Forum();
            forumEntry.setUser(user);
            forumEntry.setCourse(course);
            forumEntry.setMessage(forum.getMessage());
            forumEntry.setDateOfMessage(forum.getDateOfMessage());
            courseRepository.save(course);
            forumService.save(forumEntry);
            return forumEntry;
        }
        return null; //specifikus kivétel kell.
    }

public List<UserDto>findAllRegisteredUser(Integer courseId){
        Course course = courseRepository.findById(courseId).orElse(null);
        return course.getUserSet().stream()
                .map(userMapper::convertUserToDto)
                .collect(Collectors.toList());
}
}
