package demo.customerroute.tier.web;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UpdateTierDto(
        @NotBlank(message = "Level cannot be empty")
        String level,

        @Min(value = 0, message = "Discount can not be less than 0%" )
        @Max(value = 100, message = "Discount can be no more than 100%")
        int discountPercentage
) {}
