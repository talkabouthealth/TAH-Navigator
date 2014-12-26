ALTER TABLE nav.careteammaster ADD COLUMN logo bytea;
COMMENT ON COLUMN nav.careteammaster.logo IS 'care team logo';

ALTER TABLE nav.careteammaster ADD COLUMN active boolean DEFAULT true;
update nav.careteammaster set active = true;

ALTER TABLE nav.patientmedication ALTER COLUMN startdate TYPE text;
ALTER TABLE nav.patientmedication ALTER COLUMN enddate TYPE text;