package com.interview.repository;

import com.interview.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByEmailId(String emailId);

    Optional<User> findById(Long id);

    boolean existsByEmailId(String emailId);

    Page<User> getUsersBy(Pageable pageable);

}
