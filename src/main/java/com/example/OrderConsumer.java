package com.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import io.smallrye.common.annotation.Blocking;

@ApplicationScoped
public class OrderConsumer {

    @Inject
    ObjectMapper objectMapper;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Incoming("order-events")
    @Blocking
    public void consume(String message) throws Exception {

        OrderEvent event = objectMapper.readValue(message, OrderEvent.class);

        String json = objectMapper.writeValueAsString(event);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://opensearch:9200/orders/_doc/" + event.orderId))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("OpenSearch response: " + response.statusCode());
    }
}