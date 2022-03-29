package br.com.kikuchi.henrique.starwarsapi.controller;

import br.com.kikuchi.henrique.starwarsapi.dto.LocationDto;
import br.com.kikuchi.henrique.starwarsapi.dto.NegotiationDto;
import br.com.kikuchi.henrique.starwarsapi.dto.RebelCreateDto;
import br.com.kikuchi.henrique.starwarsapi.mapper.LocationMapper;
import br.com.kikuchi.henrique.starwarsapi.mapper.RebelMapper;
import br.com.kikuchi.henrique.starwarsapi.model.Location;
import br.com.kikuchi.henrique.starwarsapi.model.Rebel;
import br.com.kikuchi.henrique.starwarsapi.model.RebelResource;
import br.com.kikuchi.henrique.starwarsapi.service.RebelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rebels")
@RequiredArgsConstructor
public class RebelController {

    private final RebelService rebelService;

    @GetMapping
    private ResponseEntity<Page<Rebel>> getAll(Pageable pageable){
        Page<Rebel> rebelList = rebelService.getAll(pageable);
        return ResponseEntity.ok(rebelList);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Rebel> getById(@PathVariable("id") Integer id){
        Rebel rebel = rebelService.getById(id);
        return ResponseEntity.ok(rebel);
    }

    @PostMapping
    private ResponseEntity<Rebel> save(@RequestBody @Valid RebelCreateDto rebelCreateDto){
        Rebel newRebel = RebelMapper.INSTANCE.rebelCreateDtoToRebel(rebelCreateDto);
        List<RebelResource> resources = newRebel.getResources().stream()
                .peek(rebelResource -> rebelResource.setRebel(newRebel))
                .collect(Collectors.groupingBy(RebelResource::getResource, Collectors.reducing((a, b) -> {
                    if (!a.getResource().equals(b.getResource())) return b;
                    a.setQuantity(a.getQuantity() + b.getQuantity());
                    return a;
                })))
                .values()
                .stream()
                .map(opt -> opt.orElse(null))
                .toList();


        Location location = LocationMapper.INSTANCE.locationDtoToLocation(rebelCreateDto.location());
        newRebel.setResources(resources);
        newRebel.setLocation(location);

        Rebel rebelAdded = rebelService.save(newRebel);
        return ResponseEntity.ok(rebelAdded);
    }

    @PatchMapping("/{id}/location")
    private ResponseEntity<String> updateLocation(
            @PathVariable("id") Integer id,
            @RequestBody @Valid LocationDto locationDto){
        Location updatedLocation = LocationMapper.INSTANCE.locationDtoToLocation(locationDto);
        return rebelService.updateLocation(id, updatedLocation);
    }

    @PostMapping("/{id}/negotiate/{idOtherRebel}")
    private ResponseEntity<String> tradeItens(
            @PathVariable("id") Integer id,
            @PathVariable("idOtherRebel") Integer idOtherRebel,
            @RequestBody @Valid NegotiationDto negotiationDto) {
        return rebelService.negotiateResources(id,idOtherRebel,negotiationDto);
    }
}
