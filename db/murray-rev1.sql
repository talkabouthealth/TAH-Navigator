ALTER TABLE nav.userdetails ADD COLUMN ssnlast4 integer;
ALTER TABLE nav.userdetails ALTER COLUMN ssnlast4 SET DEFAULT 0;
UPDATE nav.userdetails SET ssnlast4 = 0 WHERE ssnlast4 IS NULL; 


