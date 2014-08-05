CREATE TABLE nav.patientdistress
(
  id serial NOT NULL,
  patientid integer,
  distressvalue integer,
  daterecrded date,
  through character varying(50),
  CONSTRAINT "patientdistress_PK" PRIMARY KEY (id),
  CONSTRAINT "patientdistress_FK" FOREIGN KEY (patientid)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)

CREATE TABLE nav.patientdistressdetail
(
  id serial NOT NULL,
  patiendistressid integer,
  distresstypeid integer,
  distressvalue boolean,
  CONSTRAINT "patientdistressid_FK" FOREIGN KEY (patiendistressid)
      REFERENCES nav.patientdistress (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)

CREATE TABLE nav.distresstypemaster
(
  id serial NOT NULL,
  categoryid integer NOT NULL,
  name character varying(250),
  CONSTRAINT "distresstypemaster_PK" PRIMARY KEY (id, categoryid),
  CONSTRAINT "distresstypemaster_FK" FOREIGN KEY (categoryid)
      REFERENCES nav.distrsscategorymaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)

CREATE TABLE nav.distrsscategorymaster
(
  id serial NOT NULL,
  name character varying(250),
  active boolean,
  CONSTRAINT "distrsscategorymaster_PK" PRIMARY KEY (id)
)


ALTER TABLE nav.careteammember ADD COLUMN "primary" boolean;
ALTER TABLE nav.careteammember ALTER COLUMN "primary" SET DEFAULT false;


ALTER TABLE nav.patientdistress ADD COLUMN otherdetail text;


CREATE TABLE nav.medicinemaster
(
  id serial NOT NULL,
  genericname character varying(250),
  brandname character varying(250),
  method character varying(250),
  image bytea,
  CONSTRAINT "medicine_ID" PRIMARY KEY (id)
)

-- Date : 2 July 2014

ALTER TABLE nav.patientdetails
   ADD COLUMN address integer;
COMMENT ON COLUMN nav.patientdetails.address
  IS 'Patient address.';
  
ALTER TABLE nav.patientdetails
  ADD CONSTRAINT "patientaddress_FK" FOREIGN KEY (address) REFERENCES nav.useraddress (id)
   ON UPDATE NO ACTION ON DELETE NO ACTION;
CREATE INDEX "fki_patientaddress_FK"
  ON nav.patientdetails(address);
  
CREATE TABLE nav.userotheremail
(
  id serial NOT NULL,
  userid integer,
  email character varying(250),
  verificationcode uuid,
  adddate timestamp with time zone,
  active boolean DEFAULT false,
  isprimary boolean DEFAULT false,
  CONSTRAINT "userotheremail_PK" PRIMARY KEY (id),
  CONSTRAINT fkd15144c2d2abc096 FOREIGN KEY (userid)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "userotheremail_FK" FOREIGN KEY (userid)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)  

-- Date : 4 June 2014
ALTER TABLE nav.patientdetails ADD COLUMN ec1name character varying(250);
ALTER TABLE nav.patientdetails ADD COLUMN ec1number character varying(100);
ALTER TABLE nav.patientdetails ADD COLUMN ec2name character varying(250);
ALTER TABLE nav.patientdetails ADD COLUMN ec2number character varying(100);
ALTER TABLE nav.patientdetails ADD COLUMN kinname character varying(250);
ALTER TABLE nav.patientdetails ADD COLUMN kinnumber character varying(100);
ALTER TABLE nav.patientdetails ADD COLUMN proxyname character varying(250);
ALTER TABLE nav.patientdetails ADD COLUMN proxynumber character varying(100);

-- Date 15 July
ALTER TABLE nav.careteammember RENAME "primary" TO isprimary;

-- Date 23 July
INSERT INTO nav.designationmaster(abbr, designation) VALUES ('RN', 'Nurse Navigator');
INSERT INTO nav.designationmaster(abbr, designation) VALUES ('Dr', 'Chief Radiation Therapist');
INSERT INTO nav.designationmaster(abbr, designation) VALUES ('Dr', 'Support Specialist');
INSERT INTO nav.designationmaster(abbr, designation) VALUES ('', 'Research Coordinator');
INSERT INTO nav.designationmaster(abbr, designation) VALUES ('', 'Cancer Center Administrator');

-- Date 29 July

CREATE TABLE nav.notes
(
  id serial NOT NULL,
  noteby integer,
  notefor integer,
  notedesc text,
  notedate timestamp without time zone,
  editdate timestamp without time zone,
  notetitle character varying(500),
  CONSTRAINT "notes_PK" PRIMARY KEY (id),
  CONSTRAINT fkc89a3e36836a0e07 FOREIGN KEY (notefor)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkc89a3e36c6881639 FOREIGN KEY (noteby)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "noteBy_FK" FOREIGN KEY (noteby)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "noteFor_FK" FOREIGN KEY (notefor)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
