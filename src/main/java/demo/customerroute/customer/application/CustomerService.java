package demo.customerroute.customer.application;

import demo.customerroute.customer.CustomerInfo;
import demo.customerroute.customer.CustomerLookup;
import demo.customerroute.customer.domain.Customer;
import demo.customerroute.customer.domain.CustomerRepository;
import demo.customerroute.tier.TierLookup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerService implements CustomerLookup {

    private final CustomerRepository repository;
    private final TierLookup tierLookup;

    public CustomerService(CustomerRepository repository, TierLookup tierLookup){
        this.repository = repository;
        this.tierLookup = tierLookup;
    }

    /**
     * If customer is present the tier is updated.
     * Otherwise, the customer is persisted into db.
     * @param customerInfo data transfer object containing customer details
     * @return CustomerInfo regarding existing/new Customer
     */
    @Override
    @Transactional
    public CustomerInfo processCustomer(CustomerInfo customerInfo){
        String name = customerInfo.customer().toLowerCase();
        Long tierId = getCustomerTierId(customerInfo.tier());

        Optional<Customer> customer = getCustomer(name);

        if(customer.isPresent()){
            Customer existingCustomer = customer.get();
            existingCustomer.updateCustomerTier(tierId);
            repository.save(existingCustomer);
        } else {
            Customer newCustomer = Customer.createNew(name, tierId);
            repository.save(newCustomer);
        }

        return new CustomerInfo(name, getCustomerTierName(tierId));
    }

    private Long getCustomerTierId(String tier){
        String validTier = tierLookup.validateTier(tier);
        return tierLookup.getTierId(validTier);
    }

    private String getCustomerTierName(Long id){
        return tierLookup.getTierLevel(id);
    }

    private Optional<Customer> getCustomer(String name){
        return Optional.ofNullable(repository.findCustomerByName(name));
    }

}
