/**************************************************************************************************
 * Framework Generated DTO Source
 * - HP_PERSON_D - 
 *************************************************************************************************/
package com.es.portal.conf.resource.ormapper.dto.oracle;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

import zebra.data.DataSet;
import zebra.util.CommonUtil;
import com.es.portal.common.extend.BaseDto;

@SuppressWarnings("unused")
public class HpPersonD extends BaseDto implements Serializable {
	/**
	 * Columns
	 */
	private double personId;
	private String PERSON_ID;
	private String personNumber;
	private String PERSON_NUMBER;
	private double businessGroupId;
	private String BUSINESS_GROUP_ID;
	private double createdBy;
	private String CREATED_BY;
	private Date creationDate;
	private String CREATION_DATE;
	private Date fromDate;
	private String FROM_DATE;
	private double lastUpdatedBy;
	private String LAST_UPDATED_BY;
	private Date lastUpdateDate;
	private String LAST_UPDATE_DATE;
	private Date toDate;
	private String TO_DATE;
	private double employmentCompanyOrgId;
	private String EMPLOYMENT_COMPANY_ORG_ID;
	private String attribute3;
	private String ATTRIBUTE3;
	private String attribute4;
	private String ATTRIBUTE4;
	private String attribute5;
	private String ATTRIBUTE5;
	private String attribute6;
	private String ATTRIBUTE6;
	private String attribute7;
	private String ATTRIBUTE7;
	private String attribute8;
	private String ATTRIBUTE8;
	private String contractDaysWithEcms;
	private String CONTRACT_DAYS_WITH_ECMS;
	private double customerSupport;
	private String CUSTOMER_SUPPORT;
	private Date dateOfBirth;
	private String DATE_OF_BIRTH;
	private double ecmsAccountMgrId;
	private String ECMS_ACCOUNT_MGR_ID;
	private double ecmsCsConsultantId;
	private String ECMS_CS_CONSULTANT_ID;
	private String ecmsPayrollConsultantId;
	private String ECMS_PAYROLL_CONSULTANT_ID;
	private String employmentType;
	private String EMPLOYMENT_TYPE;
	private Date firstAssignmentDate;
	private String FIRST_ASSIGNMENT_DATE;
	private Date firstContact;
	private String FIRST_CONTACT;
	private String firstName;
	private String FIRST_NAME;
	private String fullName;
	private String FULL_NAME;
	private String function;
	private String FUNCTION;
	private String gender;
	private String GENDER;
	private String gtmProfile;
	private String GTM_PROFILE;
	private String gtmStage;
	private String GTM_STAGE;
	private double lafhaAmount;
	private String LAFHA_AMOUNT;
	private String lafhaEligible;
	private String LAFHA_ELIGIBLE;
	private Date lafhaLeaseExpiry;
	private String LAFHA_LEASE_EXPIRY;
	private String lifeCycleStage;
	private String LIFE_CYCLE_STAGE;
	private String maritalStatus;
	private String MARITAL_STATUS;
	private Date memberSince;
	private String MEMBER_SINCE;
	private String middleName;
	private String MIDDLE_NAME;
	private String payrollNotes;
	private String PAYROLL_NOTES;
	private String payslipEmail;
	private String PAYSLIP_EMAIL;
	private double personalCommentId;
	private String PERSONAL_COMMENT_ID;
	private String personType;
	private String PERSON_TYPE;
	private String preferred;
	private String PREFERRED;
	private String preferredName;
	private String PREFERRED_NAME;
	private String prefix;
	private String PREFIX;
	private String previousEmploymentModel;
	private String PREVIOUS_EMPLOYMENT_MODEL;
	private double primaryPersonTypeId;
	private String PRIMARY_PERSON_TYPE_ID;
	private String referenceNo;
	private String REFERENCE_NO;
	private String referenceNo2;
	private String REFERENCE_NO_2;
	private String referenceNo3;
	private String REFERENCE_NO_3;
	private double referralId;
	private String REFERRAL_ID;
	private double referralOrganisationId;
	private String REFERRAL_ORGANISATION_ID;
	private double resignationCommentId;
	private String RESIGNATION_COMMENT_ID;
	private Date resignationDate;
	private String RESIGNATION_DATE;
	private String resignationReason;
	private String RESIGNATION_REASON;
	private String serviceStatus;
	private String SERVICE_STATUS;
	private String serviceType;
	private String SERVICE_TYPE;
	private String skills;
	private String SKILLS;
	private String surname;
	private String SURNAME;
	private String title;
	private String TITLE;
	private String insertUserName;
	private String INSERT_USER_NAME;
	private String updateUserName;
	private String UPDATE_USER_NAME;

	/**
	 * Constructor
	 */
	@SuppressWarnings("rawtypes")
	public HpPersonD() throws Exception {
		Class cls = getClass();
		Field field[] = cls.getDeclaredFields();

		for (int i=0; i<field.length; i++) {
			if (field[i].getType().isInstance(dataSet) || field[i].getType().isInstance(updateColumnsDataSet) || field[i].getName().equals("updateColumnsDataSetHeader") ||
				field[i].getName().equals("FRW_VAR_PRIMARY_KEY") || field[i].getName().equals("FRW_VAR_DATE_COLUMN") ||
				field[i].getName().equals("FRW_VAR_NUMBER_COLUMN") || field[i].getName().equals("FRW_VAR_CLOB_COLUMN") ||
				field[i].getName().equals("FRW_VAR_DEFAULT_COLUMN") || field[i].getName().equals("FRW_VAR_DEFAULT_VALUE") ||
				!CommonUtil.isUpperCaseWithNumeric(CommonUtil.remove(field[i].getName(), "_"))) {
				continue;
			}

			dataSet.addName(field[i].getName());
		}

		dataSet.addRow();
		updateColumnsDataSet.addName(updateColumnsDataSetHeader);
		setFrwVarPrimaryKey("PERSON_ID");
		setFrwVarDateColumn("CREATION_DATE,FROM_DATE,LAST_UPDATE_DATE,TO_DATE,DATE_OF_BIRTH,FIRST_ASSIGNMENT_DATE,FIRST_CONTACT,LAFHA_LEASE_EXPIRY,MEMBER_SINCE,RESIGNATION_DATE");
		setFrwVarNumberColumn("PERSON_ID,BUSINESS_GROUP_ID,CREATED_BY,LAST_UPDATED_BY,EMPLOYMENT_COMPANY_ORG_ID,CUSTOMER_SUPPORT,ECMS_ACCOUNT_MGR_ID,ECMS_CS_CONSULTANT_ID,LAFHA_AMOUNT,PERSONAL_COMMENT_ID,PRIMARY_PERSON_TYPE_ID,REFERRAL_ID,REFERRAL_ORGANISATION_ID,RESIGNATION_COMMENT_ID");
		setFrwVarClobColumn("");
		setFrwVarDefaultColumn("");
		setFrwVarDefaultValue("");
		setDefaultValue();
	}

	/**
	 * Accessors
	 */
	public double getPersonId() {
		return personId;
	}

	public void setPersonId(double personId) throws Exception {
		this.personId = personId;
		setValueFromAccessor("PERSON_ID", CommonUtil.toString(personId));
	}

	public String getPersonNumber() {
		return personNumber;
	}

	public void setPersonNumber(String personNumber) throws Exception {
		this.personNumber = personNumber;
		setValueFromAccessor("PERSON_NUMBER", personNumber);
	}

	public double getBusinessGroupId() {
		return businessGroupId;
	}

	public void setBusinessGroupId(double businessGroupId) throws Exception {
		this.businessGroupId = businessGroupId;
		setValueFromAccessor("BUSINESS_GROUP_ID", CommonUtil.toString(businessGroupId));
	}

	public double getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(double createdBy) throws Exception {
		this.createdBy = createdBy;
		setValueFromAccessor("CREATED_BY", CommonUtil.toString(createdBy));
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) throws Exception {
		this.creationDate = creationDate;
		setValueFromAccessor("CREATION_DATE", CommonUtil.toString(creationDate));
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) throws Exception {
		this.fromDate = fromDate;
		setValueFromAccessor("FROM_DATE", CommonUtil.toString(fromDate));
	}

	public double getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(double lastUpdatedBy) throws Exception {
		this.lastUpdatedBy = lastUpdatedBy;
		setValueFromAccessor("LAST_UPDATED_BY", CommonUtil.toString(lastUpdatedBy));
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) throws Exception {
		this.lastUpdateDate = lastUpdateDate;
		setValueFromAccessor("LAST_UPDATE_DATE", CommonUtil.toString(lastUpdateDate));
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) throws Exception {
		this.toDate = toDate;
		setValueFromAccessor("TO_DATE", CommonUtil.toString(toDate));
	}

	public double getEmploymentCompanyOrgId() {
		return employmentCompanyOrgId;
	}

	public void setEmploymentCompanyOrgId(double employmentCompanyOrgId) throws Exception {
		this.employmentCompanyOrgId = employmentCompanyOrgId;
		setValueFromAccessor("EMPLOYMENT_COMPANY_ORG_ID", CommonUtil.toString(employmentCompanyOrgId));
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) throws Exception {
		this.attribute3 = attribute3;
		setValueFromAccessor("ATTRIBUTE3", attribute3);
	}

	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute4(String attribute4) throws Exception {
		this.attribute4 = attribute4;
		setValueFromAccessor("ATTRIBUTE4", attribute4);
	}

	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute5(String attribute5) throws Exception {
		this.attribute5 = attribute5;
		setValueFromAccessor("ATTRIBUTE5", attribute5);
	}

	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute6(String attribute6) throws Exception {
		this.attribute6 = attribute6;
		setValueFromAccessor("ATTRIBUTE6", attribute6);
	}

	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute7(String attribute7) throws Exception {
		this.attribute7 = attribute7;
		setValueFromAccessor("ATTRIBUTE7", attribute7);
	}

	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute8(String attribute8) throws Exception {
		this.attribute8 = attribute8;
		setValueFromAccessor("ATTRIBUTE8", attribute8);
	}

	public String getContractDaysWithEcms() {
		return contractDaysWithEcms;
	}

	public void setContractDaysWithEcms(String contractDaysWithEcms) throws Exception {
		this.contractDaysWithEcms = contractDaysWithEcms;
		setValueFromAccessor("CONTRACT_DAYS_WITH_ECMS", contractDaysWithEcms);
	}

	public double getCustomerSupport() {
		return customerSupport;
	}

	public void setCustomerSupport(double customerSupport) throws Exception {
		this.customerSupport = customerSupport;
		setValueFromAccessor("CUSTOMER_SUPPORT", CommonUtil.toString(customerSupport));
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) throws Exception {
		this.dateOfBirth = dateOfBirth;
		setValueFromAccessor("DATE_OF_BIRTH", CommonUtil.toString(dateOfBirth));
	}

	public double getEcmsAccountMgrId() {
		return ecmsAccountMgrId;
	}

	public void setEcmsAccountMgrId(double ecmsAccountMgrId) throws Exception {
		this.ecmsAccountMgrId = ecmsAccountMgrId;
		setValueFromAccessor("ECMS_ACCOUNT_MGR_ID", CommonUtil.toString(ecmsAccountMgrId));
	}

	public double getEcmsCsConsultantId() {
		return ecmsCsConsultantId;
	}

	public void setEcmsCsConsultantId(double ecmsCsConsultantId) throws Exception {
		this.ecmsCsConsultantId = ecmsCsConsultantId;
		setValueFromAccessor("ECMS_CS_CONSULTANT_ID", CommonUtil.toString(ecmsCsConsultantId));
	}

	public String getEcmsPayrollConsultantId() {
		return ecmsPayrollConsultantId;
	}

	public void setEcmsPayrollConsultantId(String ecmsPayrollConsultantId) throws Exception {
		this.ecmsPayrollConsultantId = ecmsPayrollConsultantId;
		setValueFromAccessor("ECMS_PAYROLL_CONSULTANT_ID", ecmsPayrollConsultantId);
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) throws Exception {
		this.employmentType = employmentType;
		setValueFromAccessor("EMPLOYMENT_TYPE", employmentType);
	}

	public Date getFirstAssignmentDate() {
		return firstAssignmentDate;
	}

	public void setFirstAssignmentDate(Date firstAssignmentDate) throws Exception {
		this.firstAssignmentDate = firstAssignmentDate;
		setValueFromAccessor("FIRST_ASSIGNMENT_DATE", CommonUtil.toString(firstAssignmentDate));
	}

	public Date getFirstContact() {
		return firstContact;
	}

	public void setFirstContact(Date firstContact) throws Exception {
		this.firstContact = firstContact;
		setValueFromAccessor("FIRST_CONTACT", CommonUtil.toString(firstContact));
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) throws Exception {
		this.firstName = firstName;
		setValueFromAccessor("FIRST_NAME", firstName);
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) throws Exception {
		this.fullName = fullName;
		setValueFromAccessor("FULL_NAME", fullName);
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) throws Exception {
		this.function = function;
		setValueFromAccessor("FUNCTION", function);
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) throws Exception {
		this.gender = gender;
		setValueFromAccessor("GENDER", gender);
	}

	public String getGtmProfile() {
		return gtmProfile;
	}

	public void setGtmProfile(String gtmProfile) throws Exception {
		this.gtmProfile = gtmProfile;
		setValueFromAccessor("GTM_PROFILE", gtmProfile);
	}

	public String getGtmStage() {
		return gtmStage;
	}

	public void setGtmStage(String gtmStage) throws Exception {
		this.gtmStage = gtmStage;
		setValueFromAccessor("GTM_STAGE", gtmStage);
	}

	public double getLafhaAmount() {
		return lafhaAmount;
	}

	public void setLafhaAmount(double lafhaAmount) throws Exception {
		this.lafhaAmount = lafhaAmount;
		setValueFromAccessor("LAFHA_AMOUNT", CommonUtil.toString(lafhaAmount));
	}

	public String getLafhaEligible() {
		return lafhaEligible;
	}

	public void setLafhaEligible(String lafhaEligible) throws Exception {
		this.lafhaEligible = lafhaEligible;
		setValueFromAccessor("LAFHA_ELIGIBLE", lafhaEligible);
	}

	public Date getLafhaLeaseExpiry() {
		return lafhaLeaseExpiry;
	}

	public void setLafhaLeaseExpiry(Date lafhaLeaseExpiry) throws Exception {
		this.lafhaLeaseExpiry = lafhaLeaseExpiry;
		setValueFromAccessor("LAFHA_LEASE_EXPIRY", CommonUtil.toString(lafhaLeaseExpiry));
	}

	public String getLifeCycleStage() {
		return lifeCycleStage;
	}

	public void setLifeCycleStage(String lifeCycleStage) throws Exception {
		this.lifeCycleStage = lifeCycleStage;
		setValueFromAccessor("LIFE_CYCLE_STAGE", lifeCycleStage);
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) throws Exception {
		this.maritalStatus = maritalStatus;
		setValueFromAccessor("MARITAL_STATUS", maritalStatus);
	}

	public Date getMemberSince() {
		return memberSince;
	}

	public void setMemberSince(Date memberSince) throws Exception {
		this.memberSince = memberSince;
		setValueFromAccessor("MEMBER_SINCE", CommonUtil.toString(memberSince));
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) throws Exception {
		this.middleName = middleName;
		setValueFromAccessor("MIDDLE_NAME", middleName);
	}

	public String getPayrollNotes() {
		return payrollNotes;
	}

	public void setPayrollNotes(String payrollNotes) throws Exception {
		this.payrollNotes = payrollNotes;
		setValueFromAccessor("PAYROLL_NOTES", payrollNotes);
	}

	public String getPayslipEmail() {
		return payslipEmail;
	}

	public void setPayslipEmail(String payslipEmail) throws Exception {
		this.payslipEmail = payslipEmail;
		setValueFromAccessor("PAYSLIP_EMAIL", payslipEmail);
	}

	public double getPersonalCommentId() {
		return personalCommentId;
	}

	public void setPersonalCommentId(double personalCommentId) throws Exception {
		this.personalCommentId = personalCommentId;
		setValueFromAccessor("PERSONAL_COMMENT_ID", CommonUtil.toString(personalCommentId));
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) throws Exception {
		this.personType = personType;
		setValueFromAccessor("PERSON_TYPE", personType);
	}

	public String getPreferred() {
		return preferred;
	}

	public void setPreferred(String preferred) throws Exception {
		this.preferred = preferred;
		setValueFromAccessor("PREFERRED", preferred);
	}

	public String getPreferredName() {
		return preferredName;
	}

	public void setPreferredName(String preferredName) throws Exception {
		this.preferredName = preferredName;
		setValueFromAccessor("PREFERRED_NAME", preferredName);
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) throws Exception {
		this.prefix = prefix;
		setValueFromAccessor("PREFIX", prefix);
	}

	public String getPreviousEmploymentModel() {
		return previousEmploymentModel;
	}

	public void setPreviousEmploymentModel(String previousEmploymentModel) throws Exception {
		this.previousEmploymentModel = previousEmploymentModel;
		setValueFromAccessor("PREVIOUS_EMPLOYMENT_MODEL", previousEmploymentModel);
	}

	public double getPrimaryPersonTypeId() {
		return primaryPersonTypeId;
	}

	public void setPrimaryPersonTypeId(double primaryPersonTypeId) throws Exception {
		this.primaryPersonTypeId = primaryPersonTypeId;
		setValueFromAccessor("PRIMARY_PERSON_TYPE_ID", CommonUtil.toString(primaryPersonTypeId));
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) throws Exception {
		this.referenceNo = referenceNo;
		setValueFromAccessor("REFERENCE_NO", referenceNo);
	}

	public String getReferenceNo2() {
		return referenceNo2;
	}

	public void setReferenceNo2(String referenceNo2) throws Exception {
		this.referenceNo2 = referenceNo2;
		setValueFromAccessor("REFERENCE_NO_2", referenceNo2);
	}

	public String getReferenceNo3() {
		return referenceNo3;
	}

	public void setReferenceNo3(String referenceNo3) throws Exception {
		this.referenceNo3 = referenceNo3;
		setValueFromAccessor("REFERENCE_NO_3", referenceNo3);
	}

	public double getReferralId() {
		return referralId;
	}

	public void setReferralId(double referralId) throws Exception {
		this.referralId = referralId;
		setValueFromAccessor("REFERRAL_ID", CommonUtil.toString(referralId));
	}

	public double getReferralOrganisationId() {
		return referralOrganisationId;
	}

	public void setReferralOrganisationId(double referralOrganisationId) throws Exception {
		this.referralOrganisationId = referralOrganisationId;
		setValueFromAccessor("REFERRAL_ORGANISATION_ID", CommonUtil.toString(referralOrganisationId));
	}

	public double getResignationCommentId() {
		return resignationCommentId;
	}

	public void setResignationCommentId(double resignationCommentId) throws Exception {
		this.resignationCommentId = resignationCommentId;
		setValueFromAccessor("RESIGNATION_COMMENT_ID", CommonUtil.toString(resignationCommentId));
	}

	public Date getResignationDate() {
		return resignationDate;
	}

	public void setResignationDate(Date resignationDate) throws Exception {
		this.resignationDate = resignationDate;
		setValueFromAccessor("RESIGNATION_DATE", CommonUtil.toString(resignationDate));
	}

	public String getResignationReason() {
		return resignationReason;
	}

	public void setResignationReason(String resignationReason) throws Exception {
		this.resignationReason = resignationReason;
		setValueFromAccessor("RESIGNATION_REASON", resignationReason);
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) throws Exception {
		this.serviceStatus = serviceStatus;
		setValueFromAccessor("SERVICE_STATUS", serviceStatus);
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) throws Exception {
		this.serviceType = serviceType;
		setValueFromAccessor("SERVICE_TYPE", serviceType);
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) throws Exception {
		this.skills = skills;
		setValueFromAccessor("SKILLS", skills);
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) throws Exception {
		this.surname = surname;
		setValueFromAccessor("SURNAME", surname);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) throws Exception {
		this.title = title;
		setValueFromAccessor("TITLE", title);
	}

	public String getInsertUserName() {
		return insertUserName;
	}

	public void setInsertUserName(String insertUserName) throws Exception {
		this.insertUserName = insertUserName;
		setValueFromAccessor("INSERT_USER_NAME", insertUserName);
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) throws Exception {
		this.updateUserName = updateUserName;
		setValueFromAccessor("UPDATE_USER_NAME", updateUserName);
	}

	/**
	 * Hibernate Methods - If the primary key is composed of multiple columns
	 */
	
	/**
	 * Framework Methods
	 */
	public void setDefaultValue() throws Exception {
		String columns[] = CommonUtil.split(getFrwVarDefaultColumn(), ",");
		String values[] = CommonUtil.split(getFrwVarDefaultValue(), ",");

		if (CommonUtil.isNotEmpty(columns)) {
			for (int i=0; i<columns.length; i++) {
				setValue(columns[i], values[i]);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void setValue(String columnName, String value) throws Exception {
		Class cls = getClass();
		Field field[] = cls.getDeclaredFields();

		dataSet.setValue(dataSet.getRowCnt()-1, columnName, value);
		for (int i=0; i<field.length; i++) {
			if (field[i].getName().equals(CommonUtil.toCamelCaseStartLowerCase(columnName))) {
				if (CommonUtil.isIn(columnName, CommonUtil.split(getFrwVarNumberColumn(), ","))) {
					field[i].set(this, CommonUtil.toDouble(value));
				} else if (CommonUtil.isIn(columnName, CommonUtil.split(getFrwVarDateColumn(), ","))) {
					if (CommonUtil.equalsIgnoreCase(value, "SYSDATE") || CommonUtil.containsIgnoreCase(value, "SYSDATE")) {
						field[i].set(this, CommonUtil.toDate(CommonUtil.getSysdate()));
					} else {
						field[i].set(this, CommonUtil.toDate(value));
					}
				} else {
					field[i].set(this, value);
				}
			}
		}
	}

	public void setValues(DataSet dataSet) throws Exception {
		for (int i=0; i<dataSet.getColumnCnt(); i++) {
			setValue(dataSet.getName(i), dataSet.getValue(i));
		}
	}

	private void setValueFromAccessor(String columnName, String value) throws Exception {
		dataSet.setValue(dataSet.getRowCnt()-1, columnName, value);
	}

	public void setConvertedTypeValue(String columnName, String value) throws Exception {
		String numberColumn = "", dateColumn = "";

		numberColumn = getFrwVarNumberColumn();
		dateColumn = getFrwVarDateColumn();

		setValue(columnName, value);

		numberColumn += (CommonUtil.isEmpty(numberColumn)) ? CommonUtil.upperCase(columnName) : "," + CommonUtil.upperCase(columnName);
		dateColumn = CommonUtil.replace(dateColumn, columnName, "");

		setFrwVarNumberColumn(numberColumn);
		setFrwVarDateColumn(dateColumn);
	}

	public String getValue(String columnName) throws Exception {
		return dataSet.getValue(dataSet.getRowCnt()-1, columnName);
	}

	public void addUpdateColumn(String columnName, String columnValue) throws Exception {
		String dataType = "";

		if (CommonUtil.isIn(columnName, CommonUtil.split(getFrwVarNumberColumn(), ","))) {
			dataType = "Number";
		} else if (CommonUtil.isIn(columnName, CommonUtil.split(getFrwVarDateColumn(), ","))) {
			dataType = "Date";
		} else {
			dataType = "String";
		}

		addUpdateColumn(columnName, columnValue, dataType);
	}

	/**
	 * dataType : String / Number / Date
	 */
	public void addUpdateColumn(String columnName, String columnValue, String dataType) throws Exception {
		updateColumnsDataSet.addRow();
		updateColumnsDataSet.setValue(updateColumnsDataSet.getRowCnt()-1, "COLUMN_NAME", columnName);
		updateColumnsDataSet.setValue(updateColumnsDataSet.getRowCnt()-1, "COLUMN_VALUE", columnValue);
		updateColumnsDataSet.setValue(updateColumnsDataSet.getRowCnt()-1, "DATA_TYPE", CommonUtil.nvl(dataType, "String"));
	}

	public void addUpdateColumnFromField() throws Exception {
		for (int i=0; i<dataSet.getColumnCnt(); i++) {
			if (CommonUtil.isNotBlank(dataSet.getValue(i))) {
				if (CommonUtil.equalsIgnoreCase(dataSet.getName(i), "INSERT_DATE") && CommonUtil.equalsIgnoreCase(dataSet.getValue(i), "SYSDATE")) {
					continue;
				}
				addUpdateColumn(dataSet.getName(i), dataSet.getValue(i));
			}
		}
	}

	/**
	 * toString
	 */
	public String toString() {
		String str = "";

		str += "personId : "+personId+"\n";
		str += "personNumber : "+personNumber+"\n";
		str += "businessGroupId : "+businessGroupId+"\n";
		str += "createdBy : "+createdBy+"\n";
		str += "creationDate : "+creationDate+"\n";
		str += "fromDate : "+fromDate+"\n";
		str += "lastUpdatedBy : "+lastUpdatedBy+"\n";
		str += "lastUpdateDate : "+lastUpdateDate+"\n";
		str += "toDate : "+toDate+"\n";
		str += "employmentCompanyOrgId : "+employmentCompanyOrgId+"\n";
		str += "attribute3 : "+attribute3+"\n";
		str += "attribute4 : "+attribute4+"\n";
		str += "attribute5 : "+attribute5+"\n";
		str += "attribute6 : "+attribute6+"\n";
		str += "attribute7 : "+attribute7+"\n";
		str += "attribute8 : "+attribute8+"\n";
		str += "contractDaysWithEcms : "+contractDaysWithEcms+"\n";
		str += "customerSupport : "+customerSupport+"\n";
		str += "dateOfBirth : "+dateOfBirth+"\n";
		str += "ecmsAccountMgrId : "+ecmsAccountMgrId+"\n";
		str += "ecmsCsConsultantId : "+ecmsCsConsultantId+"\n";
		str += "ecmsPayrollConsultantId : "+ecmsPayrollConsultantId+"\n";
		str += "employmentType : "+employmentType+"\n";
		str += "firstAssignmentDate : "+firstAssignmentDate+"\n";
		str += "firstContact : "+firstContact+"\n";
		str += "firstName : "+firstName+"\n";
		str += "fullName : "+fullName+"\n";
		str += "function : "+function+"\n";
		str += "gender : "+gender+"\n";
		str += "gtmProfile : "+gtmProfile+"\n";
		str += "gtmStage : "+gtmStage+"\n";
		str += "lafhaAmount : "+lafhaAmount+"\n";
		str += "lafhaEligible : "+lafhaEligible+"\n";
		str += "lafhaLeaseExpiry : "+lafhaLeaseExpiry+"\n";
		str += "lifeCycleStage : "+lifeCycleStage+"\n";
		str += "maritalStatus : "+maritalStatus+"\n";
		str += "memberSince : "+memberSince+"\n";
		str += "middleName : "+middleName+"\n";
		str += "payrollNotes : "+payrollNotes+"\n";
		str += "payslipEmail : "+payslipEmail+"\n";
		str += "personalCommentId : "+personalCommentId+"\n";
		str += "personType : "+personType+"\n";
		str += "preferred : "+preferred+"\n";
		str += "preferredName : "+preferredName+"\n";
		str += "prefix : "+prefix+"\n";
		str += "previousEmploymentModel : "+previousEmploymentModel+"\n";
		str += "primaryPersonTypeId : "+primaryPersonTypeId+"\n";
		str += "referenceNo : "+referenceNo+"\n";
		str += "referenceNo2 : "+referenceNo2+"\n";
		str += "referenceNo3 : "+referenceNo3+"\n";
		str += "referralId : "+referralId+"\n";
		str += "referralOrganisationId : "+referralOrganisationId+"\n";
		str += "resignationCommentId : "+resignationCommentId+"\n";
		str += "resignationDate : "+resignationDate+"\n";
		str += "resignationReason : "+resignationReason+"\n";
		str += "serviceStatus : "+serviceStatus+"\n";
		str += "serviceType : "+serviceType+"\n";
		str += "skills : "+skills+"\n";
		str += "surname : "+surname+"\n";
		str += "title : "+title+"\n";
		str += "insertUserName : "+insertUserName+"\n";
		str += "updateUserName : "+updateUserName+"\n";

		return str;
	}

	/**
	 * toXmlString
	 */
	public String toXmlString() {
		String str = "";

		str += "<column name=\"personId\" value=\""+personId+"\">";
		str += "<column name=\"personNumber\" value=\""+personNumber+"\">";
		str += "<column name=\"businessGroupId\" value=\""+businessGroupId+"\">";
		str += "<column name=\"createdBy\" value=\""+createdBy+"\">";
		str += "<column name=\"creationDate\" value=\""+creationDate+"\">";
		str += "<column name=\"fromDate\" value=\""+fromDate+"\">";
		str += "<column name=\"lastUpdatedBy\" value=\""+lastUpdatedBy+"\">";
		str += "<column name=\"lastUpdateDate\" value=\""+lastUpdateDate+"\">";
		str += "<column name=\"toDate\" value=\""+toDate+"\">";
		str += "<column name=\"employmentCompanyOrgId\" value=\""+employmentCompanyOrgId+"\">";
		str += "<column name=\"attribute3\" value=\""+attribute3+"\">";
		str += "<column name=\"attribute4\" value=\""+attribute4+"\">";
		str += "<column name=\"attribute5\" value=\""+attribute5+"\">";
		str += "<column name=\"attribute6\" value=\""+attribute6+"\">";
		str += "<column name=\"attribute7\" value=\""+attribute7+"\">";
		str += "<column name=\"attribute8\" value=\""+attribute8+"\">";
		str += "<column name=\"contractDaysWithEcms\" value=\""+contractDaysWithEcms+"\">";
		str += "<column name=\"customerSupport\" value=\""+customerSupport+"\">";
		str += "<column name=\"dateOfBirth\" value=\""+dateOfBirth+"\">";
		str += "<column name=\"ecmsAccountMgrId\" value=\""+ecmsAccountMgrId+"\">";
		str += "<column name=\"ecmsCsConsultantId\" value=\""+ecmsCsConsultantId+"\">";
		str += "<column name=\"ecmsPayrollConsultantId\" value=\""+ecmsPayrollConsultantId+"\">";
		str += "<column name=\"employmentType\" value=\""+employmentType+"\">";
		str += "<column name=\"firstAssignmentDate\" value=\""+firstAssignmentDate+"\">";
		str += "<column name=\"firstContact\" value=\""+firstContact+"\">";
		str += "<column name=\"firstName\" value=\""+firstName+"\">";
		str += "<column name=\"fullName\" value=\""+fullName+"\">";
		str += "<column name=\"function\" value=\""+function+"\">";
		str += "<column name=\"gender\" value=\""+gender+"\">";
		str += "<column name=\"gtmProfile\" value=\""+gtmProfile+"\">";
		str += "<column name=\"gtmStage\" value=\""+gtmStage+"\">";
		str += "<column name=\"lafhaAmount\" value=\""+lafhaAmount+"\">";
		str += "<column name=\"lafhaEligible\" value=\""+lafhaEligible+"\">";
		str += "<column name=\"lafhaLeaseExpiry\" value=\""+lafhaLeaseExpiry+"\">";
		str += "<column name=\"lifeCycleStage\" value=\""+lifeCycleStage+"\">";
		str += "<column name=\"maritalStatus\" value=\""+maritalStatus+"\">";
		str += "<column name=\"memberSince\" value=\""+memberSince+"\">";
		str += "<column name=\"middleName\" value=\""+middleName+"\">";
		str += "<column name=\"payrollNotes\" value=\""+payrollNotes+"\">";
		str += "<column name=\"payslipEmail\" value=\""+payslipEmail+"\">";
		str += "<column name=\"personalCommentId\" value=\""+personalCommentId+"\">";
		str += "<column name=\"personType\" value=\""+personType+"\">";
		str += "<column name=\"preferred\" value=\""+preferred+"\">";
		str += "<column name=\"preferredName\" value=\""+preferredName+"\">";
		str += "<column name=\"prefix\" value=\""+prefix+"\">";
		str += "<column name=\"previousEmploymentModel\" value=\""+previousEmploymentModel+"\">";
		str += "<column name=\"primaryPersonTypeId\" value=\""+primaryPersonTypeId+"\">";
		str += "<column name=\"referenceNo\" value=\""+referenceNo+"\">";
		str += "<column name=\"referenceNo2\" value=\""+referenceNo2+"\">";
		str += "<column name=\"referenceNo3\" value=\""+referenceNo3+"\">";
		str += "<column name=\"referralId\" value=\""+referralId+"\">";
		str += "<column name=\"referralOrganisationId\" value=\""+referralOrganisationId+"\">";
		str += "<column name=\"resignationCommentId\" value=\""+resignationCommentId+"\">";
		str += "<column name=\"resignationDate\" value=\""+resignationDate+"\">";
		str += "<column name=\"resignationReason\" value=\""+resignationReason+"\">";
		str += "<column name=\"serviceStatus\" value=\""+serviceStatus+"\">";
		str += "<column name=\"serviceType\" value=\""+serviceType+"\">";
		str += "<column name=\"skills\" value=\""+skills+"\">";
		str += "<column name=\"surname\" value=\""+surname+"\">";
		str += "<column name=\"title\" value=\""+title+"\">";
		str += "<column name=\"insertUserName\" value=\""+insertUserName+"\">";
		str += "<column name=\"updateUserName\" value=\""+updateUserName+"\">";

		return str;
	}

	/**
	 * toJsonString
	 */
	public String toJsonString() {
		String str = "";

		str += "\"personId\":\""+personId+"\", ";
		str += "\"personNumber\":\""+personNumber+"\", ";
		str += "\"businessGroupId\":\""+businessGroupId+"\", ";
		str += "\"createdBy\":\""+createdBy+"\", ";
		str += "\"creationDate\":\""+creationDate+"\", ";
		str += "\"fromDate\":\""+fromDate+"\", ";
		str += "\"lastUpdatedBy\":\""+lastUpdatedBy+"\", ";
		str += "\"lastUpdateDate\":\""+lastUpdateDate+"\", ";
		str += "\"toDate\":\""+toDate+"\", ";
		str += "\"employmentCompanyOrgId\":\""+employmentCompanyOrgId+"\", ";
		str += "\"attribute3\":\""+attribute3+"\", ";
		str += "\"attribute4\":\""+attribute4+"\", ";
		str += "\"attribute5\":\""+attribute5+"\", ";
		str += "\"attribute6\":\""+attribute6+"\", ";
		str += "\"attribute7\":\""+attribute7+"\", ";
		str += "\"attribute8\":\""+attribute8+"\", ";
		str += "\"contractDaysWithEcms\":\""+contractDaysWithEcms+"\", ";
		str += "\"customerSupport\":\""+customerSupport+"\", ";
		str += "\"dateOfBirth\":\""+dateOfBirth+"\", ";
		str += "\"ecmsAccountMgrId\":\""+ecmsAccountMgrId+"\", ";
		str += "\"ecmsCsConsultantId\":\""+ecmsCsConsultantId+"\", ";
		str += "\"ecmsPayrollConsultantId\":\""+ecmsPayrollConsultantId+"\", ";
		str += "\"employmentType\":\""+employmentType+"\", ";
		str += "\"firstAssignmentDate\":\""+firstAssignmentDate+"\", ";
		str += "\"firstContact\":\""+firstContact+"\", ";
		str += "\"firstName\":\""+firstName+"\", ";
		str += "\"fullName\":\""+fullName+"\", ";
		str += "\"function\":\""+function+"\", ";
		str += "\"gender\":\""+gender+"\", ";
		str += "\"gtmProfile\":\""+gtmProfile+"\", ";
		str += "\"gtmStage\":\""+gtmStage+"\", ";
		str += "\"lafhaAmount\":\""+lafhaAmount+"\", ";
		str += "\"lafhaEligible\":\""+lafhaEligible+"\", ";
		str += "\"lafhaLeaseExpiry\":\""+lafhaLeaseExpiry+"\", ";
		str += "\"lifeCycleStage\":\""+lifeCycleStage+"\", ";
		str += "\"maritalStatus\":\""+maritalStatus+"\", ";
		str += "\"memberSince\":\""+memberSince+"\", ";
		str += "\"middleName\":\""+middleName+"\", ";
		str += "\"payrollNotes\":\""+payrollNotes+"\", ";
		str += "\"payslipEmail\":\""+payslipEmail+"\", ";
		str += "\"personalCommentId\":\""+personalCommentId+"\", ";
		str += "\"personType\":\""+personType+"\", ";
		str += "\"preferred\":\""+preferred+"\", ";
		str += "\"preferredName\":\""+preferredName+"\", ";
		str += "\"prefix\":\""+prefix+"\", ";
		str += "\"previousEmploymentModel\":\""+previousEmploymentModel+"\", ";
		str += "\"primaryPersonTypeId\":\""+primaryPersonTypeId+"\", ";
		str += "\"referenceNo\":\""+referenceNo+"\", ";
		str += "\"referenceNo2\":\""+referenceNo2+"\", ";
		str += "\"referenceNo3\":\""+referenceNo3+"\", ";
		str += "\"referralId\":\""+referralId+"\", ";
		str += "\"referralOrganisationId\":\""+referralOrganisationId+"\", ";
		str += "\"resignationCommentId\":\""+resignationCommentId+"\", ";
		str += "\"resignationDate\":\""+resignationDate+"\", ";
		str += "\"resignationReason\":\""+resignationReason+"\", ";
		str += "\"serviceStatus\":\""+serviceStatus+"\", ";
		str += "\"serviceType\":\""+serviceType+"\", ";
		str += "\"skills\":\""+skills+"\", ";
		str += "\"surname\":\""+surname+"\", ";
		str += "\"title\":\""+title+"\", ";
		str += "\"insertUserName\":\""+insertUserName+"\", ";
		str += "\"updateUserName\":\""+updateUserName+"\"";

		return str;
	}
}