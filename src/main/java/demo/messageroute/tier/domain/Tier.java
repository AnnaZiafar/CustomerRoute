package demo.messageroute.tier.domain;

import demo.messageroute.tier.events.DiscountUpdatedEvent;
import demo.messageroute.tier.events.TierEvent;
import demo.messageroute.tier.events.TierCreatedEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.AbstractAggregateRoot;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tier extends AbstractAggregateRoot<Tier> {

    @Id
    private Long id;
    private String level;
    private int discountPercentage;

    public static Tier create(String level, int discountPercentage){
        Tier tier = new Tier();
        tier.raiseEvent(new TierCreatedEvent(level, discountPercentage));
        return tier;
    }

    public void update(String level, int oldDiscount, int newDiscount){
        this.raiseEvent(new DiscountUpdatedEvent(level, oldDiscount, newDiscount));
    }

    private void raiseEvent(TierEvent event) {
        registerEvent(event);
        switch (event) {
            case TierCreatedEvent e ->{
                this.level = e.level();
                this.discountPercentage = e.discountPercentage();
            }
            case DiscountUpdatedEvent e -> this.discountPercentage = e.newDiscount();
        }
    }

}
