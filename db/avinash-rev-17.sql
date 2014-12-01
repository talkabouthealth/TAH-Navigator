ALTER TABLE nav.inputdefaultmaster ADD COLUMN enddate character varying(50);

update nav.inputdefaultmaster set enddate = 'Ongoing' where fieldtext = 'Medical history and physical exam';
update nav.inputdefaultmaster set enddate = 'Ongoing' where fieldtext = 'Post-treatment mammography';
update nav.inputdefaultmaster set enddate = 'Ongoing' where fieldtext = 'Breast self-exam';
update nav.inputdefaultmaster set enddate = 'Ongoing' where fieldtext = 'Pelvic exam';
update nav.inputdefaultmaster set enddate = 'Ongoing' where fieldtext = 'Genetic counselling';