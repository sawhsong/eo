/**
 * Table Name  : 공통코드
 * Description : Framework에서 사용하는 공통코드(프로젝트에서는 별도 테이블 만들것)
 */
drop table zebra_common_code cascade constraints;
purge recyclebin;

create table zebra_common_code (
    code_type                       varchar2(30)                                        not null,   -- 상위구분코드
    common_code                     varchar2(30)                                        not null,   -- 코드
    description_ko                  varchar2(1000),                                                 -- 코드설명(Korean)
    description_en                  varchar2(1000),                                                 -- 코드설명(English)
    program_constants               varchar2(100)                                       not null,   -- Constants value for the common code to be used in program
    sort_order                      varchar2(3),                                                    -- 정렬순서
    use_yn                          varchar2(1)                 default 'Y',                        -- 사용여부
    default_yn                      varchar2(1)                 default 'N',                        -- 기본데이터여부(기본데이터는 변경불가)
    insert_user_id                  varchar2(30),                                                   -- Insert User UID
    insert_date                     date                        default sysdate,                    -- Insert Date
    update_user_id                  varchar2(30),                                                   -- Update User UID
    update_date                     date,                                                           -- Update Date

    constraint pk_zebra_common_code primary key(code_type, common_code),
    constraint uk_zebra_common_code unique(program_constants)
    using index tablespace alpaca_idx storage(initial 50k next 50k pctincrease 0)
)
pctfree 20 pctused 80 tablespace alpaca_data storage(initial 100k next 100k maxextents 2000 pctincrease 0);

comment on table zebra_common_code is '공통코드';

comment on column zebra_common_code.code_type         is '상위구분코드';
comment on column zebra_common_code.common_code       is '코드';
comment on column zebra_common_code.description_ko    is '코드설명(Korean)';
comment on column zebra_common_code.description_en    is '코드설명(English)';
comment on column zebra_common_code.program_constants is 'Constants value for the common code to be used in program';
comment on column zebra_common_code.sort_order        is '정렬순서';
comment on column zebra_common_code.use_yn            is '사용여부';
comment on column zebra_common_code.default_yn        is '기본데이터여부(기본데이터는 변경불가)';
comment on column zebra_common_code.insert_user_id    is '입력자 uid';
comment on column zebra_common_code.insert_date       is '입력일자';
comment on column zebra_common_code.update_user_id    is '수정자 uid';
comment on column zebra_common_code.update_date       is '수정일자';


/**
 * Table Name  : 공통코드
 * Data        :
 */
delete zebra_common_code;

insert into zebra_common_code values('USE_YN','0000000000',     '사용여부',  'Use or Not',    'USE_YN_0000000000',  '000',   'Y','Y','0',sysdate,'','');
insert into zebra_common_code values('USE_YN','Y',              '사용',      'Use',           'USE_YN_Y',           '001',   'Y','Y','0',sysdate,'','');
insert into zebra_common_code values('USE_YN','N',              '미사용',    'Not Use',       'USE_YN_N',           '002',   'Y','Y','0',sysdate,'','');

insert into zebra_common_code values('BOARD_TYPE','0000000000', '게시판구분',  'Board Type',     'BOARD_TYPE_0000000000',  '000','Y','Y','0',sysdate,'','');
insert into zebra_common_code values('BOARD_TYPE','FREE',       '자유게시판',  'Free Board',     'BOARD_TYPE_FREE',        '001','Y','Y','0',sysdate,'','');
insert into zebra_common_code values('BOARD_TYPE','NOTICE',     '공지사항',    'Notice Board',   'BOARD_TYPE_NOTICE',      '002','Y','Y','0',sysdate,'','');
insert into zebra_common_code values('BOARD_TYPE','REPOSITORY', '자료실',      'Data Repository','BOARD_TYPE_REPOSITORY',  '003','Y','Y','0',sysdate,'','');

insert into zebra_common_code values('BOARD_SEARCH_TYPE', '0000000000','게시판검색형태', 'Board Search Criteria', 'BOARD_SEARCH_TYPE_0000000000',  '000','Y','Y','0',sysdate,'','');
insert into zebra_common_code values('BOARD_SEARCH_TYPE', 'SUBJECT',   '제목',            'Subject',               'BOARD_SEARCH_TYPE_SUBJECT',     '001','Y','Y','0',sysdate,'','');
insert into zebra_common_code values('BOARD_SEARCH_TYPE', 'CONTENTS',  '내용',            'Contents',              'BOARD_SEARCH_TYPE_CONTENTS',    '002','Y','Y','0',sysdate,'','');
insert into zebra_common_code values('BOARD_SEARCH_TYPE', 'WRITER',    '작성자',          'Writer',                'BOARD_SEARCH_TYPE_WRITER',      '003','Y','Y','0',sysdate,'','');

insert into zebra_common_code values('USER_THEME_TYPE','0000000000',   '사용자스킨구분', 'User Theme Type','USER_THEME_TYPE_0000000000',  '000','Y','Y','0',sysdate,'','');
insert into zebra_common_code values('USER_THEME_TYPE','THEME000',     'Bootstrap',       'Bootstrap',      'USER_THEME_TYPE_000',         '001','Y','Y','0',sysdate,'','');
insert into zebra_common_code values('USER_THEME_TYPE','THEME001',     'Smoothness',      'Smoothness',     'USER_THEME_TYPE_001',         '002','Y','Y','0',sysdate,'','');
insert into zebra_common_code values('USER_THEME_TYPE','THEME002',     'Redmond',         'Redmond',        'USER_THEME_TYPE_002',         '003','Y','Y','0',sysdate,'','');
insert into zebra_common_code values('USER_THEME_TYPE','THEME003',     'Lightness',       'Lightness',      'USER_THEME_TYPE_003',         '004','Y','Y','0',sysdate,'','');
insert into zebra_common_code values('USER_THEME_TYPE','THEME004',     'Start',           'Start',          'USER_THEME_TYPE_004',         '005','Y','Y','0',sysdate,'','');
insert into zebra_common_code values('USER_THEME_TYPE','THEME005',     'Sunny',           'Sunny',          'USER_THEME_TYPE_005',         '006','Y','Y','0',sysdate,'','');
insert into zebra_common_code values('USER_THEME_TYPE','THEME006',     'Flick',           'Flick',          'USER_THEME_TYPE_006',         '007','Y','Y','0',sysdate,'','');
insert into zebra_common_code values('USER_THEME_TYPE','THEME007',     'Pepper Grinder',  'Pepper Grinder', 'USER_THEME_TYPE_007',         '008','Y','Y','0',sysdate,'','');
insert into zebra_common_code values('USER_THEME_TYPE','THEME008',     'Cupertino',       'Cupertino',      'USER_THEME_TYPE_008',         '009','Y','Y','0',sysdate,'','');
insert into zebra_common_code values('USER_THEME_TYPE','THEME009',     'South Street',    'South Street',   'USER_THEME_TYPE_009',         '010','Y','Y','0',sysdate,'','');
insert into zebra_common_code values('USER_THEME_TYPE','THEME010',     'Humanity',        'Humanity',       'USER_THEME_TYPE_010',         '011','Y','Y','0',sysdate,'','');

insert into zebra_common_code values('LANGUAGE_TYPE','0000000000', '언어타입',  'Language', 'LANGUAGE_TYPE_0000000000',  '000',  'Y','Y','0',sysdate,'','');
insert into zebra_common_code values('LANGUAGE_TYPE','EN',         '영어',      'English',  'LANGUAGE_TYPE_EN',          '001',   'Y','Y','0',sysdate,'','');
insert into zebra_common_code values('LANGUAGE_TYPE','KO',         '한국어',    'Korean',   'LANGUAGE_TYPE_KO',          '002',   'Y','Y','0',sysdate,'','');

insert into zebra_common_code values('DOMAIN_DATA_TYPE', '0000000000', 'Domain Data Type',  'Domain Data Type', 'DOMAIN_DATA_TYPE_0000000000',  '000',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_TYPE', 'VARCHAR2',   'Varchar2',          'Varchar2',         'DOMAIN_DATA_TYPE_VARCHAR2',    '001',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_TYPE', 'NUMBER',     'Number',            'Number',           'DOMAIN_DATA_TYPE_NUMBER',      '002',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_TYPE', 'DATE',       'Date',              'Date',             'DOMAIN_DATA_TYPE_DATE',        '003',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_TYPE', 'CLOB',       'Clob',              'Clob',             'DOMAIN_DATA_TYPE_CLOB',        '004',  'Y', 'Y', '0', sysdate, '', '');

insert into zebra_common_code values('DOMAIN_DATA_LENGTH', '0000000000', 'Domain Data Length',  'Domain Data Length', 'DOMAIN_DATA_LENGTH_0000000000',  '000',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_LENGTH', '1',          '1',                   '1',                  'DOMAIN_DATA_LENGTH_1',           '001',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_LENGTH', '3',          '3',                   '3',                  'DOMAIN_DATA_LENGTH_3',           '002',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_LENGTH', '5',          '5',                   '5',                  'DOMAIN_DATA_LENGTH_5',           '003',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_LENGTH', '10',         '10',                  '10',                 'DOMAIN_DATA_LENGTH_10',          '004',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_LENGTH', '15',         '15',                  '15',                 'DOMAIN_DATA_LENGTH_15',          '005',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_LENGTH', '20',         '20',                  '20',                 'DOMAIN_DATA_LENGTH_20',          '006',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_LENGTH', '30',         '30',                  '30',                 'DOMAIN_DATA_LENGTH_30',          '007',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_LENGTH', '50',         '50',                  '50',                 'DOMAIN_DATA_LENGTH_50',          '008',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_LENGTH', '100',        '100',                 '100',                'DOMAIN_DATA_LENGTH_100',         '009',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_LENGTH', '300',        '300',                 '300',                'DOMAIN_DATA_LENGTH_300',         '010',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_LENGTH', '500',        '500',                 '500',                'DOMAIN_DATA_LENGTH_500',         '011',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_LENGTH', '1000',       '1000',                '1000',               'DOMAIN_DATA_LENGTH_1000',        '012',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_LENGTH', '2000',       '2000',                '2000',               'DOMAIN_DATA_LENGTH_2000',        '013',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_LENGTH', '4000',       '4000',                '4000',               'DOMAIN_DATA_LENGTH_4000',        '014',  'Y', 'Y', '0', sysdate, '', '');

insert into zebra_common_code values('DOMAIN_DATA_PRECISION', '0000000000', 'Domain Data Precision',  'Domain Data Precision', 'DOMAIN_DATA_PRECISION_0000000000',  '000',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_PRECISION', '3',          '3',                      '3',                     'DOMAIN_DATA_PRECISION_3',           '001',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_PRECISION', '5',          '5',                      '5',                     'DOMAIN_DATA_PRECISION_5',           '002',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_PRECISION', '9',          '9',                      '9',                     'DOMAIN_DATA_PRECISION_9',           '003',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_PRECISION', '12',         '12',                     '12',                    'DOMAIN_DATA_PRECISION_12',          '004',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_PRECISION', '15',         '15',                     '15',                    'DOMAIN_DATA_PRECISION_15',          '005',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_PRECISION', '24',         '24',                     '24',                    'DOMAIN_DATA_PRECISION_24',          '006',  'Y', 'Y', '0', sysdate, '', '');

insert into zebra_common_code values('DOMAIN_DATA_SCALE', '0000000000', 'Domain Data Scale',  'Domain Data Scale', 'DOMAIN_DATA_SCALE_0000000000',  '000',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_SCALE', '1',          '1',                  '1',                 'DOMAIN_DATA_SCALE_1',           '001',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_SCALE', '2',          '2',                  '2',                 'DOMAIN_DATA_SCALE_2',           '002',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_SCALE', '3',          '3',                  '3',                 'DOMAIN_DATA_SCALE_3',           '003',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_SCALE', '4',          '4',                  '4',                 'DOMAIN_DATA_SCALE_4',           '004',  'Y', 'Y', '0', sysdate, '', '');
insert into zebra_common_code values('DOMAIN_DATA_SCALE', '5',          '5',                  '5',                 'DOMAIN_DATA_SCALE_5',           '005',  'Y', 'Y', '0', sysdate, '', '');

insert into zebra_common_code values('SIMPLE_YN','0000000000',     '단순YN',  'Simple YN',    'SIMPLE_YN_0000000000',  '000',   'Y','Y','0',sysdate,'','');
insert into zebra_common_code values('SIMPLE_YN','Y',              '예',      'Yes',          'SIMPLE_YN_Y',           '001',   'Y','Y','0',sysdate,'','');
insert into zebra_common_code values('SIMPLE_YN','N',              '아니오',  'No',           'SIMPLE_YN_N',           '002',   'Y','Y','0',sysdate,'','');

insert into zebra_common_code values('CONSTRAINT_TYPE', '0000000000', 'Constraint Type', 'Constraint Type',    'CONSTRAINT_TYPE_0000000000',  '000',   'Y','Y','0',sysdate,'','');
insert into zebra_common_code values('CONSTRAINT_TYPE', 'PK',         'Primary Key',     'Primary Key',        'CONSTRAINT_TYPE_PK',          '001',   'Y','Y','0',sysdate,'','');
insert into zebra_common_code values('CONSTRAINT_TYPE', 'FK',         'Foreign Key',     'Foreign Key',        'CONSTRAINT_TYPE_FK',          '002',   'Y','Y','0',sysdate,'','');
insert into zebra_common_code values('CONSTRAINT_TYPE', 'UK',         'Unique Key',      'Unique Key',         'CONSTRAINT_TYPE_UK',          '002',   'Y','Y','0',sysdate,'','');
