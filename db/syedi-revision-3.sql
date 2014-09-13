DROP TABLE IF EXISTS nav.patientrtse;
DROP TABLE IF EXISTS nav.patientcttse;
DROP TABLE IF EXISTS nav.patientses;
DROP TABLE IF EXISTS nav.patientrt;
DROP TABLE IF EXISTS nav.patientctt;
DROP TABLE IF EXISTS nav.patientsi;


DROP TABLE IF EXISTS nav.treatment_region;
DROP TABLE IF EXISTS nav.side_effect;
DROP TABLE IF EXISTS nav.radiation_schedule;
DROP TABLE IF EXISTS nav.chemo_schedule;
DROP TABLE IF EXISTS nav.radiation_type;
DROP TABLE IF EXISTS nav.surgery_type;


DROP TABLE IF EXISTS nav.mgn_delivery;
DROP TABLE IF EXISTS nav.medication;
DROP TABLE IF EXISTS nav.medication_delivery;
DROP TABLE IF EXISTS nav.medication_bn;
DROP TABLE IF EXISTS nav.medication_gn;

------------------------ run sequentially
CREATE TABLE nav.treatment_region (
    tr_id SERIAL PRIMARY KEY,  
    region character varying(50)
);
INSERT INTO nav.treatment_region (region) VALUES 
('Left Breast'), ('Right Breast'), ('Both Breasts'), ('Lymph nodes'),
('Colon'), ('Rectum'), ('Prostate'), ('Left Lung'), ('Right Lung'), 
('Both Lungs'), ('Uterus'), ('Cervix'), ('Fallopian Tubes'), ('Ovaries'), 
('Vagina'), ('Head and neck'), ('Brain'), ('Chest'), ('Stomach and abdomen'), 
('Pelvis');

------------------------ run sequentially
CREATE TABLE nav.side_effect (
    se_id SERIAL PRIMARY KEY,  
    description character varying(100)
);

INSERT INTO nav.side_effect (description) VALUES 
('Anemia'), ('Nausea'), ('Vomiting'), ('Diarrhea'), ('Hair loss'), 
('Infection'), ('Loss of appetite'), ('Fatigue'), ('Fever'), ('Leukopenia'), 
('Mouth sores'), ('Pain'), ('Constipation'), ('Easy bruising'), 
('Thrombocytopenia'), ('Tingling'), ('Burning'), 
('Weakness or numbness in the hands and/or feet'), 
('Weak, sore, tired, or achy muscles'), ('Loss of balance'), 
('Shaking or trembling'), ('Stiff neck'), ('Headaches'), ('Visual problems'), 
('Walking problems'), ('Difficulty hearing'), ('Clumsiness'), 
('Changes in thinking or memory'), ('Sexual Issues'), ('Reproductive issues'), 
('Organ damage'), ('Weight gain'), ('Urine changes'), 
('Bladder and kidney problems'), ('Skin changes'), ('Nerve problems'), 
('Muscle problems'), ('Bleeding'), 
('Trouble picking up things and buttoning clothing'), ('Jaw pain'), 
('Vision changes'), ('Stomach pain'), ('Peripheral neuropathy'), 
('Feeling cold'), ('General weakness'), ('Chemo-brain'), ('Hot flashes'), 
('Sudden onset of menopause'), ('Osteoporosis'), ('Drowsiness'), 
('Sore throat'), ('Dizziness'), ('Blurred vision'), ('Short term memory loss'), 
('Low blood pressure (hypotension)'), ('Temporary nerve damage'), 
('Kidney failure'), ('Liver failure'), ('Allergic reaction'), ('Seizure'), 
('Muscle aches'), ('Dental injury'), ('Swelling around site of surgery'), 
('Lymphedema'), ('Drainage from site of surgery'), 
('Ecchymosis (bruising) around the site of surgery'), 
('Ileus (paralyzed bowels)'), ('Skin dryness'), ('Skin itching'), 
('Skin peeling'), ('Skin blistering'), ('Trouble swallowing'), 
('Urinary and bladder changes'), ('Abdominal pain'), ('Addiction'), 
('Weight changes'), ('Loss of weight'), ('Vision and eye problems'), 
('Vaginal discharge'), ('Vaginal dryness'), ('Urine discoloration'), 
('Urinary tract infection'), ('Urination problems'), 
('Tingling in hands or feet'), ('Taste and smell changes'), ('Swelling'), 
('Sweating'), ('Anxiety'), ('Appetite changes'), ('Armpit discomfort'), 
('Back pain'), ('Blood clots and phlebitis'), ('Bone and joint pain'), 
('Breathing problems'), ('Burning at radiation site'), ('Chest pain'), 
('Cold and flu symptoms'), ('Concentration'), ('Coughing'), ('Dehydration'), 
('Delayed wound healing'), ('Depression'), ('Dry mouth'), ('Congestion'), 
('Dry nose'), ('Dry skin'), ('Itchy'), ('Eating Problems'), 
('Electrolyte imbalance'), ('Endometriosis'), ('Fainting'), 
('Fertility issues'), ('Gas (Flatulence)'), ('Hair changes'), 
('Hand-Foot Syndrome (HFS)'), ('Heart problems'), ('Heartburn or GERD'), 
('Hematoma (Blood Build-up)'), ('High Blood Pressure'), ('High cholesterol'), 
('Lung problems'), ('Indigestion'), ('Injection site reaction'), 
('Insomnia (Trouble Sleeping)'), ('Kidney problems'), ('Leg cramps'), 
('Liver Problems (Hepatotoxicity)'), ('Loss of Libido'), ('Mobility problems'), 
('Mood Swings'), ('Throat Sores'), ('Nail Changes'), ('Runny nose'), 
('Nose bleeds'), ('Numbness'), ('Osteonecrosis of the Jaw'), 
('Phantom Breast Pain'), ('Post-Traumatic Stress Disorder (PTSD)'), ('Rash'), 
('Scar Tissue Formation'), ('Seroma (fluid build-up)'), ('Skin discoloration'), 
('Skin Sensitivity');

------------------------ run sequentially
CREATE TABLE nav.radiation_schedule (
    rs_id SERIAL PRIMARY KEY,  
    time_period character varying(100)
);

INSERT INTO nav.radiation_schedule (time_period) VALUES 
('2x per day'), ('1x per week'), ('2x per week'), ('3x per week'), 
('4x per week'), ('5x per week'), ('6x per week'), ('7x per week'), 
('1x per 2 weeks'), ('1x per 3 weeks'), ('1x per 4 weeks');

------------------------ run sequentially
CREATE TABLE nav.chemo_schedule (
    cs_id SERIAL PRIMARY KEY,  
    time_period character varying(100)
);

INSERT INTO nav.chemo_schedule (time_period) VALUES 
('Daily, 1x per day'), ('Daily, 2x per day'), ('1x per week'), ('2x per week'), 
('3x per week'), ('4x per week'), ('5x per week'), ('1x per 2 weeks'), 
('1x per 3 weeks'), ('1x per month');

------------------------ run sequentially
CREATE TABLE nav.radiation_type (
    rt_id SERIAL PRIMARY KEY,  
    name character varying(100)
);

INSERT INTO nav.radiation_type (name) VALUES 
('External-Beam Radiation Therapy'), 
('APBI (Accelerated Partial Breast Irradiation)'), 
('3D-CRT (Three-dimensional conformal radiation therapy)'), 
('IMRT (Intensity modulated radiation therapy)'), 
('IGRT (Image-Guided Radidation Therapy)'), ('Proton beam therapy'), 
('Stereotactic radiation therapy'), ('Internal radiation therapy'), 
('Systemic radiation therapy'), ('Intersitial Brachytherapy'), 
('Intracavitary brachytherapy'), ('Episcleral brachytherapy'), 
('Tomotherapy'), ('Stereotactic Radiosurgery'), 
('Stereotactic Body Radiation Therapy');

------------------------ run sequentially
CREATE TABLE nav.surgery_type (
    st_id SERIAL PRIMARY KEY,  
    name character varying(100),
    days integer DEFAULT NULL
);

INSERT INTO nav.surgery_type (name, days) VALUES 
('Breast Biopsy', 3), ('Lumpectomy', 14), ('Mastecomy', 28), 
('Double mastectomy', 28), ('Axial node dissection', 42), 
('Sentinel lymph node dissection', 14), 
('Tissue flap breast reconstruction', 49), 
('Implant breast reconstruction', 21), 
('Open Radical Prostatectomy', 42), 
('Open radical retropubic prostatectomy', 35), 
('Open radical perineal prostatectomy', 35), 
('Laparoscopic radical prostatectomy', 21), 
('Transurethral resection of the prostate (TURP)', 7), 
('Pelvic lymphadenectomy', 14), 
('Segmentectomy / Wedge Resection', 42), 
('Lobectomy', 56), 
('Pneumonectomy', 56), 
('Sleeve Resection', 28), 
('Colon Local Excision', 35), 
('Polypectomy and Colon Local Excision', 35), 
('Open Colectomy', 56), 
('Laparoscopic Colectomy', 35), 
('Colostomy', 56), 
('Radiofrequency Ablation', 7), 
('Cryosurgery', 7), 
('Polypectomy and Rectal Local Excision', 35), 
('Rectal Local Excision', 35), 
('Rectal Resection', 56), 
('Proctectomy', 56), 
('Pelvic Exenteration', 56), 
('Open Partial Hysterectomy', 35), 
('Open Total Hysterectomy', 35), 
('Open Bilateral Salpingo-Oopherectomy', 35), 
('Open Radical Hysterectomy', 35), 
('Laparoscopic Partial Hysterectomy', 21), 
('Laparoscopic Total Hysterectomy', 21), 
('Laparoscopic Bilateral Salpingo-Oopherectomy', 21), 
('Laparoscopic Radical Hysterectomy', 21);

------------------------ run sequentially
CREATE TABLE nav.patientrt (
    prt_id SERIAL PRIMARY KEY,
    user_id integer NOT NULL REFERENCES nav.user (id),    
    rt_id integer NOT NULL REFERENCES nav.radiation_type (rt_id),
    dose character varying(20) DEFAULT NULL,
    rs_id integer DEFAULT NULL REFERENCES nav.radiation_schedule (rs_id),
    start_date date DEFAULT NULL,
    end_date date DEFAULT NULL,
    tr_id integer DEFAULT NULL REFERENCES nav.treatment_region (tr_id),
    notes character varying(400)
);

COMMENT ON TABLE nav.patientrt IS '[PATIENT] [R]adiation [T]reatment';


------------------------ run sequentially
CREATE TABLE nav.patientctt (
    pct_id SERIAL PRIMARY KEY,
    user_id integer NOT NULL REFERENCES nav.user (id), 
    mgn character varying(250) NOT NULL,
    mbn character varying(250) NOT NULL,
    cycle_no integer DEFAULT NULL,
    cs_id integer DEFAULT NULL REFERENCES nav.chemo_schedule (cs_id),
    dose_reduction integer,
    start_date date DEFAULT NULL,
    end_date date DEFAULT NULL,
    notes character varying(400)
);

COMMENT ON TABLE nav.patientctt IS '[PATIENT] [C]hemo[T]herapy [T]reatment';

------------------------ run sequentially
CREATE TABLE nav.patientsi (
    psi_id SERIAL PRIMARY KEY,
    user_id integer NOT NULL REFERENCES nav.user (id),  
    st_id integer DEFAULT NULL REFERENCES nav.surgery_type (st_id),  
    surgery_date date DEFAULT NULL,
    tr_id integer DEFAULT NULL REFERENCES nav.treatment_region (tr_id),
    notes character varying(400)
);

COMMENT ON TABLE nav.patientsi IS '[PATIENT] [S]urgery [I]nfo';

------------------------ run sequentially
CREATE TABLE nav.patientrtse (
    pse_id SERIAL PRIMARY KEY,
    prt_id integer NOT NULL REFERENCES nav.patientrt (prt_id),
    se_id integer NOT NULL REFERENCES nav.side_effect (se_id)
);

COMMENT ON TABLE nav.patientrtse IS '[PATIENT] [R]adiation [T]reatment [S]ide [E]ffects';


------------------------ run sequentially
CREATE TABLE nav.patientcttse (
    pse_id SERIAL PRIMARY KEY,
    pct_id integer NOT NULL REFERENCES nav.patientctt (pct_id),
    se_id integer NOT NULL REFERENCES nav.side_effect (se_id)
);

COMMENT ON TABLE nav.patientcttse IS '[PATIENT] [C]hemo[T]herapy [S]ide [E]ffects';

------------------------ run sequentially
CREATE TABLE nav.patientses (
    pse_id SERIAL PRIMARY KEY,
    psi_id integer NOT NULL REFERENCES nav.patientsi (psi_id),
    se_id integer NOT NULL REFERENCES nav.side_effect (se_id)
);

COMMENT ON TABLE nav.patientses IS '[PATIENT] [S]ide [E]ffects after [S]urgery';

