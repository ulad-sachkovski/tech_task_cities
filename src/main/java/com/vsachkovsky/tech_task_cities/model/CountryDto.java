package com.vsachkovsky.tech_task_cities.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CountryDto {

    private String id;
    private String name;
    private byte[] flag;
    private List<CityDto> cities;
}
