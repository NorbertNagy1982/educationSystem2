package educationsystem.example.educationsystem.controller;

import educationsystem.example.educationsystem.domain.Course;
import educationsystem.example.educationsystem.domain.Forum;
import educationsystem.example.educationsystem.dto.CourseDto;
import educationsystem.example.educationsystem.dto.UserDto;
import educationsystem.example.educationsystem.service.CourseService;
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

    //student
    @PostMapping("/forum")
    public ResponseEntity<Forum> createForumEntry(@RequestParam(name = "userid") Integer userId,
                                                  @RequestParam(name = "courseid") Integer courseId,
                                                  @RequestBody Forum forum) {
        return ResponseEntity.ok(courseService.createForumEntry(userId, courseId, forum));
    }

    @GetMapping("/register")
    public ResponseEntity<CourseDto> registerToCourse(@RequestParam(name = "userid") Integer userId,
                                                      @RequestParam(name = "courseid") Integer courseId) {
        return ResponseEntity.ok(courseService.registerToCourse(userId, courseId));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> findAllUserOfCourse(@RequestParam(name = "courseid") Integer courseId) {
        return ResponseEntity.ok(courseService.findAllRegisteredUser(courseId));
    }

  @GetMapping("/id")
    public ResponseEntity<CourseDto>findById(@RequestParam(name="courseid")Integer id){
      return ResponseEntity.ok(courseService.findById(id));
    }

}
