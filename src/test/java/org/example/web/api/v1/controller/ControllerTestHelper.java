package org.example.web.api.v1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.web.api.v1.model.ShareRegistrationModel;
import org.example.web.api.v1.model.StockRegistrationModel;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ControllerTestHelper {

    private ControllerTestHelper() {

    }

    public static String asJsonString(final Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static ShareRegistrationModel createModel(String companyName, String name, String isinCode, String countryCode,
                                                     String activityField) {
        ShareRegistrationModel model = new ShareRegistrationModel();

        model.setCompanyName(companyName);
        model.setName(name);
        model.setIsinCode(isinCode);
        model.setCountryCode(countryCode);
        model.setActivityField(activityField);

        return model;
    }

    public static StockRegistrationModel createModel(Long employeeId, Long shareId, BigDecimal priceEur, Integer volume,
                                                     LocalDate registrationDate) {
        StockRegistrationModel model = new StockRegistrationModel();

        model.setEmployeeId(employeeId);
        model.setShareId(shareId);
        model.setPriceEur(priceEur);
        model.setVolume(volume);
        model.setRegistrationDate(registrationDate);

        return model;
    }
}
