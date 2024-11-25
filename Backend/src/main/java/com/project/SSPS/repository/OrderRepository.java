package com.project.SSPS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.SSPS.model.Order;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStudentIdOrderByTimeDesc(Long studentId);
}