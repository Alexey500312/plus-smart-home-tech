package ru.yandex.practicum.kafka.telemetry.collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Collector {
    public static void main(String[] args) {
        SpringApplication.run(Collector.class, args);
    }
}
