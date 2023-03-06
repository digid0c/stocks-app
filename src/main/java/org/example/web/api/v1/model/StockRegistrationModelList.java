package org.example.web.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(description = "Stock registration model list, used to register both one or many stocks within one request")
public class StockRegistrationModelList {

    @NotNull
    @NotEmpty
    @Schema(description = "Actual stocks data")
    private List<@Valid StockRegistrationModel> stocks;
}
