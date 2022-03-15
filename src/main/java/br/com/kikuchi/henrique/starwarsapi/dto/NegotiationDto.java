
package br.com.kikuchi.henrique.starwarsapi.dto;

import java.util.List;

public record NegotiationDto(
        List<NegotiationItemDto> send,
        List<NegotiationItemDto> receive
) {
}

