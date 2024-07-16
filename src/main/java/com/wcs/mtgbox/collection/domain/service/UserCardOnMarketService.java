package com.wcs.mtgbox.collection.domain.service;

import com.wcs.mtgbox.collection.domain.dto.UserCardOnMarketSearchResultDto;

import java.util.List;
import java.util.Map;

public interface UserCardOnMarketService {

    List<UserCardOnMarketSearchResultDto> getMarketCards(Map<String,String> allParams);

    List<UserCardOnMarketSearchResultDto> getLastMarketCards(int limit);
}
