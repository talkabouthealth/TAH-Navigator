INSERT INTO nav.diseasemaster(name) VALUES ('Endometrial Cancer');

INSERT INTO nav.bcsinfo (stage,diseaseid) VALUES 
('Stage 1A',7),
('Stage 1A',7),
('Stage 2',7),
('Stage 3A',7),
('Stage 3B',7),
('Stage 3C',7),
('Stage 4A',7),
('Stage 4B',7);

ALTER TABLE nav.diseasemaster ADD COLUMN diseaseactive boolean;
update nav.diseasemaster set diseaseactive  = true;

INSERT INTO nav.diseasemaster(name,diseaseactive) VALUES ('Bladder Cancer',true),
('Non-Hodgkin Lymphoma',true),
('Melanoma',true),
('AML (Acute Myeloid Leukemia)',true),
('ALL (Acute Lymphocytic Leukemia)',true),
('CLL (Chronic Lymphocytic Leukemia)',true),
('CML (Chronic Myeloid Leukemia)',true),
('Cervical Cancer',true),
('Stomach Cancer',true),
('Liver Cancer',true),
('Pancreatic Cancer',true),
('Laryngeal Cancer',true),
('Pharyngeal Cancer',true),
('Multiple Myeloma',true),
('Ovarian Cancer',true),
('Kidney (Renal) Cancer',true),
('Brain Cancer',true),
('Thyroid Cancer',true),
('Hodgkin Lymphoma',true);

INSERT INTO nav.bcsinfo (stage,diseaseid) VALUES 
('Stage 0a',8),
('Stage 0is',8),
('Stage 1',8),
('Stage 2',8),
('Stage 3',8),
('Stage 4',8);

INSERT INTO nav.bcsinfo (stage,diseaseid) VALUES 
('Stage 1',9),
('Stage 1E',9),
('Stage 2',9),
('Stage 2E',9),
('Stage 3',9),
('Stage 3E',9),
('Stage 3E+S',9),
('Stage 4',9);

INSERT INTO nav.bcsinfo (stage,diseaseid) VALUES 
('Stage 0',10),
('Stage 1A',10),
('Stage 1B',10),
('Stage 2A',10),
('Stage 2B',10),
('Stage 2C',10),
('Stage 3',10),
('Stage 4',10);

INSERT INTO nav.bcsinfo (stage,diseaseid) VALUES 
('Stage 0',13),
('Stage 1',13),
('Stage 2',13),
('Stage 3',13),
('Stage 4',13);

INSERT INTO nav.bcsinfo (stage,diseaseid) VALUES 
('Stage 0',15),
('Stage 1A',15),
('Stage 1B',15),
('Stage 2A',15),
('Stage 2B',15),
('Stage 3A',15),
('Stage 3B',15),
('Stage 4A',15),
('Stage 4B',15);

INSERT INTO nav.bcsinfo (stage,diseaseid) VALUES 
('Stage 0',16),
('Stage 1A',16),
('Stage 1B',16),
('Stage 2A',16),
('Stage 2B',16),
('Stage 3A',16),
('Stage 3B',16),
('Stage 3C',16),
('Stage 4A',16),
('Stage 4B',16);


INSERT INTO nav.bcsinfo (stage,diseaseid) VALUES 
('Stage 0',17),
('Stage A',17),
('Stage B',17),
('Stage C',17),
('Stage D',17);

INSERT INTO nav.bcsinfo (stage,diseaseid) VALUES 
('Stage 0',18),
('Stage 1A',18),
('Stage 1B',18),
('Stage 2A',18),
('Stage 2B',18),
('Stage 3',18),
('Stage 4',18);

INSERT INTO nav.bcsinfo (stage,diseaseid) VALUES 
('Stage 0',19),
('Stage 1',19),
('Stage 2',19),
('Stage 3',19),
('Stage 4',19);

INSERT INTO nav.bcsinfo (stage,diseaseid) VALUES 
('Stage 0',20),
('Stage 1',20),
('Stage 2',20),
('Stage 3',20),
('Stage 4A',20),
('Stage 4B',20),
('Stage 4C',20);

INSERT INTO nav.bcsinfo (stage,diseaseid) VALUES 
('Stage 1',21),
('Stage 2',21),
('Stage 3',21);

INSERT INTO nav.bcsinfo (stage,diseaseid) VALUES 
('Stage 1A',22),
('Stage 1B',22),
('Stage 1C',22),
('Stage 2A',22),
('Stage 2B',22),
('Stage 2C',22),
('Stage 3A',22),
('Stage 3B',22),
('Stage 3C',22),
('Stage 4',22);

INSERT INTO nav.bcsinfo (stage,diseaseid) VALUES 
('Stage 1',23),
('Stage 2',23),
('Stage 3',23),
('Stage 4',23);

INSERT INTO nav.bcsinfo (stage,diseaseid) VALUES 
('Stage 1',25),
('Stage 2',25),
('Stage 3',25),
('Stage 4A',25),
('Stage 4B',25),
('Stage 4C',25);

INSERT INTO nav.bcsinfo (stage,diseaseid) VALUES 
('Stage 1',26),
('Stage 1E',26),
('Stage 2',26),
('Stage 2E',26),
('Stage 3',26),
('Stage 3E',26),
('Stage 3S',26),
('Stage 3E,S',26),
('Stage 4',26);


INSERT INTO nav.cancertypemaster(roottype, name, diseaseid) 
VALUES 
(true, 'Adult T-cell leukemia/lymphoma', 9),
(true, 'Anaplastic large cell lymphoma (ALCL)', 9),
(true, 'Angioimmunoblastic T-cell lymphoma', 9),
(true, 'Burkitt lymphoma', 9),
(true, 'Chronic lymphocytic leukemia /small lymphocytic lymphoma', 9),
(true, 'Cutaneous T-cell lymphoma', 9),
(true, 'Diffuse large B-cell lymphoma', 9),
(true, 'Enteropathy-associated intestinal T-cell lymphoma (EATL)', 9),
(true, 'Extranodal marginal zone B-cell lymphoma', 9),
(true, 'Extranodal natural killer/T-cell lymphoma, nasal type', 9),
(true, 'Follicular lymphoma', 9),
(true, 'Hairy cell leukemia', 9),
(true, 'Intravascular large B-cell lymphoma', 9),
(true, 'Lymphoplasmacytic lymphoma', 9),
(true, 'Mantle cell lymphoma', 9),
(true, 'Marginal zone B-cell lymphoma', 9),
(true, 'Nodal marginal zone B-cell lymphoma', 9),
(true, 'Peripheral T-cell lymphoma', 9),
(true, 'Peripheral T-cell lymphoma, unspecified', 9),
(true, 'Precursor T-lymphoblastic lymphoma/leukemia', 9),
(true, 'Primary central nervous system (CNS) lymphoma', 9),
(true, 'Primary mediastinal B-cell lymphoma', 9),
(true, 'Splenic marginal zone B-cell lymphoma', 9),
(true, 'T-cell lymphoma', 9);


INSERT INTO nav.cancermutationmaster(mutation, diseaseid) 
VALUES ('BRAF', 10),
('KIT', 10),
('NRAS', 10);

INSERT INTO nav.cancertypemaster(roottype, name, diseaseid) 
VALUES 
(true, 'Adenocarcinoma', 15),
(true, 'Squamous Cell', 15);

INSERT INTO nav.cancertypemaster(roottype, name, diseaseid) 
VALUES 
(true, 'Adenocarcinoma', 16),
(true, 'Squamous Cell', 16);

INSERT INTO nav.cancertypemaster(roottype, name, diseaseid) 
VALUES 
(true, 'Angiosarcoma', 17),
(true, 'Cholangiocarcinoma', 17),
(true, 'Hepatoblastoma', 17),
(true, 'Hepatocellular carcinoma (HCC)', 17);


INSERT INTO nav.cancertypemaster(roottype, name, diseaseid) 
VALUES 
(true, 'Adenocarcinoma', 19),
(true, 'Squamous Cell', 19);

INSERT INTO nav.cancertypemaster(roottype, name, diseaseid) 
VALUES 
(true, 'Lymphocyte-depletion CHL', 26),
(true, 'Lymphocyte-rich CHL', 26),
(true, 'Mixed Cellularity CHL', 26),
(true, 'Nodular Lymphocyte Predominant HL', 26),
(true, 'Nodular Sclerosis CHL', 26);


INSERT INTO nav.cancertypemaster(roottype, name, diseaseid) 
VALUES 
(false, 'Acute B-lymphoblastic leukemia', 12),
(false, 'Acute precursor B-cell leukemia', 12),
(false, 'Acute T-lymphoblastic leukemia', 12),
(false, 'B-cell ALL', 12),
(false, 'Natural killer (NK) cell leukemia', 12),
(false, 'Philadelphia-positive ALL (Ph+ ALL)', 12),
(false, 'Pre B-cell lymphoblastic leukemia', 12),
(false, 'T-cell ALL', 12);