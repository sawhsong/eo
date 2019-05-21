/*
 * expense_cliam
 */
drop table expense_claim cascade constraints;
create table expense_claim (
    expense_claim_id number not null,
    person_id number,
    department varchar2(50),
    expense_type varchar2(50),
    date_of_claim date,
    bsb varchar2(10),
    account_name varchar2(50),
    account_number varchar2(30),
    amount number,
    gst number,
    description varchar2(1000),
    submitted_date date,
    processed_date date,
    status varchar2(60),
    approve_reject_person_id number,
    approve_reject_date date,
    approve_reject_comments varchar2(1000),
    approve_reject_ip_address varchar2(30),

    created_by number,
    creation_date date,
    last_updated_by number,
    last_update_date date,

    constraint pk_expense_claim primary key(expense_claim_id)
);
drop sequence expense_claim_s;
create sequence expense_claim_s minvalue 1 maxvalue 999999999999999999999999999 increment by 1 start with 1 nocache noorder nocycle;

/*
 * expense_attachment_link
 */
drop table expense_attachment_link cascade constraints;
create table expense_attachment_link (
    link_id number not null,
    expense_claim_id number,
    document_id number, -- hr_document.document_id
    file_icon varchar2(300),
    file_size number,

    constraint pk_expense_attachment_link primary key(link_id)
);
drop sequence expense_attachment_link_s;
create sequence expense_attachment_link_s minvalue 1 maxvalue 999999999999999999999999999 increment by 1 start with 1 nocache noorder nocycle;