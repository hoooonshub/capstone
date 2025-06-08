package privatesns.capstone.common.security.encode;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import privatesns.capstone.core.security.Encoder;

@Component
public class DefaultEncoder implements Encoder {
    private final BCryptPasswordEncoder passwordEncoder;

    public DefaultEncoder() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encode(String input) {
        return passwordEncoder.encode(input);
    }

    @Override
    public boolean matches(String encodedPassword, String rawPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
