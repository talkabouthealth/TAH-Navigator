INSERT INTO nav.diseasemaster(name,diseaseactive) VALUES ('Pancreatic Neuroendocrine Tumor (PNET)',true);

CREATE TABLE nav.careplanprint (
	  id serial NOT NULL,
	  expertid integer,
	  patientid integer,
	  issuedate timestamp without time zone,
	  note character varying(500),
	  CONSTRAINT careplanprint_id PRIMARY KEY (id)
);