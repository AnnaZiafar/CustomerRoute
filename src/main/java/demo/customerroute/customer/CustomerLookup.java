package demo.customerroute.customer;

public interface CustomerLookup {

    CustomerInfo processCustomer(CustomerInfo customerInfo);

    record CustomerInfo(String customer, String tier){}
}
