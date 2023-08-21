package com.vsachkovsky.tech_task_cities.config.data_init;

import com.vsachkovsky.tech_task_cities.domain.City;
import com.vsachkovsky.tech_task_cities.domain.Country;
import com.vsachkovsky.tech_task_cities.repository.CountryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Component
@AllArgsConstructor
@Slf4j
public class InitDBData {

    private final CountryRepository countryRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initMongoDBData() {
        countryRepository.deleteAll()
                .doOnSuccess(success ->
                        loadCountryData())
                .subscribe();
    }

    private Map<String, String> getCountryCodes() {
        Map<String, String> countryCodes = new HashMap<>();
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            countryCodes.put(l.getDisplayCountry(), iso);
        }
        return countryCodes;
    }

    private void loadCountryData() {
        Map<String, String> countryCodes = getCountryCodes();

        countryRepository.count().subscribe(count ->
        {
            if (count == 0) {

                JSONParser jsonParser = new JSONParser();
                try (FileReader reader = new FileReader("documents/countries.json")) {
                    Object obj = jsonParser.parse(reader);
                    JSONObject countryJSON = (JSONObject) obj;

                    for (Object key : countryJSON.keySet()) {
                        String countryName = (String) key;
                        JSONArray citiesJSON = (JSONArray) countryJSON.get(countryName);
                        Iterator iterator = citiesJSON.iterator();
                        ArrayList<City> cities = new ArrayList<>();
                        while (iterator.hasNext()) {
                            cities.add(City.builder()
                                    .name((String) iterator.next())
                                    .build());
                        }

                        String shortName = countryCodes.get(countryName);
                        String link = String.format("https://flagcdn.com/16x12/%s.png", shortName.toLowerCase(Locale.ROOT));
                        URL url = new URL(link);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(5000);
                        connection.setReadTimeout(5000);

                        byte[] countryFlag = connection.getInputStream().readAllBytes();
                        connection.disconnect();

                        countryRepository.save(Country.builder()
                                .name(countryName)
                                .flag(countryFlag)
                                .cities(cities)
                                .build()).subscribe();
                    }
                } catch (IOException | ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        log.info("Database was successfully loaded");
    }
}
