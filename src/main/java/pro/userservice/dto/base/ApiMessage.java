package pro.userservice.dto.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiMessage {

    @Schema(example = "SUCCESS")
    private String code;
    @Schema(example = "SUCCESS")
    private String message;
}
