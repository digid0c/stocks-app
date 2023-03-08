package org.example.web.api.v1.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AcquiredSharesViewModel {

    private String companyName;
    private String name;
    private String isinCode;
    private String countryCode;
    private String activityField;
    private Integer volume;
    private LocalDate registrationDate;
    private BigDecimal priceEur;
    private BigDecimal totalPrice;
    private Long employeeId;
}
