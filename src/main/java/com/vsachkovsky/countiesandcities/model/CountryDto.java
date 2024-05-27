package com.vsachkovsky.countiesandcities.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Country id can not by null")
    private String id;
    @NotNull(message = "Country name can not by null")
    private String name;
    @NotNull(message = "Country flag can not by null")
    private byte[] flag;
    @NotEmpty(message = "Country must contain at least one city")
    private List<CityDto> cities;
}
