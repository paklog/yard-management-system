package com.paklog.yard.domain.event;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class TrailerDepartedEvent extends DomainEvent {
    private String trailerId;
    private java.time.Instant departedAt;
    private Long dwellTimeMinutes;
}
