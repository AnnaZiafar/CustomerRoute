package demo.customerroute.messages;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException() {
        super("Missing required field 'customer'");
    }
}
