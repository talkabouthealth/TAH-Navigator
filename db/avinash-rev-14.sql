CREATE TABLE nav.inputdefaultmaster
(
  id serial NOT NULL,
  diseaseid integer,
  page character varying(100),
  field character varying(100),
  fieldtext character varying(500),
  tiptext text,
  otherfield character varying(500),
  CONSTRAINT inputdefaultmaster_id_pk PRIMARY KEY (id),
  CONSTRAINT inputdefaultmaster_diseaseid_fk FOREIGN KEY (diseaseid)
      REFERENCES nav.diseasemaster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


INSERT INTO nav.inputdefaultmaster( diseaseid, page, field, fieldtext, tiptext, otherfield)
VALUES (1, 'followupplan', 'activity', 'Medical history and physical exam', 
'Visit your doctor every three to six months for the first three years after the first treatment, every six to 12 months for years four and five, and every year thereafter.'
, 'To check for recurrence and metastasis.'),
 (1, 'followupplan', 'activity', 'Post-treatment mammography', 
'Schedule a mammogram one year after your first mammogram that led to diagnosis, but no earlier than six months after radiation therapy. Obtain a mammogram every six to 12 months thereafter.'
, 'To detect a recurrence of breast cancer.'),
(1, 'followupplan', 'activity', 'Breast self-exam', 
'Perform a breast self-examination every month. This procedure is not a substitute for a mammogram.'
, 'To gain familiarity with your breasts so that any changes in texture, including the presence of a lump, can be detected as early as possible.'),
(1, 'followupplan', 'activity', 'Pelvic exam', 
'Continue to visit a gynecologist regularly. If you use tamoxifen, you have a greater risk for developing endometrial cancer (cancer of the lining of the uterus).  Women taking tamoxifen should report any vaginal bleeding to their doctor.'
, 'To screen for cervical cancer.'),
(1, 'followupplan', 'activity', 'Genetic counselling', 
'Tell your doctor if there is a history of cancer in your family. The following risk factors may indicate that breast cancer could run in the family:
• Ashkenazi Jewish heritage
• Personal or family history of ovarian cancer
• Any first-degree relative (mother, sister, daughter) diagnosed with breast cancer before age 50
• Two or more first-degree or second-degree relatives (grandparent, aunt, uncle) diagnosed with breast cancer
• Personal or family history of breast cancer in both breasts
• History of breast cancer in a male relative'
, 'To learn more about potential risks and whether genetic testing for mutations in BRCA1 and BRCA2 might be appropriate.');



INSERT INTO nav.inputdefaultmaster( diseaseid, page, field, fieldtext, tiptext, otherfield)
VALUES (1, 'followupplan', 'concern', 'Skin problems (dryness, itching, blistering, peeling)', 
'http://www.cancer.gov/cancertopics/coping/radiation-therapy-and-you/page8#SE8'
, 'Keep area clean and dry. Talk to doctor about potential skin care products.'),
(1, 'followupplan', 'concern', 'Fatigue', 
'http://www.cancer.gov/cancertopics/coping/radiation-therapy-and-you/page8#SE3'
, 'Light exercise with increasing physical activity is one strategy for successful management of fatigue.'),
(1, 'followupplan', 'concern', 'Problems eating', 
'http://www.cancer.gov/cancertopics/coping/radiation-therapy-and-you/page8#SE5'
, 'Brush teeth with a soft toothbrush and non-irritating toothpaste. Avoid using commercial  mouthwash. Rinse the inside of the mouth several times a day with a solution of two teaspoons baking soda and one teaspoon of table salt in one quart lukewarm water.'),
(1, 'followupplan', 'concern', 'Difficulty swallowing', 
'http://www.cancer.gov/cancertopics/coping/radiation-therapy-and-you/page8#SE9'
, 'Avoid eating any rough or sharp edged foods. Avoid very hot or really spicy foods and iced or hot drinks'),
(1, 'followupplan', 'concern', 'Shortness of breath', 
'http://www.cancer.gov/cancertopics/pdq/supportivecare/cardiopulmonary/Patient/page2'
, 'Keep a humidifier or vaporizer close. Drink plenty of liquids to help in thinning phlegm. Raise the head and chest while lying in bed. Avoid irritants, such as smoke.'),
(1, 'followupplan', 'concern', 'Breast/nipple soreness', 
'Make sure you ask your radiation oncologist to look at this. Many patients continue to have tenderness even after the surgery and radiation is done for a few years, and there may always be a little tenderness in the breast. Some people have no tenderness, while others have tenderness and swelling for a long period of time.  
<br/><br/>
Here are a few methods to try to help alleviate breast and nipple soreness:<br/>
- After the radiation, massage the breast with a nice moisturizing cream a few minutes a day to try to disperse the fluids in the breast. This may help decrease some of the swelling and tenderness, but it is an ongoing process, so don\''t be discouraged if it does not work immediately. <br/>- If there is tenderness and swelling of the breast, go to a larger bra or cup size. Sometimes, if the bra is too tight, this almost acts like a tourniquet around the breast, keeping the fluid in or making the breast feel engorged or heavy. '
, 'Either do not wear a bra, or if you are unable to manage without a bra, opt for one that is soft, loose and comfortable.'),
(1, 'followupplan', 'concern', 'Shoulder stiffness', 
'After radiation therapy, some people may have difficulty moving their shoulder because the radiation causes changes to the muscles. Ask your doctor about rehabilitation exercises that may help alleviate shoulder stiffness and maintain normal shoulder movement.'
, 'Ask doctor about exercises to keep your arms moving freely.');


INSERT INTO nav.inputdefaultmaster( diseaseid, page, field, fieldtext, tiptext, otherfield)
VALUES (1, 'followupplan', 'goal', 'Maintain Skin Integrity', 
'http://www.cancer.gov/cancertopics/coping/radiation-therapy-and-you/page8#SE8'
, 'Keep area clean and dry. Talk to doctor about potential skin care products.'),
(1, 'followupplan', 'goal', 'Manage Stress', 
'http://www.cancer.gov/cancertopics/coping/radiation-therapy-and-you/page5'
, 'Set priorities. Take time out when needed. Exercise. Get rid of clutter, surround yourself with what you like.Learn to relax at will - deep breathing, visualization, etc.Manage your "self-talk".Practice saying "no".'),
(1, 'followupplan', 'goal', 'Conserve Energy', 
'http://www.cancer.org/treatment/treatmentsandsideeffects/physicalsideeffects/fatigue/seven-ways-to-manage-cancer-related-fatigue'
, 'Wear loose fitting clothes and slip on shoes. Organize early to avoid rushing. Delegate work to others. Take naps and drink a lot of fluids.'),
(1, 'followupplan', 'goal', 'Maintain Nutritious Diet', 
'http://www.cancer.gov/cancertopics/pdq/supportivecare/nutrition/Patient/page1'
, 'Eat nutritious meals and avoid empty calories.');
