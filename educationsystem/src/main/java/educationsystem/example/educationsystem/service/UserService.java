package educationsystem.example.educationsystem.service;

import educationsystem.example.educationsystem.domain.*;
import educationsystem.example.educationsystem.dto.CourseDto;
import educationsystem.example.educationsystem.dto.UserDto;
import educationsystem.example.educationsystem.mapper.CourseMapper;
import educationsystem.example.educationsystem.mapper.UserMapper;
import educationsystem.example.educationsystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CourseMapper courseMapper;
    private final UserCourseService userCourseService;

public User save(User userParam){
    User user = new User();
    user.setActivated(true);
    user.setUserCourses(new HashSet<>());
    user.setFamilyName(userParam.getFamilyName());
    user.setMiddleName(userParam.getMiddleName());
    user.setFirstname(userParam.getFirstname());
    user.setForumSet(new HashSet<>());
    user.setPassword(userParam.getPassword());
    user.setUsername(userParam.getUsername());
    user.setUserCode(this.userCodeGenerator(userParam.getFamilyName(),userParam.getFirstname()));
    user.setUserType(userParam.getUserType());
    userRepository.save(user);
    return user;
}

public User findById(Integer id){
    return userRepository.findById(id).orElse(null);
}

  public UserDto findByIdDto(Integer id){
    return userRepository.findById(id)
      .map(userMapper::convertUserToDto)
      .orElse(null);
  }



private String userCodeGenerator(String familyName, String firstName){
    StringBuilder sb = new StringBuilder();
    sb.append(familyName, 0, 2);
    sb.append(firstName, 0, 2);
    return sb.toString();
}

public UserDto inactivateUser(Integer userId){
    User user = userRepository.findById(userId).orElse(null);
    if (user != null){
        user.setActivated(false);
        userRepository.save(user);
        return userMapper.convertUserToDto(user);
    }
    return null;
}

public void modifyUserStatus(Integer userId, UserType userType){
    User user = userRepository.findById(userId).orElse(null);
    if (user != null){
        user.setUserType(userType);
        userRepository.save(user);
    }
}

public List<Forum> getAllForumEntry(Integer userId){
    User user = userRepository.findById(userId).orElse(null);
  assert user != null;
  return new ArrayList<>(user.getForumSet());
}

public UserDto identifyUser(Authentication authentication){
    return userRepository.findAll().stream()
    .filter(x-> x.getUsername().equalsIgnoreCase(authentication.getUsername()))
    .filter(x->x.getPassword().equalsIgnoreCase(authentication.getPassword()))
            .map(userMapper::convertUserToDto)
            .findFirst().orElse(null);
}

public List<CourseDto> findAllCourses(Integer userId){
  List<UserCourse>userCourseList = userCourseService.findAll();
  return userCourseList.stream()
    .filter(x->x.getUser().getUserId().equals(userId))
    .map(UserCourse::getCourse)
    .map(courseMapper::convertCourseToDto)
    .collect(Collectors.toList());
}

public List<CourseDto>findAllCoursesOfAUser(Integer userId){
  User user = userRepository.findById(userId).orElse(null);
  return userCourseService.findAvailableCourses(user);
}

public List<UserDto> findAllStudentByNameExcerpt(String nameExcerpt){
  return userRepository.findAll().stream()
    .filter(x->x.getUserType().equals(UserType.STUDENT))
    .filter(x->x.getFamilyName().startsWith(nameExcerpt))
    .map(userMapper::convertUserToDto)
    .collect(Collectors.toList());
}

  public List<UserDto> findAllUsersByNameExcerpt(String nameExcerpt){
    return userRepository.findAll().stream()
      .filter(x->x.getFamilyName().startsWith(nameExcerpt))
      .map(userMapper::convertUserToDto)
      .collect(Collectors.toList());
  }

  public List<UserDto> findAllTeacherByNameExcerpt(String nameExcerpt){
    return userRepository.findAll().stream()
      .filter(x->x.getUserType().equals(UserType.TEACHER) || x.getUserType().equals(UserType.PRINCIPAL_TEACHER))
      .filter(x->x.getFamilyName().startsWith(nameExcerpt))
      .map(userMapper::convertUserToDto)
      .collect(Collectors.toList());
  }

public List<User>findAll(){
  return userRepository.findAll().stream()
    .collect(Collectors.toList());
}

  public UserDto modifyUserData(Integer userId, UserDto userDto){
    User userToModify = userRepository.findAll().stream()
      .filter(x->x.getUserId().equals(userId))
      .findFirst().orElse(null);
    assert userToModify != null;
    if (userDto.getUserType() != null){
      userToModify.setUserType(userDto.getUserType());
    }
    if(userDto.getFamilyName() != null){
      userToModify.setFamilyName(userDto.getFamilyName());
    }
    if(userDto.getMiddleName() != null){
      userToModify.setMiddleName(userDto.getMiddleName());
    }
    if(userDto.getFirstname() != null){
      userToModify.setFirstname(userDto.getFirstname());
    }
    if (userDto.getUsername() != null){
      userToModify.setUsername(userDto.getUsername());
    }
    if (userDto.getPassword() != null){
      userToModify.setPassword(userDto.getPassword());
    }
   userRepository.save(userToModify);
    return userMapper.convertUserToDto(userToModify);
  }




}
