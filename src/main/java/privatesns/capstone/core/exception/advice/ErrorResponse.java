package privatesns.capstone.core.exception.advice;

import privatesns.capstone.core.exception.exception.BaseException;

public record ErrorResponse(String message, Integer status) {

    static ErrorResponse from(BaseException baseException) {
        return new ErrorResponse(baseException.getMessage(), baseException.getHttpStatus());
    }
}
