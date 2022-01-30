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

import com.example.avro.model.LineItem;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
class ProductGenerator {
  private final Random random;
  private final Random qty;
  private final LineItem[] products;

  public ProductGenerator() {
    String DATAFILE = "data/products.json";
    ObjectMapper mapper =
        JsonMapper.builder()
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            .build();

    random = new Random();
    qty = new Random();
    try {
      products =
          mapper.readValue(
              AddressGenerator.class.getClassLoader().getResource(DATAFILE), LineItem[].class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private int getIndex() {
    return random.nextInt(100);
  }

  private int getQuantity() {
    return qty.nextInt(2) + 1;
  }

  public LineItem getNextProduct() {
    LineItem lineItem = products[getIndex()];
    lineItem.setItemQty(getQuantity());
    lineItem.setTotalValue(lineItem.getItemPrice() * lineItem.getItemQty());
    return lineItem;
  }
}
