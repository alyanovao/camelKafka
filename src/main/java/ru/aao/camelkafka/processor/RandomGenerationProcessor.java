package ru.aao.camelkafka.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import ru.aao.camelkafka.model.CustomResponse;

import java.util.Random;
import java.util.UUID;

@Component
public class RandomGenerationProcessor implements Processor {

    private Random random = new Random();

    @Override
    public void process(Exchange exchange) throws Exception {
        Long value = random.nextLong(100);
        String name = UUID.randomUUID().toString();
        exchange.getIn().setBody(new CustomResponse(name, value));
    }
}
