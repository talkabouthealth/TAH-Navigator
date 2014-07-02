--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: nav; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA nav;


ALTER SCHEMA nav OWNER TO postgres;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = nav, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: careteammaster; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE careteammaster (
    id integer NOT NULL,
    name character varying(200),
    address integer
);


ALTER TABLE nav.careteammaster OWNER TO postgres;

--
-- Name: careteammaster_id_seq; Type: SEQUENCE; Schema: nav; Owner: postgres
--

CREATE SEQUENCE careteammaster_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nav.careteammaster_id_seq OWNER TO postgres;

--
-- Name: careteammaster_id_seq; Type: SEQUENCE OWNED BY; Schema: nav; Owner: postgres
--

ALTER SEQUENCE careteammaster_id_seq OWNED BY careteammaster.id;


--
-- Name: careteammember; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE careteammember (
    careteamid integer NOT NULL,
    memberid integer NOT NULL,
    "primary" boolean DEFAULT false
);


ALTER TABLE nav.careteammember OWNER TO postgres;

--
-- Name: contactmethodmaster; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE contactmethodmaster (
    id integer NOT NULL,
    name character(50)
);


ALTER TABLE nav.contactmethodmaster OWNER TO postgres;

--
-- Name: contactmethodmaster_id_seq; Type: SEQUENCE; Schema: nav; Owner: postgres
--

CREATE SEQUENCE contactmethodmaster_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nav.contactmethodmaster_id_seq OWNER TO postgres;

--
-- Name: contactmethodmaster_id_seq; Type: SEQUENCE OWNED BY; Schema: nav; Owner: postgres
--

ALTER SEQUENCE contactmethodmaster_id_seq OWNED BY contactmethodmaster.id;


--
-- Name: designationmaster; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE designationmaster (
    id integer NOT NULL,
    abbr character varying(10),
    designation character varying(100)
);


ALTER TABLE nav.designationmaster OWNER TO postgres;

--
-- Name: designationmaster_id_seq; Type: SEQUENCE; Schema: nav; Owner: postgres
--

CREATE SEQUENCE designationmaster_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nav.designationmaster_id_seq OWNER TO postgres;

--
-- Name: designationmaster_id_seq; Type: SEQUENCE OWNED BY; Schema: nav; Owner: postgres
--

ALTER SEQUENCE designationmaster_id_seq OWNED BY designationmaster.id;


--
-- Name: diseasemaster; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE diseasemaster (
    id integer NOT NULL,
    name character varying(200)
);


ALTER TABLE nav.diseasemaster OWNER TO postgres;

--
-- Name: diseasemaster_id_seq; Type: SEQUENCE; Schema: nav; Owner: postgres
--

CREATE SEQUENCE diseasemaster_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nav.diseasemaster_id_seq OWNER TO postgres;

--
-- Name: diseasemaster_id_seq; Type: SEQUENCE OWNED BY; Schema: nav; Owner: postgres
--

ALTER SEQUENCE diseasemaster_id_seq OWNED BY diseasemaster.id;


--
-- Name: distresstypemaster; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE distresstypemaster (
    id integer NOT NULL,
    categoryid integer NOT NULL,
    name character varying(250)
);


ALTER TABLE nav.distresstypemaster OWNER TO postgres;

--
-- Name: distresstypemaster_id_seq; Type: SEQUENCE; Schema: nav; Owner: postgres
--

CREATE SEQUENCE distresstypemaster_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nav.distresstypemaster_id_seq OWNER TO postgres;

--
-- Name: distresstypemaster_id_seq; Type: SEQUENCE OWNED BY; Schema: nav; Owner: postgres
--

ALTER SEQUENCE distresstypemaster_id_seq OWNED BY distresstypemaster.id;


--
-- Name: distrsscategorymaster; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE distrsscategorymaster (
    id integer NOT NULL,
    name character varying(250),
    active boolean
);


ALTER TABLE nav.distrsscategorymaster OWNER TO postgres;

--
-- Name: distrsscategorymaster_id_seq; Type: SEQUENCE; Schema: nav; Owner: postgres
--

CREATE SEQUENCE distrsscategorymaster_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nav.distrsscategorymaster_id_seq OWNER TO postgres;

--
-- Name: distrsscategorymaster_id_seq; Type: SEQUENCE OWNED BY; Schema: nav; Owner: postgres
--

ALTER SEQUENCE distrsscategorymaster_id_seq OWNED BY distrsscategorymaster.id;


--
-- Name: expertdetails; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE expertdetails (
    id integer NOT NULL,
    designation integer,
    practiceaddress integer,
    homeaddress integer,
    statement text
);


ALTER TABLE nav.expertdetails OWNER TO postgres;

--
-- Name: invited; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE invited (
    id integer NOT NULL,
    name character(50) NOT NULL,
    password character(75) NOT NULL,
    usertype character(1) NOT NULL,
    activateonsignup boolean,
    invitationsent boolean,
    "timestamp" timestamp with time zone
);


ALTER TABLE nav.invited OWNER TO postgres;

--
-- Name: TABLE invited; Type: COMMENT; Schema: nav; Owner: postgres
--

COMMENT ON TABLE invited IS 'Will hold list of invited users (Care team members and pationts).';


--
-- Name: COLUMN invited.id; Type: COMMENT; Schema: nav; Owner: postgres
--

COMMENT ON COLUMN invited.id IS 'Id for table.';


--
-- Name: COLUMN invited.name; Type: COMMENT; Schema: nav; Owner: postgres
--

COMMENT ON COLUMN invited.name IS 'Name of user being  invited.';


--
-- Name: COLUMN invited.password; Type: COMMENT; Schema: nav; Owner: postgres
--

COMMENT ON COLUMN invited.password IS 'Password added by admin for user';


--
-- Name: COLUMN invited.usertype; Type: COMMENT; Schema: nav; Owner: postgres
--

COMMENT ON COLUMN invited.usertype IS 'User Type';


--
-- Name: COLUMN invited.invitationsent; Type: COMMENT; Schema: nav; Owner: postgres
--

COMMENT ON COLUMN invited.invitationsent IS 'Is invitation sent.';


--
-- Name: invited_id_seq; Type: SEQUENCE; Schema: nav; Owner: postgres
--

CREATE SEQUENCE invited_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nav.invited_id_seq OWNER TO postgres;

--
-- Name: invited_id_seq; Type: SEQUENCE OWNED BY; Schema: nav; Owner: postgres
--

ALTER SEQUENCE invited_id_seq OWNED BY invited.id;


--
-- Name: loginhistory; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE loginhistory (
    id integer NOT NULL,
    userid integer NOT NULL,
    logintime timestamp with time zone,
    sessionid character(150),
    frommethod character(20),
    rememberflag boolean,
    refrer character(250)
);


ALTER TABLE nav.loginhistory OWNER TO postgres;

--
-- Name: COLUMN loginhistory.id; Type: COMMENT; Schema: nav; Owner: postgres
--

COMMENT ON COLUMN loginhistory.id IS 'id primary key';


--
-- Name: COLUMN loginhistory.sessionid; Type: COMMENT; Schema: nav; Owner: postgres
--

COMMENT ON COLUMN loginhistory.sessionid IS 'sessionid if provided';


--
-- Name: COLUMN loginhistory.frommethod; Type: COMMENT; Schema: nav; Owner: postgres
--

COMMENT ON COLUMN loginhistory.frommethod IS 'From local/service/FB etc';


--
-- Name: loginhistory_id_seq; Type: SEQUENCE; Schema: nav; Owner: postgres
--

CREATE SEQUENCE loginhistory_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nav.loginhistory_id_seq OWNER TO postgres;

--
-- Name: loginhistory_id_seq; Type: SEQUENCE OWNED BY; Schema: nav; Owner: postgres
--

ALTER SEQUENCE loginhistory_id_seq OWNED BY loginhistory.id;


--
-- Name: mailtemplate; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE mailtemplate (
    id integer NOT NULL,
    mailtemplate character varying(50) DEFAULT NULL::character varying,
    mailsubject character varying(150) DEFAULT NULL::character varying,
    type character varying(50) DEFAULT NULL::character varying,
    fromfield character varying(200) DEFAULT NULL::character varying,
    ccfield character varying(200) DEFAULT NULL::character varying,
    bccfield character varying(200) DEFAULT NULL::character varying,
    companyid integer NOT NULL
);


ALTER TABLE nav.mailtemplate OWNER TO postgres;

--
-- Name: mailtemplate_id_seq; Type: SEQUENCE; Schema: nav; Owner: postgres
--

CREATE SEQUENCE mailtemplate_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nav.mailtemplate_id_seq OWNER TO postgres;

--
-- Name: mailtemplate_id_seq; Type: SEQUENCE OWNED BY; Schema: nav; Owner: postgres
--

ALTER SEQUENCE mailtemplate_id_seq OWNED BY mailtemplate.id;


--
-- Name: medicinemaster; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE medicinemaster (
    id integer NOT NULL,
    genericname character varying(250),
    brandname character varying(250),
    method character varying(250),
    image bytea
);


ALTER TABLE nav.medicinemaster OWNER TO postgres;

--
-- Name: medicinemaster_id_seq; Type: SEQUENCE; Schema: nav; Owner: postgres
--

CREATE SEQUENCE medicinemaster_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nav.medicinemaster_id_seq OWNER TO postgres;

--
-- Name: medicinemaster_id_seq; Type: SEQUENCE OWNED BY; Schema: nav; Owner: postgres
--

ALTER SEQUENCE medicinemaster_id_seq OWNED BY medicinemaster.id;


--
-- Name: patiencareteam; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE patiencareteam (
    careteamid integer NOT NULL,
    patienid integer NOT NULL
);


ALTER TABLE nav.patiencareteam OWNER TO postgres;

--
-- Name: patientdetails; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE patientdetails (
    id integer NOT NULL,
    disease integer,
    dateofdiagnosis date,
    stage character varying(100),
    address integer
);


ALTER TABLE nav.patientdetails OWNER TO postgres;

--
-- Name: COLUMN patientdetails.address; Type: COMMENT; Schema: nav; Owner: postgres
--

COMMENT ON COLUMN patientdetails.address IS 'Patient address.';


--
-- Name: patientdistress; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE patientdistress (
    id integer NOT NULL,
    patientid integer,
    distressvalue integer,
    daterecrded timestamp with time zone,
    through character varying(50),
    otherdetail text
);


ALTER TABLE nav.patientdistress OWNER TO postgres;

--
-- Name: patientdistress_id_seq; Type: SEQUENCE; Schema: nav; Owner: postgres
--

CREATE SEQUENCE patientdistress_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nav.patientdistress_id_seq OWNER TO postgres;

--
-- Name: patientdistress_id_seq; Type: SEQUENCE OWNED BY; Schema: nav; Owner: postgres
--

ALTER SEQUENCE patientdistress_id_seq OWNED BY patientdistress.id;


--
-- Name: patientdistressdetail; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE patientdistressdetail (
    id integer NOT NULL,
    patiendistressid integer,
    distresstypeid integer,
    distressvalue boolean
);


ALTER TABLE nav.patientdistressdetail OWNER TO postgres;

--
-- Name: patientdistressdetail_id_seq; Type: SEQUENCE; Schema: nav; Owner: postgres
--

CREATE SEQUENCE patientdistressdetail_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nav.patientdistressdetail_id_seq OWNER TO postgres;

--
-- Name: patientdistressdetail_id_seq; Type: SEQUENCE OWNED BY; Schema: nav; Owner: postgres
--

ALTER SEQUENCE patientdistressdetail_id_seq OWNED BY patientdistressdetail.id;


--
-- Name: patientmedication; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE patientmedication (
    id integer NOT NULL,
    patientid integer,
    medicineid integer,
    carememberid integer,
    frequency character varying(250),
    specialinstruction text,
    method character varying(250),
    adddate timestamp with time zone,
    startdate timestamp with time zone,
    enddate timestamp with time zone,
    active boolean
);


ALTER TABLE nav.patientmedication OWNER TO postgres;

--
-- Name: COLUMN patientmedication.method; Type: COMMENT; Schema: nav; Owner: postgres
--

COMMENT ON COLUMN patientmedication.method IS 'If other than the specification';


--
-- Name: patientmedication_id_seq; Type: SEQUENCE; Schema: nav; Owner: postgres
--

CREATE SEQUENCE patientmedication_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nav.patientmedication_id_seq OWNER TO postgres;

--
-- Name: patientmedication_id_seq; Type: SEQUENCE OWNED BY; Schema: nav; Owner: postgres
--

ALTER SEQUENCE patientmedication_id_seq OWNED BY patientmedication.id;


--
-- Name: securityquestionmaster; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE securityquestionmaster (
    id integer NOT NULL,
    question text
);


ALTER TABLE nav.securityquestionmaster OWNER TO postgres;

--
-- Name: COLUMN securityquestionmaster.id; Type: COMMENT; Schema: nav; Owner: postgres
--

COMMENT ON COLUMN securityquestionmaster.id IS 'id';


--
-- Name: securityquestionmaster_id_seq; Type: SEQUENCE; Schema: nav; Owner: postgres
--

CREATE SEQUENCE securityquestionmaster_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nav.securityquestionmaster_id_seq OWNER TO postgres;

--
-- Name: securityquestionmaster_id_seq; Type: SEQUENCE OWNED BY; Schema: nav; Owner: postgres
--

ALTER SEQUENCE securityquestionmaster_id_seq OWNED BY securityquestionmaster.id;


--
-- Name: user; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE "user" (
    name character(50) NOT NULL,
    password character(75),
    usertype character(1),
    isactive boolean,
    id integer NOT NULL,
    email character(100),
    isverified boolean,
    usertypeid integer
);


ALTER TABLE nav."user" OWNER TO postgres;

--
-- Name: TABLE "user"; Type: COMMENT; Schema: nav; Owner: postgres
--

COMMENT ON TABLE "user" IS 'Common login table for all users.';


--
-- Name: COLUMN "user".name; Type: COMMENT; Schema: nav; Owner: postgres
--

COMMENT ON COLUMN "user".name IS 'Usre Name';


--
-- Name: COLUMN "user".password; Type: COMMENT; Schema: nav; Owner: postgres
--

COMMENT ON COLUMN "user".password IS 'Password';


--
-- Name: COLUMN "user".id; Type: COMMENT; Schema: nav; Owner: postgres
--

COMMENT ON COLUMN "user".id IS 'Id';


--
-- Name: user_id_seq; Type: SEQUENCE; Schema: nav; Owner: postgres
--

CREATE SEQUENCE user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nav.user_id_seq OWNER TO postgres;

--
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: nav; Owner: postgres
--

ALTER SEQUENCE user_id_seq OWNED BY "user".id;


--
-- Name: useraddress; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE useraddress (
    id integer NOT NULL,
    line1 character varying(200),
    line2 character varying(200),
    city character varying(100),
    state character varying(100),
    country character varying(100),
    zip character varying(100),
    lat double precision,
    lang double precision,
    phone character varying(20)
);


ALTER TABLE nav.useraddress OWNER TO postgres;

--
-- Name: useraddress_id_seq; Type: SEQUENCE; Schema: nav; Owner: postgres
--

CREATE SEQUENCE useraddress_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nav.useraddress_id_seq OWNER TO postgres;

--
-- Name: useraddress_id_seq; Type: SEQUENCE OWNED BY; Schema: nav; Owner: postgres
--

ALTER SEQUENCE useraddress_id_seq OWNED BY useraddress.id;


--
-- Name: usercertificate; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE usercertificate (
    id integer NOT NULL,
    userid integer NOT NULL,
    name character varying(250),
    description text
);


ALTER TABLE nav.usercertificate OWNER TO postgres;

--
-- Name: usercertificate_id_seq; Type: SEQUENCE; Schema: nav; Owner: postgres
--

CREATE SEQUENCE usercertificate_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nav.usercertificate_id_seq OWNER TO postgres;

--
-- Name: usercertificate_id_seq; Type: SEQUENCE OWNED BY; Schema: nav; Owner: postgres
--

ALTER SEQUENCE usercertificate_id_seq OWNED BY usercertificate.id;


--
-- Name: userdetails; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE userdetails (
    id integer NOT NULL,
    firstname character(100),
    lastname character(100),
    dob date,
    contactmethod integer,
    email character(100),
    mobile character(17),
    homephone character(17),
    seq1 integer,
    sea1 text,
    seq2 integer,
    sea2 text,
    tosflag boolean,
    tocflag boolean,
    createdate date,
    editdate date,
    verificationcode uuid,
    editperson integer
);


ALTER TABLE nav.userdetails OWNER TO postgres;

--
-- Name: usereducation; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE usereducation (
    id integer NOT NULL,
    userid integer NOT NULL,
    dgree character varying(200),
    school character varying(250),
    year character varying(50),
    logopath character varying(250)
);


ALTER TABLE nav.usereducation OWNER TO postgres;

--
-- Name: usereducation_id_seq; Type: SEQUENCE; Schema: nav; Owner: postgres
--

CREATE SEQUENCE usereducation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nav.usereducation_id_seq OWNER TO postgres;

--
-- Name: usereducation_id_seq; Type: SEQUENCE OWNED BY; Schema: nav; Owner: postgres
--

ALTER SEQUENCE usereducation_id_seq OWNED BY usereducation.id;


--
-- Name: userexpertise; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE userexpertise (
    id integer NOT NULL,
    userid integer NOT NULL,
    name character varying(250),
    description text
);


ALTER TABLE nav.userexpertise OWNER TO postgres;

--
-- Name: userexpertise_id_seq; Type: SEQUENCE; Schema: nav; Owner: postgres
--

CREATE SEQUENCE userexpertise_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nav.userexpertise_id_seq OWNER TO postgres;

--
-- Name: userexpertise_id_seq; Type: SEQUENCE OWNED BY; Schema: nav; Owner: postgres
--

ALTER SEQUENCE userexpertise_id_seq OWNED BY userexpertise.id;


--
-- Name: userimage; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE userimage (
    id integer NOT NULL,
    image bytea,
    imagetype character varying(50),
    imagename character varying(100)
);


ALTER TABLE nav.userimage OWNER TO postgres;

--
-- Name: COLUMN userimage.image; Type: COMMENT; Schema: nav; Owner: postgres
--

COMMENT ON COLUMN userimage.image IS 'User Image';


--
-- Name: usertypemaster; Type: TABLE; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE TABLE usertypemaster (
    id integer NOT NULL,
    name character(100),
    abbravation character(2)
);


ALTER TABLE nav.usertypemaster OWNER TO postgres;

--
-- Name: usertypemaster_id_seq; Type: SEQUENCE; Schema: nav; Owner: postgres
--

CREATE SEQUENCE usertypemaster_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nav.usertypemaster_id_seq OWNER TO postgres;

--
-- Name: usertypemaster_id_seq; Type: SEQUENCE OWNED BY; Schema: nav; Owner: postgres
--

ALTER SEQUENCE usertypemaster_id_seq OWNED BY usertypemaster.id;


SET search_path = public, pg_catalog;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

SET search_path = nav, pg_catalog;

--
-- Name: id; Type: DEFAULT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY careteammaster ALTER COLUMN id SET DEFAULT nextval('careteammaster_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY contactmethodmaster ALTER COLUMN id SET DEFAULT nextval('contactmethodmaster_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY designationmaster ALTER COLUMN id SET DEFAULT nextval('designationmaster_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY diseasemaster ALTER COLUMN id SET DEFAULT nextval('diseasemaster_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY distresstypemaster ALTER COLUMN id SET DEFAULT nextval('distresstypemaster_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY distrsscategorymaster ALTER COLUMN id SET DEFAULT nextval('distrsscategorymaster_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY invited ALTER COLUMN id SET DEFAULT nextval('invited_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY loginhistory ALTER COLUMN id SET DEFAULT nextval('loginhistory_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY mailtemplate ALTER COLUMN id SET DEFAULT nextval('mailtemplate_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY medicinemaster ALTER COLUMN id SET DEFAULT nextval('medicinemaster_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY patientdistress ALTER COLUMN id SET DEFAULT nextval('patientdistress_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY patientdistressdetail ALTER COLUMN id SET DEFAULT nextval('patientdistressdetail_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY patientmedication ALTER COLUMN id SET DEFAULT nextval('patientmedication_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY securityquestionmaster ALTER COLUMN id SET DEFAULT nextval('securityquestionmaster_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY "user" ALTER COLUMN id SET DEFAULT nextval('user_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY useraddress ALTER COLUMN id SET DEFAULT nextval('useraddress_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY usercertificate ALTER COLUMN id SET DEFAULT nextval('usercertificate_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY usereducation ALTER COLUMN id SET DEFAULT nextval('usereducation_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY userexpertise ALTER COLUMN id SET DEFAULT nextval('userexpertise_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY usertypemaster ALTER COLUMN id SET DEFAULT nextval('usertypemaster_id_seq'::regclass);


--
-- Data for Name: careteammaster; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY careteammaster (id, name, address) FROM stdin;
1	SURGERY	1
2	RADIATION	1
3	ONCOLOGY	1
4	RECONSTRUCTION	1
5	PRIMARY	1
\.


--
-- Name: careteammaster_id_seq; Type: SEQUENCE SET; Schema: nav; Owner: postgres
--

SELECT pg_catalog.setval('careteammaster_id_seq', 3, true);


--
-- Data for Name: careteammember; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY careteammember (careteamid, memberid, "primary") FROM stdin;
2	240	t
3	157	t
4	157	t
4	240	f
2	157	f
3	240	f
\.


--
-- Data for Name: contactmethodmaster; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY contactmethodmaster (id, name) FROM stdin;
1	Email                                             
2	SMS                                               
3	Call                                              
4	Mail                                              
\.


--
-- Name: contactmethodmaster_id_seq; Type: SEQUENCE SET; Schema: nav; Owner: postgres
--

SELECT pg_catalog.setval('contactmethodmaster_id_seq', 4, true);


--
-- Data for Name: designationmaster; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY designationmaster (id, abbr, designation) FROM stdin;
1	Dr	LEAD SURGEON
2	RN	PRIMARY NURSE
3	PA	PHYSICIAN ASSISTANT
4	\N	SCHEDULER
\.


--
-- Name: designationmaster_id_seq; Type: SEQUENCE SET; Schema: nav; Owner: postgres
--

SELECT pg_catalog.setval('designationmaster_id_seq', 4, true);


--
-- Data for Name: diseasemaster; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY diseasemaster (id, name) FROM stdin;
1	Breast Cancer
\.


--
-- Name: diseasemaster_id_seq; Type: SEQUENCE SET; Schema: nav; Owner: postgres
--

SELECT pg_catalog.setval('diseasemaster_id_seq', 1, true);


--
-- Data for Name: distresstypemaster; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY distresstypemaster (id, categoryid, name) FROM stdin;
1	1	Child care
2	1	Housing
3	1	Insurance/financial
4	1	Transportation
5	1	Work/school
6	1	Treatment decision
7	2	Dealing with children
8	2	Dealing with partner
9	2	Ability to have children
10	2	Family health issues
11	3	Depression
12	3	Fears
13	3	Nervousness
14	3	Sadness
15	3	Worry
16	3	Loss of interest in usual activities
17	4	Appearance
18	4	Bathing/Dressing
19	4	Breathing
20	4	Changes in Urination
21	4	Constipation
22	4	Diarrhea
23	4	Eating
24	4	Fatigue
25	4	Feeling Swollen
26	4	Fevers
27	4	Getting Around
28	4	Indigestion
29	4	Memory/Concentration
30	4	Mouth Sores
31	4	Nausea
32	4	Nose dry/congested
33	4	Pain
34	4	Sexual
35	4	Skin dry/itchy
36	4	Sleep
37	4	Tingling in hands/feet
\.


--
-- Name: distresstypemaster_id_seq; Type: SEQUENCE SET; Schema: nav; Owner: postgres
--

SELECT pg_catalog.setval('distresstypemaster_id_seq', 37, true);


--
-- Data for Name: distrsscategorymaster; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY distrsscategorymaster (id, name, active) FROM stdin;
1	PRACTICAL PROBLEMS	t
2	FAMILY PROBLEMS	t
3	EMOTIONAL PROBLEMS	t
4	PHYSICAL PROBLEMS	t
\.


--
-- Name: distrsscategorymaster_id_seq; Type: SEQUENCE SET; Schema: nav; Owner: postgres
--

SELECT pg_catalog.setval('distrsscategorymaster_id_seq', 4, true);


--
-- Data for Name: expertdetails; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY expertdetails (id, designation, practiceaddress, homeaddress, statement) FROM stdin;
240	1	1	1	Dr. Maen Abdelkarim Hussein MD is a male Internist, has 14 years of experience and practices in Internal Medicine, Hematology, Medical Oncology, and Hematology & Oncology.
157	2	1	1	Now celebrating its 30th anniversary, Florida Cancer Specialists & Research Institute (FCS) is the largest independent medical oncology/hema-tology practice in the United States. With over 170 physicians, 110 nurse practitioners and physician assistants and over 80 locations in our network, we are committed to providing world-class cancer care in community-based settings close to home. Recognized by the American Society of Clinical Oncology (ASCO) with a national Clinical Trials Participation Award, FCS offers patients access to more clinical trials than any private oncology practice in Florida.\n\nOur physicians are consistently ranked nationally as Top Doctors by U.S. News & World Report. Trained in such prestigious medical schools and research institutes as Duke, Stanford, Harvard, Emory, M.D. Anderson, and Memorial Sloan-Kettering, the physicians of Florida Cancer Specialists provide leadership and consultation in the states leading hospitals.\n\nFCS serves patients on the Gulf Coast from Naples to the greater Tampa Bay area, north as far as Tallahassee, in Orlando and surrounding Central Florida communities, and on the East Coast in Palm Beach County.\n\nAt Florida Cancer Specialists, our primary purpose – and our passion – is to provide the most advanced cancer treatment, using cutting-edge technologies, in a setting where patients can be close to home and surrounded by family and friends.
\.


--
-- Data for Name: invited; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY invited (id, name, password, usertype, activateonsignup, invitationsent, "timestamp") FROM stdin;
12	care1                                             	123456                                                                     	c	f	f	2014-05-08 18:00:10.682+05:30
\.


--
-- Name: invited_id_seq; Type: SEQUENCE SET; Schema: nav; Owner: postgres
--

SELECT pg_catalog.setval('invited_id_seq', 1, false);


--
-- Data for Name: loginhistory; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY loginhistory (id, userid, logintime, sessionid, frommethod, rememberflag, refrer) FROM stdin;
16	1	2014-05-08 20:41:08.174+05:30	1b1e7953-1994-48b2-9c2d-f9ce1cc94202                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
17	1	2014-05-08 20:44:21.536+05:30	898f8675-ca3a-466e-abe2-d8c9a8d449bc                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
18	6	2014-05-08 20:45:11.067+05:30	4a8765f6-9a07-478e-8787-a055d1db0f27                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
19	1	2014-05-09 16:29:41.115+05:30	53dbaa18-f2ec-4d34-af64-6153799b3c72                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
20	1	2014-05-09 16:44:10.231+05:30	d8668889-b7d7-4416-bd0e-3b49c1bba9dd                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
21	1	2014-05-09 16:48:36.551+05:30	f69fa302-68f1-444b-80a0-3efbccc5ae9f                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
22	1	2014-05-09 16:51:49.002+05:30	4aa40150-47f4-4c35-a402-4b06414d533e                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
23	6	2014-05-09 17:51:24.531+05:30	21b7a287-d104-437d-a5db-d2d115c4095f                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
24	6	2014-05-09 17:55:14.633+05:30	48c2faf5-db35-47b0-8309-be0d6f9903a5                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
25	1	2014-05-09 17:59:22.405+05:30	6e3ceefe-85bf-49cd-a64b-b4f231cb976c                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
26	1	2014-05-13 15:09:47.077+05:30	e7585c69-9c3e-489d-9a5f-933beb00efef                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
36	1	2014-05-14 16:15:13.741+05:30	edec8edf-9739-4202-83cb-fd1037918502                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
37	1	2014-05-14 19:04:07.821+05:30	ea7b32a2-e2bc-4bf9-bed7-2047e4dcb9da                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
38	1	2014-05-15 15:43:50.123+05:30	27243bd0-0776-4636-96f3-c6ad3f6a7684                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
39	1	2014-05-15 16:42:57.35+05:30	6319afd4-1039-4e30-8059-ba505ef914c5                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
40	1	2014-05-15 16:44:06.625+05:30	6319afd4-1039-4e30-8059-ba505ef914c5                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
41	7	2014-05-15 16:44:23.584+05:30	2fe7aa76-4671-4507-aa3a-d449ce4ae2a0                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
42	6	2014-05-15 16:44:47.802+05:30	f3d02499-d294-4692-aa7f-f6fdbff09344                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
43	7	2014-05-15 16:47:11.318+05:30	f023eb33-e54e-40ea-a996-ae2d51f3f553                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
44	6	2014-05-15 16:47:27.546+05:30	7b7f747b-77cf-47c4-8890-e11d387b3758                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
46	7	2014-05-15 16:58:54.737+05:30	47cfa3a7-dc0c-457f-a104-03d77bfb1e0d                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
47	6	2014-05-15 17:00:17.867+05:30	f16d2b97-b3d1-44df-bb53-4f2b2f768687                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
48	7	2014-05-15 17:00:32.673+05:30	aa269795-4b10-414f-abbb-d5271c5f0ebc                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
45	1	2014-05-15 16:47:51.969+05:30	b7163a73-ace6-49b8-958d-eeaf7ad75516                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
49	1	2014-05-15 17:49:02.189+05:30	4effd1e0-a33e-4600-aaae-2dbd4c962ab5                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
50	7	2014-05-15 18:34:41.064+05:30	c5c4667c-2574-4aea-8176-317cd38433cd                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
51	6	2014-05-15 18:34:53.795+05:30	fd6ff4d3-1481-4b6d-953e-474458ff48ac                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
52	1	2014-05-15 18:40:47.542+05:30	3b9772d8-d907-4c22-85c8-30d709001874                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
53	1	2014-05-16 15:04:42.553+05:30	676d0081-68a7-476c-bdee-fb8052a87c04                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
54	1	2014-05-16 16:34:40.73+05:30	2b9dd1f9-d43b-49ab-89b8-4af326522118                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
55	6	2014-05-16 16:37:30.44+05:30	ea5b444f-54ac-4831-a529-0f731cce9f96                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
56	7	2014-05-16 16:38:45.207+05:30	c22afff5-b783-46ed-96a7-33128d84f0fc                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
57	6	2014-05-16 16:43:23.44+05:30	fe27eca1-e90a-4e16-82c6-b182d546264c                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
58	1	2014-05-16 16:44:47.927+05:30	5499cc8c-2d74-40c7-868e-a02739a5b905                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
59	1	2014-05-16 17:12:47.455+05:30	6fb62d3a-e215-4f44-836d-9e6e7b28b6bb                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
60	6	2014-05-16 17:13:45.265+05:30	2867601d-0030-4ec6-9e25-6ff0b8a583f1                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
61	7	2014-05-16 17:15:05.661+05:30	f0249308-dfc2-45a6-8fa8-6f7b77b5142f                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
62	1	2014-05-16 17:59:06.949+05:30	ee62155a-2da9-4b95-8ec5-604c915dbdb2                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
63	1	2014-05-16 18:37:44.789+05:30	451f8b14-dd55-4cfa-8b7b-c8c933dd5c7a                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
64	1	2014-05-16 18:54:45.609+05:30	31101207-bbc7-4c5d-9c36-5656e1d14832                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
65	6	2014-05-16 18:55:58.841+05:30	d283ae29-b98f-40ae-b074-a50aa5c5ac43                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
66	1	2014-05-16 19:00:31.469+05:30	e8510602-bd32-48ea-82b3-500955759d24                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
67	1	2014-05-16 19:02:03.164+05:30	f4054841-d44d-4fc2-a7e4-8bc622e5ee1b                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
68	6	2014-05-16 19:12:48.94+05:30	92d02802-fee3-47bb-9871-d2563c7a7dd7                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
70	6	2014-05-16 19:33:06.703+05:30	5e38ebba-b35f-4925-bfca-7006e735b970                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
69	7	2014-05-16 19:25:26.995+05:30	3400c5f7-7d15-4a1c-8d30-81b5eee4671a                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
71	1	2014-05-16 19:46:06.43+05:30	6cdb675c-ff62-4786-a4c0-a3eeb1d4f131                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
72	1	2014-05-16 19:48:02.369+05:30	470bea7b-cb48-4820-acf7-f017cbc68a7a                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
73	1	2014-05-16 20:15:50.735+05:30	9632b9f2-e822-4bc4-b599-b5fafe8fe60f                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
74	1	2014-05-19 15:42:15.444+05:30	b2aceed8-4171-468a-85a9-cf97d2b5bc36                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
76	75	2014-05-19 18:35:27.919+05:30	51c6e09d-b0a5-4f32-b54f-31dbcae1d9ef                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
80	79	2014-05-19 19:00:20.416+05:30	51c6e09d-b0a5-4f32-b54f-31dbcae1d9ef                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
85	84	2014-05-20 15:21:15.524+05:30	05c35bf8-26e2-481a-b5d7-a9c80fd2626c                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
87	86	2014-05-20 15:54:19.561+05:30	05c35bf8-26e2-481a-b5d7-a9c80fd2626c                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
88	1	2014-05-20 17:32:50.206+05:30	05c35bf8-26e2-481a-b5d7-a9c80fd2626c                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
89	1	2014-05-20 17:33:38.467+05:30	5f3fdf15-8b95-4cab-ad5a-644c3ebf07c2                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
90	7	2014-05-20 17:34:04.666+05:30	1c9dc61c-a8f2-4446-86d2-4cea3069888f                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
91	7	2014-05-20 17:34:18.702+05:30	a261ad15-395e-4b7f-ba05-12086097ed34                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
93	92	2014-05-20 17:35:07.1+05:30	c10bb84b-7b3e-4341-a113-4cd46380c3ff                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
95	94	2014-05-20 18:25:52.448+05:30	c10bb84b-7b3e-4341-a113-4cd46380c3ff                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
97	96	2014-05-20 18:28:18.192+05:30	c10bb84b-7b3e-4341-a113-4cd46380c3ff                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
98	96	2014-05-20 18:28:39.367+05:30	c10bb84b-7b3e-4341-a113-4cd46380c3ff                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
100	99	2014-05-20 19:11:55.742+05:30	770f5a75-0d28-48d7-98ba-759f90c3531e                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
102	101	2014-05-20 19:13:44.837+05:30	770f5a75-0d28-48d7-98ba-759f90c3531e                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
103	101	2014-05-20 19:15:36.048+05:30	770f5a75-0d28-48d7-98ba-759f90c3531e                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
104	6	2014-05-20 19:56:23.428+05:30	f3b283a4-a62a-4c7d-a090-e87bf05032cb                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
105	1	2014-05-20 19:56:50.485+05:30	fa219bb5-b50d-433c-a4ba-d7e9b86e8826                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
106	6	2014-05-20 19:57:11.311+05:30	5e6dacea-af03-44ad-a97a-6dbb270c034c                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
107	1	2014-05-20 19:57:47.486+05:30	3b843b3c-689a-4803-920b-414b032628c8                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
108	6	2014-05-20 19:58:29.285+05:30	b31aedc9-d529-43b4-a53d-cc54f6932f5c                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
109	101	2014-05-21 16:18:51.845+05:30	edee9a31-50d1-4408-a062-3cc98aa1161d                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
110	1	2014-05-21 16:19:22.784+05:30	3e149f5f-eba2-464c-851e-aca13dc289f6                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
111	101	2014-05-21 16:19:57.586+05:30	12748307-20f9-4c7f-bf0d-c09b697078f7                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
112	6	2014-05-21 16:50:32.231+05:30	d6c51bd8-8e4a-4985-909b-c812367c5fff                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
113	7	2014-05-21 16:50:44.897+05:30	2c025012-1ecf-4cec-b23f-de77d9850c5e                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
114	7	2014-05-21 18:34:35.707+05:30	f329456a-0432-462c-9d74-56fa9c56118d                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
118	117	2014-05-21 20:55:13.047+05:30	6c6e7dad-8279-4e3d-9482-b17ad7dc393f                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
128	127	2014-05-22 15:20:24.361+05:30	c2bb49c2-1f2a-4dca-82ed-04f96539c4f8                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
130	129	2014-05-22 15:24:26.122+05:30	c2bb49c2-1f2a-4dca-82ed-04f96539c4f8                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
131	129	2014-05-22 15:24:42.775+05:30	c2bb49c2-1f2a-4dca-82ed-04f96539c4f8                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
132	129	2014-05-22 15:24:55.908+05:30	3cc31d9c-c087-47cd-af29-1aa861ed982a                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
133	1	2014-05-22 15:30:41.803+05:30	02aaa952-dd5a-4d46-87e6-a0f82f580e57                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
134	75	2014-05-22 15:45:14.961+05:30	8ca2b5b5-0ec2-4dd3-9de3-4f71ba641c8d                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
135	75	2014-05-22 15:45:30.533+05:30	5c93cb74-02fa-4741-a481-c10c03b5c8f5                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
137	136	2014-05-22 15:50:01.995+05:30	cb75773b-1ec4-42cd-8ea8-add0a8214adc                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
138	136	2014-05-22 15:50:11.968+05:30	cb75773b-1ec4-42cd-8ea8-add0a8214adc                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
139	1	2014-05-22 16:17:27.326+05:30	14b85180-edd6-4cc3-b836-45d06eabfb2e                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
140	1	2014-05-22 16:18:20.331+05:30	406ccaf7-427b-4e4f-932c-57e0382ea04f                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
141	77	2014-05-22 16:18:38.788+05:30	162fc120-5636-47b0-ad59-12edff523715                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
142	77	2014-05-22 16:20:56.545+05:30	c073eda2-bdff-48c4-b765-a9e7d5850075                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
143	77	2014-05-22 16:21:42.103+05:30	e46c1889-c2d8-4343-ad4d-85576ede358b                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
144	77	2014-05-22 16:25:08.216+05:30	22ebc458-94ab-43ad-bc7b-7bf9bc22338e                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
145	77	2014-05-22 16:25:38.527+05:30	cffbc7ba-cae1-4074-a2e8-899201ac69e5                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
146	77	2014-05-22 16:33:33.866+05:30	a7fa528c-12f0-453c-9776-5a03649269bf                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
147	77	2014-05-22 16:34:29.702+05:30	9f3a43e9-5bab-41c8-8000-cdf6e3b31858                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
148	1	2014-05-22 16:48:16.392+05:30	869fcc8e-ea33-41a1-b8bd-147e1e8b9112                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
149	129	2014-05-22 16:48:41.391+05:30	8d680d6c-f777-43c9-acaf-ea9ed9f42c36                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
151	150	2014-05-22 16:55:14.075+05:30	53adb969-00db-4bb3-be90-dbbc96ee89cb                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
152	150	2014-05-22 16:58:29.904+05:30	53adb969-00db-4bb3-be90-dbbc96ee89cb                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
154	153	2014-05-22 17:09:36.097+05:30	cf371bb1-058f-44df-ab27-a900818d690b                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
155	153	2014-05-22 17:10:55.07+05:30	cf371bb1-058f-44df-ab27-a900818d690b                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
156	153	2014-05-22 17:11:32.368+05:30	5d5be56a-f809-43fb-85e8-d1888105f6f8                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
158	157	2014-05-22 17:12:33.865+05:30	cd83c0f3-289c-43bf-aa25-e875d9e92757                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
159	157	2014-05-22 17:12:45.297+05:30	cd83c0f3-289c-43bf-aa25-e875d9e92757                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
160	1	2014-05-22 18:25:21.352+05:30	79c317b0-942c-482e-9372-558499bbe5da                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
161	1	2014-05-23 15:56:26.799+05:30	2764651c-e094-408c-8611-81f58a47aac9                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
162	153	2014-05-23 19:41:41.336+05:30	dd6b8ea5-0089-4dba-bed0-79c6beb25842                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
164	163	2014-05-26 14:10:27.658+05:30	09c457a7-3750-44d9-912f-9b471683c3d1                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
165	163	2014-05-26 14:10:57.779+05:30	0dea7d1d-9dbb-42f3-8c55-e5fd42c437f1                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
166	163	2014-05-26 14:17:46.123+05:30	680805e8-f900-4d88-8aff-2f768d715c90                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
167	163	2014-05-26 14:20:44.369+05:30	09c457a7-3750-44d9-912f-9b471683c3d1                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
168	163	2014-05-26 14:55:12.212+05:30	101aee1a-8ee4-4a1d-8f6e-c99bb5f50f99                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
169	163	2014-05-26 15:39:37.982+05:30	9a415560-4a82-40fb-b7a6-35d398478b25                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
170	1	2014-05-26 15:41:16.639+05:30	8c344c8c-6d77-48ff-9ef6-8cc37474d0b5                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
171	163	2014-05-26 16:29:46.571+05:30	c106805d-db45-4c1f-a494-466afee8c234                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
172	163	2014-05-26 16:31:54.661+05:30	9a7bf23c-e288-421b-8b9b-9d877b944cf4                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
173	163	2014-05-26 16:41:01.747+05:30	c672387b-daa1-44a0-97d0-65acbfd897be                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
174	163	2014-05-26 17:26:22.319+05:30	5c6f5ac5-85fc-436d-993e-c5605f16695c                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
175	163	2014-05-26 17:36:22.354+05:30	de5e7dde-08d1-48ce-aa58-435b7eb78071                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
176	163	2014-05-26 17:37:02.648+05:30	8af891b9-c4e8-4a57-9efb-c97d5dda8bf5                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
178	163	2014-05-26 17:49:31.96+05:30	1a8ccb68-73c6-4306-bade-570b464c424a                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
179	163	2014-05-26 17:50:14.804+05:30	921829c4-96bb-4bfe-816f-4e5f4cd1fafb                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
180	163	2014-05-26 17:50:39.924+05:30	29fc2146-7e20-4a96-87be-278c78cef6f1                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
181	163	2014-05-26 17:51:30.712+05:30	9cef2b9d-8979-4273-b05a-6d555e54f958                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
184	163	2014-05-26 19:09:23.228+05:30	28351d40-3cf2-4468-979b-1ebbd35d17e2                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
177	163	2014-05-26 17:45:11.536+05:30	e253ce9a-c8a4-446d-8ca4-f70cb33fe852                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
182	6	2014-05-26 19:07:03.043+05:30	7c5ec8b3-4376-4685-9735-83e73b7245f6                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
183	150	2014-05-26 19:08:10.671+05:30	3f13f9fe-9c84-4fc6-afce-d7a20d9339e9                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
185	129	2014-05-26 19:35:00.302+05:30	99a1edd8-35f1-4c8c-911b-f620b91325de                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
186	163	2014-05-26 20:08:54.188+05:30	4d71ab11-ea50-427e-8d74-845c502113f0                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
187	1	2014-05-27 15:27:50.166+05:30	96238371-1f1b-4ea8-b1df-5509ebc3247d                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
188	153	2014-05-27 15:28:26.705+05:30	680156f7-b213-48cf-a2c9-8175c800918c                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
189	153	2014-05-27 19:08:20.232+05:30	584a5170-9c95-477d-9d27-85f5327017dd                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
190	153	2014-05-27 21:34:04.169+05:30	12cea190-229e-4058-8410-02b608c068e3                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
191	1	2014-05-28 10:21:12.342+05:30	d0dda46e-8bbb-4205-8b2e-d686e6276072                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
192	153	2014-05-28 10:21:28.191+05:30	59d8e426-0661-4284-9f82-83fef29cd6c6                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
193	150	2014-05-28 21:19:51.188+05:30	bf15f0d1-ca29-476f-9af6-617c67c91bd3                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
194	153	2014-05-28 21:20:01.379+05:30	bf15f0d1-ca29-476f-9af6-617c67c91bd3                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
195	153	2014-05-28 21:20:16.783+05:30	bf15f0d1-ca29-476f-9af6-617c67c91bd3                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
196	153	2014-05-28 21:20:36.572+05:30	bf15f0d1-ca29-476f-9af6-617c67c91bd3                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
197	153	2014-05-28 21:21:00.211+05:30	3e2f2297-d58c-4615-a10a-4bd886acbf2c                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
198	153	2014-05-28 21:21:13.269+05:30	9c2f3cb1-4c6d-4024-84fd-2d7151053c3b                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
199	153	2014-05-28 21:21:36.451+05:30	0918f352-6ec0-44cb-b523-2d44f1f9fb6e                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
200	153	2014-05-28 21:23:05.349+05:30	ce6bb33e-06d1-4d3a-adb1-0ebf070a0925                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
201	150	2014-05-29 10:53:47.014+05:30	735be9b1-e9c3-4f62-849f-98067e07a9f1                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
202	1	2014-05-29 10:54:13.795+05:30	c6e00bfb-905b-47d0-9a56-e5840e5baa9a                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
203	153	2014-05-29 10:54:27.189+05:30	fc0f64a7-0be5-4271-b7ad-d33e055eb065                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
204	150	2014-05-29 11:48:28.174+05:30	fec8d74c-bda5-4608-9562-9b75af778969                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
206	205	2014-05-29 12:33:11.895+05:30	a170e3a6-f7a4-4f24-a67d-96c901ddde26                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
207	1	2014-05-29 12:36:38.072+05:30	3c336e8b-536e-4825-a71e-c62b6c65005b                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
208	1	2014-05-29 12:37:10.873+05:30	a170e3a6-f7a4-4f24-a67d-96c901ddde26                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
209	205	2014-05-29 12:37:50.469+05:30	0f91eacf-22ae-46a0-aead-769297204de4                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
211	210	2014-05-29 14:45:20.215+05:30	3c336e8b-536e-4825-a71e-c62b6c65005b                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
212	210	2014-05-29 14:49:57.3+05:30	3c336e8b-536e-4825-a71e-c62b6c65005b                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
213	153	2014-05-29 14:57:37.57+05:30	b6138d40-a5ef-4363-995c-43b41d5dc379                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
215	214	2014-05-29 15:20:18.14+05:30	a4deb85c-804f-49b3-99d4-ea2d542ead4d                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
216	214	2014-05-29 15:20:46.948+05:30	a4deb85c-804f-49b3-99d4-ea2d542ead4d                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
217	214	2014-05-29 15:21:11.611+05:30	2156ca56-4e83-4436-b0e8-3f11de281192                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
219	218	2014-05-29 15:23:57.547+05:30	d326b59e-0b86-43e1-a596-bb8422d7b78b                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
221	220	2014-05-29 15:28:15.617+05:30	d326b59e-0b86-43e1-a596-bb8422d7b78b                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
223	222	2014-05-29 15:36:37.206+05:30	988693fc-27f2-4e9e-9e40-b31aad0c4aa1                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
224	222	2014-05-29 15:36:49.972+05:30	988693fc-27f2-4e9e-9e40-b31aad0c4aa1                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
226	225	2014-05-29 15:37:40.127+05:30	aad12349-950f-4423-9b88-82fa0725ce87                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
228	227	2014-05-29 15:39:21.901+05:30	aad12349-950f-4423-9b88-82fa0725ce87                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
229	1	2014-05-29 15:39:57.31+05:30	aad12349-950f-4423-9b88-82fa0725ce87                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
230	225	2014-05-29 15:40:20.96+05:30	bdc1080f-03c3-4566-9bbd-0eb27c11fda2                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
232	231	2014-05-29 15:51:34.118+05:30	c633d8a2-98f9-412b-a7b4-51f04069f9e3                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
233	1	2014-05-29 15:52:34.892+05:30	74bb7edb-720c-4988-9dd4-a4e25e24d384                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
234	231	2014-05-29 15:52:45.992+05:30	c633d8a2-98f9-412b-a7b4-51f04069f9e3                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
236	235	2014-05-29 15:58:08.498+05:30	496e1920-60c6-4601-b2b2-3843033e9584                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
238	237	2014-05-29 16:00:03.803+05:30	496e1920-60c6-4601-b2b2-3843033e9584                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
239	237	2014-05-29 16:00:37.603+05:30	496e1920-60c6-4601-b2b2-3843033e9584                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
241	240	2014-05-29 16:59:17.889+05:30	3ed65b70-267a-4d63-a49f-9f32b7d0757c                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
242	240	2014-05-29 16:59:32.579+05:30	3ed65b70-267a-4d63-a49f-9f32b7d0757c                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
243	237	2014-05-29 17:08:45.057+05:30	393c97e4-e7b4-4c0e-99c1-2a165a06aa3b                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
244	240	2014-05-29 19:57:56.812+05:30	de25a3ac-1642-44cb-b68d-92faded20ca4                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
245	240	2014-05-29 20:04:25.838+05:30	ef23c944-75be-4e81-afb0-26af202c90d9                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
246	1	2014-05-29 20:17:14.912+05:30	7820ad2a-cf8a-484f-a373-28e31316e39d                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
247	153	2014-05-29 20:30:27.291+05:30	f94b8344-edd5-4c57-87ce-c0dfd7319054                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
248	1	2014-05-30 10:21:46.918+05:30	63547269-ba34-4240-bce1-94952796c84c                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
249	237	2014-05-30 10:23:30.968+05:30	47007e62-be03-4214-9cb8-7fba404c07a3                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
251	250	2014-05-30 10:56:41.575+05:30	06241e3e-8e8c-47f8-b4fa-de4536ed3ee2                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
252	250	2014-05-30 10:57:15.262+05:30	06241e3e-8e8c-47f8-b4fa-de4536ed3ee2                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
253	157	2014-05-30 14:52:11.44+05:30	4643c5c3-b37b-4955-bf15-7c9d80573242                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
254	250	2014-05-30 14:52:39.337+05:30	1459bd16-74ec-4d06-b5a8-d86dcd467194                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
255	237	2014-06-02 19:42:20.008+05:30	368a4820-6aa0-43e5-865b-851f53f752a3                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
256	1	2014-06-03 16:09:44.17+05:30	be0eda6d-94cd-4532-9e3c-f5f6fdb1bac4                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
257	237	2014-06-03 16:09:57.86+05:30	2952be5a-28be-470d-b226-75e5037e7361                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
258	237	2014-06-03 19:47:35.766+05:30	8024a849-27df-41a0-bf7f-8b1e6d959504                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
259	237	2014-06-03 19:48:42.777+05:30	d5e3d399-1d02-4bc2-a273-c806f67e36a6                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
260	1	2014-06-04 14:50:09.286+05:30	3d0fc53f-39e8-44d7-a498-f9fb5f35e199                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
261	250	2014-06-04 14:50:25.246+05:30	ccf3ce8f-dca9-41c2-bcc2-5925cf74967e                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
262	250	2014-06-04 15:06:06.545+05:30	a22b1b5e-34d0-49f9-a435-8a053fa7de99                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
289	288	2014-06-05 16:56:29.768+05:30	f4f180c3-9ed7-424a-b085-aff04d4bafd6                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
291	290	2014-06-05 17:05:42.666+05:30	f4f180c3-9ed7-424a-b085-aff04d4bafd6                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
293	292	2014-06-05 17:17:48.026+05:30	f4f180c3-9ed7-424a-b085-aff04d4bafd6                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
295	294	2014-06-05 17:18:39.372+05:30	f4f180c3-9ed7-424a-b085-aff04d4bafd6                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
297	296	2014-06-05 17:23:41.078+05:30	f4f180c3-9ed7-424a-b085-aff04d4bafd6                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
298	296	2014-06-05 17:24:39.503+05:30	16d183e2-54cb-4781-8c79-2e129acdd6a7                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
299	296	2014-06-05 17:38:52.84+05:30	3b2d020f-f8d5-414a-a370-295f8a078774                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
306	296	2014-06-05 18:21:38.852+05:30	f4f180c3-9ed7-424a-b085-aff04d4bafd6                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
307	1	2014-06-05 18:21:57.952+05:30	ebb25842-c9ad-4b15-b76b-9766fc3c8a53                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
308	296	2014-06-05 18:22:22.366+05:30	e8af2a5f-de05-4b14-854e-602faac975fe                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
309	1	2014-06-06 18:43:16.556+05:30	8d36b332-ce8a-498b-b04a-bf3121caf4f7                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
310	296	2014-06-06 18:44:40.457+05:30	5d1bc882-fb61-4e09-98ed-8bc80017385a                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
311	296	2014-06-06 18:48:12.226+05:30	d90ab50b-c884-4128-a93d-90bbb21f4678                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
312	240	2014-06-06 19:05:10.9+05:30	d0cc5896-9557-4a54-8fa5-e2163aba20c1                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
313	1	2014-06-09 15:06:31.091+05:30	1eabe02e-1dfd-4f8e-8b10-1f4478cb651d                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
314	296	2014-06-09 15:06:43.581+05:30	8a56c976-ada4-4a5b-9068-dc3120cf10a7                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
316	315	2014-06-09 15:10:04.409+05:30	1c012aff-402b-4d7c-96b5-07f9886b520b                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
317	240	2014-06-09 15:11:39.247+05:30	1c012aff-402b-4d7c-96b5-07f9886b520b                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
318	296	2014-06-09 15:15:05.171+05:30	fcbd1034-22ff-41c3-b3b5-79dcef33f1be                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
319	240	2014-06-09 16:41:34.126+05:30	fd4e3de7-805c-4927-a5ec-f8735ceb5ba8                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
320	237	2014-06-09 17:59:50.142+05:30	eeefc2a5-00f4-4cbf-a709-cdb39c1ffd3c                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
321	1	2014-06-09 18:08:13.775+05:30	9caf5101-6a5b-4d50-8831-8379048dd608                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
322	1	2014-06-10 15:50:21.529+05:30	021ab550-7380-4cbc-a1bb-30950ee7abf0                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
323	296	2014-06-10 15:50:33.399+05:30	0a0affe3-bd96-4b6d-90a4-ffd22e59ee2a                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
324	296	2014-06-10 15:51:33.05+05:30	53daaf6b-f8be-475c-afdb-4cc69bc03aab                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
325	296	2014-06-10 15:53:21.288+05:30	2f84f192-3323-4b62-832b-24dcb0b94c6c                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
326	296	2014-06-10 15:53:55.788+05:30	7becf296-72ef-4f09-8745-e84ab7ef0bbf                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
327	296	2014-06-10 15:55:35.494+05:30	d1598472-df79-4072-b397-12c043dcc7f6                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
328	296	2014-06-10 15:56:09.984+05:30	6b3cdf11-9e68-406d-b2fb-67839e4cbd6b                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
330	329	2014-06-10 15:58:11.503+05:30	1a7d771b-ee4e-4117-a712-5ad843398f75                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
331	329	2014-06-10 15:59:01.898+05:30	1a7d771b-ee4e-4117-a712-5ad843398f75                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
332	329	2014-06-10 15:59:11.661+05:30	c2982e14-147e-405b-9a6a-d9dc358583a8                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
351	296	2014-06-10 17:25:51.277+05:30	90497e72-9596-4acb-bf8a-8ac16daa22ad                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
353	329	2014-06-10 19:03:52.9+05:30	7fff1423-84b1-4b67-99a6-7d6131c2bab3                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
354	1	2014-06-11 19:15:38.441+05:30	b6348721-fe47-4429-993d-672f990eb3e3                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
355	329	2014-06-11 19:15:51.053+05:30	d6639ff0-a6bb-4ba7-88ec-7bf53a3a78c2                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
360	359	2014-06-11 19:35:13.429+05:30	c038fd4e-8aba-40f4-bc10-4daa850ea08c                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
364	359	2014-06-11 19:45:04.088+05:30	c038fd4e-8aba-40f4-bc10-4daa850ea08c                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
367	1	2014-06-13 10:35:37.376+05:30	4575c28d-3695-485f-9ad7-4c8bd3a79dac                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
368	359	2014-06-13 10:35:50.794+05:30	b7ec06c9-d722-4b23-8195-cb9eb4c4dee9                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
369	1	2014-06-13 10:36:34.415+05:30	e9ee4ad7-2039-4c86-b3d1-d62dc25eac8a                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
370	240	2014-06-13 10:36:51.876+05:30	8f01eb61-b512-4b46-bc5d-506dc55ee2dc                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
371	359	2014-06-13 15:21:10.072+05:30	d26cd4fb-5d21-4492-bc83-929bbb37a2f0                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
373	372	2014-06-16 12:33:23.404+05:30	791e665d-c0a0-457b-936e-fda439fbe82f                                                                                                                  	local               	f	                                                                                                                                                                                                                                                          
377	1	2014-06-16 12:57:49.459+05:30	a7fd29a4-8502-4c49-942b-f33d2f0578cc                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
378	372	2014-06-16 12:58:08.884+05:30	791e665d-c0a0-457b-936e-fda439fbe82f                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
379	372	2014-06-16 12:58:55.725+05:30	0268afe5-9710-49e3-b5c3-53373f0c56cf                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
380	372	2014-06-16 13:00:05.395+05:30	2e62729a-67d0-450f-a164-8396a70e9a41                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
381	372	2014-06-16 13:07:45.313+05:30	649d3844-18b2-40c9-80e7-d6ac439e3b89                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
382	1	2014-06-17 13:57:45.541+05:30	f0c9266b-73ab-41ca-b5f1-786448b39b76                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
383	372	2014-06-17 13:58:03.95+05:30	ead50a98-47f3-4da9-bb4f-3cd405f5a681                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
384	372	2014-06-17 14:07:58.485+05:30	3594c8fa-1332-42e6-a595-a9736314f92a                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
385	240	2014-06-17 14:31:32.218+05:30	45e7308b-e6aa-4a02-80fe-314beb15e496                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
386	240	2014-06-17 14:35:51.764+05:30	8a5371d1-1cff-40c2-a9a3-c994ddbed340                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
387	1	2014-06-18 19:42:15.498+05:30	46ad6ea8-a39f-4c95-8313-90f1f3ae8882                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
388	372	2014-06-18 19:42:34.985+05:30	746f8053-d0e0-4229-805e-f68febe78cc0                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
389	372	2014-06-18 20:41:53.722+05:30	322450d4-d151-4d8b-8a3e-7624fd731ef4                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
390	1	2014-06-19 18:10:49.432+05:30	18d857ab-44d7-4522-95b2-8e1612fce8c9                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
391	372	2014-06-19 18:11:03.747+05:30	1d120aee-b91a-473c-b43a-b96658e62b93                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
392	1	2014-06-20 16:34:46.219+05:30	cb6cbcbd-b0a2-41e3-bcb8-3e500d57e324                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
393	372	2014-06-20 16:34:59.611+05:30	f2ebaec7-c713-452e-a9d5-07213cad3892                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
394	1	2014-06-23 14:53:17.715+05:30	097b2a7f-99d8-4555-acf4-0ea4563a596b                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
395	372	2014-06-23 14:53:35.981+05:30	91f19a4c-ccf4-45ad-bb6e-8c6f7830c29a                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
396	1	2014-07-02 15:09:37.626+05:30	a069a114-8d34-4360-a3cf-fe9d38a03944                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
397	372	2014-07-02 15:10:01.761+05:30	21a7f3cc-7962-4301-8acd-a277face0741                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
398	372	2014-07-02 16:20:22.3+05:30	dc13ec32-911b-453b-958f-4e98d7568a17                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
410	359	2014-07-02 17:38:06.356+05:30	f5b1375e-07e5-4d67-a157-0d103eb7cb0b                                                                                                                  	login               	f	                                                                                                                                                                                                                                                          
\.


--
-- Name: loginhistory_id_seq; Type: SEQUENCE SET; Schema: nav; Owner: postgres
--

SELECT pg_catalog.setval('loginhistory_id_seq', 1, false);


--
-- Data for Name: mailtemplate; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY mailtemplate (id, mailtemplate, mailsubject, type, fromfield, ccfield, bccfield, companyid) FROM stdin;
1	welcome	Welcome: TAH Navigator	html	admin@talkabouthealth.com	admin@talkabouthealth.com	admin@talkabouthealth.com	1
5	forgot	TAH Your new password	html	admin@talkabouthealth.com	\N	\N	1
6	notification1	Good morning	html	admin@talkabouthealth.com	\N	\N	1
2	activation	Welcome to Moffitt – TVRH! Get ready for your first appointment and sign up for our online Navigator tool.	html	admin@talkabouthealth.com	\N	\N	1
\.


--
-- Name: mailtemplate_id_seq; Type: SEQUENCE SET; Schema: nav; Owner: postgres
--

SELECT pg_catalog.setval('mailtemplate_id_seq', 6, true);


--
-- Data for Name: medicinemaster; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY medicinemaster (id, genericname, brandname, method, image) FROM stdin;
1	TAMOXIFAN	Nolvadex	Daily, 1 pill per day	\N
2	TRASTUZUMAB	Herceptin	1 Weekly infusion	\N
3	TAMOXIFAN	Nolvadex	Daily, 1 pill per day	\N
4	TRASTUZUMAB	Herception	1 weekly infusion	\N
\.


--
-- Name: medicinemaster_id_seq; Type: SEQUENCE SET; Schema: nav; Owner: postgres
--

SELECT pg_catalog.setval('medicinemaster_id_seq', 4, true);


--
-- Data for Name: patiencareteam; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY patiencareteam (careteamid, patienid) FROM stdin;
2	153
3	153
2	231
3	231
2	237
3	237
2	250
3	250
2	288
3	288
2	290
3	290
2	294
3	294
2	296
3	296
2	315
3	315
2	329
3	329
2	359
3	359
2	372
3	372
\.


--
-- Data for Name: patientdetails; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY patientdetails (id, disease, dateofdiagnosis, stage, address) FROM stdin;
153	1	2014-05-14	III	\N
372	\N	\N	\N	409
359	\N	\N	\N	411
\.


--
-- Data for Name: patientdistress; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY patientdistress (id, patientid, distressvalue, daterecrded, through, otherdetail) FROM stdin;
352	296	5	2014-06-10 17:37:34.081+05:30	web	\N
333	329	5	2014-06-10 16:08:37.555+05:30	web	\N
303	296	1	2014-06-05 18:12:20.807+05:30	web	\N
300	296	1	2014-06-05 17:38:59.917+05:30	web	\N
285	250	1	2014-06-04 20:36:30.832+05:30	web	\N
277	250	1	2014-06-04 20:23:11.28+05:30	web	\N
272	250	1	2014-06-04 20:17:35.243+05:30	web	\N
268	250	1	2014-06-04 00:00:00+05:30	web	\N
265	250	1	2014-06-04 00:00:00+05:30	web	\N
264	250	8	2014-06-04 00:00:00+05:30	web	\N
263	250	4	2014-06-04 00:00:00+05:30	web	\N
356	329	7	2014-06-11 19:34:06.801+05:30	web	Test
361	359	7	2014-06-11 19:36:15.671+05:30	web	sdf s dfs fsdf sff sf
365	359	6	2014-06-11 19:45:11.82+05:30	web	gdf df dfgdfgdfg
374	290	1	2014-06-16 12:42:48.565+05:30	web	
\.


--
-- Name: patientdistress_id_seq; Type: SEQUENCE SET; Schema: nav; Owner: postgres
--

SELECT pg_catalog.setval('patientdistress_id_seq', 1, false);


--
-- Data for Name: patientdistressdetail; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY patientdistressdetail (id, patiendistressid, distresstypeid, distressvalue) FROM stdin;
266	265	1	t
267	265	6	t
269	268	1	t
270	268	3	t
271	268	6	t
273	272	1	t
274	272	3	t
275	272	4	t
276	272	6	t
278	277	1	t
279	277	3	t
280	277	4	t
281	277	6	t
282	277	20	t
283	277	29	t
284	277	34	t
286	285	4	t
287	285	34	t
301	300	5	t
302	300	31	t
304	303	5	t
305	303	31	t
334	333	1	t
335	333	4	t
336	333	5	t
337	333	8	t
338	333	10	t
339	333	12	t
340	333	16	t
341	333	17	t
342	333	18	t
343	333	20	t
344	333	22	t
345	333	25	t
346	333	26	t
347	333	29	t
348	333	31	t
349	333	32	t
350	333	34	t
357	356	12	t
358	356	33	t
362	361	5	t
363	361	27	t
366	365	10	t
375	374	7	t
376	374	31	t
\.


--
-- Name: patientdistressdetail_id_seq; Type: SEQUENCE SET; Schema: nav; Owner: postgres
--

SELECT pg_catalog.setval('patientdistressdetail_id_seq', 1, false);


--
-- Data for Name: patientmedication; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY patientmedication (id, patientid, medicineid, carememberid, frequency, specialinstruction, method, adddate, startdate, enddate, active) FROM stdin;
1	372	1	240	Daily, 1 per day	Dont eat anyhting before and after 1 hour.	oral	2014-06-16 15:12:44.655391+05:30	2014-06-16 15:12:44.655391+05:30	2014-06-16 15:12:44.655391+05:30	t
2	372	2	240	Daily, 1 per day	Dont eat anyhting before and after 1 hour.	oral	2014-06-16 15:12:50.177299+05:30	2014-06-16 15:12:50.177299+05:30	2014-06-16 15:12:50.177299+05:30	t
\.


--
-- Name: patientmedication_id_seq; Type: SEQUENCE SET; Schema: nav; Owner: postgres
--

SELECT pg_catalog.setval('patientmedication_id_seq', 2, true);


--
-- Data for Name: securityquestionmaster; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY securityquestionmaster (id, question) FROM stdin;
1	What is your pet name?
2	What is the name of your favorite childhood friend? 
3	What was the name of your elementary / primary school?
4	In what city were you born?
5	What was the make and model of your first car?
\.


--
-- Name: securityquestionmaster_id_seq; Type: SEQUENCE SET; Schema: nav; Owner: postgres
--

SELECT pg_catalog.setval('securityquestionmaster_id_seq', 5, true);


--
-- Data for Name: user; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY "user" (name, password, usertype, isactive, id, email, isverified, usertypeid) FROM stdin;
users8                                            	e10adc3949ba59abbe56e057f20f883e                                           	p	t	75	users8@user.com                                                                                     	f	1
users9                                            	e10adc3949ba59abbe56e057f20f883e                                           	p	t	77	users9@user.com                                                                                     	f	1
userq10                                           	25d55ad283aa400af464c76d713c07ad                                           	p	t	136	userq10@user.com                                                                                    	f	3
userm22_3                                         	25d55ad283aa400af464c76d713c07ad                                           	c	t	157	userm22_3@example.com                                                                               	t	5
user29may_1                                       	25f9e794323b453885f5181f1b624d0b                                           	p	t	214	user29may_1@example.com                                                                             	t	1
user29may_3                                       	25f9e794323b453885f5181f1b624d0b                                           	p	t	220	user29may_3@example.com                                                                             	t	2
user29may_5                                       	25f9e794323b453885f5181f1b624d0b                                           	p	t	225	user29may_5@example.com                                                                             	t	1
user29may_7                                       	25f9e794323b453885f5181f1b624d0b                                           	p	t	231	user29may_7@example.com                                                                             	t	1
user29may_8                                       	25f9e794323b453885f5181f1b624d0b                                           	p	f	235	user29may_8@example.com                                                                             	f	1
user29may_9                                       	25d55ad283aa400af464c76d713c07ad                                           	p	t	237	user29may_9@example.com                                                                             	t	1
admin                                             	e4e759f32428bfef7c6eb47d7a769b5b                                           	a	t	1	admin@talkabouthealth.com                                                                           	t	0
maenhussein                                       	25f9e794323b453885f5181f1b624d0b                                           	c	t	240	maenhussein@example.com                                                                             	t	5
user2                                             	e10adc3949ba59abbe56e057f20f883e                                           	p	t	6	user2@user.com                                                                                      	t	1
userm22_1                                         	25d55ad283aa400af464c76d713c07ad                                           	p	t	150	userm22_1@example.com                                                                               	t	1
user26may_1                                       	25f9e794323b453885f5181f1b624d0b                                           	p	t	163	user26may_1@example.com                                                                             	t	3
testuser                                          	5d9c68c6c50ed3d02a2fcf54f63993b6                                           	p	t	205	johnson.emma1338@gmail.com                                                                          	t	1
test_user_2                                       	58dd024d49e1d1b83a5d307f09f32734                                           	p	t	210	matthewandre13@yahoo.in                                                                             	f	1
user29may_2                                       	25f9e794323b453885f5181f1b624d0b                                           	p	f	218	user29may_2@example.com                                                                             	f	1
user29may_4                                       	25f9e794323b453885f5181f1b624d0b                                           	c	t	222	user29may_4@example.com                                                                             	t	4
user29may_6                                       	25f9e794323b453885f5181f1b624d0b                                           	c	t	227	user29may_6@example.com                                                                             	t	5
user3                                             	e10adc3949ba59abbe56e057f20f883e                                           	c	t	7	user3@user.com                                                                                      	t	2
users5                                            	e10adc3949ba59abbe56e057f20f883e                                           	p	f	33	users5@user.com                                                                                     	f	1
users6                                            	e10adc3949ba59abbe56e057f20f883e                                           	p	f	34	users6@user.com                                                                                     	f	1
users10                                           	e10adc3949ba59abbe56e057f20f883e                                           	p	f	79	users10@user.com                                                                                    	f	1
user4                                             	e10adc3949ba59abbe56e057f20f883e                                           	p	f	32	user4@user.com                                                                                      	f	2
users7                                            	e10adc3949ba59abbe56e057f20f883e                                           	p	f	35	users7@user.com                                                                                     	f	2
users12                                           	e10adc3949ba59abbe56e057f20f883e                                           	p	f	84	users12@user.com                                                                                    	f	3
users13                                           	25d55ad283aa400af464c76d713c07ad                                           	c	f	86	users13@user.com                                                                                    	f	4
userq1                                            	25d55ad283aa400af464c76d713c07ad                                           	c	f	92	userq1@user.com                                                                                     	f	5
userq2                                            	25d55ad283aa400af464c76d713c07ad                                           	c	f	94	userq2@user.com                                                                                     	f	5
userq3                                            	25d55ad283aa400af464c76d713c07ad                                           	c	t	96	userq3@user.com                                                                                     	f	5
userq4                                            	25d55ad283aa400af464c76d713c07ad                                           	p	f	99	userq4@user.com                                                                                     	f	1
userq5                                            	25d55ad283aa400af464c76d713c07ad                                           	p	t	101	userq5@user.com                                                                                     	t	1
userq6                                            	25d55ad283aa400af464c76d713c07ad                                           	p	f	117	userq61@test.com                                                                                    	f	1
userq8                                            	25d55ad283aa400af464c76d713c07ad                                           	p	f	127	userq8@user.com                                                                                     	f	1
userq9                                            	25d55ad283aa400af464c76d713c07ad                                           	c	t	129	userq9@user.com                                                                                     	f	5
userm22_2                                         	25d55ad283aa400af464c76d713c07ad                                           	p	t	153	userm22_2@example.com                                                                               	t	1
user9june_1                                       	25f9e794323b453885f5181f1b624d0b                                           	p	f	315	user9june_1@example.com                                                                             	f	1
user10june_1                                      	25f9e794323b453885f5181f1b624d0b                                           	p	t	329	user10june_1@example.com                                                                            	t	1
user16june_1                                      	25f9e794323b453885f5181f1b624d0b                                           	p	t	372	user16june_1@example.com                                                                            	t	1
user30may_1                                       	25f9e794323b453885f5181f1b624d0b                                           	p	t	250	user30may_1@example.com                                                                             	t	1
user5may2014_1                                    	25f9e794323b453885f5181f1b624d0b                                           	p	f	288	user5may2014_1@example.com                                                                          	f	1
user5june_1                                       	25f9e794323b453885f5181f1b624d0b                                           	p	f	290	user5june_1@example.com                                                                             	f	1
user5june_2                                       	25f9e794323b453885f5181f1b624d0b                                           	c	t	292	user5june_2@example.com                                                                             	t	4
user5june_3                                       	25f9e794323b453885f5181f1b624d0b                                           	p	f	294	user5june_3@example.com                                                                             	f	1
user5june_4                                       	25f9e794323b453885f5181f1b624d0b                                           	p	t	296	user5june_4@example.com                                                                             	t	1
user11june_1                                      	25f9e794323b453885f5181f1b624d0b                                           	p	t	359	user11june_1@example.com                                                                            	t	1
\.


--
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: nav; Owner: postgres
--

SELECT pg_catalog.setval('user_id_seq', 1, true);


--
-- Data for Name: useraddress; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY useraddress (id, line1, line2, city, state, country, zip, lat, lang, phone) FROM stdin;
1	1400 U.S. Highway 441	Suite 540	The Villages	FL	USA	32159	28.8918609999999987	-81.9068829999999934	352.751.8000
0							0	0	\N
399	sdfdfdf						0	0	\N
400	sdfdfd						0	0	\N
401	sdfdfd						0	0	\N
403	sdfdfd	dfgdfg					0	0	\N
405	sdfdfd	dfgdfg					0	0	\N
407	sdfdf						0	0	\N
409	sdfdf	sfdsf	sdf	sdf	sdf	343434	0	0	\N
411	dfgf						0	0	\N
\.


--
-- Name: useraddress_id_seq; Type: SEQUENCE SET; Schema: nav; Owner: postgres
--

SELECT pg_catalog.setval('useraddress_id_seq', 1, true);


--
-- Data for Name: usercertificate; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY usercertificate (id, userid, name, description) FROM stdin;
1	157	 Test data	 Test data Test data Test data Test data Test data Test data Test data Test data
2	157	 Test data	 Test data Test data Test data Test data Test data Test data Test data Test data Test data Test data Test data Test data
3	240	American Board of Internal Medicine\n	\N
4	240	American Board of Internal Medicine – Oncology\n	\N
5	240	American Board of Internal Medicine – Hematology	\N
\.


--
-- Name: usercertificate_id_seq; Type: SEQUENCE SET; Schema: nav; Owner: postgres
--

SELECT pg_catalog.setval('usercertificate_id_seq', 5, true);


--
-- Data for Name: userdetails; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY userdetails (id, firstname, lastname, dob, contactmethod, email, mobile, homephone, seq1, sea1, seq2, sea2, tosflag, tocflag, createdate, editdate, verificationcode, editperson) FROM stdin;
32	user4@user.com                                                                                      	user4                                                                                               	2014-01-12	\N	user4@user.com                                                                                      	32456798         	3245654788       	\N	user4	\N	user4	t	t	2014-05-14	2014-05-14	159936cf-0dbe-4cf4-8401-7b41d3a89831	\N
33	users5@user.com                                                                                     	users5                                                                                              	2014-01-07	1	users5@user.com                                                                                     	123456879        	123456879        	1	users5	1	users5	t	t	2014-05-14	2014-05-14	e8713b40-723b-463d-9946-fd3b61fdcbb1	33
34	users6@user.com                                                                                     	users6                                                                                              	2014-01-09	1	users6@user.com                                                                                     	1234654798       	132456789        	1	users6	1	users6	t	t	2014-05-14	2014-05-14	cb80584e-59f5-4446-a96f-6b665154c8c2	34
35	users7@user.com                                                                                     	users7                                                                                              	2014-01-13	1	users7@user.com                                                                                     	123456789        	12345            	2	users7	4	users7	t	t	2014-05-14	2014-05-14	779077ca-ef04-4869-a0d3-9e89d4dd48dc	35
75	users8@user.com                                                                                     	users8                                                                                              	1981-01-06	1	users8@user.com                                                                                     	1234567890       	1234567890       	1	users8	3	users8	t	t	2014-05-19	2014-05-19	91f6dfc4-790b-494b-b03b-54977830862a	75
77	users9@user.com                                                                                     	users9                                                                                              	2014-01-12	1	users9@user.com                                                                                     	1324564897       	123456789        	1	users9	3	users9	t	t	2014-05-19	2014-05-19	1d13e718-90a0-42f9-b02d-b087e9e62966	77
96	\N	\N	2014-01-01	1	userq3@user.com                                                                                     	\N	\N	1	\N	1	\N	t	t	2014-05-20	2014-05-20	\N	96
84	\N	\N	\N	1	users12@user.com                                                                                    	\N	\N	1	\N	1	\N	f	f	2014-05-20	2014-05-20	\N	84
86	\N	\N	\N	1	users13@user.com                                                                                    	\N	\N	1	\N	1	\N	t	t	2014-05-20	2014-05-20	\N	86
99	userq4                                                                                              	userq4                                                                                              	2014-01-05	1	userq4@user.com                                                                                     	\N	\N	1	\N	1	\N	t	t	2014-05-20	2014-05-20	89cc0d4d-9213-4f9d-92a7-e62ce15e4bd6	99
79	users10@user.com                                                                                    	users9                                                                                              	2014-01-06	1	users10@user.com                                                                                    	123456           	1324564          	2	users10@user.com	5	users10@user.com	t	t	2014-05-19	2014-05-19	\N	79
92	userq1                                                                                              	userq1                                                                                              	\N	1	userq1@user.com                                                                                     	\N	\N	1	\N	1	\N	t	t	2014-05-20	2014-05-20	\N	92
94	userq2                                                                                              	userq2                                                                                              	2014-01-06	2	userq2@user.com                                                                                     	\N	\N	1	\N	1	\N	t	t	2014-05-20	2014-05-20	\N	94
101	userq5                                                                                              	userq5                                                                                              	2014-01-12	1	userq5@user.com                                                                                     	\N	\N	1	\N	1	\N	t	t	2014-05-20	2014-05-20	\N	101
117	\N	\N	2014-01-09	1	userq61@test.com                                                                                    	\N	\N	1	\N	1	\N	t	t	2014-05-21	2014-05-21	105bdc32-4e55-4237-85e9-0ab00aaadd00	117
127	\N	\N	\N	1	userq8@user.com                                                                                     	\N	\N	1	\N	1	\N	t	t	2014-05-22	2014-05-22	db79fbc2-0644-433b-a939-0a1a846d2568	127
129	\N	\N	\N	1	userq9@user.com                                                                                     	\N	\N	1	\N	1	\N	t	t	2014-05-22	2014-05-22	4776dddf-1d51-4024-bdad-4005972ef144	129
136	\N	\N	2014-01-13	1	userq10@user.com                                                                                    	\N	\N	1	\N	1	\N	t	t	2014-05-22	2014-05-22	957638b1-9a15-41a4-b1c8-4bba06def742	136
163	user26may_1                                                                                         	user26may_1                                                                                         	2014-01-14	1	user26may_1@example.com                                                                             	1234567890       	1234567890       	1	sdf	3	sdf	t	t	2014-05-26	2014-05-26	\N	163
157	Judith                                                                                              	Mayfair                                                                                             	\N	1	userm22_3@example.com                                                                               	123456789        	13256478         	1	\N	1	\N	t	t	2014-05-22	2014-05-22	71fc4899-bd40-4429-ab99-54ede294906d	157
150	\N	\N	\N	1	userm22_1@example.com                                                                               	\N	\N	1	\N	1	\N	t	t	2014-05-22	2014-05-22	\N	150
153	\N	\N	\N	1	userm22_2@example.com                                                                               	\N	\N	1	\N	1	\N	t	t	2014-05-22	2014-05-22	\N	153
205	Test                                                                                                	User                                                                                                	1987-01-15	1	johnson.emma1338@gmail.com                                                                          	8149880314       	8149880314       	1	test	1	test	t	t	2014-05-29	2014-05-29	af62e00b-5cf1-4e80-9373-40325762c1f5	205
210	Test                                                                                                	User2                                                                                               	1986-01-11	1	matthewandre13@yahoo.in                                                                             	8149880314       	8014880314       	1	matt	1	matt	t	t	2014-05-29	2014-05-29	\N	210
214	first                                                                                               	last                                                                                                	1966-01-06	1	user29may_1@example.com                                                                             	1234567890       	123564870        	1	12	3	23	t	t	2014-05-29	2014-05-29	\N	214
218	user29ma                                                                                            	user29may_2                                                                                         	2014-01-13	1	user29may_2@example.com                                                                             	123              	\N	1	\N	1	\N	t	t	2014-05-29	2014-05-29	b2de0905-5b8b-4089-8b94-0b7b9e3b0b03	218
220	user29may_3                                                                                         	user29may_3                                                                                         	2014-01-08	1	user29may_3@example.com                                                                             	\N	\N	1	\N	1	\N	t	t	2014-05-29	2014-05-29	b7238fa8-65d7-41ce-8ecc-23ff8d820b62	220
222	\N	\N	\N	1	user29may_4@example.com                                                                             	\N	\N	1	\N	1	\N	t	t	2014-05-29	2014-05-29	95f8f4fb-99a9-4507-a5fa-d0d003e00741	222
225	user29may_5                                                                                         	user29may_5                                                                                         	2014-01-11	1	user29may_5@example.com                                                                             	\N	\N	1	df	4	df	t	t	2014-05-29	2014-05-29	26bf2d07-5cf5-4fe0-b1e2-1a7d62ed95c3	225
227	\N	\N	\N	1	user29may_6@example.com                                                                             	\N	ddsd             	1	sd	3	sd	t	t	2014-05-29	2014-05-29	4fec0ea1-bad8-4143-b87b-634a9d0a3690	227
231	sdfdf                                                                                               	ffg                                                                                                 	2014-01-05	1	user29may_7@example.com                                                                             	\N	\N	1	\N	1	\N	t	t	2014-05-29	2014-05-29	6921fa24-431d-48ac-8bb9-9712a6fea8c4	231
235	dfdf                                                                                                	dvf                                                                                                 	\N	1	user29may_8@example.com                                                                             	\N	\N	1	dfdf	3	dfdf	t	t	2014-05-29	2014-05-29	38a1ee8a-e388-4d36-95db-22bb946114ce	235
237	sdf                                                                                                 	sdf                                                                                                 	1970-01-12	1	user29may_9@example.com                                                                             	8888888          	555555           	1	dfdf	2	dfdf	t	t	2014-05-29	2014-05-29	df156356-e5fd-4a70-82e7-11585034e9ac	237
240	Maen                                                                                                	Abdelkarim Hussein                                                                                  	\N	1	maenhussein@example.com                                                                             	(352) 753-9777   	(352) 753-9777   	2	ans1	4	la	t	t	2014-05-29	2014-05-29	bd2155a8-cced-4f76-95e9-4dbc4401ab17	240
250	gdf                                                                                                 	fdsf                                                                                                	2014-01-12	1	user30may_1@example.com                                                                             	213564987        	132548           	1	fv	5	ff	t	t	2014-05-30	2014-05-30	0e5c3368-f520-415a-ab57-b29a567b395f	250
288	user5may2014_1                                                                                      	user5may2014_1                                                                                      	1960-01-10	1	user5may2014_1@example.com                                                                          	\N	\N	2	sdf	4	df	t	t	2014-06-05	2014-06-05	e9c6af82-258e-4088-a1b5-2d0b0ba58722	288
290	user5jun                                                                                            	user5june                                                                                           	1990-01-13	1	user5june_1@example.com                                                                             	\N	\N	1	132	3	sd	t	t	2014-06-05	2014-06-05	e30c5b7e-e043-4cfd-bcbb-50323cc3b686	290
292	user5june_2                                                                                         	\N	1990-01-01	1	user5june_2@example.com                                                                             	\N	\N	1	dfdf	3	sdf	t	t	2014-06-05	2014-06-05	d86f2009-2865-402d-9998-27ebe0dc83f6	292
294	user5june                                                                                           	user5june                                                                                           	1980-01-01	1	user5june_3@example.com                                                                             	\N	\N	1	sdf	5	sdf	t	t	2014-06-05	2014-06-05	ee260a51-e487-4a02-94b5-e610ef1fc8ca	294
296	sdfdf                                                                                               	dfgfg                                                                                               	1990-01-01	1	user5june_4@example.com                                                                             	12345678         	\N	1	sdf	2	df	t	t	2014-06-05	2014-06-05	\N	296
315	testq                                                                                               	tet                                                                                                 	1973-01-08	2	user9june_1@example.com                                                                             	\N	\N	1	dfdf	3	dsfdf	t	t	2014-06-09	2014-06-09	145460c7-1c1e-4246-81be-2c15b69f8335	315
329	esdf                                                                                                	vbcvb                                                                                               	2014-01-19	1	user10june_1@example.com                                                                            	\N	\N	1	dsf	5	sadf	t	t	2014-06-10	2014-06-10	9a2ec10b-7ac9-4c07-b30a-7a20cc46acc6	329
372	test                                                                                                	test                                                                                                	2013-01-23	1	user16june_1@example.com                                                                            	546879213        	123456789        	4	dsfgf	3	sdfsf	t	t	2014-06-16	2014-07-02	9540574b-92a0-4eca-bade-27e456ee9ead	372
359	test                                                                                                	test                                                                                                	1970-01-05	1	user11june_1@example.com                                                                            	4562             	465              	1	sdf	3	sdf	t	t	2014-06-11	2014-07-02	7229e250-72b0-47e4-99ea-ba320a45131f	359
\.


--
-- Data for Name: usereducation; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY usereducation (id, userid, dgree, school, year, logopath) FROM stdin;
4	157	FELLOWSHIP	University of California	1997-Present	/public/images/uoc.png
3	157	RESIDENCY	Boston University	1992-1995	/public/images/uoc.png
2	157	INTERNSHIP	Tufts New Medical Center	1989-1992	/public/images/uoc.png
1	157	MEDICAL SCHOOL	Boston University	1989-1992	/public/images/uoc.png
9	240	University of Jordan, Amman, Jordan	MEDICAL SCHOOL	-	/public/images/140px-University_of_Jordan_Logo.svg.png
13	240	Saint Louis University, St. Louis, MO	FELLOWSHIP	-	/public/images/slu.png
12	240	Brown University - Memorial Hospital Of Rhode Island, Pawtucket-Rhode Island	INTERNSHIP	-	/public/images/brownunivercity.jpg
11	240	Islamic Hospital, Amman-Jordan \nAlbasheer General Hospital, Amman-Jordan Security Forces Hospital, Riyadh-KSA	INTERNSHIP	-	/public/images/blankseal.jpg
\.


--
-- Name: usereducation_id_seq; Type: SEQUENCE SET; Schema: nav; Owner: postgres
--

SELECT pg_catalog.setval('usereducation_id_seq', 13, true);


--
-- Data for Name: userexpertise; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY userexpertise (id, userid, name, description) FROM stdin;
1	157	Test dat	Test data Test dataTest dataTest dataTest dataTest dataTest data Test dataTest dataTest dataTest dataTest data Test data Test data Test data Test data Test data Test data Test data Test data Test data Test data
2	157	 Test data	 Test data Test data Test data Test data Test data Test data Test data Test data Test data Test data Test data Test data Test data Test data
3	240	Board Appointments	Florida Association of Clinical Oncology (FLASCO), Board Member, Tavares
\.


--
-- Name: userexpertise_id_seq; Type: SEQUENCE SET; Schema: nav; Owner: postgres
--

SELECT pg_catalog.setval('userexpertise_id_seq', 3, true);


--
-- Data for Name: userimage; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY userimage (id, image, imagetype, imagename) FROM stdin;
205	\\x89504e470d0a1a0a0000000d494844520000006400000064080600000070e295540000408a4944415478daed7d05545cd9b6edf57783c6dddddddddd3bde7177574242800851e2091023421ca290a0c1dddddd0aad020a9234997fad7d0e496efff7febfeffdee9bf4fd5d63ac718a024af6dc53d63e527ff8c3efb7df6fbfdf7ebffd7efb356e6565657d944a651b007f494e4efe3b6dff4ca5f1f1e3c7b1e5e5e5c34b4a4a8617151555ff7da4fe05371aecba1f3e7c18f0f163c5988a8ab289a5a5a5e3dfbf7fdf89806846bf9ba72c4cbc545696f3b2bcbc781481548d4abbb03071b04a95b2b2b45431a1ea798a8b8b6b3088797979dab4d564707f1fddffe24683a3450ca849dbff50abd54d89118de8fe1ff977f473631af811cac2e86385591e6f8a73826dcaca8a0c9465cabe25e5d986d95116717949d6ef550531a604d2527545c1aea2dc77d68a94a731050a9f9b3cf815151593552ad51045bafd565551dcaea2a2cc1e0c34fd8e41fa0f7abd86f473a7df81006a161414e8d060d4e1592fcdfc1292a0c443295e7b16141704cdfbf0a1b8b75aaddc999f661b961562f2313bfcbc5a5910e8a35667ce2b2ef04948729a5399e234bfb230e97164594966a4aa30a22837f632d203777cca8eb70856bfcf995f5151ec999fe56296156d56509ce35158a64a582bbd56f1e85265f2325561b49e5a5d7a4266d19ff8bdc913844b8beaafff5f00f2e183ba7f717ecac8ecd4904e6ab56a20b161815a9dfb4371811f12dfad8122f9118af3fc6e9696243fcb8e32cd4e7c3b16c9f66391137630bd28f3b9656ecc6924d8f641f2eb3ec809dc8ea28c97c88d3143aafb32a4bacd4356f081b29262bf84d2c2c0cadce8139f327dd742117f1daafc109bf2b2cc15258501570bb35d2bf2d21d0acad42a1702a92bb3868069456c1b4a52d9931eebcc0cfa7766c59f640dff1bb16164728cebd5b8906777b3535c37962a339695a8725ee467be42844d6f44bd1c88cca853c8cf7c8d74ff9d887fd51b09afba22d17e3852fcb723c9791692df0ea51a8154e78948f55c8964f7c54872994e3517691eeb91157a1459fe7ac8f45882748f39480fd64761f6bb8faa02afb2bc840be5a9015b911e713cb3549d798adf4f5959f6c4e2fcf81b2545e95bcaca8a7b127b8711203dff5dc1f82bfb03cdbad93c1b4bd42573bcec8fbd7b7d633adcac179665253f4f5516c6a765c75e2430ba11009d9060d78f06792a323c67238d063ace6e02829f0c87db9dfe70bed6138ee683e06a3902fe4f6620f4ed22c4796c4066d03e28c20c08c4fd887eb705fecfe6c2f3f134bcbb350a3e8fa623d2690b1208ac648f1f91eabd181911869525a5896565257999cac2b0ca84401328d29d2b8b0b93f6d3fbec20cbd79ffead82006b7169a972417262804d4989c299135345857a5fb497458cf3edf170306ff5c9e741efca18e71f2bb383d620db6322325d4723f1757f04597586cbe5167876a201ee1ad5c1f5bd357179472d5cd66b863b473be0e9b97eb0bb39066e4f6621f0cd0ac47aed4656c40914255ba034fb11d50351aa8cdbc88bbf8cc48043f07ebe14b6d746c1eeda5004daadfc949772af3233e24065b2dbb24fb10e739197f23ab3b444f18c02c10e92af61151f4bc7967f50adfe3702a4ac518932c7d9face3ae5cd9323caec1f6e4acf487553244758be0f7c3a01a10f3a20e9ed20243b0c43925d2f24b234bdec8218eb0e08b46a09d76b4d6177a1055e9eed8cb7d746c2ffd54a1a787d64841e8322f612f213cd50480014a7dea0ba89a214aad43b04c21da833a9b2ad5096f31865d90f51926945bfbf41ff638eaca8d30873da8017e6e361736100226c2720f2f55c28d29c3fa8d505f125cad4d8a4a827a9416e67e362a36c6fd3c4fa3bfb0af94c0b667c551afc4dc8137b05f50df5c8acd9b0f71417e79838d818545818b4c50393b670bf3b1a112f2620f6453fc4bfe88464dbae48b2ed8cb8676d106bdd86d8d183cc793a320237213bfc10b2238e232bf22c72e2aea220e5165434b0a5d9f7a1ce7d84f2cff59006ff3eca32efa234fd369469d75194701159d1a7911a7204498186e4192750907c95fef79e604e41f235a4869e44b4e75eb83d9c89c76707c1cb760f42de1996073a6cf9e069b304016f0f141628629e125b4650846e47554bf6c2162cc3e9e9e9d5be6b40781625c57aeb79395db5f17c7bca2933d93fb8b43ccd2b32f0ee4fd6a7bbe3ddd56608b94f03ffac13b1a133b1a103125e74a0c4d403399e33a108dd8582182314279ad22cbf89321ae872c55394e7dba03cef1995f597523c41790efd3ee73eca8909e534d0e599b7a14ebf8152024445835f94482c8a3e83dc0813629531a2dcf720e0cd7a84bddb4e2c3983d2ac7b04f01da4849d4684fb01b83c598c27e707c3e6525f38dc998860e7a33965a5ca3befdfabe7977fc8bfa12c0dbfab2acddd4d13ae0b4db639044adfef1a10d2deb669497e97fc5c2e563cb93ae3a7b7775796bb3fdbfa21cc7ecd27ff7bbd11ffb237d2ecfb22f54d2f2abaef340ad93e4b901fb61b2529e7485eaca0ae1aecaf073f8f01792a3d9efb5806e2e13f00519e69897202b13ccde2ab324769aa194a922fa128ce1469818710e3ba15e18e6be06fb702de2f5721c6eb008ad32d5142b2961c64021f7aec85f9683cbd3c0a6e2f769444065a86393fd9f2dafbad9132c0e3ecc7bceca8ab4a655e5b8ac7bd690276e4e4f8dd81403585f4b52767f7a2a2d4cd1101f7321e9e1f89db864df1fa4c73843ded8d54a711c8f2188f6cb789c8f1f8010aff95288cd44749aaf9cf00b0f9729f99916ff385112c4f24370204aeacbb547708885b284fbffe1904759a19d55551a55ca957509a72092a025d99740a8a884388745e0b8f273f50cd81cf8b6548f03744719a2579d04d44791c80e38345787173166ccca7e1a9e960589de803d7178791991a72903e6b6fb979fc0faeef068cc2c242dd8f1fd583b3d2225e46053fb1f47c6bb43d2ae0cea5e8c05ba9ee447fa7ab2da887e8872cf709c8f59d43202c445ef02614c71c25adbf413e40b35df11508f9cfa412803c9581f81a0cabafc0b823314380714306c38c8a81b882b2d4cb04845c29178925e7a14c3e8de2e4e304ca0928134d901f6340117915058621707d3009516e5b91177701a5e445199117e066b312f708885b06edf0e0f41024c5b8222f37d9345f91a297991eb42227336c7451767273171797bf7c0f06fe4732bb966c7431414f5c3c6cf52aedac16c2e9c16204d8ae44c2bbf994a00620cbf307e4f952a40c5881fc88fd284963463cfedf41f8ba04104fa51212c5acb8ff33569044a5dffc8a19040631a19c00505395a55c26193c4f605c2030ce519d4109b143c560241d4371c21132fec3b4a58a3344d89b5970bd3b08110e4b901b6582920c892dc14edb6071b437ee9c1a8998a08788f0b50a0e76bf1517e875af3431d623b4a4306fdd375d756620a4e570b150574d5a1fcaf58cf4b9f6ded17276a593456f04587545b2fd20647bcf42aedf3ce4913c29138e9347dc97663b0fb400c4e62b46587fc50c1908214f56ff0804b38219218030ff0c06b3828150131bd40400cb53195529dd6766a8924e1220272530120fffac48aee2f49113ba1161b69310f9663a72c2f6536abb8d92acfb2467476166d403177637c3e57d2d706e4f6bdc383dab32d0cb3a853a4b431a874655eb61df02108d8f1fcb87a8d525d7d5cae4feaaa2b41965a5455e59491e7941af5795f9dcef8a38fb81c8f62366f82f4086ef6a920f1ab05cd927f29fcb55c50819982a69fa9c9e1e4aacc892c1c864563018d7a422699200a1e7668f48bd448c20b921692a4d324551d269ba4f692d994120994a3c467584ee1f4171128190642401117f88ca904cff208a62f7a3207a17523de722c19164367833f5339694c61e2033f222cee935c7d96d757141bf0bc2031f94bf7f9fbfb7a242d9863da5a0a0a0c937f11332ee062525050714b9e1ef63fc4d4bc35c0cca029cf67ef47db1aad2fbd1884fa1cffb21858c3bcb771e7203d6d04099a38c0659fdb5690b56d87c31eebcafc0e0a8cb1295f3954409aff87982bafab9cad823843c9d178c28264614c9559ccc12755402235102a388c1482230120f110807a48aa38ad14771d45e1484ed44a6d742a4b88c474ee07ad1f370ef12e36d84737b3bc1f6ee66c485bfccc9c9f0b7cb48f6bc929112f03a4f911c4c51f8877f39209999991abc4a5b5aaab04c897bf9c1cf7e3d5c9fcc80d3cdbef0b8dd0321cf8623ce610632bcd688b8a926f951ff6709ea1f8cfb89ec1156ff89719369677c2d5157bfaacbc2334aab00118c3825e449956422b6453223188ca244232a62448201fdcccc2010a2f55114b5ffaba25e287227f2c2b620d37b3e929dc7d1fded50e73c102b005ecfd7c1ccb01bceeb77fc78766ffbd2a35bda1659982e2c0d0f76482340767f8b4e5c8797ab8929338a0a126ec5049d573add9e84b757bac3ed666ff83f1a8168871f911b76447c88f2ff120cebaf4cfbe117d3167d459569737aba263382b62c7d244d5c655c8215925fa8088c2ad32e163ec1c62dfb852c514509c48878036204c9530c33431f85517aa20a8819f90c46c40ee4876f138028825723cb7b0631652c79cc61a8158f9097740dcf2ca6e0ccd6a6305c590be70f0d51b93b9a25e6e5a6bda23199fa2f05a4b434a39baa28d6342fd5e17e5afc63fbd8f03b61fe8e7b2b5e5c188097e73bc1f15a4f783f188d78b78d505142f9e20b4fbfb0a1cab43f1bf7fd7f640517839176fd1f1a3cc108f20a75ca65919c589e18000142b204440979858ac0286200128f4a6024185391443110713210cc8ae8fd288cae02622ff22288153218f9e15b9047a508db0c45d04a64b94d4386eb3494a49b5130798038bfa3b87ea41fae9f18036fe74b198adc44274a9c77a81fdb4435963ca5e5afba4a2c3740d52b2a0aa7a88ae3fcb2126d101b7412a1ce7bf1e2ca48dc3fda06d667dac2f64a0ff83e9d8eac1043699079b9a3ca1baa18c220704ff1352b3efbc48daf9a3c73b9afe0384bbd450a33e21279d245c10a8eb19234b1479888f424bca2cab4138d25d34e9024aa987d2266bf00a28880288cde87fcc83dc88bda2d55c4760261bb00228f8108db0a452855c87a4a8a8b91623f8cee6fa5e4750bca4c2b383e5a82dba6636175797ef143f3d5f1f7aeac8db87f6dbbb78bc3bd574545f9d3783fffaf0a08efea2c2f2f19a552663e50a439c524845c84cff3a5b865d40296068df1f0586bbcbedc0fc1b64b9117632a1933032200780c250db28a06bb8c81caf9599c15cb1eb76410ccff9111a2a76030a89f48394bf244d244a514e9e9a400a398802896c1500a793226af209f6020e2b90e12205f5851184dc61d4560444aac1025646aab0446e826e472856ca45a879ce0e5c8709f8674327955d21932f98748093d89fbe746e2e0aa1ad8bfa2268e6cea84bb57d6fee4effd2a5355a818cc695496f83ffd1a80f0911d3a1f3e7ce84e867ea2a424d52b33e925eceeccc355fd2630d76b885b862d616b360c114e9b919f6026d8a0265694655989d5d7b4007a3cfe3875c1b72430aa58c16030237ec60a8eb28211028873028c12ee27e42a4e3611ddb6244d473fb34218b7f00a293915737aa2385b18ab274954244954e42eaa1d322bb6cab5599228ea4514a11b08086246f03ae406ad8622703972021623cd7e380ac9e0cbe8bd96d0e7727ebc1427b635c5a91d5df0c47c736964b0adfffbf7a5a7b937930111872ffd1a80d42f57a50d551586ac2dccf1799297ed8ee4f06b7878761c354b4d717167035cd327402cc620ce4b0fca8cbb22daaaa92357269d4376b811621ca74211be0f65ec09554b1e9fd3133776e65273f7190ceab493b9cb3e2b18a19213946046b2d4e02913abe26c955f503f11cf601c94a22c7b458c2c4fec132c5111244fe1322304109b88155c1b91438cc8096620d69377ac2320562397c050f82d43b63f1579492e25af92a4f3220a87baeec50d9381b8717206827d6d7262235c2f85fb3f1eae5617efe3558c5fcd4718ed92a2e0ad8559f679e9d19711edbd1fbe2f97e2a6415b986ead87b35bebe086411bd85b4e4672e0616969847ca394063dc3e7476407ac47aa0f7da0a0ed50259efb2a397db5fe24cb139704c479991567a8aa963c8e1313e41260701d964d9bbc228e8ddb80d8700085d453144673a347f214b95b92a748d927c2a41425402036541503911bb456b022377015f5512b904b4064f92d4156c03228fc6723d37526bdce29a8a92fc98fbf0adbdbb361bca919cee80fc643b3359f3c1c2e97e5e584d92995697d7945e3d73c48a1515959965e7eae9f3adcd7e4278fc7b371d3b8338c57d7c289f5bab865dc1e2e0fe6202de4a458342c23d3e6fd11c1d6c391fa6e26c2a83fc924d92aa12efa8b4f980950d4727a525372e2e58e32b1f6642a4a95745a24271549924a366e214f09d45b241c115d7631a5275131528a2a2056e445b23c5145effccab0b75282da2cfbc416f209599aa8b2b8783732019113b8822611d732922a02c36f21316401b2fce721d37316315d8f12d775c112ef975b707c435318aca88583cb9b7c32d9dcfd4344e0edd282dcb85dd4abd5ff35c0d0a486a73fd56069af59c6520225d4e7ed1edc34ec8223ab6bc070b50ecc0eb481db637ae351e7a0267f284e388374efe5d48fec4546e05afaa01b69a08e51f376f94b7afa1c653939998af424b69ca044718a3a2ead3f09b396c1489258a1e404c586cd5e112b3142f8044b144953be88b155f2244559091036ec0d52c912951dccef91fa0e92a8acc06534799622d39fc0f05ffc19906c06c47b2e79cd769490dcaa29d6073aecc2857d1d71765f2fbcb5362c2d50245a2bf393fa9696962ee2558d5f6531b1a4a4a4436969d289fc1cb7849448b3cc48af03e58ef77f80b9414b1c5a5e936686b6f0120f02a4208e121119756ef84104db0c4096cf7262c700a479ce8132fe84d847f179c94374d9bc067551f88464dea78945dcdc9d90973d4cbe5af6a022d316129520af3f8935287d11692530b8afd82d25a7b0aa0425b32284bc8240e0ca09de408d1f4bd41a5139410444c072214dd9fe8b90e93307195eb30518597e3f22cb773e35893f20c37b0e79cd3aa87895806439c26d3fee9e1a864757e67f4a8c735295952bed79cfa9ec21bfbc64f18e7d5569c1aec2c2c890ec149b4fbcf219e2b0094e776650c26a0ec355d5717085364e9397b83dfe1185bcdb34fe14927c3711e5b7d207df8a4cbf3514330fd0ccbf20e4a99c40107d855810e4d558c927a43a291a3ca9af3826bca288a589244a24288ab14af209f68bc2387de4c7914f708a127d459557c809aa2ac61223a4282bb3820c9b3d2227806b2515cbd3520283ca9718e13507498ee31163378e583e9fdeff1c01502601c29545cda292e495e37cb4a701ee9c1c8033bbda7eba7c68f8c72b8727153ebbb1db372d39d85da5ca3f49e074f945a32fef84aaa8289b44266556a0080dcc4e7993131b7881fc6221aeee6f2be4eae0722d9cdc5413ee8fe7d3005ea001d147a2fb2c32c595c8f05f850cdf25c88f31142bb1e5a2af90963c4ac492c71902e494581a2f91fb8a2a304493c7515630425ef6f89957e4c7ec2356ec11cb1e121bb60b89ca17f244dd7638c558326f05b3827c2227984d9b7d82a32c1599768eff525159c48c2c9ff948779d8a44fbf14870988234f799c8f4e29a4535833c8400a1cfa5e4cf42fd48ac8f21ee9e1e08e3b5b561b0b2012e1c180b7fd7db5064274694962acf325b7e51b99257787ba8d5a50bcbca0a8f17e445a6c7855c85db9305b038d40ec66b7448b6b460bab9263c1fcf254d3f4783658c14f7f9887e3906b16fa722cde3077aec304a93cec93b8c4c454f5122efa7609f2891535491588d3d2c1a3ca558f630fcbc06551c7b504e4ffac4383db9b9db257945c436393d6d95973e36899e22374c366e4a4f39429a5613182b8561e7062e15fd450e75e2d97e8b68f29071fbce418af34424bc1983446248e29bb1c8f420403c188ca902906cbfd534912e0840e20810ebcb1361756e06debd3ef529c8dda62c272ddc41a52ad4a7719b426358f7173dd08df786299585930a72c30db3935e3d4e8db65285b8ee83a7f51cdc31e986a394b00c09908bdb6bc3ebf10c9ab52728761e462a197aacfd6c9a611c1b9751e23984928453d29287a8d35f963ea8c15325caf2243777ec15ca04c32f6b50b1d2f27861f497be423478e15fad3f8549254994c40aa9c15b2358911bb4424e50cb853ce510231808ae2c3ff20892a62c6242aaf324026234126cc7d2763cd2792dcb632ad23df8684a066415bd5f5e8db0428cd7413cbd380e0f2fce2666585666a547159695a9188c5ef211fd7fff2501a9ce072ea8d545db8a0aa2a232129e7f8af63df1c9d7763d3c6c7ec4b38b4361b2a1260e2dd3c495ddb5e1f97002cdc803343b772389a89ee5bf020561bb91e1c783b085a4e6881c63791dea94d8b7cd3d4551e297fd1545f14672736720ad417d6dda044221f944210311c5ddf67602826bebe7f4942703c10d9ed4e471a7bd423478b941cb24afa0f4941dc0acf851326caa4cdfd9c8f69d850cf7e948b21f430c198d64c7c948759a4c804ca489454c779b2a626f962f7daee863147d6f22d27d1f6e1deb0593f50d706677efcae7f7f6a89e5a9dba9b1817b0aea8286f181f92fa8b9a7a4e4e0ec5de922de515254ecae294c0b478fbc2308f33155eaf37c2e9ee649cd8509700a906b3bde42177875264e419bb17892eacc3a390e0381309a4c779811ba08c3d82924453c92f12a5a58fa2c4c382154ab94497cd1527ef34e2382b77db0511bb843415b03c9169e7476cfd07e3ce65890a5d4f296a1d35a16be9bdac155e912bfa092a92a8ec00066311b183d2932fc559c18cb9d4c0cea01445d2e43e05f1b64311fb7aa80026d1763801425ee23a456cb3487ed33d97232d400f8ad8b30875dc8c9b47bae3cc8ed69f4cf776af38b1a777e6a17d536263a33ddd8b8b15fb78e5f717ef43e435996a6565cac94aa5c22d373d2027cafb347c5f2cc3494a5706cb357069670d38dde8416c5883d2849334307b11e33807d10ee4256fc7229ff4bd30860dfad8e7650f5eee50ca071a089f48382855152bb8af105eb14f8ab2820ddba4fd15c2338815f22260ce57a69d43f2941db0526eee96ca25f7146cdcfe3f12100b848167538ccd2699caf062599a45fe315d784892d338c43b9064398d47324958a6fb7851d91e73e8732d4084cb3a24f9ed8597cd02dc3e350836b7d67f0cf37b9655909b6a493ddb8fac2ebc74f28b1f762a3f69b58ad2d231aae2ec1ba931af43dc9faf87e3fda908b35f0bd39d2da84bad8ee3ebb5f0fa7c33d2d959d46f18209b66731a513b2f7c0f0a49c2d2bd1721d5671d0daebe90a64266069b364b142f79c41a8ae64e19a72f2f934b3b8ec41279c42e797f0581411e218a1703090405afc8064b12951d2cf5135cb9bc0d94e4496aee16c93dc502c1882c9fd9146167535f319380e09a21d5bbf164e6c310ff6604491401e1319942094996eb04aac9f4f31cf85b4f83efb379f07bb900cf2d86e3dab181b07b72e8bdb23823475d561646e3a5cb5157f610ad5fa339fc5b414ef0b6d4d81725214e073eba3d5c063bcb89f07db904cfcd87e3c49686305c510d56c67511fe7c9868b80a423723d97e34d228cf273a4da4fb23e903cf4511e93ffb4461bcd197c5408eb264dac531fa9ff7597094651014e172946579923bed7c8ab279e11b6540368855d91cb1ecb1528ab2d469e706483e2101f11518bef30418593e244f04463a455a8eb6690446cabb69245323845c855a0f41d8d38124bba391fa6e327d8631621bfa6c1c1cae0d87b3e50438dc1805ab23ed716a432d8abd8d3e5d3e3aa93221ca933051e9b1a9f31ae0afb6ebb6b428b75b517ec292fcecd01379d9d1c84cf14252e42d32b53db86cd08900e1a4a503678b8e94d717d0e0ee471a25adc8375311fa6606c29ff417878d16c612633839c51b121b981506e26f8be4e2e5f10239ca4a3dc5d78b819bc582605ed806198cf504fe1a61dc39413218f2fa53b63f9bf6c27fe8b43399153ed44f78cf1260647812200444bafb34326baee948a3414fa20994e8344148563cf960b2cb04f242ba4f26ffea5c373c3ad90d2f2ef5c7abcbfd70cda01d8cd6b5c0895d0371f7ea5af87b3eab5416e72bb855f835577bf9a0b866e5e5252308fda94a65ce858c544fd83e588e70c74db86f3a9852461d1c5ea9412c6980e81743911fba56484d2c997a9c2b75b9becba0e01e208006316c2f49d42102e280bc078f0f2ed02340f67e599565464448fb2a783f452e312e2f649330edaab5a7dce055a2a7906af917aff8cc08366ef60a922892a774eab2d36456647a4a32954ec9891bc1f4775310f76a10629ef747fccbfe487e3318a98ea328028f429acb1844db8d81bb655fdcd8df10d70f34c3d3d33df0f0641fbcb25c8910df47c5e1410edee1416f4e7bbb3c1e4ee96ab842a1d0fac3af79a38eb33d9f0f989713bf3d36e4b1a7ebb31d78787e085e5a8c83dba379b876a80b0c576ac3748b0ede5c6c894c8a8a45d1db850ca4388f4786db7432c51f4883e75304dd40d1554fecafa8daaf5d10b1472c93e7cbe9293f82bd42da4f21f5141bc5b287580424466407af145e911d28499494a0a4068fd353a6cf024a4ef3683b4f7885008080109ec15bcf19d25e400224d16112d24892a29e0f44f4abe188793d1c0976c3914280243b8c449ced08043e1e0aeb932d61b6bb262cf6d5c7f3b3dd70d3b8032e1a0e84ed53a3a2ecf488b73446cbfe650738a854aada94a9fb17289256a6c4d83906bb5dfce0f3e6d04f2ecfb755467aecff647b733a4c773485f10a0d98efa9019fdb1d91e33d1d0a9ab591afc620e205d728c4d0074e77a701f25b49ac592dadc646ecfab22b35426284d4dc6df8b244ce3e416064112bb23e2f912f154070b7cd0b82625556f4140b080836ee39923cc98cc82410b2bca60b79e2462fc37d92e83192c827d25996280d26391353de8e439c006434e2df8e44e0c34170bbd6154f4c1ac1f2406d3c3ed98e18d215177635c5c19575706267af0a6bcb3d29a94901b7fea5479d48b2a55a5daaca395ba8887b96931e16981c6797ebf16afb07d7c70bf1e0f4204a5bb5716c5535dc31a88d8847dd904f793fe1dd2284dbce46f8ebc9c2f493df914478ce26939c8e5c6a1e73833751df202dfc292228c2121839e413a2a8a7903aedd5529c2520b2a8d82fb2b9c1e312ddb6244fd9c4880c1fee2b660bf3669fe0aa5afec8e2e50f0f0988349771244b43c9bc7b51aa1a25fa0f06815991f8762801340c11cf06c1ef4e0fb89ab7c46bd3867865da1c0e1683e06cb5086fee6d2cb7beb621f7c195f5092f1e1885262705def9571c8bc597b4105742e0f3210814a38a0af5293e05a1ac24ff6290fbb504cbe343de3fbb3c020e9653709b9aa423ab747062ad16ac8f37213d1e424de11aa4782d472a45466e12539c27907c914c388f21a9184733783e01b404397ecbc51a9388afdc5384122b42d688250fd1697ff609b93ec7d91fa504c5acf06210b8aff8412ae115d3843c650a56708347ddb7eb24a4388c41d29b01f07fd013912f4720fa653ff28e81f4f85092a92108a1201270af1b42ad3ac2ff4e4b78de6801afbb7d606dda07f68fb62039ee9d525d5614ac56975de74380686c46ff2b00e14380f8b8d5fa7c25047ae189f4c2abd9dce3a39d9edc319d5e72d3a80f2c4dfac38558f2eee1025cd5eb00a315ba38b6411bb6a66d10437295ed4503478393f88632feeb8124132369568e14a7b625d1c064b893bc50fc4c25e9c8f09e2f19357b45d04a796596d31381201602178b75280186afbcaf428041f2449d740615cb5306a5a60c8fe972a74d13c0753ca5282a17baef3299d83012294e244d743fcd6d0ef9dd44fa99de138111f0b02fbc2dbb20ec417bc43e6d8f282e327ce71b03717a6b4318ac6986175606e599e931be6a75c966de19f5cd2e36c057612851165ff270be9be7f4ea78aed3f323493e8e970b02dccf7e0c72d90da77b337061772b1c5aa123eaf929fa302f47929c2ca62e771e625d9650d3489daee732c43acd46ec9b8914022621e9757f24beee4bac1921ba67115f039709d6f08e23693170e167afc8f25920fbc46c69795c6ef0d833b8d94ba181cf709518914cdd77e25b66c41024dbd184783d1889b6036972f4151d783a81154f9325fae520f0d95edeb73a21ec613bc4d9503d6f4f7fdb1be1763371cb64e8c733dbbb97996eeff6e1ead1691f5f599fb02f2e56cce2cb817cb35312e2e3e3f95a217d48baced2761d4b595959e1856077b37c5bf34970bd3d1a9e4f67e2ccd6263848801ca0a6d1d2a019225e4e84c26f290a43d622c57d2162de4e13acc962637dd91bc1b7dac1ed6223445b77215086d2c092cc887dd90bc96b1692a4fd886c8eb1ec15bebc7f9b8a65c99bd9304dc85126312287c049e60b0c3891593b8f4086cb28326d6a5229c2c6da0ea6a6afbf081ab18eb3e1798f2680f368a450bf114a8da02b99b7efad8e882646243c6f8784171dc519c2bc6fc4da825e3b3d30b7a830c53e4f1117979919119f921266949f9fdf8f0019f02dcf11f93bbf810f1f3ef4e5a50192b0c5c9b1b6c1f6771755de336a019bd3ede07c7704bc5eadc0b97ded60b04a0346cb747176630378dc1e4eb39766b8ef72d1b9a7bc1d8188c71de070a131026d868b45bc2c574a37affa20cc7a30a2ec67109b1611200b089005923c79cda5ff9f4b80cc160064b84f20731e8ca457dda8bad2ecef291890643b1c41565d1170bb13a2ac7bd3cfbd91ee3884d83855ac4f45bc1c8638624ce29ba170bbde13ce665d107cbf03e29f7740e2ab0ee2ece054fb41144446e1d4a65a94a86ac0c1c6c8372af8f5183e70900f3c8f8888f81b2fc0b26a7cd3937618148ec26cfaea0ad59e00cfbb89aeb68710e87afc7d7cc4fdf2b4041b44791bc1c57a211e990ec191b5f57168b9364c36d6c42da3d6f0be3f8ad841b3de7ba1b8464982e34284be9e409df3141acc01f0b3ea85083b8aa8fee41fe413d9f477193ed46d7bcd11f29445c69deef123225f53d3f6a237521d0650a41e042fcb8e887cdc19c9af07d1cc9f42033f856637c995cb2c043d1d8d9047039046de91e44013e1250171b3171caf7443d0c35e88a3e71167071318a96ffb5253380d21af96e089f95c1cddd619facb9ae0c2a1b1ea10df57667c25211e0306834f69fb6627ecfc3c81f1968f4889097b332d36e2e586d424a7557979a1abd2e29cdcac2dd661f2a489d8b3692eee9d998a73bb5ac3881298f16a5d986eab8f7b47dbc2fb01f5259e6ccc64d01e2445de738925b310ef42064fe92b9723ad68f0a8d3e6f2921a3bee2bd23de720f8c52432e0dee405fd29104c46d88bb1f0b9d3934cb81349154996eb0c4452a888b11b8dd8b7a3104dd196d393dbf51e7032ef86e0a72328f991c153e24b771c448c184c808da1283c0c2f2ef4c6f35b8b9092e059e4eb7ae75a98dfcb84b818a782a2c2ec1852057d0a386dbe0b20fe933371dbf1c502983174bf55415efa6acf77b669f3674c47ad46d3d0b9cb8f9835650e0cb74ec515830138bdb3050eadd2c591d53ab8bcab091e1ceb88b7167d49d749aebce611000b8557648be5f1b9d2aaac0f45591fb9d316bdc57451718ed369b0f98a41c369468f23364c46d4abf188a2f496ea389c983016a136c3e07fbf3fbc6ef726c9ec07bf078311f26c04a26c89916ee439d43c66d37366bbcd40a2d30f70bb371af74cbae0dcde96b87576fa07a532299c4f35a0343553fd5e39bfe243c546fa793227cfeff2fc74f9a2607faf3a4d3a3038ccf4ccb9db3f356a390bd5eacc8356ad05a8dd680e060d59860573e6e1e0b609b8b4bf3f4cb7b684e18aea305aa985535b6be3a65173caf8edf1ea724ff83c1c8178876962253685062d85062bc56d9a64f49e52716f91e63a8dd214c99ce35832ec91d4c88d42b0f52878dea1d87aa71781d01f3ef70711134622ec195f4582170c278b7d1fb924850a8acbb9c4c45497a9f07f487fe3b81b21eee7e16e7b0c4faead81c5a9b91f7ddcef46d084eb261fe3fc1f7ca22befda0e0808f8ebf70ac85f65506a8445c64fbb68f6cc65d8b83d3f69d45e0a8d3a8ba91612308ba1d5680deab65c8111635763f5f245d8be72040cd677c2f12dcdc8636ac280d298c18abfe130359557f737c1d3339df1ea5277aa6eb0bb4a127383069966b7dfa3a1087c320c414f868b0a78340c7ef707c3ebce00b8df1c807737fac3d1bc376d7bc3f3f620f20ef20cf2831c923c85df6c62c40fc4084a676e7391eefe230135939833080f4f7482a7ad31b2d282138b0bd35e4706d9d93abcb81cede36113c8079acb0750ffe90fdffb8dbb78de19939696d5fbb2c52bd3d98b8fab74ea2e844ead15d0acbd8ac0580acd86aba1d564236dd741abe97ad46eb3125dfb2cc09c1fe662e3e231d8b1a407f456b486e1da7ae433ba305e495eb35c4becaf3758a1418069e3cc96dab8b2b729ae1f6c893b875b93d4b5c3e3e31df0f44407d89cea8497a65d6177b1071c2cfac0d5720002083436fd84b7e3452398fe6e1aa2eda6c1e5ce24585f1a07f7fbd32be3ddf69507d81efce4786f09ee9d1c0ac727bb9118e3e85256aea2e6b76c7a7979d901f20b3da552d99625ea57399afdd7d881c59deafd27ae1b662f39115cafe54a6812183a750910ae7a6ba0dd640b341aaea75a078d465c1b08a04dd069b69658b308ddfbcdc39c9933b06ed1606c9adf157b16b6c281258da98fa907c335b571941864427572632d9cdc5c1be776d6c355bd46b871b031ee1a3725605a1320e44597bbc1e95a6fb8deec2d66bdc79d11f4f350bc383f0c0f4f8e87e99e8958386d2c460c1c8b637ad33fc6853ccd2c5115f866a578babeb8b1fe9d83cd81d8f868fbd7a5e5ca657c4d13f6c7aa6b67f175b4bebb4b69fc5707d34544a76cdcae7f3dac63dfedd0a9bf0a3af55643bb01b1a28104865643aac61b0904de6e26966ca5da064dba5fadd94efa9b0dd06cb203355aaf4783764bd0b3ff6ccc983209eb178f82fefac138b6bd17ceecee84f3bbdb50b5c285bd6d48d65ac1e2602b581ab787d5f12e787ca62725a37eb0bd3884bc6828ee9a4cc4a99dd3b178fa0474ef340cf5eb8f43f5da33a15d6b0e9ab59883b54b177f880d738c51e567b6977755ff595d9233af5499358e9789d82bf8fa90f2296a7fe4c0f29bb8166361a16ad03e632b9b1ec3f6aab41a113b1a1208545a0dd7d2a06f9241a06aba850060566c864ef3ad04c05602623bfd7ebb785cbbdd5e6834db018d76fba0d5411f3a2db7a37ad355a8d574311a349b8be66da6a155fba9e8d67d2afaf49a884103c66330d5a0bee3d0b7e71874ed3206addb4e42bda65351b3c14cd4acfb2374b9c8cbb46baf8056ede5605fab567309ead45f8cb163d67d8c890a4b2e28c86cf29bf0867fd6d01d5cc3ef8d9b774ca9d36ccd272d92252df609da56e3c127166834e1c1df826a0408dfd76ebc8d00227634dd0e6d02408b98a1d166173dbe43b044b3f55e68b5df0fcd96fbe86f761290045a33fa3ffeff461ba14be056274faa4e21a106bd56757a2d5ddaead063ba3411741b12431bac82563dae15d0aebb0ada756892909f69d75e460c59061df2b5365dd71578fac61cb979d3e5ef7ff877b965e514eafdb0f04272c38e5b2bb51a13188d364bc5b2d484075e62801854b974e8679da63ba0dd7c17b49ad380b7a0e2fb3cf8ad088c16fba0dd561f5aadf5a0d96c378121fd9d663302b7e146028e18c6f227247003341a73d16b8bd797274463626783b54232b5ea71d1fdba24a17508a4da2ba1438c69d06e7de971d3a72f923314ddabbc8127d8777131997f920d7c784b0bf9873fbe740a69347399a96f93eedbdef3e06836da243c8167bfb6cc000d1a782d2e628236cd7e1df9be56330661b7542d76133854ada85aee216610286d0f92841d140069d2ef345bec115bf61ecd269b255fa2d7d26e2c6dc5eb8ac7e83d34e2f7b2410287ab4155ad8146fdd554c494fa4b51abe5eaf7f3969984595b7bf2755bfea856ab9bc89ff1b771093fbe64b77cddc1467985caf9074e3fb36e357057896e4bd2ff4664d25c0c461369f0851f34974a930121066833330418322b68a01910c18caa6266b43900ad7606d06e4d4c6941a0b4225f21b0045348f6580ab51acbf2d7e4ab6a5c555b2506356660368854a749e94e93e44d44ef062b50a3d9eaca81e3f715a4a6e6ac16a75d9089f3622133e437c11279ef61f5a8b88cfe366f835e8d9879ee638d0ebb3ecf7eadaa6a26952683d18258d282d8d24cf68be63b642078d6ef1683adc58c68a3475baa36fa34f8fb45314b74da1230ad08a0d6fb05730453e879749a33c0043ec9984ed573cb6c947c89a592278914283488415c82c50d08a4fa6bc98bd6a04dcf2d1f83c3931c8b8b4b47571d29229fc2f7a7ef1d8c5a7c8df6f0f0cc26366f03f5e7aebb9a5bbdcdde4a0d1e58d6789ef5321062f60b16302804487302a4c5ce2fbf1340d0c0321862f6cb032eea0b20dacc90f606c4187df1b8460b3d6890d16bb4a000206a87281d7a4e9daad7e408dd42024693abb1048e067999063188c38526f9909030624a9dd6eb2b2fddb08bf3f08d1cc24be8bf994b8cf3010e2ab57ac093b78153769bd8c4d4e94c33b0250f2e0f4c9504edf862d00492b6f0889d0408c90c3da6418f69b6dc2dcf7499199ca4aa8010609044b15c115358b234db1b43b3dd2162cb0102633f34f8f7fcffcdf7c812b647f6a15dd27b6949db96bb65267e618d06a7376614f73f8d36cb3e43e9acf986cae98b4d326c6cfda6f084e3e5f4efde43aaa4cac12776f096c3d657db8d3a4232421fbc156b7fd5c07c494c9a2d78e0f788d9aad97caf1830cd263bc5206a924768901f706989223058a6e4d22223d76456d056bbfd216877384c5b634a5d0452eb0392b7305b0420b284b590df038703064584839d1238cd24ffd210ecd92e648ea54c93658c129a56d3f59f7a8dd0ff18109c60c7673bf1ded0effe6ad6bc6898945538f4e855fb8bfd7f302dfb7b7b3d7930f5a4d9c81ed242620b3383bd41830785fa86bfd300ea92c1324378e03478006520b478b6d3400b396a4b6c687b48c8944e0702a4e321d4ed79acb269ff9395f57a1e81b6600d83612880d1225669b76676e909c963a669c91228de1b03267e965fb3b9cc9a2a592570446fd464231a75db0ebfe00415f9c8d8dfc4a5c59515156d1e3a869e5baeffb04cb733cd6e3260ed5672b134f107acf2121a781d9aaddafce1a9c1e3c758cb457c1560d000b6dc2f4abb8d2c4fd473e87422bfe87414ba1d8da1d3d108353a1b62d2220bf5c603d6ea61f3cde9f78604d421e8b437a23a28f94a1b0692aa0db36cbf2473ada4e7d66923078356324802b0dd8235daad64e6702c6f4e4d669b0d30bfe7fcde27307e112f957cd760f07ee39854c596dd275fc6d7ed6bf049a3fd7e31803cb36bd007abd66087a4e12c4ffc61dbec854e2bfed0f4382f8150f4d4a6ffd161a9e224d55e5f921dee3388099a6d687069a035ba100bba10205d8c519d00e937c9141677dd9343a2d293f79d7a2301c260753212a5d1c1101aed381aeb8be7e06259d361805a1f2460e9f704880ebfdf961283b8c71172c652db7c979834d528746836df8089cbce7cb8f9e4ddf6d2d2d2ef73a713eb292f37c7a6644f5c6df8ccb6edb8b31f343a1a884160f9d0e66d23fa30ec217224d56e2df5111a540c82908eb67ad02159d1658610089a2df585e48804c5d5c108da048046e7a3d011750ccd7a1b63d1a6db8fdcfce29686c5a64d9fbecef2bc6e37fabb4e87a1d9f18800468bfe47ab3dfbcc01e1375a6d0d4408d061dfa1d7d122d034590e3b1c142069cb715aa385ec5dc27328707022a368deacff9e9f4e9ad99e4e57285a7f6f7e215635395551ea58686ae96a3778cea5a21a5d7946f387a759c892d546d26e6d9ea16cb6f4e1b5dbee173356bbd321e87632162cd2e5c75a903cb1ccd04c15834383a645a543b39c01d0a6d2ea740435ba5258e87c041bf59fe69adff35c5aa82a1f925f543273c5ce3b264d06184293fe4e93644d93fe568b9e5f8381e960fcf9f9b4da1b8afbbab4ad468f6bd2f36bb537108f6972716a23dfd26cae27966744f868be472ce1d4eab8f5d3851b0e6fe39233fa7f8f80b4a14cdef9815de0a529eb2db31a0ea401eb4833ade321a1e3ba42b20e4a498866a0f8d0a4f93c2bb974ba1e864e97c334300744a7add1ce50b08aa58a9f8319a1c5f24312a5d3f538b47b9ca03a0edd1e47317b9315ccacbc4e2426660ee6f7c1d7573f7bdde1c4d825977fd2a6e7d562503a1fa73a0c0d0246b323bd16b14c4730cd58940e3d5e8d1ed7ed457fc720d1eb68d2e30ca42601c59226de3fa7339102f742b7dd36189abe50f80425187df8f0a1071ff8467ed2980ff7f91e0069e71799767ae1dec7912d469e52eb74e50f694cba4c1f9e6483b59f355c4bcc6e0289745fa733cf561a141e0006a6db11e8d2efb46846f36cd6e441e824cd5a1e1c0641a7d769e8f4a4ea638a1a7dcf7eea3ce1fca763579defbaf844f5272defce83c25f02e31b9e7cd5f0b24345f5de47a1ddfd14b4ba9e82665713c1162d7e3f34d0a2885d5a5d8e41b7e7497a8f475067d07901986ef7e38279a2e8678df646e2ff586aab7a21adb6bb307b8d39ec9cc3dc491d56561db8f1ab9fe7f17fbaf1e584f854ac0c45c99c23d7dd92bb4ebb5c59a3bb34db19085daeae34281d0d25b6909ef3aaac6ebbfd42c6aa935fd4ecb01fd5bb909ef730a1c123c07a9e9100e4594a92a441c0693223fa9d87cec08ba2aa0fba80e6a34c3f2cdc6e6515129db6b1a8a8a8390d4a0b1a94d67c698a9c82d2ed56b6a1b10d87d240f73947cf6d4a759a80311100687631f97c5fabdb71069718731475065f26108885f47a5a5de977c42a9e345aed3818d0e720b66b8bf82ca5b4f6238d6171df2d545150b4525e68acf92d99f117f68de262d51a8ba7810f7bcebdaaaad187282f66e06131f37548a274d947c8a4abb7da89ea2db6a17efb5de839d41063669ea91836ed6454bf49c7e33a8f3b0dddbe0444af530408cd4ef6863e27090802b01f3d36f822b4865e85ce7073e88e344393c9577f5aac773fd32f32b5130dc4221e0c3eaa83f7d471eae15da98f6d832cbbcf380fed8117a0dd9faad7790285d8d2e324b4bacbd5ed0434a9aaf73615e054ef778e260149223144a73b31aa8bcca88e5c4768e21c1589515b24b303a8dd531f06675f45f987262ce63d865cdf0c10cedf39f9c5235f7bc51d18b5e65e71ed01c72b35d8383909b5d947bdc50eb1bcaddb64fda7661db7540e187de843df61fb32864fd40bdb6f7cc7cbc93534283e39eb98c57d77f305db1e7eaa4183ae33c014dabd890d836956f736816eff33a83e9c80187d03ba636ea2c6f81b6838ed7ae5988d770b8262d2ad929272f98b609aca4be07fe7c8cdfb29a81a58bd0edc3d73dbbd4fda2368d60fbd0ced01547d2f4a8ce9754694762f7abd1eb425f6687667708e8b2d4bab6e4f9a08dd4c0438ba24959ac49ceabd4e48d15934a634d9ba1cc0b6a3366a7bcfc8d334399b7f53ef485628eabb85a6e8edb9e458597bf8d94f5a3d8e8908abd36453658d862bd5759bae503669bd3abf7d8f75a92b379e480a098b4b56a94af98d0fe7234ea836d1606e7ffc3ae0daca03d69535865e80ce908ba8450ca839ec2c74fb1d47cdd1e6a83dd1127544dd42a36937316cbd55e9e5279e7c99d521fc9d1c7cccb07c3af15fbf5e2970f6895f7ef4865b599d71e6a83ec61c5a232c0818da0eba4a8cb942e05c820e55f5fe9768129c43f501e7481e8f8b28addd8e249798a3c3fe424c620671135a93a44d83d3596bea83d8ec29744c5a7d13962f03ed4b2b2ac67fb3352d7ee1b7be31232d6d83d178f269d4ee7718f57aecaeacd3714d599721db95a3a71c703f7af2c1b588d8d48332007d49dff9ba82abf900075e25e51378f860b21b0f3db66d3ef6b2b2dee80b6830ea0aea4fb84ad2710275c75ba0c1544b62c46d2adede42bfd50f2a0daeb946b3891210fd78c99b8f7ee47d123f7b7f1aa9f9c5635efb247a369b6a4660de40f58937a13bee1ab44799437bb819b4875c81cee02ba84e52a8dd8740e947b24832a543fd870ec9914e07c9fcb548c284bcb1a4f53e45c183d320f52ca20cd161ec291cbdeae89f5f54ba8cf7ff7c13403c4293da99dc70bdd87d0e99eb3803acde6d8e97ce21c94f6cbda7c42766ac4f4ece9ae0e111a3ad542a5bd3800d90f717fc4d3e99e7af1c0698e23c98964f3c966c30b449af37e23c1a4cb2800e490c83527bd24dd49f7a070da7df450302a4e50fb7b0e0c08ba49834853e1f11686060f0a7ffc3a1aaada9367885a69f18bef61181c9cf7513f5a6dc44ad892c7dd7a133c60c5a23af408b245173c02568f6bb20525edf7147d1a8eb0e6a48b7a01a2f46b63716c6af29e4eb24748939ba9d0da50693d852bb9f11769e7a9517169f7dfc9b1d2aea15143fcc3520d6d6d623fac103bb80f54fec7c2666e7151c522894ad0b0ac4a5c5ab5519ff7f75751b59661abd760c59b3e5d88bb49ac3cfa2eed86ba839d60cb5265ca38123992250ea4dbe813a04d0d0b5f722cd9ffaefc92c2868f2cf1c88c769cb273c75cbdae3afd178c62dd49a7c07b5a9ea4ca19a6449cf4fac9119a331ec12c9e465fcb8f126eedb785fbe76c7f9d1c43927b31a77e6c38d78a7d65ed1946a7726507a9c25833f4a09d248ac1ae8f630c2bc1d56aa1b367ec7f875bf09200151290d6253d22716a9ca66d0075f4083db9b66fbd0ffce1795306378e9fae4d9177da889bb5363a009aa8fba8cea632d48f3c947465f478d51543458b3773d81e58b40fb940cf16dcf7ffe672495d9e8e49fd4f7ca535f349862418cbb853a932d896db705f3f87e2d025c772c0506f2afdabd0e7fb872cbf97e444cfa085fdfd86ed72c9dd7989c7dee336dfe19f2c57504cc6668b7d84dbe7244a42fed4ec6227969773b8a114b2d3fee3deb78293d3dfddbc45e1e48961b2ecefff217f4eafe4f0e9bbc74d3befbc4e5576feaf635a4487b119a2c219cacc88075a8df683fe57cb9f165fbb08494dc9dffccc5bd64305822b5d3b20ac63bfb25b8349b78ee63adf1ccba5bc492dbc496bbb4bd8bba536e5368b881fa632e60d2b28bea775e91b3333232f8abeefe9c9d5d52c7de3968d8a56b7687d76db308ee396877051f2e240ed06b419d3af5478231044ec70917b0f598ed3b92e849dfcad4fff44bedbabc6deddf79ca6a33339d9e07c868cfa31a35671adca051ffd078e8a9f7cbf6def771f28a3c454160d43ffb0528f2c1dc75f8bb11c31372170f5d78555d779c19aa8fbe4632451e42725853ae3a13afa1d3f473efefbff40e8b894969f1f5a46270f9608637f67ecb76edbd7e73dc1443afd69d3742b3fe4ab10c2fd6e3089806834e60f1de27f141d19986ec5ff21780e9fe268fd37aeb19d579eec66b663a5df528e950aca47e41b3ff39d4ea6f8285dbee16dcb3f13b205f76b6c93f3b09e4ef47e7c3385bb9fa2534d96264a36c32eeca279da194ac46122863b82ca04bfed178fc954f535699298a552afd9ffbdda3478ffecca990bf7858a12858f1ec9587deb8a906e643c7ed4faed96cd907ada69bc5da5cf53ec7306695a5c2fca9ef553ebd82ff871bd4dfcce1415fdfc263b27aafdfffe0ae4ee7bdd40c52274d8d9b76cfa3183ef7e27bab17814f13d2f346fc77bf21b30a105e6c7474f4ad6571df2baae5e8731fb40652b73e8422ef3033eafaafa0c6d0f3e83efd62c5b91b0e7e1c027e7e280f0f281f38cdcb43f4fbe9f47c53d3d2d21a5ade733c3273c191e06e83f414f5bbed7f5fbdb7317acf377fbfd3d4ee054779967262f488dfc491ef3fbfa564957458b3ebdec55a9df71010d49d532fd061e4319c32b3f771708f1acdec609ffa1fc8ea5f792923252faf81b3578c59aba1c78bb5fa51c73de812b4a8ffe0e59886234d7f9ab2c222a9485966c82cf8793ff3f3950906867b0d5e32ca2f2cd970fcd2f3a7f3d65ec9e935f50cbaccbc886da7df6470f3cbd779a1bf3dfd5d7d67e13fbf04a36ebc64cb35c3463d778be5f4a6dd8c2a0d8e3dcb75f588984a338d2f60338b53dcfff4b8278ede39aab2e57d261ecfae33e01875e9a6d01a7001da03cea0dbb4b3253b8dadeecbe164ed3f23895532c483cd178f51288a3ccedf71518c597bbd64dbe9b7ea7485d2eec307b1f23cff37f955deb9c5c5adf48e3d38d1658401ea7735fe3471f669554050e2bab2b2f2357c414d9280aef2d18f7ffc9f024283336fff89e759dc55ebf42259ec6d8a7a038f63ca0af3e8a8c4acadbc6c4e7f57fbfff61a7298a9fe55033a95dee37a5e02f20d4f7ef6c22da2d83b22295f5dae0cfefaef7e3337f99cf566db0ededd3168aa115a0fd853ece11d75c52522424b1ea4bfc931f68fff0fafc127ce74b47d1796dc9f64455b2cf3b3475d81b1e9eba7c9c945d5ff3bcfffb314c6873b35e06bdc171494744ccd2ee8c8bb74e9b1a6bfcaa5fafe156b62dcd5eb1958acdc6778fde3c397be11e9e9395d7ee943337970de3a87ad6e3fe850428dce8750afdb414c597829c0d9277ab57cf4e15ffe1f9efbcf72ccae2e3fd79f7fd3e78ed09b6f171818b1edf15387f371c9d94bd9887fe9b8c8a0a7a4e7cf1c30d138aa5e377d4c5c6806c393cfcd431372eafe52fd940cc45ffef05bbff152794a4a56075fdf103e49b2d62f7d4e1e0f1469fd78a55add6fdba1bbfedd461cc482b51621764e518ba85f68c60de71f7ebffd4b25917d6a04d5a8e7f6fe0fb6efbf956a7cf29179464651373ef1f4bbffd2f97f434034f84810beea675c5cfa4cbbb7be873dbd23567198a83a1b980f50f87db4bec14d5e0a6199ea247bd55fa46bd57fc34b25fd7efbfdf6fbedf7dbbfe7ed7f0109dab1406b3317800000000049454e44ae426082	image/png	gold ribbonnew.png
153	\\x89504e470d0a1a0a0000000d49484452000000330000003308060000003aa1302a0000001974455874536f6674776172650041646f626520496d616765526561647971c9653c000019ac4944415478daa45a09901ce575fefa9aee999e6b67777676575a492b69752074224018db20b011c68501998ab11d20c49810a818571927a29c5810db655c499c3895102ae5726c7042398e39e2f8c226981b212e2116d6ba5612da5d69ef9dfbe8e9cef7fe9e9576715c153ba39add99d9e9ee777cef7bdf7b2d044180dffdd954cf46a58897f6bcb0ece64fddb4331e8fef06f0109f2ff2795c8756e46f9fcfa2aeebc75b9f3f944aa576df72cb2d3bf7eeddbbac562d23f0bd05e7f67dffb7b6e7fff4a5779f387cdfc48bcf3eb5e4633bafba794577eea1cea43b12b3f4c0e429696ca0699afa3dff49677eedb56118238bbbb20fedbce6aa9b9f7bf6e925ff9fe09e89b04f23e78c95dffedcebe6e9bf07adcf7ffa5f8fadbb72fbb9bbd7f6b4bfbe2a970e3a624e9070cca0db8d05ba8920ed44c4c8c0d0c4e0d0688d4fc7b6ce38032dd035713a743c163182ce64fcf5f76fd9b2fbb11ffcfbbad376f0e9cdd9e3cfb3e7f4df9bbf9e99b9034f3be5d7cf1cd482c0ebafbd92fdd895dbef3c7b59c71b1b97a6839e5434e848b941226605ed6e34c8f299762c3ae7aadfa13367b224efd56b3a005d530ec64c33e88a45835cdc09b20c4a9601e94a44df78df45efbf73f0cdfd59654b739e6d4485ff1b1073e68dbfd021f9dd68bdaf56abf8fbaf7d79473aee3ed2d39e083a134eb065497b9049c482b86daa672e150f229611a462113e6d6554463266864e5996a51c10a74cdd380d33c734823627aa7e4397f7561065565d666ad5d2ec233f79f87b3b3cafbed0be77a1e77f71a6b1e08b73291c1d39813b6ff9e46d8ce601833511e5f3f22dbdc1ba9eb48286c024422325c256440b5c3ba2b2924bc4838e3833e53afc9ea5be67d05893f05a90294327e48cd3b05b9c8906cbb30c986307cb3a9ce0f69b2e3ff0ad07be715ba13c8de61cf415529a0b11c5a7167e00f0627cd3e40f03738fa1c347eccfffd1c777fdf0c9bdbb982527e158a0b158994de2a523a3a8fbfc2e8fd17431253c54cca293c4b9cf736aa8357961cf87414b2dd346b9518347d8c8df02291f714b93cad254619d956bc3d68d29140b0d3461a3ffc24d68dae96a76f18a7b6fbeee967b3b52ed35458ebe1ca4854e88f17ce8f24339a25e188a45c5b913470fdbb75f7fd5eec79f7bed6eda4214e828d51ae8cbc4b0776882464245471e8118c2e33c3a5267d48a8d06210ae588e64139e2461c755171243c464a4753d7d535537d16353574b69bc86851bc6ff5229cd797849fafc3370d67f4d4f0ddf77ff7fedd93d363f69c23a1eddae9e087ce308abe8a92fcd4313b31866bafbe7ad7d32f1fbccb5366068a93ba92314c146aa8798d96f350df17a72400a0233afff9126271ae19462ea247305bafa158abc0344243984b38427dfca66df88847749cb33c8da8cdcc3a26986cb447a33005396654bcc7f0c4c85dfffc6fdfdc55ace611e0d71fca9940d3d5c9d5db461db77ffad3b7bdf4c69bbbaa621c0d7222369a347059c2c1d04ca575223d8488ea87ad93f13b3e0362993a6a4c5d934e5a968d3ccfd9f4a42e3545ca260fb109d91aeb346945d066db38777516b94402e7aded46572e8e9e6c0a7d2b92a0670c8a4fc819744cc3d1b163bbbef383076f0b9a8d30c3f3af7fc62f7ea8f9f8eb2f7f65c7633ff9d167e99e4364a144c8d488f3cd8b92182bd3c086a732c2b26805e0cc4352eeba513ad238ed67b151555195ec9a0cb7cd936a26ff4a084699a18eb48dc51d31b4bb26369fd58575fd3d58db974177ce452e9944366929c4f85a48c374c879fbe09b9ffde92f1fdf31ffdac1696782d0af975edc9bfdeadf7efdd64aa3d9dfe4091a8da63230c30b790d1d87a767954119271632098c05f190ef56cab505efe7d5389a44403d68f0284d9d2f1eb5118df858b7ba1ddbcf5d8d7356f522666a610d06211c93b1a88a8ad6acab522187a1a169fd3f7feef15b874e1dcb9203c36bf13b7ac8066141de76fbad37ce144b5707f30a8b3d0171cdc281e902aefff0b970a264288fa76411e9baa74e32f79000c813ad6315f7cbb9f490657c42a3418b8838a45d0bb97404176f5e8e8b372c473719d28d044839066c4b5399f0d47518505f2a93216020407b0c36cb9257b9fafb0fffeb8df3c1a1cf19fde0830fae3b3c78e08639ec0bc4745a61989aaa81eb3eb00e4f3e3f2825c588aa534bf655bd9d4e758b26e7d36588603dec057c1927632e4a45b1a88b75b2690996e4528cbe8d94eb924c792ecb50241123c199a621e06c652aec253aeb4c3abffc1e3a3174c34b6fee5d77a66645fbb0c37ef18b5fbcb65a2baf57d930854d7455cc92e268268e03472730ccecc8a3def051f5b4567f5868f85c36e63b3457a4522fe9a48d6432825c2a810ec781eb305864331046369d889a1144633144082fdd3249f5cc248f150c9c3e37e1e74babf1bdf53ffac57f5e2bf5280eabcc3cf5d4334b4e8e8c5ed350f80e5027f536bcd0804058a8eae1f9b78655af58d19e40b1da5064a12943cf5c6441ad2c78ad2bb826d870858ce3b68615b928da62a4dfa44b14f0efccbee3d011db8411b1a0f35a8669a12af512760d88cf3e5b9b1fd03dd2bac06f62f2e435478e1d5c22d7d13dcfc317bef01797d1cd8d824cd5c95b46e84658e06f8d4cc26094d6926106270a90de937363b8f3235b904a849c369fd7e66727cc900f536b22c17a78efda0edcb07d2d3eb87525ceeeeb46673a05c376c8c01168cc869668836124e0370ca22382e9a2404b8e673db2b932fabc16ed62c0035dfddef8e3271ebd4cb1e5f8f838f6ecd97369440f39274a884911d7a5b9b3f8a4abc7d8073e7a4e0f7efada3b542f3e7adb63f8c88ac5a84c5570cfce6df8d3875e44bdeee1373d32ccc8056bb2f8d0b65558d597a3611e22cc4441324cfd9964266c5e2312496372268fd94299d7ada38d343f59f3f9793806f8528fac3fa359938148356a7170e8d8c14b6bb5da37cd575ed9bb8c91bbc8f32db4b1b0ebcc54d93bc350bd291717f467f0da701e93759e98e2f6fe3b3f8ecae41406de3e8ee51d2efe60eb0a7c7bcf412571c27e250d8edf6501f7e562b8fc82d5f8c0392bc95e0ee1a6a9221f9f98229458477c1f8d25117112181c1ac14f9e7c1523d3331838368d5d9fbe0a113aa3f37c1a616736c3e20fc8ae5a5053818e06069acde645274f1d5b46e16a5df2e6fefd9fd208830a836b992dfdc41374d1d0b55d2e4ee51b78950420f5f4990bcfc6a5179f0337a5e3d0913118b50069c7c659dd6d6cae5594781237a2a1ab2d82d5bd3c7e493b362cedc0d2ce18a294294d3d84afcbce2e8ce8982c1c2b8a818327909f2de13ddbd66064741c8b3bdbe174f5522109bb1121d27655913323be0c8ba468be1708f3c30419f81963e2d43bbf572c572f1631b43c1e65a40cccd41b589488b2fbda383896c7a153056449a717f664706e6f16e99c85c71edd0b2a7e1c1e9941b2cd424f679c35d08eeeb48bd58b5258bbac839acc42573a86ee641c6dc9a8d25c262127a8d785a3089be189698c9f9c44ccd530552ce11fbef73c6bc6c0ce4bd6e3e42475338f31349bacd9449d6cd7d044992b4129ca08adde8a623effb6393a5e5ce3527b2de7c596a4a378e2f8383a89d519e279f858155946b8938e64a226ae3cb71f98a11154069b56655129d5f1ec81716cdfd687894a01664dc3e2761793a52aa6f335d42b75b85a121d1913718a545f404e7914894450e539aabcc689136378636816bfd8378ce1a9025630c39fd9793e72b92c0e71968210016129d4ac5102f96239032eb5a33a8711124cb1525c6350b17efee62b362f3e72681283f92261524791d86c52608a5e8a5108aee84ae0e56393883014e7908d22be89ece21e3a5341b55cc7395bbb6998870a8b66824e8c4d9430319d47672681bec519f466e370139440827935e7986a068ad0c454d241948172f83eeae8d8bea50f3b2e7d2f0cb71387060f92a6c39ed764bf91c9b749f80781a88170309381d554d9d14bc607d7f77e71cf5b275293d51af2f52a0bdc545eb79146b72cef4277c6c1336f9d54513d345ec427dfd38fd25885ce2490ea4ee1c0dbc3d4578c1c0b7572a68a1aa32d75d7914cb04e924a2d24d8ced52846477455792457193be99c45a8e50869872380a8818bcfdf8cb64c164e228ee3870ed171ba4c079ab4a909f6bf5623d00c065c5482d491a81559a0b419da9786f3d548996997c5418319c9c422584fdc4fd46a786e704c4d8d160baf376ee3c8481e1ffd603f664f1641158f359b728833f5652ae5386549ccb2d0ce01ae9bd9e0388d04b3ebd86c82861a8c95531a29dfa7ead0d9ed7523c63e6323d7d38e9e3617a97406969326b4e284e9144af97ca80024c86acc97d75e38d0918185ae4de9877a601a75dffab2c769aac63ee1e9614f8f462d1c9d9cc5f064f9b4b8eea2512e7bd03461357daa8a0bb6f650ec11c3c53a2fc6d18066d649dd224fda1236b363b0a35334d2608b86444c996d0c35e489069351da6487d7784e2b96a6b84c10ff34928358848d53372c66cbc4c1c1438a8a9bbecc47165f9bad0d0dc2218ff5238d94cdc79289f6f335df8b48a30c5a9193287b4d3d7484df4b58d4543607266aaa0ce78bf162156fbc3d8108b3596077dd4b12c8128e810444a40b9b9c3467510d860c7e263fd5c3d1d8e424a90b1d4911cb2a8302478b44299f2a6a520d6a456685ecc5be132351fc6af057a8f01a3e056e9d906a4a6aa47ef47077a13264ca59f4b259add7a7024373a3a6cd48b7862afe30d4fc01a418b904a58644bcc15eb43893415f36a1b27068aa88ca5801e5e912b29c42d3cc884c1ca611a1aef2694cc0ce1e5e506ac414d355adf0facc12af0b8d81f24ad3aa967c4af206e77f6d7686b522d2268dde2539ec1b3c4e1663ad3544a371ea154a66209ab6a6a60d43962a06a64c3a394266e82d719a1431d8545b12b2461016578af5b3aa53fa4f841dde67817b486422ec2751a5bb660b751c3dc18e3e5552914fc41d08640592ba6faa1e6092ade41fdb0e232cb2be163607aaef1a49c7d01d364e1da7c6a6d1dd45d2e85c8646b508542b58c2a6bb7fdf11041ce29a42cd34d8672034bfa9e62435f21ba208b41193a91ae2d9cf1738712652d25aed23345fe13b41ba74d923d6716c2e163de5d0186b46f49450a6c7ded2c6d1b75c126aae21cea1cb30c375156586a261aa59422960ed304dd465be677122e5544f036d3da6547025efe3e5d746d0288d70d4f8313e4a119b26b54f72ec3045c2108242cb4d4659a64d4f16a3922d222020a208cd21d375ddc112fb85144fa5d96c2d2ac215902d5814dea303759eb08dddbcc1e80854269811d73648c77565940c708147a9c168079433e28844b141bc5b46546d1f432d4fb9c4c14a54f9f8387560f9a4229e986b6019c7e753846d349ec5fed78fa3b32b83d1c969a636a2184d50a366dba65a8baa1140c840e1aee10fea3b76ecd87f3a8a2d294fbe50eb9f34678b94c9a6c6348eb0b78c9eaa28bca7dc0892cc9864a7c18e5e629445aab8318b8c659d9ee1c59972c527a1b016d85065a81368da24860603e4c4021459276d6d54099d59b4a71388538dc45c2a686ab7e9e95916baa67a8cdad371b652439a168e1d6a6d5b2ea15cac60cdcaf5fbcdebaebbeed5471e7e78947feb96f940e686b35229a4523621202a590a574337654a89189e981135ccf9bdcd86cbbed3db9bc4de7da3d46e71c4e23ac2118311934d261d628b8147b2f0a8af3c994d92c22f1e89c1c09ebda3387cb4c0616d8a12c754e78bd9925dd9ddfa6a44af9a926193d9335406fdd6ae4182d340786b453383d1f3ce7defabe6b66ddb8e32f94ff13bd789bf8b38b2e6121656b2697650fe1f9d98518b055e0b6dd914ea350f33b30db4d3992a15723c6670846e60fc541ec9781b331e6eed65e86395288882c6c876a916a5750dea7ee2bc58ac912c4c5c71e525e85dbd0ac3278ee2d59f3f8942204d106a4f5667961b5e0415992a19d29a205e1a3b6bac4cf5207b3d5f0d6e8da796f7f51fd5bbbabab061c3a627845ddad9dc32a4e1f6548c236d94236d0ce7afc9a1a7238e310e62b21be8ed89a37f5902a74e5644b9c361516f58dd8937876751e6a8e0559a6aa3e9536b706042b1dc6027673faad4388c71cc60d3ad9708af7a8de78da1706c00f93dcf2341366daabe46384a63840b9f7dc6b748103a038770b951e360d6680a643d06966295f59eedec7d42f675ba18f8b5affce5e37d4967df599c5dfa391a2f6a8fc3219d0aa3c982e1bcb58bb072710a6fff6a0af5aa810c87ac95cbe37c3fa336f27d1d49b4b7db78677486f553835f9321361ca41a7444e85bb258297828e43d948ac411c9c22057db44c1e1d111cc1c3b0a3dca20b0667436518ff5e8180e7c1a29b70f2cce4c758118edadd2218fd7ad56cb64d1eabe3fbcf1a6c76594560b8dcb2ebfe2f8b635ab1e59d49e60274fa8c5458cb8b2f93791202c416c5ad9898b2ee8c60b6f0ca35ef4a9a81dac25fbbc7978824c17e0a20d3d7889ca7a9a23c278a1440788654f8e04662962670a5595a58650ac5127c369a8323b099e27424219e508613b2e22d464a61385e5ba08926d54d20944d269ce32c4982964205246e7b10db6020a6337fac8b6f32e3c2e1392b17bf76e35136452a98977065e785f2a66e462516aaaa8d1da9618aa736b742a158d62d50a17cfed3f89a41be3f046566396f61f9cc2e2aca4d9c091e3b3b019d512679a32ebcb74646b02d593a4ff88948911c20e19291a8fb18fccc24a65905dd4ab243e3b2c0c2709279ea00d1ca7a99ead28bb3e035b1268f12b15924bb142162bd5f67fe6d63bbeb471c3e671259524331ae783edd75e37e01ffdef07f6bdb2ffaf1cce30646335c393ce89d9264762161aa9dae214f2a10b9661e0c014ff46fa268dae5d11c70033b466713b7efcca31f6276694918ad8ec27018b9f5364876cff356138c28446c5e3165c7e6e690974ae3c0b15ca14dd92bb0c55aa6257652790a994f615bc1a0206b3c2eb952666d55d883a75614f6ed103bfff891b07e6f615c6dd77df4ddd16a8d9ba7bed8683d1d97dfde599f21a47f656114fc98e263d32f99d08d5ade8375038a6b22cde5a054d8f7963304cce3413632568cce8e86415b35e954ca4298a7628d1c92d0c9088504d1149845997602538f744bb9622d5b18c75ca6cb066226e1cc94c3ba99aef0586744a67801aec2b55426b76b64059e53ffaf5af7dfdcb3ddddde5b94597192e9d45c750fab7f58df79e7bd5fd8ddac3eb4a33f9feba6c2df5b0775872970a25246ce9e2a6923ab18e34a6664bc42f152dfd2e938916b5c570e4c8ac8a5cd92a63266f869d5b54ab6c55f89d2623ad56b1a4d83c15fac6f76c80aed83343dd9723eb796a1f2d1c5da75c964297005479a11215fb64347ff0f24b3e7cff964d84972c025a4b3b2d5cd8f94ac6c86b020af9c15fde56991cfa9b8981179cda4c41f6ebb044a2509ae88caeafb5b6ee345034921c57e6dff2a53a0e1f99c4412238d999c664d5c4a76fff1cbeff8f5fc7c95fbd8574d240947dc9d1a4d6c85654da977cec56f46fbd948d96ecc76c7a4daa0c66dce3f96427261b9f62b980e9421e274e8de2e8b1e12aece4e7eeb8e3aefb62248cb93b0ca7ef69ceddd5083f149d90c7e4b17dbb83f2e8dd33270fa37a7c008d7c516d4d7c33cc946432684544e49bc6c6e80755264db2a693693c9589cebe4db0929d0c918d628d504d7643ee7a54ea05ac5cbd011d1d1d8ac2a5abeba4dc66bd75e7b859e6f90cca258e198569cc940a54e7c3989e2ddf7df9559fb8a7bdbd53ed788279dbd4799999bbf7e4abbb5b5eb568974e0deef6cb1377552b5401c553288f0da151986217ae2898290d667a61563d713234caf0653fe671f88b506056e193c10c06e1d0d13cee7e6810139c83eeb862253e70413f1cd2b291e8829bee544b8c88db4ec268231d2f62ad24296908e3fc0cf23393189b9dfeeacab3df7b4f5b26539b83d7fc05bdb670e91d3ae26be1cdd3a056b06746067605e5d95dcd66c9811475358f6a7102deec389b638145c92659a7eae63fdf0bef9f68849fafcba65be8d8544d7068641a377de97194a80abef5c7dbb1796327d9890eb3d39b941292f5a66c61d8dd239431062164b7f721d5bb1596dd562d14c6ef4de656dfcbfe535377a66597a005adcce80b9d0947507fe19d4171ae51c1ccf09bb755f3273e1bd4cafdbeec79196de9b8befa2f204d4e8ae384079daa942921a9642b45b5935637af29498ac5223e71cfe3183a59c4355b97e2ab379f8faac1fa23056bb6a56e10cb3068e996baa9a416f80c8241c54e0b0eb6afbee8efda566fbf4ffa854ee8cdc15b7bd74edb3c73d729ecacad9d46ab80a4ebba482e39ff3ee3847db83afef6ad8d46fdea40160b22052d16a06cf89d947a1f90fde47e65b33cab8ad96716bcea497ced1b4f6368b4a854c39d3b37a2c2632c2316161b7b8e1867c9d0c5b9d83735a5920d326660da8f76acd8767fe2ac1d3fd355d00d6557381685289aef9579e63e84ae1c0ad4feb6053d4d4de672331cf1251b7e164977bd3a35f4ec737a79fa06bab03ea0fcd31a4cb964ab59570b71593ca86c9bb61a1ff6bc7c0a3f1f18e6390d5c7fe90aa4321621c89a64c60d27ced1baa9b2e8b10835912a7e4c06bdfdba9b7b60d185377cc7ede81f3f13dc70020e1dd2c3d7f3ee339f2600955a3ffc4f48a143da02a6980fbdfcc81beba60f3c77ad56295dd334838d1a9573782bdb575b18814dc061c4f30bb8fe73dfc44b87a6286093f8e13fdd4a569ce4dc9f57900a6fc2ea6a5de4b1de0c4ddb67d8c947725baffd8f74ffc503dabc3bc9ad2175ee06d0bcbb72676c34e7b2a26ed21263caf1058eb41c6df522f96eb267d3809b5b3f509f3cf42f13879eb9cc9b19b954b4a661e8dd726b5de74cd42815b1e785d7f0d2c149558f7f72d5f9886596a2666760544820e519b60066456b8cfaf5fa534ee7b227ba36ed7c3cdebbf9783847b6eedccd5d5b0b6f848585af61ee7f35ccbf75aabdfb16deeff2f0d9d42a5343cbf213035beae3a3ebeb95993511afd277c39f7fb7e7e9b74f662e5cda16fbf67d7f568eb8ee945f298c78d589215f8b0d26db96ec77966e7e35dab1eaa8487cd9b22c24a0dfeef13f020c0071ccbe176f249d010000000049454e44ae426082	image/png	header_img-01.png
240	\\xffd8ffe10d204578696600004d4d002a000000080007011200030000000100010000011a00050000000100000062011b0005000000010000006a0128000300000001000200000131000200000020000000720132000200000014000000928769000400000001000000a8000000d4000afc8000002710000afc800000271041646f62652050686f746f73686f70204353352e31204d6163696e746f736800323031323a30333a33302031303a31383a33360000000003a00100030000000100010000a00200040000000100000064a003000400000001000000960000000000000006010300030000000100060000011a00050000000100000122011b0005000000010000012a012800030000000100020000020100040000000100000132020200040000000100000be60000000000000048000000010000004800000001ffd8ffed000c41646f62655f434d0001ffee000e41646f626500648000000001ffdb0084000c08080809080c09090c110b0a0b11150f0c0c0f1518131315131318110c0c0c0c0c0c110c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c010d0b0b0d0e0d100e0e10140e0e0e14140e0e0e0e14110c0c0c0c0c11110c0c0c0c0c0c110c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0cffc00011080096006403012200021101031101ffdd00040007ffc4013f0000010501010101010100000000000000030001020405060708090a0b0100010501010101010100000000000000010002030405060708090a0b1000010401030204020507060805030c33010002110304211231054151611322718132061491a1b14223241552c16233347282d14307259253f0e1f163733516a2b283264493546445c2a3743617d255e265f2b384c3d375e3f3462794a485b495c4d4e4f4a5b5c5d5e5f55666768696a6b6c6d6e6f637475767778797a7b7c7d7e7f711000202010204040304050607070605350100021103213112044151617122130532819114a1b14223c152d1f0332462e1728292435315637334f1250616a2b283072635c2d2449354a317644555367465e2f2b384c3d375e3f34694a485b495c4d4e4f4a5b5c5d5e5f55666768696a6b6c6d6e6f62737475767778797a7b7c7ffda000c03010002110311003f00c2726da8c19e291642b4d545b52844db2819190ca8fa6df75a786a129088b3a2620c8d05dd039d141cc79206d24bbe8f9a8ef7d3730da039ce3201ec234feaad0c0ca65ef3b9803e36923c09e5569f327f447dad98f2fa6a7ec73fd370125a40f35123c16a6750c8732b04564469e3a2cbb7d46ed606c1981f7c211e63f787d8a9603b8fc5816c26db2a41f36063873dd176a9e32121618a40c74280313387608c413a04db3b7de8d22d06d4958d838492a55bffd0cd2d8f8a86d928a5a4e9d94c561a24fc95a6a35326c18f56e89713007f142c0e966db05f7497132d1e1aca2dc0db92c61fa206e3f7ad8c5a80859dce663c7c23a3a7c8e0063c67ab42fe8cf7bc3d9a83f4bfbd5bc6e8829692347bb95ad431a06bf3473b5da2a9c523d5ba31401d9cc38ac1ed201d39eeaae4e05760da469d885b0fa044fdcab5cc20842ca4c63d9e5f230dd439ed2d99680c33cebaa13186009dc3c471fd595add7080c6903e63440a186cc49920375da7c55de5e66e24f9173f99801c4079b4f669a262d846769a0e5476abed0b45b3ba48db4a492adfffd1036b004943b35e780ac3c4f3a040734b8f92b8d36ad3efcd703a0686c7e55b14b9a1c04ac0ceb1f8a6eb2bfe71e58d6189d48f0f92a8dfdbed0db5d5b8b0f0e0e1f9164e7c6659666c0f57576396c8218a0289b17a3dbd2086924cf8291781cb80f8acee93d4afb71c52e6836b7ef543abb336db9e059e993dcf650888bab6c99e9605bbe72f1da435f601f3d135ad63c0730c885c65381bdd17f50fd21e18098fc56b74e6e6e2bdb50b3d6a1da78c1fe4a7180ad0dac19644eb1a4bd56b0eaa0ea3b10a832cb998cc0c82d7687c60f785abd458efb2bcb7e90121516e0d9e8d6f066c719da3c3f3e53f09008beec3ccc49ba1648e8d70c5315a39a8b4c11aa9b2924ad51a8b1adb926c120e8435bd231c24affa036c77491a45bfffd26734b8f925e9c0f00ae0a4012545d5cea78ec15c69b99762faaf734005d01cc9f113aacdbba2645f5063af70b4125c7dc267e8b764ed6ed5bd63365cc71d0111f7152cccdc5c5a813efb5fa358b2b98328e79d77fcdd9e56119f2f027c7f047d03a77d9ad00bb739ad324abd9982cc9758c746a6777fb5374a163dceb0892401a71251326eb2865960831303924f92afa9d5b5400a722ffab74daea4d8d2e6d1a37680011a9f7edfa7f4bf3d5dc2e94cc524d7b98d264cba47dc8b8bd5abb62bb9a6bb3b03dd59cbb9be934d5c7709c492352b78603503773b3007b5c3c74fc5429cac7c6bc1c8690d7835ee2d96c8fcc77eefd2dc95ee24e9e13f3517d672f1c556fb290e6bac769b9e44fb59fba80d903595f830af18326b034639c1bfd59dcd561b5868d111a246e889d614b6ad8c3131c70077a71398989e69c86c64516c491244c24a4627ffd3d22d94db3b94584a15b69b9dd4a58da9fc0dd07e6161e53995e43b2725c61a43696fca5ce5d2e7639bf19ec1f4c7b99f10b9eea1fac63d2e025d43bdc0ff00df967f371acbc5d243f274b939de131bd627f34b8b977963dd8f76d07861833fd5d559a43aa67ab7b8df69ec08d3f159d8399d3f1da7752f6eb25ad02371f272b63ab50e67eaf8c5cf2400e71803ccb5aabd7836fd35acafc15fb53132b28633ab2d07dacb1bd9c3c0ad202c6562bb4c91dd53c6afed168bad737d4a87b1add20797f255b75bbde64e83929b2547aea8ae2035be254316cb41663d8c0458e716b81fccd5e2546e76f7fb75ec079a230b5b9f4b0b8086380f3309f845e4803d6416e6918e2c8468784b776a8b8a238c20bcad871187e724a33aa4929fffd4d984a14a123a2b6d360442c6ea78043df7d5a32d1fa56f107f7ffb4b61c557b00782d3c1d0a664c6324483f4f35f8b21c72121f5feebcfe3f4cc77b5ceb1e4f1238807e8ad0c3e994907d210c6e913c91d950c8b2ba9d752d0416b9cdf98fa2a54f5615b1ad7b893a4ff0067f7a5669810482767544c10081b875ae6d787492d9f74402a83ae0c669f49daaaf95d60e53c012e0346b06b1fda51ac9ddef1bac3c563b7f5d0e03290111c47b05dc400249a1ddbd8ac686fad61803e8cf72a26adf78c83f49bfcd8f04f5d4f30eb4c9ec3b0feaab1b410afe0e5383d73d67dba45a99b98e31c11f97aff00593ef0f6870e0eaa042a8d75ecc8018efd1907730f1fd60ad8783c882ac348c08f15b6a4a5e7d92496bfffd5dc3a2838a9140c9c9a31dbbae786f80ee7e0d569a6a7155afcbc7c6fe75deeecc1a959f93d5ecb496d03d3671b8fd23ff91545c67599279252b648e2ea51675c5f9766431b01e64b39d3ff0024ae635d8b7523735a76f730a95f6d58d57ad61d3b01c93fba165d7d51ff006a0fba052ed1cc68e07ef7f29ed55799c78a46372e199dfcbf78ba3ca4731848c63c5088ff009dfbb177f736c76cc760adbf9d601afc18ae63d35d6dd0478f8a7c7a2b15b5cd21c0804387041fa2e08c1815bc58a18a3511e72eb26a64c9299b97d9d9609acb36364f2781e2a563d953771d7b003927f7421d4c73dfead9abbc0703c87f55496b17654ed5ee3ef7f6f00890e6922263b8d511a3bf741c8b00735839ee98755c196e1e1aa4a7f9b3feb09214a7ffd67cbeb56b896628d8ce0587e91feafeeacab0becb25ce2e73b971d4a9f804fb75956e98c440d98ed111d8289604451b0d8dadceadbbde07b1be27b4a5a2e79feb7906eea15e2b0cb3184bc03f9eeedfd96aaf73fd16b8bc7d1d5c47123e8f0a671bd0c9b439e6cb0bbf4d669ee79f7bf637f7536531af6063841790ddbdd67e43c596bc785dce571fb3c94b27e951c9af97a5d8faa5d4af6d630f2cfb1defc771fcddc4fe85ff00c97fd3ad74ef2d630b9c7686825c4f6016362f476fd90970db65827e1a435bfe6abc1b73e9aabbc870af571eef23f9bdff00d55a311400708ea49530bad7facf040e2a61ec3cff0096e56d8d81e7dd41804ebdb5531aa28652a9da4bb2001dcab63955983f5bf809494da811b7c9242f507af13f9b1f3e524690ff00ffd7ce64c6e3c9538480fb94b4570ad621a133dc1ad2e3a35a249f20a7a70153eaf70a702c8fa567e8dbfdafa5ff0041091110647a0b5d089948447e9101c2638bec758797925c60180e3ff54adf4bc5764f55c7686fe8ea9b1f3e4aa56de34d40e08e07ef388feb2d9fab8436cc9bbe93ced63167f2d1e2cb67a5c9dce7e5edf25c23f4cc61f67abfee5e85e75d8de7ba872e8ecdfca901b1b132e3ab8f9a931ba7995a4e02fc0f32a4340901f82475490c86baa0300192f27b045712d20f6eea1fe19e4776828a1abb87dab7f69849420fda23cd24e53fffd0a4213885e6c92b8163e922162f5f369ba80e6c54276b8f05c7ff0022d5c824a2e63f9a96ed9e4ff9f86dbf57a0af6eb1a807b4827ccadffab3e9fd9dce711eb17196787eeebf4570092adc9fcd2f274be2ff00cce2dfe69793eae39d7e68ad8fee5e4692bce23ebad4c79d1791a488417d75d11aa1327d477840fb9793a49c87d461bf6be74949797248a9ffd9ffed147650686f746f73686f7020332e30003842494d0425000000000010000000000000000000000000000000003842494d043a0000000000bf000000100000000100000000000b7072696e744f7574707574000000040000000050737453626f6f6c0100000000496e7465656e756d00000000496e746500000000436c726d0000000f7072696e745369787465656e426974626f6f6c000000000b7072696e7465724e616d655445585400000025005200490043004f0048002000410066006900630069006f0020004d00500020004300320030003500310020005b003000300032003600370033003100450034004600390030005d0000003842494d043b0000000001b200000010000000010000000000127072696e744f75747075744f7074696f6e7300000012000000004370746e626f6f6c0000000000436c6272626f6f6c00000000005267734d626f6f6c000000000043726e43626f6f6c0000000000436e7443626f6f6c00000000004c626c73626f6f6c00000000004e677476626f6f6c0000000000456d6c44626f6f6c0000000000496e7472626f6f6c000000000042636b674f626a630000000100000000000052474243000000030000000052642020646f7562406fe000000000000000000047726e20646f7562406fe0000000000000000000426c2020646f7562406fe000000000000000000042726454556e744623526c74000000000000000000000000426c6420556e744623526c7400000000000000000000000052736c74556e74462350786c40520000000000000000000a766563746f7244617461626f6f6c010000000050675073656e756d00000000506750730000000050675043000000004c656674556e744623526c74000000000000000000000000546f7020556e744623526c7400000000000000000000000053636c20556e74462350726340590000000000003842494d03ed000000000010004800000001000200480000000100023842494d042600000000000e000000000000000000003f8000003842494d040d000000000004000000783842494d04190000000000040000001e3842494d03f3000000000009000000000000000001003842494d271000000000000a000100000000000000023842494d03f5000000000048002f66660001006c66660006000000000001002f6666000100a1999a0006000000000001003200000001005a00000006000000000001003500000001002d000000060000000000013842494d03f80000000000700000ffffffffffffffffffffffffffffffffffffffffffff03e800000000ffffffffffffffffffffffffffffffffffffffffffff03e800000000ffffffffffffffffffffffffffffffffffffffffffff03e800000000ffffffffffffffffffffffffffffffffffffffffffff03e800003842494d040000000000000200003842494d040200000000000200003842494d043000000000000101003842494d042d0000000000060001000000033842494d0408000000000010000000010000024000000240000000003842494d041e000000000004000000003842494d041a00000000034900000006000000000000000000000096000000640000000a0055006e007400690074006c00650064002d00310000000100000000000000000000000000000000000000010000000000000000000000640000009600000000000000000000000000000000010000000000000000000000000000000000000010000000010000000000006e756c6c0000000200000006626f756e64734f626a6300000001000000000000526374310000000400000000546f70206c6f6e6700000000000000004c6566746c6f6e67000000000000000042746f6d6c6f6e670000009600000000526768746c6f6e670000006400000006736c69636573566c4c73000000014f626a6300000001000000000005736c6963650000001200000007736c69636549446c6f6e67000000000000000767726f757049446c6f6e6700000000000000066f726967696e656e756d0000000c45536c6963654f726967696e0000000d6175746f47656e6572617465640000000054797065656e756d0000000a45536c6963655479706500000000496d672000000006626f756e64734f626a6300000001000000000000526374310000000400000000546f70206c6f6e6700000000000000004c6566746c6f6e67000000000000000042746f6d6c6f6e670000009600000000526768746c6f6e67000000640000000375726c54455854000000010000000000006e756c6c54455854000000010000000000004d7367655445585400000001000000000006616c74546167544558540000000100000000000e63656c6c54657874497348544d4c626f6f6c010000000863656c6c546578745445585400000001000000000009686f727a416c69676e656e756d0000000f45536c696365486f727a416c69676e0000000764656661756c740000000976657274416c69676e656e756d0000000f45536c69636556657274416c69676e0000000764656661756c740000000b6267436f6c6f7254797065656e756d0000001145536c6963654247436f6c6f7254797065000000004e6f6e6500000009746f704f75747365746c6f6e67000000000000000a6c6566744f75747365746c6f6e67000000000000000c626f74746f6d4f75747365746c6f6e67000000000000000b72696768744f75747365746c6f6e6700000000003842494d042800000000000c000000023ff00000000000003842494d0414000000000004000000033842494d040c000000000c020000000100000064000000960000012c0000afc800000be600180001ffd8ffed000c41646f62655f434d0001ffee000e41646f626500648000000001ffdb0084000c08080809080c09090c110b0a0b11150f0c0c0f1518131315131318110c0c0c0c0c0c110c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c010d0b0b0d0e0d100e0e10140e0e0e14140e0e0e0e14110c0c0c0c0c11110c0c0c0c0c0c110c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0cffc00011080096006403012200021101031101ffdd00040007ffc4013f0000010501010101010100000000000000030001020405060708090a0b0100010501010101010100000000000000010002030405060708090a0b1000010401030204020507060805030c33010002110304211231054151611322718132061491a1b14223241552c16233347282d14307259253f0e1f163733516a2b283264493546445c2a3743617d255e265f2b384c3d375e3f3462794a485b495c4d4e4f4a5b5c5d5e5f55666768696a6b6c6d6e6f637475767778797a7b7c7d7e7f711000202010204040304050607070605350100021103213112044151617122130532819114a1b14223c152d1f0332462e1728292435315637334f1250616a2b283072635c2d2449354a317644555367465e2f2b384c3d375e3f34694a485b495c4d4e4f4a5b5c5d5e5f55666768696a6b6c6d6e6f62737475767778797a7b7c7ffda000c03010002110311003f00c2726da8c19e291642b4d545b52844db2819190ca8fa6df75a786a129088b3a2620c8d05dd039d141cc79206d24bbe8f9a8ef7d3730da039ce3201ec234feaad0c0ca65ef3b9803e36923c09e5569f327f447dad98f2fa6a7ec73fd370125a40f35123c16a6750c8732b04564469e3a2cbb7d46ed606c1981f7c211e63f787d8a9603b8fc5816c26db2a41f36063873dd176a9e32121618a40c74280313387608c413a04db3b7de8d22d06d4958d838492a55bffd0cd2d8f8a86d928a5a4e9d94c561a24fc95a6a35326c18f56e89713007f142c0e966db05f7497132d1e1aca2dc0db92c61fa206e3f7ad8c5a80859dce663c7c23a3a7c8e0063c67ab42fe8cf7bc3d9a83f4bfbd5bc6e8829692347bb95ad431a06bf3473b5da2a9c523d5ba31401d9cc38ac1ed201d39eeaae4e05760da469d885b0fa044fdcab5cc20842ca4c63d9e5f230dd439ed2d99680c33cebaa13186009dc3c471fd595add7080c6903e63440a186cc49920375da7c55de5e66e24f9173f99801c4079b4f669a262d846769a0e5476abed0b45b3ba48db4a492adfffd1036b004943b35e780ac3c4f3a040734b8f92b8d36ad3efcd703a0686c7e55b14b9a1c04ac0ceb1f8a6eb2bfe71e58d6189d48f0f92a8dfdbed0db5d5b8b0f0e0e1f9164e7c6659666c0f57576396c8218a0289b17a3dbd2086924cf8291781cb80f8acee93d4afb71c52e6836b7ef543abb336db9e059e993dcf650888bab6c99e9605bbe72f1da435f601f3d135ad63c0730c885c65381bdd17f50fd21e18098fc56b74e6e6e2bdb50b3d6a1da78c1fe4a7180ad0dac19644eb1a4bd56b0eaa0ea3b10a832cb998cc0c82d7687c60f785abd458efb2bcb7e90121516e0d9e8d6f066c719da3c3f3e53f09008beec3ccc49ba1648e8d70c5315a39a8b4c11aa9b2924ad51a8b1adb926c120e8435bd231c24affa036c77491a45bfffd26734b8f925e9c0f00ae0a4012545d5cea78ec15c69b99762faaf734005d01cc9f113aacdbba2645f5063af70b4125c7dc267e8b764ed6ed5bd63365cc71d0111f7152cccdc5c5a813efb5fa358b2b98328e79d77fcdd9e56119f2f027c7f047d03a77d9ad00bb739ad324abd9982cc9758c746a6777fb5374a163dceb0892401a71251326eb2865960831303924f92afa9d5b5400a722ffab74daea4d8d2e6d1a37680011a9f7edfa7f4bf3d5dc2e94cc524d7b98d264cba47dc8b8bd5abb62bb9a6bb3b03dd59cbb9be934d5c7709c492352b78603503773b3007b5c3c74fc5429cac7c6bc1c8690d7835ee2d96c8fcc77eefd2dc95ee24e9e13f3517d672f1c556fb290e6bac769b9e44fb59fba80d903595f830af18326b034639c1bfd59dcd561b5868d111a246e889d614b6ad8c3131c70077a71398989e69c86c64516c491244c24a4627ffd3d22d94db3b94584a15b69b9dd4a58da9fc0dd07e6161e53995e43b2725c61a43696fca5ce5d2e7639bf19ec1f4c7b99f10b9eea1fac63d2e025d43bdc0ff00df967f371acbc5d243f274b939de131bd627f34b8b977963dd8f76d07861833fd5d559a43aa67ab7b8df69ec08d3f159d8399d3f1da7752f6eb25ad02371f272b63ab50e67eaf8c5cf2400e71803ccb5aabd7836fd35acafc15fb53132b28633ab2d07dacb1bd9c3c0ad202c6562bb4c91dd53c6afed168bad737d4a87b1add20797f255b75bbde64e83929b2547aea8ae2035be254316cb41663d8c0458e716b81fccd5e2546e76f7fb75ec079a230b5b9f4b0b8086380f3309f845e4803d6416e6918e2c8468784b776a8b8a238c20bcad871187e724a33aa4929fffd4d984a14a123a2b6d360442c6ea78043df7d5a32d1fa56f107f7ffb4b61c557b00782d3c1d0a664c6324483f4f35f8b21c72121f5feebcfe3f4cc77b5ceb1e4f1238807e8ad0c3e994907d210c6e913c91d950c8b2ba9d752d0416b9cdf98fa2a54f5615b1ad7b893a4ff0067f7a5669810482767544c10081b875ae6d787492d9f74402a83ae0c669f49daaaf95d60e53c012e0346b06b1fda51ac9ddef1bac3c563b7f5d0e03290111c47b05dc400249a1ddbd8ac686fad61803e8cf72a26adf78c83f49bfcd8f04f5d4f30eb4c9ec3b0feaab1b410afe0e5383d73d67dba45a99b98e31c11f97aff00593ef0f6870e0eaa042a8d75ecc8018efd1907730f1fd60ad8783c882ac348c08f15b6a4a5e7d92496bfffd5dc3a2838a9140c9c9a31dbbae786f80ee7e0d569a6a7155afcbc7c6fe75deeecc1a959f93d5ecb496d03d3671b8fd23ff91545c67599279252b648e2ea51675c5f9766431b01e64b39d3ff0024ae635d8b7523735a76f730a95f6d58d57ad61d3b01c93fba165d7d51ff006a0fba052ed1cc68e07ef7f29ed55799c78a46372e199dfcbf78ba3ca4731848c63c5088ff009dfbb177f736c76cc760adbf9d601afc18ae63d35d6dd0478f8a7c7a2b15b5cd21c0804387041fa2e08c1815bc58a18a3511e72eb26a64c9299b97d9d9609acb36364f2781e2a563d953771d7b003927f7421d4c73dfead9abbc0703c87f55496b17654ed5ee3ef7f6f00890e6922263b8d511a3bf741c8b00735839ee98755c196e1e1aa4a7f9b3feb09214a7ffd67cbeb56b896628d8ce0587e91feafeeacab0becb25ce2e73b971d4a9f804fb75956e98c440d98ed111d8289604451b0d8dadceadbbde07b1be27b4a5a2e79feb7906eea15e2b0cb3184bc03f9eeedfd96aaf73fd16b8bc7d1d5c47123e8f0a671bd0c9b439e6cb0bbf4d669ee79f7bf637f7536531af6063841790ddbdd67e43c596bc785dce571fb3c94b27e951c9af97a5d8faa5d4af6d630f2cfb1defc771fcddc4fe85ff00c97fd3ad74ef2d630b9c7686825c4f6016362f476fd90970db65827e1a435bfe6abc1b73e9aabbc870af571eef23f9bdff00d55a311400708ea49530bad7facf040e2a61ec3cff0096e56d8d81e7dd41804ebdb5531aa28652a9da4bb2001dcab63955983f5bf809494da811b7c9242f507af13f9b1f3e524690ff00ffd7ce64c6e3c9538480fb94b4570ad621a133dc1ad2e3a35a249f20a7a70153eaf70a702c8fa567e8dbfdafa5ff0041091110647a0b5d089948447e9101c2638bec758797925c60180e3ff54adf4bc5764f55c7686fe8ea9b1f3e4aa56de34d40e08e07ef388feb2d9fab8436cc9bbe93ced63167f2d1e2cb67a5c9dce7e5edf25c23f4cc61f67abfee5e85e75d8de7ba872e8ecdfca901b1b132e3ab8f9a931ba7995a4e02fc0f32a4340901f82475490c86baa0300192f27b045712d20f6eea1fe19e4776828a1abb87dab7f69849420fda23cd24e53fffd0a4213885e6c92b8163e922162f5f369ba80e6c54276b8f05c7ff0022d5c824a2e63f9a96ed9e4ff9f86dbf57a0af6eb1a807b4827ccadffab3e9fd9dce711eb17196787eeebf4570092adc9fcd2f274be2ff00cce2dfe69793eae39d7e68ad8fee5e4692bce23ebad4c79d1791a488417d75d11aa1327d477840fb9793a49c87d461bf6be74949797248a9ffd93842494d042100000000005900000001010000000f00410064006f00620065002000500068006f0074006f00730068006f00700000001500410064006f00620065002000500068006f0074006f00730068006f00700020004300530035002e003100000001003842494d04060000000000070006000000010100ffe10e65687474703a2f2f6e732e61646f62652e636f6d2f7861702f312e302f003c3f787061636b657420626567696e3d22efbbbf222069643d2257354d304d7043656869487a7265537a4e54637a6b633964223f3e203c783a786d706d65746120786d6c6e733a783d2261646f62653a6e733a6d6574612f2220783a786d70746b3d2241646f626520584d5020436f726520352e302d633036312036342e3134303934392c20323031302f31322f30372d31303a35373a30312020202020202020223e203c7264663a52444620786d6c6e733a7264663d22687474703a2f2f7777772e77332e6f72672f313939392f30322f32322d7264662d73796e7461782d6e7323223e203c7264663a4465736372697074696f6e207264663a61626f75743d222220786d6c6e733a786d703d22687474703a2f2f6e732e61646f62652e636f6d2f7861702f312e302f2220786d6c6e733a786d704d4d3d22687474703a2f2f6e732e61646f62652e636f6d2f7861702f312e302f6d6d2f2220786d6c6e733a73744576743d22687474703a2f2f6e732e61646f62652e636f6d2f7861702f312e302f73547970652f5265736f757263654576656e74232220786d6c6e733a70686f746f73686f703d22687474703a2f2f6e732e61646f62652e636f6d2f70686f746f73686f702f312e302f2220786d6c6e733a64633d22687474703a2f2f7075726c2e6f72672f64632f656c656d656e74732f312e312f2220786d703a43726561746f72546f6f6c3d2241646f62652050686f746f73686f70204353352e31204d6163696e746f73682220786d703a437265617465446174653d22323031322d30332d33305431303a31383a33362d30353a30302220786d703a4d65746164617461446174653d22323031322d30332d33305431303a31383a33362d30353a30302220786d703a4d6f64696679446174653d22323031322d30332d33305431303a31383a33362d30353a30302220786d704d4d3a496e7374616e636549443d22786d702e6969643a42353743344632423443323036383131413331394337454630323232424634332220786d704d4d3a446f63756d656e7449443d22786d702e6469643a42343743344632423443323036383131413331394337454630323232424634332220786d704d4d3a4f726967696e616c446f63756d656e7449443d22786d702e6469643a4234374334463242344332303638313141333139433745463032323242463433222070686f746f73686f703a436f6c6f724d6f64653d2233222070686f746f73686f703a49434350726f66696c653d22735247422049454336313936362d322e31222064633a666f726d61743d22696d6167652f6a706567223e203c786d704d4d3a486973746f72793e203c7264663a5365713e203c7264663a6c692073744576743a616374696f6e3d2263726561746564222073744576743a696e7374616e636549443d22786d702e6969643a4234374334463242344332303638313141333139433745463032323242463433222073744576743a7768656e3d22323031322d30332d33305431303a31383a33362d30353a3030222073744576743a736f6674776172654167656e743d2241646f62652050686f746f73686f70204353352e31204d6163696e746f7368222f3e203c7264663a6c692073744576743a616374696f6e3d227361766564222073744576743a696e7374616e636549443d22786d702e6969643a4235374334463242344332303638313141333139433745463032323242463433222073744576743a7768656e3d22323031322d30332d33305431303a31383a33362d30353a3030222073744576743a736f6674776172654167656e743d2241646f62652050686f746f73686f70204353352e31204d6163696e746f7368222073744576743a6368616e6765643d222f222f3e203c2f7264663a5365713e203c2f786d704d4d3a486973746f72793e203c70686f746f73686f703a446f63756d656e74416e636573746f72733e203c7264663a4261673e203c7264663a6c693e757569643a39373135434335343646393131314531383336343931393638373144364439333c2f7264663a6c693e203c2f7264663a4261673e203c2f70686f746f73686f703a446f63756d656e74416e636573746f72733e203c2f7264663a4465736372697074696f6e3e203c2f7264663a5244463e203c2f783a786d706d6574613e2020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020203c3f787061636b657420656e643d2277223f3effe20c584943435f50524f46494c4500010100000c484c696e6f021000006d6e74725247422058595a2007ce00020009000600310000616373704d5346540000000049454320735247420000000000000000000000010000f6d6000100000000d32d4850202000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001163707274000001500000003364657363000001840000006c77747074000001f000000014626b707400000204000000147258595a00000218000000146758595a0000022c000000146258595a0000024000000014646d6e640000025400000070646d6464000002c400000088767565640000034c0000008676696577000003d4000000246c756d69000003f8000000146d6561730000040c0000002474656368000004300000000c725452430000043c0000080c675452430000043c0000080c625452430000043c0000080c7465787400000000436f70797269676874202863292031393938204865776c6574742d5061636b61726420436f6d70616e790000646573630000000000000012735247422049454336313936362d322e31000000000000000000000012735247422049454336313936362d322e31000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000058595a20000000000000f35100010000000116cc58595a200000000000000000000000000000000058595a200000000000006fa2000038f50000039058595a2000000000000062990000b785000018da58595a2000000000000024a000000f840000b6cf64657363000000000000001649454320687474703a2f2f7777772e6965632e636800000000000000000000001649454320687474703a2f2f7777772e6965632e63680000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000064657363000000000000002e4945432036313936362d322e312044656661756c742052474220636f6c6f7572207370616365202d207352474200000000000000000000002e4945432036313936362d322e312044656661756c742052474220636f6c6f7572207370616365202d20735247420000000000000000000000000000000000000000000064657363000000000000002c5265666572656e63652056696577696e6720436f6e646974696f6e20696e2049454336313936362d322e3100000000000000000000002c5265666572656e63652056696577696e6720436f6e646974696f6e20696e2049454336313936362d322e31000000000000000000000000000000000000000000000000000076696577000000000013a4fe00145f2e0010cf140003edcc0004130b00035c9e0000000158595a2000000000004c09560050000000571fe76d6561730000000000000001000000000000000000000000000000000000028f0000000273696720000000004352542063757276000000000000040000000005000a000f00140019001e00230028002d00320037003b00400045004a004f00540059005e00630068006d00720077007c00810086008b00900095009a009f00a400a900ae00b200b700bc00c100c600cb00d000d500db00e000e500eb00f000f600fb01010107010d01130119011f0125012b01320138013e0145014c0152015901600167016e0175017c0183018b0192019a01a101a901b101b901c101c901d101d901e101e901f201fa0203020c0214021d0226022f02380241024b0254025d02670271027a0284028e029802a202ac02b602c102cb02d502e002eb02f50300030b03160321032d03380343034f035a03660372037e038a039603a203ae03ba03c703d303e003ec03f9040604130420042d043b0448045504630471047e048c049a04a804b604c404d304e104f004fe050d051c052b053a05490558056705770586059605a605b505c505d505e505f6060606160627063706480659066a067b068c069d06af06c006d106e306f507070719072b073d074f076107740786079907ac07bf07d207e507f8080b081f08320846085a086e0882089608aa08be08d208e708fb09100925093a094f09640979098f09a409ba09cf09e509fb0a110a270a3d0a540a6a0a810a980aae0ac50adc0af30b0b0b220b390b510b690b800b980bb00bc80be10bf90c120c2a0c430c5c0c750c8e0ca70cc00cd90cf30d0d0d260d400d5a0d740d8e0da90dc30dde0df80e130e2e0e490e640e7f0e9b0eb60ed20eee0f090f250f410f5e0f7a0f960fb30fcf0fec1009102610431061107e109b10b910d710f511131131114f116d118c11aa11c911e81207122612451264128412a312c312e31303132313431363138313a413c513e5140614271449146a148b14ad14ce14f01512153415561578159b15bd15e0160316261649166c168f16b216d616fa171d17411765178917ae17d217f7181b18401865188a18af18d518fa19201945196b199119b719dd1a041a2a1a511a771a9e1ac51aec1b141b3b1b631b8a1bb21bda1c021c2a1c521c7b1ca31ccc1cf51d1e1d471d701d991dc31dec1e161e401e6a1e941ebe1ee91f131f3e1f691f941fbf1fea20152041206c209820c420f0211c2148217521a121ce21fb22272255228222af22dd230a23382366239423c223f0241f244d247c24ab24da250925382568259725c725f726272657268726b726e827182749277a27ab27dc280d283f287128a228d429062938296b299d29d02a022a352a682a9b2acf2b022b362b692b9d2bd12c052c392c6e2ca22cd72d0c2d412d762dab2de12e162e4c2e822eb72eee2f242f5a2f912fc72ffe3035306c30a430db3112314a318231ba31f2322a3263329b32d4330d3346337f33b833f1342b3465349e34d83513354d358735c235fd3637367236ae36e937243760379c37d738143850388c38c839053942397f39bc39f93a363a743ab23aef3b2d3b6b3baa3be83c273c653ca43ce33d223d613da13de03e203e603ea03ee03f213f613fa23fe24023406440a640e74129416a41ac41ee4230427242b542f7433a437d43c044034447448a44ce45124555459a45de4622466746ab46f04735477b47c04805484b489148d7491d496349a949f04a374a7d4ac44b0c4b534b9a4be24c2a4c724cba4d024d4a4d934ddc4e254e6e4eb74f004f494f934fdd5027507150bb51065150519b51e65231527c52c75313535f53aa53f65442548f54db5528557555c2560f565c56a956f75744579257e0582f587d58cb591a596959b85a075a565aa65af55b455b955be55c355c865cd65d275d785dc95e1a5e6c5ebd5f0f5f615fb36005605760aa60fc614f61a261f56249629c62f06343639763eb6440649464e9653d659265e7663d669266e8673d679367e9683f689668ec6943699a69f16a486a9f6af76b4f6ba76bff6c576caf6d086d606db96e126e6b6ec46f1e6f786fd1702b708670e0713a719571f0724b72a67301735d73b87414747074cc7528758575e1763e769b76f8775677b37811786e78cc792a798979e77a467aa57b047b637bc27c217c817ce17d417da17e017e627ec27f237f847fe5804780a8810a816b81cd8230829282f4835783ba841d848084e3854785ab860e867286d7873b879f8804886988ce8933899989fe8a648aca8b308b968bfc8c638cca8d318d988dff8e668ece8f368f9e9006906e90d6913f91a89211927a92e3934d93b69420948a94f4955f95c99634969f970a977597e0984c98b89924999099fc9a689ad59b429baf9c1c9c899cf79d649dd29e409eae9f1d9f8b9ffaa069a0d8a147a1b6a226a296a306a376a3e6a456a4c7a538a5a9a61aa68ba6fda76ea7e0a852a8c4a937a9a9aa1caa8fab02ab75abe9ac5cacd0ad44adb8ae2daea1af16af8bb000b075b0eab160b1d6b24bb2c2b338b3aeb425b49cb513b58ab601b679b6f0b768b7e0b859b8d1b94ab9c2ba3bbab5bb2ebba7bc21bc9bbd15bd8fbe0abe84beffbf7abff5c070c0ecc167c1e3c25fc2dbc358c3d4c451c4cec54bc5c8c646c6c3c741c7bfc83dc8bcc93ac9b9ca38cab7cb36cbb6cc35ccb5cd35cdb5ce36ceb6cf37cfb8d039d0bad13cd1bed23fd2c1d344d3c6d449d4cbd54ed5d1d655d6d8d75cd7e0d864d8e8d96cd9f1da76dafbdb80dc05dc8add10dd96de1cdea2df29dfafe036e0bde144e1cce253e2dbe363e3ebe473e4fce584e60de696e71fe7a9e832e8bce946e9d0ea5beae5eb70ebfbec86ed11ed9cee28eeb4ef40efccf058f0e5f172f1fff28cf319f3a7f434f4c2f550f5def66df6fbf78af819f8a8f938f9c7fa57fae7fb77fc07fc98fd29fdbafe4bfedcff6dffffffee000e41646f626500644000000001ffdb0084000202020202020202020203020202030403020203040504040404040506050505050505060607070807070609090a0a09090c0c0c0c0c0c0c0c0c0c0c0c0c0c0c01030303050405090606090d0a090a0d0f0e0e0e0e0f0f0c0c0c0c0c0f0f0c0c0c0c0c0c0f0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0cffc00011080096006403011100021101031101ffdd0004000dffc401a20000000701010101010000000000000000040503020601000708090a0b0100020203010101010100000000000000010002030405060708090a0b1000020103030204020607030402060273010203110400052112314151061361227181143291a10715b14223c152d1e1331662f0247282f12543345392a2b26373c235442793a3b33617546474c3d2e2082683090a181984944546a4b456d355281af2e3f3c4d4e4f465758595a5b5c5d5e5f566768696a6b6c6d6e6f637475767778797a7b7c7d7e7f738485868788898a8b8c8d8e8f82939495969798999a9b9c9d9e9f92a3a4a5a6a7a8a9aaabacadaeafa110002020102030505040506040803036d0100021103042112314105511361220671819132a1b1f014c1d1e1234215526272f1332434438216925325a263b2c20773d235e2448317549308090a18192636451a2764745537f2a3b3c32829d3e3f38494a4b4c4d4e4f465758595a5b5c5d5e5f5465666768696a6b6c6d6e6f6475767778797a7b7c7d7e7f738485868788898a8b8c8d8e8f839495969798999a9b9c9d9e9f92a3a4a5a6a7a8a9aaabacadaeafaffda000c03010002110311003f00f29cf53d3a0e833aa7960a0b6ed5e447c582936ac2123af4c516b7d1635f0f0c56d07725215abbaa0ad2ac40a9f01e3909e48c05c8803cdb71c4c8d0165033dade34d0c2b6b2bc9742b6c8ab5326d5e2b4efed9abc9db1a606b889f707630ecece45d5205ac2e52369e6b592145af3f5053890687952a363ef8e2ed3d3e43f557bf65c9a2cd0fe1bf720a48eaa026f5ea7ad733ac48583b38dbf540490f0153d4f41808a640a15adcb9a9069d86469374dad8d772361d2b8d2f1284d193fbb8d76fda6c04262505f576afd934fe6c8f0b3e20fffd0f372d9153c9c7c47eca6756f27c4d496bc3a9ab1dd87860482a6b073340280615b62fe62f30dae9328d2ed78dd6b32ad62b4aec3c013fcc7b0cd4f6876a474de98ef3fb07bff0053b2d07674b53ea3b47bff0052486eeeb45d5f4c9b54863bbbbbb71324132f210a2a5500e35e0ce41aae721a8d54f39329caff001d1eb34da4861004457def5ff21799acbcc178ff0058d3e0b7bd68cdbbc91a91c525928af53f67a75d8f4ae614a74e70c168ff003ce8962c97f67a6c5343a6dc5b9811633d1f8a1049a102818edbd0d3119a31e6c7f2d29720f03d50dfda9b3b38ad8c0eced0a12ac0503945dd87ed536aed99da6d7cf1ff00773af2e9f270f53d9d19ef38fc50715e17bf8ecee20a7a8182dc29aa86562a558536e87bf6cdf693b5c48d65dbcff58747a9ecd9405e3dfcbaa7df5550030a1045430a114cdf088ab751c450b244f2feee31f0fed36448b640d7351364bf617ec8fb6ddce0e14f1aafd5138f0e3b787b61e1471bffd1e292c0226a8a339eade19d61790096980cae40fb35f898f7c149ba4b3ccb7ebe5cd2c5d088bdc4d22c50462955a824b9a915e2a2b4ee76cc1ed1d5fe570998e7c87bff006399a0d29d56510e9d7dc90f913f2c66d5afe2f306b8d23dccf289ace11d60a3f30c41a9a926bed9e57aeed49711113ef3defac766763094458a0f55d6ff00266f6f6f2def2c94491cac05cf6258fedaf87b8a5330e3da248ddd94bb1f8796ef41f2d7e4947a1dac93443eaf7b74393bb1e401241ebf31e1944f5b326dccc5d97111f367d2f95ad1025bcb0a3b2a0e529a962480a6addea07d1db2896a2727263a28c7a3cfbccbe42b0d5236b67b7063ff0075ba2fc4b4f73bd4549c9e3d4ca26c34e6d04662887cdbe62f275cf97ae754866b449966b34874c979f169497f8cd5b97c4475dbdb3a3d16abc58f9bc8768688e19d7448ad2ce468e3884ff588987c12c4abe8af11bc6aea06ebd28403b1cecbb13504de291f31fa5e33b5b088113039ec531fa9055e2a28bdcf8e7434e938d4648020e2abbf61fc71489287d51bed57e2c149e27fffd2e40f0b4cdc16a13a96f1f7ceba9e3ed318ec63b68c4b2aef4ac6a7bfbe1aa45bcf3598a4d5fccba5d832d6d218beb12b1dc96692817e92a3e8ce1bdb0d49808c4771fb5ed7d90d28c9394bcc07d21e58d31116255a01b01d8e79948dbec98a020283dbf44b4812345968c49e529ec003d06188b653977328956de7e48d45046c3a64b8500d24977a1a08ccaadb1de227a1fa4e4863d91e26f4c1357b5686685798001a961d323c282f9dff003ca48e1b2b4961843b302ad70adc594352841a1037f1cdbf659a2f35db91e200b13d0aca4d4fca865324d1456ca657b565e4ace05032951de86a69e35a75cebbb332f06a207bcd7cde0fb53171609796ff00263372150f14505cec076033b92f1e1042d8d6a47263d6b82936bfeacdd788af87f1c516ff00ffd3895be9c90a19a51b0e80fed1cec80a78cb4aaf8998bb39e289f69bb01d87f4180a4308d26979e73bb89d3d38ecd2d5633df8b067353f339e5ded9cef5423dd10fa7fb0f8c7857fd22fa43469eda29a0884ea198109bd493d88ce3040be9b61ea1a42cb1dac9348ecea68b1903b035dabf764c4293b146cb791c2099aea283f9bd4600d3a8eb4c908144a718f342cbe6ad02d1a282f75548ea789ac8020af6df6f7cb86191708ea617cc2cd5adacef2249ecee1668d90b2b02181523ad41fa704b1ecc865ddf367e6ae9f14fa588e4479a15a88654e3514ad050d3bf4cbb466a4eb7b4a1c51795595fead63e5cd3e1b031c96d76e62734a49e83904bb22820f502b51439d676754b3c01ef0f03da7e9c333e45452cf7e9c98ee58e7a0bc2712352c42804ad58ee01fd78b1e256fd1afc797a7b75a77f9e1a4713fffd42abd56704b1e08bf7019da3c5b1a9addee18aa82225dd41fc598e448b4834f18f3cea179e516f316a3a701fa4f527b0b5d2e431b49495e261cb82f5e210d07cb3cdfda5c032768812e5c20be93ecaea0e2d099479f1101e7b07fcafab64b1d5aeb49b992c5cd62bd4b845e2c4ec590b020f5d88cd5cb1e922363bbbc8e5d648d9ba7dd9f94df993af6afa047a2ddd9473eb3660071c41905294efbfcfa66a72900edc9e8b486463eabb7937e6e5a79d756d6751823d64e92f2053cdc7129b542a8dc014a54d32dc39611de42dc5d660cb90d44d078168de4137f394d7ff385575390d23d3a37608c4034079952df303363f9b15e9c7b3a7fc84afd5905be85fcbab6f3af952f6cf498f5aff11796ae55a20a15a4e0f4dbd17662569dc1a0cc2d41c79058d8bb1d1f8d8488c8dc5e87f9856770de56d45ed918de47199a04db956bf6475eb5cc2c22b20761acdf093dcf2a87c8da8fe88d1af12632ea775224c2d21a8ac14e33b3ab6d4561f6aa33a3d1eb71e0cb19cf600878dd5f65e6d5e3963c42e521ee1f344b694f6b298a441ea0a1d886041150432d41046f519e8da5d4e3d4e319319b897cdf5ba3cda3cd2c39a3c338f31f8e8534b3d22499c512a6bd48e999422e199327fd051fa3e9d073eb5f7c970a2dffd542e2de4b87038f14076fedf7ced5e29b3a7704dc0441426bfc715b60fad7965759bbbbb74b68e5ba6861b8b0338aaacd11701d80f0af4cf31f6ce4716b227be03ef2fa87b0f8866d2ce35b89fe80f18d63f24f5ef3069b058dd79aafa3d661b8927bb9956e104cae54c71ac05c46813891c8722c09ae697076942028407c5e9f2f646599b333f07d45f903f9767cafa94292dd35c5d5adb48d2c92316620d28b527a023c7355a8ce32cb614eef47a696089e237b3d63ce5e47b4f35dc6ad67742302572e972c28d4200a071423e838233e12ce78864029e13ad7fce3868faadc7975f53b17b9b7f2dab4763f558e144746677613fa6a3d527d46de4a9a1a1db3610ed4cb014393ad9f60e29cb8ab77a4f927f2aad7ca6f34da78bbb0b577334c92dc174af5a2a11451b6c01f90198b29cb29b2e6c3471c31afb12df38a2dfdbdf43465594aa3509dd59d49a6fd4e578e5eb05ab531fdd1097e8fe68f2f795f5c85fcc56b710da6a16efa61be92dccb68ae9c88b791cb298d82c9cd6a0f2209ed991966493dc1afb3b0547ccee90e9de5c8ecbd6d3618e9069f7d7915930d81b63319210a3c007207b533d13d8d333a59dfd3c7b7c85fdaf9b7fc113c3fcf6331fa8e31c5f335f632d834e58142a2807c73ae78144fd55a95a7f5c55fffd6e85168f1429ea4ab403a57fcfae7696f12819b4ff55b93a708c7d84fe27df0ab19beb4fa96b3a7dcb8e10cd0322b9fe647e5fc73ce7dbac5fbcc33ef047c8dfe97d47fe07198039a1e60fde3f422fcdfe76f2cf94349b79a602ff59be223b5d3976ab36cbc986e6a77a0ce2461043ea1973f073d8334fcab8efaf67bed465883ccf0430068b645794834dfeedf2ac78ae5b319640237229d799357d4741b4d5b52896398c41d63b73c64964700d020af524532df0cdb5ca4046f97548bcb1f9b3a76abe969bad593e91aab2f248e4a059077e27a139310a0b8b531975dd99f9bb57b76d2ed9b4a725156b3a7813e3dce4f26f1d9c496422449783eb97333c838725731ac8a4034e63e214f0a1ca60384db8d967e20a425de9f279c3cbf0695ab1fd1da14777697bacde928d3dfbc3cdbd3877f83e26e2cec3c00193bdcf9b93a51c311e5c99bdbc6b22fd63d21119fe34886fc54fd95a9f014cf61ec3d29d368b1c0ec6acfbe5bbe15ed1eb46b3b432e406c5d0f7476fd08c100a722283be6d9d2287ab1fabc29b53ae2afffd7edb3405db937d95fb2b9d85bc4a87d4c37c720dbaa21fe386d5e69f99224b08341bc07821bc786424569cd2a2bff00039c67b698f8b06297748fda3f63dc7b0b9fc3d4e41df11f61fdaf967cd17365a66bd7de67f355d4889693259f97adbaaaa940f2cc391a0241a03e1f339c4e2de351e65f42d567919dcb9066be58f35eb52d95fdc7967cc5f528ae2860d35f84c246efe9fef146e06dbf5c80846ea40b918f26a0c6f1d10cdf478e6d26cceadafddcfe66d6640098209230d1d4fd90a5ca8f92d4e4fc48dd4435cf4fa8ae2c9cfdec697f343ca5e6ef345bf95eef489ad60959ad74ed62d837c1749dd240a054537a1dbbe39314a31e271c66b9805edf0c5a95869f169dac3faf2c5f0faa95f8878d074a8dce61715b9f391add8eeb32c51dbdb3150af29602bd001bfe18da00daca53e58d475449b4ff002eea7a7c4e9aa5ddccb697692163f51557993d442280d76dbc7377d81a5c7aad6c212dc0dcfc37f95baaf693b4a7a3ecd94a02a521c20f77175f7d3d6d6dc0f89ba0cf5d7c410975251485d80eb8aa415fdffb6157ffd0f437a35dc8fa33b078a68c5bf4dfb1c52c47cefe5e7f30796b50b181795ec605ce9e3b99a1f8956bfe50aafd39abed9d17e734b2c63eae63de3f5f2767d8daefc96aa190f2e47dc7f5737c6de7ff00f9d9b40f2f5dc36eb25d7962e435ec4ebf0b2d1969229eead4ebdf3ca305c78a2767d873cc641190dd2ef23f9cbf2f7cbb6f71ebf97afad4b4a6796c6d6281a1facc94e4784955ab7114f872e8e3cd2e66dcb8ebf4d08ef8e8f96df73d0e2fcd9d12eece9e59f243df5ecf2c714373792048e1a9fb6f1c21471da84838258a5d4ae4ed184c0e087cf7645e5ad38f98f54b7d6758bab31a968b1b1d3ecad018d6346146e0a4d3829f015ae5120600b5c65e2c85f37a1dc6a9f5dbd91a47fdddbaf29e40d43d295edfdb988e49367dcc0f59b9faf5db2db02e17f770a03b190d00a7b54e4c47a944cdec9c594b6f6fe7cf2de9f2dcc718b7d3ae238118806593d30a02d7aeca4e74bec87f8edff44bcafb73fe25188e920f5799c283e19ea41f256397730dcf87618509273fde72f7c925ffd1f4a328268b9d83c5b623ed4c8955291420a9fa3143e68fccbf214915f6a5aee8fc534ed66263af58f2e0a932efeb281d43fed01fb5bf7ce23da2ec61099d563e47ea1dc7bfe3d7cdeebd9beda3288d2e43bff09f2eef874f278ff97ff2d341be82f67d47519e66531abc7c841c11dbf767606aacac08dc77ce4ce4944d07b9c78a128dc8bd8bca1f969a4491ccda44660d3ed90c7e834ecdce540f542c42ad3a31dea4ed90f5c8eedc46388f4b37d66db4ff0025e8f7135aacbcae5a25b68e75028c5483b11515a547d393c98ee0e3e2c9593e0f22b9d663b2b4758c8373764bb2835af23f45280ed98d0c5c46cf20e599f08f7a71e58b5820b66d73529161822ff7915f63248d5f8a9d76fc709c73cd318f182647a06719c71c4ce6680ef40cba6b5e6b51f98e656fad5b281a4c27610a86e4588fe66207c86de39ea1d81d803b3b17164df24b9ff47cbf597cf3b7fb57f9426631fa072f3f3fd4f66faec7796d0dcc46b1ce81c0f03dc7d076cdf8784944c4d1e894c8a5ab5ef858a87d597c37c6d2ff00ffd2f502c7b74ceb6de2da6a20271e6a93dccd4a93dba0c90086257e8b7b1cd6f302f14ea52400d363e07064c31cb03098b12145962cb2c531389a20d87c85e61d4ac3499bcc7a25aa4b1c96775736eb50085950a98c814345ef43f46799eb7471d2e796227972bea3a3eada0d5e4d4e9e3900fa86f5dfd51ba37e6cc5a758da5bdf5dcd2b9f44ccdc981fdc1a832abb0562d4a0afd9077f0ca78601c80329f253f347e6fcbe6fbd85610f7515ba84b2b083f7813e1e2794941c893bd728ca6006e68399881076dca0ec259beb245e5b1bed4a50193488f61103d0cec2a147b753993d9bd8da9ed395621c301ce4797ed3ee61aded1c3a11fbc3723fc239fec7a8e9da65dcc61bbd52533cc001040a38c30aff002c69dbdc9dce7a6764f61e9bb363fbb173eb23cfe1dc1e1bb47b5736b4fa8d47a4472fda597fd592452a46fc7e13e14cd8c83af058adbcfadd8798204b3bfe7a54d13fd674a94068ea0d56543b3216248d8d0f5ca2702370d1934d0ca7b8f7bd063bd8585258cc0d4dcd7929f91fecca84fbf670b2e8724371b8f254a2d3d5e4be9f5e55db276e1d1ba7ffd3f5435107ebceb1e312d9e4ebf80c9008486e9aa48ee7c324c4b0bd77cd9e5ff2ca93aa5d86baa5534e83f7931f0a8e8bfecb212c823b756fc3a69e5e436ef7c51e77d665bff35eabe65b0b3290ea13acb3d803cdb8801410683e314afe19abed2ec98f68e1e5531c8fe83e4f61d8fad9682a24dc7afeb0f4bf2ceafe58d67458cdd59d94c2d54b2bcea82846c55830e4a47704573cb753a3cb8321c72044bb9f49c1a8c597189822901ebdbea131b2f2ce9d16936cb55bcd65221eabf8ac151f0ffadd7c00eb9daf60fb212c959757b47a43a9feb777b9e5bb5bda28e3071e9b9f597eafd6f4ad0747b1d32d82c56e236af291ceeec4ee59d8ee493d49cf458c238e22100044720393c44b21993291b27ab24524922806df0aff001c243150bfbf5b1b7569872927a8821fda929d4fb28ee7bf4191add54acb4ab925ef2e252ba85fd39c2c0158d0745da94207d1db29cb20766dc71a4df85c5b33c6d1f3f4e839a51c163d401d76cc4946dbc15debc54a7a5fbdebc7bd7c69d7f0caf81151bbaddfffd4f52487976fa33ad78b2c47cc7e64d0fcb501b8d67518ad390ac5013ca593d9231563f76266073678f14b21a88b7cd9e66fcdbd4355696df408ce93644f037afbdc38f6e3b20f91afbe4252246fb7deed70767463bcf73ddd1e51752722b2fafeb4cec5a795cd59be64ef831c074760680a639ae6a9a679634b5d6b5273e9f10b1c094324b3313c638c1eacdf80a93b0c13d6c34919e49f21f6f9073bb37b372f6867860c5f548fc00ea4fb9e1361f9a177fe284bcd65218b40bc6f4ef6c2d62a3470f41273dda4953ad4f5e9b0a67231f68465d6c7365847846dcb703befa91fd8fa96afd818e9f412c58724a5979f74491d2aea8f2ebdefd04f2f68ba7c5616b716b247750dc42935b5d44418e58a55e51c8846c43035cf468e41200c7917c5a71319112144736511d929a06a95afd9e9f7e264c294efeeecf49b67b89d4cbf17a56f6f1d39cf337d9892bdcd2ac7b0dce44c8a79253a558dc5e5ebeaba9b2cb7238f18d07eed08fb2883b2a76f13563be4252a14ce3166f6d1f125ebfbc704027b0ca4eeda36637e60bf8a3b8b3b18fe176606561fb353befef8d2414f38ff00a3fabdb8f1e741cb853ad7ae46917bbfffd513e6dfce8d4aee5b8b3f28c6b63640fa71ead32729e5ec5a346f850785413f2cea092796ce8b07678ab9f3ee7cff00a9497da8ea0d2dddd4f7979722b25d4ce5dd857bb376c947170ee5d8c62222a2282256dd3d3094aa47dff98f8e4bc3e2dca4943cd6509150b43d00009ad72e18a9812f923f3afcc336b9f985a3794ec651269fe4c8566d4e3570bcef6e9431415ad4c71d074d896ce3bb7f5318c384bdbfb11d9f9b51da11962e70df7e5eef8b0fd66f0e8905e3df5b065b5227ba961656569a31c63344247c2cc454120d7639cae08788444737dbfb5b530ecfc32cd96113088078a89249e845d8df6ee7d3ff00f3899f98daf5be9d17923ce328365795bff27dfccdbdafd72573f51989e89310648c93f0934e8c29ea1d9519e2c2232e5d3cbf1cdf97b5d93c7cd2c9544927c9f74dd4b0595acf757130b586da3796f2e24d8431a7da2dee3a01dce6d6dc2a79f58c971ac5daeb77b0bc28d58b40d35fed430b1af371fefc92819cfc97b602ca22de85690ac112a93c9fab93e3959648c0fc594034a763ef8816925e6baa3b5df98a389772f32a0ff557fdbc990905e97c23f4beabc87d8ad3fe16bf7e1e1d9ab8babfffd6e3541ca38c753b0f6af539d94237ea3c83864f455f4543b3815761c6a7c06002f72add140e35dd7ae1e21cd3c282d425d42dac2f2e34db3faf5fc71b7e8fb3242ac9311440ec7eca8620b1f0ae42522450480f891fcba3cbfe66d762bbd55f55d4dee81f326ac1a306e6f25a4d3ac11b51822b82b527e74ad33cdbda0981a830bbad8fbcbef7ff00034ecee0d28cb55299328ff9bb57c47de7b90de69b382f2d2dac6e2248a4be9a2b6faa8521f765666607627a9d8f6e995f62e333d40f207f50773ff049cf8f1f64c88e79251af713c521f031fb5f717957f276dc794a692e23167ab6aa8b2aa9d842aa8a90c5e34445553f2cf54c51003f334cef4f564b7d66ef48d134ef30cf1dc43a480f7b2a3167d42588816cd3d40feec0aff94d463d3240b1e0dd975942beaa338a7a2bccf80f6c0a53488f3ab740c6b5f618daaf404bb115f61960605865945cbcddc9854428d257de9d71e6c8ec13ff00d210fe9cf4bd414faafa35dbedfdba65bd58d7a5ffd7e3362ac6317128f8e4af11e033b6ca7f843831ef4cc47debbfe1949b3b360725bab1ab74eac7df01149b51bbb88eda19e79084b6b48de69e43d92352cc7ee07260080323d13106644473269f0bdadc4da85f5d6a1316592fee249aee7648a61125c3f2a2ad777a336e0d7dc67906b3378d9a533d49bf717ea5f66f47f97d363c51e6220c7fad1e63e3ff0014f45fcaff002bcde6afcd4f28daadb2fe8dd104bab6a0b22f5083e12cad5a124a814db7ce9bd95d2994e53eed9e13fe0c1da22f4fa789d8f164f771546be625f37e8bddb8e6b616c6b20a34c47ec8ec2bef9df13d1f0f03aa5c78cb72235f8a1b5ddbc0bff66054cc2b2467bbccd4afcbae1b6348e40523500d4f403df10a51718e6a5c1a311b8cb1ad8a58c6a9e62d4247e9141b1f639288dd6476625f5a8ffc51f5de27d3fac7a5c7fd8d39532ef247f0bfffd0e591c6ac76a0441c47d19d8d9f89712918446388140a07418f2f7a42ce487e053f33928c7a95bbe4f3afcdcd5d343f21eaaa84add6b2174db5140c6939a4ad4f68831fbb357db7aaf074b33dfb7cff0063bff66742755afc701d0dfcb97db4f93b4e83fba1e88125bc678c4f07158d187c324ae86a58993b8f0dcec33cacf3fc727ea1ece859023b5ef1feb0e71f8f5ff39f4cff00ce393adbea5e79d6cffa4decdf56d3b4a06ac481c8920900d390e87c33d2fd99c3e1e9013d777e79ff008256b46abb6b208fd300235ddb5c87fa626fcdf5aaa7d42df873f52f26f8aea6eb576dcfdd9d03c09dd116b071896abf1ca7930f7ed82d53458ea5481b47551fc4fd38ad372fc41029e3c6a08cb22184964d2bdabc53004c636900ee32da6b4b5947e97d4654e971688e1876a1a64e1cd12e4f36e127e9ef4781afafcbdbad72cea9fe1a7fffd1e669c38af1a0403e019d88f2e6e22a2fa5b927e2ee3271a4169387215a05ec309b487ccbf9fd2ea8fab795e2bab5583448fd6faa5d4c434525dc82841556a831a00457ad76e87390f6a4cf82000f477f9ff63e8fff0003e86239e44cbf79d06ff0f9979369fe829729496189f8f08fd44795b9121dcd18015e23b7cba9ce0c7e3dcfd03a2dcedb5904794fbbe3d7a7d56fae3fe71996c3f40dedcdc491fe9e9af25f534e0378428e31f27a702cca397c24d2a7bd73d6bb244469e1c3ca87dcfca1ed24b24bb4750720a91c93b1dc788edb3e8e4a990fa8486e679d6b9b22e903218425496615a7c03f564155e03d68053bf857241050ae4faabc47edef4f1cbc35157b8f4cc2dea00169f157c32618b1fb4e5f5ebba5781b74e35e9c2bb64e3cd8cb9316e10ff8b3fbef83d5ad789ad78f4c9f44ef4fffd9	image/jpeg	hussein_sm.jpg
237	\\x89504e470d0a1a0a0000000d494844520000016e0000005908060000001eaace2e0000001974455874536f6674776172650041646f626520496d616765526561647971c9653c0000032269545874584d4c3a636f6d2e61646f62652e786d7000000000003c3f787061636b657420626567696e3d22efbbbf222069643d2257354d304d7043656869487a7265537a4e54637a6b633964223f3e203c783a786d706d65746120786d6c6e733a783d2261646f62653a6e733a6d6574612f2220783a786d70746b3d2241646f626520584d5020436f726520352e332d633031312036362e3134353636312c20323031322f30322f30362d31343a35363a32372020202020202020223e203c7264663a52444620786d6c6e733a7264663d22687474703a2f2f7777772e77332e6f72672f313939392f30322f32322d7264662d73796e7461782d6e7323223e203c7264663a4465736372697074696f6e207264663a61626f75743d222220786d6c6e733a786d703d22687474703a2f2f6e732e61646f62652e636f6d2f7861702f312e302f2220786d6c6e733a786d704d4d3d22687474703a2f2f6e732e61646f62652e636f6d2f7861702f312e302f6d6d2f2220786d6c6e733a73745265663d22687474703a2f2f6e732e61646f62652e636f6d2f7861702f312e302f73547970652f5265736f75726365526566232220786d703a43726561746f72546f6f6c3d2241646f62652050686f746f73686f7020435336202857696e646f7773292220786d704d4d3a496e7374616e636549443d22786d702e6969643a39354439443737344133393031314533423833303938413630334235424346432220786d704d4d3a446f63756d656e7449443d22786d702e6469643a3935443944373735413339303131453342383330393841363033423542434643223e203c786d704d4d3a4465726976656446726f6d2073745265663a696e7374616e636549443d22786d702e6969643a3935443944373732413339303131453342383330393841363033423542434643222073745265663a646f63756d656e7449443d22786d702e6469643a3935443944373733413339303131453342383330393841363033423542434643222f3e203c2f7264663a4465736372697074696f6e3e203c2f7264663a5244463e203c2f783a786d706d6574613e203c3f787061636b657420656e643d2272223f3e9b8a653c00002d504944415478daec9d099454c5f5c6ef30ecfb0e8a0a888280111115f77d415113a3d168222aae688c464de2fa7727894634468d269aa8890bc65d2389c12d11082e1171df1111649155d961feef4bffdee99ac7ebeed73ddd333d3375cfa933d3dbebd755b7befbdd5bb76e555c3db68579492c4d82d694bf15415b1fb475415b9be3339707edc8a07d2f686ffb6e2ca9687c2ae9f72a677caab27ce680a0dd1cb46b837687ef422ff541c9bd64966641eb14b40e41db34687d82d637685d83d612e09e1bb469419b1ab4993100b167d0ce08daa21c00efa530e948ebced8a86d14b4b680f7b2a0bd13b42941fb3068cb239f6f1fb4f382b649d0bef1dde9c5917660c0ca18bdf1c05d86d213001814b4c18076aba0ad0ada0ad8762b0042c07c21007e45d0fee000b4c0e35200e4b8a0bdefbbb628d23a685b04ad7fd0b6c1a076605cbec6a06ac2750b5a8fa01d0f408b4d5f1cb4795c47ef3f1dc67d49d0eef75deb055cdc2e68c399c332e86f056d3220ee81bbcc6473807848d01443fa3868ff08daf4a0cdc9c2f84606edcca0dd0ac3fb0ba03026687b076d74d0fee3bbb7c6d29ef1d91e509e6fa9d093fafbd3a0ad89f98c8ce6ae00f4c95ce3584228db03e4e38376a3ef5e2f48f3a06d1cb419415b1ab42d837642d07609dab87260df153ec6fd3f51e8e34440f6f3a04d0cdadff21c205de34f30f5ad83b66dd0fe1db47b01f575be9b6b248707ed07900d8dcf9341fb2ccf6b9cc9c41b16b40f82f654d0b60ada6e960a7379f19249ba4110a433b76520091eb86b51b460f8535cebdf303885c6a205da7f0dda084226830185795eef0b16793f3f0bdaee41bb2f68b7c3ae0b95c782f680a5d622eec55bfa87ef662f0944c44e0bd84707ed131f2aa91bd9043759b1e7178376aed53c06fd19aefb8f19e4433d68d748c66054b5107c0a5e504d455e90e2978761a83d687b492ac2098551b6f7c05d37b25fd0ae239c7157d07e12b4c545b8ae58fbe600f6cd808497fca527cce6a8a0cd0adafe56bc34ca361881f72cb598ecc54b52d1a2f77c3ce9a6568759624d1a61e7ff30687703da8f07edac2281b64471b0a1964a3b1bebf5bc201918b487f084342ec5ce7d1fc6a453f865a9efee7a2749c8a6d2f84a1503565649fbbac6cec606dcc7586a712a5c3116687f5dc4eb1f845516935beee758ded2cf520bbcbbf2f8fca0bd5ec4eb2b3ca658f9fdde1baab7b2035ef2a941db0c2fb71240572eff8fd0192d445794e0fb951eb88c795ed6d6aba1887232af84156b61ea1a4b6590144b9447ac8d1c2f05ed693fbff21631a46b19278956efc717f1fa9ac43f65c2fdc27777bd955783b63a680706ed57968a392fb154be7e9826aab1be2068cf596a735c369d6bc2f592647d092f953df69ed5f166baa68d081414c7de82c75a5878a4c8df71390c604c03ecbf4a98c692127e87d23147f2bfc0f57756dc94ab7d2c15db16a3ffd4bcd457914ebc46d3462cad83281960482482d08dd7de01985de90173df02dd9e074bff20e6bdae6cc7dfffd67527349650895cef3d9dc7cf173944a2c5b3d382f666d05e6880fda705bd5196ca94298528267998a5e392738b3c393ac1cee456dfeeb1afc1c872c226c3f1a0a3c4e23b41eb1203bebfb5d4da893cefc500f865960acdb5cff27dc7e051cfaaeb1fde5818b736c4f4741ecfb3e2c5a83a07ed6a4b6d81bfdbb21733cae5ca579569ff2d034c5573452993b38b7c7d2d48f6771e2fb6e2ae115cc8847d1106e6a5e1b1f0ff0bda57ccc5b63c2f9dea65e95dcf9b00d0d28371916b6c0af8cb73fe694ce844af2bf1e0d222933ecfb873303a57be65c5dbc9a8c510652aa886c9c43c3f2b45da0fa53bb18cfb4f064519385f06eda21231fad6cee35e18c462885ce2e3318c8f9bdfc1da90e516c853280a836ce6fcbf17f3745ccc67b5de152e78fe300627c398f92be580a98d05b81746d8ace29d3b16e1baaa61702c4aa114c02ff2f8acee413bf8b47bef8a087095a3a8c0d6bf50eadd8b7c6db9b86e6a9edcd5938b705d8dcb3996ca36a822fc52655e1aaa68c1506b231f474881448b98ca5a7a35cbe757c2c81512e9e03c7f2af3f37796b9c8940acea950d9780cc3b723d7f0c05d804c8f80aa0653bbe636aee175b5c8d195ff675af295667dbf8a1aed0aa88825d4873ad0b3504615cd6a59c4eb6a95fe83c8734ad53ca4089e42c8b8c4b4669b97862eefc08c4359ed80fae218ef3b2aefa38b07f1f814888a3291e667f88c325c9e08da5596aabbaf4408ada56cee81bb6622f726bab5590b1acae71d5883eb8ac15d8b52f4b1e46b062712ae91bc891bb6b21ef4630bc0508b3edb15f1ba8a673f68a958bacbba7f8f4753a88439f5efc2983a9b97862ed2cfd79cf9142e582a0eae0a9dc398abd958fb7800f8e7960ab55d16432c7a40ba0e87786defbca632b097e28597442af7d9b751ac4fae8375ef1e61d9bdb196b399dcf9ca1a5cafe761a3d32c7b3a9144694a7f004496629d5fa927fdb8278a2ab6fdb615b754edfb4c84e1ce736247fb5a2a2b646a82be8d13a57efe0d8fe8bd2cacc94bc31179c107e3d9de60e9c5c9b9e8d4f9e845dc3e8e7e9005854b141afcb56d9845a21af02a78a6d216a30893b8e116798b7f2c505f134963da803303173fdcee1eca163ca762fa575bfe9b7214fb7dc15269424916be9473da97ffff6ca96a75c590b6dc4ba9ca4d8a6d0f8d3c2ea6e8deb50dbd151e4925cf7764a2ed09039a64f92f306aec7fe3f1ac5e4973746c59019f5dc63c980758bb6c5aa449c5e0ceb65462c14c18725b48d57a3ef734e18f05cee7b5c0adca9fb76660ed558454eeb212afa53416c66d8ec59d0858f771c0a1392ed4110cfa870558cb24e985fdb1e09d60ac275a718ecb1253557dea45057a0e496453805369954a87bad18a5f216d2dec58e332c4d2717485f49481f37d5c54fdc67cebcb54995f98ac2fa2b0d63818ede3057c5ebaf2033ce07b2105ee3cfd90f9f20ef36f3eff3f09997ac352a1ccd918fd10b48f02f833ad8d4db05a4a176c8c45a63e06a095b3f965e4352d64dd811b34b4047d7d3ee199d580e0fc225d575bf977b654ecb95432c4f154fe8c1b590ad1c4527ae4094c3cd720b68225c9f81e6de98c012f0d4be4fd6a07f24e91304452694f7b290b311298bf8561783468cf820de101e06bacfa02fc7e9095ae19aeb704265e2b659c9b3452c5501cea0a00fcc998c195a5ffbba57643b62dd277eee100ebad569cdad212c5808f652c7be7eb7125d40101e471fcff21f7bfaac463a40975a0c5c71815877c0056d6af1eeb61973a9a835de9db63acfac6b442d9716511efad23a42abc7621f7d79730cb4b56584d91b068d50a679c2ecf712f136851690b09d422a762e33a7149d92603f0c05b15d2498dfdccc9c980e968dace91f0838e28da1e90cfb4cdb5078ad21c0b2d856966e9aa655518062d4276e333bad691b85feb1d37be09ef9d62c9ea82842cbe1d4a7679c2b08ac04ed9343a0c790eee5fb612a752b8c300eb1b602ab521f3f04cfe06033bd8aa6f493ed5d227e494ca036803035b99c79c3a145d50a1ac6519c6409b8276c108feb308f7b8de019a5cc0f85bc24e5fa3ff5f16f09dc32123029fcf61a3c528937b2a7aa9dfa3acafd70bbcb737acf05db295188d7051f33b563d6b242a0a51de631b864b45a42e62ae37a7bf9732c7438fe0dc84e3e6813bc635bf0370389ed8d8d6ceebda08d2c7521b39e2ea42aba8fa9dbca78a09bed469021f6dd4090b5c3d80520c2164b28a7b580f902f04f47331b52d9838a1b151eadc7319de2f83b1270abd2b8626640f0b7013a76551e2f3b8a7470893d4b60894558655f525b42e30223249efc3803d50a4efeb8d8b3e0266348189994476a58f04a6fbdb86bb69f7c6d084bfe19f0502b7d61c7a1156d88a6bfc29c1e7b601b40d6073d3dc76e6b5951894b8333df55dda123e12d262e8f1e38065a19effb6e8f5d93c370d6fab10c6be3d3a5168d8a2356da6d367cdb3bcff15e6902bba071dd6b257847df77442351399ef9e71d740645d7f09802bf4708aa50bd428bef547d84054399566a802468a8f6b938f164017c39897c1c06f0768a76000e616788f9aa827f05dad7177dba160bfcee0321e896bb6cad235475ccf6286653f287777424afa6d9758ddd56990517c10e37404e333cce997db00cb3b6bf01d02419d11da09600d8dedf3093fdf0ee0d17d68e1767e040c9451a43ad19bf0dc33417b38cf7bdc8f31e9e880b625f4382a2d5deb5cf2a2a5b3a8c660fc74ddce7819d1f20647e1015559f59dbe1f5961872d8ba8fc907baa22eca7853fc597959df15501d73c1812f49415b6185d8147fab5e365650b97ae0033dc648623b8ff2db37c4ef3f52f852a6a4301ee5674765f06be83a5378bac86f9ce01a43eb1ec2946da10732103af7ccca3795e5be4b5e575148a6a0e63bd2dcbf54e86c1afc730140adaa301e1e90cf86e96de9cf2cb4828a71dc0a6f7fc0726fa1edfbd0913a41df1bfe7b258fd1628e03ac230ef96c1582fc0103e8b77740e60d381c9b00c80cf473ac18efa726d8def310e2b4dcae405a8e16ecfdb19ab90b15d06a8b474c24057e6a10f1db8c7feb0e18ff04042e0bc2fc1355a380c50e19127f9ff320054b1e59761ee03229fbd14400ad767eec0b8ad83d42ccab3cf77c650e8734abb5b8e7e875ec8a305e280f619fcbd066192e678b26e88265b298bb50eb3df88d0c749e85436d909cf4b5efcc7e8f5624b1893afcfc03d9849b227cca83d96b1a5a5177cc210c41a2ce3d780d4bbb0df676cc31d51a14c62f0a5a45730a97746b94759b2c5b9ad50ce360e5bcc573461c7a114d731b1a45c17f03b5f8d51f26618aa1b78ffaa487cb5d2018f7bb37cf72928d8dd56bd784f3988c04a79f7ff00540e06c0afc638bf9af03a62ed773161c4862743022ad19d89962c761b1a4bf5fd6bf46b15e03a0660dd98fe5c0b384e4a788fdbd2ff1aef33081bf5b3f4092fff48788f3d1cc63d95eb9c4778648c13d699ed1081f0808b1d78cf14fa260c93bc9d83b8c4c9f97ce76dcc2fe9ea58747d1563584831b083080b5e62851713eb4698e3c648a8ee4b8b5f9c6c8367224f68282dc9c93bfb825d8b201b2b01eef9782f9318d7c50d01b83bc284be8be2cc270ef73ac0dc92c9d10f60ef829235c3b5eb42fc722896f92294f70f165fd96f11f1ca9709877c9bef9ec867b289bef378988bc226bf2f20cc300836f3218c3b5c44fab11323bdc9365c5cd260ff3583f5dec989d74dc1c38893cd99006f13d35c5386fab00e8fe28700da65b8a7e7c1c673e5d61f88f7b20c207999e78f064496e6c1deb7b5f44110e36169170094173246e139a46f58f23349f705f457a0fb5378fe48f4794d428fa002b62da0f986cf1c845e9de5e87f5348ca03e8c92fd1c3632d9dd37c98a5eb705c67c9f722b4435f87305e53316e07c2529bd177530bd085cdb847859edeaf814e9d8e1e7c1509293d4d5f4545c6f45b962e61916fc4a39ba59316dcb0e0aedc47bd05ee2658ba9318e09980e62400718d035015bcbf12576528acfc5080be69c4ad6a45dcee10dc2b8503de8a898dbdcfa05dc75f4da0c72c7b1eb698dc69fcffa0e57fc6a116b1ee21be7aaa136fdb0566d804d07a21e6b3ebb380d6aef4c3d7004255863eff255eccf7acfcb7892fc2b07e810bbf3fa0f4b71cb1d0fb01a7310e6857f05a05e39e645b7f0bf4a219a0f324c0dc93b19a497f76a3dfaf4dc890f7c700ebba3f71405bf77600cf4f4b08744d9cd0ca5b84102f82dd4e8c78b2edd1d79ff1f85847072a2c5d3defa53c0cdb4690a05e007f58c7a33dc4a03b86f826cbbf56bee67b78b8f46de87725f37b63c6a12b86ae95334756311e4bf03206303f0ee71aeb981f6b08c3c9e31e580bfa5c81c73897715b1f796dd37207ee8e30a75128db194edc309784d5e034792f86459d4af8225ad9ae354c7a3893386e6163212ef940067757cbbc5dbd2da0dd89d8d503967c2766058a7d0f13e33407b47bc0de7af158c09bcf16fd36965ed0fbd4329f8d7932ecf102ab3f87eaae853d6f4668eb007e5f9c61da8550453b5ce2872271ea814eac3597cbdd0a56fc7d26f8078c99f4ef47c46e7b5a7aade49f09c16e07744000794b2486bd3b2c583221813714dee3de80d55cbcc1a7315eae7c8750e208dcffa322867b175ab8669344affbc1a4bb41a23e7400f77ceecdb89f424e3e1ac17cd4fcdc0e1def65e92df3dfe0f1cc605caae89336604c6f98f65042653fe5fd61fc79097d720706b9452de8f2fe90b24708e5cd03b7e4f5f72967e0d6005c89e25e58607cd805dddf11761073d7ca7fff98f769b07fc3a0c5157e9a0d5b7a8441ce04dc3b5a7ac3ca53965f8ef17719a82f00d0158e929f64e923d83eb1e4d90e6e98248cd33d6ef1b9c95ba19c7afdd67a164a5b4bdf8d467f3ad9860baf7df0d8ba138bfe6de4f56398cc6b309cd958de6678819730a9d6131f55bfddecbc37cc24598651c925bdd0d59e1096eb23af1fe98ce3a41c2c5bbf771fd8734b0cc92058f62f62c27b871026f83e44675ee4378fe6fe64809e4df05bfa62a8fa63045ce275085ec05a18ee5d967f95cc6fd13f32067bf0fbde437fe72460ef4d9853fb40125fe05ebfc5f58ec2a37b8edf3bd3b2678b1443c270555f0cf8f72025db433646952b701f0b784c40498b55f27415934a41ff6be89026318a763a13262e31febf80e6e659d8cd3928f922d876d2dd5b0730e1d703da739cd78ec0fd9f06e39a60f9978d1c8137f04d06d6d70e56f125c66dadd53f99459cf30818dec24848e306806b0dbff5d3c8eb3ba1136b2cf3c6880118e7be30f2d0fd96db3dceaad7560f63af4d61f9b9729d9be3056c4dffdf1eb9c7964ce65032cd8dfe4c745de71dc77368cdf85f13f399918446e603621fc4782a23d0cf7109e6a5c2237763442fb5ea3b0b87f11df3306af360bbf9482f40bb15cc7f2d2c791e736f7d0ec33b140f636748e28bbcf61e2d0c53ed61e9edf7ddeb88c46e874e6aee2f2e47e03e1926302e8611144b3e26fc3213572dba0a7c18cc3b2e2c330f506f9585d51ecaff2f3aca908439dc0493ba34a2c4bbc2b60548e75afa349a7cb69d77c1f5ad043ce2763f5e04a88db0fa5bfe5493f523c0b7658c6e8565075eb10d3713b5b4f4c26d33c07f321346b1d8217824fdf0be6e22f6d919103bcf363c10e327b8e20b311ab9e4444ba7c5bd611b6eaa696dd537837c17d6bd927bfc16a19e2d08895c0fa3ece78410afb40d7702372514d90a0fe2a51842722a31e37f59ee8c980ecca1dd09b75de7bcd6133d7e8f70455b62d3f9a414b6011f348e3fc7500d86748527b77f4828f12bbc9df5dcd7c6f4d336844f8eb3cc876cac81693fcb3d3f4d1f6e8d2eb4af059dfe80df313534fce506dc4731d8afe3369552c2424fcd60c85166be3a8b3bbe2243ecb492896ab86c8f27f416ba60a806c0e8ef70d88226dc5528b618dea60cdeb43c7eab26dde596de11fa78ccfd2b763686be7839c7f5f642779eb5f2acb8b71cf6ebc67e37215c108efd1f6dc36c8825b0d34100ff5930e6193c5ec7ff4af57b93b0ca497cf6cf31a0bd2d5e5d259ede8c1cf7bd09f1d5d000fd3ee61e17728fe1411667708f9f4040748fcab4ba1f3da974f45cf204a1bea81c4e5cf5168bdf1d7b9043486eb5dc1952e763f856f1fdab1ce328c07d1f1d3e93e727e64944c632b72ee6f1eb8ed7b403467673fe563aba50491f7dce35a6e660e543f00efa5bbaf4f02c4250c3f8ae6118821e25d0e38708cfee0e36ac2c37e0de9f1b7c07d7b036ce6014e85c06601ee48076980b1c272d697149f9c39cebbc6bc94a52364579f763b2de6ce9f4bece4c922708ef841ec0f488fb9c4d5a63100fe2bba4c07f8fbc674b0cc79d967b4b7b4fc0fd69cbff70e4da920e009e9b4a75b6a58f3113d31b1ff339815b475cfa6696ae32b70e06fa9ad3ef832c9dd2275db82266d2ff18777e8125cb833fd3d2b5da3fb20d170e4360ea488cb98adfda967b9cec84f25c32b40dffcf47dfa3a28c8b0b08335c9965cc3b587a0f4436833d92fe6e42b8c105c72b2012bf828577a64f3fc8637ccf81d01c9f211cfa92e331742764d606c3b60cd0ceb6096f533c9fddf9ed5fe1393fe0782a33680ff39efe10a36118d541967d9b7c2ec07e82f19fc06fe8e4624eb900b7ac9a168c9ee2f16c2b6c0b6d21b29478df8e30dff196fdf48ace28425c9ad8a9f469b8233189ebb7a3c3d2c51a5ee0ffb6b8c9d389791ee100cf344b9657dd92f86a5bc0a31f86f1e308c8ddc0c4b9cc7267511c06a83d59c6e1924d01ed7061ad071e85f1fb1ecdc018cfe0b78dc1ab6ac604ff2ac6d85e65e962fad7c718f2039c90c77d090cad74ef604bafb9fc3503b89c8a31391d0f212c5eb42083d13edd0919dd6af139ce63009ba36dc3fa1e4d00b1dd78fcb0653fbbb317acb41de0ee7a8f97006c27a0cb21839f62c96b761c894e9f64c9b6c4cfb3fc6a967c8f909a3ef30c46fefd1c86e54bdabfe8f34dd0c1adf1ba062508abac665e3f07499be6108f23313673cb09b87b635926121e11db7dcc6a77c3c77f701fb5f3ed41cb9ee21416687a3b46614345fc2a21db6e67e92ddb46dcf053d8c19f614e97c1b2c2add40b50a624a07d3a833dcbd2c5dfa7382e695326592f18792ef7578aa7d42de598ce2953d0ee0a1178dfd28babbae78d1c431dc764b7040caeb4ea250d3231e3c3f8ff959810496774a91d7d7d7f0283788ca5b7998739f651190088de60c9ca0f68f16f27fe7fc7aaef060c653762b7d32c7e3d66283a5ac1bc78d1b22f5a0bfcc3dd996f3a86e222426c27a18fa758ba0cf11b962caffd003cc3732cf3c6b19a883c851178526f5961b5529603f21f104a6c0739ea88a1ef03836e4bd8632186f043e6d4dc08f66d4e3f4d72fbbdae81bb330afa05aed52840ea8d5abe8f7558ba9d2cbe229a2b7bc0c6a6c6c488c3c368e758b27294db135b0c652d2ed73826dad5284258dd2f74cb739d3cd31116b50483f4734b1736fac001117db716eb7e68c94a7b0e4291ee2f63b6bd15f1c63f3acfed60e94317a665604f72e19fc745cd263b130209e7ce6531cc786fc7d04eb4ecd93f728147335e617ef07f633ea3d08bd2625fb6643b25fbc2ce4377fd2adb70175e4f62c4d297db629869330c547727fc97697eb4610e9f6fe9920a2bd1dd73609f27c21c5bc2f02b9cebe65a2b1129ba072ff4f112e84d0f40fbff2c7b6d927c65196d16c6a0c2d21b84c2431bb219f503f1dca745dda0ba921630ece6c4e1be0138e759dd6434bc8511599ee53dfd9864ff8ab9c7039dc93c3bc775daa2ccb7468ce759844a9e4181963adf1bbae58b73b87e1b33c1e7127eaa006c2a1cd61c161bba002694b4e6f1e6b8821f59794a734beff2fb67247412fefea9119068421f08787e9dc3db6a053885a9a077db863b57dbc2dcc2c5c07f67606ecd88a15e4b58d02502532293b982ef1508fe2281375a81bb1f6eb67aca0943babffb4cc06a2aee79558c47b88df3784944af9bf29e63d1e521117dde1186de1552f6b96330b671186a3666db1a62712b86e00e2bbc0e492ec3f0568e3050586b7d7b4b568f244ec29d982bd1b5753988d28e51b65d978cbb0516be3feecf22627c9b59dd55a07b8bf047a60169028311438a9663942b34d879bc264b1c731798c71b00ebf58e024f277411ddadd8dfb9af2659147c382ef713ce441d6ce9052fc94f60ef83300ef9b097deb08405650adcbb0258632360e0eaf99cc8f3c7e1455d60b977a18eb4743d125de757b661ae771f4bc7832d26fcd482d0d4b17841bf41f7be13b9c7f5ce3d1e05c05e98c0230c27fc21e8cc4afae3eb98b1dc1b3096f19811739d1511e63f9cfb780616be37c6470cf57298fb9e80f91a9ebf0e3d5f13f1b4fb3a46264e2af19e8eb0746cf891129286ed2c3edbca0d3d9e8481df85fe2ae53c684938698ec56c3eac0be00e17760e20ae3ad3019e2e56c223ed135ac3aa2c16f96778095362dc44f7fcc36d008330e4b3118fbfcdf5c731185d51ce6e965e455e196330368ab8c09a40139cefdedcd28b97d75bf5f87b73c7755dca64dc8bc9fcfb02425bebac3c37e6f406043f8e84492c121a1980ae750484f707b493a457f68451ebf75f9a816474b3ea27801f84215e0a0069d26bc14aeb2ad738fae686bf06f23dedf8fcc17805af25ec8b2e965ed31867f1dbc8e5ba5f8981c8b40352ba7213d7da857e13e33f0dafef1d4245d31c8f642cfd3a05c0fe2283c7d9c9f9cc0ef4c77a74b537e3b20763b90546eea512e243a813ddf1d8a238701273498628dc1effcf12dd4b5bf46b1bbc0d2b07e03e0fc679b46db8c1a5a915ef8cc7628a80ee4ed84edc81050b70b17a3ae03a81f8662560b102d7fa26877d2c80c5e7127707d826c4637b5a7af7d716c4674f8b61fb9aecbf848d3e4f4867358a91af34b1f4e920e524326cca3f566cfb47b66146d23db043b9b8639cd8a2fae15ccb7c2c5d543ec43352fd9b07b380dd37965e701e0950bf8a17f02acc399a7124a3bd1fe1c2d3b8c7d58ce7b9965f96958054f9bf5a70557a695c7ef41a98732e990578f4b37456d35718ad351980fea604a11cf79cca0be8af4f305a5bc1342f0244afe1bb3e2b503f5a59eee3c1c218fda631737c08e1c6b100fa0a886729805bbfff12747994654802689a218cb1de4a93d5311aa53d2386feaf267ebb29f7b0aa4c40e110e26b5d00bbb8e3cb56e336df028b5e07906f02e3d06afe9fadb033f9aa62d8e00e3429b2d2f27e6a998b6fadc3e8dc09e3ef82e12c64e3cc62d8d7c6567be99ab94446eb0600f2890c5ec45bb02491851ee8f704e2baf98862c13f864167ca037e0fd03c99c7cff3fe30d52bd359a2ef302f8ec610ad01180a39545a7a764e91bda38fad7a1a694d6416218f818e277339faf51c2c3b0cf58dc0e07e5920265410be596d99eb1d89904c86042d8c796d14bf3dcc90990441e86819caae16200a876a21f8048cfab196e56cd7a6311f56b195c7acb0034473c5076fc0b58a4b755a44e71c448ceef53a0684964ca48b003bb97db76779ff8358e2f388974d8679bc56c3fba842512ec17d5c4f7c6d2a8a98b45ec9de4c8e336b30b61fc34c865bb272a7a596fd71f777c2a09e9f8570bc5684b1586cb98f465b0c4394d7b52f8071bf254be17cbd887a5f2ec4274e3e655e2986db0192f32a062e1a861c6cd5773e16327fe60088232158f362c03d34fed1f35e15aed81aaf759de3797dc2389f59e07d35c383190c7bdf8550cc5f217a590f0b8f02b756a155fbe0e1220f9426d65d30d21b33bc67354a2bb74c95c9deb0fcebf2164bd4893fa12f64711fc79dcb753f4fc2ac7603bca52c87c38cdf80151752306b394a129e243d27cfbe91725c4b7c6e720dfae523144a6c52296973eb687cbae3d68e86ad7d4178e1c33201a619783567735f32b24ae35336d22b80f8e7569aec88fa20eb21878a59b7251cf24d86104758c5b39365ae0f944b34f77e8797d58f70d99c88572a094fd172e5208c71b40c8408e844e6f4ed05861d2f807c3c06c9d3dc4cb483b4729f7dab61f7c100cd4356bc943c75bc76aabd48ac6e5d0e43321246f75e4296524ce907b3bd14f0aec0fd1e93477f2cc5c5990e935845c844d73b1450ef02eb5d91e7fd2dc545cf27cca1efba0d3673530dfbe76b0cd2ee7824136bd9b886858e7e85716f4bc8e604cbaf746e6d88c64a8b7e7fb7746d8b03b9ef51b4c370cfbb30ae4b1a19802f071433b1e94e840c06a06ff2300bdd78a3b87c57f46785c597446e4548ed3e872084876cbc1079ef023c87f0c8b5e8eb27f25def59fc0eeab05e4a0b4b173c4bbce1c7456db9c05b72b3875b7c2c375f111bba17b7e28c04eedbcb28fa28ac58535c8752cb303afa60ab9e3af70740bc106639d7f99c00a633adbfe3d9a85fee2ea181aa80a92fc1d5ab6941a8257863237173d7c21a4aed964b278fa169d1a6b513fe1863f1b5d3cb41d639211ac5de9571b2313a36085d3898092ec6f92e9ecc5f2cfb3e80c622cdac7ab6d68ee8dfca02e742b808bd3361896886ca74de773fbaa645d21e96f93091472174576208aec2b8b4c62b7d15fd5c86117fd331ce15007a577443baf260d2395a71f5d816616d0d29d1d3b04e21fff72cff42fdae744609658d0ec983b1ee06986dce0f16788eb3e2ee663258b056f18f2494d3d9d239a5ead0cb2d15cffca6c8df5b814bd60906fe6dfa7d5c09145f0b694700b21f14e99a6d09d91c8ede4c44612717f9dedb302e471003ec65d5cbb44a47fecfca6791b410506ac124ef6ee902657bc3422fb3e4a7cb375411f1bb0b4fc5203a8758617b3dc2a25fe300c8ef670049c59d958c10966fde8df7664b43ec6ae9da3493c14f118c7043d75e96caeada08325ac5b8b7e2374d006b1726069100b8afb5f4313e2746d8af986f21076f6e4c87b767e27d9127b09dc28f6e872b3e1316f21000e49e3399c4aba864d2f72364b11f56b793555f8c588dcb7381d54e8cbd1203321645f999152f9b478aa49cdb8b6dc36a803595218cefb63c96ebf93880fe1f8cddda84fdd784316a8a626fc7f8ec4978a1b96350d7a3e86238e3ad6e73fe4b21cd3150d27fe50dff098f6f8d354e090f173ec179ee62bcc77ce7a68ca44e3b3a1e5c1b9f839ca8cecb79844d2e4f885bad2162a7729f931c1d6fce75bb83b7cb98f3cb0bd16301b7d26d1eb3f873d49e21c491340d48373f1c8ba530c17156f8eea2b350da6e11607d950e7993fb5ac88f5fcbf7871d24505622bfd2c5b6a6f5b3eaf9a3a12876ab85adebb1c6b53d51f41bafc5c2df68355fb41a80b7f090655e0caea9689c6f73c03b142d60fe8bf080fe9f43ffae7298664b3cb15e8cc9408cc1408bcf11d778ccc685bdd1ea6e51b4366537c6502981e73640239554ce8ee8f002c24bf986c7fa40469566383a21e1dbdbd2758cf291cdc0a5af4bd52902eeb056c35519de3315cb333507cbed85aba06c8c27f84c4d01f010d8d5d6b6619a4e2862774b51ecf0d493f696fb404f59ec2f896bdd6bb9ab02965a94abaad422edfe9b5683eb84c78fad8830955288c2598aa1cbf877ccf09e3596ae7151c5b8b4b5641bad1432f80c367f673d0e8b142a5ba3977fb4f88d5f8d41948ef7a4a537ff18c44dd96733125ea30bc448218d63ac01ac1f28ab44934969783b5afc398a72e50f2794b204700b415293753080ad8d353b6100ae2d529841619147f8de0edc43cb18f7b21df712be27d38e50b1becf01c67b71b97e0178d7756a9618e5507e5f4d80fb678483b41abfb2c4f7acb580879940ad60cbed63c241ad2d5ddab2ad652f303f97f0dcdf706d2f21d4b3a41182965ce94f19d3c956fcbd15f541e6a22ffb3a213381b81677b5953fd7dad900c8a4f4d03d7cbb5e8b1877f8ff1098f26659deff250af405cca91fa108c56b1e251e55aac22b8a0d6973d0aeb8d41b61493b702f6e1c74156eca12c0653e6115ed4e0b8ffd2ac7013c03e02e74a1528b5b5aa1d6c2726d6f90d1c4505c5aa982c3f0c0ba593aff360c5185d5d196e329697cbe824dbf47b8e8b5460a5271224f732c7d39daea6e6f435d4a473cbb3322cf4f67ae2863c32d5bd004c01e06211501bcb92175880bdc0673be0330cc2502eb172c15077fd48a9ff5914d3a61607a4480a10ae6bc02505808681792335d17722e13f337967feadea68c875ceadfd5f1ef1043ea8d71ed66d50fb80d6b842c2314b20056b5d0bc6412ad27a83ecd45165f30aa3148580a59c6ab6fe4359194b7d1a7e6e041b851ed192b7ec6539d4b34a4f03040f86b8b3f66472bfa53e988a996fc08ad52b8e88b1ad858b4c718fddb0acbb7d642e1ab6500da06307f68e5b393b1becb743cc63d1b3170cb83d6c122aa2fa3f4c0edf1f6e5750fe6ff9978d52fd34f531aaa871205eef08c3829c9c174861892628eafe072ccb2c6196f2cc4c51d418827f4041661f0e2b222f6e13385e4ce2b7d5179f8bbf96e6f90b20246b9a5a5cf346dacf20aad2ba0ddd2210be1d99b2b1a7a27c42de2096494fea295db364e58648d9f3f896533dcba7750b215f4651f4be5e70ac0c73be101ad15a890fe8305840c94b2a4c5ab1f58e348936baca2c56b659968a3d83cdf1dff03e8058df5c767abc7bdcacabbc258b98ae2b9c701a2f746acbf58d30b96cabe114bbe8f09790900ff4c9edfa549ac05e17b0af8ac97fa172aa8b2ea5bc0bd78e0f65224d122a956c1a7c7b86c612ae504405cf5589a01f002df7c5212b572ae52948ae15d678db7d25c63920adf055e3c7097461456d2024ab8e354eb03cb01d6e63072655c1ccafbb5e0fb4001c02b56afb43f15deffc2777b839796e8c84adf155e3c70974626e2d62ae15f2949da44a135022d542abf59a94dcaccd12af95e80703ee73f6ad1f84af32192c6249df0ae16fbaef0e281bb74f22c6d38402ba63d03a07dc379df0cde93f4b836bdef17fcfd95f9758886c0a47bf337dce21f279bf2fa0adf655e3c70975ea6d232c93a26edc630f35c7294a5364a8d49f87e2fe52bdae9ba076c3aac842943fe12063ecce757da9b328fa6f82ef3e281bb7c1897d20793d4fdee63a95a309ad8f7fbaeabd7f34ee1319500554d96f731e0aa0ba46a8b67619cb5f8ac94d28118760fdc5e3c709789ec0f78e72a96a385cd0b99dc3a61da6f82aabf3214cf29acfbeeca785e579130ed86bd06300f77057af1e281bb8e453b1d55caf574cbbdcd7d2f4bd56978c8fc82647d96e68cfb3b31a01d8aaa75aad8964e5e51f90915e11a63353f7ace4b039126be0b4a2a5a50da22c36b3aa1e6265aae42edca0b57aeb6765cde628db7a87e4310e5edabf0d69c1cef5316d22300bdb29166f9aef3e21977edc83a007a0f58d442c05c754974269dce4c9c908349c9b8ea282b159457fadf24dfadf55a56a307031230f38bd02195bb5558e562df7d5e243a48c1f742e944db94df64f289792b33a013cfdd8cbb9ccbfded07602f63f27ee6bbb55ecb7a5ab841eb9398f728c34487052b54a253a0548553e7c1aadae2e7be0bbd78d42ead0894b5d3edbf5658394e6d71be04b0d72117fff65dda2044e57755a7464792294b68227aa2308a0a4929ab44e111ed8cfd00c09e0a8837d852a55e3c703714d1693f3a236f05acdb4bc3109565d5262a55f9d3e2b442618b61daed0072557b5ce01080172c75cea28e17fcc877a1076e2fe5292a56a598a6629def5a2a16eea561890e40becb526b204af3d4a94d3acd25ae3caf3c36854e067be0f6e281bb7c45a77cec0ddb7aca1ac0c9d45e6245eb1f490ecf980b2bdfc47799179f0e589ea2d8f6d996aa47a22c84c77c9778b154c8aca7f9f2ae9e71fb2e284b51bae08efcff9935de7306bd54976618f30af39b713ce3f65276727cd03af0bfb2097c0d662f926e1eb4bd78e02e4f190cdb0edde1577d9778b15485c0be968a757be0f6c0eda5cc44752c7a3b8fdff55de22590ed82b691a56a9878f1c0eda5cc445be15bf2bff27de7fb2e69f422ef4b47dd7d69a99393bc78e0f65266e3d1cb79bcdc7c1a606311e5ebb7c8f0dabe00b78eb7fbca7795170fdce525ca1a68e53cd6d6667f7a7be390ae96dad2ae6262ad794e9ed72e41bbd452b9fc7ef7ac97ff894f072c2fd1f9914b9dc76d68b5259d2d5517a519f731db0f49adc96c8cb4b6baab84abb6bb2ba6adb207da027fb5ef222f1eb8cb57deb6d4b1551500a8aa0a4e2fd177295361aba00db2546c5d75307a612c144f7dda52550cbff6c3522ba28d565a7c1c0e03ff1ac07edd778d170fdce52d3aba4a253cc3cc1255887ba488d7ef0930287b65a7a00db15461a350960318aa1bbe7bd0da5aaa42a197da918fccd722f192432aae1edbc2f742f98980fb7618b740f468d86fa1a28366b51b7324403d90e7552e54bb3255175c27c6cfe1fb14b291629c1cb43d83b6a51f122f5e3ce3f6925dee013cafb4d4410aca2638c752e74d26151d38abd377f6b3d4692b8a5fab8ca84e88bf05c016502b4b6159866b888977f7c3e1c58b67dc5e928b62cea369da02af5d947f057c05bada0aaff2afed2d155a11586f0bab1650ab9a9c62a69300ea993c9734c550619aaa3c0d86172f5e3c70377a51b8448b88aad9bc3f0c3cac1a28005ec77b74b8ec124b1d85a5a3d1de05dc5700f0856c936ecb67d7fa61f0e2a57ce4ff05180024241c3aa7d1db010000000049454e44ae426082	image/png	logo.png
250	\\x89504e470d0a1a0a0000000d49484452000000fe000000d20802000000e59aea4b0000001974455874536f6674776172650041646f626520496d616765526561647971c9653c000057f54944415478daecbd07805cc5952e7cabeeed091a6934ca39a02c1410410824443622198c011b30188cdf3edbcf3c6fb437bcc5de5d7bd7cff65bdb6b7b1d767f836d0cb6112258604020844008a18412ca12ca9a9146a39134b1fbdeaaff3b757aae2eb72774b8dd4a5dc8e39eee9eaa5b55277ce7d4a97384abbd632dc73d4b59c5566ce746ebee742bb7cb84d2faa2f9733c05daf7a4282e4bb19dfded8b631ff8d284fb1da141f44a4b2df05f71558aed1c684a284bd80e5e0921a54a785a0a595c96623b079a20b2772c6169ad41f7925e14114fb19d03946f7986f42d4b5a003b16fe758478c01bf8127d43bb96b4a5322f2d617a21a4a4a4d678535ab6a7b57444864633bac09f4b21cc584a08917c423ca28e59c233e360584f48a52cdb8e08997942db5a78c292da4a323e1e1ce3603e3686a239094284f8067e15994b066d968efe0c7f8e5e4df79e9236666c6b89e9695acfb8455f82188ac6d980f55424d94442ab1801dae433d04e61405a5e9a2c3d9ba65fda163c7dc4a03d9bd649f8fd518fd48fb25c5081c63f7c401215c490b02c3b1a926d230ec2e634013c81b40ce9e091b0535a487c825d55b4b4764774c8cb9116c4c16802344d24ee6822400c6c7bc2b56889f13686c198662d30471dcf82408cce51cc01cc879a66089af45c4c4eda7802ac315e812223e37e4d6b28312143061e75ec6108335f17fb474ce1095a6da5b33085f0a4bc62821848629d488058b634e4e9813a313f95204e10d2c57a46362f617b90112ee83e01e2c0843ca21532ea6c4582465bccd6f83063ba273ec67cc0c0867eccf209ea4b19922a31fc85cd22eef2ec84d24e54f3f24808d256d0046892527a1e8f4e3c201cfec01371902218bf8b49a437246800b3f22073255e10737bb6a69188383057a21d7c4562853d3be3a94a4bb4f198482a195fc04acbc1c75e8278ccc8464b88e8149ff40c3d90a860a90b190c89859514b6a1776ddb44c1867645e6fdfb7ca549c44b6672ea9814aef4e2442c468b6ae55891595a24356cc3d7988626a16524a061024fd2ae1959238d76c882a54d0f460619294b7a0cc421483f0be54a4546236684ffb75d29a3f39e485d868108005824d03133e54869588d308cd68275b4b015c9601901e96322803ad4afc02cdd248d0a230f41371845d1fae2259e29e665b394dca73254429d13f59309022a31031aab9c21878e4eea2b339628f1da70828d0dd3ad49990ddd0d71498bad1910653e00ed9090867bb420b04844e192c8d2da55a4a269ee4a91f8b5a274b0f9e08c9714321afbc5e884d4b3111f4219c19d8d8167881e93532c160cbc51840694b4123a6184af43b8d47c29ba6935c9a4fca39e350162cf202d8f309520e5262d96525e977da525a109a2429b286f748fd1e37a8e7979ffebd208795208b6b8b8c7948a926e4b0e2d35a46a107fa69b48c0c6c812d2a4341c48a4a2a2c7f5555782d3b1747893e09be3bd7374c5a1a64351110900abed591f1b7ce5a2c34be36e2ba94c19bb6dc86dcfec7b8e3fb46c6776d5f42547970087d9596056a18d082248e3902c6c4d68e7f6c137c608bfc50ccf7b985793d532ffe02b8e158b4c9d19f981e9d8b2f48e4173b48c6b0570804df3de38f8ee515deb78c05736ada9eb292763db891495eb01b81919ab857192282b31ac7ce42747dc5ce154ec39be6fdefe179b3c97cc485b4425f7b58c594624421a5dd86bea8d03672aab64d591d56f1c7a57091718ccc32c551b84d35614529f6006f1f1d45e13feefc55f2b2f296755895662398f5ef095fb47de41548f69828a9595b5d4d72a097b204e5a5a9a961f5db3b476458914e5b19265f52b57d4ad3bde7c225a631f4263f6a08ba7554d605132bec7e82f4cb87374f75144b19635acdbb0a97dc6124b6a951d054a6d2890b416589a886cf9d10f96d7ad597164c58d4366aeaddbb0e2e8ead5f51f4815b3223c56212c82fdb17b38a57306cd5a7a68edf2fa552b0f2f5f51b7a6511d93c6b825cb1e8c6967633b41c7db06ea905f5c01b9511f93ba4ffddea5ffe7c75b7ef92febff63e5d1f7ff74dd6f7a3815dae89de8b4b44dc04a88fb46de79fde02bbfb9f13fbfb3f1873d4aaafe7bc6bfb8a48008fc90a3de72ada4b59833e9d3d313984900b7fee3f2ff774ddf59ae01ca00fed3aac657c76bcb4aba1963db8092ccdd14a4edcdc6131c60b42f3098bbaf71574df3c19ae6a335ad75bb5bf6ef6ddcdba29b235c4ac7f8065ed9bd6846df4b080d58f1597da77f6dd5773f33f2364506a2ba75e0552b8e6c3028d3e8f2cc6d18a379055b5faca9ab5b0f1e68ae3ed07c20ee26aa5b6af6341fa86e3a684b4b44e7591684e41d6db9c2729b557375f39e034d07f7b41cded7bcdf05b96bf6d679e610d3782732c671c2f31955cb04f190be61d8ec47577cd7d35e89f0369dd8fef34d4f5dd87bb252592c5b270343f1ca84e7dd3ae8da1f6df945cc8a6149e7ee7da139e1dac60b228dd084d8f20c968c80f421cc3db2f962d0906fd7bff7e7e73f5446ee39d84c895b87def4d8e6df93e725699a1236c97cab8ca5db2659b5310f49b1481027deb71dda2d8b3d2d223a33d7337daea8df3cb1fbf00496cb2a995c3979f5d1d51f1b3e3ba1e390d0570f98f1c1f18d782ca07e9d79988736062db920cc3ab3ab87ec4e320162c0a909e929b23d9531942293fa277d2c96e30a0fe60ac64ce816220b83d3d9b3692090369c90b92541b465a624b50d6d26f4ee863d370ebd0e136e31beb9dfef7dee9dc32b6d3b1b14d0b18541a4652bef98ae9fde736ac27249ff68f1e76bfe591800a9606fd06c008e85d5d57e396992266948497b0684b8bc7af5e8ca519bebb7f729efdbbfbc6a4ff37e65f1c99836585d66aee48c2149323fc497d0a4868193e00da6b663405c34d4cfe652933ae6096770ac3f482101e06dc9c57b975596f66c6e6dfaf0c49e06afd921f75c42b7a9a40c29a40d749ee4599bec19ed995d7460ee1230d726a02422ff37c92a723592f57979df697fb8f667662c6b49cdea9f6cfea5e59034b1a4a7db9cca599ad104835d4b81bb5c74f7c7bdafdf3eecc6ffbee49bddbaf57963df3bcfed7fb52e71d8363ec8e8c43e39836da7f4af56fedbe747dffdc5319f6b164d2fee5ff0cabe452e9d98005e3a9e3296071da24461e62ac36eca804229ecffd8f6d897c63df48da3dffff8e0eb5edcb7a8443a66a18d6f95c8d2b3a2d2dda4c5c8f5678c4e695c2dca1c2144e604a42510ce86fa4de37a8eaab42b16ec7f0764386fdf6b17f7bce044bc61e5d1b592809047ab6a45a66d08169a49409a9049218caf2542c043ff84711be81547367e75d5b7e82012221a6c464751e6300d2462e488625f6186360cf9a4b015564cb380d55edc6d7966f74bf3f7bf5c16abb8b0f2fc1f5ff28fffbee5b195751b22247c4bbb0c0c8f25ea7fb4e9f112e7b7bdcafaccee73f193b3fef333effe658bdba464823c1750a53279bc95bb994b9297dcf6c67a38d472e4fceea30694f602445e78f02dda4143172252adcd4b0ce948500346a7c109c61719a91350d199edc2ea7767f4bf7062af898b6bdec3689bead73f3ce6d3d70e9afe6eddfb868a08daaae8c635b48299c48c77951cb874561ca573d3e8493a9a00bc7213aac5d54dadee89846e22450a4e260fa0e4ed97999f27180ca30d74a2d34720c384b6fe69dadf090a05738fc54fbc59bbec2b2bfff93343efb08565e9e8100ff9912060635f1cfb79a89ab84a1c6aac7e7adffc1f6f7ee2eee1370be34cd07412294956ab48b03ea12788091113e4f7b66dfbb1ddcffdaff19fdb7e7c7b8bd7ea2515b939de525684d84e9ae37d9b1cdf743cc25eea281d2182e68525dbd3b47b44e9c0d165fd8f7bc7815c5b54c291faea415754371f32871a347a94d69aa17263879137dc21ab49bb1132b4c5ba517944e50036ca559ace06bd72e31657908b90642440b30ad8257b920334f8f094a015f48abea8d734d7181af8747c8ff1fb12352a7964119911431058252eef336d58e9607380a4800c26f41ab5f3f85e1363405128aacd928900f098e9810e75331def93d85d7470f19f8db9fbabefffab20afad3ae126927b2938dc27aa0da40e61807a6c2692afd031e71711527f0c4bd968b5d6b6d6d7361db6c93fa53cdb595cbd7260ac9fab5c635b49a221655911412db34c22ee36b3532b61d0a21d990943873042b55ad02ada6bd1d4bb63dcb8da4e98b318216ddba523058812bce10a2bb30378c5514dc644d6fc7f42fe70f37ffdc7858f6e6db86eef89dd432a868caf1af5172bfe8f2d4c90948e288607cf8aa1b4fad1e6fffeb78bff61c391cd87ddda493dc6c5a4f3936dbf2ac5284a41e47b9af02445a4744a8a84082f9c3fc76338d101dcd4ec08d00e39b23cd2cd001eaed72a9d52e179e02e58da523ac2787913d273220adb30273390589e658e97b082b0aa6c3688a3c2fa6499905e7189421231d1cdd226e2c06df56cdbb14a59aab5c5ea45c5d34ac52c2fd16c8b4a2d5d69666a9bb88348ba07a277282288a2ac3c2f5e6ac720f83d5b381ec50d7986608911b089da9cc067686698e8319b0ef8a10b95e104135e05745a59d2a35f49bf63ded1da963a8a79f1cc30119931e059497a4c2ad75552f52b1bd8cdee5ed75a73cc6d8cd1eab15f040fa5cd9e75b89aff73fc038f8cff6c9a521fc25d4238d8265ac27630b672ec720af6021e21efb823455c2b07c339aa24aa08444c0448c791b6c5d195967654364ec6ce8ed2c81d40074f920022450bb197d5764ac06582621f65327ad3981ad18c4bbe24cca3a7b6e2820eab1d8a37d35e54ead296843b3cb2a16d5b9878324c46399e2c9164b350209b66b948a1b059711c59cc90101e042cac660a86b428c0ab21d1d0e8361299d83688c49659394f3b9a97f60c9f25d0b9b4ecdad61a4bd7c2108c91fbd104bee063dbcc88146b17de96f42237d97169914f80e81f6b662b8a87d244e464e34a888f98665fb9880cecb3816b82ad28aa9376acedf02b22b493f4536027412826e0c4a3234349f7d638c2c6848428c9265d74cda51ee3b472d231225f4911e5f568ac91e319794e61de58363a877114c5d20996c266190df4cf3c2c4ff3f5266c484c6a76df92f3c54aea2dac1d2038d6d189f60608fb393c3a133771e0b00393819c7ce46ae2f3c08da4a56d73a6db15d64f9e7b7566ef88b6a32613fa6a190759dbfbe698c4e623a72eceb33abc10203a1e57258fcac89960f376e9c8fa4f3eb962a56250b26e1b917f65878c0e38432319d7b6d85de587855a9d39f533ed5ff3be085e2bc92bc60e68abed5c442763607567fca3ac76a3f975db850ac1be1ec12ef7406fb6b1e555dbdb5dcc8b631693918b9dce4b91e8153e7b070f38db66679bb78c2eed74d8e295c462eb1cea268fe8895c22f65db7cfd105bb21ee14741d75c7e6e699d0ffa99b97c86fff1dda4222f861523047499c2650b7cde160e476e1aec81694f43bd4fffacce8ffd4cd2b732097a9a7ab23ff1f630aed936adbe5402bba9193ee032d4e5ec610671be917db99d8d8d62f8cc848be5310cce3a4a3f8a25b449191c23dddfa3f65f3ca10a844d67f5be8ef490334d271950ec7e1f24085013d4e810548476b7c66f47faac655f9a57dd1b158e63038dd96704124954064623f0ceeb595371dd329e96b7d6af2afe57bdce2bcb26b1407c694e80bff48c7151dbc5998fd229edeb16347c148a150dacc9c7d50904521bcb718c8b6ed020ce4baaee3144e51b7b6b69696961660a0783c5e52521242fcec4e955272da142b991649240f2d983bf8cabf4c26b9e808a1855a2fd3681d2b2b2b0b439145d23fb348bfa5a5a5acacacf0a42f92f70c458813f86a01278030b19956f26bda13e6503cc83c9db860c1cf82e3c0441a17198badd80ad78cb04f6622d3c9801c8bf3352973854398bc086db7553dc9cce087f4cace0387f8af9ce02fc5566ca705e5b7dd67a0bc0f7e5632ceb2c0a94d383eb3ed4d87dd00c2e40ed32a79f2d6559327fb2d0afe623b3d1a933bcb784a326228d91033df39a4bbfeda84d9932d9e0c2d4b062e99ec19c244268aaea57e324ea328f88bedf4a17edd661c725ec6b6abfb74c78cb207d98a42e5cd4d3413344fa1a926a23e196de17526f759ca174f738bed346e32998bd236e98260cc9ed8bbe7c89a1587d7ae397e706fbcf6a8d592909565ddfa0fe83662d4e019b3fa8e9f14abec69fc969ee8ea060eb1486d6d6d612652f4f0143d3ce978783e423374f74a4b57d51e38b0f3e95fed5fb62a9668756dc59144e4d9517c13d9d356a2a4cfa03137de3674ce1cbbb2aad4955e07f7c4bb999616e977e42ecd94943bfabeeee040dbfaa8f7c9cf9a1421e9274fced3f3079f89a41f721466248f7221fd8cdc8661e7a6c9882ea8a282f1ebc75b77ce7f76e3938f5be6865e32a53f5d87a4bcdf4a9d04eb94285b791583874cf9e2237d274fd5ecc0e49417269b9334b7a2bb752b03e9a72514fdb50b5152d048c8459c73b7fe7d05ff9dd0b6a54ff7590c1d9c51c8eecf9303c01f2b7f9a9017b0a375fbc8ed903ccc2b7b0e377952f85294dbd2bcfa07dffee089df58daa19c4882eecf7926db0424be47777f4dc24b73a6a5545c49d17070f7f26f3cba7fd11b26013addf5937c1fc9f0801f1622d35fc1e03e7de4f8c0bcf6bf901d10f2e99e69917b63bacf3709a676eb339eaf6af2e2c2532a7ff4175c55a6fed07c8322265abacf7d9bda323fd3f5d455dffdd77dcb97186a77294f175d5635b94f48359056b75dd732f7ec85998eb93659a24462d58fbebfefed37e83dbe6bc68f17dce58ca4a3cf03fe920591492eb23fd8a12fe043bde5893e525ffbaac68758f9e03a065a561e8e147d14e78f12144ca2adb1df301ffc1c817567eeb37ff0dbc70eaf59213d9b2a49d1a5742f7997d3d0bab9fd289511e9c2e407a41c3c9470c23558c85af35fff59b77393071bd9e5504269ee666625f58334910a42b29ea76e6bfe6ef903f95b18fc423e304f90b5fc49e5fb9c3ba4d6f2217a430b1b1c37af8b999b1280a416b5dbd6ed9a3fcf33f93e48152440e3b6a66c89940ad233fdb6e5f3e524ee94104439e64c57d89ea5adc686f53ffa3e7138950c61d97f32279b4c5f8ab42b778313cb5a4006299e3726a804827a46a97c55750f226f7f943c99194193a6731b34f71985c83168d6e783abfd6e7344fcf8f3ad7ff8ad4ec4016c12965b62d25c990cdcb60bc8ee58e654d7e37bea42255cbc2ddd9817a3221a46c2db2679cd897dbb0f2e5a689235c48c31e0fa349f31d46b6a6a4a2412feb2e205ccf39696167f3b73d9c820836114741b5ac47c58ba21ec0b5af42718d47211122865a08ac51a1a1a0e1d3a9427c1ef3fb3e338c78e1dc340c109e61540f2743ccfcb7a082ddca33bb61dfd60a3a90f216de1297a6853b387929a5b4e82f331995cbd5a9a946b3691a2746dca99a34cb206e5515219b179ee533e43da9c31c834277da26c6d6d3d70e040cf9e3d1b1b1bf17ac890217873fffefdd8452c687575f5c89123735c32ffd77dfbf6959494d8b65d53533368d020bc665975f0e0c1b2b2323c439ec4244f73eedcb90f3ffc3033403e8016a6f3f2cb2f77ebd6ad478f1e4b972ebde8a28b860f1f9e0f3b1e8f3d7ffefcaaaa2a0cb46cd9b21933660c183020af9e25b23b6dfb37bff9cdb5d75e8b8dd35966309710d56e6b9c52db9b3a8f949751986c4ce4f474b528bff091ff3d78d245095b39ca6a696d7ae32b8f5075266d13d837695df075dbc237add6c387e20d0d25953da44709cdecb647cac0490cb23befbcf3787a204d3000381b4265e0c08198616565e5ae5dbbb2a67edff0c2cfe3c78f839db049f815dbb673e7ce71e3c6417461c4bababaa14387e6495681e8df7ffffd0f3ffc909df4f9733beeddbb17d47ff5d557bbae3b61c284279f7cf24b5ffa5273737384d361e90ec10106bbf2ca2bf17af4e8d10b172ebcfdf6db83f2383b85937abc13d4c6ab56ad1a356a548e5338b4710d64b9ed51362ba56ccf7629439e36690ead1257a861d32e7df3fffec3d11d7bec649a74d7d225967493809e423e1d13034469ed6a962e193ee7269d3c121699011e4c0c92233873d0076814a489fda3a25a25250c7b7281015c99143df7ead58b0fa4d0397ac647602d48917efdfa452b1a83be450c74d96597dd75d75d2149dfaebb299706d008428456e129e3054f3c5a773e3330540ad6d032c761ac3c43dee45cac889007092f4e9c38c18a3a47ab2c5e572fdb5235f865e83c09a82ea8ae31d64cab63db77d9a69c8fd09e4b7c9148c62a6be72403502928af66f5aa644833d9062a03a9cf6b0472c44f485f90385e74efde1d2007abe943fc5c9cc43eb64627151515545bd3758f1c3902ee6255932719cc2412f4fd85acb4ce4f85b26ba07bbfdb75ebd64d9a3409223f729646ff7dfbf6c510d8af8d1b37429b5d7ffdf52ca78207d8394a8de072415abdf2ca2b9ff9cc67b66cd962a57190dc49731b1b8467cab15bdaafab0aca2eb5b4725d4fd815bdaac6dff1e9be53a6d5eddebae38567bde30da67cbac9db2e12fe4d15721559a27edb46954c762233f3eb072700e98b0585dc023c485dc4aca548c88a65d9dfbb776f30188056febc3a1cef1032d04387bb91dbd6bedb6ac182059091575d7555b49eaba0be42cfa5a5a593274f06165db16285348d1f201751922a26b05fefbdf7de35d75c138fc7fd53ceec5ca8043edd56938fd14b3aeccd6954b2fab6288109b0ebadc5278ed6bef39d7f2a2def7ee76f9fb5ddb8c1fae4f98c51d48f4319b335df59914d27ea38ebe847fcf269da2e98060430eb65e07b00eedada5abcc03c7df6c8c5af6f054edd1b1a1a58876035070f1e0c3d9327cced3f30cf2b44f1ed3a70a36a98e3e38f3f3e75ead49b6eba29e8318b705e68302ab0867801db09580e680432cb0796b9cc2b1822c5c341456fdab469ebd6ad4b962cc18bf5ebd76fdfbe3d6b5ba2a4a4cc4f4c42616a2c1909ba0b4a4fade58a1f7defe09b0badd6d6353ffff19ed5ab865f778ba3637127a1851b3745cc2dc17960e92fcbaa7a077d9219001e7e08c82788615f6861da40ffd842bcc9ce2cac722eabe90b89a3478fa22bd867bee32f7f474bbe4518944fed8e1521e282bc78e9a5971e78e001c8635e4926a308e7886e2198b66ddb06f4c81b64b5458985983ceb4183db8d17b0fa3ef7b9cff1af3b77ee04b3b1db2a3bbe2a2baf6c6cad3569fba95084b4a8b82bf97b5cba9a0210535ad9d33d512f5d2a26d1adaaaaeee05e4bc44d5e7872606a72f793d43731cfd68071e7738d0891ac9d9aa173b37ffffec08bf809605a5f5f8f8961b776efde8d9f5865c092dced7ac68b3092d06d9f3e7db04f870e1d1a316204de0cda67f990fd41600a319c4a19110ae655ab6075e9458b16259dcdb67dcb2db7f02146844e462cdaecd9b3a15baebcf24ae0460c7ac10517f8f49aa3400976927aa4c34a9b9f218b75a36efbf5b58e1dd1a6240c45ac4947531110c7939e23adaaf193e67cfb071fbeb970cf8aa5e75d797d65ff41c7376d30d535c1162eb10b153b92c944d052f4993ef3e4869e74a16612af0f2309740f422f2f2ff729033a14338474493342b813ac1ffc26ab66c87e0c17b225d259cd5ce2f561c6949595051fa9732ac9346819ace59b9b4ca658cf74e07efa41cb7ee7e8165209230e1c3810a38462963a5fc98c82968347fe188e17244dba4f8dd7dff4e4633b9e7dc632ae1b47715e74653c93b6e5d1095645ff8103a6cfa81a3dfed08a770fac5fe335345a0244ef68d6e46d56ae310d4a6f7ee2693b566aea8bb9da72386839b3e06facbbefe2f40911bde42e77530917bc94fa69616eba0087a4b26284fdc74c0be193c82d785ffcf34948aac28c7631837031c7fb34803423aeba7ee7fc3fea78ab549ee93266bc6c92eb0500f237d4d434bef402271de1035a617c9a64199b4b8c92abc728bbdbe0c176498c8e8105e89e03fc33f1eb17db99dbceb88403a0e66e8387f5bff822aa732a1cd7223a163a61748ab993ee5049cde4ece8fe0948dd042fd0bd44f2e428728a3a24fa637ae2bd0f189fa67294b402396e8ba45fa4fed38ff48db93af653f7c7ba57094f399489335958d81447d21494af93560407f7c714738524aca3bd982094847f7dc74c1e38632605f868e9022a69919973b3d88aada08c6a39ae54bd868f1a73c7a73c587a16fe251c1da31b2a544dc8f14c548f3215f0283859d33b26211b9df352120703f7cb7af69bfce547c0144e2289fd5ccbb5855324fd623b5d495f28a91c6d8bb177dc35fcdaab0cceb13da11274dd5070953aae602b92d970132299950a5ac02107a66739b1d8058f7ca5c7d061a41628a3494269bbcd66cec4b9596cc55640ac9fbc432b843df58b7f0d26f8f08d576df32615fc03bcb1844b82bd2d5d8fb05d91b00d3718c5e09695974dfdda3f0cbc7086f95c798c8ec8623e591dbe28f58bed3494fa2612b32d05d59447be7af197bfaaa84875a9ad29c548c2724d96597341d11c5639aa94725409ba8cd87bd8e82bbefb9f832eb9cc90b7b493352a1dcb915c9abb8dc18a7978726ec53c3c39b614bfbe3439473c696159ed1619b7c10af5c7b73df3db9daf2cb4bc264f10de37b77215476892bdab64c9a0fe63ef7b60d8a557c9921827ea61eca44c421ece7182ce2bcad3cec35324fd22e91796f43b2220a7b5bef6f8ce6d0757bf776ceba686ea9a78734b455565f7e123fb4e9c3c60faa56583873a25dd382cb9933ce319a4a02a927e91f44f07d23797cadb7208d0012fb978286589e6582cc704e5ab36bd213baac4c4a45f34738bedccb1014ec2a1e4c994a484b382935519e0234f16bad7aaf39c9b0e0badb355ea17e628a7600b985d3458d64d295598a979a6a561fed2852c7cddbc569c73dce427a4fc3c1e2c63c54144365880f2b2a9ae483ff20b78a709e9e72f715aeabc0a33108743168cf473bc79978f79e9b6e04b3fdcd658ba31836d94b98fce50c72469ee52eae7291b5147ab59b0810a36afb36fa0d3755ecad0bda16c93435cd3cd75ed585ed216485a0226bc973255c92e48bfd88aed0c698e0943d6ecb8240650a6f88a29ad62e85d25b9427741f745d22fb633ab29ed6956136dc62e27daa79c6ad264a852563a75b4da8ce5623bb3fd1ec20a1081d5e6d1e35800ffd89e83da2597dab44ec672067c8556f26f951f2279fab5003452569bffdef7f97041a1b43548b19dd1746fee5e28690a3140d37b3639fce8be86968944f586f5751fac6bdcb3b3b5fe44aca2a2fbd0619513cf1f38ede2587937f28e9097dce59c0546705ae68565ab98c2fb85725b9d3af0546c676c13c9387641542f6dcb732dc7854cafdff2c1b63fbe7874e39ae61347296919f9413c25945cb3ca7af179d07d8ff3468dbfedcebe975e46219094aac6753cc7c48bd1ef9ee5d94a52aee2b37cf58aa7b939b753779aabdace2c25c1186137d51ddefae413fbde5dac5b5ba8a832997cb6ab9503f6d01624b9b492893af07bef8953a7fed92315c386504c98906d3089ee744bbad4e1b636c74faf4086881a9fe616b1fe998c7692059429b9a465cbe3bb762dffc63fee5df49a6e698331a6d9c6bf1d2767608caefc51451e9550f2f0fab56fff9fbfae59b5d224a877db72d748db15a72dd68fb01549ff4cde3c692a44c160b59d133bf72e7bf46b0dfb77716129535b0ad2db01ece77788018489f5b5e862abb05c5bab7853d3f27ffd46cdb2b759ea535480c75131529ced15c48ba47f0637a5e39688814ee3f547dffde7bf6f6d3a66bc3a8262d88dc8c70ba9637cad896d0365c98434685e83016ce112be5ff1bdef1edbba893e550ad61fdd6a3d07ca871749ff8cb6d46ccbd0f7ea1f7daff9789d216e487a2a3b05e88f9f2e1dfc53b602ce59a6f9de86d20ed978b6a951e29174d7f1353ffd61a2a581ae7aa884a9dae016a5fe49abb15d8335f7caa1a79b7ddceeaca3ad26d4ee04b3db3e18a3b5db3e38f2fefbb691e814c54547f8d211926aaf4907d46f27dd974a88385fec26b82f04df84a21254966cdcb57bcf2b2f99c2cb4e32d98de86c0a69a6c18b7cd73aa7848ca8315dd20fe548b26ddb711caea7122ad0176d734c83f9cfe90df347e2fcf03c299e979f0fd98ab49e9c9f408e07e2dc72d9579e74ad0d3fff05853d4a92e5785ec87af4e66aa56d4729b784421829750791b82ef3ac04dd781209bae464fc7b6409d03f7bdbcb2fc54f9c4847d0f8096b43d4969a712d72d2e704a94c097ee7bc8cf8192a78dc05696524abfce4af7bf7ee5dba7429c6aba8a8f8d8c73ec684127932eeeaeaea850b17729a484ebc7ce79d77e64390586dc7848b172fc6a0186becd8b153a64ce12c9c91d71b44ff3b76ec58be7c3958baacacec965b6ec9aea21668f7c489bae3bb3783126d452544cc093f6d5169afdea36efec4a6a77e1da70a6a44e5e77ffe8bfd468ddeffceb2ed7f9a076a31e1be1834a685c136e09643070ead5f3764e66ccfc4fe4a2d3b2186502d4dfc3c7af4e8b66ddb2ebffc72d775792e5c37e9faebaf8f76b3d0ed4b2fbd74c30d373055ecdbb7efcd37dfecdebd7b6b6beb273ff9491625e94813272345c3136e686858bb76eda73ef5290cbc75ebd637de7883a71779c1cd810307de77df7d3cf401d3a2e5ae607d59f0d5ca952b7bf4e871c51557e09dd75e7bad5fbf7e78807c5438c444b66fdf7ecf3df760d0fdfbf72f58b0001b994dee41a50faf5941a9244d320245201f48de9efae0e786cf9a5579ded84d4f3e660b1b76efdd735f58f3db5faf7b7bd1b88fdf35eaa69fbdfabfbf4445d78085c84364825f741c5a75ffb2b7875c768520dcd4857393a52f6f0768eed0a143a0bf71e3c6f90974314710497d7d7d84f480e16a6a6a3ef8e003bfe442535313c8efa1871ee224f82fbcf00284639ada46662af5d1de7aebad9933673227403a4e9b362d4fb0d80ae4ec5dbf7efda5975e9a0f28ef57b5c03e0d1d3a9465065e9c3871c22fc410eda090f7980b578e193060c0e1c3874b4b4bb3615d61d5bdbf42bb922a0332f437a997d63df5f84b5ff81f0e65ed2b81757bf30f7fbeeca73fdef6fcd3755b362ffde1b79a6a6a273ff479ca7560d994a02319024405079bf6eda227f408e76b5ba7290a41faf83979f26496f77c98188fc7cf3ffffc680faad073737333188cd3bc626bc060e3c78fe7dcbd5849c891f4eb3c64504b8b5f6032757575b5b5b58bda1a171aca531562ee73fefcf9575d7555feaa965be6a0143cfceebbef62f9f6ecd9b36eddbad1a347f33daf7c58d87e3909f48fed84f4ca92f4b76fb16264c14a53554ad1050d4fbaecdd01c08f0b59d277d2c4832b96c37e35d10962cb8bcf8fb9ea6394ef4079c9d82fa16ce5d842b6d41e3515c8c950f67417a4efa729afacac1c3162049464b0e430de81f8c8b1e442a8613bd0edb061c318eb63acf3ce3bef924b2ee1e78142e0c4f7694aab0ca43e4f15fd82cba15c0072e6cc9983e9011ffb553a22cf18ccfa1480d84fbc9c278f01a735c70bc87e3036d7b50d5938518d084cf5de7bef81faf17acb962d182b3b58058c9ea83d0a0ab6b5e2332949513d44f526fdaa843ac00b5bc6e28d472d11a7af49b7e5d061d9ad8ca2d76c414e219d603fa61209b7b15125af8048aa4c989eeb86f79d99c1273b7ec74aa99f1e8961167c06f67fc0427bfef9e76176a66fe96666e6f2f4b05593264de2fa0b60bb55ab56f995d8a2a5129e182cddc8ab4d852c36deb077de79e7de7befe581a64e9d0a5503e00849c35f8810f44335a37f8054bc9e306102b81aba34ab5bb012469ff6e29ea9b666ce626d8e6bd05689c20bbab14d25049d58b96a8973521ad9ad54b98473a47662c40c82ce06a41b83a15c4a55a8924e7d912e4984d820afe598829683bf716fbffd36443ef62e7d1b371bc0038aafaaaae27d6a779868e1013b43401c792da7c505913035ceac8f565e5e0e1c92277e8688024abcf5d65b6fbbed3658d2d036a174fbe9dbb99583865006edb67a6914a44615641d1387403578f0bf444343cf1123349fe85a6ae0944b0eaf5dcd485e994acc50d9b68a415d9454f6945490d9e232e5591f53e4af85cad7e1c52bafbc0251f2e94f7f3a53709501e9f3f6834460e3425f7391c365cb96c1ece04c01f980c5403b146417b5ad992a45407c83070f5eb3660dd75d5bba742904739ef615c2093612ac0bcc0e2b397bf6ececaa095105abc917985bf1b6962dc93cc45475c7758921927cfbe6b7be79cdb7be5b01e45d56de6bead4690f3df8de4f7f6cd484768d2a75017504fe4474ef37c84a5e5b51698ac250999610868cbcf26490ccf073f7eeddd8affefdfb03a3369896feb819001edfa2874103729f376f1e7ebdf4d24b81795809448b0a7cba04320ead69b406ae8f56811441fabffffdeff13ecfcb7717443b3444fef4e9d39f79e619743b6bd62cb05c7665e4f027832fbe74f71fe7699530a9b7895e63e68a9e0294f712ecb53cb8e2cdc53f50b7fdfc89926ee575bb773cff670f7a8d0d062fc0088e29d8bb1ed9705ab93dc79f9f4cd647802796ced2f92f7af6846a1911dc293cdef8f1e323a47eff3c0b289122526d1bca198800bbe6574f821c4973a7d28ad70fd609f33d39ac5f7c799f0ac53ab129b313cc197988d38fd7f78d335e4dde33f697f974df395d6611afefe7f9f0cdc1745a6af635afa1f14f9fbd8b53cb532c03fdbf672eaa4a97227a3cf3a6f928863d73d00365a7e1dcdc1ce52f29c40de0df96d6cc6fff7baf09e70b2e5462e974b2afa5ee7ef0c02bcd2dcb345e3f1849e16f3157fff6531575327406f1faa990977dd22cec7d66c893f331df69a48205ab39e912173d0e1e65447eb0155cc0ec3bb1a4535131eae65ba56d1973d6e2020b10db04e2a527a9cc324c3f1bbf6a5780c4a4a254f48a4c01bc7089a5954858aeede8de93a6f41e4742da56d24b3b782d548f31544d317f255f83c51b7de752709bba1c3a035326289f8274e087bbe40f91073930dfc653475a2ef2a945e01ea0981d3de69ecfc47449b2b638e59c8f0955428f4d725e48cbf524fd620c5f4907593082c987430e7c8f2297817a3d4f8bf177dd039bc116301554a6c22814c71574f0e7494e050f9152d92c4a0f8fafa37d8d66a544f0e5b5b6733e044927228a4588cfd879227a7f01b35b378ffed02eefde6bece7ffa74de9276d4ec7a7f009c52fc409c4073d8f263287cc5f45c9bbc9f74fd5080534c3a8eb6eee33e54273f1c5e31cc59dc8fd76dd972117a78f43f264e976646aa7cf7832bb817d9ceacf30d5eec92b0344b58e41ca6ed73f9d0f7ef63726547239d3566afeca13dee89b6e1b3073265d2a27a9ef4a033d6c4175344d289a2dbdb63b87c2a5c35e4997b1e83b741a60f51e3d7ee2671f34051dcc4d2e6229abf36c36a922b62309988febd15dd6b54f67509935d1589146aa9daa968ee59d3fcf528e421150dd4ec699c90bffe26f874cbf8a8f73e97ead4551cba063d2032221a403d18e5f6d2aa029638a2a32180341741f39ece27ffca6d3ad0785e99bd8664edb9da900ea48f6e595423ada97ce074d1e035bc576c636a18dfa505ac9b8942517fefddf8dfcf8a7ecd232d22b54738ae232f9c08bbc1ff816408e0d790f1d1023dfa550832e9f39fbdb3fa9e8d183b49fb04ec97da05322ef92cecd23478e144678179391e4d842ce4d935f3b6164345f50a7d3dc439b376e79fc17c777ee76138d90dfb67254321fa56bb27293756b4b2b3660c0f9f73f30e8f22b1d8b0eff95d50ef43aedaaaa44d43e5255e5ac4c327e0ee4e19100ee7ca74428cac323b44389d394736cd7a66d2fcc3bbc7cb9f0dcb8e79ae43a02b04796da9563c68ebbfdee01d3a6bbc65ea3329d42856c6e2b103898bf8df3bb3d95a47ffa00eb22e96742fae6a28960914fe19674c744c785659bf7db5295486d723190a92b6409bd962e60524c3809aaa579b2f04e68837ca99fef8d0b927e010cc8620aaab300ebf3e5713a6fb185b971ab5ddb2aa3429926f982b6f18127cd0d455b9440ea27534d69874e76c9a16303329d5422465204057f61bc7605338bc31e9ee03dbd623b93cc35caba663c89c24a6815a328651bd44f69a72ce5d20997316d8d24756d179f9a5c6c8ac2923d4da594612a58276375fce31a2bffa734e9386af2ca5d0e9fa817cddc3305f0040f6bb439c402d153b4b24a969732f25eb5485fb699e87d23ea5d196fcbabac40f792b237e0d72696804142f70f2e79c4fced1a0f54300ae4c6a1e90e67c52862fd3382f44319598cf75d3a26851a1d479992c884f835e75093ca941d97c697ef8984b06292ea0afab68132f7ba4a440721ca20fa82d14621cddc64906211369cb94d49c5ce19c3e152f9c5a4289594ed11947765921f94d4a5b6f02856cda45336597888da84e59e9bab5724fd3319eb9b82c9b48990fb5273461de1d9427940f4d28425b75db6025fb8546885a2f0a116e8ee623203cfb9da8aa47f466f9e48568ab525933b2901e9d1d5740aea4c90012c92e935b9f6a0c145748fc5dce2051452e76c799162559533b8190fbd41ed30591dd7d33141af08cc589463361e8f37ea04a565d3747e6b97c8520a739084742de592d210b2cbaae245d22fb6d311eb73a826e4bf540e85eb00d62baf6edf86cd2b7f7f74cf6abaa625d884b52df3e5aa81a3c7ccb86fe0904b1dbbcc7312963a970547f134f7ccf1f0a406324895f064a9909c2753d5ec5cb671d14f1b1b6b3d4f513939ca352bf98584e4273f27e5ceef565139fe8a2f0c9978754cdbaeec3034ff9c88e12992fe9948fa7c946baa265290c2ca57be57bd79b147b16ac18b82741bc532062f5ebb94688dceb4f0b87dcebbe8b28fff9bb6cdddae738ff44f7733b7dd1b40f91e2ec83fa117f91e28a3e1cc5d2a49f9f3957eeba9afecdbfc26843fa554a3fc0a5455824e6e2dd7e6b84ec802cb8d911d40bc81df8f7cb8fab5dfdcaf9a9be9f222318bdd16cd6b0a8e8bb4e44b2793cac7baa5dea7cb9a1e32c8c3934a070590e2fe75b0fcad667041fd4b5bfe75e47c5caf4e4df110ba509766ca3ecdb9f33dfdd6bcaf1d3db4950e69c007947fd355da7538f102453580191c4de75c76429aa40d26b281722d1eab7967dedfe9a6869839ba55b65f39da4be7b64ae8b143111079baa11ba4ba8e2830ca9c9bfe5d557f9f526f49e6e30e6b281944e443f80922fd68f520d1072f1f479b5f28141dd0ee8d3bc09bcea99fa377b6bcfbc4f1fd1b4d5d44d7a6f241e65ea23426afb9bc4bdf240844675754204e9a8c066006fc8d278fd56edfbcf457095203c2dce1b2b8a44ae7a4df6e2282e036e529255927b71ff3752d3d2477431a204fa525822184c184eed152614702cce7ba7ce45909d17a28f7899fc2a973a9a9856ca9dbb765c5530972de484307943f872291b57095095ed61e3b33cc40262d8fa2ab8992f26bd00547cb533b363c5fbbff03661293c0ca526924dcf4f7222801fdd8cfbcc29e8ec8bd5d6c9213e987423b53536d86aeab473e377f95f3c160417e0e86e9fae2dfcacfdd6a5f6e057360a54285ceb650791b97fd969c9c540f31e169be5a4b509ee294c9b343b42e4d6c33e519548e671c3ef89a672c5d97f28b831bc4872be7795e82e21d483f502a1e2d543a34e7e7ebb552325c0429321f00b5dd8749ffd6b893a9880a857407535ee56f92a1b413110afe50a69de0cf2027e7297a3128444240d9e7f04e96b4a5e1c881ad8b80e3310747389e812194178ffe44c6ca2aa45ddeda585d52d643969798a46c968959d3adc7aa4de48365b03ddeb5ab772d6fa8fdb047bf51ac213a178ba1c7ebfc51f3815183fc16d2cce913a1938588f25d6cfc04070f1e8cc5627dfaf4899c325a5b5bcbcbcb834a1f0fc01eb7088bbaf929b4c0c0beeb107364a71b17cc8b1662610a5c4685f70ca31c3a74a877efde5c2c24c87b9d8f5bbf7f53d260950edd44a7a2105427519494cefec4b7864f9c7560db7b0b9ffacb9117dd76c115f7dac25c4e8f39a5e595bffcfa8c185d64740dae8f09e96a4bd7ec5e59d9778cc1f8b2f374b341d1e043c1e03bfcd83535354c1851a527f3855122912829296179c4e5704e9c383170e0404e86197105459ff2b66eddba7efd7a7663d7d5d53df9e493a08ca6a6a69ffdec677e55bda8da638f3d06d20fa2b73d7bf6fcf297bff4093492e12874db71b66cd9b26cd932ee13acf59bdffce68d37de58bc78f1c2850bb32b79d26ec3ba811a7efffbdffb68fe830f3ec0103d7bf65cb76edd82050b82965597b303b12a3aafa5db289c7b01a62c71716b62d90bdf7af5f1ff0144848f362ffdcddcefdcfce477e7fce1ffddbcf2d59feddcf016b9f6291909a5ac52b08ea138943c7260a3e598e361cb4d676183e802937afffdf73117bfa2e62f7ef10b165e4f3cf104783b2dfc961e28459b376f9e0fc2b198070e1ca8acac7ce69967f6efdf9ffe014b0699963ffcf0c3e5cb9783dc2fbbec320c0cb905caf8c4273e816dc3a737dc70035862d2a449b9d3477373f32bafbc72fcf8f154b4b371e3c6e1c3878750662e68047f0ea25fbd7a35e6356dda34eee7c89123d3a74fbfe0820bdaf53c66ed89c38bdffdee77981d5713c24710ff1b366cb8fffefb411cb366cd02ab67349163d59b889d14ddc32244ef09ca326251b4726b6bbd2546987beb9a4ebc64dc56a4bba65cfdd082c71fe1da7190f9545257733272a7e5c8878a2ebd58263842755d5ba24d0c63df41f4b5b5b5575f7d354f133cfcd9cf7e969518a865eddab53366ccc8dd0d0891f4d4534f41c0fb95ffb07a20b9091326e0d7871e7a0862f1befbee4bd3c6c8a082e279e79d376ad4a8eaea6a201c4c18bb75c5155780db40196003a86c7c1a09ecc69281a330222487aff72157b862ccabafbe1af2a3e5a24cb912deb871e376edda05e1c13de3c5e0c183376fde8c5fc78f1f9f8bad1672067cfad39f86a6fed5af7ec51feddbb70fa3435f6fdfbe1d233efcf0c35cbdab5dce496d271a0f093ece3556b164af0fe565a0c48352b974c60ba35651da057c36f6d27b0fed5ed770648f2934e4c14470b9002e19cc89a6a6a3d264250417998d88a5e381c0cfc993274f9d3a1552890bede09dd1a347031d80dcf12be63872e4c848300f24c5273ff94950fcdcb973adb6828a5cc590b7120f003dc360329a74b37ed7be94658aecd7af1fdec7b68117810ab8fc60160238d535e63b58fc176bd6ac0181f6e8d123642046e2760c9947d82d4c0ab3aba8a8803ec58a673d44e7e79d5cb76bc78e1d200e603940ac8e3257b6ffd89eeb88e48ad9827d2c928a68e9185dca9531ba99ae92cfe0496bca6577ad79fb89e4991565ae3a691d4a1bfbeb698f4f6c78cd553af023e8e6f73f023340973efdf4d3c0c3a0457042b435b7fd83ce41830661207e1f520348818db474e830b374b3a9ae53d606b7de7a2b8419b45e7684182ac2917a4914cb07493c65ca1480724c1b63f9b9bf23713bfa89aab9cf4b2eb9040aad57af5e43860c01f201d7655da83df5a277082be3f9311c94e7e5975f0e7e039bf93cdfa55fbf5bf77e9e67608b7038c57e720a9461cd556d5759246532171367dc53b377fdf19a2d14d62cb449374b813dfc2cca13e5e5bd02142fadb4a33a43a98ff1d82fbef822d6ede31fff388434accf254b9604cddfacd920684f738c16a421d0e9ca952b376dda049485b182f9e2a321fd76811a9016d798808a01ce0340cf3a8a2b743a1df473e39d152b5680f85e7ffdf5d75e7bedf0e1c3f8d9d8d818c448594b94908661850638ee67bee7bab6595bf041bde417dbf317136652dfbe7d011dd99b0433864be2041d269d74dea3ef28ff0b321973430758e61a3af101e729f1e8a4d79a32f3fef5effc96b2f2189620cf26e7a7355526c8b8ef39508a6cc456487241304158b0a44703160248662f993ffdec0456eaa91f7e058341f8565555e105cc00d80369d6439099ced07772e3e941faa0127e0dfa604897b5b9d9aeaf9ddf810cbee69a6bf0f3ca2bafecd3a70f7efab544733c4a0bb10d93388c0afe08c40a7d0a8a0c41f0ec304fa8e0075afffefda1cd60c6f058403e5035e93bc5fb0ebdc0d52ec5a2290e51d61c8216d3664d3447685269b951977cea48cd8efac3dbf03549897944924b0071880df053570d3adf9723e6399deca6894ea0c4a0a8b19898149899aba386be93cb4afa7a66fffefd6fbdf516a0292c2530d8b061c3c07869a2ee748fb482cfea97689c356bd69ffef427003bcc13560e57decc11d5f930c6f7b2870eabf93a7dc816ccfa902be8a1f395f29d77de09880ffb09d8b1aeaeee861b6ec8aefc49281ac27f5a763f6338107aefdebda1c460264165df78e38dbeba4bc7281c30f2c27576a9d4a69a25c0bc3609f385a725dd5d39b077c3c1a7fe4650c24db575e5d33b563e4d39a94c8c26e5df978049aea3632e0575daf8c301a3a65372933603aa730f8f2f9e78b3fca2f6acbeeebefbee175e78010015eb8679416cc1000d6af2ec36cb37c9185c606820d2e5cb97af5dbb16b803aa061b97c1eea413af1facd6c2336463c2328507b9ce3d189d6b207732ab4ee2ec52d130fbd7a15542648ab1c0699dffad9579bc3e56135f661f856552b56059cacbcbf1a24bbaef325e3fa482d127066225c00b0b7b17a2cb1fbd23a5148ad7575e62e9efffb2fed0768ac52473d6320fea71164e2a162d2851494cc884216413b980579481c7642d214f102b879ebd07cdbaf76725b13265b5595942b536c7d3a9a5c55363918427e4773047a070cc0e502438af76d14846f1fa74645752e2177dc2b8c0391817422448815dd6d22a5e5589a09dc29c9bb57b372c9bf7374a52da413c41c2b662147ea34d4883a17f61278371b01ec272b463eeb278e67dfa8ea637c5e41bff6ae4c41bdae2f5293f2148bfa5255ebcaa526ca76383c8ee3b644aff91974bcf51b6e512ce11adb6eb995b5a9203d728384d9bbbebb0066cbacb624c02657c9b94cc41abee83278d187595495542c90995683657d76367f7ea1549ff0c6e744b4b888b6efa6bc7884cba99a57599e730da31a10d842fd8dfaf84c51ad733efb1656389842d4b2efdd85f58258e49e049569c908eb212677abd9c22e99fe5db0758122ba99afdd99f963a1584e7a54824f18d6cf3d5726d45cfc0208b8299c9dd895f4949c0aebdfc9e1f96f71a4e5d699dac93aee8420b2c8922e917dbe90a787482bc99c2ea563960f6bdffd1bddf500adea450640a4f33ce094a42428543a93c2eb976a0084cf032652e29e9d9f7cafb7e5e3560bcb25a2d937d550bae0fabdaf2ba1549bfd84e4fc0633bcaf5a4290adabdd7f099777cf7bca9b7c892128ff0ba39c2f52fa153643ec17a3ac1a5621472f8846b67ddfd9dca7e63b58adba2ccdc5e548c833855db599f97aae8e139933d3cec91278a8d99246ae4b03c7e68c7eeb5cf57ef5dd57afc08489f12f498986613d9292abaf5ee3de2c2f3a6dc523578121d82a9387104f18263aea22b3e1796e697784b6b310f4f91f44f47d237b1c58452c87f4300c5f8cb95c9bde035d7d66caeafd9dc5c7fa0b9a9a1b45b6545e5805efdc7f51a384996988bea54828e34415b8671937c13f89e8219a4a35d4fc8b33b0fcfa94f3c781614df3d1528df84a971b629b3784cc1b492245b5ccb89f51d3205ff3aec4158f4dfc9183513af66f03dbaf2ce81ecf3f274d8c5222967212f8a8b90e3ea91d4e7188c0290204704e4b56e5728aea1308027146c973f6de6470517924af2cd667e544f81f9d9e16b728511bd0cbe0b3343be3c5118085e30acef0f543051d5dada9ae645e15c1ac7de140c02c05e820de37030d0d967e6b2303e6bcc5cff72466178cc1f34481b9167a10bea96c2d7d272f23aa5221a8e762ea7d62e3a6b9634891bad94c463c576ba114a716bf2d1e4298122e742cb538eceb352ad9d4ad23fdbd791c2bc6cce462facb66095507896f26f64731572cb7796fb4508cd050e93d5de0045ca5ea60ae3204e65a4a22ac8d5d83d57e087962ea596a76282e6d8c7a1502e8baa81b71ea8ae59b3eac8c6f78feddad97af418f8a25bbf0155a3c6f49b7671d5a4c9e57dfbd27572aac91c3365c729ac5d8bb8b9fb5d42e508cff608c7b39724ce85400690a94365944d9d648be5bd54a276fdea754f3dd1b87d3351b3b26da93ced0a2b06616f8a8ed391fee019b3c67efa9ecae1a34de20eaacae650f0003ab495eb91860053a953754b2bbfad584beb8c277d5328c1847a1918636bbbb5a97ef50fbe7378d52a656e6e700c0cc578d916513fc83ba6b4721d6d139f88d8a84fdc3df1be07a56303ea00e7508726c19909734f789e2e92fe1947fae748d032c566997bd890fbf6f14307163cfc40f5ea552077c9c99b981f6dcf487ddb81c4772d47c74c352aca14b2f3f9794bfefe2b969b50329e8cf78212509cb9a018f87d269bb9673baa3352dfc098c6fd7bdffaabffa51327ccad0c4f29e5503d65bac884ffd994655525440ba90b4ac5aaa9f602dde7768feddcbef86b7f2534271c07842a2198d466fe165b91f44f536bde64e173dde3f5cbbffb4ddddce86adbb13c42f312162b00bc266c43505e9ad23c0ed00e30bc2729b5461c4680d10b27766d5df5dd7fa13a6daa44423f90b9ec765479b3d88aa47f1a487d2a9c43307ddbdc3f1cdbbbcbf8796c17225e53c1415b508a4aca576025b4ed49536dca7c5fda00fd94a698ae775bc6317a60c57b07562c65b8af853c99f0afd8ce7ad24fcdef136d799fd484b5a9ef643a1c617aad4eecdabefbf50500efe497a43c654272c83ba59da4bc823003e82a13e9077ccb6d631b9b1843b84973c08d6ff8f94f60402b4ef764eef26537cdce6794ce1c437596fc3f6cf76f3b3ab3cf31555eeaeeb73b4a5414d251c5dc4ccb275a99665a66ab3f38614e1e8637392428eb15f47d32ec2ae1fcfd5c3d339812307b3b575a3b5e7bcd4bc41595dfa121a8a29a36c9c82c938195ea2f50360e535f9c2f3179962925680b939892be25a1239a8f1faf5bbbcaa61302ca70d3f925d6503e399e572291081586696e6e0ec5b4a6135815aa16e8133787acb6b6b6f202a65fa1283b971d3f2a86f3cbabf15c82a1c8c160f28cfa0f7d1ff3c202a686006349fd40dd88736ee2e7871f7eb876eddaaaaaaa23478ecc9a356bd0a04118e3dd77dfadababc3739495955d7bedb59c4c2f5341e207ac722e6274b260c102a6928a8a8aebaebb8ed3d9e19d356bd674ebd66dcc983119aa36ac57d3d115ef6a15d79c81d8705742cbaa9123c6dd794fe5a021d56b576d7ff6997853a3a555e5c831e7dff340b7defd0eac5dbeedf979aaa9c972610a27948c6982f8ded667e7f6bd703af9f4cd75d82e29835f60162fbdf412660449d1b76fdf193366e019b66fdfbe61c386cacaca13274e7066f3e0b2a793313854bdd83291a45840a692f2f272ec8b5fd9013cf6fcf3cf7fe6339fc9bd98333f1e46d9b973e7faf5ebbb77ef7ef4e8d1cb2fbf7cc8902118eb0f7ff803b337be73e5955762be41664e933a439a0af37af1c5173123cca25fbf7e180b9d83e55e7df5d51e3d7a6001274c98307efcf860caf85ca53e7a0157bdf1c61bf7dd77dfd5575ffda94f7deab5d75ec3a89c50ff139ff8c4c73ffef1fefdfb8331d2c96bdef940584ab0d3b871e36ebcf1c63beeb8030bba75eb56748b65c5fabef5d65b59c82d08f3631fee683a521d7cd3b57469af5e1ffbde7fd66d58bfe8d1bfe93974f4958f7e1b4f503170e8ad3f7fac7afd9ad7bffae53e23c7ccfcc76f2a1587b08fdbc948070c7f74e3ba447353f28e854e4b2aa36187aeb9e69a1b6eb8e1aebbee6a6868d8b3670fb670e5ca95f7dc730fdebff3ce3b9f7beeb9a07e4b33837488a4b080efbdf71e28e0a69b6ec2d6802676eddac504045a5cb26409d7f9c91d8af871d498c2edb7df0e098529fce94f7fe21a0860e6fbefbf1f04f3c0030f8036da4d229f11c2c19fbcf2ca2b3367ce9c33670e16b0b1b171c78e1d98ecfcf9f3417ed75f7ffdbdf7de0bca0127a4939f3d5dd2c743d7d7d78f1e3dbaa9a9893b8538e1927253a74e650d3e79f264d065eea57379ac61c386717d98912347d6d4d4e07d084b3cc025975c92156ba9133bb6d1df51a2c964424afcd75a7ff8f5affec5b605f3554bf33bdff9a7de93c611a26b3cfed2df7c65d74ba042e7ed6f3d3a78ea0542c46cedc6b41ddc0cb7a951db264d7d1a808437e3e0c1832002d00a281ea211da12fb77c105174009b0744cad149d0e7df83083173f168b55575763ddb07ad89a51a3461d387080bb5db16205640aa466849807b3c0a4fc67867407f11d3b766cc48811c78f1f8724c61c435be657ffce882ab8ea19c682f2c4bc060f1e0c69882118f960b21808da8c13f947565a826b07415cf116623cc01e301c1e0214e91793c1a2e728f52d7360099ee6d290f8157c0ca6e2ceb167599f6536d754733570ac9381fa26318db6ea776f21e78e72064c9dd65a7b94aa911c3f51bf611d417fd55a356acc8943fbcd0a886402048b52d5286dab38415bad443af298a9f3cb5ffe32278ec644a0c1401cbd7bf73efffcf3c10c60efd75f7ffdaaabae62bc172ccad9b9540e7ecd2f4d0759eb17b358bc783116101f811c215340fac102cbb9933e0803f29e812e66074e0049ecdfbf1ff018337ae1851780f17c04627db4085a06a24b51b4d5a38f3eca775c410c1b376e8428c4ba01783ffdf4d3a0935ffdea577bf7eef513d347565a82d7144342743df5d45337df7c73506da5968acf02ebfbcfca97e2d00f0000981b6c967bdafee6c646dd361609482938351f889e0eadba97cffec63796fdd77f98381d937d8fb28f95def8ef3f5ef9fd1fb40acdc19e261d1f552427264027b07eedae15b75f4ac4cf8dfecb5ffe72ca94297dfaf4f1b372438c959696023433bdfae2bfcb024ded6e307b1df0e773e7ce85d4c70282208051c15a7e1a6e5e875cd08e8fbfd998866e79f2c9270172000df02b1008900920101ee09d77def1bd20b91495805ce72707954f9c381182036f42157cf6b39f85f1f9a52f7de9edb7dfe6723b699df564702ce438cf3efb2c30dc830f3e88994030f3a55ebf9223cf2a8b050dd9fe5838b02fe013f40c1468ee95ca21dd4bcb7b98aa0b14c9639dc4e7528944c58871377fe707ef7dff87d54b9798944e744655deb3e71dbf9afbe6d7fff6e08655a51cbf9cfc0b4145060599cea6dea089e4512a1dff151760857cbaf5d65b418e982fa804d2040b0b80072500d3100801e83c547b39d386bdd8b76fdfc2850b6fb9e516d00716f0cd37dfc410b02ef02906054a06e2c2d0594b9360bd05366330e8e73fff79fe1474c9b572f111487fdbb66d7e4d86ecaa0cfaee44488765cb9641f2b24084bc183e7c387b1da173c68e1d7be4c891a143874649fae81a743f63c60c2c192b1dcc6de0c081500210ccf8142888573977bf01ccb20d1b36c06ae9a8326ec61e2461950fe84fb986a543e8bcadde1a64f7a0cbaeb8fccb7fb9e85b5f3fb27e8dc9bea41c65f79a36edfa6f7cef95bf7de4e8e6cdc0f31e6523a33f3316b31523afa78e95969aa437205095e6bcb06d20022eecca5380c4c2e601f3b074f763e0b22b09eceb0a90f8ba75eb200bfd816092b145c104142cc49d8bd4a71bae8ef3c73ffe115338efbcf378f7f1cee38f3f0ed8cd5f03a7b11ecbfa08c8971d101cdbb76f0761f848af57af5e5c5f99ca28c5628ce8d21c285de7e6a14387d02f86816dc16a0eb385d109350ad5862fe00584592e857dfce21c908b13264cd8b469137f04603060c0001f3c6421a8f02795a3ced3e4d5893b56cc13548101ef0ebffe96abbff6f5f77ffbff55f61fd0edda9b2d11dffbc6828137dc74dd57bffedeaf7fd663c8e89e43472a4b572f7dc725ba4900dadb5abac22349576e8a79494f5241722f9d67983f7f3ec415402a6f154406a4e3bc79f3b8981c181e9b07a5cab8b9ddd3a2745c81a06c000c2c2006e271b17ae8dfa77b301bf07154ce4dc07a5005f8ea830f3ee0a7c5a4a64f9ffef2cb2fcf9e3d1bf271d1a245005a2c2e5311724653c35a610141182c1760f28236c0c6e007c87ec811f48977d2f420a51bb40c6d02011fdc4828320cd0d0d0c02e4ee81a1e35cdf39d4ede07daf14bd03067a367660cd86a102a7e0db9ce172b9878d06d695cfcc8179aeb8e807d5c3ab822f93aecbaeb7b0d1eae09c7530e3320f835bffef990d9d7f41d3df66448a6909b5f98db5a5f4740df606fcf76fa8f1a3debdb3fa1a8072a61223d2fd151d072701b40dc3eeec7e36152d0da209acd9b374334823a7d1b3458c129e403e8d2d0c79fecdebddb2fecc3257df8b880bbadaeaef68b6c76ce5d9d042dfb7f88274787415266c2004b40f980cd3029f685f8cf934a24e9042db3da0c9e0261c5302fec3216101219a083cba67739af6ce2f543de591fe55b81f4529d507f3a62acf3739cf4056190f4e96856aab53ff9c1be850b28609302f73df2f3c05215966d2c5dbce390256be2fbd9a74351fc0ea43cbe467536a908b9654a12aa8bbff60f832e9b4db1fe568c227f3cab23d20f5683f4e7159a239b805ca4b1f3050c917e3aab1df246a442c7ec48bf236208b923934e858f1e69e7584bab5d52099a9a41f5d209e9cb4c070b1a3a3e08f16b6b660d784259d3421ef13437bbb3432dcb1a31e726abc471813b0dddf3bd4261200c155b108e47a9599447310b9e0964b66104d393889887b97a36e00d1ecee951d9efa2cb686b4d991269d9e9cc2bb8df1fadd14904ed97e50b1e437639d376bfe08f9264fb8f465b85363117bf5990bc7c96f6a7cc54915a313bc7a2b92177a2ff262fa02f65d29954f6c74f3e5186d2eee5522dbd5d6f4f505266e922a0007baff7d8f143675f6b6bd75ca8851077c8a36ff26a9b44db8465d881630a0c423dc4a9c0ac4b17d8a1373c3b4ec9b86d6fcae73e5f52ea18ad206dad3a09e10956260d115ceaa7c132a3d901e24e844548e8e6789a1b62e6104ba7d6f10eea1cabbdc89c8cdca9fe7158bba5e7d3cfe226b31b3e75d57271ba875467bb23fae566b3d32a3101e86c4db8f781928143293a8d2ea5000f5150b2247ffdc973163ae8a5132c73a191c0bc0dfe48080fe047ea58d5a46983afb9c194e9c103098fae3e7636afe40dc9f6363bb47afccddce93255ca86827c723fc90af5e33fb37fa0995adb39f460591ffe04c3a2fc71fd77829543a321fd76f55ac88c8be468b0cb93cbac014f424b47db65bdfbcefafbafbb429be04c65519a06872c5517d6ac67b6c59c7349566794bb84ff1c7f0b015fdabfd7ac7ffab6a9cd53927c9eaeae68057dd29d1310ff1afc5a46941a8ae0f5892cb87779ba181de48160406527e1d3594383763b4f5dbab4005b16141905f8ce00bce6be25748b9caa26535072c5d0d1577fffa740f66dd2dae4e7e102532644599146a00066619305dc567d56f4187ade553ff86fba9948e5ab5c2ebaa62d99beb84a67caa971e769ae494708be90490bdbbd6e519896051d9ecdb7b44e1a0cca11ca49c856120c22de73c498ab7ff658ef4953a4c94c224d7d11c519766845a8a08854c95be72544e1ceb0eb6f9af99def979495a51f72586ca7793b779291483ad1323573a46db90a2f5aaa97beb569dedce63d3ba8028f9290e69ee5c5a8fe54423925d27585747a4d9d3ae9ce7b7b4d9ca21ce29b76e9fed4554bcf6f2be6e189ac152cebb79572a445f25d709240c99e1ce8016d792ade5cbf7ddbc195cb8feddcdcb86f5febf1e3b61d2bedddbbfbc0c17d275dd0efa269dd870fb7ec128ad821cdd0fe058822e99f89a45fd0c483a94453403d60b26d529128a0429bf292c8160021abacacf7a40bfa4cba803c988cad4d3db693468266bd813f501cec790e4182b37ab2ced9bab81fb11741e9c201a25700f25ac5f88aa276b83e2c9d6751edd9b68acada782c4d68bea4eacb9a0e7929daf39c4bbe70765b35053573956905dbad93af293cdf552634c106557b9453cd7c21197c6152c852b21d8a5410b647d7514a88d6b5a7ed3647a1509d0c7ab652c9594cfdc98bc3859961ee374e326a1c56c4b885ceaf48845bc615efd2cd5c1373af54dc5caf754cfd64c309264cad35919026ae5f9bb2e3c6c015fc3aa4b5486bb4c52f15604605aec8946f51e5cfcbdfac82927ec14ccf82d590b30211bc6d63db1d3c52c9495814d081a92b629ebb435bb630666e30a6bf30fb5598e16cd3ce66c0536cc5768e62fd622bb622e9175bb11549bfd88aad48fac5566c45d22fb6622b927eb115dba922fdce9397a77fcdb993964e36edd42cf2395c5ec960f4c85bea0d6eeba3772fcedc4aeba7c3e96f9acf90ee5595d42bfda1fb69a11bb49936ffd666bb4fef5fc3097ea1f3fc16e95361e8f66abb5d45bba32101e11f7207af9f46c596a790abb34825944ee7d925bcc95eeafbf91f83173d8337e8821725b3d13ee6d835983623f51e77e8ba939f4b3a742f3e532a0cdd95eee84e5d84a228748baadd222ba9375fb3a0927ca891f41f202a9d1c928f9d041f042b594426f543b95342c927fc07ca7a9ea9e50042174cfda54c4d649045b98e54ae0bde956e377b4776fd07493c35cb4847716f41e599354b8774e3e9893a7294fa1dc9df74627364faa3fab97e8239a3adc03d7c2be73a054142f19f9e237f18d874948522f7a11dc709dde7e72c88d64773e3640d007c12f4e355826cec67a50ce52cc85d5afb4b57308a0fddaccf87dd12cade952aa4d261bc74736ef21c962c59d2d0d08017575c71455555d5f2e5cbf957fec2f0e1c3870d1b96cb26713f98cfb66ddbb66fdf8e37478f1e3d6edc38d7759b9b9bdf7df75dcff3faf7ef3f6ddab4764d91acd5287a3e71e2446969e9cc9933f1130fb069d3a60f3ffc109f4e99320593c20364b78ba11c387bf6ece15ca213274e1c39722497ab79ebadb7d07fefdebd2fbffc72bffa5524968c2f3b6a6a6afaf5eb976fd883feebeaeab053786c0c57565696664da42c5a535313a77fedd6ad1b671b4fb54223207d66e5975f7ef9fcf3cf1f3c78704b4bcb73cf3d77efbdf772725dde98f7df7f3f973b6641e1bd73e74ed0fd35d75c835fdf7cf34dac2098ead9679fbdedb6db2a2a2a40910b172e9c33670ea7954cb580336a10f6f3e6cdbbe8a28b30af23478e6094071f7c1073014b5f77dd7598ddebafbf5e5e5ece493f7391fa60a7dadadaad5bb7a25bfc8a29a043cc6bfefcf9980be6b86bd7aeb7df7e1b32c5af8fc27b9935e9f838a7b1b11143f7eddb37dfa47fe8d0210c8195c4b8980e4406e4488470df7ffe783c8ecdc240bcaa870f1fc6ec4234d0e5b8e9ea41d0192603198cc17af6ec3960c0008c57651ac8a2b5b5b557af5e7894dca707fe59b76edda5975e1a336dd6ac596bd6ac397efc38a804436074f01ba69d0a9ab31b8e4b2df0bc060d1a0495827981dfaebdf65a70051e66c68c191b376ecc25a4d6f7deac5ab5eaaaabaee284fa5026070e1cd8bb772fe803eb8977a00730f190ed9e0be6618d01cae0f28c05403bd09c5840468f4c15f900fdd88bfafa7af4cf9b020ae4b242219cd3e5b8e9ae08babee79e7b3801322fa89f3e175cb16cd9b2cb2ebb2c125b0abd5d7df5d5a0063f2d3028121aadc6344e663d6ad4a850e6ad5ce8f2861b6e8014615a015355565606ed42c82d1666b998effc9090f7a07b60362c2360cf8811230e1e3c88d9f12d0dfc844081b609a6f4ca454e33ff8016314461a2e1219e7c6f1b2692a71bf45828a029e01cf6ce810db84e47a66b958130e001b0a050d61093201166410833d0bd5f4523474f1c26c6c4879ef7efdf0feb62faf4e9e87cc890214b4cdbb2650b5efba4990b94648aa4bc14267dc31b6fbcc1e21f440950ce92922571d65c1dd4c26caf4379bef0c20b180e14897905531e60e2cc84399a89412f70212f4ffa75ac2041304100c568470ff9b8a155a036a16a585086321677396e667c89c19e7aea29103af4b55f5b65f7eeddd0e3ac55b39ba75f4124e82c5abc78316ca6871f7e986524a6076dc09f3ef9e49377de79672e45aa83e3e2050c265832975c72c984091330afdb6fbf7deedcb9d8390c7df1c5176323b3060ca99c09eec228fbf6ed7bf5d557a1cdfc1ccbbcbc41076bfa49833b2291dcabfa65c175a007f0331bf1d11e290413cd726d188c8205dcb1630797cb0daabb2ea93103e72608f177bffbdd17bef005a052f678b065c3a82e17e0e1a7c9f509e5e9a79f1e3366cc5d77dd858140e2584d4862ae9f8e6f0e1c3810f02e55ac662192590c03dcdf7ffffde3c68d63420127dcddd6d8a7943501059d9bbffef5af4b4d19224c04221f8b06458a05f40bc4c38267d59aeaa8ce1af0e478e8912958ddb66d1bac17bf7445b48d6784e5c242715e7bfcea98961a4d1319d6c718cf3df7dc9ffff99fa353ec1ffbadf01040ab40deb940822026e6b2af0054b032c1d05cfd06a380d956af5e8dd73c791888bebf2217cf31df287ffffdf7efbdf75eb6aa61d76238982e90f45c09f09d77de993c7932c47f8e580e0d501e7bc666eee6cd9b41e563c78e85e9c283021c831f42e791d99d5287aa59162cd9115419a42fa613bcd41b6d4a5696743d7af480b58921b09230ff42f50ad2dd9a74b2af61bcc6c646903e533c73028c36d01fd7bdc2a364eae46e774a6c3c2c58b000b68baffa870e1d3a73e64c60ee8d1b37625c3c03900f6071e7b57742d9d73a6ad01e5cfdcfa7d13973e64093bef4d24b4cfa180b4655a83448aae1d5a51dc97fbb68d122369ac1d8975e7a295e60f1c1eae8bca2a2e2965b6e099d4f877634fdec6bc17813fc09341b96315306c834fb1a947390f1c0eaec0be9f20fb3c8be867503cac75a6108888c7633aa7734df8c130f86f229f8eee7f44d994e9e26f8119fda06dd7c9c8403af99c8522b17b41b4f9a0ee9b371e68fe56716e182d87cc8da2570ec9cf44354e857890ba5a2e7070e569d4875eaa74ffaa9411f5948df4c493fb40be917814c9ff4fd35c9c58cc920f1a05f6138550e4565450557870bd1f8296e7ca5cfa41c8af1cc5d95fbf55cf98ccc7f061e2b682ce6124ee3137728082f68e287283ecd4a805d3a43ac9c4a13643cd310bf458bb5fc95f109cf67f24c7d7d4ea69b67e53f0c30352aa313177e54db999a053f4ffb970acc8274195ae1d3b676c1a91a289502b336f98ab7b48aed1c6d45d22fb622e9175bb11549bfd88aad48fac5566c45d22fb6622b927eb115dbd943fa85bcb47f4e55a43a7397aef38abf673a49487fe0c244b7166c35cfd6d44bc153ccc290636188b290fbc533723858a030b1dded5ebacbdf017bc16e69f8e109f9a6123fb0a260fc1c0ceec8eb40852c28c4fb2572c9b4719ab7b3755e67df029e929d9285096c3aeb61f15986df0abc8085df29ace7ff2fc0002084b3c73f4c6ce50000000049454e44ae426082	image/png	calender.png
296	\\x89504e470d0a1a0a0000000d494844520000005a0000003e080200000024af9b190000001974455874536f6674776172650041646f626520496d616765526561647971c9653c0000032269545874584d4c3a636f6d2e61646f62652e786d7000000000003c3f787061636b657420626567696e3d22efbbbf222069643d2257354d304d7043656869487a7265537a4e54637a6b633964223f3e203c783a786d706d65746120786d6c6e733a783d2261646f62653a6e733a6d6574612f2220783a786d70746b3d2241646f626520584d5020436f726520352e332d633031312036362e3134353636312c20323031322f30322f30362d31343a35363a32372020202020202020223e203c7264663a52444620786d6c6e733a7264663d22687474703a2f2f7777772e77332e6f72672f313939392f30322f32322d7264662d73796e7461782d6e7323223e203c7264663a4465736372697074696f6e207264663a61626f75743d222220786d6c6e733a786d703d22687474703a2f2f6e732e61646f62652e636f6d2f7861702f312e302f2220786d6c6e733a786d704d4d3d22687474703a2f2f6e732e61646f62652e636f6d2f7861702f312e302f6d6d2f2220786d6c6e733a73745265663d22687474703a2f2f6e732e61646f62652e636f6d2f7861702f312e302f73547970652f5265736f75726365526566232220786d703a43726561746f72546f6f6c3d2241646f62652050686f746f73686f7020435336202857696e646f7773292220786d704d4d3a496e7374616e636549443d22786d702e6969643a39444443363634434443304531314533413346314146453046384136434435302220786d704d4d3a446f63756d656e7449443d22786d702e6469643a3944444336363444444330453131453341334631414645304638413643443530223e203c786d704d4d3a4465726976656446726f6d2073745265663a696e7374616e636549443d22786d702e6969643a3944444336363441444330453131453341334631414645304638413643443530222073745265663a646f63756d656e7449443d22786d702e6469643a3944444336363442444330453131453341334631414645304638413643443530222f3e203c2f7264663a4465736372697074696f6e3e203c2f7264663a5244463e203c2f783a786d706d6574613e203c3f787061636b657420656e643d2272223f3e67d8e379000013b04944415478dad49b097455559686df9481040853800c400c83420c21320f1918044bba64922185e0d06ab753b7cd20ba94b6c4815e82b60c6551163694968a555a16b18010863083132a4134210809c12440080921f37bb7bffb369e1cee0b0156db10ce62bd7573ee74ce7ff6fef7bff7b9d8cf9f3f6f6b62cd300c87c32107d263b7dbe5985fa7d3191010408f7e4b6d6d6d4d4d8da553ddebf17878a07a5a23cd656b7a4d264f930399891c04050571c18913278e1c3952517e9e9ee62d5b84878747444404070773aabababaaeae4e4d5e3d01441a04eb06804381a28c850610f4e4e6e6a6addff0d5575f9596963abc0d6369d1a20570c47a5bd44d37613b20022e82c5555987bd093a8b720dd59a356be6aead5bb56ad5b66ddbb08581030776edda352424842bcbcaca4e9e3c79f8f0e1a3478f721c1616969494346cd830bf007f01451e28a05cd6409a221c62116ae860515252f2c2f3bfada8a8b8f7de7b872526a8b3caa1f8adacaccccacadab97dc7fefdfbb197bbc68f1b356a14d77097807b2506d244e1d0ede2ecd9b3b366cdea1411f9f4d34f07350f664a6eb79b61fbf9f9e141cc0f603cdee67299be5f5858b876edda8c2d5b3b477579f4d147a3a2a26059b8f64ab8c32ee0e9f679857675551475b5766198533460c7eaca2ab0c0419e9dff9cc99495559b366ddab1638749a2cd9b0307bfddba75ebdbbf1fbe53eb6d20c813f2f2f256bef5c7acefbfffe7871e1a3d668c613398e9953a8b0c4207c5b7e7528efdff441f70a4bfbfff2b2fbd8ca72c7a6db1f46766667ef8c19ae8e8683802eb802c7e2a2c389e9bc754131212a64c9dcab4abaaaa582433d018b64f535357af5e3d66cc98871f7e188875442c1ef7b375949f37ec0dc7678ee9e4ada20268f8a76e0e2a0afe5234a123cecaa7fe7ded471f7db464d9d2d6ad5bb36cf49f3b778e796211fa8d4cf2f37d9f7125fd73e7ce6dd3ae2de3c4b86053060f822fbeb060c0800173e6cef5181ec06ac4b42fe20ece71111ec8caa8cee2e2e29c9c1c96a873e7ce3d7af480ae79ca2f1e442c8609163fe59f983367ce934f3e3970f0203548a6c7951622c0889c0e273a6ce9d2a5070e1c78e1c505f0859000d3c177728f1ec3e3121313fffdc9276beb4cc12658f82262174ea229b0cf9596e178478efe989f9f0f8d0144a07fc0e9d3a7cbcbcbe7cd9b17137b2b00ffdffd4531947e201283e9f9b9fc9e9a3b97d5fecfdf3e4fb0843b7d9d5477679ac8b0b756fc61fbf6ed8b162d0a8b8ca8f082282a363b3b7beeec393366cc983c758a7893ae74958dbb5807d0023f08191488de1c80116a2f343494f00ed27d7ac7d18f1df2d05b7bc7fe22f4a9f3b7d29d327acc73c3faf5070f1e5cf1d61fe8c416e8543ad5e26232189e505e713e28b0d9c3fffa2f050505c0b1e8bf5f0385daea1aa00450ecfaf1c71f5fbe74694c4c4caf5b63b01d350b1d68d7e1acecb7df7efbcc9933e293919191f1f1f14010d12952285adecd9f346092ace117f1178bee943f0303038ffd7874d9b2653367cec43dc517c4bc75e6ab273f85a9cd941eacee134f3c417c8571a74f9f6eba956103117c9cf8b277efde152b56bcfefaeb20250acd425eaed4d45444cb3df7dcd3ae7d68870e1dd409eee759fcca8a01969ca531625ecc3b14c5fad2e19534dd59b008864827ce4f3421164c4b4921a662d863c78e95244de9cb8baccc6ec336204e39cbf550e9b809e3f38ee532429bc36e78cc4b301308e1be07ee9ffd1fb3b66edd7afb98d1beab62c281f8efd7af1f2e20afa4e9524f8d9b99b76fdf9edc09b772fab9dab66d2b06ecfb443d53f8b9df9b9eda3d0ccd6e70ec6080e69fa680f212a197b973b20faf5fbf7edfbe7d77fed3589c3c6dc386654b969828788c5f8fbb8bb9a9448ef5e0c9f470d6b4538d53b80044c68f1f8f3b6053e64884a7ec667f972e5d86260c5bb76edd881123c4e82c3acb999c94044d262625714e6c417994bef2409b979b9796968604dabe6d3b991296ac1cde57a76849badb6be7369b9b0b6c86d3e1b1010473308160628c9be5faf33bef324a6c04e7bffdf6db99e76b8b5f2b3e73a64debd699070f76edd6ad53a74e0c8f0b307232175611a30e0e0af6f3378300a7d432f0cb65788d691d3f3709222264d337a6a3dc2222235488a97716bc8014c853576ff996a64a0fad5ab56219c10e15d022b8398f603e922c58884d5731208eb5da3d6ebbc330edc16338310c42a6d3e5b43b366d4cffe4934f1862dfbe7da74e9ddab3572f2c8651f2a2b8f83ea8acf9f3e71f3b768c0c2d2e2e4e20e6d437fbbf86dadbb469837ff5ee13376edc3858cfd41a3f9748842c7ca997fe9b6fbe193327af89ef7b9bc5094ceb9836752aca226978b2e0a45bbbce73ccfc6451114f412fc3a920b865eb969d3b7742b758a058b2b2299ded9c86cde3f05a102e6ec7954d4bb1bb6c8101cd3efef8e3952b574e983001e6eb735b7c68fbd0ea1ad6be5ad89aa03674c8905b7af6ecdea33baf90b1f122567ed0c081f03d5cc6980b0b0af6ecdd0382ddbb77e7942e492460e9b3e521d863ae17df6109c330164b9474454444103ec152a48765567a22c80a70734a4a0a2b9975381b8032bf3dc07c4084e8c5409983b08f4efb7530bbc730c98ec1b0bc76f0b139bcccc7e2636893264d82f0103512b0e45e40e114911e57620052c290670a2251d137f12f2131116bcac8c878fffdf78f1f3f3e63fa3d18bbe8d746320f80fee28b2f30021c50b8b2de1520450621b19d4b03bc8d1962c0c1de26c75c4ae86ed9b225b8d6baebb011ac1d04cf9e2981c3656ef8113d5c7f91b830678f77d81db88ecde9a555d0b0bb3deef11327f0d2356bd6f8aea4484f11a362f93a55f3273071b6aaba0ae0860f1fbe78f1e25345279f79e69992625331e833f4d504d81a0f415b34500d83904e9d3a45c860359809afa9aac00d2be161deeaf6369ec82f0bc8c1a7ebfe515d5b43eeb06d6b0617e3c0b9b9b938f3aa55ab8003c826de3da957af5e58ef85386ad8fc0cbbcb2fc01ee0b0b96d95b5b56e5b8dc366300dee4d99fe9b156ffe3e36ae37f6c5d374c1ea5bda53c74267c205fc820beb346bf66c84eccb2fbffceaabafe2118c5fdd68c9d19932a77c5379130e14175ef7bb65cb870e1dca738106612e51468a08c20bb220e4912411380b613c3eaecfdab56b738f1e2522201088d6702d9ccfc462636315a1626a0e8f3be3ccf77b8ab206b68b1e11d6abba0a43c05edcbc65d4a851e9691bdffdd33baffcd7422c456281c54cd44c740b62e538562ccec88932cf3efb2c34442e3765da5475a345ce8ac6e15102a5953bfc03031effb727befefa6b73b611e10858b99adf7a3af4fa11f7030704c601f08fbc7d14e4bc65d3e6cf3efb2c2b2b6bf0d021c9894987b20f756cdfa1bcdccca38302836d75b51b7efa725749cee99ab2cec16ddf3c9a7ebeecdcb8a8840aa38a0b7845c1899f30b42143865ca178e517cf45fbc017a4763d7bf6643095004c80aba80869dd6ae67df7fef5c3bf8c1e3dba65ab10495e7deb603aa1584eb90018e3494e4ebec2ec5bafb561ed6444bf1a7b27e36bd5a62df2aa578f5e67cbcfbaabcb438242338a32d38abfadf1187d5a843f1f3631a0996b7fe191d70e7f3a263cdee167385d817535b5e41690d78c7b678a3d36522e9091088be1fcfc897825203ef0c003a42a6694356c901ae6b6397dd3c68d1ba7a64cd3a9544f9d8504a03905b13a6b1a02cf6a7c1cbee84abfd03e768b4d793c75e5e7abebaa6b1058addbb44f3bbe7fe5f18c91a1bdbe38fbe3ead29c1911c0edd8589cd9ae6d5b5760707575a5c38c30368c1c382cb1acc126a9dde6cd9b912a38f8430f3d3460c080052fbd0877ce9df714736386601adc2c88a545d7a1d3316109dbba7aa04182921ce915a00b766411d4969d1edf4e0b28dc58e56d64e14e9477805fb30014b42dade49b5d2507171d4b5b9593ba3d377ddab76fcc3bf07e5645c153e1bf72d96a5d0e335ee0a78f3cf2087ebaeed37f4891a1119721ae831d710d8f86bc5f79e59598d85b312e5c356dfd06ceaa308c84019743870e29c6d5b190622ac3867d15b3d4075acb822b6d2adcae338d227c5d9ed57bb5c70d3bfa135351e175b6916d628bca8ee41ddf6173d58686c404d90393dbdcbcbacf631d83db95d754d539cc27336886be70e14284e9271fffcde5703692078238d671fffdf7c3e5b3e6cc3e5b569a939343a28024ddb2658b381a492d8fc5dc4c419095edab38e418858245e3e94a3d36b0ede45b40b63c4ef71a451ff511d1c4d19bf2b8ed5546e5af3bf4ffd3e0057f3fb127ae55f47d9d92ba340f4387d5d655551bb54ebb8b51f8b95c2c29f92bf19fdbdf78e30d647874b7ae2adc5e2a5d600e212121086db1f9c18307635ff9f9f978103d92e031d58282029d29652e92768223ec431c04624bb9d465d9f5332ece0e9539e9151abd60535f8f32abdfa63722badc868db83c33326966e744c37c8ea3aae6bcdb2698a17dddfe6619d3f5eebbefeeddbb972482c03c72e4c8b6a1ed24c56ca462242abbd8dba462dabe6307d0c1fe814325d92c7e51511126438f2ee1580094445e5e1e3625c85ae49fcb529bb3c8924b6dd834708de1315401029565d86bdc1501ee00c36dab765498cff060cb75c28a7e7ece13f9f9f8fc830f3e387ce408794e8d195d6a1d36fba5a894718ae47dfb8f2bc939f1147aa44a7221a69a59c0858bb13274adecbce87c4762613a699f3ee69f4e356a8d3b748f6d90dbafa4326a281a927ccd4c55ecd5f083bdc64cdb0cb3c0e13125fb05a5880e66aca40f92a18808b660a1b49c944bc182d8fcfbdfbd999999496461a9a5de61eae63ab7ac841ab0c90bee8b24965455f6ecd9131d1d1dd9b993590df318d761cbda220d6558303f7a27b4437bc90374df54062c7a9c880329a4a6a64207dc7b5397a865cb96e159041a1c071507946223beaba82f24d740a2c426f8583cc557555d0b382c59b224be9028ab24d240b7504b8622a5638205193d331f366c98d8b96c207141f1a9d31cb70869a98752338afbfbbbfcfd2c3165cfaedd247883060d9205f0dd76ba461f34a857ca1c584f161cf2536998daf1b21490e400a1d8dbdb549ea2ec0ea7e34f5207fd5e9ed9ad4777cc4125fba66755d77cfef9e7e456cd8283a456d440adf41a7fac216365a024d030bce420b271a318dd526d947e4b0056dc7ff0e041c22a70c8d73f340efaf7ef1f1e192115c30bf374b9becf3a545252929094a897752c9b4f8e6bbf292f25cf949494ddbb77ffcfcab7ddb575b80c36ac076fb5fefafe909e5cd0cf82636266992f3e5e1e2bfd3857ff8103c2c3c355d896a9eedbb7af63c78ea4ef4a4cfa16c9af1d1cfa54b155f428b9c6ce9d3b89116bdeffe05c6919a000932e67f466311971baf4b48da0903c62b8e2456996a21c642cb21dde913875a904d5712db9432fe1800839d8f2e5cbefbce38e1ddbb63df6d863a467528b53ebaf2b60bd49d02d2d39bb76eddab163c7e22cf59f3e19f5e45dbfb7e672656767237f11be16997b49917ecdb843d9083c07ab4d4e993671ca6426b66cc9d2d385452933ee91fa5b23f98bd462de7bef3da4fa844913a5de79c1d7ec0dbffac037dfa2623b4775d1771b7c81be9e9fcac9a699e4da13274eec1cd9e9b9e79e8b8cea929090a0f6df75c5ace6c0f5df7df71d0b3e6fde3ca091f0e1fb1d8adc2b0ef8c30f3f8485856153b29b77c9b4a8297cf184e3e0dbfd06f44f4a4afae82f7f65c4f2e182a5eca076ea09aeab57af8674c2c2c3d5e73a52e2d6777c24e543e6949696e2299191918a719b221c96ba3907f02ba9d7a9a293fa07167a210f6601bbc5af2e22eb8b89bdb5b2aa526d1b1f397284d8217ea467db1c800531283434f4b209c7f58443af9ee8fa5d165cafbce8f2e1c30fd680ddf494df08c548e0e0c6952b576edab4497d90ac93b7ecd7b66cd9f2b2c9d7f5b70e3dda9d3e7d1a63865f2d5f39a8a483f47cd7ae5d77dd7597d3cf255b070247eed163270b8b468e1c2971c7f29180388e329ca60b87e24be9215e76b9298a782182d2aa911c0e34283da2bb74facccaca22afb9ad4fbc85299542078bc68b29d739b29811f1625128db2e34a67de15b049fd22ccc421e2cdba33aa624bb1111116642585b63d19a1cc01ab06c717171231fa75d6738ec4603e21d652d959b86bf9f31cc028f69f34eebe78d302562ccdcfdd460528fc570489148ed79826c6e59b66fb54f889a4c63401885fe7984b54a60b7312bf92c49fff048f8c29789141c12b370b4b2d252084865cfbe1f2d362138645896bcd682484c4c0cd3cbcfcfb7d00aa2432ac9fa37496aceb4c4c444b04e4f4f97f064d9a66c5a32acc1af6b7c4f319fc84e9d10977b76edd6e1e3187720e8c824d5f68afe7944870e1dc68c19b366cd9ac2c242d970b2d4a59a101c964f6b2e950d9b11d46e239a7ef9e597c5a74ecbaeb55cd0ad4777b4565141a1e8370ba0a250a64c99026a4b962c91e28082bec9c161d9e5f3f514e53eb0c6a02183892ca9a9a9aa13f7b9e5965b5022bb77efb6dbaca500951c0506359b3d7b36849a9191a16fd036b00b77ddeda29192af7e2cb222393979efdebd65656689447c81d5468cecd8b1039a954ebd98240f81a47bdc72f3fcf9f3711c5d9e3421ebb8946ad63337dfff5c3278f06096f7e0814c65ed5c70c71d779494946cdebcd9c23eaae0ca01213c2e2e4ebec669700c4d8e4a2f95e3e9c1b84dbbb6b171bd737e3ca23a319076edda21dedf79e79db3674a24b56df069e6e74d3fef4536d104ffaaac49aa6418083254ed0f8818b97bca644864e1c285d595556a4fffaa3e876eeafff9ab9178ac7658952f040707633b2f2d78118f9834f96e29be5fd5ff47ba51e19052804a5e5571507293534527a3bb756dbcd273c3c3d1e0a6baa55ffd8f5bb5e17455cd7503594483ff3fc9d22f5b76be947185ff4fcd7163f185ef2e945e37b9ac655dfe1537967534f8bdfc653f60bbf2f6bf020c00789aea2262a4d64f0000000049454e44ae426082	image/png	img055.png
\.


--
-- Data for Name: usertypemaster; Type: TABLE DATA; Schema: nav; Owner: postgres
--

COPY usertypemaster (id, name, abbravation) FROM stdin;
0	Admin                                                                                               	a 
1	Patient                                                                                             	p 
2	Care giver                                                                                          	p 
3	Family member                                                                                       	p 
4	Nurse                                                                                               	c 
5	Doctor                                                                                              	c 
\.


--
-- Name: usertypemaster_id_seq; Type: SEQUENCE SET; Schema: nav; Owner: postgres
--

SELECT pg_catalog.setval('usertypemaster_id_seq', 5, true);


SET search_path = public, pg_catalog;

--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('hibernate_sequence', 411, true);


SET search_path = nav, pg_catalog;

--
-- Name: address_PK; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY useraddress
    ADD CONSTRAINT "address_PK" PRIMARY KEY (id);


--
-- Name: careteammaster_PK; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY careteammaster
    ADD CONSTRAINT "careteammaster_PK" PRIMARY KEY (id);


--
-- Name: careteammember_FK; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY careteammember
    ADD CONSTRAINT "careteammember_FK" PRIMARY KEY (careteamid, memberid);


--
-- Name: certificate_PK; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usercertificate
    ADD CONSTRAINT "certificate_PK" PRIMARY KEY (id, userid);


--
-- Name: contactmethod_PK; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY contactmethodmaster
    ADD CONSTRAINT "contactmethod_PK" PRIMARY KEY (id);


--
-- Name: designation_PK; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY designationmaster
    ADD CONSTRAINT "designation_PK" PRIMARY KEY (id);


--
-- Name: disease_PK; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY diseasemaster
    ADD CONSTRAINT "disease_PK" PRIMARY KEY (id);


--
-- Name: distresstypemaster_PK; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY distresstypemaster
    ADD CONSTRAINT "distresstypemaster_PK" PRIMARY KEY (id, categoryid);


--
-- Name: distrsscategorymaster_PK; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY distrsscategorymaster
    ADD CONSTRAINT "distrsscategorymaster_PK" PRIMARY KEY (id);


--
-- Name: education_PK; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usereducation
    ADD CONSTRAINT "education_PK" PRIMARY KEY (id, userid);


--
-- Name: expertdetails_PK; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY expertdetails
    ADD CONSTRAINT "expertdetails_PK" PRIMARY KEY (id);


--
-- Name: experties_PK; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY userexpertise
    ADD CONSTRAINT "experties_PK" PRIMARY KEY (id, userid);


--
-- Name: id_pk; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY loginhistory
    ADD CONSTRAINT id_pk PRIMARY KEY (id);


--
-- Name: invited_PK; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY invited
    ADD CONSTRAINT "invited_PK" PRIMARY KEY (id);


--
-- Name: mailtemplate_pkey; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mailtemplate
    ADD CONSTRAINT mailtemplate_pkey PRIMARY KEY (id);


--
-- Name: medicine_ID; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY medicinemaster
    ADD CONSTRAINT "medicine_ID" PRIMARY KEY (id);


--
-- Name: patient_PK; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY patientdetails
    ADD CONSTRAINT "patient_PK" PRIMARY KEY (id);


--
-- Name: patientcare_PK; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY patiencareteam
    ADD CONSTRAINT "patientcare_PK" PRIMARY KEY (careteamid, patienid);


--
-- Name: patientdistress_PK; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY patientdistress
    ADD CONSTRAINT "patientdistress_PK" PRIMARY KEY (id);


--
-- Name: patientmedication_ID; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY patientmedication
    ADD CONSTRAINT "patientmedication_ID" PRIMARY KEY (id);


--
-- Name: question_PK; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY securityquestionmaster
    ADD CONSTRAINT "question_PK" PRIMARY KEY (id);


--
-- Name: user_pkey; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- Name: userdetails_PK; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY userdetails
    ADD CONSTRAINT "userdetails_PK" PRIMARY KEY (id);


--
-- Name: userimage_pkey; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY userimage
    ADD CONSTRAINT userimage_pkey PRIMARY KEY (id);


--
-- Name: usertypemaster_PK; Type: CONSTRAINT; Schema: nav; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usertypemaster
    ADD CONSTRAINT "usertypemaster_PK" PRIMARY KEY (id);


--
-- Name: fki_address_FK; Type: INDEX; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_address_FK" ON careteammaster USING btree (address);


--
-- Name: fki_caremember_FK; Type: INDEX; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_caremember_FK" ON patientmedication USING btree (carememberid);


--
-- Name: fki_careteam_FK; Type: INDEX; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_careteam_FK" ON careteammember USING btree (careteamid);


--
-- Name: fki_certificate_user_FK; Type: INDEX; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_certificate_user_FK" ON usercertificate USING btree (userid);


--
-- Name: fki_contactmethod_FK; Type: INDEX; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_contactmethod_FK" ON userdetails USING btree (contactmethod);


--
-- Name: fki_designation_FK; Type: INDEX; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_designation_FK" ON expertdetails USING btree (designation);


--
-- Name: fki_disease_FK; Type: INDEX; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_disease_FK" ON patientdetails USING btree (disease);


--
-- Name: fki_distresstypemaster_FK; Type: INDEX; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_distresstypemaster_FK" ON distresstypemaster USING btree (categoryid);


--
-- Name: fki_edituser_FK; Type: INDEX; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_edituser_FK" ON userdetails USING btree (editperson);


--
-- Name: fki_expertiesuser_FK; Type: INDEX; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_expertiesuser_FK" ON userexpertise USING btree (userid);


--
-- Name: fki_homeaddress_FK; Type: INDEX; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_homeaddress_FK" ON expertdetails USING btree (homeaddress);


--
-- Name: fki_medicineid_FK; Type: INDEX; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_medicineid_FK" ON patientmedication USING btree (medicineid);


--
-- Name: fki_member_FK; Type: INDEX; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_member_FK" ON careteammember USING btree (memberid);


--
-- Name: fki_patientaddress_FK; Type: INDEX; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_patientaddress_FK" ON patientdetails USING btree (address);


--
-- Name: fki_patientid_FK; Type: INDEX; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_patientid_FK" ON patientmedication USING btree (patientid);


--
-- Name: fki_practiceAddress_FK; Type: INDEX; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_practiceAddress_FK" ON expertdetails USING btree (practiceaddress);


--
-- Name: fki_seq1_FK; Type: INDEX; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_seq1_FK" ON userdetails USING btree (seq1);


--
-- Name: fki_seq2_FK; Type: INDEX; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_seq2_FK" ON userdetails USING btree (seq2);


--
-- Name: fki_userid_FK; Type: INDEX; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_userid_FK" ON usereducation USING btree (userid);


--
-- Name: fki_usertype_FK; Type: INDEX; Schema: nav; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_usertype_FK" ON "user" USING btree (usertypeid);


--
-- Name: address_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY careteammaster
    ADD CONSTRAINT "address_FK" FOREIGN KEY (address) REFERENCES useraddress(id);


--
-- Name: caremember_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY patientmedication
    ADD CONSTRAINT "caremember_FK" FOREIGN KEY (carememberid) REFERENCES "user"(id);


--
-- Name: careteam_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY careteammember
    ADD CONSTRAINT "careteam_FK" FOREIGN KEY (careteamid) REFERENCES careteammaster(id);


--
-- Name: careteam_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY patiencareteam
    ADD CONSTRAINT "careteam_FK" FOREIGN KEY (careteamid) REFERENCES careteammaster(id);


--
-- Name: certificate_user_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY usercertificate
    ADD CONSTRAINT "certificate_user_FK" FOREIGN KEY (userid) REFERENCES "user"(id);


--
-- Name: contactmethod_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY userdetails
    ADD CONSTRAINT "contactmethod_FK" FOREIGN KEY (contactmethod) REFERENCES contactmethodmaster(id);


--
-- Name: designation_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY expertdetails
    ADD CONSTRAINT "designation_FK" FOREIGN KEY (designation) REFERENCES designationmaster(id);


--
-- Name: disease_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY patientdetails
    ADD CONSTRAINT "disease_FK" FOREIGN KEY (disease) REFERENCES diseasemaster(id);


--
-- Name: distresstypemaster_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY distresstypemaster
    ADD CONSTRAINT "distresstypemaster_FK" FOREIGN KEY (categoryid) REFERENCES distrsscategorymaster(id);


--
-- Name: edituser_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY userdetails
    ADD CONSTRAINT "edituser_FK" FOREIGN KEY (editperson) REFERENCES "user"(id);


--
-- Name: expertiesuser_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY userexpertise
    ADD CONSTRAINT "expertiesuser_FK" FOREIGN KEY (userid) REFERENCES "user"(id);


--
-- Name: fk13c1a0007bfb33b; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY distresstypemaster
    ADD CONSTRAINT fk13c1a0007bfb33b FOREIGN KEY (categoryid) REFERENCES distrsscategorymaster(id);


--
-- Name: fk69f22bc83563d6a1; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY patientdetails
    ADD CONSTRAINT fk69f22bc83563d6a1 FOREIGN KEY (disease) REFERENCES diseasemaster(id);


--
-- Name: fk69f22bc8b44cdf63; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY patientdetails
    ADD CONSTRAINT fk69f22bc8b44cdf63 FOREIGN KEY (address) REFERENCES useraddress(id);


--
-- Name: fk6b43ffcc591fd2c7; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY userdetails
    ADD CONSTRAINT fk6b43ffcc591fd2c7 FOREIGN KEY (seq1) REFERENCES securityquestionmaster(id);


--
-- Name: fk6b43ffcc591fd2c8; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY userdetails
    ADD CONSTRAINT fk6b43ffcc591fd2c8 FOREIGN KEY (seq2) REFERENCES securityquestionmaster(id);


--
-- Name: fk6b43ffcca8c7684f; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY userdetails
    ADD CONSTRAINT fk6b43ffcca8c7684f FOREIGN KEY (editperson) REFERENCES "user"(id);


--
-- Name: fk6b43ffccf8e37d2a; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY userdetails
    ADD CONSTRAINT fk6b43ffccf8e37d2a FOREIGN KEY (contactmethod) REFERENCES contactmethodmaster(id);


--
-- Name: fk7a18b9b6c2b631b6; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT fk7a18b9b6c2b631b6 FOREIGN KEY (usertypeid) REFERENCES usertypemaster(id);


--
-- Name: fk9822e3c452b48468; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY patientdistressdetail
    ADD CONSTRAINT fk9822e3c452b48468 FOREIGN KEY (patiendistressid) REFERENCES patientdistress(id);


--
-- Name: fka7471db3f005d950; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY patientdistress
    ADD CONSTRAINT fka7471db3f005d950 FOREIGN KEY (patientid) REFERENCES "user"(id);


--
-- Name: fkb2512ff6d2abc096; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY loginhistory
    ADD CONSTRAINT fkb2512ff6d2abc096 FOREIGN KEY (userid) REFERENCES "user"(id);


--
-- Name: fkc192db89257e5934; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY patientmedication
    ADD CONSTRAINT fkc192db89257e5934 FOREIGN KEY (medicineid) REFERENCES medicinemaster(id);


--
-- Name: fkc192db8989d9c1b6; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY patientmedication
    ADD CONSTRAINT fkc192db8989d9c1b6 FOREIGN KEY (carememberid) REFERENCES "user"(id);


--
-- Name: fkc192db89f005d950; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY patientmedication
    ADD CONSTRAINT fkc192db89f005d950 FOREIGN KEY (patientid) REFERENCES "user"(id);


--
-- Name: fkd1994b4d3a0781a4; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY expertdetails
    ADD CONSTRAINT fkd1994b4d3a0781a4 FOREIGN KEY (homeaddress) REFERENCES useraddress(id);


--
-- Name: fkd1994b4d546cbf01; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY expertdetails
    ADD CONSTRAINT fkd1994b4d546cbf01 FOREIGN KEY (designation) REFERENCES designationmaster(id);


--
-- Name: fkd1994b4dee7595c8; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY expertdetails
    ADD CONSTRAINT fkd1994b4dee7595c8 FOREIGN KEY (practiceaddress) REFERENCES useraddress(id);


--
-- Name: fkdba3b0fbb44cdf63; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY careteammaster
    ADD CONSTRAINT fkdba3b0fbb44cdf63 FOREIGN KEY (address) REFERENCES designationmaster(id);


--
-- Name: fkdbd91133ddcae7a5; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY careteammember
    ADD CONSTRAINT fkdbd91133ddcae7a5 FOREIGN KEY (memberid) REFERENCES "user"(id);


--
-- Name: fkdbd91133f0e2f694; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY careteammember
    ADD CONSTRAINT fkdbd91133f0e2f694 FOREIGN KEY (careteamid) REFERENCES careteammaster(id);


--
-- Name: fkfaafb1684e2a031a; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY patiencareteam
    ADD CONSTRAINT fkfaafb1684e2a031a FOREIGN KEY (patienid) REFERENCES "user"(id);


--
-- Name: fkfaafb168f0e2f694; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY patiencareteam
    ADD CONSTRAINT fkfaafb168f0e2f694 FOREIGN KEY (careteamid) REFERENCES careteammaster(id);


--
-- Name: homeaddress_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY expertdetails
    ADD CONSTRAINT "homeaddress_FK" FOREIGN KEY (homeaddress) REFERENCES useraddress(id);


--
-- Name: id_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY expertdetails
    ADD CONSTRAINT "id_FK" FOREIGN KEY (id) REFERENCES "user"(id);


--
-- Name: medicineid_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY patientmedication
    ADD CONSTRAINT "medicineid_FK" FOREIGN KEY (medicineid) REFERENCES medicinemaster(id);


--
-- Name: member_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY careteammember
    ADD CONSTRAINT "member_FK" FOREIGN KEY (memberid) REFERENCES "user"(id);


--
-- Name: member_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY patiencareteam
    ADD CONSTRAINT "member_FK" FOREIGN KEY (patienid) REFERENCES "user"(id);


--
-- Name: patient_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY patientdetails
    ADD CONSTRAINT "patient_FK" FOREIGN KEY (id) REFERENCES "user"(id);


--
-- Name: patientaddress_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY patientdetails
    ADD CONSTRAINT "patientaddress_FK" FOREIGN KEY (address) REFERENCES useraddress(id);


--
-- Name: patientdistress_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY patientdistress
    ADD CONSTRAINT "patientdistress_FK" FOREIGN KEY (patientid) REFERENCES "user"(id);


--
-- Name: patientdistressid_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY patientdistressdetail
    ADD CONSTRAINT "patientdistressid_FK" FOREIGN KEY (patiendistressid) REFERENCES patientdistress(id);


--
-- Name: patientid_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY patientmedication
    ADD CONSTRAINT "patientid_FK" FOREIGN KEY (patientid) REFERENCES "user"(id);


--
-- Name: practiceAddress_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY expertdetails
    ADD CONSTRAINT "practiceAddress_FK" FOREIGN KEY (practiceaddress) REFERENCES useraddress(id);


--
-- Name: seq1_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY userdetails
    ADD CONSTRAINT "seq1_FK" FOREIGN KEY (seq1) REFERENCES securityquestionmaster(id);


--
-- Name: seq2_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY userdetails
    ADD CONSTRAINT "seq2_FK" FOREIGN KEY (seq2) REFERENCES securityquestionmaster(id);


--
-- Name: userID_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY userimage
    ADD CONSTRAINT "userID_FK" FOREIGN KEY (id) REFERENCES "user"(id);


--
-- Name: user_id_fk; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY loginhistory
    ADD CONSTRAINT user_id_fk FOREIGN KEY (userid) REFERENCES "user"(id);


--
-- Name: CONSTRAINT user_id_fk ON loginhistory; Type: COMMENT; Schema: nav; Owner: postgres
--

COMMENT ON CONSTRAINT user_id_fk ON loginhistory IS 'FK User id';


--
-- Name: user_id_fkey; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_id_fkey FOREIGN KEY (id) REFERENCES "user"(id);


--
-- Name: userdetails_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY userdetails
    ADD CONSTRAINT "userdetails_FK" FOREIGN KEY (id) REFERENCES "user"(id);


--
-- Name: userid_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY usereducation
    ADD CONSTRAINT "userid_FK" FOREIGN KEY (userid) REFERENCES "user"(id);


--
-- Name: usertype_FK; Type: FK CONSTRAINT; Schema: nav; Owner: postgres
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT "usertype_FK" FOREIGN KEY (usertypeid) REFERENCES usertypemaster(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

