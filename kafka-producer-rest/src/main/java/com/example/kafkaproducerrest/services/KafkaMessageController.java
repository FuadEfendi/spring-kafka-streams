/*
 * Copyright (c) 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
