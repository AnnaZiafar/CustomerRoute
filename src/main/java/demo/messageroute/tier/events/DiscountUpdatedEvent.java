package demo.messageroute.tier.events;

public record DiscountUpdatedEvent(String id, int discountPercentage) implements Event {
    @Override
    public String getId() {
        return id;
    }
}
