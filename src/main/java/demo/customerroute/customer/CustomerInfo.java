package demo.customerroute.customer;

import jakarta.validation.constraints.NotBlank;

public record CustomerInfo(
        @NotBlank(message = "Customer field can not be empty.")
        String customer,
        String tier
) {}
