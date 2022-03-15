package br.com.kikuchi.henrique.starwarsapi.dto;

public record ReportViewDto(
        Integer id,
        String accuserName,
        String accusedName
){
}
