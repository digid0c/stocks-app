package org.example.service.mapper;

import org.example.persistence.entity.Stock;
import org.example.web.api.v1.model.StockRegistrationModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StockMapper {

    @Mapping(target = "id", ignore = true)
    Stock toEntity(StockRegistrationModel model);
}
