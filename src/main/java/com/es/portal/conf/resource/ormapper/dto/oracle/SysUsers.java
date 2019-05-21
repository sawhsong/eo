/**************************************************************************************************
 * Framework Generated DTO Source
 * - SYS_USERS - 
 *************************************************************************************************/
package com.es.portal.conf.resource.ormapper.dto.oracle;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

import zebra.data.DataSet;
import zebra.util.CommonUtil;
import com.es.portal.common.extend.BaseDto;

@SuppressWarnings("unused")
public class SysUsers extends BaseDto implements Serializable {
	/**
	 * Columns
	 */
	private double userId;
	private String USER_ID;
	private double createdBy;
	private String CREATED_BY;
	private Date creationDate;
	private String CREATION_DATE;
	private double lastUpdatedBy;
	private String LAST_UPDATED_BY;
	private Date lastUpdateDate;
	private String LAST_UPDATE_DATE;
	private String userName;
	private String USER_NAME;
	private double customerId;
	private String CUSTOMER_ID;
	private String description;
	private String DESCRIPTION;
	private Date disabledDate;
	private String DISABLED_DATE;
	private String email;
	private String EMAIL;
	private Date fromDate;
	private String FROM_DATE;
	private String isActive;
	private String IS_ACTIVE;
	private String isPortalUser;
	private String IS_PORTAL_USER;
	private Date lastLogin;
	private String LAST_LOGIN;
	private Date lastPasswordChange;
	private String LAST_PASSWORD_CHANGE;
	private String locked;
	private String LOCKED;
	private double loginAttempts;
	private String LOGIN_ATTEMPTS;
	private String password;
	private String PASSWORD;
	private double personId;
	private String PERSON_ID;
	private double pin;
	private String PIN;
	private double portalOrgProfileId;
	private String PORTAL_ORG_PROFILE_ID;
	private String portalSecurityRole;
	private String PORTAL_SECURITY_ROLE;
	private String portalSkin;
	private String PORTAL_SKIN;
	private String primaryContact;
	private String PRIMARY_CONTACT;
	private String propToPortal;
	private String PROP_TO_PORTAL;
	private String resetPassword;
	private String RESET_PASSWORD;
	private String resetTermCondition;
	private String RESET_TERM_CONDITION;
	private String secondaryContact;
	private String SECONDARY_CONTACT;
	private String securityGroup;
	private String SECURITY_GROUP;
	private String securityPattern;
	private String SECURITY_PATTERN;
	private String securityQuestion1;
	private String SECURITY_QUESTION_1;
	private String securityQuestion2;
	private String SECURITY_QUESTION_2;
	private String securityQuestionAnswer1;
	private String SECURITY_QUESTION_ANSWER_1;
	private String securityQuestionAnswer2;
	private String SECURITY_QUESTION_ANSWER_2;
	private double supplier;
	private String SUPPLIER;
	private Date toDate;
	private String TO_DATE;
	private String startupUrl;
	private String STARTUP_URL;
	private String insertUserName;
	private String INSERT_USER_NAME;
	private String updateUserName;
	private String UPDATE_USER_NAME;

	/**
	 * Constructor
	 */
	@SuppressWarnings("rawtypes")
	public SysUsers() throws Exception {
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
		setFrwVarPrimaryKey("USER_ID");
		setFrwVarDateColumn("CREATION_DATE,LAST_UPDATE_DATE,DISABLED_DATE,FROM_DATE,LAST_LOGIN,LAST_PASSWORD_CHANGE,TO_DATE");
		setFrwVarNumberColumn("USER_ID,CREATED_BY,LAST_UPDATED_BY,CUSTOMER_ID,LOGIN_ATTEMPTS,PERSON_ID,PIN,PORTAL_ORG_PROFILE_ID,SUPPLIER");
		setFrwVarClobColumn("");
		setFrwVarDefaultColumn("LAST_PASSWORD_CHANGE,LOCKED,LOGIN_ATTEMPTS,PROP_TO_PORTAL,RESET_PASSWORD,RESET_TERM_CONDITION");
		setFrwVarDefaultValue("trunc(sysdate),N,0,I,Y,Y");
		setDefaultValue();
	}

	/**
	 * Accessors
	 */
	public double getUserId() {
		return userId;
	}

	public void setUserId(double userId) throws Exception {
		this.userId = userId;
		setValueFromAccessor("USER_ID", CommonUtil.toString(userId));
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) throws Exception {
		this.userName = userName;
		setValueFromAccessor("USER_NAME", userName);
	}

	public double getCustomerId() {
		return customerId;
	}

	public void setCustomerId(double customerId) throws Exception {
		this.customerId = customerId;
		setValueFromAccessor("CUSTOMER_ID", CommonUtil.toString(customerId));
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) throws Exception {
		this.description = description;
		setValueFromAccessor("DESCRIPTION", description);
	}

	public Date getDisabledDate() {
		return disabledDate;
	}

	public void setDisabledDate(Date disabledDate) throws Exception {
		this.disabledDate = disabledDate;
		setValueFromAccessor("DISABLED_DATE", CommonUtil.toString(disabledDate));
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws Exception {
		this.email = email;
		setValueFromAccessor("EMAIL", email);
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) throws Exception {
		this.fromDate = fromDate;
		setValueFromAccessor("FROM_DATE", CommonUtil.toString(fromDate));
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) throws Exception {
		this.isActive = isActive;
		setValueFromAccessor("IS_ACTIVE", isActive);
	}

	public String getIsPortalUser() {
		return isPortalUser;
	}

	public void setIsPortalUser(String isPortalUser) throws Exception {
		this.isPortalUser = isPortalUser;
		setValueFromAccessor("IS_PORTAL_USER", isPortalUser);
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) throws Exception {
		this.lastLogin = lastLogin;
		setValueFromAccessor("LAST_LOGIN", CommonUtil.toString(lastLogin));
	}

	public Date getLastPasswordChange() {
		return lastPasswordChange;
	}

	public void setLastPasswordChange(Date lastPasswordChange) throws Exception {
		this.lastPasswordChange = lastPasswordChange;
		setValueFromAccessor("LAST_PASSWORD_CHANGE", CommonUtil.toString(lastPasswordChange));
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) throws Exception {
		this.locked = locked;
		setValueFromAccessor("LOCKED", locked);
	}

	public double getLoginAttempts() {
		return loginAttempts;
	}

	public void setLoginAttempts(double loginAttempts) throws Exception {
		this.loginAttempts = loginAttempts;
		setValueFromAccessor("LOGIN_ATTEMPTS", CommonUtil.toString(loginAttempts));
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws Exception {
		this.password = password;
		setValueFromAccessor("PASSWORD", password);
	}

	public double getPersonId() {
		return personId;
	}

	public void setPersonId(double personId) throws Exception {
		this.personId = personId;
		setValueFromAccessor("PERSON_ID", CommonUtil.toString(personId));
	}

	public double getPin() {
		return pin;
	}

	public void setPin(double pin) throws Exception {
		this.pin = pin;
		setValueFromAccessor("PIN", CommonUtil.toString(pin));
	}

	public double getPortalOrgProfileId() {
		return portalOrgProfileId;
	}

	public void setPortalOrgProfileId(double portalOrgProfileId) throws Exception {
		this.portalOrgProfileId = portalOrgProfileId;
		setValueFromAccessor("PORTAL_ORG_PROFILE_ID", CommonUtil.toString(portalOrgProfileId));
	}

	public String getPortalSecurityRole() {
		return portalSecurityRole;
	}

	public void setPortalSecurityRole(String portalSecurityRole) throws Exception {
		this.portalSecurityRole = portalSecurityRole;
		setValueFromAccessor("PORTAL_SECURITY_ROLE", portalSecurityRole);
	}

	public String getPortalSkin() {
		return portalSkin;
	}

	public void setPortalSkin(String portalSkin) throws Exception {
		this.portalSkin = portalSkin;
		setValueFromAccessor("PORTAL_SKIN", portalSkin);
	}

	public String getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(String primaryContact) throws Exception {
		this.primaryContact = primaryContact;
		setValueFromAccessor("PRIMARY_CONTACT", primaryContact);
	}

	public String getPropToPortal() {
		return propToPortal;
	}

	public void setPropToPortal(String propToPortal) throws Exception {
		this.propToPortal = propToPortal;
		setValueFromAccessor("PROP_TO_PORTAL", propToPortal);
	}

	public String getResetPassword() {
		return resetPassword;
	}

	public void setResetPassword(String resetPassword) throws Exception {
		this.resetPassword = resetPassword;
		setValueFromAccessor("RESET_PASSWORD", resetPassword);
	}

	public String getResetTermCondition() {
		return resetTermCondition;
	}

	public void setResetTermCondition(String resetTermCondition) throws Exception {
		this.resetTermCondition = resetTermCondition;
		setValueFromAccessor("RESET_TERM_CONDITION", resetTermCondition);
	}

	public String getSecondaryContact() {
		return secondaryContact;
	}

	public void setSecondaryContact(String secondaryContact) throws Exception {
		this.secondaryContact = secondaryContact;
		setValueFromAccessor("SECONDARY_CONTACT", secondaryContact);
	}

	public String getSecurityGroup() {
		return securityGroup;
	}

	public void setSecurityGroup(String securityGroup) throws Exception {
		this.securityGroup = securityGroup;
		setValueFromAccessor("SECURITY_GROUP", securityGroup);
	}

	public String getSecurityPattern() {
		return securityPattern;
	}

	public void setSecurityPattern(String securityPattern) throws Exception {
		this.securityPattern = securityPattern;
		setValueFromAccessor("SECURITY_PATTERN", securityPattern);
	}

	public String getSecurityQuestion1() {
		return securityQuestion1;
	}

	public void setSecurityQuestion1(String securityQuestion1) throws Exception {
		this.securityQuestion1 = securityQuestion1;
		setValueFromAccessor("SECURITY_QUESTION_1", securityQuestion1);
	}

	public String getSecurityQuestion2() {
		return securityQuestion2;
	}

	public void setSecurityQuestion2(String securityQuestion2) throws Exception {
		this.securityQuestion2 = securityQuestion2;
		setValueFromAccessor("SECURITY_QUESTION_2", securityQuestion2);
	}

	public String getSecurityQuestionAnswer1() {
		return securityQuestionAnswer1;
	}

	public void setSecurityQuestionAnswer1(String securityQuestionAnswer1) throws Exception {
		this.securityQuestionAnswer1 = securityQuestionAnswer1;
		setValueFromAccessor("SECURITY_QUESTION_ANSWER_1", securityQuestionAnswer1);
	}

	public String getSecurityQuestionAnswer2() {
		return securityQuestionAnswer2;
	}

	public void setSecurityQuestionAnswer2(String securityQuestionAnswer2) throws Exception {
		this.securityQuestionAnswer2 = securityQuestionAnswer2;
		setValueFromAccessor("SECURITY_QUESTION_ANSWER_2", securityQuestionAnswer2);
	}

	public double getSupplier() {
		return supplier;
	}

	public void setSupplier(double supplier) throws Exception {
		this.supplier = supplier;
		setValueFromAccessor("SUPPLIER", CommonUtil.toString(supplier));
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) throws Exception {
		this.toDate = toDate;
		setValueFromAccessor("TO_DATE", CommonUtil.toString(toDate));
	}

	public String getStartupUrl() {
		return startupUrl;
	}

	public void setStartupUrl(String startupUrl) throws Exception {
		this.startupUrl = startupUrl;
		setValueFromAccessor("STARTUP_URL", startupUrl);
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

	public void setValues(DataSet dataSet, int rowIndex) throws Exception {
		for (int i=0; i<dataSet.getColumnCnt(); i++) {
			setValue(dataSet.getName(i), dataSet.getValue(rowIndex, i));
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

		str += "userId : "+userId+"\n";
		str += "createdBy : "+createdBy+"\n";
		str += "creationDate : "+creationDate+"\n";
		str += "lastUpdatedBy : "+lastUpdatedBy+"\n";
		str += "lastUpdateDate : "+lastUpdateDate+"\n";
		str += "userName : "+userName+"\n";
		str += "customerId : "+customerId+"\n";
		str += "description : "+description+"\n";
		str += "disabledDate : "+disabledDate+"\n";
		str += "email : "+email+"\n";
		str += "fromDate : "+fromDate+"\n";
		str += "isActive : "+isActive+"\n";
		str += "isPortalUser : "+isPortalUser+"\n";
		str += "lastLogin : "+lastLogin+"\n";
		str += "lastPasswordChange : "+lastPasswordChange+"\n";
		str += "locked : "+locked+"\n";
		str += "loginAttempts : "+loginAttempts+"\n";
		str += "password : "+password+"\n";
		str += "personId : "+personId+"\n";
		str += "pin : "+pin+"\n";
		str += "portalOrgProfileId : "+portalOrgProfileId+"\n";
		str += "portalSecurityRole : "+portalSecurityRole+"\n";
		str += "portalSkin : "+portalSkin+"\n";
		str += "primaryContact : "+primaryContact+"\n";
		str += "propToPortal : "+propToPortal+"\n";
		str += "resetPassword : "+resetPassword+"\n";
		str += "resetTermCondition : "+resetTermCondition+"\n";
		str += "secondaryContact : "+secondaryContact+"\n";
		str += "securityGroup : "+securityGroup+"\n";
		str += "securityPattern : "+securityPattern+"\n";
		str += "securityQuestion1 : "+securityQuestion1+"\n";
		str += "securityQuestion2 : "+securityQuestion2+"\n";
		str += "securityQuestionAnswer1 : "+securityQuestionAnswer1+"\n";
		str += "securityQuestionAnswer2 : "+securityQuestionAnswer2+"\n";
		str += "supplier : "+supplier+"\n";
		str += "toDate : "+toDate+"\n";
		str += "startupUrl : "+startupUrl+"\n";
		str += "insertUserName : "+insertUserName+"\n";
		str += "updateUserName : "+updateUserName+"\n";

		return str;
	}

	/**
	 * toXmlString
	 */
	public String toXmlString() {
		String str = "";

		str += "<column name=\"userId\" value=\""+userId+"\">";
		str += "<column name=\"createdBy\" value=\""+createdBy+"\">";
		str += "<column name=\"creationDate\" value=\""+creationDate+"\">";
		str += "<column name=\"lastUpdatedBy\" value=\""+lastUpdatedBy+"\">";
		str += "<column name=\"lastUpdateDate\" value=\""+lastUpdateDate+"\">";
		str += "<column name=\"userName\" value=\""+userName+"\">";
		str += "<column name=\"customerId\" value=\""+customerId+"\">";
		str += "<column name=\"description\" value=\""+description+"\">";
		str += "<column name=\"disabledDate\" value=\""+disabledDate+"\">";
		str += "<column name=\"email\" value=\""+email+"\">";
		str += "<column name=\"fromDate\" value=\""+fromDate+"\">";
		str += "<column name=\"isActive\" value=\""+isActive+"\">";
		str += "<column name=\"isPortalUser\" value=\""+isPortalUser+"\">";
		str += "<column name=\"lastLogin\" value=\""+lastLogin+"\">";
		str += "<column name=\"lastPasswordChange\" value=\""+lastPasswordChange+"\">";
		str += "<column name=\"locked\" value=\""+locked+"\">";
		str += "<column name=\"loginAttempts\" value=\""+loginAttempts+"\">";
		str += "<column name=\"password\" value=\""+password+"\">";
		str += "<column name=\"personId\" value=\""+personId+"\">";
		str += "<column name=\"pin\" value=\""+pin+"\">";
		str += "<column name=\"portalOrgProfileId\" value=\""+portalOrgProfileId+"\">";
		str += "<column name=\"portalSecurityRole\" value=\""+portalSecurityRole+"\">";
		str += "<column name=\"portalSkin\" value=\""+portalSkin+"\">";
		str += "<column name=\"primaryContact\" value=\""+primaryContact+"\">";
		str += "<column name=\"propToPortal\" value=\""+propToPortal+"\">";
		str += "<column name=\"resetPassword\" value=\""+resetPassword+"\">";
		str += "<column name=\"resetTermCondition\" value=\""+resetTermCondition+"\">";
		str += "<column name=\"secondaryContact\" value=\""+secondaryContact+"\">";
		str += "<column name=\"securityGroup\" value=\""+securityGroup+"\">";
		str += "<column name=\"securityPattern\" value=\""+securityPattern+"\">";
		str += "<column name=\"securityQuestion1\" value=\""+securityQuestion1+"\">";
		str += "<column name=\"securityQuestion2\" value=\""+securityQuestion2+"\">";
		str += "<column name=\"securityQuestionAnswer1\" value=\""+securityQuestionAnswer1+"\">";
		str += "<column name=\"securityQuestionAnswer2\" value=\""+securityQuestionAnswer2+"\">";
		str += "<column name=\"supplier\" value=\""+supplier+"\">";
		str += "<column name=\"toDate\" value=\""+toDate+"\">";
		str += "<column name=\"startupUrl\" value=\""+startupUrl+"\">";
		str += "<column name=\"insertUserName\" value=\""+insertUserName+"\">";
		str += "<column name=\"updateUserName\" value=\""+updateUserName+"\">";

		return str;
	}

	/**
	 * toJsonString
	 */
	public String toJsonString() {
		String str = "";

		str += "\"userId\":\""+userId+"\", ";
		str += "\"createdBy\":\""+createdBy+"\", ";
		str += "\"creationDate\":\""+creationDate+"\", ";
		str += "\"lastUpdatedBy\":\""+lastUpdatedBy+"\", ";
		str += "\"lastUpdateDate\":\""+lastUpdateDate+"\", ";
		str += "\"userName\":\""+userName+"\", ";
		str += "\"customerId\":\""+customerId+"\", ";
		str += "\"description\":\""+description+"\", ";
		str += "\"disabledDate\":\""+disabledDate+"\", ";
		str += "\"email\":\""+email+"\", ";
		str += "\"fromDate\":\""+fromDate+"\", ";
		str += "\"isActive\":\""+isActive+"\", ";
		str += "\"isPortalUser\":\""+isPortalUser+"\", ";
		str += "\"lastLogin\":\""+lastLogin+"\", ";
		str += "\"lastPasswordChange\":\""+lastPasswordChange+"\", ";
		str += "\"locked\":\""+locked+"\", ";
		str += "\"loginAttempts\":\""+loginAttempts+"\", ";
		str += "\"password\":\""+password+"\", ";
		str += "\"personId\":\""+personId+"\", ";
		str += "\"pin\":\""+pin+"\", ";
		str += "\"portalOrgProfileId\":\""+portalOrgProfileId+"\", ";
		str += "\"portalSecurityRole\":\""+portalSecurityRole+"\", ";
		str += "\"portalSkin\":\""+portalSkin+"\", ";
		str += "\"primaryContact\":\""+primaryContact+"\", ";
		str += "\"propToPortal\":\""+propToPortal+"\", ";
		str += "\"resetPassword\":\""+resetPassword+"\", ";
		str += "\"resetTermCondition\":\""+resetTermCondition+"\", ";
		str += "\"secondaryContact\":\""+secondaryContact+"\", ";
		str += "\"securityGroup\":\""+securityGroup+"\", ";
		str += "\"securityPattern\":\""+securityPattern+"\", ";
		str += "\"securityQuestion1\":\""+securityQuestion1+"\", ";
		str += "\"securityQuestion2\":\""+securityQuestion2+"\", ";
		str += "\"securityQuestionAnswer1\":\""+securityQuestionAnswer1+"\", ";
		str += "\"securityQuestionAnswer2\":\""+securityQuestionAnswer2+"\", ";
		str += "\"supplier\":\""+supplier+"\", ";
		str += "\"toDate\":\""+toDate+"\", ";
		str += "\"startupUrl\":\""+startupUrl+"\", ";
		str += "\"insertUserName\":\""+insertUserName+"\", ";
		str += "\"updateUserName\":\""+updateUserName+"\"";

		return str;
	}
}