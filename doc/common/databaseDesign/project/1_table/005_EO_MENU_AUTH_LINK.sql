/**
 * Table Name  : EO_MENU_AUTH_LINK
 * Description : Menu - Authority group mapping
 */
drop table eo_menu_auth_link cascade constraints;
purge recyclebin;

create table eo_menu_auth_link (
    group_id                        varchar2(30)                                        not null,   -- Authority group UID (PK) ([sys_auth_group.auth_id])
    menu_id                         varchar2(30)                                        not null,   -- Menu UID (PK) ([sys_menu.menu_id])
    insert_user_id                  varchar2(30),                                                   -- Insert User UID
    insert_date                     date                        default sysdate,                    -- Insert Date
    update_user_id                  varchar2(30),                                                   -- Update User UID
    update_date                     date,                                                           -- Update Date

    constraint pk_eo_menu_auth_link primary key(group_id, menu_id)
);

comment on table  eo_menu_auth_link                 is 'Menu - Authority group mapping';
comment on column eo_menu_auth_link.group_id        is 'Authority group UID (PK) ([eo_auth_group.auth_id])';
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
/*
insert into eo_menu_auth_link (
	select eo_auth_group.group_id,
	       eo_menu.menu_id,
	       0,
	       sysdate,
	       null,
	       null
	  from sys_auth_group,
	       eo_menu
	 where sys_auth_group.group_id = '0'
)
;
*/