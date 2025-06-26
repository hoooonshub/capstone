package privatesns.capstone.common.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtSecretProvider {
    private final Key key;
    private final long accessTokenExpireTime;

    public JwtSecretProvider(
            @Value("${jwt.key}") String key,
            @Value("${jwt.access-duration}") long accessTokenDuration
    ) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
        this.accessTokenExpireTime = accessTokenDuration;
    }

    public Key key() {
        return key;
    }

    public Date accessTokenExpired() {
        return new Date(new Date().getTime() + accessTokenExpireTime * 1000);
    }
}
