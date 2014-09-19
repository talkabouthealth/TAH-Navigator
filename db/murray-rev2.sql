update nav.contactmethodmaster SET name = 'Text message' where name = 'SMS';
update nav.contactmethodmaster SET name = 'Phone' where name = 'Call';
delete from nav.contactmethodmaster where name = 'Mail';
