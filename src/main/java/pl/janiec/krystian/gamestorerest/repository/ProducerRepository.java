package pl.janiec.krystian.gamestorerest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.janiec.krystian.gamestorerest.domain.Producer;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {

    Producer findById(Long id);
}
