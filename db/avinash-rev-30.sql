CREATE TABLE nav.usercontactmethod
(
  userid integer NOT NULL,
  contactmethod integer NOT NULL,
  CONSTRAINT usercontactmethod_pk PRIMARY KEY (userid, contactmethod),
  CONSTRAINT usercontactmethod_contactmethod_fk FOREIGN KEY (contactmethod)
      REFERENCES nav.contactmethodmaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT usercontactmethod_user_fk FOREIGN KEY (userid)
      REFERENCES nav."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);