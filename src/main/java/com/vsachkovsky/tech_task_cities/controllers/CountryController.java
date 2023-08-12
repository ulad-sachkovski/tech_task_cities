package com.vsachkovsky.tech_task_cities.controllers;

import com.vsachkovsky.tech_task_cities.model.CityDto;
import com.vsachkovsky.tech_task_cities.model.CityWithFlagDto;
import com.vsachkovsky.tech_task_cities.model.CountryDto;
import com.vsachkovsky.tech_task_cities.model.CountryNameDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("api/countries-application")
public interface CountryController {

    @GetMapping(value = "/{country}/cities", produces = MediaType.APPLICATION_NDJSON_VALUE)
    ResponseEntity<Flux<CityDto>> getAllCitiesByCountryName(@PathVariable String country);

    @GetMapping(value = "/cities/all", produces = MediaType.APPLICATION_NDJSON_VALUE)
    ResponseEntity<Flux<CityWithFlagDto>> getAllCitiesWithFlags(@Valid @PositiveOrZero
                                                                @RequestParam(required = false, defaultValue = "0") final int page,
                                                                @Valid @Positive
                                                                @RequestParam(required = false, defaultValue = "4") final int size);

    @GetMapping(value = "/countries/names", produces = MediaType.APPLICATION_NDJSON_VALUE)
    ResponseEntity<Flux<CountryNameDto>> getAllCountryNames(@Valid @PositiveOrZero
                                                            @RequestParam(required = false, defaultValue = "0") final int page,
                                                            @Valid @Positive
                                                            @RequestParam(required = false, defaultValue = "2") final int size);

    @GetMapping("/cities/{city}")
    ResponseEntity<Mono<CityWithFlagDto>> getCity(@PathVariable String city);

    @PutMapping("/countries/update")
    ResponseEntity<Mono<CountryDto>> updateCountry(@RequestBody CountryDto countryDto);

}
