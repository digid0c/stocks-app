package org.example.web.api.v1.controller.integration;

import io.restassured.RestAssured;
import org.example.persistence.entity.Employee;
import org.example.persistence.entity.Share;
import org.example.persistence.repository.EmployeeRepository;
import org.example.persistence.repository.ShareRepository;
import org.example.persistence.repository.StockRepository;
import org.example.web.api.v1.model.StockRegistrationModelList;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.example.web.WebConstants.*;
import static org.example.web.api.v1.controller.ControllerTestHelper.asJsonString;
import static org.example.web.api.v1.controller.ControllerTestHelper.createModel;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = NONE)
@Testcontainers
@ActiveProfiles("test")
public class StockControllerIntegrationTest {

    private static final BigDecimal VALID_PRICE_EUR = valueOf(25.50);
    private static final Integer VALID_VOLUME = 10;
    private static final LocalDate VALID_REGISTRATION_DATE = of(2023, 3, 5);

    @Container
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:15-alpine");

    @LocalServerPort
    private Integer port;

    @Autowired
    private ShareRepository shareRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

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
        stockRepository.deleteAll();
        shareRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    public void shouldSuccessfullyRegisterStock() {
        // given
        assertThat(stockRepository.findAll()).isEmpty();
        StockRegistrationModelList modelList = new StockRegistrationModelList();
        modelList.setStocks(List.of(createModel(populateEmployee(), populateShare(), VALID_PRICE_EUR, VALID_VOLUME,
                VALID_REGISTRATION_DATE)));

        // when
        given()
                .contentType(JSON)
                .body(asJsonString(modelList))
                .when()
                .post(V1_ENDPOINTS_URI + "/stocks")
                .then()
                .statusCode(CREATED_CODE_INT);

        // then
        assertThat(stockRepository.findAll()).hasSize(1);
    }

    @Test
    public void shouldNotRegisterStockInCaseOfValidationErrors() {
        // given
        assertThat(stockRepository.findAll()).isEmpty();
        StockRegistrationModelList modelList = new StockRegistrationModelList();
        modelList.setStocks(List.of(createModel(populateEmployee(), populateShare(), VALID_PRICE_EUR, -2,
                VALID_REGISTRATION_DATE)));

        // when
        given()
                .contentType(JSON)
                .body(asJsonString(modelList))
                .when()
                .post(V1_ENDPOINTS_URI + "/stocks")
                .then()
                .statusCode(BAD_REQUEST_CODE_INT);

        // then
        assertThat(stockRepository.findAll()).isEmpty();
    }

    @Test
    public void shouldNotRegisterStockInCaseEmployeeDoesNotExist() {
        // given
        assertThat(stockRepository.findAll()).isEmpty();
        StockRegistrationModelList modelList = new StockRegistrationModelList();
        modelList.setStocks(List.of(createModel(5L, populateShare(), VALID_PRICE_EUR, VALID_VOLUME,
                VALID_REGISTRATION_DATE)));

        // when
        given()
                .contentType(JSON)
                .body(asJsonString(modelList))
                .when()
                .post(V1_ENDPOINTS_URI + "/stocks")
                .then()
                .statusCode(BAD_REQUEST_CODE_INT);

        // then
        assertThat(stockRepository.findAll()).isEmpty();
    }

    @Test
    public void shouldNotRegisterStockInCaseShareDoesNotExist() {
        // given
        assertThat(stockRepository.findAll()).isEmpty();
        StockRegistrationModelList modelList = new StockRegistrationModelList();
        modelList.setStocks(List.of(createModel(populateEmployee(), 5L, VALID_PRICE_EUR, VALID_VOLUME,
                VALID_REGISTRATION_DATE)));

        // when
        given()
                .contentType(JSON)
                .body(asJsonString(modelList))
                .when()
                .post(V1_ENDPOINTS_URI + "/stocks")
                .then()
                .statusCode(BAD_REQUEST_CODE_INT);

        // then
        assertThat(stockRepository.findAll()).isEmpty();
    }

    private Long populateEmployee() {
        Employee employee = new Employee();

        employee.setFirstName("John");
        employee.setLastName("Deere");

        return employeeRepository.save(employee).getId();
    }

    private Long populateShare() {
        Share share = new Share();

        share.setCompanyName("Some company name");
        share.setName("Some share name");
        share.setIsinCode("US0120719985");
        share.setCountryCode("USA");
        share.setActivityField("Technology");

        return shareRepository.save(share).getId();
    }
}
