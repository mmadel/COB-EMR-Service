package com.cob.emr.usecases.appointment.create.repetition;


import com.cob.emr.model.appointment.AppointmentModel;

public class AppointmentCloner {
    public static AppointmentModel clone(AppointmentModel original) {
        AppointmentModel model = new AppointmentModel();
        model.setTitle(original.getTitle());
        model.setNote(original.getNote());
        model.setTherapyUUID(original.getTherapyUUID());
        model.setAppointmentStatus(original.getAppointmentStatus());
        model.setClinicId(original.getClinicId());
        model.setPatientId(original.getPatientId());
        model.setPatientCaseId(original.getPatientCaseId());
        model.setAppointmentTypeId(original.getAppointmentTypeId());
        model.setAppointmentType(original.getAppointmentType());
        model.setAppointmentTypeColor(original.getAppointmentTypeColor());
        model.setAppointmentRepeat(original.getAppointmentRepeat());
        model.setAppointmentCancelNoShowReason(original.getAppointmentCancelNoShowReason());
        return model;
    }
}
