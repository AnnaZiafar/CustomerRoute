package demo.customerroute.tier.web;

import demo.customerroute.tier.domain.Tier;
import demo.customerroute.tier.application.TierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tiers")
public class TierController {

    private final TierService tierService;

    public TierController(TierService tierService){
        this.tierService = tierService;
    }

    @GetMapping
    private List<Tier> showAllTiers(){
        return tierService.findAll();
    }

    @PostMapping
    private void addOrUpdateTier(@RequestBody Tier tier){
        tierService.processTier(tier);
    }

}
