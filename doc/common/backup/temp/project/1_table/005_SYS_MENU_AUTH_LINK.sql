/**
 * Category    : SYS
 * Table Name  : SYS_MENU_AUTH_LINK
 * Description : Menu - Authority group mapping
 */
drop table sys_menu_auth_link cascade constraints;
purge recyclebin;

create table sys_menu_auth_link (
    group_id                        varchar2(30)                                        not null,   -- Authority group UID (PK) ([sys_auth_group.auth_id])
    menu_id                         varchar2(30)                                        not null,   -- Menu UID (PK) ([sys_menu.menu_id])
    insert_user_id                  varchar2(30),                                                   -- Insert User UID
    insert_date                     date                        default sysdate,                    -- Insert Date
    update_user_id                  varchar2(30),                                                   -- Update User UID
    update_date                     date,                                                           -- Update Date

    constraint fk_sys_menu_auth_link_group foreign key(group_id) references sys_auth_group(group_id),
    constraint fk_sys_menu_auth_link_menu foreign key(menu_id) references sys_menu(menu_id),
    constraint pk_sys_menu_auth_link primary key(group_id, menu_id)

    using index tablespace alpaca_idx storage(initial 50k next 50k pctincrease 0)
)
pctfree 20 pctused 80 tablespace alpaca_data storage(initial 100k next 100k maxextents 2000 pctincrease 0);

comment on table  sys_menu_auth_link                 is 'Menu - Authority group mapping';
comment on column sys_menu_auth_link.group_id        is 'Authority group UID (PK) ([sys_auth_group.auth_id])';
comment on column sys_menu_auth_link.menu_id         is 'Menu UID (PK) ([sys_menu.menu_id])';
comment on column sys_menu_auth_link.insert_user_id  is 'Insert User UID';
comment on column sys_menu_auth_link.insert_date     is 'Insert Date';
comment on column sys_menu_auth_link.update_user_id  is 'Update User UID';
comment on column sys_menu_auth_link.update_date     is 'Update Date';


/**
 * Category    : SYS
 * Table Name  : SYS_MENU_AUTH_LINK
 * Description : Menu - Authority group mapping
 */
delete sys_menu_auth_link;

insert into sys_menu_auth_link (
	select sys_auth_group.group_id,
	       sys_menu.menu_id,
	       0,
	       sysdate,
	       null,
	       null
	  from sys_auth_group,
	       sys_menu
	 where sys_auth_group.group_id = '0'
)
;