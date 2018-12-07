/**
 * Table Name  : EO_MENU_AUTH_LINK
 * Description : Menu - Authority group mapping
 */
drop table eo_menu_auth_link cascade constraints;
purge recyclebin;

create table eo_menu_auth_link (
    group_id                        varchar2(60)                                        not null,   -- Authority group UID (PK) (lookup_type = PORTAL_SECURITY_GROUP [sys_common_lookup.lookup_code])
    menu_id                         varchar2(30)                                        not null,   -- Menu UID (PK) ([sys_menu.menu_id])
    insert_user_id                  varchar2(30),                                                   -- Insert User UID
    insert_date                     date                        default sysdate,                    -- Insert Date
    update_user_id                  varchar2(30),                                                   -- Update User UID
    update_date                     date,                                                           -- Update Date

    constraint pk_eo_menu_auth_link primary key(group_id, menu_id)
);

comment on table  eo_menu_auth_link                 is 'Menu - Authority group mapping';
comment on column eo_menu_auth_link.group_id        is 'Authority group UID (PK) Authority group UID (PK) (lookup_type = PORTAL_SECURITY_GROUP [sys_common_lookup.lookup_code])';
comment on column eo_menu_auth_link.menu_id         is 'Menu UID (PK) ([eo_menu.menu_id])';
comment on column eo_menu_auth_link.insert_user_id  is 'Insert User UID';
comment on column eo_menu_auth_link.insert_date     is 'Insert Date';
comment on column eo_menu_auth_link.update_user_id  is 'Update User UID';
comment on column eo_menu_auth_link.update_date     is 'Update Date';


/**
 * Table Name  : eo_MENU_AUTH_LINK
 * Data        : Menu - Authority group mapping
 */
delete eo_menu_auth_link;

insert into eo_menu_auth_link (
select 'administrator' as group_id,
       eo_menu.menu_id,
       (select user_id from sys_users where user_name = 'admdustin') as insert_user_id,
       sysdate as insert_date,
       null as update_user_id,
       null as update_date
  from eo_menu
)
;

insert into eo_menu_auth_link (
select 'ENTITY_SOLUTIONS_STAFF' as group_id,
       eo_menu.menu_id,
       (select user_id from sys_users where user_name = 'admdustin') as insert_user_id,
       sysdate as insert_date,
       null as update_user_id,
       null as update_date
  from eo_menu
 where menu_id in ('HOME', 'MTF', 'MPF', 'DOC', 'PAY', 'TMS', 'FRM', 'SEP', 'SVC', 'IPR', 'CTR', 'INV', 'CON')
)
;

insert into eo_menu_auth_link (
select 'PREMIUM_IPRO_PORTAL_USER' as group_id,
       eo_menu.menu_id,
       (select user_id from sys_users where user_name = 'admdustin') as insert_user_id,
       sysdate as insert_date,
       null as update_user_id,
       null as update_date
  from eo_menu
 where menu_id in ('HOME', 'MTF', 'MPF', 'DOC', 'PAY', 'TMS', 'FRM', 'SEP', 'SVC', 'CON')
)
;

insert into eo_menu_auth_link (
select 'TEMPORARY_IPRO_PORTAL_USER' as group_id,
       eo_menu.menu_id,
       (select user_id from sys_users where user_name = 'admdustin') as insert_user_id,
       sysdate as insert_date,
       null as update_user_id,
       null as update_date
  from eo_menu
 where menu_id in ('HOME', 'MTF', 'MPF', 'DOC', 'PAY', 'TMS', 'FRM', 'SEP', 'SVC', 'CON')
)
;

insert into eo_menu_auth_link (
select 'GENERAL_CORPORATE_USER' as group_id,
       eo_menu.menu_id,
       (select user_id from sys_users where user_name = 'admdustin') as insert_user_id,
       sysdate as insert_date,
       null as update_user_id,
       null as update_date
  from eo_menu
 where menu_id in ('HOME', 'IPR', 'INV', 'CTR', 'TMS', 'FRM', 'CON')
)
;


/*
insert into eo_menu_auth_link (
select auth_group.group_id,
       eo_menu.menu_id,
       (select user_id from sys_users where user_name = 'admdustin') as insert_user_id,
       sysdate as insert_date,
       null as update_user_id,
       null as update_date
  from (select lookup_code as group_id
          from sys_common_lookups
         where lookup_type = 'PORTAL_SECURITY_GROUP'
       ) auth_group,
       eo_menu
-- where auth_group.group_id = '0'
)
;
*/