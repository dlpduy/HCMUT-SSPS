package com.project.SSPS.repository;

import org.springframework.stereotype.Repository;

import com.project.SSPS.model.FileType;
import com.project.SSPS.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface FileTypeRepository extends JpaRepository<FileType, Long> {
    FileType save(FileType fileType);

    List<FileType> findAll();

    boolean existsByType(String type);
}
