package demo.customerroute.tier.infrastructure;

import demo.customerroute.tier.domain.TierEvent;
import org.springframework.data.repository.CrudRepository;

public interface TierEventRepository extends CrudRepository<TierEvent, Long> {
}
