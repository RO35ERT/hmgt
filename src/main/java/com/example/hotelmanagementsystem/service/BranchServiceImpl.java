package com.example.hotelmanagementsystem.service;

import com.example.hotelmanagementsystem.entity.Branch;
import com.example.hotelmanagementsystem.repository.BranchRepository;
import org.springframework.stereotype.Service;

@Service
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    public BranchServiceImpl(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public Branch createBranch(String branch) {
        Branch branch1 = new Branch();
        branch1.setBranch(branch);
        return branchRepository.save(branch1);
    }
}
