package com.ms.user.services;

import com.ms.user.dto.emergencyDtos.EmergencyCreateDto;
import com.ms.user.dto.emergencyDtos.EmergencyResponseDto;
import com.ms.user.exception.customException.CustomNotFoundException;
import com.ms.user.mappers.EmergencyMapper;
import com.ms.user.models.EmergencyEntity;
import com.ms.user.models.UserEntity;
import com.ms.user.repositories.EmergencyRepository;
import com.ms.user.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmergencyService {

    private final EmergencyRepository emergencyRepository;
    private final UserRepository userRepository;
    private final EmergencyMapper emergencyMapper;

    public EmergencyService(EmergencyRepository emergencyRepository, UserRepository userRepository, EmergencyMapper emergencyMapper) {
        this.emergencyRepository = emergencyRepository;
        this.userRepository = userRepository;
        this.emergencyMapper = emergencyMapper;
    }

    public ResponseEntity<EmergencyResponseDto> createEmergency(EmergencyCreateDto emergencyCreateDto) {
        EmergencyEntity emergencyEntity = emergencyMapper.emergencyCreateDtoToEmergencyEntity(emergencyCreateDto);
        EmergencyEntity savedEmergency = emergencyRepository.save(emergencyEntity);
        EmergencyResponseDto emergencyResponseDto = emergencyMapper.emergencyEntityToEmergencyResponseDto(savedEmergency);
        return ResponseEntity.status(201).body(emergencyResponseDto);
    }

    @Transactional
    public ResponseEntity<String> deleteEmergency(Integer id) {
        EmergencyEntity emergencyEntity = emergencyRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(id, "Emergency"));
        emergencyEntity.setSoftDeleted(true);
        emergencyRepository.save(emergencyEntity);
        return ResponseEntity.status(204).body("Emergency deleted");
    }

    public ResponseEntity<List<EmergencyResponseDto>> getAllEmergencies(Boolean softDeleted) {
        List<EmergencyResponseDto> emergencies = emergencyRepository.findAllBySoftDeleted(softDeleted).stream()
                .map(emergencyMapper::emergencyEntityToEmergencyResponseDto)
                .toList();
        return ResponseEntity.status(200).body(emergencies);
    }

    public ResponseEntity<EmergencyResponseDto> getEmergencyById(Integer id, Boolean softDeleted) {
        EmergencyEntity emergencyEntity = emergencyRepository.findByIdAndSoftDeleted(id, softDeleted)
                .orElseThrow(() -> new CustomNotFoundException(id, "Emergency"));
        EmergencyResponseDto emergencyResponseDto = emergencyMapper.emergencyEntityToEmergencyResponseDto(emergencyEntity);
        return ResponseEntity.status(200).body(emergencyResponseDto);
    }

    public ResponseEntity<List<EmergencyResponseDto>> getAllEmergenciesByUserId(Integer userId, Boolean softDeleted) {
        List<EmergencyResponseDto> emergencies = emergencyRepository.findByUserIdAndSoftDeleted(userId, softDeleted).stream()
                .map(emergencyMapper::emergencyEntityToEmergencyResponseDto)
                .toList();
        return ResponseEntity.status(200).body(emergencies);
    }
}
