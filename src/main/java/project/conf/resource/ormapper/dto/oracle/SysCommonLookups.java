/**************************************************************************************************
 * Framework Generated DTO Source
 * - SYS_COMMON_LOOKUPS - 
 *************************************************************************************************/
package project.conf.resource.ormapper.dto.oracle;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

import zebra.data.DataSet;
import zebra.util.CommonUtil;
import project.common.extend.BaseDto;

@SuppressWarnings("unused")
public class SysCommonLookups extends BaseDto implements Serializable {
	/**
	 * Columns
	 */
	private double createdBy;
	private String CREATED_BY;
	private Date creationDate;
	private String CREATION_DATE;
	private String enabledFlag;
	private String ENABLED_FLAG;
	private double lastUpdatedBy;
	private String LAST_UPDATED_BY;
	private Date lastUpdateDate;
	private String LAST_UPDATE_DATE;
	private String lookupCode;
	private String LOOKUP_CODE;
	private String lookupType;
	private String LOOKUP_TYPE;
	private String meaning;
	private String MEANING;
	private String attribute1;
	private String ATTRIBUTE1;
	private String attribute2;
	private String ATTRIBUTE2;
	private String attribute3;
	private String ATTRIBUTE3;
	private String attribute4;
	private String ATTRIBUTE4;
	private String attribute5;
	private String ATTRIBUTE5;
	private String description;
	private String DESCRIPTION;
	private double displayOrder;
	private String DISPLAY_ORDER;
	private Date fromDateActive;
	private String FROM_DATE_ACTIVE;
	private double lookupId;
	private String LOOKUP_ID;
	private Date toDateActive;
	private String TO_DATE_ACTIVE;
	private String insertUserName;
	private String INSERT_USER_NAME;
	private String updateUserName;
	private String UPDATE_USER_NAME;

	/**
	 * Constructor
	 */
	@SuppressWarnings("rawtypes")
	public SysCommonLookups() throws Exception {
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
		setFrwVarPrimaryKey("");
		setFrwVarDateColumn("CREATION_DATE,LAST_UPDATE_DATE,FROM_DATE_ACTIVE,TO_DATE_ACTIVE");
		setFrwVarNumberColumn("CREATED_BY,LAST_UPDATED_BY,DISPLAY_ORDER,LOOKUP_ID");
		setFrwVarClobColumn("");
		setFrwVarDefaultColumn("");
		setFrwVarDefaultValue("");
		setDefaultValue();
	}

	/**
	 * Accessors
	 */
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

	public String getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(String enabledFlag) throws Exception {
		this.enabledFlag = enabledFlag;
		setValueFromAccessor("ENABLED_FLAG", enabledFlag);
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

	public String getLookupCode() {
		return lookupCode;
	}

	public void setLookupCode(String lookupCode) throws Exception {
		this.lookupCode = lookupCode;
		setValueFromAccessor("LOOKUP_CODE", lookupCode);
	}

	public String getLookupType() {
		return lookupType;
	}

	public void setLookupType(String lookupType) throws Exception {
		this.lookupType = lookupType;
		setValueFromAccessor("LOOKUP_TYPE", lookupType);
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) throws Exception {
		this.meaning = meaning;
		setValueFromAccessor("MEANING", meaning);
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) throws Exception {
		this.attribute1 = attribute1;
		setValueFromAccessor("ATTRIBUTE1", attribute1);
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) throws Exception {
		this.attribute2 = attribute2;
		setValueFromAccessor("ATTRIBUTE2", attribute2);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) throws Exception {
		this.description = description;
		setValueFromAccessor("DESCRIPTION", description);
	}

	public double getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(double displayOrder) throws Exception {
		this.displayOrder = displayOrder;
		setValueFromAccessor("DISPLAY_ORDER", CommonUtil.toString(displayOrder));
	}

	public Date getFromDateActive() {
		return fromDateActive;
	}

	public void setFromDateActive(Date fromDateActive) throws Exception {
		this.fromDateActive = fromDateActive;
		setValueFromAccessor("FROM_DATE_ACTIVE", CommonUtil.toString(fromDateActive));
	}

	public double getLookupId() {
		return lookupId;
	}

	public void setLookupId(double lookupId) throws Exception {
		this.lookupId = lookupId;
		setValueFromAccessor("LOOKUP_ID", CommonUtil.toString(lookupId));
	}

	public Date getToDateActive() {
		return toDateActive;
	}

	public void setToDateActive(Date toDateActive) throws Exception {
		this.toDateActive = toDateActive;
		setValueFromAccessor("TO_DATE_ACTIVE", CommonUtil.toString(toDateActive));
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
				if (CommonUtil.containsIgnoreCase(getFrwVarNumberColumn(), columnName)) {
					field[i].set(this, CommonUtil.toDouble(value));
				} else if (CommonUtil.containsIgnoreCase(getFrwVarDateColumn(), columnName)) {
					if (CommonUtil.equalsIgnoreCase(value, "SYSDATE")) {
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

		if (CommonUtil.containsIgnoreCase(getFrwVarNumberColumn(), columnName)) {
			dataType = "Number";
		} else if (CommonUtil.containsIgnoreCase(getFrwVarDateColumn(), columnName)) {
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

		str += "createdBy : "+createdBy+"\n";
		str += "creationDate : "+creationDate+"\n";
		str += "enabledFlag : "+enabledFlag+"\n";
		str += "lastUpdatedBy : "+lastUpdatedBy+"\n";
		str += "lastUpdateDate : "+lastUpdateDate+"\n";
		str += "lookupCode : "+lookupCode+"\n";
		str += "lookupType : "+lookupType+"\n";
		str += "meaning : "+meaning+"\n";
		str += "attribute1 : "+attribute1+"\n";
		str += "attribute2 : "+attribute2+"\n";
		str += "attribute3 : "+attribute3+"\n";
		str += "attribute4 : "+attribute4+"\n";
		str += "attribute5 : "+attribute5+"\n";
		str += "description : "+description+"\n";
		str += "displayOrder : "+displayOrder+"\n";
		str += "fromDateActive : "+fromDateActive+"\n";
		str += "lookupId : "+lookupId+"\n";
		str += "toDateActive : "+toDateActive+"\n";
		str += "insertUserName : "+insertUserName+"\n";
		str += "updateUserName : "+updateUserName+"\n";

		return str;
	}

	/**
	 * toXmlString
	 */
	public String toXmlString() {
		String str = "";

		str += "<column name=\"createdBy\" value=\""+createdBy+"\">";
		str += "<column name=\"creationDate\" value=\""+creationDate+"\">";
		str += "<column name=\"enabledFlag\" value=\""+enabledFlag+"\">";
		str += "<column name=\"lastUpdatedBy\" value=\""+lastUpdatedBy+"\">";
		str += "<column name=\"lastUpdateDate\" value=\""+lastUpdateDate+"\">";
		str += "<column name=\"lookupCode\" value=\""+lookupCode+"\">";
		str += "<column name=\"lookupType\" value=\""+lookupType+"\">";
		str += "<column name=\"meaning\" value=\""+meaning+"\">";
		str += "<column name=\"attribute1\" value=\""+attribute1+"\">";
		str += "<column name=\"attribute2\" value=\""+attribute2+"\">";
		str += "<column name=\"attribute3\" value=\""+attribute3+"\">";
		str += "<column name=\"attribute4\" value=\""+attribute4+"\">";
		str += "<column name=\"attribute5\" value=\""+attribute5+"\">";
		str += "<column name=\"description\" value=\""+description+"\">";
		str += "<column name=\"displayOrder\" value=\""+displayOrder+"\">";
		str += "<column name=\"fromDateActive\" value=\""+fromDateActive+"\">";
		str += "<column name=\"lookupId\" value=\""+lookupId+"\">";
		str += "<column name=\"toDateActive\" value=\""+toDateActive+"\">";
		str += "<column name=\"insertUserName\" value=\""+insertUserName+"\">";
		str += "<column name=\"updateUserName\" value=\""+updateUserName+"\">";

		return str;
	}

	/**
	 * toJsonString
	 */
	public String toJsonString() {
		String str = "";

		str += "\"createdBy\":\""+createdBy+"\", ";
		str += "\"creationDate\":\""+creationDate+"\", ";
		str += "\"enabledFlag\":\""+enabledFlag+"\", ";
		str += "\"lastUpdatedBy\":\""+lastUpdatedBy+"\", ";
		str += "\"lastUpdateDate\":\""+lastUpdateDate+"\", ";
		str += "\"lookupCode\":\""+lookupCode+"\", ";
		str += "\"lookupType\":\""+lookupType+"\", ";
		str += "\"meaning\":\""+meaning+"\", ";
		str += "\"attribute1\":\""+attribute1+"\", ";
		str += "\"attribute2\":\""+attribute2+"\", ";
		str += "\"attribute3\":\""+attribute3+"\", ";
		str += "\"attribute4\":\""+attribute4+"\", ";
		str += "\"attribute5\":\""+attribute5+"\", ";
		str += "\"description\":\""+description+"\", ";
		str += "\"displayOrder\":\""+displayOrder+"\", ";
		str += "\"fromDateActive\":\""+fromDateActive+"\", ";
		str += "\"lookupId\":\""+lookupId+"\", ";
		str += "\"toDateActive\":\""+toDateActive+"\", ";
		str += "\"insertUserName\":\""+insertUserName+"\", ";
		str += "\"updateUserName\":\""+updateUserName+"\"";

		return str;
	}
}