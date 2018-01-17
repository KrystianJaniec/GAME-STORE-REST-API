package pl.janiec.krystian.gamestorerest.service;

import pl.janiec.krystian.gamestorerest.api.model.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryByName(String name);
}
