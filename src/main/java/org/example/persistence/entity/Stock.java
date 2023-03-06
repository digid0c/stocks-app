package org.example.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "stock")
@Data
public class Stock {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "share_id", nullable = false)
    private Long shareId;

    @Column(name = "price_eur", nullable = false, precision = 8, scale = 2)
    private BigDecimal priceEur;

    @Column(name = "volume", nullable = false)
    private Integer volume;

    @Column(name = "reg_date", nullable = false)
    private LocalDate registrationDate;
}
