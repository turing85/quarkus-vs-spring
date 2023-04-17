package de.turing85;

import io.quarkus.scheduler.Scheduled;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class AnimalClearJob {
  private final AnimalRepository repository;

  @Scheduled(cron = "${db.cleanup.cron}")
  @Transactional
  void clearAnimals() {
    repository.deleteAll();
  }
}