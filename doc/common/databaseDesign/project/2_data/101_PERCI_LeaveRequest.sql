/*
 * workday_calendar
 */
delete workday_calendar;

insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20190101', 'yyyymmdd'), 'VIC_03', 'PH', 'New Year''s Day');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20190128', 'yyyymmdd'), 'VIC_03', 'PH', 'Australia Day Holiday');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20190311', 'yyyymmdd'), 'VIC_03', 'PH', 'Labour Day');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20190419', 'yyyymmdd'), 'VIC_03', 'PH', 'Good Friday');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20190420', 'yyyymmdd'), 'VIC_03', 'PH', 'Day following Good Friday');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20190421', 'yyyymmdd'), 'VIC_03', 'PH', 'Easter Sunday');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20190422', 'yyyymmdd'), 'VIC_03', 'PH', 'Easter Monday');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20190425', 'yyyymmdd'), 'VIC_03', 'PH', 'Anzac Day');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20190610', 'yyyymmdd'), 'VIC_03', 'PH', 'Queen''s Birthday');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20190927', 'yyyymmdd'), 'VIC_03', 'PH', 'AFL Grand Final Friday');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20191105', 'yyyymmdd'), 'VIC_03', 'PH', 'Melbourne Cup Day');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20191225', 'yyyymmdd'), 'VIC_03', 'PH', 'Christmas Day');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20191226', 'yyyymmdd'), 'VIC_03', 'PH', 'Boxing Day');

insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20190101', 'yyyymmdd'), 'NSW_02', 'PH', 'New Year''s Day');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20190128', 'yyyymmdd'), 'NSW_02', 'PH', 'Australia Day Holiday');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20190311', 'yyyymmdd'), 'NSW_02', 'PH', 'Labour Day');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20190419', 'yyyymmdd'), 'NSW_02', 'PH', 'Good Friday');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20190420', 'yyyymmdd'), 'NSW_02', 'PH', 'Day following Good Friday');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20190421', 'yyyymmdd'), 'NSW_02', 'PH', 'Easter Sunday');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20190422', 'yyyymmdd'), 'NSW_02', 'PH', 'Easter Monday');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20190425', 'yyyymmdd'), 'NSW_02', 'PH', 'Anzac Day');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20190610', 'yyyymmdd'), 'NSW_02', 'PH', 'Queen''s Birthday');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20190927', 'yyyymmdd'), 'NSW_02', 'PH', 'AFL Grand Final Friday');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20191105', 'yyyymmdd'), 'NSW_02', 'PH', 'Melbourne Cup Day');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20191225', 'yyyymmdd'), 'NSW_02', 'PH', 'Christmas Day');
insert into workday_calendar (calendar_id, calendar_date, state, date_type, description)
values (workday_calendar_s.nextval, to_date('20191226', 'yyyymmdd'), 'NSW_02', 'PH', 'Boxing Day');

/*
 * sys_common_lookups.LEAVE_STATUS
 */
delete sys_common_lookups where lookup_type = 'LEAVE_STATUS';

insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_STATUS', -- lookup_type
        'SA', -- lookup_code
        'In Progress', -- meaning
        'In Progress', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'LEAVE_STATUS'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_STATUS', -- lookup_type
        'SU', -- lookup_code
        'Pending Approval', -- meaning
        'Pending Approval', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'LEAVE_STATUS'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_STATUS', -- lookup_type
        'AP', -- lookup_code
        'Approved', -- meaning
        'Approved', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'LEAVE_STATUS'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_STATUS', -- lookup_type
        'RE', -- lookup_code
        'Rejected', -- meaning
        'Rejected', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'LEAVE_STATUS'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_STATUS', -- lookup_type
        'PD', -- lookup_code
        'Paid', -- meaning
        'Paid', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'LEAVE_STATUS'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_STATUS', -- lookup_type
        'DE', -- lookup_code
        'Deleted', -- meaning
        'Deleted', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'LEAVE_STATUS'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );

/*
 * sys_common_lookups.PORTAL_SECURITY_GROUP
 */
delete sys_common_lookups where lookup_type = 'PORTAL_SECURITY_GROUP' and lookup_code = 'LEAVE_ADM_USER';

insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('PORTAL_SECURITY_GROUP', -- lookup_type
        'LEAVE_ADM_USER', -- lookup_code
        'Entity Solutions Leave Administrator', -- meaning
        'Entity Solutions Leave Administrator', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'PORTAL_SECURITY_GROUP'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );

/*
 * sys_common_lookups.LEAVE_DATE_TYPE
 */
delete sys_common_lookups where lookup_type = 'LEAVE_DATE_TYPE';

insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_DATE_TYPE', -- lookup_type
        'WD', -- lookup_code
        'Working day', -- meaning
        'Normal working day', -- description
        1,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_DATE_TYPE', -- lookup_type
        'WE', -- lookup_code
        'Weekend', -- meaning
        'Weekend', -- description
        2,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_DATE_TYPE', -- lookup_type
        'PH', -- lookup_code
        'Public holiday', -- meaning
        'Public holiday', -- description
        3,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );

/*
 * sys_common_lookups.LEAVE_CATEGORY
 */
delete sys_common_lookups where lookup_type = 'LEAVE_CATEGORY';

insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_CATEGORY', -- lookup_type
        'P', -- lookup_code
        'Paid', -- meaning
        'Paid Leave', -- description
        1,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_CATEGORY', -- lookup_type
        'U', -- lookup_code
        'UnPaid', -- meaning
        'Un Paid Leave', -- description
        2,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );

/*
 * sys_common_lookups.LEAVE_TYPE
 */
delete sys_common_lookups where lookup_type = 'LEAVE_TYPE';

insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_TYPE', -- lookup_type
        'A', -- lookup_code
        'Annual', -- meaning
        'Annual Leave', -- description
        1,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_TYPE', -- lookup_type
        'B', -- lookup_code
        'BDO', -- meaning
        'Birth Day Off', -- description
        2,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_TYPE', -- lookup_type
        'C', -- lookup_code
        'Compassionate', -- meaning
        'Compassionate Leave', -- description
        3,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_TYPE', -- lookup_type
        'F', -- lookup_code
        'Family / Domestic Violence', -- meaning
        'Family / Domestic Violence Leave', -- description
        4,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_TYPE', -- lookup_type
        'J', -- lookup_code
        'Jury Duty', -- meaning
        'Jury Duty Violence Leave', -- description
        5,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_TYPE', -- lookup_type
        'L', -- lookup_code
        'LSL', -- meaning
        'LSL', -- description
        6,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_TYPE', -- lookup_type
        'LW', -- lookup_code
        'LWOP', -- meaning
        'LWOP', -- description
        7,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_TYPE', -- lookup_type
        'PA', -- lookup_code
        'Parental', -- meaning
        'Parental Leave', -- description
        8,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_TYPE', -- lookup_type
        'P', -- lookup_code
        'Personal', -- meaning
        'Personal Leave', -- description
        9,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_TYPE', -- lookup_type
        'R', -- lookup_code
        'RDO', -- meaning
        'RDO', -- description
        10,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_TYPE', -- lookup_type
        'RE', -- lookup_code
        'Replacement Leave(Asis)', -- meaning
        'Replacement Leave(Asis)', -- description
        11,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_TYPE', -- lookup_type
        'S', -- lookup_code
        'Study', -- meaning
        'Study Leave', -- description
        12,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_TYPE', -- lookup_type
        'T', -- lookup_code
        'TOIL', -- meaning
        'TOIL', -- description
        13,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );

/*
 * sys_common_lookups.LEAVE_DURATION
 */
delete sys_common_lookups where lookup_type = 'LEAVE_DURATION';

insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_DURATION', -- lookup_type
        'H', -- lookup_code
        'Hours', -- meaning
        'Hours', -- description
        1,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_DURATION', -- lookup_type
        'D', -- lookup_code
        'Days', -- meaning
        'Days', -- description
        2,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_DURATION', -- lookup_type
        'W', -- lookup_code
        'Weeks', -- meaning
        'Weeks', -- description
        3,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_DURATION', -- lookup_type
        'M', -- lookup_code
        'Months', -- meaning
        'Months', -- description
        4,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('LEAVE_DURATION', -- lookup_type
        'Y', -- lookup_code
        'Years', -- meaning
        'Years', -- description
        5,
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
