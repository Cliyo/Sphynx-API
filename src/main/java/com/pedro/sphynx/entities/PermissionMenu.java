package com.pedro.sphynx.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="permission_menus")
@Entity(name="PermissionMenu")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionMenu {
    private Long id;
    private String name;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
