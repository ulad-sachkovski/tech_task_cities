package com.vsachkovsky.tech_task_cities.service;

import com.vsachkovsky.tech_task_cities.domain.Country;
import com.vsachkovsky.tech_task_cities.exception.EntityType;
import com.vsachkovsky.tech_task_cities.exception.NotFoundException;
import com.vsachkovsky.tech_task_cities.mapper.CountryMapper;
import com.vsachkovsky.tech_task_cities.model.CityDto;
import com.vsachkovsky.tech_task_cities.model.CityWithFlagDto;
import com.vsachkovsky.tech_task_cities.model.CountryDto;
import com.vsachkovsky.tech_task_cities.model.CountryNameDto;
import com.vsachkovsky.tech_task_cities.repository.CountryRepository;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper = Mappers.getMapper(CountryMapper.class);

    @Override
    public Mono<CountryDto> getCountry(String name) {
        return countryRepository.findByNameIgnoreCase(name)
                .map(countryMapper::toCountryDto);
    }

    @Override
    @Transactional
    public Mono<CountryDto> updateCountry(CountryDto countryDto) {
        return countryRepository.findById(countryDto.getId())
                .switchIfEmpty(Mono.error(() ->
                        NotFoundException.notFoundById(EntityType.COUNTRY, countryDto.getId())))
                .map(foundCountry -> {
                    foundCountry.setName(countryDto.getName());
                    foundCountry.setFlag(countryDto.getFlag());
                    foundCountry.setCities(countryDto.getCities()
                            .stream()
                            .map(countryMapper::toCity)
                            .toList());

                    return foundCountry;
                })
                .flatMap(countryRepository::save)
                .map(countryMapper::toCountryDto);
    }

    @Override
    public Flux<CityDto> getCitiesFromOneCountry(String country) {
        return countryRepository.findByNameIgnoreCase(country)
                .switchIfEmpty(Mono.error(() ->
                        NotFoundException.notFoundByName(EntityType.COUNTRY, country)))
                .flatMapIterable(Country::getCities)
                .map(countryMapper::toCityDto);
    }

    @Override
    public Flux<CountryNameDto> getCountryNames(Pageable pageable) {
        return countryRepository.findAllByOrderByName(pageable)
                .map(countryMapper::toCountryNameDto);
    }

    @Override
    public Flux<CityWithFlagDto> getCitiesWithFlags(Pageable pageable) {
        return countryRepository.findAll()
                .skip(pageable.getOffset())
                .take(pageable.getPageSize())
                .flatMap(countryMapper::toFluxCityWithFlagDto);

    }

    @Override
    public Mono<CityWithFlagDto> getCity(String city) {
        return countryRepository.findByCitiesNameIgnoreCase(city)
                .switchIfEmpty(Mono.error(() ->
                        NotFoundException.notFoundByName(EntityType.CITY, city)))
                .map(country -> countryMapper.toCityWithFlagDto(country, city));

    }
}
