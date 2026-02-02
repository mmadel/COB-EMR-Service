ALTER TABLE clinical_emr_user ADD COLUMN status VARCHAR(20) DEFAULT 'ACTIVE';
ALTER TABLE clerical_emr_user ADD COLUMN status VARCHAR(20) DEFAULT 'ACTIVE';

UPDATE clinical_emr_user SET status = 'ACTIVE' WHERE status IS NULL;
UPDATE clerical_emr_user SET status = 'ACTIVE' WHERE status IS NULL;
