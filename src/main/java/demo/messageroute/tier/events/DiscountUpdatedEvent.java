package demo.messageroute.tier.events;

public record DiscountUpdatedEvent(String level, int discountPercentage) implements Event {

}
