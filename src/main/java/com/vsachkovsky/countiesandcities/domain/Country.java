package com.vsachkovsky.countiesandcities.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Country {

    @Id
    private String id;
    @Indexed
    private String name;
    private byte[] flag;
    private List<City> cities;
}
