package educationsystem.example.educationsystem.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="course_table")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="courseid")
    private Integer courseId;
    @Column(name="code")
    private String code;
    @Column(name="title")
    private String title;
    @Enumerated(EnumType.STRING)
    @Column(name="coursetype")
    private CourseType courseType;
    @Column(name="registrationstart")
    private LocalDate registrationStart;
    @Column(name="registrationend")
    private LocalDate registrationEnd;
    @Column(name="coursedescription")
    private String courseDescription;
    @OneToMany(mappedBy = "course")
    private Set<User> userSet;
    @OneToMany(mappedBy = "course")
    private Set<Forum> forumSet;
    //private List<UploadMaterials> uploadMaterialsList;
    @Column(name="activated")
    private Boolean activated;
    @Column(name="limitofregisteredstudents")
    private Integer limitOfRegisteredStudents;
    @Column(name="numberofregisteredstudents")
    private Integer numberOfRegisteredStudents;
    @Enumerated(EnumType.STRING)
    @Column(name="examtype")
    private ExamType examType;








}