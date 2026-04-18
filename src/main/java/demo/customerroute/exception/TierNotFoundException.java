package demo.customerroute.exception;

public class TierNotFoundException extends RuntimeException{

    public TierNotFoundException(String tierLevel){
        super("Could not find tier level: " + tierLevel);
    }

    public TierNotFoundException(Long tierId){
        super("Could not find tier with id: " + tierId);
    }
}
