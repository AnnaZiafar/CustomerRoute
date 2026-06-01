package demo.customerroute.tier.infrastructure;

import demo.customerroute.tier.domain.TierEvent;
import demo.customerroute.tier.events.DiscountUpdatedEvent;
import demo.customerroute.tier.events.TierCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class TierEventListener {

    private final TierEventRepository repository;
    private final ObjectMapper mapper;

    @EventListener
    public void onCreate(TierCreatedEvent event) {
        String user = getCurrentUser();
        String payload = mapper.writeValueAsString(
                Map.of(
                        "level", event.level(),
                        "discountPercentage", event.discountPercentage()
                )
        );
        repository.save(TierEvent.create(event.level(), "TIER_CREATED", payload, user));
    }

    @EventListener
    public void onUpdate(DiscountUpdatedEvent event){
        String user = getCurrentUser();
        String payload = mapper.writeValueAsString(
                Map.of(
                        "level", event.level(),
                        "oldDiscountPercentage", event.oldDiscount(),
                        "newDiscountPercentage", event.newDiscount()
                )
        );
        repository.save(TierEvent.create(event.level(), "DISCOUNT_UPDATED", payload, user));
    }

    private String getCurrentUser(){
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }
}
