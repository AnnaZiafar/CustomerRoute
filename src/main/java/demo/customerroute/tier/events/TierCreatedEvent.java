package demo.customerroute.tier.events;

public record TierCreatedEvent(String level, int discountPercentage) implements TierEvent {

}
