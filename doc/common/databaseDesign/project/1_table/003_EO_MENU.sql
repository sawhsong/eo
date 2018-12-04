/**
 * Table Name  : EO_MENU
 * Description : Menu Info - Use Excel file to initialise data (eo_menu.xlsx)
 */
drop table eo_menu cascade constraints;
purge recyclebin;

create table eo_menu (
    menu_id                         varchar2(30)                                        not null,   -- Menu UID (PK)
    parent_menu_id                  varchar2(30),                                                   -- Parent menu UID
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
delete eo_menu;
