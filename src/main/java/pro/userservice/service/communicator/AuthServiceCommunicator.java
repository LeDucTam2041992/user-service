package pro.userservice.service.communicator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pro.userservice.dto.base.BaseApiResponse;
import pro.userservice.dto.base.CreateUserDto;
import pro.userservice.service.communicator.client.AuthServiceClient;
import pro.userservice.util.InternalTokenUtils;

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
            ResponseEntity<BaseApiResponse<String>> rs = authServiceClient.createUser(dto, internalTokenUtils.generateInternalJwt(ADMIN));
            return Objects.requireNonNull(rs.getBody()).getData();
        } catch (Exception e) {
            log.error("{} call auth service create user error {}", getClass().getSimpleName(), e);
            return null;
        }
    }
}
