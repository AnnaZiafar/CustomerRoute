package demo.customerroute.tier.events;

public sealed interface TierEvent permits TierCreatedEvent, DiscountUpdatedEvent{
}
