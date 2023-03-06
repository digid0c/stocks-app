package org.example.service.mapper;

import org.example.persistence.entity.Stock;
import org.example.web.api.v1.model.StockRegistrationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.math.BigDecimal.valueOf;
import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mapstruct.factory.Mappers.getMapper;

public class StockMapperUnitTest {

    private static final Long EMPLOYEE_ID = 5L;
    private static final Long SHARE_ID = 13L;
    private static final BigDecimal PRICE_EUR = valueOf(25.50);
    private static final Integer VOLUME = 10;
    private static final LocalDate REGISTRATION_DATE = of(2023, 3, 5);

    private StockMapper tested;

    @BeforeEach
    public void setUp() {
        tested = getMapper(StockMapper.class);
    }

    @Test
    public void shouldSuccessfullyMapModelToEntity() {
        // given
        StockRegistrationModel model = new StockRegistrationModel();
        model.setEmployeeId(EMPLOYEE_ID);
        model.setShareId(SHARE_ID);
        model.setPriceEur(PRICE_EUR);
        model.setVolume(VOLUME);
        model.setRegistrationDate(REGISTRATION_DATE);

        // when
        Stock entity = tested.toEntity(model);

        // then
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isNull();
        assertThat(entity.getEmployeeId()).isEqualTo(EMPLOYEE_ID);
        assertThat(entity.getShareId()).isEqualTo(SHARE_ID);
        assertThat(entity.getPriceEur()).isEqualTo(PRICE_EUR);
        assertThat(entity.getVolume()).isEqualTo(VOLUME);
        assertThat(entity.getRegistrationDate()).isEqualTo(REGISTRATION_DATE);
    }
}
