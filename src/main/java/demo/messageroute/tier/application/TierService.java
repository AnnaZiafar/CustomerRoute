package demo.messageroute.tier.application;

import demo.messageroute.tier.TierDto;
import demo.messageroute.tier.exceptions.TierAlreadyExistException;
import demo.messageroute.tier.exceptions.TierNotFoundException;
import demo.messageroute.tier.infrastructure.TierRepository;
import demo.messageroute.tier.domain.Tier;
import demo.messageroute.tier.web.CreateTierDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TierService {

    private final TierRepository repository;

    public TierService(TierRepository repository){
        this.repository = repository;
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
        if(repository.existsByLevel(level)){
            throw new TierAlreadyExistException(level);
        }

        Tier created = Tier.create(level, dto.discountPercentage());
        Tier saved = repository.save(created);

        return toDto(saved);
    }

    @Transactional
    public TierDto updateTierDiscount(CreateTierDto dto) {
        Tier tier = repository.findByLevel(dto.level())
                .orElseThrow(() -> new TierNotFoundException(dto.level()));

        tier.update(dto.level(), dto.discountPercentage());
        return toDto(repository.save(tier));
    }



    //PRIVATE HELPERS

    private TierDto toDto(Tier tier){
        return new TierDto(tier.getId(),
                tier.getLevel(),
                tier.getDiscountPercentage());
    }

}
