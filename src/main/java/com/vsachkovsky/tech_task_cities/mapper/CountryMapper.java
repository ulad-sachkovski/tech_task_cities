package com.vsachkovsky.tech_task_cities.mapper;

import com.vsachkovsky.tech_task_cities.domain.City;
import com.vsachkovsky.tech_task_cities.domain.Country;
import com.vsachkovsky.tech_task_cities.model.CityDto;
import com.vsachkovsky.tech_task_cities.model.CityWithFlagDto;
import com.vsachkovsky.tech_task_cities.model.CountryDto;
import com.vsachkovsky.tech_task_cities.model.CountryNameDto;
import org.mapstruct.Mapper;
import reactor.core.publisher.Flux;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryNameDto toCountryNameDto(Country country);

    CountryDto toCountryDto(Country country);

    CityDto toCityDto(City city);

    City toCity(CityDto cityDto);

    default CityWithFlagDto toCityWithFlagDto(Country country) {
        return CityWithFlagDto.builder()
                .name(country.getName())
                .countryFlag(country.getFlag())
                .build();
    }

    default Flux<CityWithFlagDto> toFluxCityWithFlagDto(Country country) {
        return Flux.fromIterable(country.getCities())
                .map(city -> CityWithFlagDto.builder()
                        .name(city.getName())
                        .countryFlag(country.getFlag())
                        .build()
                );
    }
}
