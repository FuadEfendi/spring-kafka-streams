package com.example.kafkaproducerrest.services;

import com.example.kafkaproducerrest.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaMessageController {
  @Autowired KafkaProducerService kafkaProducerService;

  @PostMapping("/api/message")
  public String sendMessageToKafka(@RequestBody Message message) {
    kafkaProducerService.sendMessage(message.getTopic(), message.getKey(), message.getValue());
    return String.format(
        "Success - Message with key %s was sent to Kafka Topic: %s",
        message.getKey(), message.getTopic());
  }
}
