delete from  new_soap_field where category_id =29

delete from objective_profile_soap_field
where soap_field_id in (
    select id from new_soap_field where category_id =29
)
