package com.wcs.mtgbox.collection.domain.dto;

import com.wcs.mtgbox.collection.domain.model.CardBasicInfo;
import com.wcs.mtgbox.collection.domain.model.AddCardUserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddCollectionCardDto {
    private CardBasicInfo cardInfo;
    private AddCardUserInfo userInfo;

}
