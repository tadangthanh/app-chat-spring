package vnua.edu.appchat.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.stereotype.Service;
import vnua.edu.appchat.entity.User;

import java.util.Map;
@Service
public class JwtService {
    private static final String SECRET = "titv";
    private static final long EXPIRATION_TIME = 864000000;

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("role", user.getRole().getName())
                .withClaim("id", user.getId())
                .withExpiresAt(new java.util.Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    public String extractUsername(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());
        return JWT.require(algorithm)
                .build()
                .verify(token)
                .getSubject();
    }

    public boolean tokenIsValid(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());
        try {
            JWT.require(algorithm)
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Map<String, Claim> extractAllClaims(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());
        return JWT.require(algorithm)
                .build()
                .verify(token)
                .getClaims();

    }
}
