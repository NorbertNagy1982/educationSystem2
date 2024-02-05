package educationsystem.example.educationsystem.mapper;

import educationsystem.example.educationsystem.domain.Course;
import educationsystem.example.educationsystem.dto.CourseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CourseMapper {


    @Mappings({
            @Mapping(target = "dtoId", source = "courseId"),

    })
    CourseDto convertCourseToDto(Course course);






    @Mappings({ //itt azokat a mezőket kell ignorálni, amik nem kivülről lesznek beállítva
            @Mapping(target = "courseId", ignore = true),
            @Mapping(target = "code", ignore = true),
            @Mapping(target = "numberOfRegisteredStudents", ignore = true),
            @Mapping(target = "activated", ignore = true),
            @Mapping(target = "userSet", ignore = true)
    })
    Course convertDtoToCourse(CourseDto courseDto);


}
