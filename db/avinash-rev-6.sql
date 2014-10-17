DROP TABLE IF EXISTS nav.invited;

CREATE TABLE nav.invited
(
  id serial NOT NULL,
  email character(100),
  firstname character(100) NOT NULL,
  lastname character(100) NOT NULL,
  purpose_text character varying(200),
  treatment_process_step character varying(200),
  appointmenttime character varying(20),
  appointmentdate date,
  appointmentcenter character varying(500),
  caremember integer,
  addedby integer,
  addressid integer,
  activateonsignup boolean,
  invitationsent boolean,
  addedon timestamp with time zone,
  caremember_name character varying(100),
  purpose character varying(500),
  appointmentid integer,
  CONSTRAINT "invited_PK" PRIMARY KEY (id),
  CONSTRAINT "appointment_addedby_FK" FOREIGN KEY (addedby)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "appointment_address_FK" FOREIGN KEY (addressid)
      REFERENCES nav.useraddress (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "appointment_caremember_FK" FOREIGN KEY (caremember)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fked98b02cd5bf7e FOREIGN KEY (addressid)
      REFERENCES nav.useraddress (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fked98b08bb3435b FOREIGN KEY (caremember)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fked98b0c0123bc7 FOREIGN KEY (addedby)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT intivation_appointmentfk FOREIGN KEY (appointmentid)
      REFERENCES nav.appointmentmaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);