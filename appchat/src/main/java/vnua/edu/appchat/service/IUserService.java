package vnua.edu.appchat.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import vnua.edu.appchat.dto.UserAuth;
import vnua.edu.appchat.dto.UserDTO;

import java.util.List;

public interface IUserService extends UserDetailsService {
    UserDTO save(UserDTO user);
    List<UserDTO> getAllIgNore(String username);
    UserDTO authenticate(UserAuth user);
    UserDTO findById(Long id);
    UserDTO findByUsername(String username);
}
