package pl.janiec.krystian.gamestorerest.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.janiec.krystian.gamestorerest.api.mapper.CategoryMapper;
import pl.janiec.krystian.gamestorerest.api.model.CategoryDTO;
import pl.janiec.krystian.gamestorerest.domain.Category;
import pl.janiec.krystian.gamestorerest.repository.CategoryRepository;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static pl.janiec.krystian.gamestorerest.controller.CategoryController.CATEGORIES_URL;
import static pl.janiec.krystian.gamestorerest.util.TestConstants.*;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.createNewCategory;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryService categoryService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
    }

    @Test
    public void shouldSuccessfullyGetListWithAllCategories() throws Exception {
        List<Category> categoryList = Arrays.asList(createNewCategory(SPORT_ID, CATEGORY_SPORT),
                createNewCategory(ACTION_ID, CATEGORY_ACTION));

        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();

        assertThat(categoryDTOList.size(), is(equalTo(categoryList.size())));
    }

    @Test
    public void shouldSuccessfullyGetCategoryByName() throws Exception {
        Category category = createNewCategory(SPORT_ID, CATEGORY_SPORT);

        when(categoryRepository.findByName(anyString())).thenReturn(category);

        CategoryDTO categoryDTO = categoryService.getCategoryByName(CATEGORY_SPORT);

        assertThat(categoryDTO.getName(), is(equalTo(CATEGORY_SPORT)));
        assertThat(categoryDTO.getId(), is(equalTo(SPORT_ID)));
        assertThat(categoryDTO.getCategoryUrl(), is(equalTo(CATEGORIES_URL + CATEGORY_SPORT.toLowerCase())));
    }
}
