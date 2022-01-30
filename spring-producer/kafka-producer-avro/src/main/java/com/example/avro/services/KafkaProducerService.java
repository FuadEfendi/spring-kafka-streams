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

package com.example.avro.services;

import com.example.avro.model.Invoice;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KafkaProducerService {
  @Value("${application.configs.topic.name}")
  private String TOPIC_NAME;

  private final KafkaTemplate<String, Invoice> kafkaTemplate;

  public KafkaProducerService(KafkaTemplate<String, Invoice> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMessage(Invoice invoice) {
    log.info(String.format("Producing Invoice No: %s", invoice.getInvoiceNumber()));
    kafkaTemplate.send(TOPIC_NAME, invoice.getStoreID(), invoice);
  }
}
