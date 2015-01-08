ALTER TABLE nav.patientcareteammember ADD COLUMN isdeleted boolean;
ALTER TABLE nav.patientcareteammember ALTER COLUMN isdeleted SET DEFAULT false;

ALTER TABLE nav.patiencareteam ADD COLUMN isdeleted boolean;
ALTER TABLE nav.patiencareteam ALTER COLUMN isdeleted SET DEFAULT false;
