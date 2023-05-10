package pro.userservice.dto.base;

import java.util.Collections;

public class ApiResponseFactory {
    public static final String SUCCESS_MESS = "SUCCESS";
    public static final String SUCCESS_CODE = "200";
    public static final String FAIL_MESS = "FAIL";
    public static final String FAIL_CODE = "400";

    public static <T> BaseApiResponse<T> success(T resultObject) {
        return gettBaseApiResponse(resultObject, SUCCESS_MESS, SUCCESS_CODE);
    }

    private static <T> BaseApiResponse<T> gettBaseApiResponse(T resultObject, String successMess, String successCode) {
        BaseApiResponse<T> result = new BaseApiResponse<>();
        ApiMessage message = new ApiMessage();
        message.setMessage(successMess);
        message.setCode(successCode);
        result.setData(resultObject);
        result.setMessages(Collections.singletonList(message));

        return result;
    }

    public static <T> BaseApiResponse<T> fail(T resultObject) {
        return gettBaseApiResponse(resultObject, FAIL_MESS, FAIL_CODE);
    }
}
