package com.ms.user.dto.userDtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserLoginResponseDto extends UserResponseDto{
    private String token;
}
