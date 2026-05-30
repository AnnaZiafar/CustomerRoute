package demo.messageroute.tier.web;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record UpdateTierDto(

        @Min(value = 0, message = "Discount can not be less than 0%")
        @Max(value = 100, message = "Discount can not be more than 100%")
        int discountPercentage
) {}