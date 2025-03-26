package ru.aao.camelkafka.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventProducer extends RouteBuilder {

    @Override
    public void configure() {
        errorHandler(defaultErrorHandler());

        from("direct:applicationEventProducerRoute")
            .routeId("applicationEventProducerRouteId")
            .setHeader(KafkaConstants.HEADERS, constant("FROM-PRODUCER"))
            .setBody(constant("Тестовое сообщение"))
            .to("kafka:{{kafka.event.topic}}?brokers={{kafka.url}}")
            .log("send message :: ${body}");
    }
}
