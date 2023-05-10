package pro.userservice.exception;

public class ErrorHelper {
    public static Response buildResponse(String errorCode) {
        return buildResponse(errorCode, null);
    }

    private static Response buildResponse(String errorCode, String errorDesc) {
        Response response = new Response();
        response.setErrorObject(buildErrorObject(errorCode, errorDesc));
        return response;
    }

    private static ErrorObject buildErrorObject(String errorCode, String errorDesc) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setErrorCode(errorCode);
        errorObject.setErrorDesc(errorDesc);
        errorObject.setErrorMessage(ErrorMessageLoader.getMessage(errorCode));
        return errorObject;
    }

    public static Response buildInternalServerError() {
        return buildResponse(ErrorCode.INTERNAL_SEVER_ERROR);
    }
}
