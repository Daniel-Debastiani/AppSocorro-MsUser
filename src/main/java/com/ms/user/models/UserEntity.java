package com.ms.user.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "tb_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, length = 11, nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String password;

    @Column(length = 60)
    private String city;

    @Column(length = 2)
    private String state;

    @Column(length = 8)
    private String postalCode;

    private String address;

    @Column(nullable = false)
    private Boolean verified = false;

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
