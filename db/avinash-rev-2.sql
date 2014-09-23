CREATE TABLE nav.appointmentmaster
(
  id serial NOT NULL,
  name character varying(500),
  CONSTRAINT "appointmentmaster_PK" PRIMARY KEY (id)
);


INSERT INTO nav.appointmentmaster(name) VALUES ('1st Appointment With Radiation Oncologist');
INSERT INTO nav.appointmentmaster(name) VALUES ('1st Appointment With Surgeon');
INSERT INTO nav.appointmentmaster(name) VALUES ('1st Appointment With Oncologist');
INSERT INTO nav.appointmentmaster(name) VALUES ('Make Treatment Decision');
INSERT INTO nav.appointmentmaster(name) VALUES ('1st Radiation Treatment');
INSERT INTO nav.appointmentmaster(name) VALUES ('Surgery');
INSERT INTO nav.appointmentmaster(name) VALUES ('Surgery Follow-up Appointment');
INSERT INTO nav.appointmentmaster(name) VALUES ('1st Oncology Treatment With New Medication');
INSERT INTO nav.appointmentmaster(name) VALUES ('Ongoing Radiation Treatment');
INSERT INTO nav.appointmentmaster(name) VALUES ('Treatment Progress Check-in');
INSERT INTO nav.appointmentmaster(name) VALUES ('Ongoing Treatment Follow-up');

DROP TABLE nav.appointment;

CREATE TABLE nav.appointment
(
  id serial NOT NULL,
  purpose character varying(500),
  appointmenttime character varying(20),
  appointmentdate date,
  appointmentcenter character varying(500),
  caremember integer,
  addedby integer,
  addressid integer,
  addedon timestamp with time zone,
  patientid integer,
  deleteflag boolean,
  appointmentid integer,
  CONSTRAINT appointment_pk PRIMARY KEY (id),
  CONSTRAINT "appointment_addedby_FK" FOREIGN KEY (addedby)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "appointment_address_FK" FOREIGN KEY (addressid)
      REFERENCES nav.useraddress (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "appointment_caremember_FK" FOREIGN KEY (caremember)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "appointment_patient_FK" FOREIGN KEY (patientid)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT appointmentid_fk FOREIGN KEY (appointmentid)
      REFERENCES nav.appointmentmaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk2bb71f542cd5bf7e FOREIGN KEY (addressid)
      REFERENCES nav.useraddress (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk2bb71f548bb3435b FOREIGN KEY (caremember)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk2bb71f54c0123bc7 FOREIGN KEY (addedby)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk2bb71f54f005d950 FOREIGN KEY (patientid)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)


CREATE TABLE nav.appointmentchecklist
(
  appointmentid integer,
  checkitem text,
  id serial NOT NULL,
  CONSTRAINT appointmentchecklist_pk PRIMARY KEY (id),
  CONSTRAINT appointmentchecklist_fk FOREIGN KEY (appointmentid)
      REFERENCES nav.appointmentmaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)


INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (1, 'Pack All Medication Pill Bottles In A Ziploc Bag');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (1, 'Write Down All Questions To Ask My Doctor');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (1, 'Bring Drivers License And Insurance Card');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (1, 'Bring A Friend Or Family Member');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (1, 'Bring All Test Results, Lab Work, And Past Patient Records ');


INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (2, 'Pack All Medication Pill Bottles In A Ziploc Bag');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (2, 'Write Down All Questions To Ask My Doctor');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (2, 'Bring Drivers License And Insurance Card');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (2, 'Bring A Friend Or Family Member');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (2, 'Bring All Test Results, Lab Work, And Past Patient Records ');

INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (3, 'Pack All Medication Pill Bottles In A Ziploc Bag');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (3, 'Write Down All Questions To Ask My Doctor');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (3, 'Bring Drivers License And Insurance Card');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (3, 'Bring A Friend Or Family Member');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (3, 'Bring All Test Results, Lab Work, And Past Patient Records ');

INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (4, 'Pack All Medication Pill Bottles In A Ziploc Bag');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (4, 'Write Down All Questions To Ask My Doctor');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (4, 'Bring Drivers License And Insurance Card');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (4, 'Bring A Friend Or Family Member');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (4, 'Bring All Test Results, Lab Work, And Past Patient Records ');

INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (5, 'Pack All Medication Pill Bottles In A Ziploc Bag');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (5, 'Write Down All Questions To Ask My Doctor');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (5, 'Bring Drivers License And Insurance Card');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (5, 'Bring A Friend Or Family Member');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (5, 'Bring All Test Results, Lab Work, And Past Patient Records ');

INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (6, 'Pack All Medication Pill Bottles In A Ziploc Bag');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (6, 'Write Down All Questions To Ask My Doctor');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (6, 'Bring Drivers License And Insurance Card');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (6, 'Bring A Friend Or Family Member');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (6, 'Bring All Test Results, Lab Work, And Past Patient Records ');

INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (7, 'Pack All Medication Pill Bottles In A Ziploc Bag');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (7, 'Write Down All Questions To Ask My Doctor');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (7, 'Bring Drivers License And Insurance Card');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (7, 'Bring A Friend Or Family Member');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (7, 'Bring All Test Results, Lab Work, And Past Patient Records ');

INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (8, 'Pack All Medication Pill Bottles In A Ziploc Bag');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (8, 'Write Down All Questions To Ask My Doctor');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (8, 'Bring Drivers License And Insurance Card');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (8, 'Bring A Friend Or Family Member');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (8, 'Bring All Test Results, Lab Work, And Past Patient Records ');

INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (9, 'Pack All Medication Pill Bottles In A Ziploc Bag');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (9, 'Write Down All Questions To Ask My Doctor');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (9, 'Bring Drivers License And Insurance Card');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (9, 'Bring A Friend Or Family Member');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (9, 'Bring All Test Results, Lab Work, And Past Patient Records ');

INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (10, 'Pack All Medication Pill Bottles In A Ziploc Bag');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (10, 'Write Down All Questions To Ask My Doctor');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (10, 'Bring Drivers License And Insurance Card');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (10, 'Bring A Friend Or Family Member');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (10, 'Bring All Test Results, Lab Work, And Past Patient Records ');

INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (11, 'Pack All Medication Pill Bottles In A Ziploc Bag');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (11, 'Write Down All Questions To Ask My Doctor');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (11, 'Bring Drivers License And Insurance Card');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (11, 'Bring A Friend Or Family Member');
INSERT INTO nav.appointmentchecklist(appointmentid, checkitem) VALUES (11, 'Bring All Test Results, Lab Work, And Past Patient Records ');

