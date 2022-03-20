package br.com.kikuchi.henrique.starwarsapi.service;

import br.com.kikuchi.henrique.starwarsapi.dto.NegotiationDto;
import br.com.kikuchi.henrique.starwarsapi.dto.NegotiationItemDto;
import br.com.kikuchi.henrique.starwarsapi.exception.*;
import br.com.kikuchi.henrique.starwarsapi.model.Location;
import br.com.kikuchi.henrique.starwarsapi.model.Rebel;
import br.com.kikuchi.henrique.starwarsapi.model.RebelResource;
import br.com.kikuchi.henrique.starwarsapi.repository.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RebelService {

    private final RebelRepository rebelRepository;

    public List<Rebel> getAll() {
        return rebelRepository.findAll();
    }

    public Rebel getById(Integer id) {
        return rebelRepository.findById(id).orElseThrow(RebelNotFoundException::new);
    }

    public Rebel save(Rebel rebel) {
        return rebelRepository.save(rebel);
    }

    public ResponseEntity<String> updateLocation(Integer id, Location location) {
        Rebel rebel = getById(id);
        location.setId(rebel.getLocation().getId());
        rebel.setLocation(location);
        rebelRepository.save(rebel);
        return new ResponseEntity<String>("Location updated with successfully", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<String> negotiateResources(Integer meId, Integer otherId, NegotiationDto negotiationDto) {
        if (meId.equals(otherId)) throw new SelfNegotiationException();

        Rebel me = getById(meId);
        Rebel other = getById(otherId);

        if(me.isBetrayer() || other.isBetrayer()) throw new BetrayerDetectedException();

        if (!isBothNegotiationPointsEquals(negotiationDto)) throw new NegotiationResourcesPointsIsNotEqualsException();

        me = updateResources(me, negotiationDto.send(), negotiationDto.receive());
        other = updateResources(other, negotiationDto.receive(), negotiationDto.send());

        rebelRepository.save(me);
        rebelRepository.save(other);

        return new ResponseEntity<String>("The negotiation was successfully!", HttpStatus.OK);
    }

    private Rebel updateResources(Rebel rebel, List<NegotiationItemDto> send, List<NegotiationItemDto> receive) {
        rebel.setResources(deductItensFromResources(send, rebel));
        rebel.setResources(addReceivedItensToResources(receive, rebel));
        return rebel;
    }

    private boolean isBothNegotiationPointsEquals(NegotiationDto negotiationDto) {
        int a = negotiationDto.send().stream().mapToInt(item -> item.quantity() * item.resource().getPoints()).sum();
        int b = negotiationDto.receive().stream().mapToInt(item -> item.quantity() * item.resource().getPoints()).sum();
        return a == b;
    }

    private List<RebelResource>  addReceivedItensToResources(List<NegotiationItemDto> receiveList, Rebel rebel) {
        return  rebel.getResources().stream()
                .map(resource -> {
                    var itemDto = receiveList.stream()
                            .filter(it -> it.resource().equals(resource.getResource()))
                            .findFirst();
                    if (itemDto.isEmpty()){
                        return resource;
                    } else {
                        NegotiationItemDto negotiationItemDto = itemDto.get();
                        resource.setQuantity(resource.getQuantity() + negotiationItemDto.quantity());
                        return resource;
                    }
                }).collect(Collectors.toList());
    }

    private List<RebelResource> deductItensFromResources(List<NegotiationItemDto> itemList, Rebel rebel) {
        return rebel.getResources().stream().map(resource -> {
            var item = itemList.stream()
                    .filter(it -> resource.getResource().equals(it.resource()))
                    .findFirst();
            if (item.isEmpty()) return resource;
            if (item.get().quantity() > resource.getQuantity()) throw new RebelResourcesIsNotEnoughException();
            resource.setQuantity(resource.getQuantity() - item.get().quantity());
            return resource;
        }).collect(Collectors.toList());
    }

}

