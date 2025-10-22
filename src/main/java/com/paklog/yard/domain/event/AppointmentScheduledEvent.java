package com.paklog.yard.domain.event;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class AppointmentScheduledEvent extends DomainEvent {
    private String appointmentId;
    private String trailerId;
    private String dockId;
    private java.time.Instant scheduledTime;
}
