package educationsystem.example.educationsystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="user_course")
public class UserCourse {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="user_course_id")
  private Integer id;
  @ManyToOne
  @JoinColumn(name = "user_fk")
  @JsonIgnore
  private User user;
  @ManyToOne
  @JoinColumn(name = "course_fk")
  @JsonIgnore
  private Course course;
  @Column(name="grade")
  private Integer grade;
}
