package com.ms.user.dto.userDtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    private Integer id;
    private String name;
    private String email;
    private String cpf;
    private String city;
    private String state;
    private String postalCode;
    private String address;
    private Boolean verified;
    private Boolean softDeleted;
    private String token;
}
