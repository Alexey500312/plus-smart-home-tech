package ru.yandex.practicum.kafka.telemetry.analyzer.db.scenario;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.kafka.telemetry.analyzer.db.action.Action;
import ru.yandex.practicum.kafka.telemetry.analyzer.db.condition.Condition;

import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Table(name = "scenarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
public class Scenario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hub_id")
    private String hubId;

    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapKeyColumn(
            table = "scenario_conditions",
            name = "sensor_id")
    @JoinTable(
            name = "scenario_conditions",
            joinColumns = @JoinColumn(name = "scenario_id"),
            inverseJoinColumns = @JoinColumn(name = "condition_id"))
    private Map<String, Condition> conditions = new LinkedHashMap<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapKeyColumn(
            table = "scenario_actions",
            name = "sensor_id")
    @JoinTable(
            name = "scenario_actions",
            joinColumns = @JoinColumn(name = "scenario_id"),
            inverseJoinColumns = @JoinColumn(name = "action_id"))
    private Map<String, Action> actions = new LinkedHashMap<>();
}
