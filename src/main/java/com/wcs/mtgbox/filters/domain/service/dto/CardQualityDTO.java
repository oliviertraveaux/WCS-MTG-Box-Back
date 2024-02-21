package com.wcs.mtgbox.filters.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardQualityDTO {
    private Long id;
    private String name;
    private String abbreviation;
}
