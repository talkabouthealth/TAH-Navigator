CREATE TABLE nav.applicationsettings
(
  id serial NOT NULL,
  propertyname character varying(100),
  propertyvalue character varying(100),
  propertytype character varying(20),
  CONSTRAINT applicationsettings_pk PRIMARY KEY (id)
);

INSERT INTO nav.applicationsettings (propertyname, propertyvalue, propertytype) VALUES ('accesstoallpages', 'true', 'boolean');
