package com.paklog.yard.application.command;

import jakarta.validation.constraints.NotBlank;

public record CheckOutTrailerCommand(
    @NotBlank
    String trailerId,
    String checkedOutBy
) {}
