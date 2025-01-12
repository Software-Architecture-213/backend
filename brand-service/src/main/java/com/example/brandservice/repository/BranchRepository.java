package com.example.brandservice.repository;

import com.example.brandservice.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, String> {
    Branch findByBrandId(String brandId);
    List<Branch> findByBrandIdIn(List<String> brandIds); // // Finds branches by a list of brandIds
}
