package com.wcs.mtgbox.transaction.offer.domain.service.impl;

import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.auth.domain.service.auth.JwtTokenService;
import com.wcs.mtgbox.auth.infrastructure.exception.user.UserNotFoundErrorException;
import com.wcs.mtgbox.auth.infrastructure.repository.UserRepository;
import com.wcs.mtgbox.collection.domain.entity.UserCard;
import com.wcs.mtgbox.collection.infrastructure.exception.UserCardNotFoundErrorException;
import com.wcs.mtgbox.collection.infrastructure.repository.UserCardRepository;
import com.wcs.mtgbox.transaction.offer.domain.dto.OfferCreationDto;
import com.wcs.mtgbox.transaction.offer.domain.dto.OfferDto;
import com.wcs.mtgbox.transaction.offer.domain.dto.OfferFullWantedCardDto;
import com.wcs.mtgbox.transaction.offer.domain.dto.OfferStatusEnum;
import com.wcs.mtgbox.transaction.offer.domain.entity.Offer;
import com.wcs.mtgbox.transaction.offer.domain.service.OfferEmailService;
import com.wcs.mtgbox.transaction.offer.domain.service.OfferService;
import com.wcs.mtgbox.transaction.offer.infrastructure.OfferRepository;
import com.wcs.mtgbox.transaction.offer.infrastructure.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final UserCardRepository userCardRepository;
    private final OfferMapper offerMapper;
    private final JwtTokenService jwtTokenService;
    private final OfferEmailService offerEmailService;


    public OfferServiceImpl(OfferRepository offerRepository,
                            UserRepository userRepository,
                            UserCardRepository userCardRepository,
                            OfferMapper offerMapper,
                            JwtTokenService jwtTokenService,
                            OfferEmailService offerEmailService
    ) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.userCardRepository = userCardRepository;
        this.offerMapper = offerMapper;
        this.jwtTokenService = jwtTokenService;
        this.offerEmailService = offerEmailService;
    }

    @Override
    public OfferCreationDto saveOffer(OfferCreationDto offerCreationDto, HttpServletRequest request) {
        Long tokenUserId = this.jwtTokenService.getUserIdFromToken(request);
        UserCard wantedUserCard = userCardRepository.findById(offerCreationDto.getWantedUserCardId()).orElseThrow(UserCardNotFoundErrorException::new);
        if (wantedUserCard.getUser().getId().equals(tokenUserId)) {
            throw new CreateOfferNotAuthorizedErrorException();
        }
        List<UserCard> userCards = userCardRepository.findAllById(offerCreationDto.getUserCardIds())
                .stream()
                .filter(userCard -> userCard.getUser().getId().equals(tokenUserId))
                .toList();
        User user = userRepository.findById(offerCreationDto.getUserId()).orElseThrow(UserNotFoundErrorException::new);
        if (!user.getId().equals(tokenUserId)) {
            throw new CreateOfferNotAuthorizedErrorException();
        }
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
    public List<OfferFullWantedCardDto> getOffersMadeByUserId(Long userId) {
        List<Offer> offers = offerRepository.findAllByUser_IdOrderByLastModificationDateDesc(userId);
        return offers.stream()
                .filter(offer -> !offer.getStatus().equals(OfferStatusEnum.VALIDATED.getFullName()))
                .map(offerMapper::offerEntityToOfferReceivedDto).toList();
    }


    @Override
    public List<OfferFullWantedCardDto> getOffersReceivedByUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundErrorException::new);
            List<Offer> offers = offerRepository.findAllByWantedUserCard_User_IdOrderByCreatedDateDesc(userId);
            return offers.stream()
                    .filter(offer -> !offer.getStatus().equals(OfferStatusEnum.VALIDATED.getFullName()))
                    .map(offerMapper::offerEntityToOfferReceivedDto).toList();
        }

    @Override
    public List<OfferFullWantedCardDto> getOffersHistoryByUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundErrorException::new);
        List<Offer> offers = offerRepository.findAllByUser_IdOrWantedUserCard_User_IdOrderByLastModificationDateDesc(userId, userId);
        return offers.stream()
                .filter(offer -> offer.getStatus().equals(OfferStatusEnum.VALIDATED.getFullName()))
                .map(offerMapper::offerEntityToOfferReceivedDto).toList();
    }

    @Override
    public void deleteOffer(Long offerId, HttpServletRequest request){
        Offer offer = offerRepository.findById(offerId).orElseThrow(OfferNotFoundErrorException::new);
        if (!isAuthorizedToDelete(request, offer.getUser().getId())
                || offer.getStatus().equals(OfferStatusEnum.VALIDATED.getFullName())) {
            throw new DeleteOfferNotAuthorizedErrorException();
        }
        offerRepository.delete(offer);
    }

    @Override
    public OfferDto acceptOffer(Long offerId, String offerStatus, HttpServletRequest request) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(OfferNotFoundErrorException::new);
        if (!isAuthorizedToAcceptOffer(offer, offerStatus, request )) {
            throw new UpdateOfferNotAuthorizedErrorException();
        }
        offer.setStatus(offerStatus);
        offer.setLastModificationDate(LocalDateTime.now());

        return offerMapper.offerEntityToOfferDto(offerRepository.save(offer));
    }

    @Override
    @Transactional
    public OfferDto validateOffer(Long offerId, String offerStatus, HttpServletRequest request) {
        try {
            Offer offer = offerRepository.findById(offerId).orElseThrow(OfferNotFoundErrorException::new);
            if (!isAuthorizedToValidateOffer(offer, offerStatus, request)) {
                throw new UpdateOfferNotAuthorizedErrorException();
            }
            offer.setStatus(offerStatus);
            offer.setLastModificationDate(LocalDateTime.now());
            offerRepository.save(offer);

            List<Offer> offersWithSameWantedCard = offerRepository.findAllByWantedUserCard_Id(offer.getWantedUserCard().getId())
                    .stream()
                    .filter(currentOffer -> !currentOffer.getStatus().equals(OfferStatusEnum.VALIDATED.getFullName()))
                    .toList();

            offerRepository.deleteAll(offersWithSameWantedCard);

            for (UserCard userCard : offer.getUserCards()) {
              UserCard userCardToChange = userCardRepository.findById(userCard.getId()).orElseThrow(UserCardNotFoundErrorException::new);
              userCardToChange.setIsActive(false);
              userCardRepository.save(userCardToChange);
              List<Offer> offersWithSameUserCard = offerRepository.findOffersByUserCardId(userCard.getId())
                      .stream()
                      .filter(offerResult -> !offerResult.getStatus().equals(OfferStatusEnum.VALIDATED.getFullName()))
                      .toList();
              if (!offersWithSameUserCard.isEmpty()) {
                  offerRepository.deleteAll(offersWithSameUserCard);
              }
            }

            UserCard wantedUserCardToChange = userCardRepository.findById(offer.getWantedUserCard().getId()).orElseThrow(UserCardNotFoundErrorException::new);
            wantedUserCardToChange.setIsActive(false);
            userCardRepository.save(wantedUserCardToChange);

            offerEmailService.sendOfferEmail(offer);

            return offerMapper.offerEntityToOfferDto(offer);
        } catch (Exception e) {
            throw new ValidateOfferErrorException(e.getMessage());
        }

    }

    @Override
    public OfferDto cancelOffer(Long offerId, String offerStatus, HttpServletRequest request) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(OfferNotFoundErrorException::new);
        if (!isAuthorizedToCancelOffer(offer, offerStatus, request )) {
            throw new UpdateOfferNotAuthorizedErrorException();
        }
        offer.setStatus(offerStatus);
        offer.setLastModificationDate(LocalDateTime.now());
        return offerMapper.offerEntityToOfferDto(offerRepository.save(offer));
    }

    private boolean isAuthorizedToDelete(HttpServletRequest request, Long id) {
        return jwtTokenService.getUserIdFromToken(request).equals(id);
    }

    private boolean isAuthorizedToAcceptOffer(Offer offer, String offerStatus, HttpServletRequest request) {
        if (offerStatus.equals(OfferStatusEnum.ACCEPTED.getFullName())) {
            return jwtTokenService.getUserIdFromToken(request).equals(offer.getWantedUserCard().getUser().getId())
                    && offer.getStatus().equals(OfferStatusEnum.PENDING.getFullName());
        }
        return false;
    }

    private boolean isAuthorizedToValidateOffer(Offer offer, String offerStatus, HttpServletRequest request) {
        if (offerStatus.equals(OfferStatusEnum.VALIDATED.getFullName())) {
            return jwtTokenService.getUserIdFromToken(request).equals(offer.getUser().getId())
                    && offer.getStatus().equals(OfferStatusEnum.ACCEPTED.getFullName());
        }
        return false;
    }

    private boolean isAuthorizedToCancelOffer(Offer offer, String offerStatus, HttpServletRequest request) {
        if (offerStatus.equals(OfferStatusEnum.PENDING.getFullName())) {
            return jwtTokenService.getUserIdFromToken(request).equals(offer.getWantedUserCard().getUser().getId())
                    && offer.getStatus().equals(OfferStatusEnum.ACCEPTED.getFullName());
        }
        return false;
    }

}
