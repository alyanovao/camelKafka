package ru.aao.camelkafka.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import ru.aao.camelkafka.processor.RandomGenerationProcessor;

@Component
public class QuartzRoute extends RouteBuilder {

    @Override
    public void configure() {
        errorHandler(defaultErrorHandler());

        from("quartz://customScheduler?cron={{scheduler.kafkaProducer.cron}}")
            .process(new RandomGenerationProcessor())
            .to("direct:message");
    }
}