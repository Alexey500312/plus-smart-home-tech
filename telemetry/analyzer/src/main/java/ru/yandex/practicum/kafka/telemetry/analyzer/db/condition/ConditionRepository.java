package ru.yandex.practicum.kafka.telemetry.analyzer.db.condition;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConditionRepository extends JpaRepository<Condition, Long> {
}
