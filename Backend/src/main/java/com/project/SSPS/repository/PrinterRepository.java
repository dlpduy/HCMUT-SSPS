package com.project.SSPS.repository;

import com.project.SSPS.model.Printer;
import com.project.SSPS.response.PrinterResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrinterRepository extends JpaRepository<Printer, Long> {
    List<Printer> findAll();
}
