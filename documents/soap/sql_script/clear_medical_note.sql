SET SQL_SAFE_UPDATES = 0;
delete from  initial_evaluation;
delete from daily_note;
delete from progress_note;
delete from quick_discharge_note;
delete from full_discharge_note;
delete from medical_note;
select * from clinical_emr_user