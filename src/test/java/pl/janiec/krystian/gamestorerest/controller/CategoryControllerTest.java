package pl.janiec.krystian.gamestorerest.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.janiec.krystian.gamestorerest.api.model.CategoryDTO;
import pl.janiec.krystian.gamestorerest.service.CategoryService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.janiec.krystian.gamestorerest.controller.CategoryController.CATEGORIES_URL;
import static pl.janiec.krystian.gamestorerest.util.TestConstants.*;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.createNewCategoryDTO;

public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void shouldSuccessfullyShowListWithAllCategories() throws Exception {
        List<CategoryDTO> categories = Arrays.asList(createNewCategoryDTO(ACTION_ID, CATEGORY_ACTION),
                createNewCategoryDTO(SPORT_ID, CATEGORY_SPORT));

        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get(CATEGORIES_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)));

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    public void shouldSuccessfullyShowCategoryWithThisName() throws Exception {
        CategoryDTO category = createNewCategoryDTO(SPORT_ID, CATEGORY_SPORT);
        category.setCategoryUrl(CATEGORIES_URL + CATEGORY_SPORT);

        when(categoryService.getCategoryByName(anyString())).thenReturn(category);

        mockMvc.perform(get(CATEGORIES_URL + "{name}", CATEGORY_SPORT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(equalTo(CATEGORY_SPORT))))
                .andExpect(jsonPath("$.category_url", is(equalTo(CATEGORIES_URL + CATEGORY_SPORT))));

        verify(categoryService, times(1)).getCategoryByName(CATEGORY_SPORT);
    }
}