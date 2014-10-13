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
  CONSTRAINT "invited_PK" PRIMARY KEY (id)
    ,CONSTRAINT "appointment_addedby_FK" FOREIGN KEY (addedby)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "appointment_address_FK" FOREIGN KEY (addressid)
      REFERENCES nav.useraddress (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "appointment_caremember_FK" FOREIGN KEY (caremember)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


INSERT INTO nav.mailtemplate(mailtemplate, mailsubject, type, fromfield, ccfield, bccfield,companyid) VALUES ('invitation', 
'Welcome to Moffitt – TVRH! Get ready for your first appointment and sign up for our online Navigator tool.', 'html', 'admin@talkabouthealth.com', '', '', 1);