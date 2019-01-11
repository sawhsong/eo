/**
 * Create Dustin
 */
set define off;
insert into sys_users (USER_ID,USER_NAME,DESCRIPTION,PASSWORD,PERSON_ID,CUSTOMER_ID,SUPPLIER,EMAIL,FROM_DATE,TO_DATE,CREATED_BY,CREATION_DATE,LAST_UPDATED_BY,LAST_UPDATE_DATE,SECURITY_GROUP,SECURITY_PATTERN,LAST_LOGIN,LAST_PASSWORD_CHANGE,LOCKED,LOGIN_ATTEMPTS,PROP_TO_PORTAL,PIN,DISABLED_DATE,SECURITY_QUESTION_1,SECURITY_QUESTION_ANSWER_1,SECURITY_QUESTION_2,SECURITY_QUESTION_ANSWER_2,PORTAL_SKIN,PORTAL_SECURITY_ROLE,RESET_PASSWORD,RESET_TERM_CONDITION,IS_PORTAL_USER,PRIMARY_CONTACT,SECONDARY_CONTACT,IS_ACTIVE,PORTAL_ORG_PROFILE_ID)
values (sys_users_s.nextval,'admdustin','dsa@entitysolutions.com.au','admdustin',285493,-1,-1,'dsa@entitysolutions.com.au',to_date('26/07/2013','DD/MM/RRRR'),to_date('31/12/4712','DD/MM/RRRR'),202743,to_date('26/07/2013','DD/MM/RRRR'),0,to_date('07/12/2018','DD/MM/RRRR'),'ALL',null,null,to_date('03/08/2018','DD/MM/RRRR'),'N',0,'C',285261,null,null,null,null,null,'theme000','administrator','Y','Y',null,null,null,null,null);

/**
 * Alter sys_users
 */
alter table sys_users add(startup_url varchar2(100));

update sys_users
   set startup_url = '/index/main/dashboard'
;

/**
 * SYS_USERS.PORTAL_SECURITY_ROLE is coming from SYS_COMMON_LOOKUPS
 * 		Employee Portal user's role = SYS_COMMON_LOOKUPS.ENTITY_SOLUTIONS_STAFF
 */



/**
* Table Name  : 
* Description : Query for TableDefinition Document
**/
select a.table_name,
       a.column_name,
       d.comments,
       a.data_type,
       decode(a.data_type, 'NUMBER', decode(a.data_precision||','||a.data_scale, ',', '', a.data_precision||','||a.data_scale), a.data_length) data_length,
       a.nullable,
       decode(c.constraint_type,'P','PK','R','FK','U','UK') constraint_type,
       '' aaa
  from user_tab_columns a,
       (select a.table_name,
               b.column_name,
               a.constraint_type
          from user_constraints a,
               user_cons_columns b
         where a.constraint_type in ('P', 'R', 'U')
           and a.table_name = b.table_name
           and a.constraint_name = b.constraint_name
       ) c,
       user_col_comments d
 where a.table_name = c.table_name (+)
   and a.column_name = c.column_name (+)
   and a.table_name = d.table_name (+)
   and a.column_name = d.column_name (+)
 order by a.table_name,
       a.nullable,
       a.column_name
;


/**
 * Just for reference
 */
--alter table zebra_board_file add(constraint fk_zebra_board_file foreign key(article_uid) references zebra_board(article_uid));
--create index idx_zebra_board_file on zebra_board_file(file_uid) tablespace alpaca_idx storage(initial 3m next 3m maxextents 2000 pctincrease 0);