package demo.messageroute.tier.events;

public record DiscountUpdatedEvent(String level, int oldDiscount, int newDiscount) implements TierEvent {

}
