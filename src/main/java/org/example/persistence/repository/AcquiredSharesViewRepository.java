package org.example.persistence.repository;

import org.example.persistence.view.AcquiredSharesView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AcquiredSharesViewRepository extends JpaRepository<AcquiredSharesView, UUID> {

    List<AcquiredSharesView> findByMonthAndYear(Integer month, Integer year);

    List<AcquiredSharesView> findByMonthAndYearAndEmployeeId(Integer month, Integer year, Long employeeId);
}
