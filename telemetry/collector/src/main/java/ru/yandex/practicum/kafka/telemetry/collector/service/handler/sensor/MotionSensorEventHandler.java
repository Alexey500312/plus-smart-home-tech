package ru.yandex.practicum.kafka.telemetry.collector.service.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.MotionSensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.collector.service.KafkaEventProducer;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;

@Component
public class MotionSensorEventHandler extends BaseSensorEventHandler<MotionSensorAvro> {
    public MotionSensorEventHandler(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.MOTION_SENSOR_EVENT;
    }

    @Override
    protected MotionSensorAvro toAvro(SensorEventProto event) {
        MotionSensorEvent motionSensorEvent = event.getMotionSensorEvent();
        return MotionSensorAvro.newBuilder()
                .setLinkQuality(motionSensorEvent.getLinkQuality())
                .setMotion(motionSensorEvent.getMotion())
                .setVoltage(motionSensorEvent.getVoltage())
                .build();
    }
}
