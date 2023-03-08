package org.example.web.api.v1.controller.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import org.example.persistence.repository.AcquiredSharesViewRepository;
import org.example.persistence.repository.TotalSpentAmountViewRepository;
import org.example.web.api.v1.model.AcquiredSharesViewModelList;
import org.example.web.api.v1.model.TotalSpentAmountViewModelList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.example.web.WebConstants.V1_ENDPOINTS_URI;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = NONE)
@Testcontainers
@ActiveProfiles("test")
public class StockControllerViewsIntegrationTest {

    private static final int FULL_ACQUIRED_SHARES_REPO_SIZE = 18;
    private static final int FULL_TOTAL_SPENT_AMOUNTS_REPO_SIZE = 6;
    private static final int EMPLOYEE_ID = 1;
    private static final int JANUARY = 1;
    private static final int FEBRUARY = 2;
    private static final int YEAR_2023 = 2023;

    @Container
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:15-alpine");

    @LocalServerPort
    private Integer port;

    @Autowired
    private AcquiredSharesViewRepository acquiredSharesViewRepository;

    @Autowired
    private TotalSpentAmountViewRepository totalSpentAmountViewRepository;

    @DynamicPropertySource
    private static void datasourceProps(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.liquibase.url", POSTGRES::getJdbcUrl);
        registry.add("spring.liquibase.user", POSTGRES::getUsername);
        registry.add("spring.liquibase.password", POSTGRES::getPassword);
        registry.add("spring.liquibase.default-schema", () -> "public");
    }

    @BeforeAll
    public static void beforeAll() {
        POSTGRES.start();
    }

    @AfterAll
    public static void afterAll() {
        POSTGRES.stop();
    }

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    public void shouldSuccessfullyRetrieveAcquiredSharesForSpecifiedEmployee() {
        // given
        assertThat(acquiredSharesViewRepository.findAll()).hasSize(FULL_ACQUIRED_SHARES_REPO_SIZE);

        // when
        var response = given()
                .queryParam("month", JANUARY)
                .queryParam("year", YEAR_2023)
                .when()
                .get(V1_ENDPOINTS_URI + "/stocks/" + EMPLOYEE_ID)
                .body();

        // then
        var models = fromJsonString(response.asString(), AcquiredSharesViewModelList.class).getAcquiredSharesList();
        assertThat(models).hasSize(4);
        assertThat(models.stream()
                .allMatch(model -> model.getRegistrationDate().getMonthValue() == JANUARY
                        && model.getRegistrationDate().getYear() == YEAR_2023
                        && model.getEmployeeId() == EMPLOYEE_ID))
                .isTrue();
    }

    @Test
    public void shouldSuccessfullyRetrieveAcquiredSharesForAllEmployees() {
        // given
        assertThat(acquiredSharesViewRepository.findAll()).hasSize(FULL_ACQUIRED_SHARES_REPO_SIZE);

        // when
        var response = given()
                .queryParam("month", JANUARY)
                .queryParam("year", YEAR_2023)
                .when()
                .get(V1_ENDPOINTS_URI + "/stocks/")
                .body();

        // then
        var models = fromJsonString(response.asString(), AcquiredSharesViewModelList.class).getAcquiredSharesList();
        assertThat(models).hasSize(8);
        assertThat(models.stream()
                .allMatch(model -> model.getRegistrationDate().getMonthValue() == JANUARY
                        && model.getRegistrationDate().getYear() == YEAR_2023))
                .isTrue();
    }

    @Test
    public void shouldNotRetrieveAnyAcquiredShares() {
        // given
        assertThat(acquiredSharesViewRepository.findAll()).hasSize(FULL_ACQUIRED_SHARES_REPO_SIZE);

        // when
        var response = given()
                .queryParam("month", JANUARY)
                .queryParam("year", 2024)
                .when()
                .get(V1_ENDPOINTS_URI + "/stocks/" + EMPLOYEE_ID)
                .body();

        // then
        var models = fromJsonString(response.asString(), AcquiredSharesViewModelList.class).getAcquiredSharesList();
        assertThat(models).isEmpty();
    }

    @Test
    public void shouldSuccessfullyRetrieveTotalSpentAmountsForSpecifiedEmployee() {
        // given
        assertThat(totalSpentAmountViewRepository.findAll()).hasSize(FULL_TOTAL_SPENT_AMOUNTS_REPO_SIZE);

        // when
        var response = given()
                .queryParam("year", YEAR_2023)
                .when()
                .get(V1_ENDPOINTS_URI + "/stocks/amounts/" + EMPLOYEE_ID)
                .body();

        // then
        var models = fromJsonString(response.asString(), TotalSpentAmountViewModelList.class).getTotalSpentAmountsList();
        assertThat(models).hasSize(2);
        assertThat(models.stream()
                .allMatch(model -> (model.getMonth() == JANUARY || model.getMonth() == FEBRUARY)
                        && model.getEmployeeId() == EMPLOYEE_ID))
                .isTrue();
    }

    @Test
    public void shouldSuccessfullyRetrieveTotalSpentAmountsForAllEmployees() {
        // given
        assertThat(totalSpentAmountViewRepository.findAll()).hasSize(FULL_TOTAL_SPENT_AMOUNTS_REPO_SIZE);

        // when
        var response = given()
                .queryParam("year", YEAR_2023)
                .when()
                .get(V1_ENDPOINTS_URI + "/stocks/amounts")
                .body();

        // then
        var models = fromJsonString(response.asString(), TotalSpentAmountViewModelList.class).getTotalSpentAmountsList();
        assertThat(models).hasSize(4);
        assertThat(models.stream()
                .allMatch(model -> model.getMonth() == JANUARY || model.getMonth() == FEBRUARY))
                .isTrue();
    }

    @Test
    public void shouldNotRetrieveAnyTotalSpentAmounts() {
        // given
        assertThat(totalSpentAmountViewRepository.findAll()).hasSize(FULL_TOTAL_SPENT_AMOUNTS_REPO_SIZE);

        // when
        var response = given()
                .queryParam("year", 2024)
                .when()
                .get(V1_ENDPOINTS_URI + "/stocks/amounts/" + EMPLOYEE_ID)
                .body();

        // then
        var models = fromJsonString(response.asString(), TotalSpentAmountViewModelList.class).getTotalSpentAmountsList();
        assertThat(models).isEmpty();
    }

    private <T> T fromJsonString(String json, Class<T> clazz) {
        try {
            var objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
