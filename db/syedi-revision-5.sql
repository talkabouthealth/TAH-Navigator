DELETE FROM nav.appointment;
DELETE FROM nav.patientcttse;
DELETE FROM nav.patientctt;
ALTER TABLE nav.appointment ADD COLUMN caremember_name character varying(100);
ALTER TABLE nav.appointment ADD COLUMN purpose_text character varying(200);
ALTER TABLE nav.appointment ADD COLUMN treatment_process_step character varying(200);
ALTER TABLE nav.patientctt ALTER COLUMN dose_reduction TYPE character varying(100);
INSERT INTO nav.designationmaster (designation) VALUES ('Nurse'), ('Radiation Therapist'), ('Breast Surgeon'), ('Hematologist'), ('Radiologist'), ('Social Worker'), ('Cardiologist'), ('Urologist'), ('Primary Care Physician'), ('Internal Medicine'), ('OBGYN'), ('Gynecologist'), ('Brain Surgeon'), ('Surgical Oncologist'), ('Urologic Oncologist'), ('Neuro-surgeon'), ('Neuro-oncologist'), ('Family Doctor'), ('Palliative Care Physician'), ('Endocrinologist'), ('Gynecologic Oncologist'), ('Gastroenterologist'), ('Dermatologist'), ('Plastic Surgeon');

CREATE TABLE nav.distressalert (
    id SERIAL PRIMARY KEY,
    user_id integer NOT NULL REFERENCES nav.user (id),  
    alert_thru character varying(100),    
    alert_detail character varying(200),
    alert_sent timestamp without time zone
);

CREATE TABLE nav.appointmentalert (
    id SERIAL PRIMARY KEY,
    appointment_id integer NOT NULL REFERENCES nav.appointment (id),  
    alert_thru character varying(100),    
    alert_detail character varying(200),
    alert_sent timestamp without time zone
);

ALTER TABLE nav.patientdistress ADD COLUMN update_by INTEGER DEFAULT NULL;
