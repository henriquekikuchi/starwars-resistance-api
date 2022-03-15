package br.com.kikuchi.henrique.starwarsapi.mapper;

import br.com.kikuchi.henrique.starwarsapi.dto.RebelCreateDto;
import br.com.kikuchi.henrique.starwarsapi.model.Rebel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RebelMapper {

    RebelMapper INSTANCE = Mappers.getMapper( RebelMapper.class );

    Rebel rebelCreateDtoToRebel(RebelCreateDto rebelCreateDto);
}
