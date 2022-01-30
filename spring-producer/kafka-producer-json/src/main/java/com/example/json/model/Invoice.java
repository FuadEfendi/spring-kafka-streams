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

package com.example.kafka.pos.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Invoice {

  @JsonProperty("InvoiceNumber")
  private String invoiceNumber;

  @JsonProperty("CreatedTime")
  private Long createdTime;

  @JsonProperty("StoreID")
  private String storeID;

  @JsonProperty("PosID")
  private String posID;

  @JsonProperty("CashierID")
  private String cashierID;

  @JsonProperty("CustomerType")
  private String customerType;

  @JsonProperty("CustomerCardNo")
  private String customerCardNo;

  @JsonProperty("TotalAmount")
  private Double totalAmount;

  @JsonProperty("NumberOfItems")
  private Integer numberOfItems;

  @JsonProperty("PaymentMethod")
  private String paymentMethod;

  @JsonProperty("TaxableAmount")
  private Double taxableAmount;

  @JsonProperty("CGST")
  private Double cGST;

  @JsonProperty("SGST")
  private Double sGST;

  @JsonProperty("CESS")
  private Double cESS;

  @JsonProperty("DeliveryType")
  private String deliveryType;

  @JsonProperty("DeliveryAddress")
  private DeliveryAddress deliveryAddress;

  @JsonProperty("InvoiceLineItems")
  private List<LineItem> invoiceLineItems = new ArrayList<LineItem>();
}
