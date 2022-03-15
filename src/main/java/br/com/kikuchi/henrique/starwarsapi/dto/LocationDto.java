package br.com.kikuchi.henrique.starwarsapi.dto;

public record LocationDto(
        Float latitude,
        Float longitude,
        String name
) {
}
