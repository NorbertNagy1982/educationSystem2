package educationsystem.example.educationsystem.service;

import educationsystem.example.educationsystem.domain.Forum;
import educationsystem.example.educationsystem.domain.User;
import educationsystem.example.educationsystem.domain.UserType;
import educationsystem.example.educationsystem.dto.UserDto;
import educationsystem.example.educationsystem.mapper.UserMapper;
import educationsystem.example.educationsystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

public User save(User userParam){
    User user = new User();
    user.setActivated(true);
    user.setCourseSet(new HashSet<>());
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

private String userCodeGenerator(String familyName, String firstName){
    StringBuilder sb = new StringBuilder();
    sb.append(familyName, 0, 2);
    sb.append(firstName, 0, 2);
    return sb.toString();
}

public void inactivateUser(Integer userId){
    User user = userRepository.findById(userId).orElse(null);
    if (user != null){
        user.setActivated(false);
        userRepository.save(user);
    }
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
    return user.getForumSet().stream()
            .collect(Collectors.toList());
}

public UserDto identifyUser(Authentication authentication){
    return userRepository.findAll().stream()
    .filter(x-> x.getUsername().equalsIgnoreCase(authentication.getUsername()))
    .filter(x->x.getPassword().equalsIgnoreCase(authentication.getPassword()))
            .map(userMapper::convertUserToDto)
            .findFirst().orElse(null);
}


}
