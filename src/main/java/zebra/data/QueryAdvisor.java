package zebra.data;

import java.util.HashMap;
import java.util.Map;

import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class QueryAdvisor {
	private HashMap<String, Object> object;
	private boolean isPagination = false;
	private int totalResultRows = 0;
	private int currentPage = 1;
	private int maxRowsPerPage;
	private DataSet requestDataSet;
	private DataSet autoFillDataSet;
	private String autoFillDataSetHeader[] = {"VALUE", "SQL_SENTENCE"};
	private DataSet variableDataSet;
	private String variableDataSetHeader[] = {"VARIABLE_NAME", "VARIABLE_VALUE"};
	private DataSet whereClauseDataSet;
	private String whereClauseDataSetHeader[] = {"INDEX", "WHERE_CLAUSE"};
	private DataSet orderByClauseDataSet;
	private String orderByClauseDataSetHeader[] = {"INDEX", "ORDER_BY_CLAUSE"};

	/**
	 * Constructor
	 */
	public QueryAdvisor() {
		requestDataSet = new DataSet();

		autoFillDataSet = new DataSet();
		autoFillDataSet.addName(autoFillDataSetHeader);

		variableDataSet = new DataSet();
		variableDataSet.addName(variableDataSetHeader);

		whereClauseDataSet = new DataSet();
		whereClauseDataSet.addName(whereClauseDataSetHeader);

		orderByClauseDataSet = new DataSet();
		orderByClauseDataSet.addName(orderByClauseDataSetHeader);
	}

	/**
	 * Accessor
	 */
	public boolean isPagination() {
		return isPagination;
	}

	public void setPagination(boolean isPagination) {
		this.isPagination = isPagination;
	}

	public int getTotalResultRows() {
		return totalResultRows;
	}

	public void setTotalResultRows(int totalResultRows) {
		this.totalResultRows = totalResultRows;
	}

	public DataSet getAutoFillDataSet() {
		return autoFillDataSet;
	}

	public void setAutoFillDataSet(DataSet autoFillDataSet) {
		this.autoFillDataSet = autoFillDataSet;
	}

	public DataSet getVariableDataSet() {
		return variableDataSet;
	}

	public void setVariableDataSet(DataSet variableDataSet) {
		this.variableDataSet = variableDataSet;
	}

	public DataSet getWhereClauseDataSet() {
		return whereClauseDataSet;
	}

	public void setWhereClauseDataSet(DataSet whereClauseDataSet) {
		this.whereClauseDataSet = whereClauseDataSet;
	}

	public DataSet getOrderByClauseDataSet() {
		return orderByClauseDataSet;
	}

	public void setOrderByClauseDataSet(DataSet orderByClauseDataSet) {
		this.orderByClauseDataSet = orderByClauseDataSet;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getMaxRowsPerPage() {
		return maxRowsPerPage;
	}

	public void setMaxRowsPerPage(int maxRowsPerPage) {
		this.maxRowsPerPage = maxRowsPerPage;
	}

	public void setRequestDataSet(DataSet requestDataSet) {
		this.requestDataSet = requestDataSet;
	}

	public DataSet getRequestDataSet() {
		return requestDataSet;
	}

	public Object getObject(String key) {
		if (object == null || CommonUtil.isBlank(key)) {return null;}
		if (!object.containsKey(key)) {return null;}
		return object.get(key);
	}

	public void setObject(String key, Object obj) {
		if (CommonUtil.isBlank(key) || obj == null) {return;}
		if (object == null) {object = new HashMap<String, Object>();}
		object.put(key, obj);
	}

	public void removeObject(String key) {
		if (object == null || CommonUtil.isBlank(key)) {return;}
		if (!object.containsKey(key)) {return;}
		object.remove(key);
	}

	/**
	 * AutoFill
	 */
	public void addAutoFillCriteria(String value, String sqlSentence) throws Exception {
		autoFillDataSet.addRow();
		autoFillDataSet.setValue(autoFillDataSet.getRowCnt()-1, "VALUE", value);
		autoFillDataSet.setValue(autoFillDataSet.getRowCnt()-1, "SQL_SENTENCE", sqlSentence);
	}

	public void resetAutoFillCriteria() throws Exception {
		autoFillDataSet = new DataSet();
		autoFillDataSet.addName(autoFillDataSetHeader);
	}

	/**
	 * Variable
	 */
	public void addVariable(String variableName, String variableValue) throws Exception {
		variableDataSet.addRow();
		variableDataSet.setValue(variableDataSet.getRowCnt()-1, "VARIABLE_NAME", variableName);
		variableDataSet.setValue(variableDataSet.getRowCnt()-1, "VARIABLE_VALUE", variableValue);
	}

	public void resetVariable() throws Exception {
		variableDataSet = new DataSet();
		variableDataSet.addName(variableDataSetHeader);
	}

	/**
	 * Where clause
	 */
	public void addWhereClause(String whereClause) throws Exception {
		whereClauseDataSet.addRow();
		whereClauseDataSet.setValue(whereClauseDataSet.getRowCnt()-1, "INDEX", CommonUtil.toString(whereClauseDataSet.getRowCnt()-1));
		whereClauseDataSet.setValue(whereClauseDataSet.getRowCnt()-1, "WHERE_CLAUSE", whereClause);
	}

	public void resetWhereClause() throws Exception {
		whereClauseDataSet = new DataSet();
		whereClauseDataSet.addName(whereClauseDataSetHeader);
	}

	/**
	 * Order by clause
	 */
	public void addOrderByClause(String orderByClause) throws Exception {
		orderByClauseDataSet.addRow();
		orderByClauseDataSet.setValue(orderByClauseDataSet.getRowCnt()-1, "INDEX", CommonUtil.toString(orderByClauseDataSet.getRowCnt()-1));
		orderByClauseDataSet.setValue(orderByClauseDataSet.getRowCnt()-1, "ORDER_BY_CLAUSE", orderByClause);
	}

	public void resetOrderByClause() throws Exception {
		orderByClauseDataSet = new DataSet();
		orderByClauseDataSet.addName(orderByClauseDataSetHeader);
	}

	/**
	 * Only for MyBatis
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getQueryAdvisorMap() throws Exception {
		Map map = new HashMap();

		map.putAll(getAutoFillMap());
		map.putAll(getVariableMap());
		map.putAll(getWhereClauseMap());
		map.putAll(getOrderByClauseMap());

		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getAutoFillMap() throws Exception {
		Map map = new HashMap();
		String autoFillString = ConfigUtil.getProperty("db.constants.autoFillString");
		String autoFill = "";

		autoFillString = CommonUtil.remove(CommonUtil.remove(autoFillString, "${"), "}");

		if (!(autoFillDataSet == null || autoFillDataSet.getRowCnt() <= 0)) {
			for (int i=0; i<autoFillDataSet.getRowCnt(); i++) {
				if (CommonUtil.isNotBlank(autoFillDataSet.getValue("VALUE"))) {
					autoFill += (CommonUtil.isBlank(autoFill)) ? "AND "+autoFillDataSet.getValue(i, "SQL_SENTENCE") : "\n\t\t    AND "+autoFillDataSet.getValue(i, "SQL_SENTENCE");
				}
			}

			map.put(autoFillString, autoFill);
		}

		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getVariableMap() throws Exception {
		Map map = new HashMap();

		if (!(variableDataSet == null || variableDataSet.getRowCnt() <= 0)) {
			for (int i=0; i<variableDataSet.getRowCnt(); i++) {
				if (CommonUtil.isNotBlank(variableDataSet.getValue("VARIABLE_NAME"))) {
					map.put(variableDataSet.getValue(i, "VARIABLE_NAME"), variableDataSet.getValue(i, "VARIABLE_VALUE"));
				}
			}
		}

		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getWhereClauseMap() throws Exception {
		Map map = new HashMap();
		String systemWhereString = ConfigUtil.getProperty("db.constants.whereString");
		String userWhereString = ConfigUtil.getProperty("db.constants.whereClauseString");
		String whereClause = "";

		systemWhereString = CommonUtil.remove(CommonUtil.remove(systemWhereString, "${"), "}");
		userWhereString = CommonUtil.remove(CommonUtil.remove(userWhereString, "${"), "}");

		if (!(whereClauseDataSet == null || whereClauseDataSet.getRowCnt() <= 0)) {
			for (int i=0; i<whereClauseDataSet.getRowCnt(); i++) {
				if (CommonUtil.isNotBlank(whereClauseDataSet.getValue("WHERE_CLAUSE"))) {
					whereClause += (CommonUtil.isBlank(whereClause)) ? "AND "+whereClauseDataSet.getValue(i, "WHERE_CLAUSE") : "\n\t\t    AND "+whereClauseDataSet.getValue(i, "WHERE_CLAUSE");
				}
			}
			map.put(systemWhereString, whereClause);

			whereClause = "";
			for (int i=0; i<whereClauseDataSet.getRowCnt(); i++) {
				if (CommonUtil.isNotBlank(whereClauseDataSet.getValue("WHERE_CLAUSE"))) {
					whereClause += (CommonUtil.isBlank(whereClause)) ? "AND "+whereClauseDataSet.getValue(i, "WHERE_CLAUSE") : "\n\t\t    AND "+whereClauseDataSet.getValue(i, "WHERE_CLAUSE");
				}
			}
			map.put(userWhereString, whereClause);
		}

		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getOrderByClauseMap() throws Exception {
		Map map = new HashMap();
		String systemOrderByString = ConfigUtil.getProperty("db.constants.orderByString");
		String userOrderByString = ConfigUtil.getProperty("db.constants.orderByClauseString");
		String orderByClause = "";

		systemOrderByString = CommonUtil.remove(CommonUtil.remove(systemOrderByString, "${"), "}");
		userOrderByString = CommonUtil.remove(CommonUtil.remove(userOrderByString, "${"), "}");

		if (!(orderByClauseDataSet == null || orderByClauseDataSet.getRowCnt() <= 0)) {
			for (int i=0; i<orderByClauseDataSet.getRowCnt(); i++) {
				if (CommonUtil.isNotBlank(orderByClauseDataSet.getValue("ORDER_BY_CLAUSE"))) {
					orderByClause += (CommonUtil.isBlank(orderByClause)) ? "ORDER BY "+orderByClauseDataSet.getValue(i, "ORDER_BY_CLAUSE") : "\n\t\t ,"+orderByClauseDataSet.getValue(i, "ORDER_BY_CLAUSE");
				}
			}
			map.put(systemOrderByString, orderByClause);

			orderByClause = "";
			for (int i=0; i<orderByClauseDataSet.getRowCnt(); i++) {
				if (CommonUtil.isNotBlank(orderByClauseDataSet.getValue("ORDER_BY_CLAUSE"))) {
					orderByClause += (CommonUtil.isBlank(orderByClause)) ? "ORDER BY "+orderByClauseDataSet.getValue(i, "ORDER_BY_CLAUSE") : "\n\t\t ,"+orderByClauseDataSet.getValue(i, "ORDER_BY_CLAUSE");
				}
			}
			map.put(userOrderByString, orderByClause);
		}

		return map;
	}

	public void resetAll() throws Exception {
		resetAutoFillCriteria();
		resetVariable();
		resetWhereClause();
		resetOrderByClause();
	}

	public QueryAdvisor copyQueryAdvisor() throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();

		queryAdvisor.setPagination(this.isPagination());
		queryAdvisor.setTotalResultRows(this.getTotalResultRows());
		queryAdvisor.setCurrentPage(this.getCurrentPage());
		queryAdvisor.setMaxRowsPerPage(this.getMaxRowsPerPage());
		queryAdvisor.setRequestDataSet(this.getRequestDataSet());
		queryAdvisor.setAutoFillDataSet(this.getAutoFillDataSet());
		queryAdvisor.setVariableDataSet(this.getVariableDataSet());
		queryAdvisor.setWhereClauseDataSet(this.getWhereClauseDataSet());
		queryAdvisor.setOrderByClauseDataSet(this.getOrderByClauseDataSet());

		return queryAdvisor;
	}

	/**
	 * toString
	 */
	public String toString() {
		String str = "";

		str += "QueryAdvisor [isPagination] : "+isPagination+"\n";
		str += "QueryAdvisor [totalResultRows] : "+totalResultRows+"\n";
		str += "QueryAdvisor [currentPage] : "+currentPage+"\n";
		str += "QueryAdvisor [maxRowsPerPage] : "+maxRowsPerPage+"\n";
		str += "QueryAdvisor [RequestDataSet]\n" + requestDataSet.toString()+"\n";
		str += "QueryAdvisor [AutoFillDataSet]\n" + autoFillDataSet.toString()+"\n";
		str += "QueryAdvisor [VariableDataSet]\n" + variableDataSet.toString()+"\n";
		str += "QueryAdvisor [WhereClauseDataSet]\n" + whereClauseDataSet.toString()+"\n";
		str += "QueryAdvisor [OrderByClauseDataSet]\n" + orderByClauseDataSet.toString()+"\n";

		return  str;
	}
}