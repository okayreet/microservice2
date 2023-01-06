package com.okayreet.customer;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {
}