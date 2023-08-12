package com.vsachkovsky.tech_task_cities.config.data_init;

import com.vsachkovsky.tech_task_cities.domain.City;
import com.vsachkovsky.tech_task_cities.domain.Country;
import com.vsachkovsky.tech_task_cities.repository.CountryRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class InitDBData {

    private final CountryRepository countryRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initMongoDBData() {
        countryRepository.deleteAll()
                .doOnSuccess(success ->
                        loadCountryData())
                .subscribe();
    }
    private void loadCountryData() {
        countryRepository.count().subscribe(count ->
        {
            if (count == 0) {
                countryRepository.save(createPolandCountry()).subscribe();
                countryRepository.save(createBelarusCountry()).subscribe();
                countryRepository.save(createUkraineCountry()).subscribe();
            }
        });
    }

    private Country createBelarusCountry() {
        City city1 = City.builder()
                .name("Minsk")
                .build();
        City city2 = City.builder()
                .name("Mogilev")
                .build();
        City city3 = City.builder()
                .name("Grodno")
                .build();

        File file = new File("flags/Belarus.jpg");
        byte[] flag;
        try {
            flag = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Country.builder()
                .name("Belarus")
                .flag(flag)
                .cities(new ArrayList<>(List.of(city1, city2, city3)))
                .build();
    }

    private Country createUkraineCountry() {
        City city2 = City.builder()
                .name("Lvov")
                .build();
        City city1 = City.builder()
                .name("Kiev")
                .build();
        City city3 = City.builder()
                .name("Odessa")
                .build();

        File file = new File("flags/Ukraine.jpg");
        byte[] flag;
        try {
            flag = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Country.builder()
                .name("Ukraine")
                .flag(flag)
                .cities(new ArrayList<>(List.of(city1, city2, city3)))
                .build();
    }

    private Country createPolandCountry() {
        City city1 = City.builder()
                .name("Warsaw")
                .build();
        City city2 = City.builder()
                .name("Krakov")
                .build();
        City city3 = City.builder()
                .name("Gdansk")
                .build();

        File file = new File("flags/Poland.jpg");
        byte[] flag;
        try {
            flag = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Country.builder()
                .name("Poland")
                .flag(flag)
                .cities(new ArrayList<>(List.of(city1, city2, city3)))
                .build();
    }
}
