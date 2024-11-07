package com.ms.user.mappers;

import com.ms.user.dto.emergencyDtos.EmergencyCreateDto;
import com.ms.user.dto.emergencyDtos.EmergencyResponseDto;
import com.ms.user.models.EmergencyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmergencyMapper {
    @Mapping(source = "userId", target = "user.id")
    EmergencyEntity emergencyCreateDtoToEmergencyEntity(EmergencyCreateDto createEmergencyDto);
    @Mapping(source = "user", target = "user")
    EmergencyResponseDto emergencyEntityToEmergencyResponseDto(EmergencyEntity emergencyEntity);
}
