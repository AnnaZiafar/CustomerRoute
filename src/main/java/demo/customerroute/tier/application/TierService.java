package demo.customerroute.tier.application;

import demo.customerroute.exception.TierAlreadyExistException;
import demo.customerroute.exception.TierNotFoundException;
import demo.customerroute.tier.TierLookup;
import demo.customerroute.tier.domain.Tier;
import demo.customerroute.tier.domain.TierRepository;
import demo.customerroute.tier.web.TierDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TierService implements TierLookup {

    private static final String DEFAULT_TIER = "base";
    private final TierRepository repository;

    public TierService(TierRepository repository){
        this.repository = repository;
    }

    @Override
    public String validateTier(String tier){
        String result = defaultBaseIfTierIsMissing(tier);
        if(!repository.existsByLevel(result)){
            throw new TierNotFoundException("Tier " + result + " does not exist. " +
                    "Create tier with appropriate discount percentage before using it in errands.");
        }

        return result;
    }

    @Override
    public Long getTierId(String tierLevel) {
        return findByLevel(tierLevel).getId();
    }

    @Override
    public String getTierLevel(Long id) {
        return findById(id).getLevel();
    }

    @Override
    public int getDiscountPercentage(String tierLevel) {
        return findByLevel(tierLevel).getDiscountPercentage();
    }

    @Transactional
    public Tier addNewTier(TierDto dto){
        String level = dto.level().toLowerCase();
        if (repository.existsByLevel(level)) {
            throw new TierAlreadyExistException(level);
        }

        Tier newTier = Tier.createNew(dto.level(), dto.discountPercentage());
        return repository.save(newTier);
    }

    @Transactional
    public Tier updateTier(TierDto tier){
        Tier tierToUpdate = findByLevel(tier.level());
        tierToUpdate.updateDiscount(tier.discountPercentage());
        return repository.save(tierToUpdate);
    }

    public List<Tier> findAll(){
        return repository.findAll();
    }

    private Tier findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new TierNotFoundException(id));
    }

    private Tier findByLevel(String level){
        return repository.findByLevel(level)
                .orElseThrow(() -> new TierNotFoundException(level));
    }

    private String defaultBaseIfTierIsMissing(String tier){
        return (tier == null || tier.isBlank()) ? DEFAULT_TIER : tier;
    }

}
