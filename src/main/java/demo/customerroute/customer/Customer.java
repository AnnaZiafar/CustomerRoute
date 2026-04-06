package demo.customerroute.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Customer (
        @JsonProperty("customer")
        String name,
        String tier
) {}
