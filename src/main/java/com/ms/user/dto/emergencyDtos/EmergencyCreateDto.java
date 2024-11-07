package com.ms.user.dto.emergencyDtos;

import com.ms.user.enums.EmergencyTypeEnum;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Builder
public class EmergencyCreateDto {

    @NotNull private Integer userId;

    @NotNull private EmergencyTypeEnum type;

    @NotBlank @Size(max = 100) private String location;
}
