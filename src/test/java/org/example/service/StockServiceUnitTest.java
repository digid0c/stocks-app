package org.example.service;

import org.example.persistence.entity.Stock;
import org.example.persistence.repository.StockRepository;
import org.example.service.mapper.StockMapper;
import org.example.web.api.v1.model.StockRegistrationModel;
import org.example.web.api.v1.model.StockRegistrationModelList;
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

    @Mock
    private StockRepository stockRepository;

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
}
