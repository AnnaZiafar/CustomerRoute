package demo.customerroute.customer;

import demo.customerroute.tier.TierService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final JdbcTemplate jdbcTemplate;
    private final TierService tierService;

    public CustomerService(JdbcTemplate jdbcTemplate, TierService tierService){
        this.jdbcTemplate = jdbcTemplate;
        this.tierService = tierService;
    }

    public void processCustomer(Customer customer){
        String tier = tierService.validateTier(customer.tier());
        saveCustomer(customer.name(), tier);
    }

    private void saveCustomer(String customer, String tier){
        String tierQuery = "Select id from tiers where tier_name = ?";
        Integer tierId = jdbcTemplate.queryForObject(tierQuery, Integer.class, tier);

        String insertCustomer = "Insert into customer (name, tier_id) " +
                "values(?, ?) " +
                "on duplicate key update tier_id = values(tier_id)";

        jdbcTemplate.update(insertCustomer, customer, tierId);
    }


}
