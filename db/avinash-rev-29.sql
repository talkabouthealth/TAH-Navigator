CREATE TABLE nav.othercaremember
(
  id serial NOT NULL,
  name character varying(200),
  title character varying(200),
  telephone character varying(20),
  careteamid integer,
  patientid integer,
  isprimary boolean,
  CONSTRAINT othercaremember_pk PRIMARY KEY (id)
);

INSERT INTO nav.diseasemaster(name,diseaseactive) VALUES ('Gastric Cancer',true),('Merkel Cell Carcinoma',true),('Skin Cancer',true),('Salivary Gland Cancer',true),('Unknown Primary',true);

INSERT INTO nav.diseasemaster(name,diseaseactive) VALUES ('Pancreatic Neuroendocrine Tumor (PNET)',true);
