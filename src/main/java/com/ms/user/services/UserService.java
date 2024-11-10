package com.ms.user.services;

import com.ms.user.dto.userDtos.UserCreateDto;
import com.ms.user.dto.userDtos.UserLoginResponseDto;
import com.ms.user.dto.userDtos.UserUpdateDto;
import com.ms.user.dto.userDtos.UserResponseDto;
import com.ms.user.exception.customException.CustomNotFoundException;
import com.ms.user.infra.security.TokenService;
import com.ms.user.mappers.UserMapper;
import com.ms.user.models.UserEntity;
import com.ms.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    final private UserRepository userRepository;
    final private UserMapper userMapper;
    final private TokenService tokenService;
    final private PasswordEncoder passwordEncoder;

    public ResponseEntity<UserResponseDto> createUser(UserCreateDto userCreateDto) {
        userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        UserEntity userEntity = userMapper.userCreateDtoToUserEntity(userCreateDto);
        UserEntity newUser = userRepository.save(userEntity);
        UserResponseDto userResponseDto = userMapper.userEntityToUserResponseDto(newUser);
        return ResponseEntity.status(201).body(userResponseDto);
    }

    public ResponseEntity<UserLoginResponseDto> login(String cpf, String password) {
        UserEntity userEntity = userRepository.findByCpf(cpf).orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(password, userEntity.getPassword())) {
            UserLoginResponseDto userLoginResponseDto = userMapper.userEntityToUserLoginResponseDto(userEntity);
            userLoginResponseDto.setToken(this.tokenService.generateToken(userEntity));
            return ResponseEntity.status(200).body(userLoginResponseDto);
        }
        return ResponseEntity.status(401).body(null);
    }

    @Transactional
    public ResponseEntity<UserResponseDto> updateUser(Integer id, UserUpdateDto userUpdateDto) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(id, "User"));
        userMapper.userUpdateFromDto(userUpdateDto, userEntity);
        userEntity = userRepository.save(userEntity);
        UserResponseDto user = userMapper.userEntityToUserResponseDto(userEntity);
        return ResponseEntity.status(200).body(user);
    }

    @Transactional
    public ResponseEntity<String> deleteUser(Integer id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(id, "User"));
        userEntity.setSoftDeleted(true);
        userRepository.save(userEntity);
        return ResponseEntity.status(204).body("User deleted");
    }

    public ResponseEntity<UserResponseDto> getUserById(Integer id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(id, "User"));
        UserResponseDto userResponseDto = userMapper.userEntityToUserResponseDto(userEntity);
        return ResponseEntity.status(200).body(userResponseDto);

    }

    public ResponseEntity<List<UserResponseDto>> getAllUsers(Boolean softDeleted) {
        List<UserResponseDto> users = userRepository.findAllBySoftDeleted(softDeleted).stream()
                .map(userMapper::userEntityToUserResponseDto)
                .toList();
        return ResponseEntity.status(200).body(users);
    }
}
