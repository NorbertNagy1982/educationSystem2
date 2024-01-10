package educationsystem.example.educationsystem.domain;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userid")
    private Integer userId;
    @Column(name="usercode")
    private String userCode;
    @Column(name="usertype")
    private UserType userType;
    @Column(name="familyname")
    private String familyName;
    @Column(name="middlename")
    private String middleName;
    @Column(name="firstname")
    private String firstname;
    @Column(name="username")
    private String username;
    @Column(name="userpassword")
    private String password;
    @Column(name="activated")
    private Boolean activated;
    @ManyToOne
    @JoinColumn(name="course_fk")
    private Course course;
    @OneToMany(mappedBy = "user")
    private Set<Forum> forumSet;

}
