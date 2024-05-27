package com.vsachkovsky.countiesandcities.controllers;

import com.vsachkovsky.countiesandcities.model.CountryDto;
import com.vsachkovsky.countiesandcities.model.CountryNameDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(summary = "Returns country object of exact country",
            description = "does not require authorization")
    @GetMapping("/{country}")
    ResponseEntity<Mono<CountryDto>> getCountry(@PathVariable @NotNull String country);

    @Operation(summary = "Returns paginated only country names",
            description = "does not require authorization")
    @GetMapping("/countries/names")
    ResponseEntity<Flux<CountryNameDto>> getAllCountryNames(@Valid @PositiveOrZero
                                                            @RequestParam(required = false, defaultValue = "0") final int page,
                                                            @Valid @Positive
                                                            @RequestParam(required = false, defaultValue = "2") final int size);

    @Operation(summary = "Updates country. Accepts ready country object",
            description = "require authorization and role EDITOR. " +
                    "JWT token can be obtained through postman. Please, check README file")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/countries/update")
    ResponseEntity<Mono<CountryDto>> updateCountry(@RequestBody @Validated CountryDto countryDto);

}
