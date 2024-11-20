package com.project.SSPS.repositorie;

import org.springframework.stereotype.Repository;

import com.project.SSPS.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);

    User findByEmail(String email);

    boolean existsByEmail(String email);
}
