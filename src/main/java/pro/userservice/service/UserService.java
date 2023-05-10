package pro.userservice.service;

import pro.userservice.dto.DetailUserDto;
import pro.userservice.dto.UpsertUserDto;

import java.util.UUID;

public interface UserService {
    UUID createUser(UpsertUserDto dto);

    DetailUserDto getUser(UUID id);
}
