/*
 * Copyright (c) 2018-2022 the original author or authors.
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

package com.example.avro.services.datagenerator;

import com.example.avro.model.DeliveryAddress;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
class AddressGenerator {

  private final Random random;
  private final DeliveryAddress[] addresses;

  public AddressGenerator() {
    final String DATAFILE = "data/address.json";
    final ObjectMapper mapper =
    JsonMapper.builder().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true).build();
    random = new Random();

    try {
      addresses =
          mapper.readValue(
              AddressGenerator.class.getClassLoader().getResource(DATAFILE),
              DeliveryAddress[].class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private int getIndex() {
    return random.nextInt(100);
  }

  public DeliveryAddress getNextAddress() {
    return addresses[getIndex()];
  }
}
