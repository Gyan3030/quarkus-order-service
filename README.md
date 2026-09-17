# Quarkus Order Service

A REST-based Order Service built using Quarkus, PostgreSQL, Apache Kafka, OpenSearch and Docker.

## Project Overview

This project accepts USD orders, converts the amount into a target currency using an external Exchange Rate API, stores the order in PostgreSQL, publishes an event to Kafka, and indexes the event into OpenSearch for analytics.

## Architecture

Client
↓
Quarkus REST API
↓
Exchange Rate API
↓
PostgreSQL
↓
Apache Kafka
↓
Kafka Consumer
↓
OpenSearch
↓
OpenSearch Dashboards

## Technologies Used

- Java 21
- Quarkus
- PostgreSQL
- Apache Kafka
- OpenSearch
- OpenSearch Dashboards
- Docker
- Docker Compose
- Maven
- REST API

## API Endpoint

### Create Order

POST `/api/v1/orders`

### Request

```json
{
  "customerId": "CUST-1001",
  "amountUSD": 150.00,
  "targetCurrency": "EUR"
}
