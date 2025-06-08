package privatesns.capstone.common.security.jwt;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import privatesns.capstone.core.security.TokenGenerator;
import privatesns.capstone.core.security.model.Tokens;

@Component
@RequiredArgsConstructor
public class JwtGenerator implements TokenGenerator {
    private final JwtSecretProvider jwtSecretProvider;

    @Override
    public Tokens login(Long userId, String loginId) {
        return new Tokens(generateAccessToken(userId, loginId));
    }

    private String generateAccessToken(Long userId, String loginId) {
        return Jwts.builder()
                .signWith(jwtSecretProvider.key())
                .header().add("type", "JWT").and()
                .claim("userId", userId)
                .claim("loginId", loginId)
                .expiration(jwtSecretProvider.accessTokenExpired())
                .compact();
    }
}
