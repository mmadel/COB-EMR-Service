-- Run this AFTER the application has created the audit_log table via ddl-auto=update.
-- This script restructures the table to support range partitioning by performed_at.

-- Step 1: Drop existing indexes (Hibernate-generated)
DROP INDEX idx_clinic_entity ON audit_log;
DROP INDEX idx_performed_at ON audit_log;

-- Step 2: Restructure PK to include partition key
ALTER TABLE audit_log
    DROP PRIMARY KEY,
    ADD PRIMARY KEY (id, performed_at);

-- Step 3: Re-add indexes
CREATE INDEX idx_clinic_entity ON audit_log (clinic_id, entity_name, entity_id, performed_at);
CREATE INDEX idx_performed_at ON audit_log (performed_at);

-- Step 4: Apply range partitioning by quarter
ALTER TABLE audit_log
PARTITION BY RANGE (performed_at) (
    PARTITION p2025_q1 VALUES LESS THAN (1743465600000),  -- 2025-04-01
    PARTITION p2025_q2 VALUES LESS THAN (1751328000000),  -- 2025-07-01
    PARTITION p2025_q3 VALUES LESS THAN (1759290000000),  -- 2025-10-01
    PARTITION p2025_q4 VALUES LESS THAN (1767225600000),  -- 2026-01-01
    PARTITION p2026_q1 VALUES LESS THAN (1774994400000),  -- 2026-04-01
    PARTITION p2026_q2 VALUES LESS THAN (1782856800000),  -- 2026-07-01
    PARTITION p2026_q3 VALUES LESS THAN (1790819200000),  -- 2026-10-01
    PARTITION p2026_q4 VALUES LESS THAN (1798761600000),  -- 2027-01-01
    PARTITION p_future  VALUES LESS THAN MAXVALUE
);
