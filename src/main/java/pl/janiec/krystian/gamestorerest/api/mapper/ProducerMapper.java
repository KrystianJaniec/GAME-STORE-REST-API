package pl.janiec.krystian.gamestorerest.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import pl.janiec.krystian.gamestorerest.api.model.ProducerDTO;
import pl.janiec.krystian.gamestorerest.domain.Producer;

@Mapper(componentModel = "spring")
public interface ProducerMapper {

    ProducerMapper INSTANCE = Mappers.getMapper(ProducerMapper.class);

    @Mappings({
            @Mapping(source = "companyName", target = "name"),
            @Mapping(source = "companyShortcutName", target = "shortcut")})
    ProducerDTO producerToProducerDTO(Producer producer);

    @Mappings({
            @Mapping(source = "name", target = "companyName"),
            @Mapping(source = "shortcut", target = "companyShortcutName")})
    Producer producerDTOToProducer(ProducerDTO producerDTO);
}
