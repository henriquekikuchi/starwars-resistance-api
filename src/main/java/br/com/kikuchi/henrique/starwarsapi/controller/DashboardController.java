package br.com.kikuchi.henrique.starwarsapi.controller;

import br.com.kikuchi.henrique.starwarsapi.dto.DashboardDto;
import br.com.kikuchi.henrique.starwarsapi.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    private ResponseEntity<DashboardDto> getAll(){
        return ResponseEntity.ok(dashboardService.getAll());
    }
}
