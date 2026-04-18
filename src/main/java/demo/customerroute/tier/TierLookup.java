package demo.customerroute.tier;

public interface TierLookup {

    String validateTier(String tier);

    Long getTierId(String tier);

    String getTierLevel(Long id);

    int getDiscountPercentage(String tier);
}
