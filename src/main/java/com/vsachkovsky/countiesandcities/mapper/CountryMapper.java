package com.vsachkovsky.countiesandcities.mapper;

import com.vsachkovsky.countiesandcities.domain.City;
import com.vsachkovsky.countiesandcities.domain.Country;
import com.vsachkovsky.countiesandcities.exception.EntityType;
import com.vsachkovsky.countiesandcities.exception.NotFoundException;
import com.vsachkovsky.countiesandcities.model.CityDto;
import com.vsachkovsky.countiesandcities.model.CityWithFlagDto;
import com.vsachkovsky.countiesandcities.model.CountryDto;
import com.vsachkovsky.countiesandcities.model.CountryNameDto;
import org.mapstruct.Mapper;
import reactor.core.publisher.Flux;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryNameDto toCountryNameDto(Country country);

    CountryDto toCountryDto(Country country);

    CityDto toCityDto(City city);

    City toCity(CityDto cityDto);

    default CityWithFlagDto toCityWithFlagDto(Country country, String cityName) {
        return CityWithFlagDto.builder()
                .cityDto(getCityDto(country, cityName))
                .countryFlag(country.getFlag())
                .build();
    }

    private CityDto getCityDto(Country country, String cityName) {
        return toCityDto(country.getCities().stream()
                .filter(city -> city.getName()
                        .equalsIgnoreCase(cityName))
                .findFirst()
                .orElseThrow(() -> NotFoundException.notFoundByName(EntityType.CITY, cityName)));
    }

    default Flux<CityWithFlagDto> toFluxCityWithFlagDto(Country country) {
        return Flux.fromIterable(country.getCities())
                .map(city -> CityWithFlagDto.builder()
                        .cityDto(toCityDto(city))
                        .countryFlag(country.getFlag())
                        .build()
                );
    }
}
