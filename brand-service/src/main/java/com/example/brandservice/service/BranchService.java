package com.example.brandservice.service;

import com.example.brandservice.model.Branch;
import com.example.brandservice.repository.BranchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Branch> getMainBranches(List<String> brandIds) {
        List<Branch> branches = branchRepository.findByBrandIdIn(brandIds);
        Map<String, Branch> brandIdAndBranch = new HashMap<>();
        for (Branch branch : branches) {
            brandIdAndBranch.put(branch.getBrandId(), branch);
        }
        return brandIdAndBranch;
    }
}
