package demo.customerroute.tier.web;

import demo.customerroute.tier.domain.Tier;
import demo.customerroute.tier.application.TierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tiers/api")
public class TierController {

    private final TierService tierService;

    public TierController(TierService tierService){
        this.tierService = tierService;
    }

    @GetMapping
    public List<Tier> showAllTiers(){
        return tierService.findAll();
    }

    @PostMapping
    public Tier addTier(@RequestBody TierDto tier){
        return tierService.addNewTier(tier);
    }

    @PutMapping
    public Tier updateTierDiscount(@RequestBody TierDto tier){
        return tierService.updateTier(tier);
    }

}
