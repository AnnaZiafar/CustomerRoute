package demo.customerroute.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TierNotFoundException extends RuntimeException{

    public TierNotFoundException(String tierLevel){
        super("Could not find tier level: " + tierLevel);
    }

    public TierNotFoundException(Long tierId){
        super("Could not find tier with id: " + tierId);
    }
}
