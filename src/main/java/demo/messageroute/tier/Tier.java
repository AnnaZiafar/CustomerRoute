package demo.messageroute.tier;

import demo.messageroute.tier.events.DiscountUpdatedEvent;
import demo.messageroute.tier.events.Event;
import demo.messageroute.tier.events.TierCreatedEvent;
import lombok.Getter;

@Getter
public class Tier {

    private String id;
    private String level;
    private int discountPercentage;

    public static Tier create(String id, String level, int discountPercentage){
        Tier tier = new Tier();
        tier.raiseEvent(new TierCreatedEvent(id, level, discountPercentage));
        return tier;
    }

    public void update(String id, int discountPercentage){
        this.raiseEvent(new DiscountUpdatedEvent(id, discountPercentage));
    }

    private void raiseEvent(Event event) {
        switch (event) {
            case TierCreatedEvent e ->{
                this.id = e.id();
                this.level = e.level();
                this.discountPercentage = e.discountPercentage();
            }
            case DiscountUpdatedEvent e -> this.discountPercentage = e.discountPercentage();
        }
    }

}
