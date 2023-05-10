package pro.userservice.exception;

public interface ErrorCode {
    String INTERNAL_SEVER_ERROR = "STO-10-001";
    String TOKEN_INVALID = "AUT-10-002";

    String USER_NOT_FOUND = "US_20-001";
    String TOKEN_EXPIRED = "AUTH-20-003";
}
