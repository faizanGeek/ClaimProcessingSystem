package com.claim.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.claim.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
