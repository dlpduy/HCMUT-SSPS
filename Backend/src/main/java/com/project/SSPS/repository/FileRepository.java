package com.project.SSPS.repository;

import com.project.SSPS.model.File;
import com.project.SSPS.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByStudentId(Long studentId);

    Optional<File> findByStudentIdAndFileNameAndFileType(Long studentId, String fileName, String fileType);

    boolean existsByStudentIdAndFileNameAndFileType(Long studentId, String fileName, String fileType);

    List<File> findByStudentIdAndFileType(Long studentId, String fileType);

    void deleteByStudentId(Long studentId);

    List<File> findByStudentOrderByIdDesc(User student);
}