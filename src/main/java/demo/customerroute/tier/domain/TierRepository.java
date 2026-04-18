package demo.customerroute.tier.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TierRepository extends CrudRepository<Tier, Long> {
    
    @Override
    List<Tier> findAll();
    
    boolean existsByLevel(String level);

    Optional<Tier> findByLevel(String level);
}
