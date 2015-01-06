CREATE TABLE nav.patientcareteammember
(
  careteamid integer NOT NULL,
  patientid integer NOT NULL,
  memberid integer NOT NULL,
  isprimary boolean DEFAULT false,
  CONSTRAINT "patientcareteammember_FK" PRIMARY KEY (careteamid, memberid, patientid),
  CONSTRAINT "patientcareteam_FK" FOREIGN KEY (careteamid)
      REFERENCES nav.careteammaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "patientcareteam_member_FK" FOREIGN KEY (memberid)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "patientcareteam_careteam_FK" FOREIGN KEY (careteamid)
      REFERENCES nav.careteammaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "member_FK" FOREIGN KEY (memberid)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
       CONSTRAINT "patient_FK" FOREIGN KEY (memberid)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
