package ru.yandex.practicum.kafka.telemetry.collector.service.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SwitchSensorEvent;
import ru.yandex.practicum.kafka.telemetry.collector.service.KafkaEventProducer;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;

@Component
public class SwitchSensorEventHandler extends BaseSensorEventHandler<SwitchSensorAvro> {
    public SwitchSensorEventHandler(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.SWITCH_SENSOR_EVENT;
    }

    @Override
    protected SwitchSensorAvro toAvro(SensorEventProto event) {
        SwitchSensorEvent switchSensorEvent = event.getSwitchSensorEvent();

        return SwitchSensorAvro.newBuilder()
                .setState(switchSensorEvent.getState())
                .build();
    }
}