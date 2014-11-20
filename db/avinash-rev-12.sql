CREATE TABLE nav.cancerchromosomemaster
(
  id serial NOT NULL,
  chromosomename character varying(250),
  diseaseid integer,
  CONSTRAINT "cancerchromosomemaster_id_PK" PRIMARY KEY (id),
  CONSTRAINT "cancerchromosomemaster_diseaseid_FK" FOREIGN KEY (diseaseid)
      REFERENCES nav.diseasemaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)

INSERT INTO nav.cancerchromosomemaster(chromosomename, diseaseid) VALUES ('FLT3', 11),('NPM1', 11),('CEBPA', 11),('JAK2', 11),('chromosome 22 (the Philadelphia chromosome)', 14);


CREATE TABLE nav.patientchromosome
(
  patientid integer NOT NULL,
  chromosomeid integer NOT NULL,
  CONSTRAINT patientchromosome_pk PRIMARY KEY (patientid, chromosomeid),
  CONSTRAINT "patientchromosome_mutation_FK" FOREIGN KEY (chromosomeid)
      REFERENCES nav.cancerchromosomemaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "patientchromosome_patient_FK" FOREIGN KEY (patientid)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE nav.cancerphasemaster
(
  phase_id serial NOT NULL,
  phase character varying(20),
  diseaseid integer,
  CONSTRAINT cancerphasemaster_pkey PRIMARY KEY (phase_id)
);

ALTER TABLE nav.pbcsvinfo ADD COLUMN phaseid integer;

ALTER TABLE nav.pbcsvinfo
  ADD CONSTRAINT pbcsvinfo_phaseid_fk FOREIGN KEY (phaseid)
      REFERENCES nav.cancerphasemaster (phase_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
INSERT INTO nav.cancerphasemaster(phase, diseaseid) 
VALUES ('Accelerated', 14),('Blastic', 14),('Chronic', 14);
