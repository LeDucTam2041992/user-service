package pro.userservice.service.communicator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pro.userservice.dto.base.BaseApiResponse;
import pro.userservice.service.communicator.client.StorageServiceClient;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class StorageServiceCommunicator {
    private final StorageServiceClient storageServiceClient;

    public String generateUrl(String avatar) {
        try {
            ResponseEntity<BaseApiResponse<String>> rs = storageServiceClient.generateUrl(avatar);
            return Objects.requireNonNull(rs.getBody()).getData();
        } catch (Exception e) {
            log.error("{} call storage service generate url error {}", getClass().getSimpleName(), e);
            return null;
        }
    }
}
