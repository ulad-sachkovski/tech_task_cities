package com.vsachkovsky.countiesandcities.controllers;

import com.vsachkovsky.countiesandcities.model.CityDto;
import com.vsachkovsky.countiesandcities.model.CityWithFlagDto;
import com.vsachkovsky.countiesandcities.service.CountryService;
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
public class CityControllerImpl implements CityController {

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
    public ResponseEntity<Mono<CityWithFlagDto>> getCity(String city) {
        log.info("Start of end-point GET/api/countries-application/cities/{city}");
        Mono<CityWithFlagDto> response = countryService.getCity(city);
        log.info("End of end-point GET/api/countries-application/cities/{city}");
        return ResponseEntity.ok(response);
    }
}
