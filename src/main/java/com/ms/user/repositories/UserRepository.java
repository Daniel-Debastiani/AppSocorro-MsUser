package com.ms.user.repositories;

import com.ms.user.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findAllBySoftDeleted(Boolean softDeleted);
    Optional<UserEntity> findByCpf(String cpf);
}
