package demo.messageroute.tier.events;

public record TierCreatedEvent(String level, int discountPercentage) implements Event {

}
