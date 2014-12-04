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

update nav.inputdefaultmaster  set otherfield = 'Set priorities.
Take time out when needed.
Exercise.
Get rid of clutter, surround yourself with what you like.
Learn to relax at will - deep breathing, visualization, etc.
Manage your "self-talk".
Practice saying "no".' where field =  'goal' and fieldtext = 'Manage Stress';

update nav.inputdefaultmaster  set otherfield = 'Wear loose fitting clothes and slip on shoes.
Organize early to avoid rushing.
Delegate work to others.
Take naps and drink a lot of fluids.' where field =  'goal' and fieldtext = 'Conserve Energy';

update nav.inputdefaultmaster  set tiptext = 
'Tell your doctor if there is a history of cancer in your family.<br/>
 The following risk factors may indicate that breast cancer could run in the family:<br/>
 • Ashkenazi Jewish heritage<br/>
 • Personal or family history of ovarian cancer<br/>
 • Any first-degree relative (mother, sister, daughter) diagnosed with breast cancer before age 50<br/>
 • Two or more first-degree or second-degree relatives (grandparent, aunt, uncle) diagnosed with breast cancer<br/>
 • Personal or family history of breast cancer in both breasts<br/>
 • History of breast cancer in a male relative'
where fieldtext = 'Genetic counselling';

delete from nav.defaultemplatetdetail where fieldtext = 'Genetic counselling';
