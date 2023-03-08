package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.persistence.repository.AcquiredSharesViewRepository;
import org.example.persistence.repository.StockRepository;
import org.example.persistence.repository.TotalSpentAmountViewRepository;
import org.example.service.mapper.StockMapper;
import org.example.web.api.v1.model.AcquiredSharesViewModelList;
import org.example.web.api.v1.model.StockRegistrationModelList;
import org.example.web.api.v1.model.TotalSpentAmountViewModelList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final AcquiredSharesViewRepository acquiredSharesViewRepository;
    private final TotalSpentAmountViewRepository totalSpentAmountViewRepository;
    private final StockMapper stockMapper;

    public void register(StockRegistrationModelList modelList) {
        stockRepository.saveAll(modelList.getStocks()
                .stream()
                .map(stockMapper::toEntity)
                .collect(toList()));
    }

    public AcquiredSharesViewModelList getAcquiredShares(Long employeeId, Integer month, Integer year) {
        var modelList = new AcquiredSharesViewModelList();

        var acquiredShares = ofNullable(employeeId)
                .map(id -> acquiredSharesViewRepository.findByMonthAndYearAndEmployeeId(month, year, id))
                .orElseGet(() -> acquiredSharesViewRepository.findByMonthAndYear(month, year));
        modelList.setAcquiredSharesList(acquiredShares.stream()
                .map(stockMapper::toModel)
                .collect(toList()));

        return modelList;
    }

    public TotalSpentAmountViewModelList getTotalSpentAmounts(Long employeeId, Integer year) {
        var modelList = new TotalSpentAmountViewModelList();

        var totalSpentAmounts = ofNullable(employeeId)
                .map(id -> totalSpentAmountViewRepository.findByYearAndEmployeeId(year, id))
                .orElseGet(() -> totalSpentAmountViewRepository.findByYear(year));
        modelList.setTotalSpentAmountsList(totalSpentAmounts.stream()
                .map(stockMapper::toModel)
                .collect(toList()));

        return modelList;
    }

}
