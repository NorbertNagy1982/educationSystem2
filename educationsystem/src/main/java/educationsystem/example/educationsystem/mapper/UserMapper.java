package educationsystem.example.educationsystem.mapper;

import educationsystem.example.educationsystem.domain.User;
import educationsystem.example.educationsystem.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mappings({
            @Mapping(target = "dtoId", source = "userId")
    })
    UserDto convertUserToDto(User user);


@Mappings({
        @Mapping(target ="userId", ignore = true),
        @Mapping(target = "userCode", ignore = true),
        @Mapping(target = "activated", ignore = true)
})
    User convertDtoToUser(UserDto userDto);

}
