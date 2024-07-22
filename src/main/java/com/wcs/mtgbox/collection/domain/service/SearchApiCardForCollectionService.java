package com.wcs.mtgbox.collection.domain.service;

import com.wcs.mtgbox.collection.domain.dto.ApiCardDTO;

import java.util.List;
import java.util.Map;

public interface SearchApiCardForCollectionService {

    List<ApiCardDTO> getApiCardsAndFormat(Map<String,String> filtersList);

    List<String> getAllApiCardTypes();
}
