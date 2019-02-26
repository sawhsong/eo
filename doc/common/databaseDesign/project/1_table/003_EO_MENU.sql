/**
 * Table Name  : EO_MENU
 * Description : 
 */
drop table eo_menu cascade constraints;
purge recyclebin;

create table eo_menu (
    menu_id                         varchar2(100)                                       not null,   -- Menu UID (PK)
    parent_menu_id                  varchar2(100),                                                  -- Parent menu UID
    menu_name                       varchar2(500)                                       not null,   -- Menu name
    menu_url                        varchar2(500),                                                  -- Menu URL
    menu_icon                       varchar2(100),                                                  -- Menu Icon
    sort_order                      varchar2(10),                                                   -- Sort order
    description                     varchar2(1000),                                                 -- Description
    is_active                       varchar2(1),                                                    -- Is active ?
    insert_user_id                  varchar2(30),                                                   -- Insert User UID
    insert_date                     date                        default sysdate,                    -- Insert Date
    update_user_id                  varchar2(30),                                                   -- Update User UID
    update_date                     date,                                                           -- Update Date

    constraint pk_eo_menu primary key(menu_id)
);

comment on table  eo_menu                 is 'Menu Info';
comment on column eo_menu.menu_id         is 'Menu UID (PK)';
comment on column eo_menu.parent_menu_id  is 'Parent menu UID';
comment on column eo_menu.menu_name       is 'Menu name';
comment on column eo_menu.menu_url        is 'Menu URL';
comment on column eo_menu.menu_icon       is 'Menu Icon';
comment on column eo_menu.sort_order      is 'Sort order';
comment on column eo_menu.description     is 'Description';
comment on column eo_menu.is_active       is 'Is active?';
comment on column eo_menu.insert_user_id  is 'Insert User UID';
comment on column eo_menu.insert_date     is 'Insert Date';
comment on column eo_menu.update_user_id  is 'Update User UID';
comment on column eo_menu.update_date     is 'Update Date';


/**
 * Table Name  : eo_menu
 * Data        : Menu Info - Use Excel file to initialise data (eo_menu.xlsx)
 */
set define off;
delete eo_menu;

insert into eo_menu (menu_id, parent_menu_id, menu_name, menu_url, menu_icon, sort_order, description, is_active, insert_user_id, insert_date, update_user_id, update_date)
values ('LeaveManagement', null, 'Leave Admin', '/employee/leaveadm/leavelist', 'LVE', '030000', 'Leave Management for Administrator(Employee)', 'Y', '0', to_date('2018-12-05', 'yyyy-dd-mm'), null, null);

