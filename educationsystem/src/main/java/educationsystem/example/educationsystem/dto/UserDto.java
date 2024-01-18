package educationsystem.example.educationsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import educationsystem.example.educationsystem.domain.Course;
import educationsystem.example.educationsystem.domain.Forum;
import educationsystem.example.educationsystem.domain.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class UserDto{

    private Integer dtoId;
    private String userCode;
    private UserType userType;
    private String familyName;
    private String middleName;
    private String firstname;
    private String username;
    private String password;
    private Boolean activated;


}
