package org.example.web.api.v1.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TotalSpentAmountViewModel {

    private Long employeeId;
    private Integer month;
    private BigDecimal totalSpentAmount;
}
