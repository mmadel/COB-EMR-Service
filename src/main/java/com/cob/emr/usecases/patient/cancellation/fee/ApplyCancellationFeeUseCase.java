package com.cob.emr.usecases.patient.cancellation.fee;

import com.cob.emr.model.patient.PatientCancellationFeeModel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Component
public class ApplyCancellationFeeUseCase {
    public Double apply(Long appointmentStartDate, Long appointmentCancelDate, PatientCancellationFeeModel rule) {
        boolean isFeeApplied = false;
        switch (rule.getFeeDurationUnit()) {
            case Day:
                isFeeApplied = isFeeApplicableByDay(appointmentStartDate, appointmentCancelDate, rule.getDuration());
                break;
            case Hour:
                isFeeApplied = isFeeApplicableByHour(appointmentStartDate, appointmentCancelDate, rule.getDuration());
                break;
        }
        if (rule.getIsEnabled())
            return isFeeApplied ? rule.getAmount() : 0.0;
        else
            return 0.0;
    }

    public boolean isFeeApplicableByHour(long appointmentStartDate, long cancellationDate, Integer duration) {
        LocalDateTime appointmentStart = convertToDateTime(appointmentStartDate);
        LocalDateTime cancellationTime = convertToDateTime(cancellationDate);

        LocalDateTime feeThreshold = appointmentStart.minus(duration, ChronoUnit.HOURS);
        return !cancellationTime.isBefore(feeThreshold);
    }

    public boolean isFeeApplicableByDay(long appointmentStartDate, long cancellationDate, Integer duration) {
        LocalDateTime appointmentStart = convertToDateTime(appointmentStartDate);
        LocalDateTime cancellationTime = convertToDateTime(cancellationDate);

        LocalDateTime feeThreshold = appointmentStart.minus(duration, ChronoUnit.DAYS);
        return !cancellationTime.isBefore(feeThreshold);
    }


    private LocalDateTime convertToDateTime(long millis) {
        return Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
