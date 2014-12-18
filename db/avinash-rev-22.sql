ALTER TABLE nav.radiation_type ADD COLUMN active boolean;
ALTER TABLE nav.radiation_type ALTER COLUMN active SET DEFAULT true;
update  nav.radiation_type set active = false 
update  nav.radiation_type set active = true where name = '3D-CRT (Three-dimensional conformal radiation therapy)';

ALTER TABLE nav.radiation_schedule ADD COLUMN active boolean;
ALTER TABLE nav.radiation_schedule ALTER COLUMN active SET DEFAULT true;
update  nav.radiation_schedule set active = false 
update  nav.radiation_schedule set active = true where rs_id in (2,3,4,5,6);

ALTER TABLE nav.treatment_region ADD COLUMN diseaseid integer;

ALTER TABLE nav.treatment_region ADD COLUMN active boolean;
ALTER TABLE nav.treatment_region ALTER COLUMN active SET DEFAULT true;
update  nav.treatment_region set active = true; 

ALTER TABLE nav.treatment_region
  ADD CONSTRAINT treatment_regin_disese_fk FOREIGN KEY (diseaseid)
      REFERENCES nav.diseasemaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

update nav.treatment_region set diseaseid = 1 where region in ('Left Breast','Right Breast','Both Breasts','Left Chest Wall','Right Chest Wall','Both Chest Walls');

INSERT INTO nav.treatment_region(region, diseaseid, active) VALUES ('Right Chest Wall', 1, true),('Both Chest Wall', 1, true);

update nav.treatment_region set diseaseid = 3 where region in ('Left Lung','Right Lung','Both Lungs');
INSERT INTO nav.treatment_region(region, diseaseid, active) VALUES ('Left Chest Wall', 3, true),('Right Chest Wall', 3, true),('Both Chest Wall', 3, true);

update nav.treatment_region set diseaseid = 4 where region in ('Colon');
update nav.treatment_region set diseaseid = 5 where region in ('Rectum');
update nav.treatment_region set diseaseid = 2 where region in ('Prostate');
update nav.treatment_region set diseaseid = 22 where region in ('Uterus','Cervix','Fallopian Tubes','Ovaries','Vagina');
INSERT INTO nav.treatment_region(region, diseaseid, active) VALUES ('Uterus', 7, true),('Cervix', 7, true),('Fallopian Tubes', 7, true),
('Ovaries', 7, true),('Vagina', 7, true);
INSERT INTO nav.treatment_region(region, diseaseid, active) VALUES ('Uterus', 15, true),('Cervix', 15, true),('Fallopian Tubes', 15, true),
('Ovaries', 15, true),('Vagina', 15, true);

update nav.treatment_region set active = false where diseaseid is null;

update nav.treatment_region set active = true where region = 'Lymph nodes';


/* surgery type*/
ALTER TABLE nav.surgery_type ADD COLUMN active boolean;
ALTER TABLE nav.surgery_type ALTER COLUMN active SET DEFAULT false;
update  nav.surgery_type set active = false; 

ALTER TABLE nav.surgery_type ADD COLUMN diseaseid integer;
ALTER TABLE nav.surgery_type
  ADD CONSTRAINT surgery_type_disese_fk FOREIGN KEY (diseaseid)
      REFERENCES nav.diseasemaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
update nav.surgery_type set active = true where name in ('Radiofrequency Ablation','Cryosurgery');

update nav.surgery_type set active = true ,diseaseid = 1 where name in ('Breast Biopsy','Lumpectomy','Mastectomy','Double mastectomy','Axial node dissection','Sentinel lymph node dissection','Tissue flap breast reconstruction','Implant breast reconstruction');

update nav.surgery_type set active = true ,diseaseid = 2 where name in ('Open Radical Prostatectomy','Laparoscopic radical prostatectomy','Transurethral resection of the prostate (TURP)','Pelvic lymphadenectomy');

update nav.surgery_type set active = true ,diseaseid = 4 where name in ('Colon Local Excision','Polypectomy and Colon Local Excision','Open Colectomy','Laparoscopic Colectomy','Colostomy');

update nav.surgery_type set active = true ,diseaseid = 5 where name in ('Polypectomy and Rectal Local Excision','Rectal Local Excision','Rectal Resection','Proctectomy','Pelvic Exenteration');

update nav.surgery_type set active = true ,diseaseid = 3 where name in ('Segmentectomy / Wedge Resection','Lobectomy','Pneumonectomy','Sleeve Resection');

update nav.surgery_type set active = true ,diseaseid = 7 where name in ('Open Partial Hysterectomy','Open Total Hysterectomy','Open Bilateral Salpingo-Oopherectomy','Open Radical Hysterectomy','Laparoscopic Partial Hysterectomy',
'Laparoscopic Total Hysterectomy','Laparoscopic Bilateral Salpingo-Oopherectomy','Laparoscopic Radical Hysterectomy');

INSERT INTO nav.surgery_type(name, days, active, diseaseid) VALUES 
('Open Partial Hysterectomy', 35, true, 15)
,('Open Total Hysterectomy', 35, true, 15)
,('Open Bilateral Salpingo-Oopherectomy', 35, true, 15)
,('Open Radical Hysterectomy', 35, true, 15)
,('Laparoscopic Partial Hysterectomy', 21, true, 15)
,('Laparoscopic Total Hysterectomy', 21, true, 15)
,('Laparoscopic Bilateral Salpingo-Oopherectomy', 21, true, 15)
,('Laparoscopic Radical Hysterectomy', 21, true, 15);

INSERT INTO nav.surgery_type(name, days, active, diseaseid) VALUES 
('Open Partial Hysterectomy', 35, true, 22)
,('Open Total Hysterectomy', 35, true, 22)
,('Open Bilateral Salpingo-Oopherectomy', 35, true, 22)
,('Open Radical Hysterectomy', 35, true, 22)
,('Laparoscopic Partial Hysterectomy', 21, true, 22)
,('Laparoscopic Total Hysterectomy', 21, true, 22)
,('Laparoscopic Bilateral Salpingo-Oopherectomy', 21, true, 22)
,('Laparoscopic Radical Hysterectomy', 21, true, 22);
