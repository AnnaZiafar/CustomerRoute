package demo.customerroute.customer.domain;

import lombok.Getter;

@Getter
public class Customer{

    private final Integer id;
    private final String customer;
    private final int tierId;

    public Customer(Integer id, String customer, int tierId){
        this.id = id;
        this.customer = customer;
        this. tierId = tierId;
    }

    public static Customer createNew(String customer, int tierId){
        return new Customer(null, customer, tierId);
    }

}
