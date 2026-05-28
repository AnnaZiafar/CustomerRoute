package demo.messageroute.tier.events;

public record TierCreatedEvent(String id, String level, int discountPercentage) implements Event {
    @Override
    public String getId() {
        return id;
    }
}
