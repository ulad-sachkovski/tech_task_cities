package com.vsachkovsky.tech_task_cities.controllers;

import com.vsachkovsky.tech_task_cities.model.CityDto;
import com.vsachkovsky.tech_task_cities.model.CityWithFlagDto;
import com.vsachkovsky.tech_task_cities.model.CountryDto;
import com.vsachkovsky.tech_task_cities.model.CountryNameDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("api/countries-application/v1")
public interface CountryController {

    @GetMapping("/{country}")
    ResponseEntity<Mono<CountryDto>> getCountry(@PathVariable @NotNull String country);




    @GetMapping("/countries/names")
    ResponseEntity<Flux<CountryNameDto>> getAllCountryNames(@Valid @PositiveOrZero
                                                            @RequestParam(required = false, defaultValue = "0") final int page,
                                                            @Valid @Positive
                                                            @RequestParam(required = false, defaultValue = "2") final int size);



    @PutMapping("/countries/update")
    ResponseEntity<Mono<CountryDto>> updateCountry(@RequestBody @Validated CountryDto countryDto);

}
