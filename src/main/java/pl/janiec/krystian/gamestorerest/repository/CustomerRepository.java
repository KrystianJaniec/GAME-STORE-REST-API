package pl.janiec.krystian.gamestorerest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.janiec.krystian.gamestorerest.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findById(Long id);
}
