package pro.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.userservice.dto.DetailUserDto;
import pro.userservice.dto.UpsertUserDto;
import pro.userservice.dto.base.ApiResponseFactory;
import pro.userservice.dto.base.BaseApiResponse;
import pro.userservice.service.CacheService;
import pro.userservice.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CacheService cacheService;

    @PostMapping
    public ResponseEntity<BaseApiResponse<UUID>> createUser(@RequestBody UpsertUserDto dto) {
        return ResponseEntity.ok().body(ApiResponseFactory.success(userService.createUser(dto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<DetailUserDto>> findUser(@PathVariable UUID id) {
        return ResponseEntity.ok().body(ApiResponseFactory.success(userService.getUser(id)));
    }

    @GetMapping("/cleanCache/user")
    public ResponseEntity<BaseApiResponse<Boolean>> cleanCacheUser() {
        cacheService.cleanCacheUser();
        return ResponseEntity.ok().body(ApiResponseFactory.success(true));
    }
}
