package privatesns.capstone.common.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import privatesns.capstone.core.exception.exception.AccountException;
import privatesns.capstone.core.exception.exception.ExceptionCode;

import javax.crypto.SecretKey;

@Component
public class JwtParser {
    private final UserDetailsService userDetailsService;
    private final io.jsonwebtoken.JwtParser parser;

    public JwtParser(UserDetailsService userDetailsService, JwtSecretProvider secretProvider) {
        this.userDetailsService = userDetailsService;
        this.parser = Jwts.parser()
                .verifyWith((SecretKey)secretProvider.key())
                .build();
    }

    public Authentication parseAuthentication(String token) {
        var loginId = parseLoginId(token);
        var userDetails = userDetailsService.loadUserByUsername(loginId);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private String parseLoginId(String token) {
        return validate(token).getPayload()
                .get("loginId", String.class);
    }

    private Jws<Claims> validate(String token) {
        try {
            return parser.parseSignedClaims(token);
        } catch (JwtException | IllegalArgumentException exception) {
            throw new AccountException(ExceptionCode.INVALID_JWT);
        }
    }
}
