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