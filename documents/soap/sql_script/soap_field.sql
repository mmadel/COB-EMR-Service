#query soap field parent_id
SELECT  * from new_soap_field
where  JSON_EXTRACT(field_value, '$.parentField') ='75df6b64-a797-4457-b3b1-c1eee48500f4';

#query soap field id
SELECT  * from new_soap_field
where  JSON_EXTRACT(field_value, '$.idField.id') ='92d3c99e-72b7-42f9-8f4b-c0b0d8e82fad';

#query soap field display_name
SELECT  * from new_soap_field
where  JSON_EXTRACT(field_value, '$.display') ='Mental Status/Cognitive Function Appears Impaired'

#query some columns of the soap field by type
select
    JSON_UNQUOTE(JSON_EXTRACT(field_value, '$.idField.id')) AS 'field',
        JSON_UNQUOTE(JSON_EXTRACT(field_value, '$.parentField')) AS 'parent',
        category_id,
    JSON_UNQUOTE(JSON_EXTRACT(field_value, '$.display')) AS 'display',
        JSON_UNQUOTE(JSON_EXTRACT(field_value, '$.name')) AS 'name'
from new_soap_field
where
        JSON_EXTRACT(field_value, '$.type') = 'non-material-handling';


#query id,parent_id,display_name of the soap field display
select
    JSON_UNQUOTE(JSON_EXTRACT(field_value, '$.idField.id')) AS 'field',
        JSON_UNQUOTE(JSON_EXTRACT(field_value, '$.parentField')) AS 'parent',
        category_id,
    JSON_UNQUOTE(JSON_EXTRACT(field_value, '$.display')) AS 'display',
        JSON_UNQUOTE(JSON_EXTRACT(field_value, '$.name')) AS 'name'
from new_soap_field
where
        JSON_EXTRACT(field_value, '$.display') = 'test';

## get assign fields to profile
SELECT sc.name,
       sf.field_name,
       sf.field_value
FROM new_soap_field sf
         INNER JOIN new_soap_section_category sc ON sf.category_id = sc.id
WHERE sf.id IN
      (SELECT soap_field_id
       FROM objective_profile_soap_field sfp
                INNER JOIN soap_objective_profile sp ON sfp.objective_profile_id = sp.id
       WHERE sp.id = 4)
ORDER BY sf.id DESC