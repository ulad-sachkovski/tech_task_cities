package com.vsachkovsky.countiesandcities.controller;

import com.vsachkovsky.countiesandcities.domain.City;
import com.vsachkovsky.countiesandcities.domain.Country;
import com.vsachkovsky.countiesandcities.mapper.CountryMapper;
import com.vsachkovsky.countiesandcities.model.CityDto;
import com.vsachkovsky.countiesandcities.model.CityWithFlagDto;
import com.vsachkovsky.countiesandcities.model.CountryDto;
import com.vsachkovsky.countiesandcities.model.CountryNameDto;
import com.vsachkovsky.countiesandcities.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryControllerImplTest {

    @Autowired
    private CountryMapper countryMapper;
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private CountryRepository countryRepository;

    @Test
    void getCountry() {
        String countryName = "poland";

        countryRepository.findByNameIgnoreCase(countryName)
                .map(countryMapper::toCountryDto)
                .subscribe(expected ->
                        webTestClient.get()
                                .uri("api/countries-application/" + countryName)
                                .exchange()
                                .expectAll(
                                        responseSpec -> responseSpec.expectStatus()
                                                .isOk(),
                                        responseSpec -> responseSpec.expectBody(CountryDto.class)
                                                .isEqualTo(expected)
                                ));
    }

    @Test
    void getAllCitiesByCountryName() {
        String countryName = "ukraine";

        countryRepository.findByNameIgnoreCase(countryName)
                .flatMapIterable(Country::getCities)
                .map(countryMapper::toCityDto)
                .collectList()
                .subscribe(expected ->
                        webTestClient.get()
                                .uri("api/countries-application/" + countryName + "/cities")
                                .exchange()
                                .expectAll(
                                        responseSpec -> responseSpec.expectStatus()
                                                .isOk(),
                                        responseSpec -> responseSpec.expectBodyList(CityDto.class)
                                                .isEqualTo(expected)
                                ));
    }

    @Test
    void getAllCitiesWithFlags() {
        int page = 0;
        int size = 3;

        Pageable pageable = PageRequest.of(page, size);

        countryRepository.findAll()
                .skip(pageable.getOffset())
                .take(pageable.getPageSize())
                .flatMap(countryMapper::toFluxCityWithFlagDto)
                .collectList()
                .subscribe(expected ->
                        webTestClient.get()
                                .uri("api/countries-application/cities/all")
                                .attribute("page", page)
                                .attribute("size", size)
                                .exchange().expectAll(
                                        responseSpec -> responseSpec.expectStatus()
                                                .isOk(),
                                        responseSpec -> responseSpec.expectBodyList(CityWithFlagDto.class)
                                                .isEqualTo(expected)
                                ));
    }

    @Test
    void getAllCountryNames() {
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);

        countryRepository.findAllByOrderByName(pageable)
                .map(countryMapper::toCountryNameDto)
                .collectList()
                .subscribe(expected ->
                        webTestClient.get()
                                .uri("api/countries-application/countries/names")
                                .attribute("page", page)
                                .attribute("size", size)
                                .exchange().expectAll(
                                        responseSpec -> responseSpec.expectStatus()
                                                .isOk(),
                                        responseSpec -> responseSpec.expectBodyList(CountryNameDto.class)
                                                .isEqualTo(expected)
                                ));
    }

    @Test
    void getCity() {
        String cityName = "Kiev";

        countryRepository.findByCitiesNameIgnoreCase(cityName)
                .map(country -> countryMapper.toCityWithFlagDto(country, cityName))
                .subscribe(expected ->
                        webTestClient.get()
                                .uri("api/countries-application/cities/" + cityName)
                                .exchange().expectAll(
                                        responseSpec -> responseSpec.expectStatus()
                                                .isOk(),
                                        responseSpec -> responseSpec.expectBody(CityWithFlagDto.class)
                                                .isEqualTo(expected)
                                ));
    }

    @Test
    void updateCountry() {
        String countryName = "belarus";

        countryRepository.findByNameIgnoreCase(countryName)
                .subscribe(result -> {
                    result.getCities().add(City.builder()
                            .name("Senno")
                            .build());

                    CountryDto requestDto = countryMapper.toCountryDto(result);
                    countryRepository.save(result).subscribe();
                    CountryDto expectedDto = countryMapper.toCountryDto(result);

                    webTestClient.put()
                            .uri("api/countries-application/countries/update")
                            .bodyValue(requestDto)
                            .exchange().expectAll(
                                    responseSpec -> responseSpec.expectStatus()
                                            .isOk(),
                                    responseSpec -> responseSpec.expectBody(CountryDto.class)
                                            .isEqualTo(expectedDto)
                            );
                });
    }

}
