package com.paklog.yard.application.command;

import lombok.*;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckOutTrailerCommand {
    @NotBlank
    private String trailerId;
    private String checkedOutBy;
}
