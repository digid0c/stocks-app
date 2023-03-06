package org.example.web.api.v1.controller.integration;

import io.restassured.RestAssured;
import org.example.persistence.repository.ShareRepository;
import org.example.web.api.v1.model.ShareRegistrationModelList;
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

import java.util.stream.IntStream;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.example.web.WebConstants.*;
import static org.example.web.api.v1.controller.ControllerTestHelper.asJsonString;
import static org.example.web.api.v1.controller.ControllerTestHelper.createModel;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = NONE)
@Testcontainers
@ActiveProfiles("test")
public class ShareControllerIntegrationTest {

    private static final String VALID_COMPANY_NAME = "Some company";
    private static final String VALID_SHARE_NAME = "Some share";
    private static final String VALID_ISIN_CODE = "US0120719985";
    private static final String VALID_COUNTRY_CODE = "USA";
    private static final String VALID_ACTIVITY_FIELD = "Technology";

    @Container
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:15-alpine");

    @LocalServerPort
    private Integer port;

    @Autowired
    private ShareRepository shareRepository;

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
        shareRepository.deleteAll();
    }

    @Test
    public void shouldSuccessfullyRegisterShares() {
        // given
        assertThat(shareRepository.findAll()).isEmpty();
        ShareRegistrationModelList modelList = new ShareRegistrationModelList();
        modelList.setShares(IntStream.range(0, 3)
                .mapToObj(i -> createModel(VALID_COMPANY_NAME, VALID_SHARE_NAME, VALID_ISIN_CODE, VALID_COUNTRY_CODE,
                        VALID_ACTIVITY_FIELD))
                .collect(toList()));

        // when
        given()
                .contentType(JSON)
                .body(asJsonString(modelList))
                .when()
                .post(V1_ENDPOINTS_URI + "/shares")
                .then()
                .statusCode(CREATED_CODE_INT);

        // then
        assertThat(shareRepository.findAll()).hasSize(3);
    }

    @Test
    public void shouldNotRegisterSharesInCaseAtLeastOneShareDataIsIncorrect() {
        // given
        assertThat(shareRepository.findAll()).isEmpty();
        ShareRegistrationModelList modelList = new ShareRegistrationModelList();
        modelList.setShares(IntStream.range(0, 3)
                .mapToObj(i -> createModel(VALID_COMPANY_NAME, VALID_SHARE_NAME, VALID_ISIN_CODE, VALID_COUNTRY_CODE,
                        i == 2 ? null : VALID_ACTIVITY_FIELD))
                .collect(toList()));

        // when
        given()
                .contentType(JSON)
                .body(asJsonString(modelList))
                .when()
                .post(V1_ENDPOINTS_URI + "/shares")
                .then()
                .statusCode(BAD_REQUEST_CODE_INT);

        // then
        assertThat(shareRepository.findAll()).isEmpty();
    }
}
