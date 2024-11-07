package com.ms.user.repositories;

import com.ms.user.models.EmergencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmergencyRepository extends JpaRepository<EmergencyEntity, Integer> {
    List<EmergencyEntity> findAllBySoftDeleted(Boolean softDeleted);
    List<EmergencyEntity> findByUserIdAndSoftDeleted(Integer userId, Boolean softDeleted);
    Optional<EmergencyEntity> findByIdAndSoftDeleted(Integer id, Boolean softDeleted);
}
