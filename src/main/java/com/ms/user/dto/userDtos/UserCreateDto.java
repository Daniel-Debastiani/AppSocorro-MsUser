package com.ms.user.dto.userDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    @NotBlank @Size(max = 60) private String name;

    @NotBlank @Email private String email;

    @NotBlank @CPF @Size(min = 11, max = 11) private String cpf;

    @NotBlank @Size(min = 8, max = 100) private String password;

    @Size(max = 60) private String city;

    @Size(max = 2) private String state;

    @Size(max = 8) private String postalCode;
    private String address;
}
