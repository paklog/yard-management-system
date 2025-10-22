package com.paklog.yard.application.command;

import com.paklog.yard.domain.valueobject.AppointmentWindow;
import lombok.*;
import javax.validation.constraints.*;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleAppointmentCommand {
    @NotBlank
    private String carrierId;
    private String trailerId;
    @NotNull
    private AppointmentWindow window;
    private String appointmentType;
    private String referenceNumber;
    private Map<String, Object> cargo;
}
