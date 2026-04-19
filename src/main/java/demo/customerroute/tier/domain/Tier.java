package demo.customerroute.tier.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Getter
@NoArgsConstructor
@JsonPropertyOrder({"id", "level", "discountPercentage", "createdAt", "updatedAt"})
public class Tier{

    @Id
    private Long id;
    private String level;
    private int discountPercentage;
    private Instant createdAt;
    private Instant updatedAt;

    private Tier(Long id, String level, int discountPercentage, Instant createdAt, Instant updatedAt){
        this.id = id;
        this.level = level;
        this.discountPercentage = discountPercentage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Tier createNew(String level, int discountPercentage){
        String processedLevel = level.toLowerCase();
        return new Tier(null, processedLevel, discountPercentage, Instant.now(), Instant.now());
    }

    public void updateDiscount(int discountPercentage){
        this.discountPercentage = discountPercentage;
        this.updatedAt = Instant.now();
    }

}
