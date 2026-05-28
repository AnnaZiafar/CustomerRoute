package demo.messageroute.tier.events;

public sealed interface Event permits TierCreatedEvent, DiscountUpdatedEvent {
    String getId();
}
