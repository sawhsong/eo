/**
 * Table Name  : SYS_COUNTRY_CURRENCY
 * Description : Country and Currency Info - Use Excel file to initialise data (SYS_COUNTRY_CURRENCY.xlsx)
 */
drop table sys_country_currency cascade constraints;
purge recyclebin;

create table sys_country_currency (
    country_currency_id             varchar2(30)                                        not null,   -- Country and Currency UID (PK)
    currency_name                   varchar2(300),                                                  -- Currency Name
    currency_symbol                 varchar2(10),                                                   -- Currency Symbol
    currency_alphabetic_code        varchar2(5),                                                    -- Currency Alphabetic Code
    currency_numeric_code           varchar2(5),                                                    -- Currency Numeric Code
    country_name                    varchar2(300),                                                  -- Country Name
    country_code_2                  varchar2(5),                                                    -- Country Code (2 Digit)
    country_code_3                  varchar2(5),                                                    -- Country Code (3 Digit)
    country_numeric_code            varchar2(5),                                                    -- Country Code Numeric
    country_language_code           varchar2(5),                                                    -- Country Language Code
    insert_user_id                  varchar2(30),                                                   -- Insert User UID
    insert_date                     date                        default sysdate,                    -- Insert Date
    update_user_id                  varchar2(30),                                                   -- Update User UID
    update_date                     date,                                                           -- Update Date

    constraint pk_sys_country_currency primary key(country_currency_id)
    using index tablespace alpaca_idx storage(initial 50k next 50k pctincrease 0)
)
pctfree 20 pctused 80 tablespace alpaca_data storage(initial 100k next 100k maxextents 2000 pctincrease 0);

comment on table  sys_country_currency                          is 'Country and Currency Info';
comment on column sys_country_currency.country_currency_id      is 'Country and Currency UID (PK)';
comment on column sys_country_currency.currency_name            is 'Currency Name';
comment on column sys_country_currency.currency_symbol          is 'Currency Symbol';
comment on column sys_country_currency.currency_alphabetic_code is 'Currency Alphabetic Code';
comment on column sys_country_currency.currency_numeric_code    is 'Currency Numeric Code';
comment on column sys_country_currency.country_name             is 'Country Name';
comment on column sys_country_currency.country_code_2           is 'Country Code (2 Digit)';
comment on column sys_country_currency.country_code_3           is 'Country Code (3 Digit)';
comment on column sys_country_currency.country_numeric_code     is 'Country Code Numeric';
comment on column sys_country_currency.country_language_code    is 'Country Language Code';
comment on column sys_country_currency.insert_user_id           is 'Insert User UID';
comment on column sys_country_currency.insert_date              is 'Insert Date';
comment on column sys_country_currency.update_user_id           is 'Update User UID';
comment on column sys_country_currency.update_date              is 'Update Date';


/**
 * Table Name  : SYS_COUNTRY_CURRENCY
 * Data        : Country and Currency Info - Use Excel file to initialise data (SYS_COUNTRY_CURRENCY.xlsx)
 */
update sys_country_currency
   set country_name = initcap(country_name)
;