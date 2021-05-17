package com.java.springbootrabitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.springbootrabitmq.dto.MessageDTO;
import com.java.springbootrabitmq.services.RabbitMQService;
import com.java.springbootrabitmq.util.RabbitMQConstant;

@RestController
@RequestMapping("/rabbitMQ/")
public class DemoRabbitMqController {

	@Autowired
	RabbitMQService rabbitMQService;

	@GetMapping(value = "/send")
	public String sendMessage(@RequestParam("message") String message) {

		System.out.println("message "+message);
		MessageDTO sampleDto = new MessageDTO();
		sampleDto.setValue(message);
		rabbitMQService.convertAndSendMessage(RabbitMQConstant.EXCHANGE,RabbitMQConstant.ROUTING_KEY, sampleDto);

		return "Message has been sent Successfully";
	}

}
