ALTER TABLE nav.userdetails ADD COLUMN ssnlast4 integer;

UPDATE nav.userdetails SET ssnlast4 = 1234 WHERE ssnlast4 IS NULL; 
