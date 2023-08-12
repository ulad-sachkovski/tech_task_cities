package com.vsachkovsky.tech_task_cities.repository;

import com.vsachkovsky.tech_task_cities.domain.Country;
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
