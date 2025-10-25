package com.paklog.yard.application.command;

import jakarta.validation.constraints.NotBlank;

public record AssignDockCommand(
    @NotBlank
    String trailerId,
    String dockId,
    String assignedBy
) {}
