package demo.customerroute.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TierAlreadyExistException extends RuntimeException {
    public TierAlreadyExistException(String level) {
        super("Tier " + level + " already exists. Use PUT to update discountpercentage.");
    }
}
