/**
 * Category    : SYS
 * Table Name  : SYS_USER
 * Description : User Info - Use Excel file to initialise data (SYS_USER_1.xlsx, SYS_USER_2.xlsx)
 */
drop table sys_user cascade constraints;
purge recyclebin;

create table sys_user (
    user_id                         varchar2(30)                                        not null,   -- User UID (PK)
    user_name                       varchar2(50)                                        not null,   -- User name
    login_id                        varchar2(30)                                        not null,   -- Login ID
    login_password                  varchar2(30)                                        not null,   -- Login password
    person_id                       varchar2(30)                                        not null,   -- Person UID ([PERCI.HP_PERSON_D.PERSON_ID])
    auth_group_id                   varchar2(30)                default 'Z'             not null,   -- Authority group code for menu access ([sys_auth_group.auth_id], Default : Z)
    language                        varchar2(30)                                        not null,   -- Language ([sys_common_code.language_type])
    theme_type                      varchar2(30)                                        not null,   -- Theme ID ([sys_common_code.user_theme_type])
    user_type                       varchar2(30)                                        not null,   -- User type ([sys_common_code.user_type])
    email                           varchar2(100),                                                  -- Email
    max_row_per_page                number(5)                                           not null,   -- Number of rows to display on a page (config.properties - view.data.maxRowsPerPage)
    page_num_per_page               number(5)                                           not null,   -- Number of pages to display on a page (config.properties - view.data.pageNumsPerPage)
    user_status                     varchar2(30)                                        not null,   -- User status ([sys_common_code.user_status])
    photo_path                      varchar2(1000),                                                 -- User photo path
    is_active                       varchar2(1)                                         not null,   -- Is active ?
    description                     varchar2(1000),                                                 -- From PERCI(Description for the User)
    prop_to_portal                  varchar2(3),                                                    -- From PERCI(For What?)
    pin                             varchar2(30),                                                   -- From PERCI(For What?)
    disabled_date                   date,                                                           -- From PERCI(For What?)
    security_question_1             varchar2(60),                                                   -- From PERCI(For What? - sys_common_code.PORTAL_SECURITY_QUESTIONS)
    security_question_answer_1      varchar2(500),                                                  -- From PERCI()
    security_question_2             varchar2(60),                                                   -- From PERCI(For What? - sys_common_code.PORTAL_SECURITY_QUESTIONS)
    security_question_answer_2      varchar2(500),                                                  -- From PERCI()
    portal_skin                     varchar2(60),                                                   -- From PERCI(sys_common_code.PORTAL_SKIN)
    portal_security_role            varchar2(60),                                                   -- From PERCI(sys_common_code.PORTAL_SECURITY_GROUP)
    reset_password                  varchar2(30),                                                   -- From PERCI(For What?)
    reset_term_condition            varchar2(30),                                                   -- From PERCI(For What?)
    is_portal_user                  varchar2(30),                                                   -- From PERCI(For What?)
    portal_org_profile_id           varchar2(30),                                                   -- From PERCI(For What?)
    insert_user_id                  varchar2(30),                                                   -- Insert User UID
    insert_date                     date                        default sysdate,                    -- Insert Date
    update_user_id                  varchar2(30),                                                   -- Update User UID
    update_date                     date,                                                           -- Update Date

    constraint fk_sys_user_auth_group foreign key(auth_group_id) references sys_auth_group(group_id),
    constraint pk_sys_user primary key(user_id),
    constraint uk_sys_user unique(login_id, login_password)
    using index tablespace alpaca_idx storage(initial 50k next 50k pctincrease 0)
)
pctfree 20 pctused 80 tablespace alpaca_data storage(initial 100k next 100k maxextents 2000 pctincrease 0);

comment on table  sys_user                            is 'User Info';
comment on column sys_user.user_id                    is 'User UID (PK)';
comment on column sys_user.user_name                  is 'User name';
comment on column sys_user.login_id                   is 'Login ID';
comment on column sys_user.login_password             is 'Login password';
comment on column sys_user.person_id                  is 'Person UID ([PERCI.HP_PERSON_D.PERSON_ID])';
comment on column sys_user.auth_group_id              is 'Authority group code for menu access ([sys_auth_group.auth_id], Default : Z)';
comment on column sys_user.language                   is 'Language ([sys_common_code.language_type])';
comment on column sys_user.theme_type                 is 'Theme ID ([sys_common_code.user_theme_type])';
comment on column sys_user.user_type                  is 'User type ([sys_common_code.user_type])';
comment on column sys_user.email                      is 'Email';
comment on column sys_user.max_row_per_page           is 'Number of rows to display on a page (config.properties - view.data.maxRowsPerPage)';
comment on column sys_user.page_num_per_page          is 'Number of pages to display on a page (config.properties - view.data.pageNumsPerPage)';
comment on column sys_user.user_status                is 'User status ([sys_common_code.user_status])';
comment on column sys_user.photo_path                 is 'User photo path';
comment on column sys_user.is_active                  is 'Is active?';
comment on column sys_user.description                is 'From PERCI(Description for the User)';
comment on column sys_user.prop_to_portal             is 'From PERCI(For What?)';
comment on column sys_user.pin                        is 'From PERCI(For What?)';
comment on column sys_user.disabled_date              is 'From PERCI(For What?)';
comment on column sys_user.security_question_1        is 'From PERCI(For What? - sys_common_code.PORTAL_SECURITY_QUESTIONS)';
comment on column sys_user.security_question_answer_1 is 'From PERCI()';
comment on column sys_user.security_question_2        is 'From PERCI(For What? - sys_common_code.PORTAL_SECURITY_QUESTIONS)';
comment on column sys_user.security_question_answer_2 is 'From PERCI()';
comment on column sys_user.portal_skin                is 'From PERCI(sys_common_code.PORTAL_SKIN)';
comment on column sys_user.portal_security_role       is 'From PERCI(sys_common_code.PORTAL_SECURITY_GROUP)';
comment on column sys_user.reset_password             is 'From PERCI(For What?)';
comment on column sys_user.reset_term_condition       is 'From PERCI(For What?)';
comment on column sys_user.is_portal_user             is 'From PERCI(For What?)';
comment on column sys_user.portal_org_profile_id      is 'From PERCI(For What?)';
comment on column sys_user.insert_user_id             is 'Insert User UID';
comment on column sys_user.insert_date                is 'Insert Date';
comment on column sys_user.update_user_id             is 'Update User UID';
comment on column sys_user.update_date                is 'Update Date';


/**
 * Category    : SYS
 * Table Name  : SYS_USER
 * Description : User Info - Use Excel file to initialise data (SYS_USER_1.xlsx, SYS_USER_2.xlsx)
 */
delete sys_user;

insert into sys_user values('0', 'Dustin', 'dustin', 'dustin', '0', '0', 'EN', 'THEME000', 'INTERNAL', 'dsa@entitysolutions.com.au', 50, 5, 'NU',
	'/shared/resource/image/photo/DefaultUser_128_Black.png', 'Y', 'System Admin - Dustin', 'Y', null, null, null, null, null, null, 'ipro-default', null, 'N', 'N', 'Y', '1',
	'0', sysdate, null, null
);
insert into sys_user values('1', 'Admin', 'admin', 'admin', '1', '1', 'EN', 'THEME000', 'INTERNAL', 'dsa@entitysolutions.com.au', 50, 5, 'NU',
	'/shared/resource/image/photo/DefaultUser_128_Black.png', 'Y', 'General Admin - Admin', 'Y', null, null, null, null, null, null, 'ipro-default', null, 'N', 'N', 'Y', '1',
	'0', sysdate, null, null
);

-- From PERCI
/*
insert into sys_user
select user_id as user_id,
       user_name as user_name,
       user_name as login_id,
       password as login_password,
       person_id as person_id,
       'Z' as auth_group_id,
       'EN' as language,
       'THEME000' as theme_type,
       'INTERNAL' as user_type,
       email as email,
       50 as max_row_per_page,
       5 as page_num_per_page,
       'NU' as user_status,
       '/shared/resource/image/photo/DefaultUser_128_Black.png' as photo_path,
       decode(is_active, null, 'Y', is_active) as is_active,
       description as description,
       prop_to_portal as prop_to_portal,
       pin as pin,
       disabled_date as disabled_date,
       security_question_1 as security_question_1,
       security_question_answer_1 as security_question_answer_1,
       security_question_2 as security_question_2,
       security_question_answer_2 as security_question_answer_2,
       portal_skin as portal_skin,
       portal_security_role as portal_security_role,
       reset_password as reset_password,
       reset_term_condition as reset_term_condition,
       is_portal_user as is_portal_user,
       portal_org_profile_id as portal_org_profile_id,
       '0' as insert_user_id,
       to_char(sysdate, 'yyyymmdd') as insert_date,
       null as update_user_id,
       null update_date
  from sys_users@perci
 where user_id not in ('0', '1')
 order by login_id, login_password
;
*/