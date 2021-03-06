package pl.janiec.krystian.gamestorerest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;
    @JsonProperty("category_url")
    private String categoryUrl;
}
