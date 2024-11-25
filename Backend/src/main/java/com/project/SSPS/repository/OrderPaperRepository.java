package com.project.SSPS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.project.SSPS.model.OrderPaper;

public interface OrderPaperRepository extends JpaRepository<OrderPaper, Long> {
    List<OrderPaper> findByOrderId(Long orderId);
}
