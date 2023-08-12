package com.vsachkovsky.tech_task_cities.controllers;

import com.vsachkovsky.tech_task_cities.model.CityDto;
import com.vsachkovsky.tech_task_cities.model.CityWithFlagDto;
import com.vsachkovsky.tech_task_cities.model.CountryDto;
import com.vsachkovsky.tech_task_cities.model.CountryNameDto;
import com.vsachkovsky.tech_task_cities.service.CountryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@Slf4j
public class CountryControllerImpl implements CountryController {

    private final CountryService countryService;

    @Override
    public ResponseEntity<Flux<CityDto>> getAllCitiesByCountryName(String country) {
        log.info("Start of end-point GET/api/{country}/cities");
        Flux<CityDto> response = countryService.getCitiesFromOneCountry(country);
        log.info("End of end-point GET/api/{country}/cities");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Flux<CityWithFlagDto>> getAllCitiesWithFlags(int page, int size) {
        log.info("Start of end-point GET/api/countries-application/cities/all");
        Pageable pageable = PageRequest.of(page, size);
        Flux<CityWithFlagDto> response = countryService.getCitiesWithFlags(pageable);
        log.info("End of end-point GET/api/countries-application/cities/all");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Flux<CountryNameDto>> getAllCountryNames(int page, int size) {
        log.info("Start of end-point GET/api/countries-application/countries/names");
        Pageable pageable = PageRequest.of(page, size);
        Flux<CountryNameDto> response = countryService.getCountryNames(pageable);
        log.info("End of end-point GET/api/countries-application/countries/names");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Mono<CityWithFlagDto>> getCity(String city) {
        log.info("Start of end-point GET/api/countries-application/cities/{city}");
        Mono<CityWithFlagDto> response = countryService.getCity(city);
        log.info("End of end-point GET/api/countries-application/cities/{city}");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Mono<CountryDto>> updateCountry(CountryDto countryDto) {
        log.info("Start of end-point PUT/api/countries-application/countries/update");
        Mono<CountryDto> response = countryService.updateCountry(countryDto);
        log.info("End of end-point PUT/api/countries-application/countries/update");
        return ResponseEntity.ok(response);
    }
}
