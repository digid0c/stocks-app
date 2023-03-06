package org.example.web.api.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.service.ShareService;
import org.example.web.api.v1.model.ShareRegistrationModelList;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.example.web.WebConstants.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping(V1_ENDPOINTS_URI + "/shares")
@Tag(name = "Shares API", description = "Provides CRUD operations to manage shares entities")
public class ShareController {

    private final ShareService shareService;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(description = "Register new shares", responses = {
            @ApiResponse(responseCode = CREATED_CODE, description = "Returned in case shares registration is successful"),
            @ApiResponse(responseCode = BAD_REQUEST_CODE, description = "Returned in case parameters validation fails")
    })
    public void register(@Parameter(description = "List of shares to register", required = true)
                         @Valid @RequestBody ShareRegistrationModelList shareRegistrationModelList) {
        shareService.register(shareRegistrationModelList);
    }
}
