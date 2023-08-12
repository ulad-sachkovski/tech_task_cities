package com.vsachkovsky.tech_task_cities.service;

import com.vsachkovsky.tech_task_cities.model.CityDto;
import com.vsachkovsky.tech_task_cities.model.CityWithFlagDto;
import com.vsachkovsky.tech_task_cities.model.CountryDto;
import com.vsachkovsky.tech_task_cities.model.CountryNameDto;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CountryService {

    Mono<CountryDto> updateCountry(CountryDto countryDto);

    Flux<CountryNameDto> getCountryNames(Pageable pageable);

    Flux<CityWithFlagDto> getCitiesWithFlags(Pageable pageable);

    Mono<CityWithFlagDto> getCity(String city);

    Flux<CityDto> getCitiesFromOneCountry(String country);

}
