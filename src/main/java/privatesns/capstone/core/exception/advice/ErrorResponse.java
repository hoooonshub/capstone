package privatesns.capstone.core.exception.advice;

import privatesns.capstone.core.exception.exception.BaseException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public record ErrorResponse(String message, Integer status) {

    static ErrorResponse from(BaseException baseException) {
        return new ErrorResponse(baseException.getMessage(), baseException.getHttpStatus());
    }

    static ErrorResponse internal() {
        return new ErrorResponse(INTERNAL_SERVER_ERROR.name(), INTERNAL_SERVER_ERROR.value());
    }
}
