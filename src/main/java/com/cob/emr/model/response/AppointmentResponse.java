package com.cob.emr.model.response;

import com.cob.emr.model.appointment.AppointmentModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class AppointmentResponse {
    Integer number_of_records;
    Integer number_of_matching_records;
    List<AppointmentModel> records;
}
