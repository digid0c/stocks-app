package org.example.web.api.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.service.StockService;
import org.example.web.api.v1.model.StockRegistrationModelList;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.example.web.WebConstants.*;
import static org.springframework.http.HttpStatus.CREATED;

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
}
