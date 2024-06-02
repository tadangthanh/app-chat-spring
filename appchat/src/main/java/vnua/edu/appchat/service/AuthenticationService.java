package vnua.edu.appchat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import vnua.edu.appchat.dto.AuthenticationResponse;
import vnua.edu.appchat.dto.UserAuth;
import vnua.edu.appchat.repository.UserRepo;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(UserAuth authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException("Incorrect username or password");
        }
        return AuthenticationResponse.builder().token(jwtService.generateToken(userRepo.findByUsername(authenticationRequest.getUsername()).orElseThrow(() -> new RuntimeException("User not found")))).refreshToken("refreshToken").build();
    }
}
