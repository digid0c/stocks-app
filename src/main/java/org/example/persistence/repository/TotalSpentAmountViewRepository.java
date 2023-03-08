package org.example.persistence.repository;

import org.example.persistence.view.TotalSpentAmountView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TotalSpentAmountViewRepository extends JpaRepository<TotalSpentAmountView, UUID> {

    List<TotalSpentAmountView> findByYear(Integer year);

    List<TotalSpentAmountView> findByYearAndEmployeeId(Integer year, Long employeeId);
}
