package com.cob.emr.usecases.appointment.create.repetition;


import com.cob.emr.model.appointment.SchedulerAppointment;

public class AppointmentCloner {
    public static SchedulerAppointment clone(SchedulerAppointment original) {
        SchedulerAppointment model = new SchedulerAppointment();
        model.setTitle(original.getTitle());
        model.setNote(original.getNote());
        model.setTherapyUUID(original.getTherapyUUID());
        model.setAppointmentStatus(original.getAppointmentStatus());
        model.setClinicId(original.getClinicId());
        model.setPatientId(original.getPatientId());
        model.setPatientCaseId(original.getPatientCaseId());
        model.setAppointmentTypeId(original.getAppointmentTypeId());
        return model;
    }
}
