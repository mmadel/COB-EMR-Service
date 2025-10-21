-- Step 1: Shift existing fields
-- shift by 4 as we insert four fields
UPDATE soap_field_note_type
SET field_order = field_order + 4
WHERE medical_note_id = 1
  AND field_order > 8;

-- Step 2: Insert the new fields
INSERT INTO soap_field_note_type (field_id, medical_note_id, field_order)
VALUES
    (296, 1, 9),
    (297, 1, 10),
    (298, 1, 11),
    (299, 1, 12);
