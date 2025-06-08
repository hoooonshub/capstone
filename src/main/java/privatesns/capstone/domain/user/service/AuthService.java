package privatesns.capstone.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import privatesns.capstone.core.exception.exception.AccountException;
import privatesns.capstone.core.security.Encoder;
import privatesns.capstone.core.security.TokenGenerator;
import privatesns.capstone.core.security.model.Tokens;
import privatesns.capstone.domain.user.User;
import privatesns.capstone.domain.user.UserRepository;

import static privatesns.capstone.core.exception.exception.ExceptionCode.DUPLICATED_ID;
import static privatesns.capstone.core.exception.exception.ExceptionCode.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    private final Encoder encoder;
    private final TokenGenerator tokenGenerator;

    public void join(String loginId, String password, String name) {
        if (isDuplicatedId(loginId)) {
            throw new AccountException(DUPLICATED_ID);
        }

        userRepository.save(new User(loginId, encoder.encode(password), name));
    }

    private boolean isDuplicatedId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    public Tokens login(String loginId, String password) {
        var user = findUser(loginId);

        user.validateLoginable(encoder, password);

        return tokenGenerator.login(user.getId(), loginId);
    }

    private User findUser(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new AccountException(USER_NOT_FOUND));

    }
}
