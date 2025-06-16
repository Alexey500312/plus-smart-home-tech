package ru.yandex.practicum.kafka.telemetry.collector.service.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.LightSensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.collector.service.KafkaEventProducer;
import ru.yandex.practicum.kafka.telemetry.event.LightSensorAvro;

@Component
public class LightSensorEventHandler extends BaseSensorEventHandler<LightSensorAvro> {
    public LightSensorEventHandler(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.LIGHT_SENSOR_EVENT;
    }

    @Override
    protected LightSensorAvro toAvro(SensorEventProto event) {
        LightSensorEvent lightSensorEvent = event.getLightSensorEvent();

        return LightSensorAvro.newBuilder()
                .setLinkQuality(lightSensorEvent.getLinkQuality())
                .setLuminosity(lightSensorEvent.getLuminosity())
                .build();
    }
}
