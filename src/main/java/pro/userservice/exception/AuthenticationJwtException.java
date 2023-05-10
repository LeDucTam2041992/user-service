package pro.userservice.exception;

import lombok.Data;

@Data
public class AuthenticationJwtException extends BaseException {
    private String principal;

    public AuthenticationJwtException() {
    }

    public AuthenticationJwtException(String errorCode) {
        super(errorCode);
    }

    public AuthenticationJwtException(String errorCode, String desc) {
        super(errorCode, desc);
    }

    public AuthenticationJwtException(String errorCode, Object tag) {
        super(errorCode, tag);
    }
}
