package com.vsachkovsky.countiesandcities.service;

import com.vsachkovsky.countiesandcities.model.CityDto;
import com.vsachkovsky.countiesandcities.model.CityWithFlagDto;
import com.vsachkovsky.countiesandcities.model.CountryDto;
import com.vsachkovsky.countiesandcities.model.CountryNameDto;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CountryService {
    Mono<CountryDto> getCountry(String name);

    Mono<CountryDto> updateCountry(CountryDto countryDto);

    Flux<CountryNameDto> getCountryNames(Pageable pageable);

    Flux<CityWithFlagDto> getCitiesWithFlags(Pageable pageable);

    Mono<CityWithFlagDto> getCity(String city);

    Flux<CityDto> getCitiesFromOneCountry(String country);
}
