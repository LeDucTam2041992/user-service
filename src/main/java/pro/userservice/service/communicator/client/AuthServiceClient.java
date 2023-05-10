package pro.userservice.service.communicator.client;

import feign.HeaderMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pro.userservice.dto.base.BaseApiResponse;
import pro.userservice.dto.base.CreateUserDto;
import pro.userservice.feign.InternalFeignConfig;

import java.util.Map;

@FeignClient(url = "${url.auth-service}", name = "auth-client", configuration = InternalFeignConfig.class)
public interface AuthServiceClient {
    @PostMapping
    ResponseEntity<BaseApiResponse<String>> createUser(@RequestBody CreateUserDto dto, @HeaderMap Map<String, Object> headers);
}
