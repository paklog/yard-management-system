package com.paklog.yard.domain.valueobject;

import java.time.Instant;

public record DockAssignment(
    String dockId,
    String dockName,
    Instant assignedAt,
    Instant expectedCompletionTime,
    String assignedBy,
    String assignmentReason
) {}
