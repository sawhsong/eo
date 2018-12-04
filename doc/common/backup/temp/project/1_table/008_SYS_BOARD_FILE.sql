/**
 * Category    : SYS
 * Table Name  : SYS_BOARD_FILE
 * Description : Attached file for Bulletin board
 */
drop table sys_board_file cascade constraints;
purge recyclebin;

create table sys_board_file (
    file_id                         varchar2(30)                                        not null,   -- File item UID (PK)
    article_id                      varchar2(30)                                        not null,   -- BBS Article UID ([sys_board.article_id])
    original_name                   varchar2(1000)                                      not null,   -- Real file name
    new_name                        varchar2(1000)                                      not null,   -- System defined file name
    file_type                       varchar2(300),                                                  -- File type
    file_icon                       varchar2(1000),                                                 -- File Icon path and name
    file_size                       number(12,2),                                                   -- File size (KB)
    repository_path                 varchar2(2000)                                      not null,   -- Saved file path
    insert_user_id                  varchar2(30),                                                   -- Insert User UID
    insert_date                     date                        default sysdate,                    -- Insert Date
    update_user_id                  varchar2(30),                                                   -- Update User UID
    update_date                     date,                                                           -- Update Date

    constraint fk_sys_board_file_sys_board foreign key(article_id) references sys_board(article_id),
    constraint pk_sys_board_file primary key(file_id)

    using index tablespace alpaca_idx storage(initial 50k next 50k pctincrease 0)
)
pctfree 20 pctused 80 tablespace alpaca_data storage(initial 100k next 100k maxextents 2000 pctincrease 0);

comment on table  sys_board_file                    is 'Attached file for Bulletin board';
comment on column sys_board_file.file_id            is 'File item UID (PK)';
comment on column sys_board_file.article_id         is 'BBS Article UID ([sys_board.article_id])';
comment on column sys_board_file.original_name      is 'Real file name';
comment on column sys_board_file.new_name           is 'System defined file name';
comment on column sys_board_file.file_type          is 'File type';
comment on column sys_board_file.file_icon          is 'File Icon path and name';
comment on column sys_board_file.file_size          is 'File size (KB)';
comment on column sys_board_file.repository_path    is 'Saved file path';
comment on column sys_board_file.insert_user_id     is 'Insert User UID';
comment on column sys_board_file.insert_date        is 'Insert Date';
comment on column sys_board_file.update_user_id     is 'Update User UID';
comment on column sys_board_file.update_date        is 'Update Date';


/**
 * Category    : SYS
 * Table Name  : SYS_BOARD_FILE
 * Description : Attached file for Bulletin board
 */
