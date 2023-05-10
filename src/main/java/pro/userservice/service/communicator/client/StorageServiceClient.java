package pro.userservice.service.communicator.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pro.userservice.dto.base.BaseApiResponse;
import pro.userservice.feign.InternalFeignConfig;

@FeignClient(url = "${url.storage-service}", name = "storage-client", configuration = InternalFeignConfig.class)
public interface StorageServiceClient {

    @GetMapping("/getPath")
    ResponseEntity<BaseApiResponse<String>> generateUrl(@RequestParam String path);
}
