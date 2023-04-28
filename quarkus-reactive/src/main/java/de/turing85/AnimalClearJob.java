package de.turing85;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class AnimalClearJob {
  private final AnimalRepository repository;

  @Scheduled(cron = "${db.cleanup.cron}")
  @WithTransaction
  Uni<Void> clearAnimals() {
    return repository.deleteAll()
        .replaceWithVoid();
  }
}