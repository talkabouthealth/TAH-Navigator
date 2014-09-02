-- delete column which is created for Commit: 'Story: 76976684, Update 1'
ALTER TABLE nav.patientdetails DROP COLUMN cancer_type_info;
ALTER TABLE nav.patientdetails DROP COLUMN family_history;

-- new tables for Breast Cancer And Related Info

CREATE TABLE nav.bcsinfo (
    stage_id SERIAL PRIMARY KEY,  
    stage character varying(20)
);
COMMENT ON TABLE nav.bcsinfo IS '[B]reast [C]ancer [S]tage [INFO]';

INSERT INTO nav.bcsinfo (stage) VALUES 
('Stage 0 (LCIS)'),
('Stage 0 (DCIS)'),
('Stage 1A'),
('Stage 1B'),
('Stage 2A'),
('Stage 2B'),
('Stage 3A'),
('Stage 3B'),
('Stage 3C'),
('Stage 4');

CREATE TABLE nav.pbcsvinfo (
    id integer NOT NULL REFERENCES nav.user (id),
    stage_id integer DEFAULT NULL REFERENCES nav.bcsinfo(stage_id),
    brca character(1) DEFAULT NULL,
    er character(1) DEFAULT NULL,
    pr character(1) DEFAULT NULL,
    her2 character(1) DEFAULT NULL
);

COMMENT ON TABLE nav.pbcsvinfo IS '[P]atient related [B]reast [C]ancer [S]ingle [V]alued [INFO]';
COMMENT ON COLUMN nav.pbcsvinfo.brca IS 'value +|-|NULL';
COMMENT ON COLUMN nav.pbcsvinfo.er IS 'estrogen receptor value +|-|NULL';
COMMENT ON COLUMN nav.pbcsvinfo.pr IS 'progesterone receptor value +|-|NULL';
COMMENT ON COLUMN nav.pbcsvinfo.her2 IS 'Her2/neu value +|-|NULL';
