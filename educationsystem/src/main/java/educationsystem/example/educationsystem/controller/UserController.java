package educationsystem.example.educationsystem.controller;

import educationsystem.example.educationsystem.domain.User;
import educationsystem.example.educationsystem.domain.UserCourse;
import educationsystem.example.educationsystem.dto.CourseDto;
import educationsystem.example.educationsystem.dto.UserCourseDto;
import educationsystem.example.educationsystem.dto.UserDto;
import educationsystem.example.educationsystem.service.Authentication;
import educationsystem.example.educationsystem.service.UserCourseService;
import educationsystem.example.educationsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

  private final UserService userService;
  private final UserCourseService userCourseService;


  @PostMapping("/save")
  public ResponseEntity<User> save(@RequestBody User user) {
    return ResponseEntity.ok(userService.save(user));
  }

  @PostMapping("/login")
  public ResponseEntity<UserDto> login(@RequestBody Authentication authentication) {
    return ResponseEntity.ok(userService.identifyUser(authentication));
  }

  @GetMapping("/allcourse")
  public ResponseEntity<List<CourseDto>> findAllCourses(@RequestParam(name = "userid") Integer userId) {
    return ResponseEntity.ok(userService.findAllCourses(userId));
  }

  @PutMapping("/grade")
  public ResponseEntity<UserCourseDto> setGrade(@RequestParam(name = "courseid") Integer courseId,
                                                @RequestParam(name = "userid") Integer userId,
                                                @RequestParam(name = "grade") Integer grade) {
    return ResponseEntity.ok(userCourseService.setGrade(courseId, userId, grade));
  }

  @GetMapping("/id")
  public ResponseEntity<UserDto> findById(@RequestParam(name = "userid") Integer userId) {
    return ResponseEntity.ok(userService.findByIdDto(userId));
  }

  @GetMapping("/getgrade")
  public ResponseEntity<Integer> getGrade(@RequestParam(name = "courseid") Integer courseId,
                                          @RequestParam(name = "userid") Integer userId) {
    return ResponseEntity.ok(userCourseService.getGrade(courseId, userId));
  }

  @GetMapping("/allusercourse")
  public ResponseEntity<List<UserCourse>> findAllUserCourse() {
    return ResponseEntity.ok(userCourseService.findAll());
  }

  @GetMapping("/allstudentusercourse")
  public ResponseEntity<List<UserCourseDto>> findAllStudentUserCourses(@RequestParam(name = "courseid") Integer courseId) {
    return ResponseEntity.ok(userCourseService.findAllStudent(courseId));
  }

  @GetMapping("/allcourseofuser")
public ResponseEntity<List<CourseDto>>findAllCoursesOfAUser(@RequestParam(name="userid")Integer userId){
    return ResponseEntity.ok(userService.findAllCoursesOfAUser(userId));
}

  @GetMapping("/finduser")
  public ResponseEntity<List<UserDto>>findAllStudents(@RequestParam(name="name")String nameExcerpt){
    return ResponseEntity.ok(userService.findAllStudentByNameExcerpt(nameExcerpt));
  }

  @GetMapping("/finduserbyexcerpt")
  public ResponseEntity<List<UserDto>>findAllUsers(@RequestParam(name="name")String nameExcerpt){
    return ResponseEntity.ok(userService.findAllUsersByNameExcerpt(nameExcerpt));
  }

  @GetMapping("/findteacher")
  public ResponseEntity<List<UserDto>>findAllTeachers(@RequestParam(name="name")String nameExcerpt){
    return ResponseEntity.ok(userService.findAllTeacherByNameExcerpt(nameExcerpt));
  }

  @GetMapping("/allcoursesofstudent")
  public ResponseEntity<List<CourseDto>> findAllCoursesOfStudent(@RequestParam(name = "userid") Integer userId) {
    return ResponseEntity.ok(userCourseService.findAllCoursesById(userId));
  }

  @GetMapping("/allusercourseofuser")
  public ResponseEntity<List<UserCourseDto>> findAllUserCoursesOfUser(@RequestParam(name = "userid") Integer userId) {
    return ResponseEntity.ok(userCourseService.findAllById(userId));
  }

  @PutMapping("/modifyuser")
  public ResponseEntity<UserDto>modifyUserData(@RequestParam(name="userid")Integer userId,
                                               @RequestBody UserDto userDto){
    return ResponseEntity.ok(userService.modifyUserData(userId,userDto));
  }

  @PutMapping("/inactivateuser")
  public ResponseEntity<UserDto>inactivateUser(@RequestParam(name="userid")Integer userId){
    return ResponseEntity.ok(userService.inactivateUser(userId));
  }



}
