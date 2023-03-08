package org.example.web.api.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.service.StockService;
import org.example.web.api.v1.model.AcquiredSharesViewModelList;
import org.example.web.api.v1.model.StockRegistrationModelList;
import org.example.web.api.v1.model.TotalSpentAmountViewModelList;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.example.web.WebConstants.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(V1_ENDPOINTS_URI + "/stocks")
@Tag(name = "Stocks API", description = "Provides CRUD operations to manage stocks entities")
public class StockController {

    private final StockService stockService;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(description = "Register new stocks", responses = {
            @ApiResponse(responseCode = CREATED_CODE, description = "Returned in case stocks registration is successful"),
            @ApiResponse(responseCode = BAD_REQUEST_CODE, description = "Returned in case parameters validation fails")
    })
    public void register(@Parameter(description = "List of stocks to register", required = true)
                       @Valid @RequestBody StockRegistrationModelList stockRegistrationModelList) {
        stockService.register(stockRegistrationModelList);
    }

    @GetMapping({"/", "/{employeeId}"})
    @ResponseStatus(OK)
    @Operation(description = "List share acquiring records for a specified month and year")
    public AcquiredSharesViewModelList getAcquiredShares(@PathVariable(required = false) Long employeeId,
                                                         @RequestParam Integer month,
                                                         @RequestParam Integer year) {
        return stockService.getAcquiredShares(employeeId, month, year);
    }

    @GetMapping({"/amounts", "/amounts/{employeeId}"})
    @ResponseStatus(OK)
    @Operation(description = "List total spent amount of money by month for a specified year")
    public TotalSpentAmountViewModelList getTotalAmounts(@PathVariable(required = false) Long employeeId,
                                                           @RequestParam Integer year) {
        return stockService.getTotalSpentAmounts(employeeId, year);
    }
}
