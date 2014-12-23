CREATE TABLE nav.appointmentgroup
(
  id serial,
  startson date,
  endcheckflag boolean DEFAULT true,
  endneverflag boolean DEFAULT true,
  occurences int,
  endsondate date
);
ALTER TABLE nav.appointmentgroup ADD CONSTRAINT appointmentgroup_pk PRIMARY KEY(id);

ALTER TABLE nav.appointment ADD COLUMN appointmentgroupid integer;

ALTER TABLE nav.appointment ADD CONSTRAINT appointmentgroupid_fk FOREIGN KEY (appointmentgroupid) REFERENCES nav.appointmentgroup (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE nav.appointmentgroup ADD COLUMN addressid integer;