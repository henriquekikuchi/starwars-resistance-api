package br.com.kikuchi.henrique.starwarsapi.service;

import br.com.kikuchi.henrique.starwarsapi.exception.RebelNotFoundException;
import br.com.kikuchi.henrique.starwarsapi.exception.ReportAlreadyExistsException;
import br.com.kikuchi.henrique.starwarsapi.exception.SelfReportException;
import br.com.kikuchi.henrique.starwarsapi.model.Report;
import br.com.kikuchi.henrique.starwarsapi.repository.ReportRepository;
import br.com.kikuchi.henrique.starwarsapi.util.RebelUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(SpringExtension.class)
@DisplayName("Testing report service")
class ReportServiceTest {

    @InjectMocks
    public ReportService reportService;

    @Mock
    public ReportRepository reportRepository;

    @Mock
    public RebelService rebelService;


    @Test
    @DisplayName("When reports is ok")
    void whenReportWithSuccessul(){
        var rebelList = RebelUtils.getRebelList();
        var rebel1 = rebelList.get(0);
        var rebel2 = rebelList.get(1);

        rebel1.setId(1);
        rebel2.setId(2);

        var report = Report.builder().accuser(rebel1).accused(rebel2).build();

        Mockito.when(reportRepository
                        .existsByAccuserIdAndAccusedId(report.getAccuser().getId(), report.getAccused().getId()))
                .thenReturn(false);
        Mockito.when(reportRepository.save(report)).thenReturn(report);
        Mockito.when(rebelService.save(rebel2)).thenReturn(rebel2);
        Mockito.when(rebelService.getById(report.getAccused().getId())).thenReturn(rebel2);

        var resp = reportService.reportRebel(report);

        Assertions.assertThat(resp).isEqualTo(report);
    }

    @Test
    @DisplayName("When the rebel is reported equals or more than to three times, it is defined as a betrayer")
    void whenTheRebelIsReportedThreeTimesIsDefinedAsABetrayer(){
        var rebelList = RebelUtils.getRebelList();
        var rebel1 = rebelList.get(0);
        var rebel2 = rebelList.get(1);

        rebel2.setReportedList(List.of(
                Report.builder()
                        .accuser(rebelList.get(2))
                        .accused(rebel2)
                        .build(),
                Report.builder()
                        .accuser(rebelList.get(3))
                        .accused(rebel2)
                        .build())
        );

        var report = Report.builder().accuser(rebel1).accused(rebel2).build();
        ArrayList<Report> newReportList = new ArrayList<>(rebel2.getReportedList());
        newReportList.add(report);
        rebel2.setReportedList(newReportList);


        Mockito.when(reportRepository
                        .existsByAccuserIdAndAccusedId(report.getAccuser().getId(), report.getAccused().getId()))
                .thenReturn(false);
        Mockito.when(reportRepository.save(report)).thenReturn(report);
        Mockito.when(rebelService.getById(report.getAccused().getId())).thenReturn(rebel2);

        var resp = reportService.reportRebel(report);

        Assertions.assertThat(resp.getAccused().isBetrayer()).isTrue();
    }

    @Test
    @DisplayName("When report is self report -> give a error SelfReportException")
    void whenReportAccuserAndAccusedIsEqualsGiveAError(){
        var rebelList = RebelUtils.getRebelList();
        var rebel1 = rebelList.get(0);
        var rebel2 = rebelList.get(0);

        rebel1.setId(1);
        rebel2.setId(1);

        var report = Report.builder().accuser(rebel1).accused(rebel2).build();

        Assertions.assertThatExceptionOfType(SelfReportException.class)
                .isThrownBy(() -> this.reportService.reportRebel(report));
    }

    @Test
    @DisplayName("When report is self report -> give a error ReportAlreadyExistsException")
    void whenReportAlreadyExistsGiveAError(){
        var rebelList = RebelUtils.getRebelList();
        var rebel1 = rebelList.get(0);
        var rebel2 = rebelList.get(1);

        rebel1.setId(1);
        rebel2.setId(2);

        var report = Report.builder().accuser(rebel1).accused(rebel2).build();

        Mockito.when(reportRepository
                        .existsByAccuserIdAndAccusedId(report.getAccuser().getId(), report.getAccused().getId()))
                .thenReturn(true);

        Assertions.assertThatExceptionOfType(ReportAlreadyExistsException.class)
                .isThrownBy(() -> this.reportService.reportRebel(report));
    }


}