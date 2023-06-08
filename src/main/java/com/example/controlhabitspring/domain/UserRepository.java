package com.example.controlhabitspring.domain;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, String>, JpaSpecificationExecutor<User> {
    List<User> findAll();
    Optional<User> findByUserId(String userId);
    Optional<User> findByUserEmail(String email);
}
