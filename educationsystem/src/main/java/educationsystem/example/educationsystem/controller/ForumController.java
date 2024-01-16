package educationsystem.example.educationsystem.controller;

import educationsystem.example.educationsystem.domain.Forum;
import educationsystem.example.educationsystem.service.ForumService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forum")
@AllArgsConstructor
public class ForumController {

    private final ForumService forumService;

    @GetMapping("/create")
    public ResponseEntity<Forum>createForumEntry(@RequestParam(name="userid") Integer userId,
                                                 @RequestParam(name="courseId")Integer courseId,
                                                 @RequestBody Forum forum){
        return ResponseEntity.ok(forumService.createForumEntry(userId,courseId,forum));
    }


    

}
