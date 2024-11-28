package com.project.SSPS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.project.SSPS.model.Paper;

public interface PaperRepository extends JpaRepository<Paper, Long> {
    boolean existsByType(String type);
    Paper findByType(String type);
}
