package com.project.SSPS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.project.SSPS.model.StudentPaper;

public interface StudentPaperRepository extends JpaRepository<StudentPaper, Long> {
    List<StudentPaper> findByStudentId(Long studentId);
    StudentPaper findByStudentIdAndPaperType(Long studentId, String paperType);
}
