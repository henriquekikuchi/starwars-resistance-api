package br.com.kikuchi.henrique.starwarsapi.controller;

import br.com.kikuchi.henrique.starwarsapi.dto.ReportDto;
import br.com.kikuchi.henrique.starwarsapi.model.Rebel;
import br.com.kikuchi.henrique.starwarsapi.model.Report;
import br.com.kikuchi.henrique.starwarsapi.service.RebelService;
import br.com.kikuchi.henrique.starwarsapi.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final RebelService rebelService;

    @PostMapping
    private ResponseEntity<String> reportRebel(@RequestBody @Valid ReportDto reportDto){
        Rebel accuser = rebelService.getById(reportDto.idAccuser());
        Rebel accused = rebelService.getById(reportDto.idAccused());
        Report report = Report.builder().accuser(accuser).accused(accused).build();
        reportService.reportRebel(report);
        return new ResponseEntity("Reported with successfully!", HttpStatus.CREATED);
    }
}
