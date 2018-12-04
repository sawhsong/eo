/**
 * Table Name  : Domain Dictionary
 * Description : Database column data types
 */
drop table zebra_domain_dictionary cascade constraints;
purge recyclebin;

create table zebra_domain_dictionary (
    domain_id                       varchar2(30)                                        not null,   -- Domain item UID (PK)
    domain_name                     varchar2(100)                                       not null,   -- Domain item name
    name_abbreviation               varchar2(50)                                        not null,   -- Domain item name abbreviated
    data_type                       varchar2(30)                                        not null,   -- Data type ([zebra_common_code.DOMAIN_DATA_TYPE])
    data_precision                  number,                                                         -- Data precision ([zebra_common_code.DOMAIN_DATA_PRECISION])
    data_scale                      number,                                                         -- Data scale ([zebra_common_code.DOMAIN_DATA_SCALE])
    data_length                     number,                                                         -- Data length ([zebra_common_code.DOMAIN_DATA_LENGTH])
    description                     varchar2(4000),                                                 -- Description
    insert_user_id                  varchar2(30),                                                   -- Insert User UID
    insert_date                     date                        default sysdate,                    -- Insert Date
    update_user_id                  varchar2(30),                                                   -- Update User UID
    update_date                     date,                                                           -- Update Date

    constraint pk_zebra_domain_dictionary primary key(domain_id),
    constraint uk_zebra_domain_dictionary unique(domain_name, name_abbreviation)
    using index tablespace alpaca_idx storage(initial 50k next 50k pctincrease 0)
)
pctfree 20 pctused 80 tablespace alpaca_data storage(initial 100k next 100k maxextents 2000 pctincrease 0);

comment on table  zebra_domain_dictionary                     is 'Domain Dictionary';
comment on column zebra_domain_dictionary.domain_id           is 'Domain item UID (PK)';
comment on column zebra_domain_dictionary.domain_name         is 'Domain item name';
comment on column zebra_domain_dictionary.name_abbreviation   is 'Domain item name abbreviated';
comment on column zebra_domain_dictionary.data_type           is 'Data type ([zebra_common_code.DOMAIN_DATA_TYPE])';
comment on column zebra_domain_dictionary.data_precision      is 'Data precision ([zebra_common_code.DOMAIN_DATA_PRECISION])';
comment on column zebra_domain_dictionary.data_scale          is 'Data scale ([zebra_common_code.DOMAIN_DATA_SCALE])';
comment on column zebra_domain_dictionary.data_length         is 'Data length ([zebra_common_code.DOMAIN_DATA_LENGTH])';
comment on column zebra_domain_dictionary.description         is 'Description';
comment on column zebra_domain_dictionary.insert_user_id      is 'Insert User UID';
comment on column zebra_domain_dictionary.insert_date         is 'Insert Date';
comment on column zebra_domain_dictionary.update_user_id      is 'Update User UID';
comment on column zebra_domain_dictionary.update_date         is 'Update Date';


/**
 * Table Name  : Domain Dictionary
 * Data        :
 */
delete zebra_domain_dictionary;

insert into zebra_domain_dictionary values('1',    'ID',                   'id',                   'VARCHAR2',  null,  null,  50,    'uid, id',                           '0',  sysdate,  '',  '');
insert into zebra_domain_dictionary values('2',    'Password',             'password',             'VARCHAR2',  null,  null,  30,    'password',                          '0',  sysdate,  '',  '');
insert into zebra_domain_dictionary values('3',    'Common Code',          'common_code',          'VARCHAR2',  null,  null,  30,    'common_code',                       '0',  sysdate,  '',  '');
insert into zebra_domain_dictionary values('4',    'Normal Name',          'name',                 'VARCHAR2',  null,  null,  50,    'person name, user name, org name',  '0',  sysdate,  '',  '');
insert into zebra_domain_dictionary values('5',    'File Name',            'file_name',            'VARCHAR2',  null,  null,  1000,  'file name, image/icon name',        '0',  sysdate,  '',  '');
insert into zebra_domain_dictionary values('6',    'Menu Name',            'menu_name',            'VARCHAR2',  null,  null,  500,   'menu name',                         '0',  sysdate,  '',  '');
insert into zebra_domain_dictionary values('7',    'Email',                'email',                'VARCHAR2',  null,  null,  100,   'email',                             '0',  sysdate,  '',  '');
insert into zebra_domain_dictionary values('8',    'IP Address',           'ip_address',           'VARCHAR2',  null,  null,  50,    'ip_address (considering ipv6)',     '0',  sysdate,  '',  '');
insert into zebra_domain_dictionary values('9',    'Size',                 'size',                 'NUMBER',    24,    2,     24,    'size',                              '0',  sysdate,  '',  '');
insert into zebra_domain_dictionary values('10',   'Quantity',             'qty',                  'NUMBER',    24,    2,     24,    'quantity',                          '0',  sysdate,  '',  '');
insert into zebra_domain_dictionary values('11',   'File Size',            'file_path',            'VARCHAR2',  null,  null,  2000,  'file/directory path',               '0',  sysdate,  '',  '');
insert into zebra_domain_dictionary values('12',   'Description(Short)',   'description',          'VARCHAR2',  null,  null,  1000,  'short description',                 '0',  sysdate,  '',  '');
insert into zebra_domain_dictionary values('13',   'Description(Normal)',  'description',          'VARCHAR2',  null,  null,  2000,  'normal description',                '0',  sysdate,  '',  '');
insert into zebra_domain_dictionary values('14',   'File Type',            'file_type',            'VARCHAR2',  null,  null,  300,   'file type',                         '0',  sysdate,  '',  '');
insert into zebra_domain_dictionary values('15',   'Constants',            'constants',            'VARCHAR2',  null,  null,  100,   'constants (common code constants)', '0',  sysdate,  '',  '');
