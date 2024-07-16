package com.wcs.mtgbox.transaction.offer.domain.dto;

import com.wcs.mtgbox.collection.domain.dto.CollectionCardDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OfferDto {
    private Long id;
    private Long wantedUserCardId;
    private Long userId;
    private String userName;
    private String city;
    private int department;
    private String status;
    private LocalDateTime createdDate;
    private LocalDateTime lastModificationDate;
    private List<CollectionCardDto> userCards;
}
