package com.ms.user.services;

import com.ms.user.dto.userDtos.*;
import com.ms.user.exception.customException.CustomNotFoundException;
import com.ms.user.infra.security.TokenService;
import com.ms.user.mappers.UserMapper;
import com.ms.user.models.UserEntity;
import com.ms.user.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private TokenService tokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserEntity userEntity;
    private UserCreateDto userCreateDto;
    private UserUpdateDto userUpdateDto;
    private UserResponseDto userResponseDto;
    private UserLoginResponseDto userLoginResponseDto;

    @BeforeEach
    void setUp() {
        try (AutoCloseable closeable = MockitoAnnotations.openMocks(this)) {
            userEntity = new UserEntity();
            userEntity.setId(1);
            userEntity.setCpf("04457999008");
            userEntity.setEmail("user@test.com");
            userEntity.setPassword("encodedPassword");

            userCreateDto = new UserCreateDto();
            userCreateDto.setCpf("04457999008");
            userCreateDto.setPassword("password");

            userUpdateDto = new UserUpdateDto();
            userUpdateDto.setEmail("email@test.com");

            userResponseDto = new UserResponseDto();
            userResponseDto.setId(1);
            userResponseDto.setCpf("04457999008");
            userResponseDto.setEmail("email@test.com");

            userLoginResponseDto = new UserLoginResponseDto();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void createUser_shouldCreateUser() {
        when(passwordEncoder.encode(userCreateDto.getPassword())).thenReturn("encodedPassword");
        when(userMapper.userCreateDtoToUserEntity(userCreateDto)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.userEntityToUserResponseDto(userEntity)).thenReturn(userResponseDto);

        ResponseEntity<UserResponseDto> response = userService.createUser(userCreateDto);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("04457999008", response.getBody().getCpf());
    }

    @Test
    void login_shouldReturnToken_whenCredentialsAreCorrect() {
        when(userRepository.findByCpf(userCreateDto.getCpf())).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(userCreateDto.getPassword(), userEntity.getPassword())).thenReturn(true);
        when(userMapper.userEntityToUserLoginResponseDto(userEntity)).thenReturn(userLoginResponseDto);
        when(tokenService.generateToken(userEntity)).thenReturn("token");

        ResponseEntity<UserLoginResponseDto> response = userService.login(userCreateDto.getCpf(), userCreateDto.getPassword());

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("token", response.getBody().getToken());
    }

    @Test
    void login_shouldReturn401_whenCredentialsAreIncorrect() {
        when(userRepository.findByCpf(userCreateDto.getCpf())).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(userCreateDto.getPassword(), userEntity.getPassword())).thenReturn(false);

        ResponseEntity<UserLoginResponseDto> response = userService.login(userCreateDto.getCpf(), userCreateDto.getPassword());

        assertEquals(401, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void updateUser_shouldUpdateUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.userEntityToUserResponseDto(userEntity)).thenReturn(userResponseDto);

        ResponseEntity<UserResponseDto> response = userService.updateUser(1, userUpdateDto);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("email@test.com", response.getBody().getEmail()); // Check for email update
    }

    @Test
    void updateUser_shouldThrowNotFound_whenUserDoesNotExist() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CustomNotFoundException.class, () -> userService.updateUser(1, userUpdateDto));
    }

    @Test
    void deleteUser_shouldDeleteUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        ResponseEntity<String> response = userService.deleteUser(1);

        assertEquals(204, response.getStatusCode().value());
        assertEquals("User deleted", response.getBody());
    }

    @Test
    void deleteUser_shouldThrowNotFound_whenUserDoesNotExist() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CustomNotFoundException.class, () -> userService.deleteUser(1));
    }

    @Test
    void getUserById_shouldReturnUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(userEntity));
        when(userMapper.userEntityToUserResponseDto(userEntity)).thenReturn(userResponseDto);

        ResponseEntity<UserResponseDto> response = userService.getUserById(1);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("04457999008", response.getBody().getCpf());
    }

    @Test
    void getUserById_shouldThrowNotFound_whenUserDoesNotExist() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CustomNotFoundException.class, () -> userService.getUserById(1));
    }

    @Test
    void getAllUsers_shouldReturnUsers() {
        List<UserEntity> users = List.of(userEntity);
        when(userRepository.findAllBySoftDeleted(false)).thenReturn(users);
        when(userMapper.userEntityToUserResponseDto(userEntity)).thenReturn(userResponseDto);

        ResponseEntity<List<UserResponseDto>> response = userService.getAllUsers(false);

        assertEquals(200, response.getStatusCode().value());
        assertFalse(Objects.requireNonNull(response.getBody()).isEmpty());
        assertEquals("04457999008", response.getBody().getFirst().getCpf());
    }
}
