package demo.messageroute.tier;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import demo.messageroute.tier.events.DiscountUpdatedEvent;
import demo.messageroute.tier.events.Event;
import demo.messageroute.tier.events.TierCreatedEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@JsonPropertyOrder({"id", "level", "discountPercentage"})
public class Tier {

    @Id
    private Long id;
    private String level;
    private int discountPercentage;

    public static Tier create(Long id, String level, int discountPercentage){
        Tier tier = new Tier();
        tier.raiseEvent(new TierCreatedEvent(id, level, discountPercentage));
        return tier;
    }

    public void update(Long id, int discountPercentage){
        this.discountPercentage = discountPercentage;
        this.raiseEvent(new DiscountUpdatedEvent(id, discountPercentage));
    }

    private void raiseEvent(Event event) {
        switch (event) {
            case TierCreatedEvent e ->{
                this.level = e.level();
                this.discountPercentage = e.discountPercentage();
            }
            case DiscountUpdatedEvent e -> this.discountPercentage = e.discountPercentage();
        }
    }

}
