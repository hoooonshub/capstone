package privatesns.capstone.core.security;

import privatesns.capstone.core.security.model.Tokens;

public interface TokenGenerator {

    Tokens login(Long id, String loginId);
}
