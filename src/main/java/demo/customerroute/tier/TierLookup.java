package demo.customerroute.tier;

public interface TierLookup {

    String validateTier(String tier);

    int getTierId(String tier);

    int getDiscountPercentage(String tier);
}
