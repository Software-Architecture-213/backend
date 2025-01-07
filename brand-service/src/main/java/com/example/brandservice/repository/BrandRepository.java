package com.example.brandservice.repository;

import com.example.brandservice.model.Brand;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {
    Optional<Brand> findByUsername(String username);
    Page<Brand> findAll(@NotNull Pageable pageable);
}
