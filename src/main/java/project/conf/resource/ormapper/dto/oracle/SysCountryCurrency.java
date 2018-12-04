/**************************************************************************************************
 * Framework Generated DTO Source
 * - SYS_COUNTRY_CURRENCY - Country and Currency Info
 *************************************************************************************************/
package project.conf.resource.ormapper.dto.oracle;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

import zebra.data.DataSet;
import zebra.util.CommonUtil;
import project.common.extend.BaseDto;

@SuppressWarnings("unused")
public class SysCountryCurrency extends BaseDto implements Serializable {
	/**
	 * Columns
	 */
	private String countryCurrencyId;
	private String COUNTRY_CURRENCY_ID;
	private String countryCode2;
	private String COUNTRY_CODE_2;
	private String countryCode3;
	private String COUNTRY_CODE_3;
	private String countryLanguageCode;
	private String COUNTRY_LANGUAGE_CODE;
	private String countryName;
	private String COUNTRY_NAME;
	private String countryNumericCode;
	private String COUNTRY_NUMERIC_CODE;
	private String currencyAlphabeticCode;
	private String CURRENCY_ALPHABETIC_CODE;
	private String currencyName;
	private String CURRENCY_NAME;
	private String currencyNumericCode;
	private String CURRENCY_NUMERIC_CODE;
	private String currencySymbol;
	private String CURRENCY_SYMBOL;
	private Date insertDate;
	private String INSERT_DATE;
	private String insertUserId;
	private String INSERT_USER_ID;
	private Date updateDate;
	private String UPDATE_DATE;
	private String updateUserId;
	private String UPDATE_USER_ID;
	private String insertUserName;
	private String INSERT_USER_NAME;
	private String updateUserName;
	private String UPDATE_USER_NAME;

	/**
	 * Constructor
	 */
	@SuppressWarnings("rawtypes")
	public SysCountryCurrency() throws Exception {
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
		setFrwVarPrimaryKey("COUNTRY_CURRENCY_ID");
		setFrwVarDateColumn("INSERT_DATE,UPDATE_DATE");
		setFrwVarNumberColumn("");
		setFrwVarClobColumn("");
		setFrwVarDefaultColumn("INSERT_DATE");
		setFrwVarDefaultValue("sysdate");
		setDefaultValue();
	}

	/**
	 * Accessors
	 */
	public String getCountryCurrencyId() {
		return countryCurrencyId;
	}

	public void setCountryCurrencyId(String countryCurrencyId) throws Exception {
		this.countryCurrencyId = countryCurrencyId;
		setValueFromAccessor("COUNTRY_CURRENCY_ID", countryCurrencyId);
	}

	public String getCountryCode2() {
		return countryCode2;
	}

	public void setCountryCode2(String countryCode2) throws Exception {
		this.countryCode2 = countryCode2;
		setValueFromAccessor("COUNTRY_CODE_2", countryCode2);
	}

	public String getCountryCode3() {
		return countryCode3;
	}

	public void setCountryCode3(String countryCode3) throws Exception {
		this.countryCode3 = countryCode3;
		setValueFromAccessor("COUNTRY_CODE_3", countryCode3);
	}

	public String getCountryLanguageCode() {
		return countryLanguageCode;
	}

	public void setCountryLanguageCode(String countryLanguageCode) throws Exception {
		this.countryLanguageCode = countryLanguageCode;
		setValueFromAccessor("COUNTRY_LANGUAGE_CODE", countryLanguageCode);
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) throws Exception {
		this.countryName = countryName;
		setValueFromAccessor("COUNTRY_NAME", countryName);
	}

	public String getCountryNumericCode() {
		return countryNumericCode;
	}

	public void setCountryNumericCode(String countryNumericCode) throws Exception {
		this.countryNumericCode = countryNumericCode;
		setValueFromAccessor("COUNTRY_NUMERIC_CODE", countryNumericCode);
	}

	public String getCurrencyAlphabeticCode() {
		return currencyAlphabeticCode;
	}

	public void setCurrencyAlphabeticCode(String currencyAlphabeticCode) throws Exception {
		this.currencyAlphabeticCode = currencyAlphabeticCode;
		setValueFromAccessor("CURRENCY_ALPHABETIC_CODE", currencyAlphabeticCode);
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) throws Exception {
		this.currencyName = currencyName;
		setValueFromAccessor("CURRENCY_NAME", currencyName);
	}

	public String getCurrencyNumericCode() {
		return currencyNumericCode;
	}

	public void setCurrencyNumericCode(String currencyNumericCode) throws Exception {
		this.currencyNumericCode = currencyNumericCode;
		setValueFromAccessor("CURRENCY_NUMERIC_CODE", currencyNumericCode);
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) throws Exception {
		this.currencySymbol = currencySymbol;
		setValueFromAccessor("CURRENCY_SYMBOL", currencySymbol);
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) throws Exception {
		this.insertDate = insertDate;
		setValueFromAccessor("INSERT_DATE", CommonUtil.toString(insertDate));
	}

	public String getInsertUserId() {
		return insertUserId;
	}

	public void setInsertUserId(String insertUserId) throws Exception {
		this.insertUserId = insertUserId;
		setValueFromAccessor("INSERT_USER_ID", insertUserId);
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) throws Exception {
		this.updateDate = updateDate;
		setValueFromAccessor("UPDATE_DATE", CommonUtil.toString(updateDate));
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) throws Exception {
		this.updateUserId = updateUserId;
		setValueFromAccessor("UPDATE_USER_ID", updateUserId);
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

		str += "countryCurrencyId : "+countryCurrencyId+"\n";
		str += "countryCode2 : "+countryCode2+"\n";
		str += "countryCode3 : "+countryCode3+"\n";
		str += "countryLanguageCode : "+countryLanguageCode+"\n";
		str += "countryName : "+countryName+"\n";
		str += "countryNumericCode : "+countryNumericCode+"\n";
		str += "currencyAlphabeticCode : "+currencyAlphabeticCode+"\n";
		str += "currencyName : "+currencyName+"\n";
		str += "currencyNumericCode : "+currencyNumericCode+"\n";
		str += "currencySymbol : "+currencySymbol+"\n";
		str += "insertDate : "+insertDate+"\n";
		str += "insertUserId : "+insertUserId+"\n";
		str += "updateDate : "+updateDate+"\n";
		str += "updateUserId : "+updateUserId+"\n";
		str += "insertUserName : "+insertUserName+"\n";
		str += "updateUserName : "+updateUserName+"\n";

		return str;
	}

	/**
	 * toXmlString
	 */
	public String toXmlString() {
		String str = "";

		str += "<column name=\"countryCurrencyId\" value=\""+countryCurrencyId+"\">";
		str += "<column name=\"countryCode2\" value=\""+countryCode2+"\">";
		str += "<column name=\"countryCode3\" value=\""+countryCode3+"\">";
		str += "<column name=\"countryLanguageCode\" value=\""+countryLanguageCode+"\">";
		str += "<column name=\"countryName\" value=\""+countryName+"\">";
		str += "<column name=\"countryNumericCode\" value=\""+countryNumericCode+"\">";
		str += "<column name=\"currencyAlphabeticCode\" value=\""+currencyAlphabeticCode+"\">";
		str += "<column name=\"currencyName\" value=\""+currencyName+"\">";
		str += "<column name=\"currencyNumericCode\" value=\""+currencyNumericCode+"\">";
		str += "<column name=\"currencySymbol\" value=\""+currencySymbol+"\">";
		str += "<column name=\"insertDate\" value=\""+insertDate+"\">";
		str += "<column name=\"insertUserId\" value=\""+insertUserId+"\">";
		str += "<column name=\"updateDate\" value=\""+updateDate+"\">";
		str += "<column name=\"updateUserId\" value=\""+updateUserId+"\">";
		str += "<column name=\"insertUserName\" value=\""+insertUserName+"\">";
		str += "<column name=\"updateUserName\" value=\""+updateUserName+"\">";

		return str;
	}

	/**
	 * toJsonString
	 */
	public String toJsonString() {
		String str = "";

		str += "\"countryCurrencyId\":\""+countryCurrencyId+"\", ";
		str += "\"countryCode2\":\""+countryCode2+"\", ";
		str += "\"countryCode3\":\""+countryCode3+"\", ";
		str += "\"countryLanguageCode\":\""+countryLanguageCode+"\", ";
		str += "\"countryName\":\""+countryName+"\", ";
		str += "\"countryNumericCode\":\""+countryNumericCode+"\", ";
		str += "\"currencyAlphabeticCode\":\""+currencyAlphabeticCode+"\", ";
		str += "\"currencyName\":\""+currencyName+"\", ";
		str += "\"currencyNumericCode\":\""+currencyNumericCode+"\", ";
		str += "\"currencySymbol\":\""+currencySymbol+"\", ";
		str += "\"insertDate\":\""+insertDate+"\", ";
		str += "\"insertUserId\":\""+insertUserId+"\", ";
		str += "\"updateDate\":\""+updateDate+"\", ";
		str += "\"updateUserId\":\""+updateUserId+"\", ";
		str += "\"insertUserName\":\""+insertUserName+"\", ";
		str += "\"updateUserName\":\""+updateUserName+"\"";

		return str;
	}
}