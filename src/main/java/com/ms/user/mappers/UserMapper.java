package com.ms.user.mappers;

import com.ms.user.dto.userDtos.UserCreateDto;
import com.ms.user.dto.userDtos.UserLoginResponseDto;
import com.ms.user.dto.userDtos.UserResponseDto;
import com.ms.user.dto.userDtos.UserUpdateDto;
import com.ms.user.models.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserEntity userCreateDtoToUserEntity(UserCreateDto userCreateDto);
    UserResponseDto userEntityToUserResponseDto(UserEntity userEntity);
    UserLoginResponseDto userEntityToUserLoginResponseDto(UserEntity userEntity);
    @Mapping(target = "id", ignore = true)
    void userUpdateFromDto(UserUpdateDto userUpdateDTO, @MappingTarget UserEntity userEntity);
}
