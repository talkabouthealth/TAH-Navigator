ALTER TABLE nav.appointmentmaster ADD COLUMN active boolean;
ALTER TABLE nav.appointmentmaster ALTER COLUMN active SET DEFAULT true;

INSERT INTO nav.appointmentmaster(name) VALUES ('Simulation'),('On Treatment Visit'),('End of Treatment Visit'),('Follow-up Appointment');

update  nav.appointmentmaster set active = false where name not in ('Simulation','On Treatment Visit','End of Treatment Visit','Follow-up Appointment','1st Appointment With Radiation Oncologist');