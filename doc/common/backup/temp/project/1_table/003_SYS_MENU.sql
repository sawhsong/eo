/**
 * Category    : SYS
 * Table Name  : SYS_MENU
 * Description : Menu Info - Use Excel file to initialise data (SYS_MENU.xlsx)
 */
drop table sys_menu cascade constraints;
purge recyclebin;

create table sys_menu (
    menu_id                         varchar2(30)                                        not null,   -- Menu UID (PK)
    parent_menu_id                  varchar2(30),                                                   -- Parent menu UID
    menu_name_ko                    varchar2(500)                                       not null,   -- Menu name (Korean)
    menu_name_en                    varchar2(500)                                       not null,   -- Menu name (English)
    menu_url                        varchar2(500),                                                  -- Menu URL
    menu_icon                       varchar2(100),                                                  -- Menu Icon
    sort_order                      varchar2(10),                                                   -- Sort order
    description                     varchar2(1000),                                                 -- Description
    is_active                       varchar2(1),                                                    -- Is active ?
    insert_user_id                  varchar2(30),                                                   -- Insert User UID
    insert_date                     date                        default sysdate,                    -- Insert Date
    update_user_id                  varchar2(30),                                                   -- Update User UID
    update_date                     date,                                                           -- Update Date

    constraint pk_sys_menu primary key(menu_id)
    using index tablespace alpaca_idx storage(initial 50k next 50k pctincrease 0)
)
pctfree 20 pctused 80 tablespace alpaca_data storage(initial 100k next 100k maxextents 2000 pctincrease 0);

comment on table  sys_menu                 is 'Menu Info';
comment on column sys_menu.menu_id         is 'Menu UID (PK)';
comment on column sys_menu.parent_menu_id  is 'Parent menu UID';
comment on column sys_menu.menu_name_ko    is 'Menu name (Korean)';
comment on column sys_menu.menu_name_en    is 'Menu name (English)';
comment on column sys_menu.menu_url        is 'Menu URL';
comment on column sys_menu.menu_icon       is 'Menu Icon';
comment on column sys_menu.sort_order      is 'Sort order';
comment on column sys_menu.description     is 'Description';
comment on column sys_menu.is_active       is 'Is active?';
comment on column sys_menu.insert_user_id  is 'Insert User UID';
comment on column sys_menu.insert_date     is 'Insert Date';
comment on column sys_menu.update_user_id  is 'Update User UID';
comment on column sys_menu.update_date     is 'Update Date';


/**
 * Category    : SYS
 * Table Name  : SYS_MENU
 * Description : Menu Info - Use Excel file to initialise data (SYS_MENU.xlsx)
 */
delete sys_menu;

-- PERCI Menu
/*
 select connect_by_root sequence_number||'/'||sub_menu_id as my_root,
        substr(sys_connect_by_path(sequence_number||'/'||sub_menu_id, '^'), 2) as connect_path,
        level as my_level,
        connect_by_isleaf as is_leaf,
        menu_id,
        sub_menu_id,
        sequence_number,
        prompt,
        (select jsp_page
           from sys_user_function@perci
          where function_id = smd.function_id
        ) jsp_link
   from sys_menu_details@perci smd
connect by prior sub_menu_id = menu_id
  start with menu_id = (select menu_id
                          from sys_menus@perci
                         where user_menu_name = 'Entity_Responsibilities'
                       )
 order siblings by sequence_number
;
*/