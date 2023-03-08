package org.example.service.mapper;

import org.example.persistence.entity.Stock;
import org.example.persistence.view.AcquiredSharesView;
import org.example.persistence.view.TotalSpentAmountView;
import org.example.web.api.v1.model.AcquiredSharesViewModel;
import org.example.web.api.v1.model.StockRegistrationModel;
import org.example.web.api.v1.model.TotalSpentAmountViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StockMapper {

    @Mapping(target = "id", ignore = true)
    Stock toEntity(StockRegistrationModel model);

    AcquiredSharesViewModel toModel(AcquiredSharesView entity);

    TotalSpentAmountViewModel toModel(TotalSpentAmountView entity);
}
