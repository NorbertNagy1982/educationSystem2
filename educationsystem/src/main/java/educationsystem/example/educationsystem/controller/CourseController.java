package educationsystem.example.educationsystem.controller;

import educationsystem.example.educationsystem.domain.Course;
import educationsystem.example.educationsystem.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/course")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<Course> createCourse(@RequestParam(name="id") Integer id,
                                     @RequestBody Course course) {
        return ResponseEntity.ok(courseService.createCourse(id,course));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Course>>findAll(){
        return ResponseEntity.ok(courseService.findAll());
    }

}
