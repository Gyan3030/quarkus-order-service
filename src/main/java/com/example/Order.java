package com.example;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    public Long orderId;

    public String customerId;
    public double amountUSD;
    public String targetCurrency;
    public double convertedAmount;
    public String status;
    public String createdAt;
}