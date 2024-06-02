package vnua.edu.appchat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vnua.edu.appchat.dto.UserDTO;
import vnua.edu.appchat.service.IUserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final IUserService userService;

    @GetMapping("/ignore/{username}")
    public List<UserDTO> hello(@PathVariable String username) {
        return this.userService.getAllIgNore(username);
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable Long id) {
        return this.userService.findById(id);
    }

    @GetMapping("/username/{username}")
    public UserDTO findByUsername(@PathVariable String username) {
        return this.userService.findByUsername(username);
    }
}
