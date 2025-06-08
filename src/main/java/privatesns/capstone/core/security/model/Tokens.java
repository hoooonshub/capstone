package privatesns.capstone.core.security.model;

import lombok.Getter;

@Getter
public final class Tokens {
    private final String accessToken;

    public Tokens(String accessToken) {
        this.accessToken = accessToken;
    }
}
