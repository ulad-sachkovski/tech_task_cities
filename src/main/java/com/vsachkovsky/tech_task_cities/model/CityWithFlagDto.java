package com.vsachkovsky.tech_task_cities.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityWithFlagDto {

    private byte[] countryFlag;
    private CityDto cityDto;
}
