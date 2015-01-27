ALTER TABLE appointmentalert RENAME to notifications;

ALTER TABLE nav.notifications DROP COLUMN alert_detail;
ALTER TABLE nav.notifications DROP COLUMN alert_sent;
ALTER TABLE nav.notifications DROP COLUMN alert_thru;
ALTER TABLE nav.notifications DROP COLUMN appointment_id;


ALTER TABLE nav.notifications ADD COLUMN category char(200);
ALTER TABLE nav.notifications ADD COLUMN related_id int;
ALTER TABLE nav.notifications ADD COLUMN notified_to int;
ALTER TABLE nav.notifications ADD COLUMN communication int;
ALTER TABLE nav.notifications ADD COLUMN description text;
ALTER TABLE nav.notifications ADD COLUMN scheduled_time timestamp;
ALTER TABLE nav.notifications ADD COLUMN notified boolean DEFAULT false;
ALTER TABLE nav.notifications ADD COLUMN discard boolean DEFAULT false;
ALTER TABLE nav.notifications ADD COLUMN priority int;
