package demo.customerroute.tier.infrastructure;

import demo.customerroute.tier.domain.Tier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TierRepository extends CrudRepository<Tier, Long> {

    @Override
    List<Tier> findAll();

    boolean existsByLevel(String level);

    Optional<Tier> findByLevel(String level);
}
