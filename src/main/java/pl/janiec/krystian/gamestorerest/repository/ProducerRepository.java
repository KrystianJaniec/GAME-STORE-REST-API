package pl.janiec.krystian.gamestorerest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.janiec.krystian.gamestorerest.api.model.ProducerDTO;
import pl.janiec.krystian.gamestorerest.domain.Producer;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

    Producer findById(Long id);
}
