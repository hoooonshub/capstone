package privatesns.capstone.core.exception.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    DUPLICATED_ID("중복된 아이디입니다", CONFLICT),
    ;

    private final String message;
    private final HttpStatus status;

    public int getStatus() {
        return status.value();
    }
}
