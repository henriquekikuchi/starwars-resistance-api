package br.com.kikuchi.henrique.starwarsapi.mapper;

import br.com.kikuchi.henrique.starwarsapi.dto.RebelResourceDto;
import br.com.kikuchi.henrique.starwarsapi.model.RebelResource;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RebelResourceMapper {

    RebelResourceMapper INSTANCE = Mappers.getMapper(RebelResourceMapper.class);

    RebelResource rebelResourceDtoToRebelResource(RebelResource rebelResourceDto);
}
