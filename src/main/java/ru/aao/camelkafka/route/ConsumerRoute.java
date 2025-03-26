package ru.aao.camelkafka.route;

import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.aao.camelkafka.model.CustomResponse;

@Component
public class ConsumerRoute extends RouteBuilder {

    @Override
    public void configure() {
        errorHandler(defaultErrorHandler());

        from("kafka:{{kafka.topic}}?brokers={{kafka.url}}&groupId={{kafka.consumer.groupId}}&" +
                "autoOffsetReset={{kafka.consumer.offset}}&" +
                "seekTo={{kafka.consumer.seekTo}}")
                .routeId("kafkaTopic-consumer-route")

                .unmarshal().json(JsonLibrary.Jackson, CustomResponse.class)
                .log("message - ${body} from ${headers[kafka.TOPIC]}")
                .process(exchange -> {
                    Message message = exchange.getIn();
                    CustomResponse resp = message.getBody(CustomResponse.class);
                    Assert.isInstanceOf(CustomResponse.class, resp, "Message isn't CustomResponse");
                });
    }
}
