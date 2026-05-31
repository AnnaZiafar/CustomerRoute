package demo.messageroute.tier.infrastructure;

import demo.messageroute.tier.domain.TierEvent;
import org.springframework.data.repository.CrudRepository;

public interface TierEventRepository extends CrudRepository<TierEvent, Long> {
}
