package com.example;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api/v1/orders")
public class OrderResource {

    @Inject
    EntityManager entityManager;

    @Inject
    @RestClient
    ExchangeRateClient exchangeRateClient;

    @Inject
    @Channel("order-created")
    Emitter<OrderEvent> orderEventEmitter;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Order createOrder(OrderRequest request) {

        Order order = new Order();

        order.customerId = request.customerId;
        order.amountUSD = request.amountUSD;
        order.targetCurrency = request.targetCurrency;

        Double rate = exchangeRateClient.getRates()
        .rates.get(request.targetCurrency);

if (rate == null) {
    throw new IllegalArgumentException("Invalid target currency");
}

        order.convertedAmount = request.amountUSD * rate;
        order.status = "PROCESSED";
        order.createdAt = java.time.Instant.now().toString();

        entityManager.persist(order);

        OrderEvent event = new OrderEvent();
        event.orderId = order.orderId;
        event.customerId = order.customerId;
        event.amountUSD = order.amountUSD;
        event.targetCurrency = order.targetCurrency;
        event.convertedAmount = order.convertedAmount;
        event.status = order.status;
        event.createdAt = order.createdAt;

        orderEventEmitter.send(event);

        return order;
    }
}