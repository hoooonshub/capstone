package privatesns.capstone.core.exception.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    DUPLICATED_ID("중복된 아이디입니다", CONFLICT),
    USER_NOT_FOUND("찾을 수 없는 유저입니다.", NOT_FOUND),
    INVALID_PASSWORD("잘못된 비밀번호입니다.", BAD_REQUEST),
    INVALID_JWT("잘못된 JWT 토큰입니다.", BAD_REQUEST),
    ;

    private final String message;
    private final HttpStatus status;

    public int getStatus() {
        return status.value();
    }
}
