package com.wcs.mtgbox.transaction.offer.domain.service.impl;

import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.auth.infrastructure.exception.user.UserNotFoundErrorException;
import com.wcs.mtgbox.auth.infrastructure.repository.UserRepository;
import com.wcs.mtgbox.collection.domain.entity.UserCard;
import com.wcs.mtgbox.collection.infrastructure.exception.UserCardNotFoundErrorException;
import com.wcs.mtgbox.collection.infrastructure.repository.UserCardRepository;
import com.wcs.mtgbox.transaction.offer.domain.dto.OfferCreationDto;
import com.wcs.mtgbox.transaction.offer.domain.dto.OfferDto;
import com.wcs.mtgbox.transaction.offer.domain.dto.OfferFullWantedCardDto;
import com.wcs.mtgbox.transaction.offer.domain.entity.Offer;
import com.wcs.mtgbox.transaction.offer.domain.service.OfferService;
import com.wcs.mtgbox.transaction.offer.infrastructure.OfferRepository;
import com.wcs.mtgbox.transaction.offer.infrastructure.exception.OfferNotFoundErrorException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final UserCardRepository userCardRepository;
    private final OfferMapper offerMapper;

    public OfferServiceImpl(OfferRepository offerRepository, UserRepository userRepository, UserCardRepository userCardRepository, OfferMapper offerMapper
    ) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.userCardRepository = userCardRepository;
        this.offerMapper = offerMapper;
    }

    @Override
    public OfferCreationDto saveOffer(OfferCreationDto offerCreationDto) {
        UserCard wantedUserCard = userCardRepository.findById(offerCreationDto.getWantedUserCardId()).orElseThrow(UserCardNotFoundErrorException::new);
        List<UserCard> userCards = userCardRepository.findAllById(offerCreationDto.getUserCardIds());
        User user = userRepository.findById(offerCreationDto.getUserId()).orElseThrow(UserNotFoundErrorException::new);
        Offer offer = offerRepository.save(offerMapper.offerCreationDtoToOfferEntity(user, userCards, wantedUserCard));
        return offerMapper.offerEntityToOfferCreationDto(offer);
    }

    @Override
    public OfferDto getOfferById(Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(OfferNotFoundErrorException::new);
        return offerMapper.offerEntityToOfferDto(offer);
    }

    @Override
    public List<OfferDto> getOffersByUserCardId(Long userCardId) {
        UserCard userCard = userCardRepository.findById(userCardId).orElseThrow(UserCardNotFoundErrorException::new);
        List<Offer> cardAdOffers = offerRepository.findAllByWantedUserCard_Id(userCard.getId());
        List<OfferDto> offerDtoList = new ArrayList<>();
        cardAdOffers.forEach(offer -> {
            OfferDto offerFulldto = offerMapper.offerEntityToOfferDto(offer);
            offerDtoList.add(offerFulldto);
        });
        return offerDtoList;
    }

    @Override
    public List<OfferFullWantedCardDto> getOffersByUserId(Long userId) {
        List<Offer> offers = offerRepository.findAllByUser_Id(userId);
        return offers.stream()
                .map(offer -> offerMapper.offerEntityToOfferReceivedDto(offer)).toList();
    }


    @Override
    public List<OfferFullWantedCardDto> getOffersReceivedByUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundErrorException::new);
            List<Offer> offers = offerRepository.findAllByWantedUserCard_User_Id(userId);
            return offers.stream()
                    .map(offer -> offerMapper.offerEntityToOfferReceivedDto(offer)).toList();
        }
}
