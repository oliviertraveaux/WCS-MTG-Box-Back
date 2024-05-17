package com.wcs.mtgbox.collection.domain.service.impl;

import com.wcs.mtgbox.collection.domain.dto.CardAdDto;
import com.wcs.mtgbox.collection.domain.entity.UserCard;
import com.wcs.mtgbox.collection.domain.service.CardAdService;
import com.wcs.mtgbox.collection.infrastructure.repository.CardRepository;
import com.wcs.mtgbox.collection.infrastructure.repository.UserCardRepository;
import org.springframework.stereotype.Service;

@Service
public class CardAdServiceImpl implements CardAdService {

    private final CardRepository cardRepository;
    private final UserCardRepository userCardRepository;
    private final CardMapper cardMapper;

    public CardAdServiceImpl(CardRepository cardRepository,UserCardRepository userCardRepository,CardMapper cardMapper){
        this.cardRepository = cardRepository;
        this.userCardRepository = userCardRepository;
        this.cardMapper = cardMapper;
    }

    @Override
    public CardAdDto getCardAd(long id){
        return cardMapper.userCardToCardAdDto(userCardRepository.findById(id));
    }
}
