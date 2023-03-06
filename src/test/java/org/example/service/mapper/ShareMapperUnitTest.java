package org.example.service.mapper;

import org.example.persistence.entity.Share;
import org.example.web.api.v1.model.ShareRegistrationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mapstruct.factory.Mappers.getMapper;

public class ShareMapperUnitTest {

    private static final String COMPANY_NAME = "Some company";
    private static final String SHARE_NAME = "Some share";
    private static final String ISIN_CODE = "US0120719985";
    private static final String COUNTRY_CODE = "USA";
    private static final String ACTIVITY_FIELD = "Technology";

    private ShareMapper tested;

    @BeforeEach
    public void setUp() {
        tested = getMapper(ShareMapper.class);
    }

    @Test
    public void shouldSuccessfullyMapModelToEntity() {
        // given
        ShareRegistrationModel model = new ShareRegistrationModel();
        model.setCompanyName(COMPANY_NAME);
        model.setName(SHARE_NAME);
        model.setIsinCode(ISIN_CODE);
        model.setCountryCode(COUNTRY_CODE);
        model.setActivityField(ACTIVITY_FIELD);

        // when
        Share entity = tested.toEntity(model);

        // then
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isNull();
        assertThat(entity.getCompanyName()).isEqualTo(COMPANY_NAME);
        assertThat(entity.getName()).isEqualTo(SHARE_NAME);
        assertThat(entity.getIsinCode()).isEqualTo(ISIN_CODE);
        assertThat(entity.getCountryCode()).isEqualTo(COUNTRY_CODE);
        assertThat(entity.getActivityField()).isEqualTo(ACTIVITY_FIELD);
    }
}
