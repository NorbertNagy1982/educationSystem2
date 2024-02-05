package educationsystem.example.educationsystem.controller;

import educationsystem.example.educationsystem.domain.User;
import educationsystem.example.educationsystem.domain.UserType;
import educationsystem.example.educationsystem.dto.UserDto;
import educationsystem.example.educationsystem.service.Authentication;
import educationsystem.example.educationsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;


    @PostMapping("/save")
    public ResponseEntity<User> save(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto>login(@RequestBody Authentication authentication){
        return ResponseEntity.ok(userService.identifyUser(authentication));
    }

}
