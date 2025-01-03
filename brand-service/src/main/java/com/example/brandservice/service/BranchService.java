package com.example.brandservice.service;

import com.example.brandservice.model.Branch;
import com.example.brandservice.repository.BranchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BranchService {
    private final BranchRepository branchRepository;

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    public Branch createBranch(Branch branch) {
        return branchRepository.save(branch);
    }
}
