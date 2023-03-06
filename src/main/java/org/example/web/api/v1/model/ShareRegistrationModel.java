package org.example.web.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.example.web.validation.ValidCountryCode;
import org.example.web.validation.ValidIsinCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Schema(description = "Share registration model, used to map data from incoming requests")
public class ShareRegistrationModel {

    @NotBlank
    @Size(min = 1, max = 200)
    @Schema(description = "Share company name")
    private String companyName;

    @NotBlank
    @Size(min = 1, max = 200)
    @Schema(description = "Share name")
    private String name;

    @NotBlank
    @ValidIsinCode
    @Schema(description = "Share 12-digits alphanumeric ISIN code")
    private String isinCode;

    @NotBlank
    @ValidCountryCode
    @Schema(description = "Share alpha-3 country code")
    private String countryCode;

    @NotBlank
    @Size(min = 1, max = 200)
    @Schema(description = "Field of economic activity")
    private String activityField;
}
