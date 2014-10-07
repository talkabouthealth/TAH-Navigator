DELETE FROM nav.appointment;
DELETE FROM nav.patientcttse;
DELETE FROM nav.patientctt;
ALTER TABLE nav.appointment ADD COLUMN caremember_name character varying(100);
ALTER TABLE nav.appointment ADD COLUMN purpose_text character varying(200);
ALTER TABLE nav.appointment ADD COLUMN treatment_process_step character varying(200);
ALTER TABLE nav.patientctt ALTER COLUMN dose_reduction TYPE character varying(100);