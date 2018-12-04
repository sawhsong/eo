/**
 * Table Name  : SYS_BOARD
 * Description : Bulletin board
 */
drop table sys_board cascade constraints;
purge recyclebin;

create table sys_board (
    article_id                      varchar2(30)                                        not null,   -- Article UID (PK)
    board_type                      varchar2(30)                                        not null,   -- BBS Type([sys_common_code.board_type - bbs / notice])
    parent_article_id               varchar2(30)                                        not null,   -- Parent article UID (Level 1 => 0)
    writer_id                       varchar2(30),                                                   -- Writer UID ([sys_user.user_id] Anonymous user is allowed to write an article with only name)
    writer_name                     varchar2(50)                                        not null,   -- Writer Name (Anonymous user is allowed to write an article with only name)
    article_password                varchar2(30),                                                   -- Article Password (if the article is confidential)
    writer_email                    varchar2(100),                                                  -- Writer e-mail
    writer_ip_address               varchar2(50),                                                   -- Writer IP Adress
    article_subject                 varchar2(1000),                                                 -- Subject
    article_contents                clob                        default empty_clob(),               -- Contents
    hit_cnt                         number(5)                   default 0               not null,   -- Hit count
    is_confidential                 varchar2(1)                 default 'N',                        -- Is confidential?
    insert_user_id                  varchar2(30),                                                   -- Insert User UID
    insert_date                     date                        default sysdate,                    -- Insert Date
    update_user_id                  varchar2(30),                                                   -- Update User UID
    update_date                     date,                                                           -- Update Date

    constraint pk_sys_board primary key(article_id)
    using index tablespace alpaca_idx storage(initial 50k next 50k pctincrease 0)
)
pctfree 20 pctused 80 tablespace alpaca_data storage(initial 100k next 100k maxextents 2000 pctincrease 0);

comment on table  sys_board                     is 'Bulletin Board';
comment on column sys_board.article_id          is 'Article UID (PK)';
comment on column sys_board.board_type          is 'BBS Type([sys_common_code.board_type - bbs / notice])';
comment on column sys_board.parent_article_id   is 'Parent article UID (Level 1 => 0)';
comment on column sys_board.writer_id           is 'Writer UID ([sys_user.user_id] Anonymous user is allowed to write an article with only name)';
comment on column sys_board.writer_name         is 'Writer Name (Anonymous user is allowed to write an article with only name)';
comment on column sys_board.article_password    is 'Article Password (if the article is confidential)';
comment on column sys_board.writer_email        is 'Writer e-mail';
comment on column sys_board.writer_ip_address   is 'Writer IP Adress';
comment on column sys_board.article_subject     is 'Subject';
comment on column sys_board.article_contents    is 'Contents';
comment on column sys_board.hit_cnt             is 'Hit count';
comment on column sys_board.is_confidential     is 'Is confidential?';
comment on column sys_board.insert_user_id      is 'Insert User UID';
comment on column sys_board.insert_date         is 'Insert Date';
comment on column sys_board.update_user_id      is 'Update User UID';
comment on column sys_board.update_date         is 'Update Date';


/**
 * Table Name  : SYS_BOARD
 * Data        : Bulletin board
 */
