package educationsystem.example.educationsystem.mapper;

import educationsystem.example.educationsystem.domain.Forum;
import educationsystem.example.educationsystem.domain.UserCourse;
import educationsystem.example.educationsystem.dto.ForumDto;
import educationsystem.example.educationsystem.dto.UserCourseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring" , uses = {CourseMapper.class, UserMapper.class})
public interface UserCourseMapper {

  @Mappings({
    @Mapping(target = "dtoId", source = "id"),
    @Mapping(target = "userDto", source = "user"),
    @Mapping(target = "courseDto", source ="course"),
  })
  UserCourseDto convertUserCourseToDto(UserCourse userCourse);



  @Mappings({
    @Mapping(target = "id", ignore = true),

  })
  UserCourse convertDtoToUserCourse(UserCourseDto userCourseDto);



}
