package pro.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import pro.userservice.dto.base.PageRequestInput;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class UserSearchDto extends PageRequestInput {
    private String id;

    @Schema(example = "2020-05-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdDate;

    @Schema(example = "2020-05-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime approvedAt;
}
