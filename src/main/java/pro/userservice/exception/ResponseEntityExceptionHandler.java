package pro.userservice.exception;

import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RestController
@Order(value = PriorityOrdered.HIGHEST_PRECEDENCE)
public class ResponseEntityExceptionHandler extends org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {Throwable.class})
    protected ResponseEntity<Response> handleException(Throwable ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        if (ex instanceof BaseException) {
            BaseException exception = (BaseException) ex;
            return new ResponseEntity<>(exception.getResponse(), headers, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ErrorHelper.buildInternalServerError(), headers, HttpStatus.BAD_REQUEST);
    }
}
