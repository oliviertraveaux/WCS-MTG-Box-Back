package com.wcs.mtgbox.collection.domain.service.impl;

import com.wcs.mtgbox.collection.domain.dto.ApiCardDTO;
import io.magicthegathering.javasdk.resource.Card;
import org.springframework.stereotype.Service;

@Service
public class ApiCardMapper {
    public ApiCardDTO transformApiCardRawInApiCardDto(Card apiCardRaw) {
        ApiCardDTO apiCardDTO = new ApiCardDTO();
        apiCardDTO.setCardIdApi(apiCardRaw.getId());
        apiCardDTO.setName(apiCardRaw.getName());
        apiCardDTO.setForeignNames(apiCardRaw.getForeignNames());
        apiCardDTO.setImageUrl(apiCardRaw.getImageUrl());
        apiCardDTO.setManaCost(apiCardRaw.getManaCost());
        apiCardDTO.setRarity(apiCardRaw.getRarity());
        apiCardDTO.setSet(apiCardRaw.getSet());
        apiCardDTO.setSetName(apiCardRaw.getSetName());
        apiCardDTO.setArtist(apiCardRaw.getArtist());
        apiCardDTO.setText(apiCardRaw.getText());

        return apiCardDTO;
    }
}
