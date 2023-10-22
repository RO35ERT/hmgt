package com.example.hotelmanagementsystem.service;

import com.example.hotelmanagementsystem.entity.Branch;
import org.springframework.stereotype.Service;

@Service
public interface BranchService {

    public Branch createBranch(String branch);
}
