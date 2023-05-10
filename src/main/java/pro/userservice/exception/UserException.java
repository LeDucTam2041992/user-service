package pro.userservice.exception;

import lombok.Data;

@Data
public class UserException extends BaseException {

    private String principal;

    public UserException() {
    }

    public UserException(String errorCode) {
        super(errorCode);
    }

    public UserException(String errorCode, String desc) {
        super(errorCode, desc);
    }

    public UserException(String errorCode, Object tag) {
        super(errorCode, tag);
    }

}
