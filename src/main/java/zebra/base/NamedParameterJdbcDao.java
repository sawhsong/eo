package zebra.base;

import java.util.Locale;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.data.QueryAdvisor;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class NamedParameterJdbcDao extends NamedParameterJdbcDaoSupport {
	protected Logger logger = LogManager.getLogger(this.getClass());
	protected MessageSourceAccessor messageSourceAccessor;
	protected DataFieldMaxValueIncrementer incrementer;
	protected SqlSession sqlSession;
	protected boolean isMultipleDatasource = false;
	protected String dataSourceName = "";

	public void init() {
	}

	public void setIncrementer(DataFieldMaxValueIncrementer incrementer) {
		this.incrementer = incrementer;
	}

	protected DataFieldMaxValueIncrementer getIncrementer() {
		return this.incrementer;
	}

	public void setMessageSourceAccessor(MessageSourceAccessor newMessageSourceAccessor) {
		this.messageSourceAccessor = newMessageSourceAccessor;
	}

	protected MessageSourceAccessor getMessageSourceAccessor() {
		return this.messageSourceAccessor;
	}

	protected SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public boolean isMultipleDatasource() {
		return isMultipleDatasource;
	}

	public void setMultipleDatasource(boolean isMultipleDatasource) {
		this.isMultipleDatasource = isMultipleDatasource;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		setMultipleDatasource(true);
		this.dataSourceName = dataSourceName;
	}

	public void resetDataSourceName() {
		setMultipleDatasource(false);
		this.dataSourceName = "";
	}

	/*!
	 * getMessage()
	 */
	protected String getMessage(String messageCode) {
		return messageSourceAccessor.getMessage(messageCode, new Locale(ConfigUtil.getProperty("etc.default.language")));
	}

	protected String getMessage(String messageCode, Locale locale) {
		return messageSourceAccessor.getMessage(messageCode, locale);
	}

	protected String getMessage(String messageCode, String languageCode) {
		return messageSourceAccessor.getMessage(messageCode, new Locale(languageCode));
	}

	protected String getMessage(String messageCode, ParamEntity paramEntity) {
		String lang = (String)paramEntity.getSession().getAttribute("langCode");
		return messageSourceAccessor.getMessage(messageCode, new Locale(CommonUtil.nvl(lang, CommonUtil.lowerCase(ConfigUtil.getProperty("etc.default.language")))));
	}

	/**
	 * Protected Methods
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Map setMapFromDto(Map paramMap, Dto dto) throws Exception {
		DataSet fieldDataSet = dto.getDataSet();
		DataSet updateColumnDataSet = dto.getUpdateColumnsDataSet();
		String columnsToUpdateString = CommonUtil.remove(CommonUtil.remove(ConfigUtil.getProperty("db.constants.columnsToUpdateString"), "${"), "}");
		String sql = "", numberColumn = "", dateColumn = "";

		if (!(fieldDataSet == null || fieldDataSet.getRowCnt() <= 0)) {
			numberColumn = dto.getFrwVarNumberColumn();
			dateColumn = dto.getFrwVarDateColumn();

			for (int i=0; i<fieldDataSet.getColumnCnt(); i++) {
				String columnName = fieldDataSet.getName(i);

				if (CommonUtil.containsIgnoreCase(numberColumn, columnName)) {
					paramMap.put(columnName, CommonUtil.nvl(fieldDataSet.getValue(columnName), "null"));
				} else if (CommonUtil.containsIgnoreCase(dateColumn, columnName)) {
					if (CommonUtil.equalsIgnoreCase(fieldDataSet.getValue(columnName), "sysdate")) {
						paramMap.put(columnName, "TO_DATE('"+CommonUtil.getSysdate(ConfigUtil.getProperty("format.default.dateTime"))+"', '"+ConfigUtil.getProperty("format.dateTime.db")+"')");
					} else if (CommonUtil.isBlank(fieldDataSet.getValue(columnName))) {
						paramMap.put(columnName, "null");
					} else {
						paramMap.put(columnName, "TO_DATE('"+fieldDataSet.getValue(columnName)+"', '"+ConfigUtil.getProperty("format.dateTime.db")+"')");
					}
				} else {
					paramMap.put(columnName, "'"+fieldDataSet.getValue(columnName)+"'");
				}
			}
		}

		if (!(updateColumnDataSet == null || updateColumnDataSet.getRowCnt() <= 0)) {
			for (int i=0; i<updateColumnDataSet.getRowCnt(); i++) {
				if (CommonUtil.isEmpty(sql)) {
					if (CommonUtil.equalsIgnoreCase(updateColumnDataSet.getValue(i, "DATA_TYPE"), "String")) {
						sql += updateColumnDataSet.getValue(i, "COLUMN_NAME")+" = '"+updateColumnDataSet.getValue(i, "COLUMN_VALUE")+"'";
					} else if (CommonUtil.equalsIgnoreCase(updateColumnDataSet.getValue(i, "DATA_TYPE"), "Date")) {
						if (CommonUtil.isValidDateString(updateColumnDataSet.getValue(i, "COLUMN_VALUE"))) {
							sql += updateColumnDataSet.getValue(i, "COLUMN_NAME")+" = TO_DATE('"+updateColumnDataSet.getValue(i, "COLUMN_VALUE")+"', '"+ConfigUtil.getProperty("format.dateTime.db")+"')";
						} else {
							sql += updateColumnDataSet.getValue(i, "COLUMN_NAME")+" = "+updateColumnDataSet.getValue(i, "COLUMN_VALUE");
						}
					} else {
						sql += updateColumnDataSet.getValue(i, "COLUMN_NAME")+" = "+updateColumnDataSet.getValue(i, "COLUMN_VALUE");
					}
				} else {
					if (CommonUtil.equalsIgnoreCase(updateColumnDataSet.getValue(i, "DATA_TYPE"), "String")) {
						sql += ",\n       "+updateColumnDataSet.getValue(i, "COLUMN_NAME")+" = '"+updateColumnDataSet.getValue(i, "COLUMN_VALUE")+"'";
					} else if (CommonUtil.equalsIgnoreCase(updateColumnDataSet.getValue(i, "DATA_TYPE"), "Date")) {
						if (CommonUtil.isValidDateString(updateColumnDataSet.getValue(i, "COLUMN_VALUE"))) {
							sql += ",\n       "+updateColumnDataSet.getValue(i, "COLUMN_NAME")+" = TO_DATE('"+updateColumnDataSet.getValue(i, "COLUMN_VALUE")+"', '"+ConfigUtil.getProperty("format.dateTime.db")+"')";
						} else {
							sql += ",\n       "+updateColumnDataSet.getValue(i, "COLUMN_NAME")+" = "+updateColumnDataSet.getValue(i, "COLUMN_VALUE");
						}
					} else {
						sql += ",\n       "+updateColumnDataSet.getValue(i, "COLUMN_NAME")+" = "+updateColumnDataSet.getValue(i, "COLUMN_VALUE");
					}
				}
			}

			paramMap.put(columnsToUpdateString, sql);
		}

		return paramMap;
	}

	@SuppressWarnings("rawtypes")
	protected boolean isValidWhereClause(Map paramMap) throws Exception {
		String whereClauseString = CommonUtil.remove(CommonUtil.remove(ConfigUtil.getProperty("db.constants.whereString"), "${"), "}");

		if (paramMap.containsKey(whereClauseString)) {
			return true;
		} else {
			return false;
		}
	}

	protected void setPagination(QueryAdvisor queryAdvisor) throws Exception {
		int currentPage = queryAdvisor.getCurrentPage();
		int maxRowsPerPage = queryAdvisor.getMaxRowsPerPage();
		int totalPages = ((queryAdvisor.getTotalResultRows() - 1) / maxRowsPerPage) + 1;

		totalPages = (totalPages == 0) ? 1 : totalPages;

		if (currentPage <= 0) {
			currentPage = 1;
		}
		currentPage = (currentPage > totalPages) ? totalPages : currentPage;

		queryAdvisor.addVariable("startRow", CommonUtil.toString((currentPage - 1) * maxRowsPerPage + 0));
		queryAdvisor.addVariable("endRow", CommonUtil.toString(maxRowsPerPage * currentPage));
	}
}