package br.com.kikuchi.henrique.starwarsapi.dto;

import java.util.List;

public record DashboardItemDto(
        String name,
        List<Double> data
) {}
