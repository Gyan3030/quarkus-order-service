package com.example;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
class OrderResourceTest {

    @Test
    void testCreateOrder() {

        given()
            .contentType(ContentType.JSON)
            .body("""
                {
                    "customerId": "CUST-TEST",
                    "amountUSD": 100.0,
                    "targetCurrency": "EUR"
                }
                """)
        .when()
            .post("/api/v1/orders")
        .then()
            .statusCode(200)
            .body("customerId", equalTo("CUST-TEST"))
            .body("status", equalTo("PROCESSED"));
    }
}