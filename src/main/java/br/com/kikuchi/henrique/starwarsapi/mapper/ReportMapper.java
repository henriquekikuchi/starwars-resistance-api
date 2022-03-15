package br.com.kikuchi.henrique.starwarsapi.mapper;

import br.com.kikuchi.henrique.starwarsapi.dto.ReportDto;
import br.com.kikuchi.henrique.starwarsapi.model.Report;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReportMapper {

    ReportMapper INSTANCE = Mappers.getMapper(ReportMapper.class);

    Report reportDtoToReport(ReportDto reportDto);
    ReportDto reportToReportDto(Report report);
}
