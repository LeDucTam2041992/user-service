package pro.userservice.exception;

import lombok.Data;

@Data
public class BaseException extends RuntimeException {
    private Response response;
    private Object tagObject;

    public BaseException() {
        super();
        setResponse(new Response());
    }

    public BaseException(String errorCode) {
        setResponse(ErrorHelper.buildResponse(errorCode));
    }

    public BaseException(String errorCode, Object tag) {
        this(errorCode);
        this.setTagObject(tag);
    }

}
