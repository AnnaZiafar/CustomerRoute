package demo.messageroute.tier.web;

import demo.messageroute.tier.TierDto;
import demo.messageroute.tier.application.TierService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tiers")
public class TierController {

    private final TierService service;

    public TierController(TierService service){
        this.service = service;
    }

    @GetMapping
    public List<TierDto> showAllTiers(){
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<TierDto> addTier(@RequestBody @Valid CreateTierDto dto) {
        TierDto created = service.addTier(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{level}")
    public ResponseEntity<TierDto> updateTierDiscount(@PathVariable String level,
                                                      @RequestBody @Valid UpdateTierDto dto) {
        TierDto updated = service.updateTierDiscount(level, dto.discountPercentage());
        return ResponseEntity.ok(updated);
    }

}
