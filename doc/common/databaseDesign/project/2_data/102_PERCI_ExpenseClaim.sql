/*
 * sys_common_lookups.EXPENSE_STATUS
 */
delete sys_common_lookups where lookup_type = 'EXPENSE_STATUS';

insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_STATUS', -- lookup_type
        'SA', -- lookup_code
        'In Progress', -- meaning
        'In Progress', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_STATUS'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_STATUS', -- lookup_type
        'SU', -- lookup_code
        'Pending Approval', -- meaning
        'Pending Approval', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_STATUS'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_STATUS', -- lookup_type
        'AP', -- lookup_code
        'Approved', -- meaning
        'Approved', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_STATUS'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_STATUS', -- lookup_type
        'RE', -- lookup_code
        'Rejected', -- meaning
        'Rejected', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_STATUS'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );

/*
 * sys_common_lookups.DOCUMENT_SUB_TYPE
 */
delete sys_common_lookups where lookup_type = 'DOCUMENT_SUB_TYPE' and lookup_code = 'IPRO_DOCUMENTS_ECA';

insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('DOCUMENT_SUB_TYPE', -- lookup_type
        'IPRO_DOCUMENTS_ECA', -- lookup_code
        'IPro Documents - Expense Claim Attachment', -- meaning
        'IPro Documents - Expense Claim Attachment', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'INTERNAL_DEPARTMENT'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );

/*
 * sys_common_lookups.INTERNAL_DEPARTMENT
 */
delete sys_common_lookups where lookup_type = 'INTERNAL_DEPARTMENT';

insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('INTERNAL_DEPARTMENT', -- lookup_type
        'STLE', -- lookup_code
        'Strategic Leadership Team', -- meaning
        'Strategic Leadership Team', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'INTERNAL_DEPARTMENT'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('INTERNAL_DEPARTMENT', -- lookup_type
        'DRTR', -- lookup_code
        'Directors', -- meaning
        'Directors', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'INTERNAL_DEPARTMENT'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('INTERNAL_DEPARTMENT', -- lookup_type
        'PECU', -- lookup_code
        'People and Culture', -- meaning
        'People and Culture', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'INTERNAL_DEPARTMENT'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('INTERNAL_DEPARTMENT', -- lookup_type
        'FRON', -- lookup_code
        'Front Office', -- meaning
        'Front Office', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'INTERNAL_DEPARTMENT'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('INTERNAL_DEPARTMENT', -- lookup_type
        'LECO', -- lookup_code
        'Legal and Compliance', -- meaning
        'Legal and Compliance', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'INTERNAL_DEPARTMENT'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('INTERNAL_DEPARTMENT', -- lookup_type
        'MACO', -- lookup_code
        'Marketing and Communications', -- meaning
        'Marketing and Communications', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'INTERNAL_DEPARTMENT'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('INTERNAL_DEPARTMENT', -- lookup_type
        'CUDE', -- lookup_code
        'Customer Development', -- meaning
        'Customer Development', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'INTERNAL_DEPARTMENT'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('INTERNAL_DEPARTMENT', -- lookup_type
        'DBIT', -- lookup_code
        'Digital, Business and IT Systems', -- meaning
        'Digital, Business and IT Systems', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'INTERNAL_DEPARTMENT'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('INTERNAL_DEPARTMENT', -- lookup_type
        'SRDC', -- lookup_code
        'Service Delivery Centre', -- meaning
        'Service Delivery Centre', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'INTERNAL_DEPARTMENT'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('INTERNAL_DEPARTMENT', -- lookup_type
        'GSDC', -- lookup_code
        'Global Service Delivery Centre', -- meaning
        'Global Service Delivery Centre', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'INTERNAL_DEPARTMENT'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('INTERNAL_DEPARTMENT', -- lookup_type
        'ASSA', -- lookup_code
        'Asia Sales', -- meaning
        'Asia Sales', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'INTERNAL_DEPARTMENT'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('INTERNAL_DEPARTMENT', -- lookup_type
        'MIGR', -- lookup_code
        'Migration', -- meaning
        'Migration', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'INTERNAL_DEPARTMENT'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('INTERNAL_DEPARTMENT', -- lookup_type
        'FINA', -- lookup_code
        'Finance', -- meaning
        'Finance', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'INTERNAL_DEPARTMENT'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('INTERNAL_DEPARTMENT', -- lookup_type
        'CSTL', -- lookup_code
        'Consultel', -- meaning
        'Consultel', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'INTERNAL_DEPARTMENT'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );

/*
 * sys_common_lookups.EXPENSE_TYPE
 */
delete sys_common_lookups where lookup_type = 'EXPENSE_TYPE';

insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'ENTRDEDC', -- lookup_code
        'Entertainment – Deductible', -- meaning
        'Entertainment – Deductible', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'CLNTREFR', -- lookup_code
        'Client Light Refreshments', -- meaning
        'Client Light Refreshments', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'CLNTENTS', -- lookup_code
        'Client Entertainment – Staff', -- meaning
        'Client Entertainment – Staff', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'CLNTENTC', -- lookup_code
        'Client Entertainment – Clients', -- meaning
        'Client Entertainment – Clients', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'CLNTEVNT', -- lookup_code
        'Client Events', -- meaning
        'Client Events', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'LGHTREFR', -- lookup_code
        'Light Refreshments', -- meaning
        'Light Refreshments', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'ENTRSTAF', -- lookup_code
        'Entertainment – Staff', -- meaning
        'Entertainment – Staff', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'ENTRCLNT', -- lookup_code
        'Entertainment – Clients', -- meaning
        'Entertainment – Clients', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'CORPEVNT', -- lookup_code
        'Corporate Events', -- meaning
        'Corporate Events', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'ASSET', -- lookup_code
        'Asset', -- meaning
        'Asset', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'ACCNTFEE', -- lookup_code
        'Account Fees', -- meaning
        'Account Fees', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'ADVRTIS', -- lookup_code
        'Advertising', -- meaning
        'Advertising', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'BANKFEE', -- lookup_code
        'Bank Fees', -- meaning
        'Bank Fees', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'CLEAN', -- lookup_code
        'Cleaning', -- meaning
        'Cleaning', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'CORPPRMR', -- lookup_code
        'Corporate Printing / Merchandise', -- meaning
        'Corporate Printing / Merchandise', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'CORPSUBS', -- lookup_code
        'Corporate Subscriptions', -- meaning
        'Corporate Subscriptions', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'CLNTGFVO', -- lookup_code
        'Client gifts / vouchers', -- meaning
        'Client gifts / vouchers', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'COLCRCO', -- lookup_code
        'Collection / Credit Costs', -- meaning
        'Collection / Credit Costs', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'DONAT', -- lookup_code
        'Donations', -- meaning
        'Donations', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'EMASOFT', -- lookup_code
        'EMA Software', -- meaning
        'EMA Software', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'INTHOMGT', -- lookup_code
        'Internet Hosting / Management', -- meaning
        'Internet Hosting / Management', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'OFFSTTN', -- lookup_code
        'Office Stationery', -- meaning
        'Office Stationery', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'OFFSUBS', -- lookup_code
        'Office Subscriptions', -- meaning
        'Office Subscriptions', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'OFFSUPP', -- lookup_code
        'Office Supplies', -- meaning
        'Office Supplies', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'ONLTM', -- lookup_code
        'Online Timesheets', -- meaning
        'Online Timesheets', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'OTSTBE', -- lookup_code
        'Other Staff Benefits', -- meaning
        'Other Staff Benefits', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'PROCURE', -- lookup_code
        'Procurement', -- meaning
        'Procurement', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'PROMEM', -- lookup_code
        'Professional Membership', -- meaning
        'Professional Membership', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'POSTFRGT', -- lookup_code
        'Postage / Freight', -- meaning
        'Postage / Freight', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'REPMAINT', -- lookup_code
        'Repairs / Maintenance', -- meaning
        'Repairs / Maintenance', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'STFAMEN', -- lookup_code
        'Staff Amenities', -- meaning
        'Staff Amenities', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'SOFTLCNC', -- lookup_code
        'Software / Licence Expense', -- meaning
        'Software / Licence Expense', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'STFFFUNC', -- lookup_code
        'Staff Function', -- meaning
        'Staff Function', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'STFFTRNG', -- lookup_code
        'Staff Training', -- meaning
        'Staff Training', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'STFFPRKG', -- lookup_code
        'Staff Car Parking Fees', -- meaning
        'Staff Car Parking Fees', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'STFFVOBO', -- lookup_code
        'Staff Voucher / Bonuses', -- meaning
        'Staff Voucher / Bonuses', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'TCHEXPN', -- lookup_code
        'Technology Expenses', -- meaning
        'Technology Expenses', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'TRVACCOM', -- lookup_code
        'Travel – Accommodation', -- meaning
        'Travel – Accommodation', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'TRVARFAR', -- lookup_code
        'Travel – Airfare', -- meaning
        'Travel – Airfare', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'TRVLTRN', -- lookup_code
        'Travel – Local Taxis, Rental', -- meaning
        'Travel – Local Taxis, Rental', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'TRVMEAL', -- lookup_code
        'Travel – Meals', -- meaning
        'Travel – Meals', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'TRVOTHR', -- lookup_code
        'Travel – Other', -- meaning
        'Travel – Other', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'TIPGRA', -- lookup_code
        'Tips and Gratuities', -- meaning
        'Tips and Gratuities', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
insert into sys_common_lookups (lookup_type, lookup_code, meaning, description, display_order, lookup_id, enabled_flag, from_date_active, to_date_active, attribute1, attribute2, attribute3, attribute4, attribute5, created_by, creation_date, last_update_date, last_updated_by)
values ('EXPENSE_TYPE', -- lookup_type
        'OTHERS', -- lookup_code
        'Others', -- meaning
        'Others', -- description
        (select nvl(max(display_order), 0)+1 from sys_common_lookups where lookup_type = 'EXPENSE_TYPE'),
        sys_common_lookups_s.nextval, 'Y', to_date(sysdate,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'), null, null, null, null, null, 1, to_date(sysdate,'dd/mm/yyyy'), to_date(sysdate,'dd/mm/yyyy'), 1
       );
