package com.project.SSPS.repository;

import com.project.SSPS.model.Printer;
import com.project.SSPS.response.PrinterResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PrinterRepository extends JpaRepository<Printer, Long> {
    List<Printer> findAll();
}
