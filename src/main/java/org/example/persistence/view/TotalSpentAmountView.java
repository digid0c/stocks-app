package org.example.persistence.view;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Immutable
@Table(name = "total_spent_amount_view")
@Getter
public class TotalSpentAmountView {

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    private UUID id;

    @Column(name = "employee_id", insertable = false, updatable = false)
    private Long employeeId;

    @Column(name = "month", insertable = false, updatable = false)
    private Integer month;

    @Column(name = "year", insertable = false, updatable = false)
    private Integer year;

    @Column(name = "total_spent_amount", insertable = false, updatable = false)
    private BigDecimal totalSpentAmount;
}
