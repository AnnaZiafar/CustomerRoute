package demo.customerroute.exception;

public class TierNotFoundException extends RuntimeException{

    public TierNotFoundException(String message){
        super(message);
    }
}
