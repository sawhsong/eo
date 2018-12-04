/**************************************************************************************************
 * Hibernate DAO
 * - There are couple of deprecated classes and methods in version 5.2.6
 * - Use Hibernate 5.1.4(Important)
 * - Below is just for referrence purpose in case of using Version 5.2.x
	DataSet ds = session.doReturningWork(new ReturningWork<DataSet>() {
		public DataSet execute(Connection connection) throws SQLException {
			PreparedStatement pstmt = null;
			ResultSetMetaData rsmd = null;
			DataSet dataSet = new DataSet();
			try {
				pstmt = connection.prepareStatement(HibernateQueryManager.getNamedNativeQuery(queryName));
				rsmd = pstmt.getMetaData();
				int cnt = rsmd.getColumnCount();
	
				for (int i=0; i<cnt; i++) {
					String name = rsmd.getColumnName(i+1);
					dataSet.addName(name);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					pstmt.close();
				}
			}
			return dataSet;
		}
	});
	List rs = query.getResultList();
	
	for (int i=0; i<rs.size(); i++) {
		Object row[] = (Object[])rs.get(i);
		ds.addRow();
		for (int j=0; j<row.length; j++) {
			ds.setValue(ds.getRowCnt()-1, ds.getName(j), row[j]);
		}
	}
 *************************************************************************************************/
package zebra.base;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.data.QueryAdvisor;
import zebra.exception.FrameworkException;
import zebra.util.BeanHelper;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class HDao extends HibernateDaoSupport {
	protected Logger logger = LogManager.getLogger(this.getClass());
	protected MessageSourceAccessor messageSourceAccessor;
	protected Session session;
	protected Query<?> query;
	protected boolean isMultipleDatasource = false;
	protected String dataSourceName = "";

	public void init() {
	}

	/**
	 * Accessors
	 */
	protected MessageSourceAccessor getMessageSourceAccessor() {
		return messageSourceAccessor;
	}

	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		this.messageSourceAccessor = messageSourceAccessor;
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
	/**
	 * Messages
	 */
	protected String getMessage(String messageCode) {
		return messageSourceAccessor.getMessage(messageCode, new Locale(CommonUtil.lowerCase(ConfigUtil.getProperty("etc.default.language"))));
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
	 * Protected methods
	 */
	/*!
	 * Select
	 */
	protected DataSet selectAsDataSet(QueryAdvisor queryAdvisor, String queryName) throws Exception {
		setSessionFactoryForMultiDatasource();
		setNamedSQLQuery(queryName);
		return selectAsDataSet(queryAdvisor);
	}

	protected DataSet selectAllAsDataSet(QueryAdvisor queryAdvisor, Dto dto) throws Exception {
		setSessionFactoryForMultiDatasource();
		setNamedSQLQuery(getCamelCaseClassName(dto.getClass().getSimpleName())+".selectAll");
		return selectAsDataSet(queryAdvisor, dto);
	}

	protected Dto selectAllToDto(QueryAdvisor queryAdvisor, Dto dto) throws Exception {
		setSessionFactoryForMultiDatasource();
		dto.setValues(selectAllAsDataSet(queryAdvisor, dto));
		return dto;
	}

	protected DataSet selectAsDataSetBySQLQuery(String sqlQuery) throws Exception {
		setSessionFactoryForMultiDatasource();
		query = session.createSQLQuery(sqlQuery);
		return new DataSet(query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list());
	}

	/*!
	 * Insert
	 */
	protected int insertWithSQLQuery(Dto dto) throws Exception {
		setSessionFactoryForMultiDatasource();
		setNamedSQLQuery(getCamelCaseClassName(dto.getClass().getSimpleName())+".insert");
		setColumnValues(dto);
		return query.executeUpdate();
	}

	protected int insertWithDto(Dto dto) throws Exception {
		try {
			setSessionFactoryForMultiDatasource();
			Object object = session.save(dto);
			return (object == null) || (CommonUtil.isBlank(object.toString())) ? 0 : 1;
		} catch (Exception ex) {
			this.logger.error(ex);
			return 0;
		}
	}

	/*!
	 * Update
	 */
	/**
	 * When update all columns - this uses all columns
	 * @param queryAdvisor
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	protected int updateWithSQLQuery(QueryAdvisor queryAdvisor, Dto dto) throws Exception {
		DataSet dsWhereClause = queryAdvisor.getWhereClauseDataSet();

		if (dsWhereClause == null || dsWhereClause.getRowCnt() <= 0) {
			throw new FrameworkException("E906", getMessage("E906"));
		}

		setSessionFactoryForMultiDatasource();
		setNamedSQLQuery(getCamelCaseClassName(dto.getClass().getSimpleName())+".update");
		setColumnValues(dto);
		setAutoFill(queryAdvisor);
		setWhereClauseVariables(queryAdvisor);
		return query.executeUpdate();
	}

	protected int updateWithSQLQuery(QueryAdvisor queryAdvisor, String queryName) throws Exception {
		DataSet dsWhereClause = queryAdvisor.getWhereClauseDataSet();

		if (dsWhereClause == null || dsWhereClause.getRowCnt() <= 0) {
			throw new FrameworkException("E906", getMessage("E906"));
		}

		setSessionFactoryForMultiDatasource();
		setNamedSQLQuery(queryName);
		setAutoFill(queryAdvisor);
		setVariables(queryAdvisor);
		setWhereClauseVariables(queryAdvisor);
		return query.executeUpdate();
	}

	/**
	 * DTO must have primary key column value
	 * @param queryAdvisor
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	protected int updateWithDto(QueryAdvisor queryAdvisor, Dto dto) throws Exception {
		DataSet dsWhereClause = queryAdvisor.getWhereClauseDataSet();

		try {
			if (dsWhereClause == null || dsWhereClause.getRowCnt() <= 0) {
				throw new FrameworkException("E906", getMessage("E906"));
			}

			setSessionFactoryForMultiDatasource();
			session.update(dto);
			return 1;
		} catch (Exception ex) {
			this.logger.error(ex);
			return 0;
		}
	}

	/**
	 * When update particular columns - addUpdateColumnFromField() method must be called for this
	 * @param queryAdvisor
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	protected int updateColumns(QueryAdvisor queryAdvisor, Dto dto) throws Exception {
		DataSet dsWhereClause = queryAdvisor.getWhereClauseDataSet();

		if (dsWhereClause == null || dsWhereClause.getRowCnt() <= 0) {
			throw new FrameworkException("E906", getMessage("E906"));
		}

		setSessionFactoryForMultiDatasource();
		setNamedSQLQuery(getCamelCaseClassName(dto.getClass().getSimpleName())+".updateColumns");
		return executeUpdateColumnQuery(queryAdvisor, dto);
	}

	/*!
	 * Delete
	 */
	protected int deleteWithSQLQuery(QueryAdvisor queryAdvisor, Dto dto) throws Exception {
		DataSet dsWhereClause = queryAdvisor.getWhereClauseDataSet();

		if (dsWhereClause == null || dsWhereClause.getRowCnt() <= 0) {
			throw new FrameworkException("E906", getMessage("E906"));
		}

		setSessionFactoryForMultiDatasource();
		setNamedSQLQuery(getCamelCaseClassName(dto.getClass().getSimpleName())+".delete");
		setAutoFill(queryAdvisor);
		setWhereClauseVariables(queryAdvisor);
		return query.executeUpdate();
	}

	protected int deleteWithSQLQuery(QueryAdvisor queryAdvisor, String queryName) throws Exception {
		DataSet dsWhereClause = queryAdvisor.getWhereClauseDataSet();

		if (dsWhereClause == null || dsWhereClause.getRowCnt() <= 0) {
			throw new FrameworkException("E906", getMessage("E906"));
		}

		setSessionFactoryForMultiDatasource();
		setNamedSQLQuery(queryName);
		setAutoFill(queryAdvisor);
		setVariables(queryAdvisor);
		setWhereClauseVariables(queryAdvisor);
		return query.executeUpdate();
	}

	protected int deleteWithDto(QueryAdvisor queryAdvisor, Dto dto) throws Exception {
		DataSet dsWhereClause = queryAdvisor.getWhereClauseDataSet();

		try {
			if (dsWhereClause == null || dsWhereClause.getRowCnt() <= 0) {
				throw new FrameworkException("E906", getMessage("E906"));
			}

			setSessionFactoryForMultiDatasource();
			session.delete(dto);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * DDL
	 */
	protected int executeSql(String sql) throws Exception {
		if (CommonUtil.isBlank(sql)) {
			throw new FrameworkException("E801", getMessage("E801"));
		}

		setSessionFactoryForMultiDatasource();
		query = session.createSQLQuery(sql);
		return query.executeUpdate();
	}

	/**
	 * Private methods
	 */
	private void setSessionFactoryForMultiDatasource() throws Exception {
		if (CommonUtil.equals(ConfigUtil.getProperty("jdbc.isMultiDatasource"), "Y")) {
			if (isMultipleDatasource() && CommonUtil.isNotEmpty(getDataSourceName())) {
				setSessionFactory((SessionFactory)BeanHelper.getBean("hibernateLocalSessionFactory_"+getDataSourceName()));
			} else {
				setSessionFactory((SessionFactory)BeanHelper.getBean("hibernateLocalSessionFactory"));
			}
		}
		loadSession();
	}

	private void loadSession() throws Exception {
		try {
			session = getSessionFactory().getCurrentSession();
		} catch (Exception ex) {
			if (session == null || !session.isOpen()) {
				session = getSessionFactory().openSession();
			}
		}
	}

	private void setNamedSQLQuery(String queryName) throws Exception {
		query = getNamedQuery(queryName);
	}

	private Query<?> getNamedQuery(String queryName) throws Exception {
		if (CommonUtil.isBlank(queryName)) {
			throw new FrameworkException("E901", getMessage("E901"));
		}
		return session.getNamedQuery(queryName);
	}

	private void setAutoFill(QueryAdvisor queryAdvisor) throws Exception {
		String autoFillString = ConfigUtil.getProperty("db.constants.autoFillString");
		String queryString = getQueryString(), sql = "";
		DataSet dsAutoFill = queryAdvisor.getAutoFillDataSet();

		if (CommonUtil.containsIgnoreCase(queryString, autoFillString)) {
			for (int i=0; i<dsAutoFill.getRowCnt(); i++) {
				String value = dsAutoFill.getValue(i, "VALUE");
				if (CommonUtil.isNotBlank(value)) {
					sql += "   and "+dsAutoFill.getValue(i, "SQL_SENTENCE")+"\n";
				}
			}
			modifyQuery(autoFillString, sql);
		}
	}

	private void setVariables(QueryAdvisor queryAdvisor) throws Exception {
		DataSet dsVariable = queryAdvisor.getVariableDataSet();

		for (int i=0; i<dsVariable.getRowCnt(); i++) {
			String variableName = dsVariable.getValue(i, "VARIABLE_NAME");
			if (CommonUtil.isNotBlank(variableName)) {
				modifyQuery("${"+variableName+"}", dsVariable.getValue(i, "VARIABLE_VALUE"));
			}
		}
	}

	private void setWhereClauseVariables(QueryAdvisor queryAdvisor) throws Exception {
		String systemWhereString = ConfigUtil.getProperty("db.constants.whereString");
		String userWhereString = ConfigUtil.getProperty("db.constants.whereClauseString");
		DataSet dsWhereClause = queryAdvisor.getWhereClauseDataSet();
		String sql = "";

		if (dsWhereClause != null && dsWhereClause.getRowCnt() > 0) {
			for (int i=0; i<dsWhereClause.getRowCnt(); i++) {
				sql += "   and "+dsWhereClause.getValue(i, "WHERE_CLAUSE");
			}
			modifyQuery(systemWhereString, sql);

			sql = "";
			for (int i=0; i<dsWhereClause.getRowCnt(); i++) {
				sql += "   and "+dsWhereClause.getValue(i, "WHERE_CLAUSE");
			}
			modifyQuery(userWhereString, sql);
		} else {
			modifyQuery(systemWhereString, "");
			modifyQuery(userWhereString, "");
		}
	}

	private void setOrderByClauseVariables(QueryAdvisor queryAdvisor) throws Exception {
		String systemOrderByString = ConfigUtil.getProperty("db.constants.orderByString");
		String userOrderByString = ConfigUtil.getProperty("db.constants.orderByClauseString");
		DataSet dsOrderByClause = queryAdvisor.getOrderByClauseDataSet();
		String sql = "";

		if (dsOrderByClause != null && dsOrderByClause.getRowCnt() > 0) {
			for (int i=0; i<dsOrderByClause.getRowCnt(); i++) {
				sql += (CommonUtil.isBlank(sql)) ? " order by "+dsOrderByClause.getValue(i, "ORDER_BY_CLAUSE") : " ,"+dsOrderByClause.getValue(i, "ORDER_BY_CLAUSE");
			}
			modifyQuery(systemOrderByString, sql);

			sql = "";
			for (int i=0; i<dsOrderByClause.getRowCnt(); i++) {
				sql += (CommonUtil.isBlank(sql)) ? " order by "+dsOrderByClause.getValue(i, "ORDER_BY_CLAUSE") : " ,"+dsOrderByClause.getValue(i, "ORDER_BY_CLAUSE");
			}
			modifyQuery(userOrderByString, sql);
		} else {
			modifyQuery(systemOrderByString, "");
			modifyQuery(userOrderByString, "");
		}
	}

	private DataSet selectAsDataSet(QueryAdvisor queryAdvisor, Dto dto) throws Exception {
		DataSet dataSet;

		setAutoFill(queryAdvisor);
		setWhereClauseVariables(queryAdvisor);
		setOrderByClauseVariables(queryAdvisor);

		dataSet = new DataSet(query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list());
		queryAdvisor.setTotalResultRows(dataSet.getRowCnt());

		if (queryAdvisor.isPagination()) {
			setPagination(queryAdvisor);
			dataSet = new DataSet(query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list());
		}

		return dataSet;
	}

	private DataSet selectAsDataSet(QueryAdvisor queryAdvisor) throws Exception {
		DataSet dataSet;

		setAutoFill(queryAdvisor);
		setVariables(queryAdvisor);
		setWhereClauseVariables(queryAdvisor);
		setOrderByClauseVariables(queryAdvisor);

		dataSet = new DataSet(query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list());
		queryAdvisor.setTotalResultRows(dataSet.getRowCnt());

		if (queryAdvisor.isPagination()) {
			setPagination(queryAdvisor);
			dataSet = new DataSet(query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list());
		}

		return dataSet;
	}

	private int executeUpdateColumnQuery(QueryAdvisor queryAdvisor, Dto dto) throws Exception {
		String columnsToUpdateString = ConfigUtil.getProperty("db.constants.columnsToUpdateString");
		String sql = "";
		DataSet dsUpdateColumns = dto.getUpdateColumnsDataSet();

		if (dsUpdateColumns != null && dsUpdateColumns.getRowCnt() > 0) {
			for (int i=0; i<dsUpdateColumns.getRowCnt(); i++) {
				if (CommonUtil.isBlank(sql)) {
					if (CommonUtil.equalsIgnoreCase(dsUpdateColumns.getValue(i, "DATA_TYPE"), "String")) {
						sql += dsUpdateColumns.getValue(i, "COLUMN_NAME")+" = '"+dsUpdateColumns.getValue(i, "COLUMN_VALUE")+"'";
					} else if (CommonUtil.equalsIgnoreCase(dsUpdateColumns.getValue(i, "DATA_TYPE"), "Date")) {
						if (CommonUtil.isValidDateString(dsUpdateColumns.getValue(i, "COLUMN_VALUE"))) {
							sql += dsUpdateColumns.getValue(i, "COLUMN_NAME")+" = TO_DATE('"+dsUpdateColumns.getValue(i, "COLUMN_VALUE")+"', '"+ConfigUtil.getProperty("format.dateTime.db")+"')";
						} else {
							sql += dsUpdateColumns.getValue(i, "COLUMN_NAME")+" = "+dsUpdateColumns.getValue(i, "COLUMN_VALUE");
						}
					} else {
						sql += dsUpdateColumns.getValue(i, "COLUMN_NAME")+" = "+dsUpdateColumns.getValue(i, "COLUMN_VALUE");
					}
				} else {
					if (CommonUtil.equalsIgnoreCase(dsUpdateColumns.getValue(i, "DATA_TYPE"), "String")) {
						sql += ",\n       "+dsUpdateColumns.getValue(i, "COLUMN_NAME")+" = '"+dsUpdateColumns.getValue(i, "COLUMN_VALUE")+"'";
					} else if (CommonUtil.equalsIgnoreCase(dsUpdateColumns.getValue(i, "DATA_TYPE"), "Date")) {
						if (CommonUtil.isValidDateString(dsUpdateColumns.getValue(i, "COLUMN_VALUE"))) {
							sql += ",\n       "+dsUpdateColumns.getValue(i, "COLUMN_NAME")+" = TO_DATE('"+dsUpdateColumns.getValue(i, "COLUMN_VALUE")+"', '"+ConfigUtil.getProperty("format.dateTime.db")+"')";
						} else {
							sql += ",\n       "+dsUpdateColumns.getValue(i, "COLUMN_NAME")+" = "+dsUpdateColumns.getValue(i, "COLUMN_VALUE");
						}
					} else {
						sql += ",\n       "+dsUpdateColumns.getValue(i, "COLUMN_NAME")+" = "+dsUpdateColumns.getValue(i, "COLUMN_VALUE");
					}
				}
			}

			modifyQuery(columnsToUpdateString, sql);
		} else {
			modifyQuery(columnsToUpdateString, "");
		}

		setWhereClauseVariables(queryAdvisor);

		return query.executeUpdate();
	}

	private void setColumnValues(Dto dto) throws Exception {
		DataSet columnDataSet = dto.getDataSet();
		String columnName = "", numberColumn = "", dateColumn = "";

		if (columnDataSet != null && columnDataSet.getColumnCnt() > 0) {
			numberColumn = dto.getFrwVarNumberColumn();
			dateColumn = dto.getFrwVarDateColumn();

			for (int i=0; i<columnDataSet.getColumnCnt(); i++) {
				columnName = columnDataSet.getName(i);

				if (CommonUtil.containsIgnoreCase(numberColumn, columnName)) {
					modifyQuery("${"+columnName+"}", CommonUtil.nvl(columnDataSet.getValue(columnName), "null"));
				} else if (CommonUtil.containsIgnoreCase(dateColumn, columnName)) {
					if (CommonUtil.equalsIgnoreCase(columnDataSet.getValue(columnName), "sysdate")) {
						modifyQuery("${"+columnName+"}", "TO_DATE('"+CommonUtil.getSysdate(ConfigUtil.getProperty("format.default.dateTime"))+"', '"+ConfigUtil.getProperty("format.dateTime.db")+"')");
					} else if (CommonUtil.isBlank(columnDataSet.getValue(columnName))) {
						modifyQuery("${"+columnName+"}", "null");
					} else {
						modifyQuery("${"+columnName+"}", "TO_DATE('"+columnDataSet.getValue(columnName)+"', '"+ConfigUtil.getProperty("format.dateTime.db")+"')");
					}
				} else {
					modifyQuery("${"+columnName+"}", "'"+columnDataSet.getValue(columnName)+"'");
				}
			}
		}
	}

	private void checkQueryObject() throws Exception {
		if (query == null) {
			throw new FrameworkException("E902", getMessage("E902"));
		}
	}

	private String getQueryString() throws Exception {
		checkQueryObject();
		return query.getQueryString();
	}

	private void modifyQuery(String name, String value) throws Exception {
		query = session.createSQLQuery(CommonUtil.replace(getQueryString(), name, value));
	}

	private void setPagination(QueryAdvisor queryAdvisor) throws Exception {
		int currentPage = queryAdvisor.getCurrentPage();
		int maxRowsPerPage = queryAdvisor.getMaxRowsPerPage();
		int totalPages = ((queryAdvisor.getTotalResultRows() - 1) / maxRowsPerPage) + 1;

		totalPages = (totalPages == 0) ? 1 : totalPages;

		if (currentPage <= 0) {
			currentPage = 1;
		}
		currentPage = (currentPage > totalPages) ? totalPages : currentPage;

		setFirstResult((currentPage - 1) * maxRowsPerPage + 0);
		setMaxResults(maxRowsPerPage);
	}

	private void setFirstResult(int value) throws Exception {
		checkQueryObject();
		query.setFirstResult(value);
	}

	private void setMaxResults(int value) throws Exception {
		checkQueryObject();
		query.setMaxResults(value);
	}

	private String getCamelCaseClassName(String className) {
		String str = "";

		str += CommonUtil.lowerCase(className.substring(0, 1));
		str += className.substring(1);

		return str;
	}
}