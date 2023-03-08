package org.example.web.api.v1.controller;

import org.assertj.core.util.Lists;
import org.example.service.StockService;
import org.example.web.api.v1.model.StockRegistrationModelList;
import org.example.web.exception.RestExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDate.of;
import static java.util.stream.Collectors.toList;
import static org.example.web.WebConstants.V1_ENDPOINTS_URI;
import static org.example.web.api.v1.controller.ControllerTestHelper.asJsonString;
import static org.example.web.api.v1.controller.ControllerTestHelper.createModel;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class StockControllerUnitTest {

    private static final String TEST_URI = V1_ENDPOINTS_URI + "/stocks";

    private static final Long VALID_EMPLOYEE_ID = 5L;
    private static final Long VALID_SHARE_ID = 13L;
    private static final BigDecimal VALID_PRICE_EUR = valueOf(25.50);
    private static final Integer VALID_VOLUME = 10;
    private static final LocalDate VALID_REGISTRATION_DATE = of(2023, 3, 5);
    private static final String MONTH = "1";
    private static final String YEAR = "2023";

    @Mock
    private StockService stockService;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private StockController tested;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = standaloneSetup(tested)
                .setControllerAdvice(new RestExceptionHandler(messageSource))
                .build();
    }

    @Test
    public void shouldSuccessfullyRegisterSingleStock() throws Exception {
        // given
        StockRegistrationModelList modelList = new StockRegistrationModelList();
        modelList.setStocks(List.of(createModel(VALID_EMPLOYEE_ID, VALID_SHARE_ID, VALID_PRICE_EUR, VALID_VOLUME,
                VALID_REGISTRATION_DATE)));

        // when
        mockMvc.perform(post(TEST_URI)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(modelList)))
                .andExpect(status().isCreated());

        // then
        verify(stockService).register(modelList);
    }

    @Test
    public void shouldSuccessfullyRegisterManyStocks() throws Exception {
        // given
        StockRegistrationModelList modelList = new StockRegistrationModelList();
        modelList.setStocks(IntStream.range(0, 3)
                .mapToObj(i -> createModel(VALID_EMPLOYEE_ID, VALID_SHARE_ID, VALID_PRICE_EUR, VALID_VOLUME,
                        VALID_REGISTRATION_DATE))
                .collect(toList()));

        // when
        mockMvc.perform(post(TEST_URI)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(modelList)))
                .andExpect(status().isCreated());

        // then
        verify(stockService).register(modelList);
    }

    @Test
    public void shouldNotRegisterStockInCaseOfNullStocksList() throws Exception {
        // given
        shouldNotRegisterStocks(new StockRegistrationModelList());
    }

    @Test
    public void shouldNotRegisterStockInCaseOfEmptyStocksList() throws Exception {
        // given
        StockRegistrationModelList modelList = new StockRegistrationModelList();
        modelList.setStocks(Lists.newArrayList());
        shouldNotRegisterStocks(modelList);
    }

    @Test
    public void shouldNotRegisterStockInCaseOfNullParameters() throws Exception {
        // given
        StockRegistrationModelList modelList = new StockRegistrationModelList();
        modelList.setStocks(List.of(createModel(1L, null, null, null, null)));
        shouldNotRegisterStocks(modelList);
    }

    @Test
    public void shouldNotRegisterStockInCaseOfNegativeParameters() throws Exception {
        // given
        StockRegistrationModelList modelList = new StockRegistrationModelList();
        modelList.setStocks(List.of(createModel(-1L, -1L, VALID_PRICE_EUR, -1, VALID_REGISTRATION_DATE)));
        shouldNotRegisterStocks(modelList);
    }

    @Test
    public void shouldNotRegisterStockInCaseOfNegativePrice() throws Exception {
        // given
        StockRegistrationModelList modelList = new StockRegistrationModelList();
        modelList.setStocks(List.of(createModel(VALID_EMPLOYEE_ID, VALID_SHARE_ID, BigDecimal.valueOf(-12),
                VALID_VOLUME, VALID_REGISTRATION_DATE)));
        shouldNotRegisterStocks(modelList);
    }

    @Test
    public void shouldNotRegisterStockInCaseOfTooHighPrice() throws Exception {
        // given
        StockRegistrationModelList modelList = new StockRegistrationModelList();
        modelList.setStocks(List.of(createModel(VALID_EMPLOYEE_ID, VALID_SHARE_ID, BigDecimal.valueOf(1234567.89),
                VALID_VOLUME, VALID_REGISTRATION_DATE)));
        shouldNotRegisterStocks(modelList);
    }

    @Test
    public void shouldNotRegisterStocksInCaseAtLeastOneStockDataIsIncorrect() throws Exception {
        // given
        StockRegistrationModelList modelList = new StockRegistrationModelList();
        modelList.setStocks(IntStream.range(0, 3)
                .mapToObj(i -> createModel(VALID_EMPLOYEE_ID, VALID_SHARE_ID, VALID_PRICE_EUR, VALID_VOLUME,
                        i == 2 ? null : VALID_REGISTRATION_DATE))
                .collect(toList()));
        shouldNotRegisterStocks(modelList);
    }

    @Test
    public void shouldSuccessfullyRetrieveAcquiredSharesForSpecifiedEmployee() throws Exception {
        // when
        mockMvc.perform(get(TEST_URI + "/" + VALID_EMPLOYEE_ID)
                        .accept(APPLICATION_JSON)
                        .queryParam("month", MONTH)
                        .queryParam("year", YEAR))
                .andExpect(status().isOk());

        // then
        verify(stockService).getAcquiredShares(VALID_EMPLOYEE_ID, parseInt(MONTH), parseInt(YEAR));
    }

    @Test
    public void shouldSuccessfullyRetrieveAcquiredSharesForAllEmployees() throws Exception {
        // when
        mockMvc.perform(get(TEST_URI + "/")
                        .accept(APPLICATION_JSON)
                        .queryParam("month", MONTH)
                        .queryParam("year", YEAR))
                .andExpect(status().isOk());

        // then
        verify(stockService).getAcquiredShares(null, parseInt(MONTH), parseInt(YEAR));
    }

    @Test
    public void shouldSuccessfullyRetrieveTotalSpentAmountsForSpecifiedEmployee() throws Exception {
        // when
        mockMvc.perform(get(TEST_URI + "/amounts/" + VALID_EMPLOYEE_ID)
                        .accept(APPLICATION_JSON)
                        .queryParam("year", YEAR))
                .andExpect(status().isOk());

        // then
        verify(stockService).getTotalSpentAmounts(VALID_EMPLOYEE_ID, parseInt(YEAR));
    }

    @Test
    public void shouldSuccessfullyRetrieveTotalSpentAmountsForAllEmployees() throws Exception {
        // when
        mockMvc.perform(get(TEST_URI + "/amounts")
                        .accept(APPLICATION_JSON)
                        .queryParam("year", YEAR))
                .andExpect(status().isOk());

        // then
        verify(stockService).getTotalSpentAmounts(null, parseInt(YEAR));
    }

    private void shouldNotRegisterStocks(StockRegistrationModelList modelList) throws Exception {
        // when
        MvcResult result = mockMvc.perform(post(TEST_URI)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(modelList)))
                .andExpect(status().isBadRequest())
                .andReturn();

        // then
        verify(stockService, never()).register(any(StockRegistrationModelList.class));
    }
}
