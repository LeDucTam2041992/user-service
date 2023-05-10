package pro.userservice.service.communicator.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import pro.userservice.dto.base.BaseApiResponse;
import pro.userservice.dto.base.CreateUserDto;
import pro.userservice.feign.InternalFeignConfig;

@FeignClient(url = "${url.auth-service}", name = "auth-client", configuration = InternalFeignConfig.class)
public interface AuthServiceClient {
    @PostMapping
    ResponseEntity<BaseApiResponse<String>> createUser(@RequestBody CreateUserDto dto, @RequestHeader("internal-token") String token);
}
