package demo.messageroute.tier.events;

public sealed interface TierEvent permits TierCreatedEvent, DiscountUpdatedEvent{
}
