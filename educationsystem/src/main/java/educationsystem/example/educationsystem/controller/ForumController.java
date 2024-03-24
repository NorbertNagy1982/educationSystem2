package educationsystem.example.educationsystem.controller;

import educationsystem.example.educationsystem.domain.Forum;
import educationsystem.example.educationsystem.dto.ForumDto;
import educationsystem.example.educationsystem.service.ForumService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forum")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ForumController {

    private final ForumService forumService;

    @GetMapping("/all")
    public ResponseEntity<List<ForumDto>> findaAll(@RequestParam(name="courseid")Integer courseId) {
        return ResponseEntity.ok(forumService.findAllByCourse(courseId));
    }

  @PostMapping("/create")
  public ResponseEntity<Forum> createForumEntry(@RequestParam(name = "userid") Integer userId,
                                                @RequestBody Forum forum) {
    return ResponseEntity.ok(forumService.save(forum, userId));
  }

  @GetMapping("/allbyuser")
  public ResponseEntity<List<ForumDto>> findAllForumByUser(@RequestParam(name="userid")Integer userId) {
    return ResponseEntity.ok(forumService.findAllByUser(userId));
  }

  @PutMapping("/disable")
  public ResponseEntity<ForumDto>disableForumEntry(@RequestParam(name="forumid")Integer forumId,
                                                   @RequestParam(name="familyname")String familyName){
      return ResponseEntity.ok(forumService.disableForumEntry(forumId,familyName));
  }


}
