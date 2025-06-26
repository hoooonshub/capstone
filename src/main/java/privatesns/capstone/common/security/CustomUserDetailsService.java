package privatesns.capstone.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import privatesns.capstone.core.exception.exception.AccountException;
import privatesns.capstone.core.exception.exception.ExceptionCode;
import privatesns.capstone.domain.user.User;
import privatesns.capstone.domain.user.UserRepository;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        return userRepository.findByLoginId(loginId)
                .map(this::toDetails)
                .orElseThrow(() -> new AccountException(ExceptionCode.USER_NOT_FOUND));
    }

    private UserDetails toDetails(User user) {
        return new org.springframework.security.core.userdetails.User(
                String.valueOf(user.getId()),
                "",
                Collections.emptyList()
        );
    }
}
