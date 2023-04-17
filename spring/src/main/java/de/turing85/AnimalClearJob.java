package de.turing85;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class AnimalClearJob {
  private final AnimalRepository repository;

  @Scheduled(cron = "${db.cleanup.cron}")
  @Transactional
  public void clearAnimals() {
    repository.deleteAll();
  }
}