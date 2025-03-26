package ru.aao.camelkafka.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.camel.FluentProducerTemplate;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomMigrationConfiguration {

    private final FluentProducerTemplate template;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent() {
        System.out.println("Start listener by ApplicationReadyEvent");
        template.to("direct:applicationEventProducerRoute").send();
    }
}
