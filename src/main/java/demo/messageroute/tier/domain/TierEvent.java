package demo.messageroute.tier.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table("tier_events")
public class TierEvent {

    @Id
    private Long id;
    private String level;
    private String eventType;
    private String payload;
    private String performedBy;
    private LocalDateTime occurredAt;

    public static TierEvent create(String level, String eventType, String payload, String performedBy){
        TierEvent event = new TierEvent();
        event.level = level;
        event.eventType = eventType;
        event.payload = payload;
        event.performedBy = performedBy;
        event.occurredAt = LocalDateTime.now();

        return event;
    }

}
