package demo.customerroute.customer;

import jakarta.validation.constraints.NotBlank;

public record CreateCustomerDto(
        @NotBlank(message = "Customer must have a name")
        String name,

        Long tierId
) {}
