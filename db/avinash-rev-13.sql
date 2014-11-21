CREATE TABLE nav.cancerwhoclassificationmaster (
  id serial NOT NULL,
  whoname character varying(250),
  diseaseid integer,
  CONSTRAINT "cancerwhoclassificationmaster_id_PK" PRIMARY KEY (id),
  CONSTRAINT "cancerwhoclassificationmaster_diseaseid_FK" FOREIGN KEY (diseaseid)
      REFERENCES nav.diseasemaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

INSERT INTO nav.cancerwhoclassificationmaster(whoname, diseaseid) 
VALUES ('Acute promyelocytic leukemia, which usually has a translocation between chromosomes 15 and 17', 11),
('AML not otherwise specified', 11),
('AML with a translocation between chromosomes 8 and 21', 11),
('AML with a translocation or inversion in chromosome 16', 11),
('AML with changes in chromosome 11', 11),
('AML with myelodysplasia-related changes', 11),
('AML with recurrent genetic abnormalities', 11),
('Therapy-related AML', 11);


CREATE TABLE nav.cancerfabclassificationmaster (
  id serial NOT NULL,
  fabname character varying(250),
  diseaseid integer,
  CONSTRAINT "cancerfabclassificationmaster_id_PK" PRIMARY KEY (id),
  CONSTRAINT "cancerfabclassificationmaster_diseaseid_FK" FOREIGN KEY (diseaseid)
      REFERENCES nav.diseasemaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

INSERT INTO nav.cancerfabclassificationmaster(fabname, diseaseid) 
VALUES ('M0', 11),('M1', 11),('M2', 11),('M3', 11),('M4', 11),('M5', 11),('M6', 11),('M7', 11),('M8', 11);

ALTER TABLE nav.pbcsvinfo ADD COLUMN whoid integer;

ALTER TABLE nav.pbcsvinfo
  ADD CONSTRAINT pbcsvinfo_whoid_fk FOREIGN KEY (whoid)
      REFERENCES nav.cancerwhoclassificationmaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE nav.pbcsvinfo ADD COLUMN fabid integer;

ALTER TABLE nav.pbcsvinfo
  ADD CONSTRAINT pbcsvinfo_fabid_fk FOREIGN KEY (fabid)
      REFERENCES nav.cancerwhoclassificationmaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
