package pro.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pro.userservice.dto.SendEmailRequestBody;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl {

    private final StreamBridge streamBridge;

    @Async("asyncExecutor")
    public void sendMessage(SendEmailRequestBody body) {
        Message<SendEmailRequestBody> msg = MessageBuilder.withPayload(body)
                .setHeader(KafkaHeaders.KEY, body.getCif().getBytes())
                .build();
        streamBridge.send("msmChannel-out-0", msg);
    }
}
