package com.vsachkovsky.countiesandcities.controllers;

import com.vsachkovsky.countiesandcities.model.CityDto;
import com.vsachkovsky.countiesandcities.model.CityWithFlagDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("api/countries-application/v1")
public interface CityController {

    @GetMapping("/{country}/cities")
    @Operation(summary = "Get all cities of exact country",
            description = "does not require authorization")
    ResponseEntity<Flux<CityDto>> getAllCitiesByCountryName(@PathVariable @NotNull String country);

    @GetMapping("/cities/all")
    @Operation(summary = "Get paginated cities with country flag",
            description = "does not require authorization")
    ResponseEntity<Flux<CityWithFlagDto>> getAllCitiesWithFlags(@Valid @PositiveOrZero
                                                                @RequestParam(required = false, defaultValue = "0") final int page,
                                                                @Valid @Positive
                                                                @RequestParam(required = false, defaultValue = "2") final int size);

    @Operation(summary = "Returns country flag and city object of exact city",
            description = "does not require authorization")
    @GetMapping("/cities/{city}")
    ResponseEntity<Mono<CityWithFlagDto>> getCity(@PathVariable @NotNull String city);

}
