package com.project.SSPS.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.project.SSPS.model.OrderPaper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface OrderPaperRepository extends JpaRepository<OrderPaper, Long> {
    List<OrderPaper> findByOrderId(Long orderId);
    Page<OrderPaper> findAll(Pageable pageable);
    @Query("SELECT COUNT(*) FROM OrderPaper")
    Long countOrderPapers();

}
