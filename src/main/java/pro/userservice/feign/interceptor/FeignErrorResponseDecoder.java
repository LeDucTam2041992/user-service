package pro.userservice.feign.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import pro.userservice.exception.ErrorCode;
import pro.userservice.exception.UserException;

import java.io.InputStream;
@Slf4j
public class FeignErrorResponseDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        Object message = null;
        try (InputStream bodyIs = response.body()
                .asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, UserException.class);
            log.info("{} error message {}", getClass().getSimpleName(), message);
        } catch (Exception e) {
            log.error("{} feign client error {}", getClass().getSimpleName(), e);
            return new Exception(e.getMessage());
        }
        return new UserException(ErrorCode.INTERNAL_SEVER_ERROR);
    }
}
