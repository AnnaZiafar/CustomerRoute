package demo.customerroute.exception;

public class TierAlreadyExistException extends RuntimeException {
    public TierAlreadyExistException(String level) {
        super("Tier " + level + " already exists. Use PUT to update discountpercentage.");
    }
}
