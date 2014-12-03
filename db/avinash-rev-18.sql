CREATE TABLE nav.defaultemplatetmaster
(
  id serial NOT NULL,
  templatename character varying(250),
  diseaseid integer,
  page character varying(100),
  field character varying(100),
  CONSTRAINT defaultemplatetmaster_id_pk PRIMARY KEY (id),
  CONSTRAINT defaultemplatetmaster_diseaseid_fk FOREIGN KEY (diseaseid)
      REFERENCES nav.diseasemaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

INSERT INTO nav.defaultemplatetmaster(templatename, diseaseid, page, field) VALUES ('Breast Cancer Template',1, 'followupplan', 'activity');

CREATE TABLE nav.defaultemplatetdetail (
  id serial NOT NULL,
  templateid integer,
  fieldtext character varying(500),
  otherfield character varying(500),
  frequency character varying(100),
  enddate character varying(50),
  CONSTRAINT defaultemplatetdetail_id_pk PRIMARY KEY (id),
  CONSTRAINT defaultemplatetdetail_templateid_fk FOREIGN KEY (templateid)
      REFERENCES nav.defaultemplatetmaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

INSERT INTO nav.defaultemplatetdetail(templateid, fieldtext, otherfield, frequency, enddate) VALUES 
(1, 'Medical history and physical exam','To check for recurrence and metastasis.','Every 6 months','Ongoing'),
(1, 'Post-treatment mammography', 'To detect a recurrence of breast cancer.','Every year','Ongoing'),
(1, 'Breast self-exam', 'To gain familiarity with your breasts so that any changes in texture, including the presence of a lump, can be detected as early as possible.','Monthly','Ongoing'),
(1, 'Pelvic exam', 'To screen for cervical cancer.','Every year','Ongoing'),
(1, 'Genetic counselling','To learn more about potential risks and whether genetic testing for mutations in BRCA1 and BRCA2 might be appropriate.','One appointment',null);