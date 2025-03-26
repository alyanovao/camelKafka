package ru.aao.camelkafka.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventConsumer extends RouteBuilder {

    @Override
    public void configure() {
        errorHandler(defaultErrorHandler());

        from("kafka:{{kafka.event.topic}}?brokers={{kafka.url}}&groupId={{kafka.consumer.groupId}}&" +
            "autoOffsetReset={{kafka.consumer.offset}}&" +
            "seekTo={{kafka.consumer.seekTo}}")
            .routeId("eventTopic-consumer-route")
            .log("Read message - ${body} from ${headers[kafka.TOPIC]}");
    }
}