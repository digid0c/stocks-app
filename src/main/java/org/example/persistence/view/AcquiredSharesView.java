package org.example.persistence.view;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Immutable
@Table(name = "acquired_shares_view")
@Getter
public class AcquiredSharesView {

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    private UUID id;

    @Column(name = "share_id", insertable = false, updatable = false)
    private Long shareId;

    @Column(name = "company_name", insertable = false, updatable = false)
    private String companyName;

    @Column(name = "name", insertable = false, updatable = false)
    private String name;

    @Column(name = "isin_code", insertable = false, updatable = false)
    private String isinCode;

    @Column(name = "country_code", insertable = false, updatable = false)
    private String countryCode;

    @Column(name = "activity_field", insertable = false, updatable = false)
    private String activityField;

    @Column(name = "volume", insertable = false, updatable = false)
    private Integer volume;

    @Column(name = "reg_date", insertable = false, updatable = false)
    private LocalDate registrationDate;

    @Column(name = "price_eur", insertable = false, updatable = false)
    private BigDecimal priceEur;

    @Column(name = "total_price", insertable = false, updatable = false)
    private BigDecimal totalPrice;

    @Column(name = "employee_id", insertable = false, updatable = false)
    private Long employeeId;

    @Column(name = "month", insertable = false, updatable = false)
    private Integer month;

    @Column(name = "year", insertable = false, updatable = false)
    private Integer year;
}
