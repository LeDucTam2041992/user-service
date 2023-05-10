package pro.userservice.exception;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class ErrorMessageLoader {
    private static Map<String, ErrorMessage> errorMessageMap ;

    @PostConstruct
    public static void loadConfig() {
        log.info("-----load error config message------");
        try {
            Charset charset = StandardCharsets.UTF_8;
            Properties englishMessages = new Properties();
            englishMessages.load(new InputStreamReader(ErrorMessageLoader.class.getResourceAsStream("/message_en.properties"), charset));
            errorMessageMap = englishMessages.entrySet().stream().collect(
                    Collectors.toMap(
                            e -> e.getKey().toString(),
                            e -> {
                                ErrorMessage errorMessage = new ErrorMessage();
                                errorMessage.setEn(e.getValue().toString());
                                return errorMessage;
                            }
                    )
            );
            Properties vietnameseMessages = new Properties();
            vietnameseMessages.load(new InputStreamReader(ErrorMessageLoader.class.getResourceAsStream("/message_vn.properties"), charset));
            errorMessageMap.forEach((key, value) -> value.setVn(vietnameseMessages.getProperty(key)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ErrorMessage getMessage(String errorCode) {
        return errorMessageMap.get(errorCode) != null ? errorMessageMap.get(errorCode) : errorMessageMap.get(ErrorCode.INTERNAL_SEVER_ERROR);
    }
}
