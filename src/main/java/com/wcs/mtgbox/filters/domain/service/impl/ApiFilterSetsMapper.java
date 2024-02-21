package com.wcs.mtgbox.filters.domain.service.impl;

import com.wcs.mtgbox.filters.domain.service.dto.ApiSetDTO;
import io.magicthegathering.javasdk.resource.MtgSet;
import org.springframework.stereotype.Service;

@Service
public class ApiFilterSetsMapper {
    public ApiSetDTO fromMtgSetToApiSetDto(MtgSet mtgSet) {
        return new ApiSetDTO(mtgSet.getName(), mtgSet.getCode());
    }
}