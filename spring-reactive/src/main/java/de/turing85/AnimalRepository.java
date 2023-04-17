package de.turing85;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AnimalRepository extends ReactiveCrudRepository<Animal, Long> {
}
