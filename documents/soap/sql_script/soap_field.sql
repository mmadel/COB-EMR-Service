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