package com.paklog.yard.domain.valueobject;

import lombok.*;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DockAssignment {
    private String dockId;
    private String dockName;
    private Instant assignedAt;
    private Instant expectedCompletionTime;
    private String assignedBy;
    private String assignmentReason;
}
