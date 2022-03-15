package br.com.kikuchi.henrique.starwarsapi.dto;

import br.com.kikuchi.henrique.starwarsapi.model.ResourcesEnum;

public record RebelResourceDto(
    ResourcesEnum resource,
    Integer quantity
) { }
