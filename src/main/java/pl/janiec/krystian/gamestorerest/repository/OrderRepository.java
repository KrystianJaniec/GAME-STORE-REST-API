package pl.janiec.krystian.gamestorerest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.janiec.krystian.gamestorerest.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
