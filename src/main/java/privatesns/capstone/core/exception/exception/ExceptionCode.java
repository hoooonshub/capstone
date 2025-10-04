package privatesns.capstone.core.exception.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    // User
    DUPLICATED_ID("중복된 아이디입니다", CONFLICT),
    USER_NOT_FOUND("찾을 수 없는 유저입니다.", NOT_FOUND),
    INVALID_PASSWORD("잘못된 비밀번호입니다.", BAD_REQUEST),
    INVALID_JWT("잘못된 JWT 토큰입니다.", BAD_REQUEST),

    // Post
    INVALID_IMAGE_FILE("유효하지 않은 이미지입니다.", BAD_REQUEST),
    NOT_ALLOWED_EXTENSION("지원하지 않는 이미지 확장자입니다.", BAD_REQUEST),
    FILE_DIRECTORY_CREATED_FAILED("파일 루트 디렉터리 생성 실패", INTERNAL_SERVER_ERROR),
    FILE_UPLOAD_FAILED("이미지 저장에 실패했습니다", INTERNAL_SERVER_ERROR),

    // Group,
    GROUP_NOT_FOUND("찾을 수 없는 그룹 정보입니다", NOT_FOUND),
    IS_NOT_GROUP_MEMBER("그룹에 속한 멤버가 아닙니다", BAD_REQUEST),

    ;

    private final String message;
    private final HttpStatus status;

    public int getStatus() {
        return status.value();
    }
}
