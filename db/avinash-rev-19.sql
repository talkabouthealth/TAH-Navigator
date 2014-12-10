delete from nav.inputdefaultmaster where  diseaseid = 2;

INSERT INTO nav.inputdefaultmaster( diseaseid, page, field, fieldtext, tiptext, otherfield,frequency,tiptype,enddate)
VALUES 
(2, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.'
,'Every 6 months','text','Ongoing'),
(2, 'followupplan', 'activity', 'PSA Test', 
'A PSA test measures the amount of prostate-specific antigen (PSA) in your blood. The PSA test can detect high levels of PSA that may indicate the presence of prostate cancer. However, many other conditions, such as an enlarged or inflamed prostate, can also increase PSA levels.'
,'Used primarily to screen for prostate cancer.','Every 6 months','text','Ongoing')
;

delete from nav.inputdefaultmaster where  diseaseid = 3;

INSERT INTO nav.inputdefaultmaster( diseaseid, page, field, fieldtext, tiptext, otherfield,frequency,tiptype,enddate)
VALUES 
(3, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.'
,'Every 6 months','text','Ongoing'),
(3, 'followupplan', 'activity', 'CT Scanning', 
'The CT scan is an x-ray test that produces detailed cross-sectional images of your body.'
,'CT scans of the chest are recommended every 6 months for the first 2 years after resection and every year thereafter. This test can help tell if cancer has returned or spread into your liver or other organs.','Every 6 months','text','Ongoing')
;

delete from nav.inputdefaultmaster where  diseaseid = 4;

INSERT INTO nav.inputdefaultmaster( diseaseid, page, field, fieldtext, tiptext, otherfield,frequency,tiptype,enddate)
VALUES 
(4, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.'
,'Every 6 months','text','Ongoing'),
(4, 'followupplan', 'activity', 'CEA Test', 'CEA testing is recommended every three to six months for five years. The CEA test measures the level of carcinoembryonic antigen (CEA) in the blood. A blood sample is needed .Higher than normal CEA level alone cannot diagnose a new cancer. Further testing is needed.'
,'Abnormal level of CEA may be a sign of cancer, but further testing is needed.','Every 6 months','text','Ongoing'),
(4, 'followupplan', 'activity', 'CT Scanning', 
'Frequency is determined by your doctor, potentially every year. The CT scan is an x-ray test that produces detailed cross-sectional images of your body.'
,'CT scans of the abdomen and chest are recommended each year for three years. This test can help tell if colon cancer has spread into your liver or other organs.','Every year','text','Ongoing'),
(4, 'followupplan', 'activity', 'Colonoscopy', 
'A colonoscopy should be done around the time of surgery. If the examination shows no signs of a recurrent tumor or polyps, a colonoscopy should be done at 3 years, and if normal, every 5 years thereafter.'
,'This test allows the doctor to look for polyps or second cancers in the entire rectum and colon with a colonoscope (a flexible, lighted tube).','At 3 years and every 5 years thereafter','text','Ongoing')
;

delete from nav.inputdefaultmaster where  diseaseid = 5;

INSERT INTO nav.inputdefaultmaster( diseaseid, page, field, fieldtext, tiptext, otherfield,frequency,tiptype,enddate)
VALUES 
(5, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.'
,'Every 6 months','text','Ongoing'),
(5, 'followupplan', 'activity', 'CEA Test', 'CEA testing is recommended every three to six months for five years. The CEA test measures the level of carcinoembryonic antigen (CEA) in the blood. A blood sample is needed .Higher than normal CEA level alone cannot diagnose a new cancer. Further testing is needed.'
,'Abnormal level of CEA may be a sign of cancer, but further testing is needed.','Every 6 months','text','Ongoing'),
(5, 'followupplan', 'activity', 'CT Scanning', 
'Frequency is determined by your doctor, potentially every year. The CT scan is an x-ray test that produces detailed cross-sectional images of your body.'
,'CT scans of the abdomen and chest are recommended each year for three years. This test can help tell if colon cancer has spread into your liver or other organs.','Every year','text','Ongoing'),
(5, 'followupplan', 'activity', 'Colonoscopy', 
'A colonoscopy should be done around the time of surgery. If the examination shows no signs of a recurrent tumor or polyps, a colonoscopy should be done at 3 years, and if normal, every 5 years thereafter.'
,'This test allows the doctor to look for polyps or second cancers in the entire rectum and colon with a colonoscope (a flexible, lighted tube).','At 3 years and every 5 years thereafter','text','Ongoing'),
(5, 'followupplan', 'activity', 'Rectosigmoidoscopy', 
'If you had rectal cancer, but did not have radiation therapy to the pelvis, this test is recommended every six months. Although, rectosigmoidoscopy may be recommended even if you had radiation therapy for rectal cancer, but it depends on your risk of recurrence.'
,'In this test, a sigmoidoscope (a flexible, lighted tube) is inserted into a patient’s rectum to check for polyps, cancer, and other abnormalities.','Every 6 months','text','Ongoing')
;

delete from nav.inputdefaultmaster where  diseaseid = 22;

INSERT INTO nav.inputdefaultmaster( diseaseid, page, field, fieldtext, tiptext, otherfield,frequency,tiptype,enddate)
VALUES 
(22, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.'
,'Every 6 months','text','Ongoing'),
(22, 'followupplan', 'activity', 'CA-125', 
'http://www.foundationforwomenscancer.org/wp-content/uploads/CA125levels.pdf'
,'Elevated levels of CA-125 suggest further testing is needed.','Every 6 months','link','Ongoing')
;

delete from nav.inputdefaultmaster where  diseaseid in (6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,23,24,25,26);

INSERT INTO nav.inputdefaultmaster( diseaseid, page, field, fieldtext, tiptext, otherfield,frequency,tiptype,enddate)
VALUES 
(6, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.','Every 6 months','text','Ongoing')
,(7, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.','Every 6 months','text','Ongoing')
,(8, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.','Every 6 months','text','Ongoing')
,(9, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.','Every 6 months','text','Ongoing')
,(10, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.','Every 6 months','text','Ongoing')
,(11, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.','Every 6 months','text','Ongoing')
,(12, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.','Every 6 months','text','Ongoing')
,(13, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.','Every 6 months','text','Ongoing')
,(14, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.','Every 6 months','text','Ongoing')
,(15, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.','Every 6 months','text','Ongoing')
,(16, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.','Every 6 months','text','Ongoing')
,(17, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.','Every 6 months','text','Ongoing')
,(19, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.','Every 6 months','text','Ongoing')
,(20, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.','Every 6 months','text','Ongoing')
,(21, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.','Every 6 months','text','Ongoing')
,(23, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.','Every 6 months','text','Ongoing')
,(24, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.','Every 6 months','text','Ongoing')
,(25, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.','Every 6 months','text','Ongoing')
,(26, 'followupplan', 'activity', 'Medical history and physical exam', 'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.', 'To check for recurrence and metastasis.','Every 6 months','text','Ongoing')
;


/*General template*/
INSERT INTO nav.defaultemplatetmaster(templatename,  page, field) VALUES ('General Template', 'followupplan', 'activity');
INSERT INTO nav.defaultemplatetdetail(templateid, fieldtext, otherfield, frequency, enddate) VALUES (3, 'Medical history and physical exam','To check for recurrence and metastasis.','Every 6 months','Ongoing');

INSERT INTO nav.defaultemplatetmaster(templatename, diseaseid, page, field) VALUES ('Prostate Cancer Template',2, 'followupplan', 'activity');
INSERT INTO nav.defaultemplatetdetail(templateid, fieldtext, otherfield, frequency, enddate) VALUES 
(4, 'Medical history and physical exam','To check for recurrence and metastasis.','Every 6 months','Ongoing'),
(4, 'PSA Test','Used primarily to screen for prostate cancer.','Every 6 months','Ongoing');


INSERT INTO nav.defaultemplatetmaster(templatename, diseaseid, page, field) VALUES ('Lung Cancer Template',3, 'followupplan', 'activity');
INSERT INTO nav.defaultemplatetdetail(templateid, fieldtext, otherfield, frequency, enddate) VALUES 
(5, 'Medical history and physical exam','To check for recurrence and metastasis.','Every 6 months','Ongoing'),
(5, 'CT Scanning','CT scans of the chest are recommended every 6 months for the first 2 years after resection and every year thereafter. This test can help tell if cancer has returned or spread into your liver or other organs.'
,'Every 6 months','Ongoing');

INSERT INTO nav.defaultemplatetmaster(templatename, diseaseid, page, field) VALUES ('Colon Cancer Template',4, 'followupplan', 'activity');
INSERT INTO nav.defaultemplatetdetail(templateid, fieldtext, otherfield, frequency, enddate) VALUES 
(6, 'Medical history and physical exam','To check for recurrence and metastasis.','Every 6 months','Ongoing'),
(6, 'CEA Test','Abnormal level of CEA may be a sign of cancer, but further testing is needed.'
,'Every 6 months','Ongoing'),
(6, 'CT Scanning','CT scans of the abdomen and chest are recommended each year for three years. This test can help tell if colon cancer has spread into your liver or other organs.'
,'Every year','Ongoing'),
(6, 'Colonoscopy','This test allows the doctor to look for polyps or second cancers in the entire rectum and colon with a colonoscope (a flexible, lighted tube).'
,'At 3 years and every 5 years thereafter','Ongoing');

INSERT INTO nav.defaultemplatetmaster(templatename, diseaseid, page, field) VALUES ('Rectal Cancer Template',5, 'followupplan', 'activity');
INSERT INTO nav.defaultemplatetdetail(templateid, fieldtext, otherfield, frequency, enddate) VALUES 
(7, 'Medical history and physical exam','To check for recurrence and metastasis.','Every 6 months','Ongoing'),
(7, 'CEA Test','Abnormal level of CEA may be a sign of cancer, but further testing is needed.'
,'Every 6 months','Ongoing'),
(7, 'CT Scanning','CT scans of the abdomen and chest are recommended each year for three years. This test can help tell if colon cancer has spread into your liver or other organs.'
,'Every year','Ongoing'),
(7, 'Colonoscopy','This test allows the doctor to look for polyps or second cancers in the entire rectum and colon with a colonoscope (a flexible, lighted tube).'
,'At 3 years and every 5 years thereafter','Ongoing'),
(7, 'Rectosigmoidoscopy','In this test, a sigmoidoscope (a flexible, lighted tube) is inserted into a patient’s rectum to check for polyps, cancer, and other abnormalities.'
,'Every 6 months','Ongoing');

INSERT INTO nav.defaultemplatetmaster(templatename, diseaseid, page, field) VALUES ('Endometrial Cancer Low Risk Template',7, 'followupplan', 'activity');
INSERT INTO nav.defaultemplatetdetail(templateid, fieldtext, otherfield, frequency, enddate) VALUES 
(8, 'Medical history and physical exam','To check for recurrence and metastasis.','Every 6 months for year 1. Yearly after year 1','Ongoing');

INSERT INTO nav.defaultemplatetmaster(templatename, diseaseid, page, field) VALUES ('Endometrial Cancer Medium Risk Template',7, 'followupplan', 'activity');
INSERT INTO nav.defaultemplatetdetail(templateid, fieldtext, otherfield, frequency, enddate) VALUES 
(9, 'Medical history and physical exam','To check for recurrence and metastasis.','Every 3 months for year 1. Every 6 months for year 1 through 5. Yearly after year 5.','Ongoing');

INSERT INTO nav.defaultemplatetmaster(templatename, diseaseid, page, field) VALUES ('Endometrial Cancer High Risk Template',7, 'followupplan', 'activity');
INSERT INTO nav.defaultemplatetdetail(templateid, fieldtext, otherfield, frequency, enddate) VALUES 
(10, 'Medical history and physical exam','To check for recurrence and metastasis.','Every 3 months for year 1 and 2. Every 6 months for year 2 through 5. Yearly after year 5.','Ongoing');

INSERT INTO nav.defaultemplatetmaster(templatename, diseaseid, page, field) VALUES ('Cervical Cancer Low Risk Template',15, 'followupplan', 'activity');
INSERT INTO nav.defaultemplatetdetail(templateid, fieldtext, otherfield, frequency, enddate) VALUES 
(11, 'Medical history and physical exam','To check for recurrence and metastasis.','Every 6 months for year 1 and 2. Yearly after year 2.','Ongoing');

INSERT INTO nav.defaultemplatetmaster(templatename, diseaseid, page, field) VALUES ('Cervical Cancer High Risk Template',15, 'followupplan', 'activity');
INSERT INTO nav.defaultemplatetdetail(templateid, fieldtext, otherfield, frequency, enddate) VALUES 
(12, 'Medical history and physical exam','To check for recurrence and metastasis.','Every 3 months for year 1 and 2. Every 6 months for year 3, 4, and 5. Yearly after year 5.','Ongoing');


ALTER TABLE nav.defaultemplatetdetail ALTER COLUMN frequency TYPE character varying(150);

INSERT INTO nav.defaultemplatetmaster(templatename, diseaseid, page, field) VALUES ('Ovarian Cancer Template',22, 'followupplan', 'activity');
INSERT INTO nav.defaultemplatetdetail(templateid, fieldtext, otherfield, frequency, enddate) VALUES 
(13, 'Medical history and physical exam','To check for recurrence and metastasis.','Every 3 months for year 1 and 2. Every 4 to 6 months in year 3. Every 6 months in year 4 and 5. Yearly after year 5.','Ongoing');

update nav.inputdefaultmaster set tiptext = 'http://www.cancer.gov/cancertopics/coping/radiation-side-effects/skin.pdf' where field =  'goal' and fieldtext = 'Maintain Skin Integrity';
update nav.inputdefaultmaster set tiptext = 'http://www.cancer.net/coping-and-emotions/managing-emotions/managing-stress' where field =  'goal' and fieldtext = 'Manage Stress';

INSERT INTO nav.inputdefaultmaster(diseaseid, page, field, fieldtext, tiptext, otherfield, tiptype) VALUES
(1, 'followupplan', 'goal', 'Minimize alcohol use', 'http://www.cancer.org/cancer/cancercauses/dietandphysicalactivity/alcohol-use-and-cancer', 'Talk to social worker about support programs.', 'link'),
(1, 'followupplan', 'goal', 'Quit smoking', 'http://www.nccn.org/patients/resources/survivorship/smoking.aspx', 'Talk to care team about smoking cessation programs.', 'link'),
(1, 'followupplan', 'goal', 'Use sun protection', 'http://www.skincancer.org/prevention/sun-protection', 'Purchase sun screen and sun-safe clothing. Place sun-safe items in a convenient location you will see every day or in an item you carry with you every day. Apply sunscreen before outdoor activity.', 'link'),
(1, 'followupplan', 'goal', 'Weight management', 'http://www.cancer.net/navigating-cancer-care/prevention-and-healthy-living/obesity-and-cancer/obesity-weight-and-cancer-risk', 
'Talk to doctor and/or nutritionist about weight management programs.
Take time out when needed.
Exercise.
Get rid of clutter, surround yourself with what you like.
Learn to relax at will - deep breathing, visualization, etc.
Manage your "self-talk".
Practice saying "no".
Organize early to avoid rushing.
Delegate work to others.
Take naps and drink a lot of fluids.', 'text');

update nav.inputdefaultmaster set tiptext = 'http://www.cancer.gov/cancertopics/coping/radiation-side-effects/mouthandthroat.pdf' where field =  'concern' and fieldtext = 'Difficulty swallowing';
update nav.inputdefaultmaster set tiptext = 'http://www.cancer.gov/cancertopics/coping/radiation-side-effects/fatigue.pdf' where field =  'concern' and fieldtext = 'Fatigue';
update nav.inputdefaultmaster set tiptext = 'http://www.cancer.gov/cancertopics/coping/eatinghints.pdf' where field =  'concern' and fieldtext = 'Problems eating';

INSERT INTO nav.inputdefaultmaster(diseaseid, page, field, fieldtext, tiptext, otherfield, tiptype) VALUES 
(1, 'followupplan', 'concern', 'Bladder irritation', 'http://www.cancer.gov/cancertopics/coping/radiation-side-effects/urination.pdf', 'Ask doctor about treatment options.', 'link')
,(1, 'followupplan', 'concern', 'Cough', 'This could be a symptom of radiation pneumonitis. If left untreated, radiation pneumonitis can cause radiation fibrosis (permanent scarring of the lungs from radiation), which can lead to more serious heart and lung problems.', 'Ask doctor about treatment options.', 'text')
,(1, 'followupplan', 'concern', 'Diarrhea', 'http://www.cancer.net/navigating-cancer-care/side-effects/diarrhea', 'Talk to your doctor about treatment options. Drink 8 to 12 cups of clear liquid per day. Eat many small meals and snacks. Eat foods that are easy on the stomach. Take care of your rectal area.', 'link') 
,(1, 'followupplan', 'concern', 'Emotional and mental health', '', 'Ask doctor about treatment options.', '') 
,(1, 'followupplan', 'concern', 'Fertility', 'http://www.cancer.net/survivorship/life-after-cancer/having-baby-after-cancer-fertility-assistance-and-other-options', 'Ask doctor about treatment options.', 'link') 
,(1, 'followupplan', 'concern', 'Fever', 'A fever can be a sign that your body has an infection. Fevers can also cause other problems, such as chills, shivering and headaches. It is important, therefore, to investigate the cause of infection and to treat it appropriately. It is also possible to have an infection but to not have a fever – just to feel unwell.', 'Contact your doctor immediately.', 'text') 
,(1, 'followupplan', 'concern', 'Financial advice or assistance', 'http://www.aacr.org/AdvocacyPolicy/SurvivorPatientAdvocacy/PAGES/HOW-TO-FIND-FINANCIAL-AID-AND-ADVICE.ASPX#.VHoiVDHF-O0', 'Ask social worker or case manager about support programs and options.', 'link') 
,(1, 'followupplan', 'concern', 'Incontinence', '', 'Ask doctor about treatment options.', '') 
,(1, 'followupplan', 'concern', 'Insurance', 'http://www.cancer.org/treatment/findingandpayingfortreatment/managinginsuranceissues/healthinsuranceandfinancialassistanceforthecancerpatient/health-insurance-and-financial-assistance-toc', 'Ask social worker or case manager about support programs and options.', 'link') 
,(1, 'followupplan', 'concern', 'Leg swelling', '', 'Minimal to pronounced lower leg swelling can occur. Symptom control with compression hose, lymphedema massage or specialized physical therapy can be ordered.', '') 
,(1, 'followupplan', 'concern', 'Lymphedema', 'http://www.cancer.gov/cancertopics/pdq/supportivecare/lymphedema/Patient/page1', 'Ask doctor about treatment options.', 'link') 
,(1, 'followupplan', 'concern', 'Memory or concentration loss', 'http://www.cancer.net/navigating-cancer-care/side-effects/attention-thinking-or-memory-problems', 'Ask doctor about treatment options.', 'link') 
,(1, 'followupplan', 'concern', 'Menopause symptoms', '', 'Hot flashes, night sweats and vaginal dryness may occur. See your health care professionals about non-medication recommendations and medication-based treatment.', '') 
,(1, 'followupplan', 'concern', 'Mouth or teeth problems', 'http://www.cancer.gov/cancertopics/coping/radiation-side-effects/mouthandthroat.pdf', 'Ask doctor about treatment options.', 'link') 
,(1, 'followupplan', 'concern', 'Nausea', 'http://www.cancer.net/research-and-advocacy/asco-care-and-treatment-recommendations-patients/preventing-vomiting-caused-cancer-treatment', 'Ask doctor about medical and behavioral therapy options.', 'link') 
,(1, 'followupplan', 'concern', 'Neuropathy', 'http://www.cancer.net/navigating-cancer-care/side-effects/peripheral-neuropathy', 'Ask doctor about treatment options.', 'link') 
,(1, 'followupplan', 'concern', 'Pain', '', 'Ask doctor about treatment options.', '') 
,(1, 'followupplan', 'concern', 'Parenting', '', 'Ask social worker or case manager about support programs and options.', '') 
,(1, 'followupplan', 'concern', 'Physical Functioning', '', 'Ask doctor about treatment options.', '') 
,(1, 'followupplan', 'concern', 'Rectal Bleeding', '', 'Ask doctor about treatment options.', '') 
,(1, 'followupplan', 'concern', 'School/Work', 'http://www.cancer.net/survivorship/life-after-cancer/having-baby-after-cancer-fertility-assistance-and-other-options', 'Ask social worker or case manager about support programs and options.', 'link') 
,(1, 'followupplan', 'concern', 'Sexual functioning', '', 'Ask doctor about treatment options.', '') 
,(1, 'followupplan', 'concern', 'Stopping smoking', 'http://www.nccn.org/patients/resources/survivorship/smoking.aspx', 'Ask doctor about treatment options.', 'link') 
,(1, 'followupplan', 'concern', 'Vaginal dryness and vaginal tightening', '', 'Use of a lubricant and dilator can help prevent or improve vaginal symptoms.', '') 
,(1, 'followupplan', 'concern', 'Vomiting', 'http://www.cancer.net/research-and-advocacy/asco-care-and-treatment-recommendations-patients/preventing-vomiting-caused-cancer-treatment', 'Ask doctor about medical and behavioral therapy options.', 'link') 
,(1, 'followupplan', 'concern', 'Weight changes', 'http://www.cancer.gov/cancertopics/coping/life-after-treatment/page4#c7', 'Ask doctor and/or nutritionist about options.', 'link') 
;

