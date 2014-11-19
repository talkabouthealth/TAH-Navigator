CREATE TABLE nav.cancerinvasivemaster
(
  id serial NOT NULL,
  invname character varying(250),
  diseaseid integer,
  CONSTRAINT "cancerinvasivemaster_id_PK" PRIMARY KEY (id),
  CONSTRAINT "cancerinvasivemaster_diseaseid_FK" FOREIGN KEY (diseaseid)
      REFERENCES nav.diseasemaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

INSERT INTO nav.cancerinvasivemaster(invname, diseaseid)
    VALUES ('Invasive', 8),
    ('Non-invasive', 8),
    ('Non-muscle invasive', 8),
    ('Superficial', 8);


CREATE TABLE nav.cancergrademaster
(
  id serial NOT NULL,
  gradename character varying(250),
  diseaseid integer,
  CONSTRAINT "cancergrademaster_id_PK" PRIMARY KEY (id),
  CONSTRAINT "cancergrademaster_diseaseid_FK" FOREIGN KEY (diseaseid)
      REFERENCES nav.diseasemaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

INSERT INTO nav.cancergrademaster(gradename, diseaseid)
    VALUES 
    ('High Grade', 8),
    ('Low grade', 8);
    
    
    ALTER TABLE nav.pbcsvinfo ADD COLUMN grade integer;
    
    ALTER TABLE nav.pbcsvinfo ADD COLUMN invasion integer;
    
    ALTER TABLE nav.pbcsvinfo
  ADD CONSTRAINT pbcsvinfo_invasion_fk FOREIGN KEY (invasion)
      REFERENCES nav.cancerinvasivemaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
      ALTER TABLE nav.pbcsvinfo
  ADD CONSTRAINT pbcsvinfo_grade_fk FOREIGN KEY (grade)
      REFERENCES nav.cancergrademaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
