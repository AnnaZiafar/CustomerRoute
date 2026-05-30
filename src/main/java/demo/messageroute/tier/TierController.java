package demo.messageroute.tier;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tiers")
public class TierController {

    TierService service;

    public TierController(TierService service){
        this.service = service;
    }

    @GetMapping
    List<Tier> showAllTiers(){
        return service.getAll();
    }
}
