package com.vsachkovsky.countiesandcities.repository;

import com.vsachkovsky.countiesandcities.domain.Country;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CountryRepository extends ReactiveMongoRepository<Country, String> {

    Mono<Country> findByCitiesNameIgnoreCase(String city);

    Mono<Country> findByNameIgnoreCase(String name);

    Flux<Country> findAllByOrderByName(Pageable pageable);
}
