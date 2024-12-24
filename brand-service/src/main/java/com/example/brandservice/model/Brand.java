package com.example.brandservice.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Table(name = "brands")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String email;
    String password;
    String field;
    String address;

    @Embedded
    GPS gps;

    // active / inactive
    @Column(columnDefinition = "varchar(255) default 'INACTIVE'")
    String status;
}
