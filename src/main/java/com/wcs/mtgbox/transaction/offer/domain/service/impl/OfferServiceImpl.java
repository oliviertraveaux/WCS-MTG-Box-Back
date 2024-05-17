package com.wcs.mtgbox.transaction.offer.domain.service.impl;

import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.auth.infrastructure.exception.user.UserNotFoundErrorException;
import com.wcs.mtgbox.auth.infrastructure.repository.UserRepository;
import com.wcs.mtgbox.collection.domain.entity.UserCard;
import com.wcs.mtgbox.collection.infrastructure.exception.UserCardNotFoundErrorException;
import com.wcs.mtgbox.collection.infrastructure.repository.UserCardRepository;
import com.wcs.mtgbox.transaction.offer.domain.dto.OfferDto;
import com.wcs.mtgbox.transaction.offer.domain.entity.Offer;
import com.wcs.mtgbox.transaction.offer.domain.service.OfferService;
import com.wcs.mtgbox.transaction.offer.infrastructure.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final UserCardRepository userCardRepository;
    private final OfferMapper offerMapper;

    public OfferServiceImpl(OfferRepository offerRepository, UserRepository userRepository, UserCardRepository userCardRepository, OfferMapper offerMapper) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.userCardRepository = userCardRepository;
        this.offerMapper = offerMapper;
    }

    @Override
    public OfferDto saveOffer(OfferDto offerDto) {
        UserCard wantedUserCard = userCardRepository.findById(offerDto.getWantedUserCardId()).orElseThrow(UserCardNotFoundErrorException::new);
        List<UserCard> userCards= userCardRepository.findAllById(offerDto.getUserCardIds());
        User user = userRepository.findById(offerDto.getUserId()).orElseThrow(UserNotFoundErrorException::new);
        Offer offer = offerRepository.save(offerMapper.offerDtoToOfferEntity(user, userCards, wantedUserCard));
        return offerMapper.offerEntityToOfferDto(offer);
    }
}
