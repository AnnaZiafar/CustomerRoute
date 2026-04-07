package demo.customerroute.tier.application;

import demo.customerroute.exception.TierNotFoundException;
import demo.customerroute.tier.TierLookup;
import demo.customerroute.tier.domain.Tier;
import demo.customerroute.tier.domain.TierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TierService implements TierLookup {

    private final TierRepository tierRepository;

    public TierService(TierRepository tierRepository){
        this.tierRepository = tierRepository;
    }

    @Override
    public String validateTier(String tier){
        String result = defaultBaseIfTierIsMissing(tier);

        if(!tierRepository.existByName(result)){
            throw new TierNotFoundException("Tier " + result + " does not exist. " +
                    "Create tier with appropriate discount percentage before using it in errands.");
        }

        return result;
    }

    @Override
    public int getTierId(String tier) {
        return tierRepository.getId(tier);
    }

    @Override
    public int getDiscountPercentage(String tier) {
        return tierRepository.getDiscountPercentage(tier);
    }

    private String defaultBaseIfTierIsMissing(String tier){
        return (tier == null || tier.isBlank()) ? "base" : tier;
    }

    public void processTier(Tier tier){
        Tier newTier = Tier.createNew(tier.getTier(), tier.getDiscountPercentage());

        if(tierRepository.existByName(newTier.getTier()))
            tierRepository.updateTier(newTier);
        else
            tierRepository.saveTier(newTier);
    }

    public List<Tier> findAll(){
        return tierRepository.findAll();
    }

}
