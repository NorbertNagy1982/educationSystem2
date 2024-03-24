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

@AllArgsConstructor
@Getter
@Setter
public class UserCourseDto {

  private Integer dtoId;
  private UserDto userDto;
  private CourseDto courseDto;
  private Integer grade;


}
