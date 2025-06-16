package ru.yandex.practicum.kafka.telemetry.analyzer.service.snapshot;

import com.google.protobuf.Empty;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouterControllerGrpc;

@Slf4j
@Service
public class SendDeviceAction {
    @GrpcClient("hub-router")
    HubRouterControllerGrpc.HubRouterControllerBlockingStub hubRouterClient;

    public void handleDeviceAction(DeviceActionRequest deviceActionRequest) {
        Empty empty = hubRouterClient.handleDeviceAction(deviceActionRequest);
        log.info("Send: {}", deviceActionRequest);
    }
}
