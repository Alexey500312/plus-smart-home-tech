package ru.yandex.practicum.kafka.telemetry.analyzer.db.action;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, Long> {
}
