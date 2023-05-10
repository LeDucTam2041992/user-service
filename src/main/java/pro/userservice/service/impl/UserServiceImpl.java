package pro.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.userservice.dto.DetailUserDto;
import pro.userservice.dto.SendEmailRequestBody;
import pro.userservice.dto.UpsertUserDto;
import pro.userservice.dto.base.CreateUserDto;
import pro.userservice.exception.ErrorCode;
import pro.userservice.exception.UserException;
import pro.userservice.model.User;
import pro.userservice.repository.UserRepository;
import pro.userservice.service.UserService;
import pro.userservice.service.communicator.AuthServiceCommunicator;
import pro.userservice.service.communicator.StorageServiceCommunicator;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final StorageServiceCommunicator storageServiceCommunicator;
    private final ModelMapper mapper;
    private final AuthServiceCommunicator authServiceCommunicator;
    private final NotificationServiceImpl notificationService;

    @Override
    @Transactional
    public UUID createUser(UpsertUserDto dto) {
        var userEntity = mapper.map(dto, User.class);
        userRepository.save(userEntity);
        authServiceCommunicator.createUser(CreateUserDto
                .builder()
                .username(dto.getEmail())
                .password(dto.getPassword())
                .build());
        //send email to active account
        Map<String, String> data = new HashMap<>();
        data.put("\\[source_name\\]", dto.getFullName());
        SendEmailRequestBody emailRequestBody = SendEmailRequestBody
                .builder()
                .type("otp")
                .subject("WELL COME TO K_PRODUCT!")
                .mailName("K_Product")
                .cif("000001")
                .params(data)
                .build();
        notificationService.sendMessage(emailRequestBody);
        return userEntity.getId();
    }

    @Override
//    @Cacheable(value = CommonCacheConfiguration.USER, key = "#id")
    public DetailUserDto getUser(UUID id) {
        var user = userRepository.findById(id).orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
        var userDto =  mapper.map(user, DetailUserDto.class);
        if (userDto.getAvatar() != null) {
            userDto.setAvatar(storageServiceCommunicator.generateUrl(userDto.getAvatar()));
        }
        return userDto;
    }
}
