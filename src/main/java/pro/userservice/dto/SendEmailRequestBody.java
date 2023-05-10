package pro.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendEmailRequestBody {
    private String cif;
    private String type;
    private String description;
    private String subject;
    private String mailName;
    private Map<String, String> params;

}
