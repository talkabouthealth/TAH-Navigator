ALTER TABLE nav.inputdefaultmaster ADD COLUMN frequency character varying(100);

update nav.inputdefaultmaster set frequency = 'Every 6 months' where fieldtext = 'Medical history and physical exam';
update nav.inputdefaultmaster set frequency = 'Every year' where fieldtext = 'Post-treatment mammography';
update nav.inputdefaultmaster set frequency = 'Monthly' where fieldtext = 'Breast self-exam';
update nav.inputdefaultmaster set frequency = 'Every year' where fieldtext = 'Pelvic exam';
update nav.inputdefaultmaster set frequency = 'One appointment' where fieldtext = 'Genetic counselling';


update nav.inputdefaultmaster  set otherfield = 'Set priorities
Take time out when needed.
Exercise
Get rid of clutter, surround yourself with what you like.
Learn to relax at will - deep breathing, visualization, etc.
Manage your "self-talk".
Practice saying "no".'
where field =  'goal' and fieldtext = 'Manage Stress';


update nav.inputdefaultmaster  set otherfield = 'Wear loose fitting clothes and slip on shoes. 
Organize early to avoid rushing. 
Delegate work to others. 
Take naps and drink a lot of fluids.'
where field =  'goal' and fieldtext = 'Conserve Energy';

ALTER TABLE nav.inputdefaultmaster ADD COLUMN tiptype character varying(10) default 'text';

update nav.inputdefaultmaster  set tiptype = 'link' where field =  'goal';
update nav.inputdefaultmaster  set tiptype = 'link' where id in (6, 7, 8, 9, 10);