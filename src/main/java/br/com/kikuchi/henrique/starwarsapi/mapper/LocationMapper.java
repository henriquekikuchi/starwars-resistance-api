package br.com.kikuchi.henrique.starwarsapi.mapper;

import br.com.kikuchi.henrique.starwarsapi.dto.LocationDto;
import br.com.kikuchi.henrique.starwarsapi.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    Location locationDtoToLocation(LocationDto locationDto);
}
