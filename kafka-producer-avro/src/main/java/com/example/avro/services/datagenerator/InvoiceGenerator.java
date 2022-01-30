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
import com.example.avro.model.Invoice;
import com.example.avro.model.LineItem;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Log4j2
public class InvoiceGenerator {
  private final Random invoiceIndex;
  private final Random invoiceNumber;
  private final Random numberOfItems;
  private final Invoice[] invoices;
  final AddressGenerator addressGenerator;
  final ProductGenerator productGenerator;

  public InvoiceGenerator(AddressGenerator addressGenerator, ProductGenerator productGenerator) {
    String DATAFILE = "data/invoice.json";
    invoiceIndex = new Random();
    invoiceNumber = new Random();
    numberOfItems = new Random();
    ObjectMapper mapper =
        JsonMapper.builder()
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            .build();
    try {
      invoices =
          mapper.readValue(
              AddressGenerator.class.getClassLoader().getResource(DATAFILE), Invoice[].class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    this.addressGenerator = addressGenerator;
    this.productGenerator = productGenerator;
  }

  private int getIndex() {
    return invoiceIndex.nextInt(100);
  }

  private int getNewInvoiceNumber() {
    return invoiceNumber.nextInt(99999999) + 99999;
  }

  private int getNoOfItems() {
    return numberOfItems.nextInt(4) + 1;
  }

  public Invoice getNextInvoice() {
    Invoice invoice = invoices[getIndex()];
    invoice.setInvoiceNumber(getNewInvoiceNumber());
    invoice.setCreatedTime(System.currentTimeMillis());
    if ("HOME-DELIVERY".equalsIgnoreCase(invoice.getDeliveryType())) {
      DeliveryAddress deliveryAddress = addressGenerator.getNextAddress();
      invoice.setDeliveryAddress(deliveryAddress);
    }
    int itemCount = getNoOfItems();
    double totalAmount = 0.0;
    List<LineItem> items = new ArrayList<>();
    for (int i = 0; i < itemCount; i++) {
      LineItem item = productGenerator.getNextProduct();
      totalAmount = totalAmount + item.getTotalValue();
      items.add(item);
    }
    invoice.setNumberOfItems(itemCount);
    invoice.setInvoiceLineItems(items);
    invoice.setTotalAmount(totalAmount);
    invoice.setTaxableAmount(totalAmount);
    invoice.setCGST(totalAmount * 0.025);
    invoice.setSGST(totalAmount * 0.025);
    invoice.setCESS(totalAmount * 0.00125);
    log.debug(invoice);
    return invoice;
  }
}
