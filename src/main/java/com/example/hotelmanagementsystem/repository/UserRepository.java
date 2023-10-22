package com.example.hotelmanagementsystem.repository;


import com.example.hotelmanagementsystem.entity.Branch;
import com.example.hotelmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    User findByEmail(String email);
    User findByEmailAndIsEnabled(String email, Boolean isEnabled);

    List<User> findAllByBranch(Branch branch);
}
