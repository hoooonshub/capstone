package privatesns.capstone.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import privatesns.capstone.core.exception.exception.AccountException;
import privatesns.capstone.core.security.Encoder;
import privatesns.capstone.domain.user.User;
import privatesns.capstone.domain.user.UserRepository;
import privatesns.capstone.domain.user.dto.UserResponse;

import static privatesns.capstone.core.exception.exception.ExceptionCode.DUPLICATED_ID;
import static privatesns.capstone.core.exception.exception.ExceptionCode.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final Encoder encoder;

    public void join(String loginId, String password, String name) {
        if (isDuplicatedId(loginId)) {
            throw new AccountException(DUPLICATED_ID);
        }

        userRepository.save(new User(loginId, encoder.encode(password), name));
    }

    private boolean isDuplicatedId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    @Transactional(readOnly = true)
    User findUser(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new AccountException(USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new AccountException(USER_NOT_FOUND));
    }

    public String findUsernameById(Long userId) {
        return findUser(userId).getName();
    }

    public UserResponse.SearchResults searchByLoginId(String loginId) {
        return new UserResponse.SearchResults(
                userRepository.findByLoginIdContainingIgnoreCase(loginId).stream()
                        .map(user -> new UserResponse.SearchResult(
                                        user.getId(),
                                        user.getLoginId()
                                )
                        )
                        .toList()
        );
    }
}
