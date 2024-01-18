package educationsystem.example.educationsystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="forum_table")
@Getter
@Setter
public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="forumid")
    private Integer forumId;
    @Column(name="message")
    private String message;
    @Column(name="dateofmessage")
    private LocalDate dateOfMessage;
    @ManyToOne
    @JoinColumn(name="user_fk")
    @JsonIgnore
    private User user;
    @Column(name="enabled")
    private Boolean enabled;
    @ManyToOne
    @JoinColumn(name="course_fk")
    @JsonIgnore
    private Course course;
}
