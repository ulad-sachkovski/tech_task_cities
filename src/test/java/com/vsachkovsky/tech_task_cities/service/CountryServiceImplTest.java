//package com.vsachkovsky.tech_task_cities.service;
//
//import com.vsachkovsky.tech_task_cities.controllers.CountryController;
//import com.vsachkovsky.tech_task_cities.domain.City;
//import com.vsachkovsky.tech_task_cities.domain.Country;
//import com.vsachkovsky.tech_task_cities.mapper.CountryMapper;
//import com.vsachkovsky.tech_task_cities.model.CityWithFlagDto;
//import com.vsachkovsky.tech_task_cities.model.CountryDto;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import reactor.core.publisher.Mono;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//import static org.awaitility.Awaitility.await;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//public class CountryServiceImplTest {
//
//    @Autowired
//    CountryService countryService;
//
//    @Autowired
//    CountryController countryController;
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    CountryMapper countryMapper;
//
//    CountryDto countryDto;
//
//
//    public static Country getTestCountry() {
//        return Country.builder()
//                .name("Belarus")
//                .cities(new ArrayList<>(getTestCities()))
//                .build();
//    }
//
//    public static List<City> getTestCities() {
//        List<City> list = new ArrayList<>();
//        City city1 = City.builder()
//                .name("Minsk")
//                .build();
//        City city2 = City.builder()
//                .name("Mogilev")
//                .build();
//        City city3 = City.builder()
//                .name("Brest")
//                .build();
//
//        list.add(city1);
//        list.add(city2);
//        list.add(city3);
//        return list;
//    }
//
//    private CountryDto getSavedDto() {
//        countryDto = countryMapper.toCountryDto(getTestCountry());
//        return countryService.saveCountry(Mono.just(countryDto)).block();
//    }
//
//    @BeforeEach
//    void setUp() {
//        countryDto = countryMapper.toCountryDto(getTestCountry());
//    }
//
//    @Test
//    void saveCountry() {
//        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
//
//        Mono<CountryDto> savedMono = countryService.saveCountry(Mono.just(countryDto));
//
//        savedMono.subscribe(savedDto ->
//        {
//            System.out.println(savedDto.getId());
//            atomicBoolean.set(true);
//        });
//
//        await().untilTrue(atomicBoolean);
//    }
//
//    @Test
//    void findCity() {
//        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
//
//        getSavedDto();
//        Mono<CityWithFlagDto> foundMono = countryService.getCity("Minsk");
//
//        foundMono.subscribe(foundDto ->
//        {
//            System.out.println(foundDto);
//            atomicBoolean.set(true);
//        });
//
//        await().untilTrue(atomicBoolean);
//    }
//}
