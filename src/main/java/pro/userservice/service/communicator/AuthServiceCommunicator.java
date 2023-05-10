package pro.userservice.service.communicator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pro.userservice.dto.base.BaseApiResponse;
import pro.userservice.dto.base.CreateUserDto;
import pro.userservice.service.communicator.client.AuthServiceClient;
import pro.userservice.util.InternalTokenUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthServiceCommunicator {
    private final AuthServiceClient authServiceClient;
    private final InternalTokenUtils internalTokenUtils;
    private final String ADMIN= "admin";

    public String createUser(CreateUserDto dto) {
        try {
            Map<String,Object> headerMap = new HashMap<>();
            headerMap.put("internal-token", internalTokenUtils.generateInternalJwt(ADMIN));
            ResponseEntity<BaseApiResponse<String>> rs = authServiceClient.createUser(dto, headerMap);
            return Objects.requireNonNull(rs.getBody()).getData();
        } catch (Exception e) {
            log.error("{} call auth service create user error {}", getClass().getSimpleName(), e);
            return null;
        }
    }
}
