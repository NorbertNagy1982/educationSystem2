package educationsystem.example.educationsystem.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="forum_table")
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
    private User user;
    @Column(name="enabled")
    private Boolean enabled;
    @ManyToOne
    @JoinColumn(name="courseid")
    private Course course;
}
