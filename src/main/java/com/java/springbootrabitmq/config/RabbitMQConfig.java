
		package com.java.springbootrabitmq.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.java.springbootrabitmq.util.RabbitMQConstant;

@Configuration
public class RabbitMQConfig {

	
	@Bean
	Queue dlq() {
		return QueueBuilder.durable("deadLetter.queue").build();
	}
	
	@Bean
	DirectExchange deadLetterExchange() {
		return new DirectExchange("deadLetterExchange");
	}
	
	@Bean
	Queue queue() {
		return QueueBuilder.durable(RabbitMQConstant.QUEUE).withArgument("x-dead-letter-exchange", "deadLetterExchange")
				.withArgument("x-dead-letter-routing-key", "deadLetter").build();
	}


	@Bean
	DirectExchange exchange() {
		return new DirectExchange(RabbitMQConstant.EXCHANGE);
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue()).to(exchange()).with(RabbitMQConstant.ROUTING_KEY);
	}
	
	@Bean
	Binding DLQbinding() {
		return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with("deadLetter");
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
	
	
	
}
