package com.messagequeue.rabbitmq;

import com.messagequeue.rabbitmq.receiver.Receiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitmqApplication {

	public final static String queueName = "spring-boot";

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange("Spring boot exchange");
	}

	@Bean
	Binding binding(Queue queue,TopicExchange topicExchange) {
		return BindingBuilder.bind(queue).to(topicExchange).with(queueName);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(messageListenerAdapter);

		return container;
	}

	@Bean
	MessageListenerAdapter messageListenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receive message");
	}

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqApplication.class, args);
	}
}
