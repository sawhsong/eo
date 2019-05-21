/*
 * leave_request
 */
drop table leave_request cascade constraints;
create table leave_request (
    leave_request_id number,
    assignment_id number,
    leave_type varchar2(20),
    leave_category varchar2(20),
    start_date date,
    end_date date,
    duration number,
    duration_unit varchar2(10),
    reason varchar2(1000),
    status varchar(10),
    approve_reject_person_id number,
    approve_reject_date date,
    approve_reject_comments varchar2(1000),
    approve_reject_ip_address varchar2(30),

    created_by number,
    creation_date date,
    last_updated_by number,
    last_update_date date,

    constraint pk_leave_request_id primary key(leave_request_id)
);
drop sequence leave_request_s;
create sequence leave_request_s minvalue 1 maxvalue 999999999999999999999999999 increment by 1 start with 1 nocache noorder nocycle;


/*
 * leave_request_detail
 */
drop table leave_request_detail cascade constraints;
create table leave_request_detail (
    detail_id number not null,
    leave_request_id number,
    leave_date date,
    date_type varchar2(30), -- WD(Normal working day), WE(Weekend), PH(Public holiday)
    hours number,
    description varchar2(1000),

    constraint pk_leave_request_detail primary key(detail_id)
);
drop sequence leave_request_detail_s;
create sequence leave_request_detail_s minvalue 1 maxvalue 999999999999999999999999999 increment by 1 start with 1 nocache noorder nocycle;


/*
 * workday_calendar
 */
drop table workday_calendar cascade constraints;
create table workday_calendar (
    calendar_id number not null,
    calendar_date date,
    state varchar2(30),
    date_type varchar2(30), -- WD(Normal working day), WE(Weekend), PH(Public holiday)
    description varchar2(1000),

    constraint pk_workday_calendar primary key(calendar_id)
);
drop sequence workday_calendar_s;
create sequence workday_calendar_s minvalue 1 maxvalue 999999999999999999999999999 increment by 1 start with 1 nocache noorder nocycle;