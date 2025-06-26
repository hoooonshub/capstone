package privatesns.capstone.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import privatesns.capstone.common.entity.BaseEntity;
import privatesns.capstone.core.exception.exception.AccountException;
import privatesns.capstone.core.security.Encoder;

import static privatesns.capstone.core.exception.exception.ExceptionCode.INVALID_PASSWORD;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    private String loginId;

    private String password;

    private String name;

    public User(String loginId, String password, String name) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
    }

    public void validateLoginable(Encoder encoder, String rawPassword) {
        if (!encoder.matches(this.password, rawPassword)) {
            throw new AccountException(INVALID_PASSWORD);
        }
    }
}
