package com.example.hotelmanagementsystem.repository;

import com.example.hotelmanagementsystem.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch,Long> {
    public Branch findByBranch(String branch);
}
