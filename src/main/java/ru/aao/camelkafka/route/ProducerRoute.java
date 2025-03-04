package ru.aao.camelkafka.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class ProducerRoute extends RouteBuilder {

    @Override
    public void configure() {
        errorHandler(defaultErrorHandler());

        from("direct:message")
            .setHeader(KafkaConstants.HEADERS, constant("FROM-CAMEL"))
            .marshal().json(JsonLibrary.Jackson)
            .to("kafka:{{kafka.topic}}?brokers={{kafka.url}}")
            .log("send message :: ${body}");
    }
}
