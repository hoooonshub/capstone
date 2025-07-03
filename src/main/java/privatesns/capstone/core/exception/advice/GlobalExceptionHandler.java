package privatesns.capstone.core.exception.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import privatesns.capstone.core.exception.exception.BaseException;
import privatesns.capstone.core.exception.exception.FileException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException exception) {
        log.warn("[BaseException] : status={}, message={}",
                exception.getHttpStatus(),
                exception.getMessage());

        return ResponseEntity.status(exception.getHttpStatus()).body(ErrorResponse.from(exception));
    }

    @ExceptionHandler({
            FileException.class,
            Exception.class
    })
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception exception) {
        log.error("[Unexpected Exception] : exception={}, message={}",
                exception.getClass().getName(),
                exception.getMessage());

        return ErrorResponse.internal();
    }
}
