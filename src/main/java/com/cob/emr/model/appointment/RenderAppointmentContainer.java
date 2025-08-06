package com.cob.emr.model.appointment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class RenderAppointmentContainer {
    List<RenderAppointment> addedRenderAppointment;
    List<RenderAppointment> deletedRenderAppointment;
}
