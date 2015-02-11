ALTER TABLE nav.notes ADD COLUMN notesection character varying(30);
ALTER TABLE nav.notes ALTER COLUMN notesection SET NOT NULL;
ALTER TABLE nav.notes ALTER COLUMN notesection SET DEFAULT 'summary'::bpchar;