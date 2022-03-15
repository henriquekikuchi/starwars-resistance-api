package br.com.kikuchi.henrique.starwarsapi.dto;

import br.com.kikuchi.henrique.starwarsapi.model.RebelResource;

import java.util.List;

public record InventoryDto(
    List<RebelResourceDto> resources
) {}
