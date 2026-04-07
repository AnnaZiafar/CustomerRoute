package demo.customerroute.tier.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TierRepository {

    private final JdbcTemplate jdbcTemplate;

    public TierRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Tier> findAll(){
        String query = "Select * from tier";

        return jdbcTemplate.query(query, (rs, rowNumber) -> new Tier(
                rs.getInt("id"),
                rs.getString("tier_name"),
                rs.getInt("discount_percentage")
        ));
    }

    public boolean existByName(String tier){
        String query = "Select count(*) from tier where tier = ?";
        Integer rows = jdbcTemplate.queryForObject(query, Integer.class, tier);
        return rows != null && rows > 0;
    }

    public void saveTier(Tier tier){
        String query = "Insert into tier (tier, discount_percentage) values(?, ?)";
        jdbcTemplate.update(query, tier.getTier().toLowerCase(), tier.getDiscountPercentage());
    }

    public void updateTier(Tier tier){
        String query = "Update tier set discount_percentage = ? where tier = ?";
        jdbcTemplate.update(query, tier.getDiscountPercentage(), tier.getTier());
    }

    public int getId(String tier){
        String query = "Select id from tier where tier = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, tier);
    }


    public int getDiscountPercentage(String tier) {
        String query = "Select discount_percentage from tier where tier = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, tier);
    }
}
