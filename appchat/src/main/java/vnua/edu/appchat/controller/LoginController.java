package vnua.edu.appchat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vnua.edu.appchat.dto.AuthenticationResponse;
import vnua.edu.appchat.dto.UserAuth;
import vnua.edu.appchat.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login")
public class LoginController {
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody UserAuth userAuth) {
        AuthenticationResponse user = authenticationService.authenticate(userAuth);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().build();
    }
}
