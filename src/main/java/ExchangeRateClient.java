package com.example;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/v6/latest/USD")
@RegisterRestClient(configKey = "exchange-api")
public interface ExchangeRateClient {

    @GET
    ExchangeRateResponse getRates();
}