package demo.customerroute.tier.application;

import demo.customerroute.tier.TierDto;
import demo.customerroute.tier.TierLookup;
import demo.customerroute.tier.exceptions.TierAlreadyExistException;
import demo.customerroute.tier.TierNotFoundException;
import demo.customerroute.tier.infrastructure.TierRepository;
import demo.customerroute.tier.domain.Tier;
import demo.customerroute.tier.web.CreateTierDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TierService implements TierLookup {

    private static final String BASE_TIER = "base";
    private final TierRepository repository;

    public TierService(TierRepository repository) {
        this.repository = repository;
    }

    /**
     * Resolves a tier by level and returns its data.
     * If level is null or blank, falls back to {@link #BASE_TIER}.
     *
     * @param level the tier level, e.g. "gold" or "silver"
     * @return the resolved tier as a {@link TierDto}
     * @throws TierNotFoundException if the resolved level does not exist
     */
    @Override
    @Transactional(readOnly = true)
    public TierDto processTier(String level) {
        String result = processLevel(level);
        return toDto(findByLevel(result));
    }

    @Transactional(readOnly = true)
    public List<TierDto> getAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public TierDto addTier(CreateTierDto dto) {
        String level = dto.level().toLowerCase();
        if (repository.existsByLevel(level)) {
            throw new TierAlreadyExistException(level);
        }

        Tier created = Tier.create(level, dto.discountPercentage());
        Tier saved = repository.save(created);

        return toDto(saved);
    }

    @Transactional
    public TierDto updateTierDiscount(String level, int newDiscount) {
        Tier tier = findByLevel(level);

        int oldDiscount = tier.getDiscountPercentage();
        tier.update(level, oldDiscount, newDiscount);
        return toDto(repository.save(tier));
    }


    //PRIVATE HELPERS
    private Tier findByLevel(String level) {
        return repository.findByLevel(level)
                .orElseThrow(() -> new TierNotFoundException(level));
    }

    private String processLevel(String level) {
        return (level == null || level.isBlank()) ? BASE_TIER : level;
    }

    private TierDto toDto(Tier tier) {
        return new TierDto(tier.getId(),
                tier.getLevel(),
                tier.getDiscountPercentage());
    }

}
