DROP TABLE IF EXISTS nav.pconcern;
DROP TABLE IF EXISTS nav.pgoal;
DROP TABLE IF EXISTS nav.pfupci;


CREATE TABLE nav.pconcern (
    pconcern_id SERIAL PRIMARY KEY,
    user_id integer NOT NULL REFERENCES nav.user (id),
    concern character varying(200) NOT NULL,
    next_step character varying(200) DEFAULT NULL,
    notes character varying(400),
    concern_date date DEFAULT NULL
);

COMMENT ON TABLE nav.pconcern IS 'Patient Needs / Concern';


CREATE TABLE nav.pgoal (
    pgoal_id SERIAL PRIMARY KEY,
    user_id integer NOT NULL REFERENCES nav.user (id),
    goal character varying(200) NOT NULL,
    next_step character varying(200) DEFAULT NULL,
    goal_deadline date DEFAULT NULL,
    notes character varying(400)
);

COMMENT ON TABLE nav.pgoal IS 'Goals to Achieve';


CREATE TABLE nav.pfupci (
    pfup_id SERIAL PRIMARY KEY,
    user_id integer NOT NULL REFERENCES nav.user (id),
    activity character varying(200) NOT NULL,
    frequency character varying(200) DEFAULT NULL,
    end_date date DEFAULT NULL,
    ongoing character varying(20) DEFAULT NULL,
    purpose character varying(200) DEFAULT NULL,
    doctor character varying(200) DEFAULT NULL
);

COMMENT ON TABLE nav.pfupci IS 'Patient Follow-Up Care Item';

