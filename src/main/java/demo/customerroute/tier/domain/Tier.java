package demo.customerroute.tier.domain;

import lombok.Getter;

@Getter
public class Tier{

    private final Integer id;
    private final String tier;
    private final int discountPercentage;

    public Tier(Integer id, String tier, int discountPercentage){
        this.id = id;
        this.tier = tier;
        this.discountPercentage = discountPercentage;
    }

    public static Tier createNew(String tier, int discountPercentage){
        return new Tier(null, tier, discountPercentage);
    }
}
