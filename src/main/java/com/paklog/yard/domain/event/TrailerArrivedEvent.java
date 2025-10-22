package com.paklog.yard.domain.event;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class TrailerArrivedEvent extends DomainEvent {
    private String trailerId;
    private String trailerNumber;
    private java.time.Instant arrivedAt;
}
