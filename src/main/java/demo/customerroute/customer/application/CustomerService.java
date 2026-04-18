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

    @Override
    @Transactional
    public CustomerInfo processCustomer(CustomerInfo customerInfo){
        Long tierId = getCustomerTierId(customerInfo.tier());
        String name = customerInfo.customer();

        getCustomer(name).ifPresentOrElse(
                existing -> {
                    existing.updateCustomerTier(tierId);
                    repository.save(existing);
                },
                () -> {
                    repository.save(Customer.createNew(name, tierId));
                }
        );

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
