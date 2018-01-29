package pl.janiec.krystian.gamestorerest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProducerDTO {

    private Long id;
    private String name;
    private String shortcut;
    @JsonProperty("producer_url")
    private String producerUrl;
}
