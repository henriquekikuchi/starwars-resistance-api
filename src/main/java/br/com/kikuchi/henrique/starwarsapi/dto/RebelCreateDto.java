package br.com.kikuchi.henrique.starwarsapi.dto;

import br.com.kikuchi.henrique.starwarsapi.model.Gender;
import br.com.kikuchi.henrique.starwarsapi.model.Inventory;
import br.com.kikuchi.henrique.starwarsapi.model.Location;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;


public record RebelCreateDto(
        @NotNull(message = "Name field cannot be null!")
        String name,
        @Min(value = 18, message = "Age field cannot be less than 18.")
        Integer age,
        @NotNull(message = "Gender field cannot be null!")
        Gender gender,
        @NotNull(message = "Location field cannot be null!")
        LocationDto location,
        @NotNull(message = "Inventory field cannot be null!")
        List<RebelResourceDto> resources
) {
}
