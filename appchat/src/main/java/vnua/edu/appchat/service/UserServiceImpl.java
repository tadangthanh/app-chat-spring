package vnua.edu.appchat.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vnua.edu.appchat.dto.UserAuth;
import vnua.edu.appchat.dto.UserDTO;
import vnua.edu.appchat.entity.Role;
import vnua.edu.appchat.entity.User;
import vnua.edu.appchat.map.UserMapping;
import vnua.edu.appchat.repository.RoleRepo;
import vnua.edu.appchat.repository.UserRepo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements IUserService {
    private final UserMapping userMapping;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Override
    public UserDTO save(UserDTO user) {
        User userEntity = userMapping.toEntity(user);
        Role role = roleRepo.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found"));
        userEntity.setRole(role);
        userEntity = userRepo.saveAndFlush(userEntity);
        return userMapping.toDto(userEntity);
    }

    @Override
    public List<UserDTO> getAllIgNore(String username) {
        List<User> users = userRepo.getAllByUsernameNot(username);
        return users.stream().map(userMapping::toDto).toList();
    }

    @Override
    public UserDTO authenticate(UserAuth user) {
        User userEntity = userRepo.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (userEntity != null) {
            return userMapping.toDto(userEntity);
        }
        return null;
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapping.toDto(user);
    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapping.toDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        Optional<Role> role = roleRepo.findByName(user.getRole().getName());
        if (role.isEmpty()) {
            return Collections.emptyList();
        }
        return Collections.singleton(new SimpleGrantedAuthority(role.get().getName()));
    }
}
