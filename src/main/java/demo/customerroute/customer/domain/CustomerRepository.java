package demo.customerroute.customer.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomerRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void updateCustomer(String customer, int tierId){
        String query = "Update customer set tier_id = ? where customer = ?";
        jdbcTemplate.update(query, tierId, customer);
    }

    public void saveCustomer(String customer, int tierId){
        String query = "Insert into customer (customer, tier_id) " +
                "values(?, ?) ";

        jdbcTemplate.update(query, customer.toLowerCase(), tierId);
    }

    public boolean existByName(String customer){
        String sql = "Select count(*) from customer where customer = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, customer);
        return count > 0;
    }
}
