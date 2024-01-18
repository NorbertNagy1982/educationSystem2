package educationsystem.example.educationsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import educationsystem.example.educationsystem.domain.Course;
import educationsystem.example.educationsystem.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class ForumDto {

    private Integer dtoId;
    private String message;
    private LocalDate dateOfMessage;
    private UserDto userDto;
    private Boolean enabled;
    private CourseDto courseDto; //felfelé bemutatáshoz
}
