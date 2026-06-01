package demo.customerroute.tier.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TierAlreadyExistException extends RuntimeException {
    public TierAlreadyExistException(String level) {
        super("Tier " + level + " already exists.");
    }
}
