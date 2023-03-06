package org.example.web.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Data
@Schema(description = "Stock registration model, used to map data from incoming requests")
public class StockRegistrationModel {

    @NotNull
    @Positive
    @Schema(description = "Employee ID")
    private Long employeeId;

    @NotNull
    @Positive
    @Schema(description = "Purchased share ID")
    private Long shareId;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = false)
    @DecimalMax(value = "999999.99", inclusive = true)
    @Schema(description = "Purchased share price in EUR")
    private BigDecimal priceEur;

    @NotNull
    @Positive
    @Schema(description = "Purchased share volume")
    private Integer volume;

    @NotNull
    @DateTimeFormat(iso = DATE)
    @Schema(description = "Actual share purchase ISO date")
    private LocalDate registrationDate;
}
