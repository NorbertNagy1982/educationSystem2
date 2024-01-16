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
    @ManyToMany
    @JoinTable(
            name = "user_course",
            joinColumns = @JoinColumn(name = "user_fk"),
            inverseJoinColumns = @JoinColumn(name = "course_fk")
    )
    @JsonIgnore
    private Set<Course> courseSet;
    @OneToMany(mappedBy = "user")
    private Set<Forum> forumSet;
}
