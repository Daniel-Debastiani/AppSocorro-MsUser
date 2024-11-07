package com.ms.user.models;

import com.ms.user.enums.EmergencyTypeEnum;
import jakarta.persistence.*;
import lombok.Data;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "tb_emergency")
public class EmergencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmergencyTypeEnum type;

    @Column(length = 100)
    private String location;

    @Column(nullable = false)
    private ZonedDateTime date = ZonedDateTime.now();

    @Column(nullable = false)
    private ZonedDateTime createdAt;

    @Column
    private ZonedDateTime updatedAt;

    @Column
    private Boolean softDeleted = false;

    @PrePersist
    public void prePersist() {
        createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = ZonedDateTime.now();
    }
}
