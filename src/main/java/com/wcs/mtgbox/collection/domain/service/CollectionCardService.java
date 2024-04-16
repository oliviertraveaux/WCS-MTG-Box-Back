package com.wcs.mtgbox.collection.domain.service;

import com.wcs.mtgbox.collection.domain.dto.AddCollectionCardDto;
import com.wcs.mtgbox.collection.domain.dto.CollectionCardDto;
import com.wcs.mtgbox.collection.domain.entity.UserCard;

import java.util.List;

public interface CollectionCardService {
    List<CollectionCardDto> saveCollectionCards(List<AddCollectionCardDto> addCardList);
    List<CollectionCardDto> getCollectionCardsByUserId(Long userId);

    void removeUserCardId(Long userCardId);

    void removeUserCardsByUserCardIds(List<Long> userCardId);
}
