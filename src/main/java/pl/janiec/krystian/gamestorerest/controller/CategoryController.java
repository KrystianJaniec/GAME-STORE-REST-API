package pl.janiec.krystian.gamestorerest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.janiec.krystian.gamestorerest.api.model.CategoryDTO;
import pl.janiec.krystian.gamestorerest.api.model.CategoryListDTO;
import pl.janiec.krystian.gamestorerest.service.CategoryService;

@RestController
@RequestMapping(CategoryController.CATEGORIES_URL)
public class CategoryController {

    public static final String CATEGORIES_URL = "/api/categories/";

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryListDTO getAllCategories() {
        return new CategoryListDTO(categoryService.getAllCategories());
    }

    @GetMapping("{name}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }
}
