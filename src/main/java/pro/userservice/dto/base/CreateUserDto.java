package pro.userservice.dto.base;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserDto {
    private String username;
    private String password;
}
