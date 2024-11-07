package com.ms.user.dto.emergencyDtos;

import com.ms.user.dto.userDtos.UserResponseDto;
import com.ms.user.enums.EmergencyTypeEnum;
import lombok.Builder;
import lombok.Data;
import java.time.ZonedDateTime;

@Data
@Builder
public class EmergencyResponseDto {
    private Integer id;
    private UserResponseDto user;
    private EmergencyTypeEnum type;
    private String location;
    private ZonedDateTime date;
}
