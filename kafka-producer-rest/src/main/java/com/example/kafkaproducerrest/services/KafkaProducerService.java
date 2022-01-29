package com.example.kafkaproducerrest.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KafkaProducerService {
  @Autowired private KafkaTemplate<String, String> kafkaTemplate;

  public void sendMessage(String topic, String key, String value) {
    log.info(
        String.format("Sending message to Kafka; key: %s value: %s topic: %s", key, value, topic));
    kafkaTemplate.send(topic, key, value);
  }
}
