package pl.janiec.krystian.gamestorerest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.janiec.krystian.gamestorerest.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository <Category, Long> {

    Category findByName(String name);
}
