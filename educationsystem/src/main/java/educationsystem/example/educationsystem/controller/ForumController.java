package educationsystem.example.educationsystem.controller;

import educationsystem.example.educationsystem.domain.Forum;
import educationsystem.example.educationsystem.service.ForumService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forum")
@AllArgsConstructor
public class ForumController {

    private final ForumService forumService;

    @GetMapping("/all")
    public ResponseEntity<List<Forum>> findaAll(@RequestParam(name="courseid")Integer courseId) {
        return ResponseEntity.ok(forumService.findAll(courseId));
    }


}
