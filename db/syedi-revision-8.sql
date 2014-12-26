ALTER TABLE nav.cancertypemaster ADD COLUMN user_defined boolean DEFAULT 'f';
ALTER TABLE nav.bcsinfo ADD COLUMN user_defined boolean DEFAULT 'f';
ALTER TABLE nav.cancerinvasivemaster ADD COLUMN user_defined boolean DEFAULT 'f';
ALTER TABLE nav.cancergrademaster ADD COLUMN user_defined boolean DEFAULT 'f';
ALTER TABLE nav.cancerphasemaster ADD COLUMN user_defined boolean DEFAULT 'f';
ALTER TABLE nav.cancerfabclassificationmaster ADD COLUMN user_defined boolean DEFAULT 'f';
ALTER TABLE nav.cancerwhoclassificationmaster ADD COLUMN user_defined boolean DEFAULT 'f';