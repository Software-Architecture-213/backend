package com.example.brandservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @Size(max = 255)
    @Column(name = "id", nullable = false)
    private String id;

    @NotNull
    @Column(name = "userid", nullable = false)
    private UUID userid;

    @NotNull
    @Column(name = "message", nullable = false, length = Integer.MAX_VALUE)
    private String message;

    @Size(max = 10)
    @NotNull
    @Column(name = "type", nullable = false, length = 10)
    private String type;

    @ColumnDefault("false")
    @Column(name = "isread")
    private Boolean isread;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}