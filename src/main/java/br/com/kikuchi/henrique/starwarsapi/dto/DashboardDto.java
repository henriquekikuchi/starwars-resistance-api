package br.com.kikuchi.henrique.starwarsapi.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record DashboardDto(
        Map<String, Map<String, Double>> dashboardMap
) {
}
