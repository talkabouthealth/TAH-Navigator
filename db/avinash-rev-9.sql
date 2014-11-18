INSERT INTO nav.diseasemaster(name) VALUES ('Esophageal Cancer');

ALTER TABLE nav.bcsinfo ADD COLUMN diseaseid integer;

UPDATE nav.bcsinfo SET  diseaseid=1;

INSERT INTO nav.bcsinfo (stage,diseaseid) VALUES 
('Stage 0',4),
('Stage 1',4),
('Stage 2A',4),
('Stage 2B',4),
('Stage 2C',4),
('Stage 3A',4),
('Stage 3B',4),
('Stage 3C',4),
('Stage 4A',4),
('Stage 4B',4);

INSERT INTO nav.bcsinfo (stage,diseaseid) VALUES 
('Stage 1A',6),
('Stage 1B',6),
('Stage 2A',6),
('Stage 2B',6),
('Stage 3A',6),
('Stage 3B',6),
('Stage 3C',6),
('Stage 4',6);

INSERT INTO nav.bcsinfo (stage,diseaseid) VALUES 
('Stage 0',3),
('Stage 1A',3),
('Stage 1B',3),
('Stage 2A',3),
('Stage 2B',3),
('Stage 3A',3),
('Stage 3B',3),
('Stage 4A',3),
('Stage 4B',3);

INSERT INTO nav.bcsinfo (stage,diseaseid) VALUES 
('Stage 1',2),
('Stage 2A',2),
('Stage 2B',2),
('Stage 3',2),
('Stage 4',2);

INSERT INTO nav.bcsinfo (stage,diseaseid) VALUES 
('Stage 0',5),
('Stage 1',5),
('Stage 2A',5),
('Stage 2B',5),
('Stage 2C',5),
('Stage 3A',5),
('Stage 3B',5),
('Stage 3C',5),
('Stage 4A',5),
('Stage 4B',5);


CREATE TABLE nav.cancermutationmaster (
  id serial NOT NULL,
  mutation character varying(50),
  diseaseid integer,
  CONSTRAINT cancermutationmaster_pk PRIMARY KEY (id)
);

INSERT INTO nav.cancermutationmaster(mutation, diseaseid) 
VALUES ('KRAS', 4),
('BRAF', 4),
('PI3KCA', 4),
('PI3KCA', 3),
('EGFR', 3),
('EML-4ALK', 3),
('KRAS', 3),
('PIK-3', 3),
('MET', 3),
('KRAS', 5),
('BRAF', 5),
('PI3KCA', 5);

CREATE TABLE nav.patientmutation
(
  patientid integer NOT NULL,
  mutationid integer NOT NULL,
  CONSTRAINT patientmutation_pk PRIMARY KEY (patientid, mutationid),
  CONSTRAINT "patinetmutaion_mutation_FK" FOREIGN KEY (mutationid)
      REFERENCES nav.cancermutationmaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "patinetmutaion_patient_FK" FOREIGN KEY (patientid)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE nav.cancertypemaster
(
  id serial NOT NULL,
  roottype boolean,
  name character varying(250),
  diseaseid integer,
  CONSTRAINT "cancertypemaster_id_PK" PRIMARY KEY (id),
  CONSTRAINT "cancertypemaster_diseaseid_FK" FOREIGN KEY (diseaseid)
      REFERENCES nav.diseasemaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

INSERT INTO nav.cancertypemaster(roottype, name, diseaseid) 
VALUES (true, 'NSCLC (Small-cell lung cancer)', 3),
(true, 'SCLC (Small cell lung caner)', 3),
(false, 'Adenocarcinoma', 3),
(false, 'Squamous Cell', 3),
(false, 'Large Cell', 3),
(false, 'Adenocarcinoma ', 6),
(false, 'Squamous cell', 6);



ALTER TABLE nav.pbcsvinfo ADD COLUMN typeid integer;

ALTER TABLE nav.pbcsvinfo
  ADD CONSTRAINT pbcsvinfo_typeid_fk FOREIGN KEY (typeid)
      REFERENCES nav.cancertypemaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;


ALTER TABLE nav.pbcsvinfo ADD COLUMN subtypeid integer;

ALTER TABLE nav.pbcsvinfo
  ADD CONSTRAINT pbcsvinfo_subtypeid_fk FOREIGN KEY (subtypeid)
      REFERENCES nav.cancertypemaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE nav.pbcsvinfo ADD COLUMN risklevel character varying(20);

ALTER TABLE nav.pbcsvinfo ADD COLUMN psascore character varying(5);

ALTER TABLE nav.pbcsvinfo ADD COLUMN gleasonscore character varying(5);
