package com.project.SSPS.repository;

import com.project.SSPS.model.PrintingLog;
import com.project.SSPS.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrintingLogRepository extends JpaRepository<PrintingLog, Long> {
    Page<PrintingLog> findAll(Pageable pageable);

    @Query("SELECT COUNT(*) FROM PrintingLog")
    Long countPrintingLog();

    List<PrintingLog> findByFileStudentOrderByTimeDesc(User student);

    List<PrintingLog> findByPrinterIdAndFileIdOrderByTimeDesc(Long printerId, Long fileId);

    @Query("SELECT p FROM PrintingLog p " +
            "JOIN p.file f " +
            "JOIN f.student s " +
            "WHERE s.id = :studentId AND p.printer.id = :printerId " +
            "ORDER BY p.time DESC")
    List<PrintingLog> findByStudentIdAndPrinterId(@Param("studentId") Long studentId,
            @Param("printerId") Long printerId);

}
