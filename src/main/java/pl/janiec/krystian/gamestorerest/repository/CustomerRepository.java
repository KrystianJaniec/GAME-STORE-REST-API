package pl.janiec.krystian.gamestorerest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.janiec.krystian.gamestorerest.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findById(Long id);
}
