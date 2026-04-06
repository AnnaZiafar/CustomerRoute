package demo.customerroute.tier;

import demo.customerroute.exception.TierNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TierService {

    private final JdbcTemplate jdbcTemplate;

    public TierService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public String validateTier(String tier){
        String result = defaultBaseIfTierIsMissing(tier);

        if(!isTierRegistered(tier)){
            throw new TierNotFoundException("Tier " + tier + " does not exist. " +
                    "Create tier with appropriate discount percentage before using it in errands.");
        }

        return result;
    }

    private String defaultBaseIfTierIsMissing(String tier){
        if (tier == null || tier.isBlank())
            tier = "base";

        return tier;
    }

    private boolean isTierRegistered(String tier){
        String query = "Select count(*) from tiers where tier_name = ?";
        Integer rows = jdbcTemplate.queryForObject(query, Integer.class, tier);
        return rows != null && rows > 0;
    }
}
