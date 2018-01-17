package pl.janiec.krystian.gamestorerest.api.mapper;

import org.junit.Test;
import pl.janiec.krystian.gamestorerest.api.model.CategoryDTO;
import pl.janiec.krystian.gamestorerest.domain.Category;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static pl.janiec.krystian.gamestorerest.util.TestConstants.*;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.*;


public class CategoryMapperTest {

    private CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void shouldMappingCategoryClassToCategoryDTO() throws Exception {
        Category testCategory = createNewCategory(ACTION_ID, CATEGORY_ACTION);

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(testCategory);

        assertThat(categoryDTO.getId(), is(equalTo(ACTION_ID)));
        assertThat(categoryDTO.getName(), is(equalTo(CATEGORY_ACTION)));
    }
}
