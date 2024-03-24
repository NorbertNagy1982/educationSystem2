package educationsystem.example.educationsystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Entity
@Table(name = "user_table")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userid")
    private Integer userId;
    @Column(name="usercode")
    private String userCode;
    @Column(name="usertype")
    @Enumerated(EnumType.STRING)
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
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<UserCourse> userCourses;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Forum> forumSet;
}
