package pl.janiec.krystian.gamestorerest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.janiec.krystian.gamestorerest.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findById(Long id);
}
