package org.example.web.api.v1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.util.Lists;
import org.example.service.ShareService;
import org.example.web.api.v1.model.ShareRegistrationModel;
import org.example.web.api.v1.model.ShareRegistrationModelList;
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

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.example.web.WebConstants.V1_ENDPOINTS_URI;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class ShareControllerUnitTest {

    private static final String TEST_URI = V1_ENDPOINTS_URI + "/shares";

    private static final String VALID_COMPANY_NAME = "Some company";
    private static final String VALID_SHARE_NAME = "Some share";
    private static final String VALID_ISIN_CODE = "US0120719985";
    private static final String VALID_COUNTRY_CODE = "USA";
    private static final String VALID_ACTIVITY_FIELD = "Technology";
    private static final String TOO_LONG_STRING = "pfo6lQywV5jH0UnILtGA3EiVCRoLCato6PkOiVfFhDnx0pzs72OAmP3m5XqY539QoBkxk" +
            "R5JEz4deQsFq37TZowLyttB3wD2S3PMwmbnLNyNZftdwXAX2DUhQwRpazfeTcEguuHpnEcbty3oB3x4L9DbFHe6cdlhbIZ6JxYVGnfDepGX" +
            "Sk5wxiAzACNmrP3lz0rHytAOCC7VS98Q5xM9S4ju2SC0ltfrW";

    @Mock
    private ShareService shareService;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private ShareController tested;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = standaloneSetup(tested)
                .setControllerAdvice(new RestExceptionHandler(messageSource))
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void shouldSuccessfullyRegisterSingleShare() throws Exception {
        // given
        ShareRegistrationModelList modelList = new ShareRegistrationModelList();
        modelList.setShares(List.of(createModel(VALID_COMPANY_NAME, VALID_SHARE_NAME, VALID_ISIN_CODE, VALID_COUNTRY_CODE,
                VALID_ACTIVITY_FIELD)));

        // when
        mockMvc.perform(post(TEST_URI)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(modelList)))
                .andExpect(status().isCreated());

        // then
        verify(shareService).register(modelList);
    }

    @Test
    public void shouldSuccessfullyRegisterManyShares() throws Exception {
        // given
        ShareRegistrationModelList modelList = new ShareRegistrationModelList();
        modelList.setShares(IntStream.range(0, 3)
                .mapToObj(i -> createModel(VALID_COMPANY_NAME, VALID_SHARE_NAME, VALID_ISIN_CODE, VALID_COUNTRY_CODE,
                        VALID_ACTIVITY_FIELD))
                .collect(toList()));

        // when
        mockMvc.perform(post(TEST_URI)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(modelList)))
                .andExpect(status().isCreated());

        // then
        verify(shareService).register(modelList);
    }

    @Test
    public void shouldNotRegisterShareInCaseOfNullSharesList() throws Exception {
        // given
        shouldNotRegisterShares(new ShareRegistrationModelList());
    }

    @Test
    public void shouldNotRegisterShareInCaseOfEmptySharesList() throws Exception {
        // given
        ShareRegistrationModelList modelList = new ShareRegistrationModelList();
        modelList.setShares(Lists.newArrayList());
        shouldNotRegisterShares(modelList);
    }

    @Test
    public void shouldNotRegisterShareInCaseOfNullParameters() throws Exception {
        // given
        ShareRegistrationModelList modelList = new ShareRegistrationModelList();
        modelList.setShares(List.of(createModel(null, null, null, null, null)));
        shouldNotRegisterShares(modelList);
    }

    @Test
    public void shouldNotRegisterShareInCaseOfEmptyParameters() throws Exception {
        // given
        ShareRegistrationModelList modelList = new ShareRegistrationModelList();
        modelList.setShares(List.of(createModel("", "", "", "", "")));
        shouldNotRegisterShares(modelList);
    }

    @Test
    public void shouldNotRegisterShareInCaseOfTooLongStringParameters() throws Exception {
        // given
        ShareRegistrationModelList modelList = new ShareRegistrationModelList();
        modelList.setShares(List.of(createModel(TOO_LONG_STRING, TOO_LONG_STRING, VALID_ISIN_CODE, VALID_COUNTRY_CODE,
                TOO_LONG_STRING)));
        shouldNotRegisterShares(modelList);
    }

    @Test
    public void shouldNotRegisterShareInCaseOfIncorrectIsinCode() throws Exception {
        // given
        ShareRegistrationModelList modelList = new ShareRegistrationModelList();
        modelList.setShares(List.of(createModel(VALID_COMPANY_NAME, VALID_SHARE_NAME, "USA120LA99LA", VALID_COUNTRY_CODE,
                VALID_ACTIVITY_FIELD)));
        shouldNotRegisterShares(modelList);
    }

    @Test
    public void shouldNotRegisterShareInCaseOfIncorrectCountryCode() throws Exception {
        // given
        ShareRegistrationModelList modelList = new ShareRegistrationModelList();
        modelList.setShares(List.of(createModel(VALID_COMPANY_NAME, VALID_SHARE_NAME, VALID_ISIN_CODE, "US",
                VALID_ACTIVITY_FIELD)));
        shouldNotRegisterShares(modelList);
    }

    private void shouldNotRegisterShares(ShareRegistrationModelList modelList) throws Exception {
        // when
        MvcResult result = mockMvc.perform(post(TEST_URI)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(modelList)))
                .andExpect(status().isBadRequest())
                .andReturn();

        // then
        verify(shareService, never()).register(any(ShareRegistrationModelList.class));
    }

    private ShareRegistrationModel createModel(String companyName, String name, String isinCode, String countryCode,
                                               String activityField) {
        ShareRegistrationModel model = new ShareRegistrationModel();

        model.setCompanyName(companyName);
        model.setName(name);
        model.setIsinCode(isinCode);
        model.setCountryCode(countryCode);
        model.setActivityField(activityField);

        return model;
    }

    private String asJsonString(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
