package zebra.base;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import zebra.data.DataSet;

public abstract class Dto {
	protected Logger logger = LogManager.getLogger(this.getClass());

	protected DataSet dataSet = new DataSet();
	protected DataSet updateColumnsDataSet = new DataSet();
	protected String updateColumnsDataSetHeader[] = {"COLUMN_NAME", "COLUMN_VALUE", "DATA_TYPE"};

	private String FRW_VAR_PRIMARY_KEY = "";
	private String FRW_VAR_DATE_COLUMN = "";
	private String FRW_VAR_NUMBER_COLUMN = "";
	private String FRW_VAR_CLOB_COLUMN = "";
	private String FRW_VAR_DEFAULT_COLUMN = "";
	private String FRW_VAR_DEFAULT_VALUE = "";
	@SuppressWarnings("rawtypes")
	private Map additionalAttributesForUpdateWithDto;

	public DataSet getDataSet() throws Exception {
		return dataSet;
	}

	public DataSet getUpdateColumnsDataSet() {
		return updateColumnsDataSet;
	}

	public String getFrwVarPrimaryKey() throws Exception {
		return FRW_VAR_PRIMARY_KEY;
	}

	protected void setFrwVarPrimaryKey(String primaryKey) throws Exception {
		FRW_VAR_PRIMARY_KEY = primaryKey;
	}

	protected String getFrwVarDateColumn() throws Exception {
		return FRW_VAR_DATE_COLUMN;
	}

	protected void setFrwVarDateColumn(String dateColumn) throws Exception {
		FRW_VAR_DATE_COLUMN = dateColumn;
	}

	protected String getFrwVarNumberColumn() throws Exception {
		return FRW_VAR_NUMBER_COLUMN;
	}

	protected void setFrwVarNumberColumn(String numberColumn) throws Exception {
		FRW_VAR_NUMBER_COLUMN = numberColumn;
	}

	protected String getFrwVarClobColumn() throws Exception {
		return FRW_VAR_CLOB_COLUMN;
	}

	protected void setFrwVarClobColumn(String clobColumn) throws Exception {
		FRW_VAR_CLOB_COLUMN = clobColumn;
	}

	protected String getFrwVarDefaultColumn() throws Exception {
		return FRW_VAR_DEFAULT_COLUMN;
	}

	protected void setFrwVarDefaultColumn(String defaultColumn) throws Exception {
		FRW_VAR_DEFAULT_COLUMN = defaultColumn;
	}

	protected String getFrwVarDefaultValue() throws Exception {
		return FRW_VAR_DEFAULT_VALUE;
	}

	protected void setFrwVarDefaultValue(String defaultValue) throws Exception {
		FRW_VAR_DEFAULT_VALUE = defaultValue;
	}

	@SuppressWarnings("rawtypes")
	protected String getAdditionalAttributesForUpdateWithDto() throws Exception {
		String str = "";
		for (Iterator iter = this.additionalAttributesForUpdateWithDto.entrySet().iterator(); iter.hasNext();) {
			Entry entry = (Entry)iter.next();
			String val = (String)entry.getValue();

			str += val+"\n";
		}
		return str;
	}

	@SuppressWarnings("rawtypes")
	protected void setAdditionalAttributesForUpdateWithDto(Map additionalAttributesForUpdateWithDto) throws Exception {
		this.additionalAttributesForUpdateWithDto = additionalAttributesForUpdateWithDto;
	}
	/*!
	 * Override
	 */
	/*
	 * dataSet has only 1 row - DAO.selectAllToDto()
	 */
	public abstract void setValues(DataSet dataSet) throws Exception;
	/*
	 * dataSet has multiple rows - DataSet.getRowAsDto()
	 */
	public abstract void setValues(DataSet dataSet, int rowIndex) throws Exception;
	public abstract String toXmlString();
	public abstract String toJsonString();
}