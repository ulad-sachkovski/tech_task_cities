package com.vsachkovsky.tech_task_cities.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityDto {

    @NotNull(message = "City name can not be null")
    private String name;
}
