package br.com.kikuchi.henrique.starwarsapi.service;

import br.com.kikuchi.henrique.starwarsapi.dto.DashboardDto;
import br.com.kikuchi.henrique.starwarsapi.model.Rebel;
import br.com.kikuchi.henrique.starwarsapi.model.RebelResource;
import br.com.kikuchi.henrique.starwarsapi.repository.RebelRepository;
import br.com.kikuchi.henrique.starwarsapi.repository.RebelResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final RebelRepository rebelRepository;
    private final RebelResourceRepository rebelResourceRepository;

    public DashboardDto getAll(){
        Map<String, Map<String, Double>> map = new HashMap<>();
        map.putAll(getPercentageOfRebelsAndBetrayers());
        map.putAll(getLostPoints());
        map.putAll(getRebelResourcesMean());

        return new DashboardDto(map);
    }

    // Porcentagem de rebeldes e traidores
    public  Map<String, Map<String, Double>> getPercentageOfRebelsAndBetrayers() {
        List<Rebel> rebelList = getRebelsAndBetrayers();
        Map<String, Double> percents = rebelList.stream()
                .collect(Collectors
                        .groupingBy(rebel -> rebel.isBetrayer() ? "Betrayer" : "Rebel",
                                Collectors.collectingAndThen(
                                        Collectors.counting(),
                                        count -> (double) count / (double) rebelList.size()))
                );
        Map<String, Map<String, Double>> finalData = new HashMap();
        finalData.put("Percentage Of Rebels And Betrayers", percents);
        return finalData;
    }

    //Media de cada recurso de rebeldes
    private Map<String, Map<String, Double>> getRebelResourcesMean() {
        List<RebelResource> rebelResourceList = getResources();
        Map<String, Double> resourcesMean = getRebels().stream()
                .flatMap(rebel -> rebel.getResources().stream())
                .collect(Collectors
                        .groupingBy(rebelResource -> rebelResource.getResource().getDescribe(),
                                Collectors.averagingInt(RebelResource::getQuantity)));
        Map<String, Map<String, Double>> finalData = new HashMap();
        finalData.put("Mean Resources Per Rebel", resourcesMean);
        return finalData;
    }

    // Pontos perdidos por causa dos traidores
    private Map<String, Map<String, Double>> getLostPoints(){
        Map<String, Double> map = new HashMap<>();
        Double sumPoints = getBetrayers().stream()
                .flatMap(resources -> resources.getResources().stream())
                .mapToDouble(rebelResource -> rebelResource.getQuantity() * rebelResource.getResource().getPoints())
                .sum();
        map.put("Because Of Betrayers", sumPoints);
        Map<String, Map<String, Double>> finalData = new HashMap<>();
        finalData.put("Lost Points", map);
        return finalData;
    }

    private List<Rebel> getRebelsAndBetrayers() {
        return rebelRepository.findAll();
    }

    private List<Rebel> getBetrayers() {
        return rebelRepository.findAllByBetrayerIsTrue();
    }

    private List<Rebel> getRebels() {
        return rebelRepository.findAllByBetrayerIsFalse();
    }

    private List<RebelResource> getResources() {
        return rebelResourceRepository.findAll();
    }
}
