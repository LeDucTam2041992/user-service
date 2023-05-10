package pro.userservice.exception;

import lombok.Data;

@Data
public class Response {
    private int responseCode;
    private int requestCode;
    private ErrorObject errorObject;
}
