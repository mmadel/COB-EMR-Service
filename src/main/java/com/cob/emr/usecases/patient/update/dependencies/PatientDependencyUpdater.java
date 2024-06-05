package com.cob.emr.usecases.patient.update.dependencies;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.patient.PatientDependencyModel;
import com.cob.emr.model.patient.PatientModel;

import java.util.List;
import java.util.stream.Collectors;

public abstract class PatientDependencyUpdater<T extends PatientDependencyModel> {
    protected abstract void update(PatientModel model, Patient patient);

    protected List<T> findAdded(List<T> updated, List<T> original) {
        return updated.stream()
                .filter(item -> !original.contains(item))
                .collect(Collectors.toList());
    }

    protected List<T> findDeleted(List<T> updated, List<T> original) {
        return original.stream()
                .filter(item -> !updated.contains(item))
                .collect(Collectors.toList());
    }
}
