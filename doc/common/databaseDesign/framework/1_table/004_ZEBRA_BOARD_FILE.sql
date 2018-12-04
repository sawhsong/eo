/**
 * Table Name  : ZEBRA_BOARD_FILE
 * Description : Bulletin Board File
 */
drop table zebra_board_file cascade constraints;
purge recyclebin;

create table zebra_board_file (
    file_id                         varchar2(30)                                        not null,   -- 파일 unique id
    article_id                      varchar2(30)                                        not null,   -- 게시물 unique id
    original_name                   varchar2(1000)                                      not null,   -- 실제 파일명
    new_name                        varchar2(1000)                                      not null,   -- 시스템 설정 파일명
    file_type                       varchar2(300),                                                  -- 파일 type
    file_icon                       varchar2(1000),                                                 -- File Icon path and name
    file_size                       number(12,2),                                                   -- 파일 크기(kb)
    repository_path                 varchar2(2000)                                      not null,   -- 파일경로
    insert_user_id                  varchar2(30),                                                   -- Insert User UID
    insert_date                     date                        default sysdate,                    -- Insert Date
    update_user_id                  varchar2(30),                                                   -- Update User UID
    update_date                     date,                                                           -- Update Date

    constraint fk_zebra_board_file foreign key(article_id) references zebra_board(article_id),
    constraint pk_zebra_board_file primary key(file_id)
    using index tablespace alpaca_idx storage(initial 50k next 50k pctincrease 0)
)
pctfree 20 pctused 80 tablespace alpaca_data storage(initial 100k next 100k maxextents 2000 pctincrease 0);

comment on table  zebra_board_file                    is '게시판 첨부파일';
comment on column zebra_board_file.file_id            is '파일 unique id';
comment on column zebra_board_file.article_id         is '게시물 unique id';
comment on column zebra_board_file.original_name      is '실제 파일명';
comment on column zebra_board_file.new_name           is '시스템 설정 파일명';
comment on column zebra_board_file.file_type          is '파일 type';
comment on column zebra_board_file.file_icon          is 'File Icon path and name';
comment on column zebra_board_file.file_size          is '파일 크기(kb)';
comment on column zebra_board_file.repository_path    is '파일경로';
comment on column zebra_board_file.insert_user_id     is '입력자 uid';
comment on column zebra_board_file.insert_date        is '입력일자';
comment on column zebra_board_file.update_user_id     is '수정자 uid';
comment on column zebra_board_file.update_date        is '수정일자';

--alter table zebra_board_file add(constraint fk_zebra_board_file foreign key(article_uid) references zebra_board(article_uid));
--create index idx_zebra_board_file on zebra_board_file(file_uid) tablespace alpaca_idx storage(initial 3m next 3m maxextents 2000 pctincrease 0);


/**
 * Table Name  : 게시판 첨부파일 정보
 * Data        :
 */