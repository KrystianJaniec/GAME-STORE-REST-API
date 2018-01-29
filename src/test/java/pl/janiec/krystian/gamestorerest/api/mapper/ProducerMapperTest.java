package pl.janiec.krystian.gamestorerest.api.mapper;

import org.junit.Test;
import pl.janiec.krystian.gamestorerest.api.model.ProducerDTO;
import pl.janiec.krystian.gamestorerest.domain.Producer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static pl.janiec.krystian.gamestorerest.util.TestConstants.*;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.createNewProducer;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.createNewProducerDTO;

public class ProducerMapperTest {

    private ProducerMapper producerMapper = ProducerMapper.INSTANCE;

    @Test
    public void shouldMappingProducerClassToProducerDTO() throws Exception {
        Producer producer = createNewProducer(CD_PROJEKT_RED, CDPR, CDPR_ID);

        ProducerDTO producerDTO = producerMapper.producerToProducerDTO(producer);

        assertThat(producerDTO.getName(), is(equalTo(CD_PROJEKT_RED)));
        assertThat(producerDTO.getShortcut(), is(equalTo(CDPR)));
    }

    @Test
    public void shouldMappingProducerDTOClassToProducer() throws Exception {
        ProducerDTO producerDTO = createNewProducerDTO(CD_PROJEKT_RED, CDPR);

        Producer producer = producerMapper.producerDTOtoProducer(producerDTO);

        assertThat(producer.getCompanyName(), is(equalTo(CD_PROJEKT_RED)));
        assertThat(producer.getCompanyShortcutName(), is(equalTo(CDPR)));
    }
}
