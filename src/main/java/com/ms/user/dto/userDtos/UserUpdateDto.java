package com.ms.user.dto.userDtos;

import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

@Data
@Builder
public class UserUpdateDto {

    @Size(max = 60) private String name;

    @Email private String email;

    @Size(max = 60) private String city;

    @Size(max = 2) private String state;

    @Size(max = 8) private String postalCode;

    private String address;
}
