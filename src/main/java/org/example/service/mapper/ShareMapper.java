package org.example.service.mapper;

import org.example.persistence.entity.Share;
import org.example.web.api.v1.model.ShareRegistrationModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ShareMapper {

    @Mapping(target = "id", ignore = true)
    Share toEntity(ShareRegistrationModel model);
}
