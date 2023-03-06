package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.persistence.repository.StockRepository;
import org.example.service.mapper.StockMapper;
import org.example.web.api.v1.model.StockRegistrationModelList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    public void register(StockRegistrationModelList modelList) {
        stockRepository.saveAll(modelList.getStocks()
                .stream()
                .map(stockMapper::toEntity)
                .collect(toList()));
    }
}
