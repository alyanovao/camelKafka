package ru.aao.camelkafka.route;

import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;
import ru.aao.camelkafka.model.CustomResponse;
import ru.aao.camelkafka.processor.RandomGenerationProcessor;

@Component
public class KafkaProducerRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("quartz://customScheduler?cron={{scheduler.kafkaProducer.cron}}")
            .process(new RandomGenerationProcessor())
            .to("direct:message");

        from("direct:message")
            .setHeader(KafkaConstants.HEADERS, constant("FROM-CAMEL"))
            .marshal().json(JsonLibrary.Jackson)
            .to("kafka:{{kafka.topic}}?brokers={{kafka.broker.url}}")
            .log("send message :: ${body}");

        from("kafka:{{kafka.topic}}?brokers={{kafka.broker.url}}&groupId=app&autoOffsetReset=earliest&seekTo=BEGINNING")
            .unmarshal().json(JsonLibrary.Jackson, CustomResponse.class)
            .log("message - ${body} from ${headers[kafka.TOPIC]}")
                .process(exchange -> {
                    Message message = exchange.getIn();
                });

    }
}
