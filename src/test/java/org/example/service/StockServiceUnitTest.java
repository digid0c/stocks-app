package org.example.service;

import org.example.persistence.entity.Stock;
import org.example.persistence.repository.AcquiredSharesViewRepository;
import org.example.persistence.repository.StockRepository;
import org.example.persistence.repository.TotalSpentAmountViewRepository;
import org.example.persistence.view.AcquiredSharesView;
import org.example.persistence.view.TotalSpentAmountView;
import org.example.service.mapper.StockMapper;
import org.example.web.api.v1.model.AcquiredSharesViewModel;
import org.example.web.api.v1.model.StockRegistrationModel;
import org.example.web.api.v1.model.StockRegistrationModelList;
import org.example.web.api.v1.model.TotalSpentAmountViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockServiceUnitTest {

    private static final int AMOUNT_OF_STOCKS = 3;
    private static final int MONTH = 1;
    private static final int YEAR = 2023;
    private static final long EMPLOYEE_ID = 1L;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private AcquiredSharesViewRepository acquiredSharesViewRepository;

    @Mock
    private TotalSpentAmountViewRepository totalSpentAmountViewRepository;

    @Mock
    private StockMapper stockMapper;

    @Captor
    private ArgumentCaptor<List<Stock>> stockCaptor;

    @InjectMocks
    private StockService tested;

    private StockRegistrationModelList stockRegistrationModelList;

    @BeforeEach
    public void setUp() {
        stockRegistrationModelList = new StockRegistrationModelList();
        stockRegistrationModelList.setStocks(List.of(new StockRegistrationModel(), new StockRegistrationModel(),
                new StockRegistrationModel()));
    }

    @Test
    public void shouldSuccessfullyRegisterNewStocks() {
        // given
        when(stockMapper.toEntity(any(StockRegistrationModel.class))).thenReturn(new Stock());

        // when
        tested.register(stockRegistrationModelList);

        // then
        verify(stockMapper, times(AMOUNT_OF_STOCKS)).toEntity(any(StockRegistrationModel.class));
        verify(stockRepository).saveAll(stockCaptor.capture());

        List<Stock> stocks = stockCaptor.getValue();
        assertThat(stocks).isNotNull();
        assertThat(stocks).hasSize(AMOUNT_OF_STOCKS);
    }

    @Test
    public void shouldSuccessfullyRetrieveAcquiredSharesForSpecifiedEmployee() {
        // given
        when(acquiredSharesViewRepository.findByMonthAndYearAndEmployeeId(MONTH, YEAR, EMPLOYEE_ID))
                .thenReturn(List.of(new AcquiredSharesView()));
        when(stockMapper.toModel(any(AcquiredSharesView.class))).thenReturn(new AcquiredSharesViewModel());

        // when
        tested.getAcquiredShares(EMPLOYEE_ID, MONTH, YEAR);

        // then
        verify(acquiredSharesViewRepository).findByMonthAndYearAndEmployeeId(MONTH, YEAR, EMPLOYEE_ID);
        verify(stockMapper).toModel(any(AcquiredSharesView.class));
    }

    @Test
    public void shouldSuccessfullyRetrieveAcquiredSharesForAllEmployees() {
        // given
        when(acquiredSharesViewRepository.findByMonthAndYear(MONTH, YEAR))
                .thenReturn(List.of(new AcquiredSharesView()));
        when(stockMapper.toModel(any(AcquiredSharesView.class))).thenReturn(new AcquiredSharesViewModel());

        // when
        tested.getAcquiredShares(null, MONTH, YEAR);

        // then
        verify(acquiredSharesViewRepository).findByMonthAndYear(MONTH, YEAR);
        verify(stockMapper).toModel(any(AcquiredSharesView.class));
    }

    @Test
    public void shouldSuccessfullyRetrieveTotalSpentAmountsForSpecifiedEmployee() {
        // given
        when(totalSpentAmountViewRepository.findByYearAndEmployeeId(YEAR, EMPLOYEE_ID))
                .thenReturn(List.of(new TotalSpentAmountView()));
        when(stockMapper.toModel(any(TotalSpentAmountView.class))).thenReturn(new TotalSpentAmountViewModel());

        // when
        tested.getTotalSpentAmounts(EMPLOYEE_ID, YEAR);

        // then
        verify(totalSpentAmountViewRepository).findByYearAndEmployeeId(YEAR, EMPLOYEE_ID);
        verify(stockMapper).toModel(any(TotalSpentAmountView.class));
    }

    @Test
    public void shouldSuccessfullyRetrieveTotalSpentAmountsForAllEmployees() {
        // given
        when(totalSpentAmountViewRepository.findByYear(YEAR))
                .thenReturn(List.of(new TotalSpentAmountView()));
        when(stockMapper.toModel(any(TotalSpentAmountView.class))).thenReturn(new TotalSpentAmountViewModel());

        // when
        tested.getTotalSpentAmounts(null, YEAR);

        // then
        verify(totalSpentAmountViewRepository).findByYear(YEAR);
        verify(stockMapper).toModel(any(TotalSpentAmountView.class));
    }
}
