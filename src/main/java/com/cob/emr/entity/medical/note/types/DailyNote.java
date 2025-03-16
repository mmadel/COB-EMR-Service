package com.cob.emr.entity.medical.note.types;

import com.cob.emr.entity.medical.note.MedicalNote;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "daily_note")
public class DailyNote extends MedicalNote {
}
