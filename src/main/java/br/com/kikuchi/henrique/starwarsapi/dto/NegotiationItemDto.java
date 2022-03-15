package br.com.kikuchi.henrique.starwarsapi.dto;

import br.com.kikuchi.henrique.starwarsapi.model.ResourcesEnum;
public record NegotiationItemDto(
//        @Value(value = "0")
//        Integer weapon,
//        @Value(value = "0")
//        Integer bullet,
//        @Value(value = "0")
//        Integer water,
//        @Value(value = "0")
//        Integer food
        ResourcesEnum resource,
        int quantity
) {}
