package com.bezkoder.spring.oracle.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bezkoder.spring.oracle.dto.ResponseData;
import com.bezkoder.spring.oracle.model.Tutorial;
import com.bezkoder.spring.oracle.repository.TutorialRepository;
import com.bezkoder.spring.oracle.service.TutorialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.util.Map;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerRecord;

@Component
public class KafkaConsumer {

	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
	@Autowired
	TutorialRepository tutorialRepository;
	@Autowired
	private TutorialService tutorialService;

	@KafkaListener(topics = "topic_demo")
	public void receivedMessage(ConsumerRecord<?, Map<String, Object>> message, Acknowledgment acknowledgment) throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();

		logger.info("=============================== value: " + message.value());
		// Tutorial fooFromJson = new Gson().fromJson(message.value().toString(),
		// Tutorial.class);

		// Tutorial lib = objectMapper.readValue(new
		// Gson().toJson(message.value()),Tutorial.class);

		Tutorial obj = new Tutorial();
		obj.setId(UUID.randomUUID().toString());
		obj.setCategory_id(1);
		obj.setCode("232");
		obj.setPublished(false);
		obj.setTitle("tiêu đề");
		obj.setDescription("mô tả");

		 ResponseData res = tutorialService.save(0, obj);
		 acknowledgment.acknowledge();
		logger.info("=============================== value: " + res);

	}

}
