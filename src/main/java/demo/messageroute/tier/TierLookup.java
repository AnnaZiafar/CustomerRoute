package demo.messageroute.tier;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public interface TierLookup {
    TierDto processTier(String tier);
}
