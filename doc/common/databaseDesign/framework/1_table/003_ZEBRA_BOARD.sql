/**
 * Table Name  : ZEBRA_BOARD
 * Description : Bulletin Board
 */
drop table zebra_board cascade constraints;
purge recyclebin;

create table zebra_board (
    article_id                      varchar2(30)                                        not null,   -- Article UID (PK)
    board_type                      varchar2(30)                                        not null,   -- BBS Type([sys_common_code.board_type - bbs / notice])
    writer_id                       varchar2(30),                                                   -- Writer UID ([sys_user.user_id] Anonymous user is allowed to write an article with only name)
    writer_name                     varchar2(50)                                        not null,   -- Writer Name (Anonymous user is allowed to write an article with only name)
    article_password                varchar2(12),                                                   -- Article Password (if wants)
    writer_email                    varchar2(100),                                                  -- Writer e-mail
    writer_ip_address               varchar2(50),                                                   -- Writer IP Adress
    article_subject                 varchar2(1000),                                                 -- Subject
    article_contents                clob                        default empty_clob(),               -- Contents
    visit_cnt                       number(5)                   default 0               not null,   -- Number of read
    ref_article_id                  varchar2(30)                                        not null,   -- Referred article UID (Level 1 => 0)
    insert_user_id                  varchar2(30),                                                   -- Insert User UID
    insert_date                     date                        default sysdate,                    -- Insert Date
    update_user_id                  varchar2(30),                                                   -- Update User UID
    update_date                     date,                                                           -- Update Date

    constraint pk_zebra_board primary key(article_id)
    using index tablespace alpaca_idx storage(initial 50k next 50k pctincrease 0)
)
pctfree 20 pctused 80 tablespace alpaca_data storage(initial 100k next 100k maxextents 2000 pctincrease 0);

comment on table  zebra_board                     is '게시판';
comment on column zebra_board.article_id          is 'Article UID (PK)';
comment on column zebra_board.board_type          is '게시판 종류([sys_common_code.board_type - bbs / notice])';
comment on column zebra_board.writer_id           is '작성자 uid(등록된 사용자가 아니라도 이름만 입력하면 등록 가능)';
comment on column zebra_board.writer_name         is '작성자성명';
comment on column zebra_board.article_password    is '게시물비밀번호';
comment on column zebra_board.writer_email        is '작성자e-mail';
comment on column zebra_board.writer_ip_address   is '작성자ip adress';
comment on column zebra_board.article_subject     is '게시물제목';
comment on column zebra_board.article_contents    is '게시물내용';
comment on column zebra_board.visit_cnt           is '방문횟수';
comment on column zebra_board.ref_article_id      is '참조게시물 unique id(1레벨자료는 0)';
comment on column zebra_board.insert_user_id      is '입력자 uid';
comment on column zebra_board.insert_date         is '입력일자';
comment on column zebra_board.update_user_id      is '수정자 uid';
comment on column zebra_board.update_date         is '수정일자';


/**
 * Table Name  : 게시판
 * Data        :
 */