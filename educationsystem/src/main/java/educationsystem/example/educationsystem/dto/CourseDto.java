package educationsystem.example.educationsystem.dto;

import educationsystem.example.educationsystem.domain.CourseType;
import educationsystem.example.educationsystem.domain.ExamType;
import educationsystem.example.educationsystem.domain.Forum;
import educationsystem.example.educationsystem.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class CourseDto {

    private Integer dtoId;
    private String code;
    private String title;
    private CourseType courseType;
    private LocalDate registrationStart;
    private LocalDate registrationEnd;
    private String courseDescription;
    private Boolean activated;
    private Integer limitOfRegisteredStudents;
    private Integer numberOfRegisteredStudents;
    private ExamType examType;
    private Set<User>userSet;
    private Integer grade;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CourseDto other = (CourseDto) o;
    return Objects.equals(title, other.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title);
  }

    //a gyűjteményeket (set, list) nem kell belerakni a dto-ba, mert amikor létrejön egy objektum, üres
    //listával inicializálódik. Ha például egy kurzshoz tartozó usereket, vagy forumbejegyzéseket
    //akarjuk megnézni, akkor egy külön metódust alkalmazunk.
}
