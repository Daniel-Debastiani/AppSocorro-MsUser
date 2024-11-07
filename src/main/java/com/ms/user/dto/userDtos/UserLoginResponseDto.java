package com.ms.user.dto.userDtos;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserLoginResponseDto extends UserResponseDto{
    private String token;
}
