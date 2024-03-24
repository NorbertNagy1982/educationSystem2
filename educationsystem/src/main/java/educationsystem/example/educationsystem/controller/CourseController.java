package educationsystem.example.educationsystem.controller;

import educationsystem.example.educationsystem.domain.Course;
import educationsystem.example.educationsystem.domain.Forum;
import educationsystem.example.educationsystem.dto.CourseDto;
import educationsystem.example.educationsystem.dto.UserDto;
import educationsystem.example.educationsystem.service.CourseService;
import educationsystem.example.educationsystem.service.UserCourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CourseController {

  private final CourseService courseService;
  private final UserCourseService userCourseService;

  @PostMapping("/create")
  public ResponseEntity<CourseDto> createCourse(@RequestParam(name = "userid") Integer id,
                                                @RequestBody CourseDto courseDto) {
    return ResponseEntity.ok(courseService.createCourse(id, courseDto));
  }

  //student
  @GetMapping("/all")
  public ResponseEntity<List<CourseDto>> findAll() {
    return ResponseEntity.ok(courseService.findAll());
  }

  @GetMapping("/allinactivated")
  public ResponseEntity<List<CourseDto>> findAllInactivated() {
    return ResponseEntity.ok(courseService.findAllInactivated());
  }

  //student
  @PostMapping("/forum")
  public ResponseEntity<Forum> createForumEntry(@RequestParam(name = "userid") Integer userId,
                                                @RequestParam(name = "courseid") Integer courseId,
                                                @RequestBody Forum forum) {
    return ResponseEntity.ok(courseService.createForumEntry(userId, courseId, forum));
  }

  @PutMapping("/register")
  public ResponseEntity<CourseDto> registerToCourse(@RequestParam(name = "userid") Integer userId,
                                                    @RequestParam(name = "courseid") Integer courseId) {
    return ResponseEntity.ok(courseService.registerToCourse(userId, courseId));
  }

  @PutMapping("/registerbycode")
  public ResponseEntity<CourseDto> registerToCourseByCode(@RequestParam(name = "usercode") String userCode,
                                                    @RequestParam(name = "coursecode") String courseCode) {
    return ResponseEntity.ok(courseService.registerToCourseByCode(userCode,courseCode));
  }

  @DeleteMapping("/delreg")
  public ResponseEntity<CourseDto> deleteRegisteredCourse(@RequestParam(name = "userid") Integer userId,
                                                          @RequestParam(name = "courseid") Integer courseId) {
    return ResponseEntity.ok(courseService.deleteRegistryFromCourse(userId, courseId));
  }

  @GetMapping("/id")
  public ResponseEntity<CourseDto> findById(@RequestParam(name = "courseid") Integer id) {
    return ResponseEntity.ok(courseService.findById(id));
  }

  @GetMapping("/student")
  public ResponseEntity<List<UserDto>> findAllStudentOfCourse(@RequestParam(name = "courseid") Integer courseId) {
    return ResponseEntity.ok(userCourseService.findAllStudents(courseId));
  }


  @PutMapping("/description")
  public ResponseEntity<CourseDto> setCourseDescription(@RequestParam(name = "courseid") Integer courseId,
                                                        @RequestParam(name = "text") String text) {
    return ResponseEntity.ok(courseService.setCourseDescription(courseId, text));
  }

  @GetMapping("/code")
  public ResponseEntity<List<String>> getCourseCodes() {
    return ResponseEntity.ok(courseService.getCourseCodes());
  }

  @GetMapping("/inactivatedcode")
  public ResponseEntity<List<String>> getInactivatedCourseCodes() {
    return ResponseEntity.ok(courseService.getInactivatedCourseCodes());
  }

  @PutMapping("/modifycourse")
  public ResponseEntity<CourseDto> modifyCourse(@RequestParam(name = "coursecode") String code,
                                                @RequestBody CourseDto courseDto) {
    return ResponseEntity.ok(courseService.modifyCourseData(code, courseDto));
  }

  @PutMapping("/inactivatecourse")
  public ResponseEntity<CourseDto> inactivateCourse(@RequestParam(name = "coursecode") String code) {
    return ResponseEntity.ok(courseService.inactivateCourse(code));
  }

  @PutMapping("/reactivatecourse")
  public ResponseEntity<CourseDto> reactivateCourse(@RequestParam(name = "coursecode") String code) {
    return ResponseEntity.ok(courseService.reactivateCourse(code));
  }

  @GetMapping("/findcoursebyexcerpt")
  public ResponseEntity<List<CourseDto>>findAllCourses(@RequestParam(name="course")String nameExcerpt){
    return ResponseEntity.ok(courseService.findAllCoursesByNameExcerpt(nameExcerpt));
  }


}
