package educationsystem.example.educationsystem.service;

import educationsystem.example.educationsystem.domain.*;
import educationsystem.example.educationsystem.repository.CourseRepository;
import educationsystem.example.educationsystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserService userService;
    private final int LENGTH_OF_COURSECODE = 4;

    public Course createCourse(Integer userId, Course courseParam) {

        User user = userService.findById(userId);
        if (user != null && user.getActivated()) {
            Course course = new Course();
            course.setTitle(courseParam.getTitle());
            course.setCourseType(courseParam.getCourseType());
            course.setCourseDescription(courseParam.getCourseDescription());
            course.setActivated(true);
            course.setCode(this.codeGenerator());
            course.setForumSet(new HashSet<>());
            course.setExamType(courseParam.getExamType());
            course.setLimitOfRegisteredStudents(courseParam.getLimitOfRegisteredStudents());
            course.setRegistrationStart(courseParam.getRegistrationStart());
            course.setRegistrationEnd(courseParam.getRegistrationEnd());
            course.setUserSet(new HashSet<>());
            course.getUserSet().add(user);
            user.getCourseSet().add(course);
            course.setNumberOfRegisteredStudents(this.studentNumberCalculator(course.getUserSet()));
            courseRepository.save(course);
            return course;
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

    public Course findById(Integer id) {
        return courseRepository.findById(id).orElse(null);
    }

    public List<Course>findAll(){
        return courseRepository.findAll().stream()
                .collect(Collectors.toList());
    }

    public void registerToCourse(Integer userId, Integer courseId) {
        User user = userService.findById(userId);
        Course course = courseRepository.findById(courseId).orElse(null);
        course.getUserSet().add(user);
        courseRepository.save(course);
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

}
