package demo.customerroute.customer.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Getter
@NoArgsConstructor
@JsonPropertyOrder({"id", "name", "tierId", "created_at", "updated_at"})
public class Customer{

    @Id
    private Long id;
    private String name;
    private Long tierId;
    private Instant createdAt;
    private Instant updatedAt;

    private Customer(Long id, String name, Long tierId, Instant createdAt, Instant updatedAt){
        this.id = id;
        this.name = name;
        this. tierId = tierId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Customer createNew(String name, Long tierId){
        String processedName = name.toLowerCase();
        return new Customer(null, processedName, tierId, Instant.now(), Instant.now());
    }

    public void updateCustomerTier(Long tierId){
        this.tierId = tierId;
        this.updatedAt = Instant.now();
    }

}
