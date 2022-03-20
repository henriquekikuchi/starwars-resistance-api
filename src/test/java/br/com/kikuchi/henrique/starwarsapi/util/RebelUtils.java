package br.com.kikuchi.henrique.starwarsapi.util;

import br.com.kikuchi.henrique.starwarsapi.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class RebelUtils {


    public static List<Rebel> getRebelList(){
        Rebel rebel1 = createRebel("PAM BEESLY",Gender.FEMALE,37);
        Rebel rebel2 = createRebel("DWIGHT SCHURUTE", Gender.MALE, 41);
        Rebel rebel3 = createRebel("JIM HALPERT", Gender.MALE, 38);
        Rebel rebel4 = createRebel("ANGELA NOELLE", Gender.FEMALE, 43);
        Rebel rebel5 = createRebel("PHYLLIS VANCE", Gender.FEMALE, 56);
        Rebel rebel6 = createRebel("KEVIN MALONE", Gender.MALE, 54);

        rebel1.setLocation(createLocation("Dunder Mifflin",2.555f, 5.555f, rebel1));
        rebel2.setLocation(createLocation("Dunder Mifflin",2.555f, 5.555f, rebel2));
        rebel3.setLocation(createLocation("Dunder Mifflin",2.555f, 5.555f, rebel3));
        rebel4.setLocation(createLocation("Dunder Mifflin",2.555f, 5.555f, rebel4));
        rebel5.setLocation(createLocation("Dunder Mifflin",2.555f, 5.555f, rebel5));
        rebel6.setLocation(createLocation("Dunder Mifflin",2.555f, 5.555f, rebel6));

        int[] quantityResourceRebel1 = new int[]{4,4,3,1};
        int[] quantityResourceRebel2 = new int[]{2,4,7,9};
        int[] quantityResourceRebel3 = new int[]{4,10,9,6};
        int[] quantityResourceRebel4 = new int[]{3,2,7,4};
        int[] quantityResourceRebel5 = new int[]{5,7,2,3};
        int[] quantityResourceRebel6 = new int[]{6,3,6,5};

        rebel1.setResources(createRebelResourceList(quantityResourceRebel1,rebel1));
        rebel2.setResources(createRebelResourceList(quantityResourceRebel2,rebel2));
        rebel3.setResources(createRebelResourceList(quantityResourceRebel3,rebel3));
        rebel4.setResources(createRebelResourceList(quantityResourceRebel4,rebel4));
        rebel5.setResources(createRebelResourceList(quantityResourceRebel5,rebel5));
        rebel6.setResources(createRebelResourceList(quantityResourceRebel6,rebel6));

        return List.of(rebel1, rebel2, rebel3, rebel4, rebel5, rebel6);
    }

    public static List<RebelResource> createRebelResourceList(int[] quantityArray, Rebel rebel){
        int[] quantities = Arrays.copyOfRange(quantityArray, 0, 4);
        return Stream.of(ResourcesEnum.values()).map(resourcesEnum -> {
            int ordinal = resourcesEnum.ordinal();
            int quantity = quantities[ordinal];
            RebelResource rebelResource = createRebelResource(resourcesEnum,quantity);
            rebelResource.setRebel(rebel);
            return rebelResource;
        }).toList();
    }

    public static RebelResource createRebelResource(ResourcesEnum resource, int quantity){
        return RebelResource.builder()
                .resource(resource)
                .quantity(quantity)
                .build();
    }

    public static Location createLocation(String name, float latitude, float longitude, Rebel rebel){
        return Location.builder()
                .name(name)
                .latitude(latitude)
                .longitude(longitude)
                .rebel(rebel)
                .build();
    }

    public static Rebel createRebel(String name, Gender gender, int age){
        return Rebel.builder()
                .name(name)
                .gender(gender)
                .age(age)
                .betrayer(false)
                .reportedList(new ArrayList<>())
                .build();
    }
}
