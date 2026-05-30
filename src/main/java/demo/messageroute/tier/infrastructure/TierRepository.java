package demo.messageroute.tier.infrastructure;

import demo.messageroute.tier.domain.Tier;
import jakarta.validation.constraints.NotBlank;
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
