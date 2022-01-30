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

package com.example.avro;

import com.example.avro.services.KafkaProducerService;
import com.example.avro.services.datagenerator.InvoiceGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaProducerAvroApplication implements ApplicationRunner {

  @Value("${application.configs.invoice.count}")
  private int INVOICE_COUNT;

  private final KafkaProducerService producerService;
  private final InvoiceGenerator invoiceGenerator;

  public KafkaProducerAvroApplication(
      KafkaProducerService producerService, InvoiceGenerator invoiceGenerator) {
    this.producerService = producerService;
    this.invoiceGenerator = invoiceGenerator;
  }

  public static void main(String[] args) {
    SpringApplication.run(KafkaProducerAvroApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    for (int i = 0; i < INVOICE_COUNT; i++) {
      producerService.sendMessage(invoiceGenerator.getNextInvoice());
      Thread.sleep(1000);
    }
  }
}
