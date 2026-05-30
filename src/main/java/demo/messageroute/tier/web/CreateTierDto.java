package demo.messageroute.tier.web;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@JsonPropertyOrder({"level", "discount_percentage"})
public record CreateTierDto(
        @NotBlank(message = "Level cannot be empty")
        String level,

        @Min(value = 0, message = "Discount can not be less than 0%" )
        @Max(value = 100, message = "Discount can not be more than 100%")
        int discountPercentage
) {}
