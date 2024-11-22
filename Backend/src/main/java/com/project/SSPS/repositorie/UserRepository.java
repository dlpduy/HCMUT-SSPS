package com.project.SSPS.repositorie;

import org.springframework.stereotype.Repository;

import com.project.SSPS.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
