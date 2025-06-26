package privatesns.capstone.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import privatesns.capstone.core.security.Encoder;
import privatesns.capstone.core.security.TokenGenerator;
import privatesns.capstone.core.security.model.Tokens;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;

    private final Encoder encoder;
    private final TokenGenerator tokenGenerator;

    public Tokens login(String loginId, String password) {
        var user = userService.findUser(loginId);

        user.validateLoginable(encoder, password);

        return tokenGenerator.login(user.getId(), loginId);
    }


}
