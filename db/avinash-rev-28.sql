ALTER TABLE nav.patientcareteammember ADD COLUMN isdeleted boolean;
ALTER TABLE nav.patientcareteammember ALTER COLUMN isdeleted SET DEFAULT false;
update nav.patientcareteammember set isdeleted = false;

ALTER TABLE nav.patiencareteam ADD COLUMN isdeleted boolean;
ALTER TABLE nav.patiencareteam ALTER COLUMN isdeleted SET DEFAULT false;
update nav.patiencareteam set isdeleted = false;

ALTER TABLE nav.careteammaster ADD COLUMN adminteam boolean;
ALTER TABLE nav.careteammaster ALTER COLUMN adminteam SET DEFAULT false;
update nav.careteammaster set adminteam =false;
update nav.careteammaster set adminteam =true where id= 2 or id=11;