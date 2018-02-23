package com.messagequeue.rabbitmq.sender;

import com.messagequeue.rabbitmq.RabbitmqApplication;
import com.messagequeue.rabbitmq.receiver.Receiver;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

public class Sender implements CommandLineRunner{

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;
    private final ConfigurableApplicationContext configurableApplicationContext;

    public Sender(final RabbitTemplate rabbitTemplate, final Receiver receiver,
                  final ConfigurableApplicationContext configurableApplicationContext) {
        this.rabbitTemplate = rabbitTemplate;
        this.receiver = receiver;
        this.configurableApplicationContext = configurableApplicationContext;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message");

        rabbitTemplate.convertAndSend(RabbitmqApplication.queueName,"Hello RabbitMQueue");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        configurableApplicationContext.close();
    }
}
