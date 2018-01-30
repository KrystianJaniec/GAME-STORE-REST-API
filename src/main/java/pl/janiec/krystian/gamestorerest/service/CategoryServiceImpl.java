package pl.janiec.krystian.gamestorerest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.janiec.krystian.gamestorerest.api.mapper.CategoryMapper;
import pl.janiec.krystian.gamestorerest.api.model.CategoryDTO;
import pl.janiec.krystian.gamestorerest.domain.Category;
import pl.janiec.krystian.gamestorerest.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

import static pl.janiec.krystian.gamestorerest.controller.CategoryController.CATEGORIES_URL;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> {
                    CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
                    categoryDTO.setCategoryUrl(CATEGORIES_URL + category.getName().toLowerCase());

                    return categoryDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(categoryRepository.findByName(name.toLowerCase()));
        categoryDTO.setCategoryUrl(CATEGORIES_URL + name.toLowerCase());

        return categoryDTO;
    }
}
