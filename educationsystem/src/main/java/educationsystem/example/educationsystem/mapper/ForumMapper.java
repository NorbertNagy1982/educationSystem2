package educationsystem.example.educationsystem.mapper;

import educationsystem.example.educationsystem.domain.Forum;
import educationsystem.example.educationsystem.dto.ForumDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {CourseMapper.class, UserMapper.class})
public interface ForumMapper {

    @Mappings({
            @Mapping(target = "dtoId", source = "forumId"),
            @Mapping(target = "userDto", source = "user"),
            @Mapping(target = "courseDto", source ="course"),
    })
    ForumDto convertForumToDto(Forum forum);



    @Mappings({
            @Mapping(target = "forumId", ignore = true),
            @Mapping(target = "enabled", ignore = true)
    })
    Forum convertDtoToForum(ForumDto forumDto);

}
