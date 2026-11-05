package com.example.specvalidationtest.model;

public record PaymentResponse(
        String paymentMethod,
        String paymentReference
) {
}
