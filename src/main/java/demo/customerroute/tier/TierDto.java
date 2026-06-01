package demo.customerroute.tier;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "level", "discount_percentage"})
public record TierDto(
        Long id,
        String level,
        int discountPercentage
) {}
