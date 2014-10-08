DELETE FROM nav.appointment;
DELETE FROM nav.patientcttse;
DELETE FROM nav.patientctt;
ALTER TABLE nav.appointment ADD COLUMN caremember_name character varying(100);
ALTER TABLE nav.appointment ADD COLUMN purpose_text character varying(200);
ALTER TABLE nav.appointment ADD COLUMN treatment_process_step character varying(200);
ALTER TABLE nav.patientctt ALTER COLUMN dose_reduction TYPE character varying(100);
INSERT INTO nav.designationmaster (designation) VALUES ('Nurse'), ('Radiation Therapist'), ('Breast Surgeon'), ('Hematologist'), ('Radiologist'), ('Social Worker'), ('Cardiologist'), ('Urologist'), ('Primary Care Physician'), ('Internal Medicine'), ('OBGYN'), ('Gynecologist'), ('Brain Surgeon'), ('Surgical Oncologist'), ('Urologic Oncologist'), ('Neuro-surgeon'), ('Neuro-oncologist'), ('Family Doctor'), ('Palliative Care Physician'), ('Endocrinologist'), ('Gynecologic Oncologist'), ('Gastroenterologist'), ('Dermatologist'), ('Plastic Surgeon');
