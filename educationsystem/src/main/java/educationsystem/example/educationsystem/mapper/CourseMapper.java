package educationsystem.example.educationsystem.mapper;

import educationsystem.example.educationsystem.domain.Course;
import educationsystem.example.educationsystem.domain.User;
import educationsystem.example.educationsystem.domain.UserCourse;
import educationsystem.example.educationsystem.dto.CourseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CourseMapper {


    @Mappings({
            @Mapping(target = "dtoId", source = "courseId"),
      @Mapping(target = "userSet", source = "userCourses", qualifiedByName = "getUsers")

    })
    CourseDto convertCourseToDto(Course course);






    @Mappings({ //itt azokat a mezőket kell ignorálni, amik nem kivülről lesznek beállítva
            @Mapping(target = "courseId", ignore = true),
            @Mapping(target = "code", ignore = true),
            @Mapping(target = "numberOfRegisteredStudents", ignore = true),
            @Mapping(target = "activated", ignore = true),

    })
    Course convertDtoToCourse(CourseDto courseDto);


  @Named("getUsers")
  default Set<User> getUsers(Set<UserCourse> userCourses)
  {
   return userCourses.stream().map(UserCourse::getUser).collect(Collectors.toSet());
  }

}
