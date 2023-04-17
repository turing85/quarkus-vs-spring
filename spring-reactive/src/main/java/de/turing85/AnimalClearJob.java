package de.turing85;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class AnimalClearJob {
  private final AnimalRepository repository;

  @Scheduled(cron = "${db.cleanup.cron}")
  public void clearAnimals() {
    repository.deleteAll()
        .subscribeOn(Schedulers.immediate())
        .subscribe();
  }
}