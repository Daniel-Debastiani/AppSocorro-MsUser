package com.ms.user.controllers;

import com.ms.user.dto.emergencyDtos.EmergencyCreateDto;
import com.ms.user.dto.emergencyDtos.EmergencyResponseDto;
import com.ms.user.services.EmergencyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emergencies")
public class EmergencyController {

    private final EmergencyService emergencyService;

    public EmergencyController(EmergencyService emergencyService) {
        this.emergencyService = emergencyService;
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<EmergencyResponseDto> createEmergency(@RequestBody EmergencyCreateDto emergencyCreateDto) {
        return emergencyService.createEmergency(emergencyCreateDto);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> deleteEmergency(@PathVariable Integer id) {
        return emergencyService.deleteEmergency(id);
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<EmergencyResponseDto>> getAllEmergencies(@RequestParam(defaultValue = "false") Boolean softDeleted) {
        return emergencyService.getAllEmergencies(softDeleted);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<EmergencyResponseDto> getEmergencyById(@PathVariable Integer id, @RequestParam(defaultValue = "false") Boolean softDeleted) {
        return emergencyService.getEmergencyById(id, softDeleted);
    }

    @GetMapping("/user/{userId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<EmergencyResponseDto>> getAllEmergenciesByUserId(
            @PathVariable Integer userId, @RequestParam(defaultValue = "false") Boolean softDeleted
    ) {
        return emergencyService.getAllEmergenciesByUserId(userId, softDeleted);
    }

}
