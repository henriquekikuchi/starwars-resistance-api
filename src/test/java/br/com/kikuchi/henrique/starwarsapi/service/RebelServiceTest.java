package br.com.kikuchi.henrique.starwarsapi.service;

import br.com.kikuchi.henrique.starwarsapi.dto.NegotiationDto;
import br.com.kikuchi.henrique.starwarsapi.dto.NegotiationItemDto;
import br.com.kikuchi.henrique.starwarsapi.exception.*;
import br.com.kikuchi.henrique.starwarsapi.model.*;
import br.com.kikuchi.henrique.starwarsapi.repository.RebelRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;


@ExtendWith(SpringExtension.class)
@DisplayName("Testing rebel service")
class RebelServiceTest {

    @InjectMocks
    public RebelService rebelService;

    @Mock
    public RebelRepository rebelRepository;

    @Test
    @DisplayName("When the save rebel is successful")
    void saveRebelWithSuccessful(){
        var rebel = createRebel();

        Mockito.when(rebelRepository.save(rebel)).thenReturn(rebel);

        var rebelPersisted = rebelService.save(rebel);

        Assertions.assertThat(rebelPersisted).isNotNull();
    }

    @Test
    @DisplayName("Find rebel when exists")
    void findByIdRebelWithSucessful(){
        var rebel = createRebel();

        Mockito.when(rebelRepository.findById(1)).thenReturn(Optional.ofNullable(rebel));

        Assertions.assertThat(rebelService.getById(1)).isEqualTo(rebel);
    }

    @Test
    @DisplayName("Find rebel when not exists --> get a error RebelNotFoundException")
    void findByIdRebelWithUnsuccessful(){
        Mockito.when(rebelRepository.findById(anyInt())).thenThrow(new RebelNotFoundException());
        Assertions.assertThatExceptionOfType(RebelNotFoundException.class)
                .isThrownBy(() -> this.rebelService.getById(1));
    }

    @Test
    @DisplayName("Resources trading with successful")
    void resourcesNegotiationWithSuccessful(){
        Rebel rebelOne = createRebel();
        Rebel rebelTwo = createRebel();
        NegotiationItemDto send = new NegotiationItemDto(ResourcesEnum.WATER,1);
        NegotiationItemDto receive = new NegotiationItemDto(ResourcesEnum.WATER,1);
        NegotiationDto negotiationDto = new NegotiationDto(List.of(send), List.of(receive));
        rebelTwo.setName("Outro rebel");
        rebelTwo.setId(2);

        Mockito.when(rebelRepository.save(rebelOne)).thenReturn(rebelOne);
        Mockito.when(rebelRepository.save(rebelTwo)).thenReturn(rebelTwo);
        Mockito.when(rebelRepository.findById(rebelOne.getId())).thenReturn(Optional.of(rebelOne));
        Mockito.when(rebelRepository.findById(rebelTwo.getId())).thenReturn(Optional.of(rebelTwo));

        ResponseEntity<String> resp = rebelService.negotiateResources(rebelOne.getId(),rebelTwo.getId(),negotiationDto);

        Assertions.assertThat(resp.getBody()).isEqualTo("The negotiation was successfully!");
    }

    @Test
    @DisplayName("Resource trading when one of the rebels is a betrayer --> get a error BetrayerDetectedException")
    void resourcesNegotiationWhenOneOfTheRebelsIsABetrayer(){
        Rebel rebelOne = createRebel();
        Rebel rebelTwo = createRebel();
        NegotiationItemDto send = new NegotiationItemDto(ResourcesEnum.WATER,1);
        NegotiationItemDto receive = new NegotiationItemDto(ResourcesEnum.WATER,1);
        NegotiationDto negotiationDto = new NegotiationDto(List.of(send), List.of(receive));
        rebelOne.setBetrayer(true);
        rebelTwo.setName("Outro rebel");
        rebelTwo.setId(2);

        Mockito.when(rebelRepository.save(rebelOne)).thenReturn(rebelOne);
        Mockito.when(rebelRepository.save(rebelTwo)).thenReturn(rebelTwo);
        Mockito.when(rebelRepository.findById(rebelOne.getId())).thenReturn(Optional.of(rebelOne));
        Mockito.when(rebelRepository.findById(rebelTwo.getId())).thenReturn(Optional.of(rebelTwo));

        Assertions.assertThatExceptionOfType(BetrayerDetectedException.class)
                .isThrownBy(() ->
                        this.rebelService.negotiateResources(rebelOne.getId(),rebelTwo.getId(),negotiationDto));
    }

    @Test
    @DisplayName("Resource trading when both rebels has the same id --> get a error SelfNegotiationException")
    void resourcesNegotiationWhenBothRebelsHasTheSameId(){
        Rebel rebelOne = createRebel();
        Rebel rebelTwo = createRebel();
        NegotiationItemDto send = new NegotiationItemDto(ResourcesEnum.WATER,1);
        NegotiationItemDto receive = new NegotiationItemDto(ResourcesEnum.WATER,1);
        NegotiationDto negotiationDto = new NegotiationDto(List.of(send), List.of(receive));
        rebelTwo.setName("Outro rebel");

        Mockito.when(rebelRepository.save(rebelOne)).thenReturn(rebelOne);
        Mockito.when(rebelRepository.save(rebelTwo)).thenReturn(rebelTwo);
        Mockito.when(rebelRepository.findById(rebelOne.getId())).thenReturn(Optional.of(rebelOne));
        Mockito.when(rebelRepository.findById(rebelTwo.getId())).thenReturn(Optional.of(rebelTwo));

        Assertions.assertThatExceptionOfType(SelfNegotiationException.class)
                .isThrownBy(() ->
                        this.rebelService.negotiateResources(rebelOne.getId(),rebelTwo.getId(),negotiationDto));
    }

    @Test
    @DisplayName("Resource trading when the resources is not enough --> get a error RebelResourcesIsNotEnoughException")
    void resourcesNegotiationWhenResourcesIsNotEnough(){
        Rebel rebelOne = createRebel();
        Rebel rebelTwo = createRebel();
        NegotiationItemDto send = new NegotiationItemDto(ResourcesEnum.WATER,1);
        NegotiationItemDto receive = new NegotiationItemDto(ResourcesEnum.WATER,9999);
        NegotiationDto negotiationDto = new NegotiationDto(List.of(send), List.of(receive));
        rebelTwo.setId(2);
        rebelTwo.setName("Outro rebel");

        Mockito.when(rebelRepository.save(rebelOne)).thenReturn(rebelOne);
        Mockito.when(rebelRepository.save(rebelTwo)).thenReturn(rebelTwo);
        Mockito.when(rebelRepository.findById(rebelOne.getId())).thenReturn(Optional.of(rebelOne));
        Mockito.when(rebelRepository.findById(rebelTwo.getId())).thenReturn(Optional.of(rebelTwo));

        Assertions.assertThatExceptionOfType(RebelResourcesIsNotEnoughException.class)
                .isThrownBy(() ->
                        this.rebelService.negotiateResources(rebelOne.getId(),rebelTwo.getId(),negotiationDto));
    }

    @Test
    @DisplayName("Resource trading when the resources points are no equals --> get a error NegotiationResourcesPointsIsNotEqualsException")
    void resourcesNegotiationWhenResourcesPointsAreNoEquals(){
        Rebel rebelOne = createRebel();
        Rebel rebelTwo = createRebel();
        NegotiationItemDto send = new NegotiationItemDto(ResourcesEnum.WATER,2);
        NegotiationItemDto receive = new NegotiationItemDto(ResourcesEnum.WATER,1);
        NegotiationDto negotiationDto = new NegotiationDto(List.of(send), List.of(receive));
        rebelOne.getResources().get(0).setQuantity(2);
        rebelTwo.setId(2);
        rebelTwo.setName("Outro rebel");

        Mockito.when(rebelRepository.save(rebelOne)).thenReturn(rebelOne);
        Mockito.when(rebelRepository.save(rebelTwo)).thenReturn(rebelTwo);
        Mockito.when(rebelRepository.findById(rebelOne.getId())).thenReturn(Optional.of(rebelOne));
        Mockito.when(rebelRepository.findById(rebelTwo.getId())).thenReturn(Optional.of(rebelTwo));

        Assertions.assertThatExceptionOfType(NegotiationResourcesPointsIsNotEqualsException.class)
                .isThrownBy(() ->
                        this.rebelService.negotiateResources(rebelOne.getId(),rebelTwo.getId(),negotiationDto));
    }


    public Rebel createRebel(){
        Rebel rebel = Rebel.builder()
                .id(1)
                .name("Henrique")
                .age(26)
                .gender(Gender.MALE)
                .betrayer(false)
                .build();

        List<RebelResource> rebelResourceList = new ArrayList<>();

        RebelResource resource1 = RebelResource.builder()
                .resource(ResourcesEnum.WATER)
                .quantity(2)
                .rebel(rebel)
                .build();

        rebelResourceList.add(resource1);

        Location location = (Location.builder()
                .latitude(2.555f)
                .longitude(5.222f)
                .name("qualquer coisa")
                .rebel(rebel)
                .build());

        rebel.setLocation(location);
        rebel.setResources(rebelResourceList);

        return rebel;
    }
}