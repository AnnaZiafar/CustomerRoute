package demo.customerroute.customer.application;

import demo.customerroute.customer.CustomerLookup;
import demo.customerroute.customer.domain.Customer;
import demo.customerroute.customer.domain.CustomerRepository;
import demo.customerroute.tier.TierLookup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService implements CustomerLookup {

    private final CustomerRepository customerRepository;
    private final TierLookup tierLookup;

    public CustomerService(CustomerRepository customerRepository, TierLookup tierLookup){
        this.customerRepository = customerRepository;
        this.tierLookup = tierLookup;
    }

    @Override
    @Transactional
    public CustomerInfo processCustomer(CustomerInfo customerInfo){
        String validTier = tierLookup.validateTier(customerInfo.tier());
        int tierId = tierLookup.getTierId(validTier);
        String customer = customerInfo.customer();

        if(customerRepository.existByName(customer))
            customerRepository.updateCustomer(customer, tierId);
        else
            customerRepository.saveCustomer(customerInfo.customer(), tierId);

        return new CustomerInfo(customer, validTier);
    }

}
