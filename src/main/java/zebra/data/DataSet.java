package zebra.data;

import java.io.Reader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zebra.base.Dto;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class DataSet {
	private Logger logger = LogManager.getLogger(this.getClass());

	private Map<String, Integer> fieldNameIdx = new HashMap<String, Integer>();
	private List<String> fieldName = new ArrayList<String>();
	@SuppressWarnings("rawtypes")
	private List fieldValue = new ArrayList();

	public DataSet() {
	}

	public DataSet(String names[]) {
		for (int i=0; i<names.length; i++) {
			addName(names[i]);
		}
	}

	@SuppressWarnings("rawtypes")
	public DataSet(List list) throws Exception {
		if (list != null && list.size() > 0) {
			HashMap map = (HashMap)list.get(0);
			for (Iterator ir = map.keySet().iterator(); ir.hasNext();) {
				String key = (String)ir.next();
				this.addName(key);
			}

			for (int i=0; i<list.size(); i++) {
				map = (HashMap)list.get(i);
				this.addRow(this.getColumnCnt());
				for (Iterator ir = map.keySet().iterator(); ir.hasNext();) {
					String key = (String)ir.next();
					this.setValue(i, key, map.get(key));
				}
			}
		}
	}

	public DataSet(Node node) throws Exception {
		Element nodeElement = (Element)node;
		NodeList nodeHeader = nodeElement.getElementsByTagName("header");
		NodeList nodeRows = nodeElement.getElementsByTagName("dataSetRowItems");

		if (nodeHeader != null) {
			for (int i=0; i<nodeHeader.getLength(); i++) {
				Element element = (Element)nodeHeader.item(i);

				addName(element.getAttribute("name"));
			}
		}

		if (nodeRows != null) {
			for (int i=0; i<nodeRows.getLength(); i++) {
				Element element = (Element)nodeRows.item(i);
				NodeList nodeColumns = element.getElementsByTagName("rowItem");

				addRow();
				for (int j=0; j<nodeColumns.getLength(); j++) {
					Element item = (Element)nodeColumns.item(j);

					setValue(getRowCnt()-1, item.getAttribute("name"), item.getAttribute("value"));
				}
			}
		}
	}

	public DataSet(JSONObject jsonObject) throws Exception {
		JSONObject dsValues = ((JSONObject)jsonObject.get("dataSetValue"));
		JSONArray headers = jsonObject.getJSONArray("dataSetHeader");

		for (int i=0; i<headers.size(); i++) {
			addName((String)headers.get(i));
		}
		
		for (Object keys : dsValues.keySet()) {
			String key = (String)keys;
			JSONArray values = dsValues.getJSONArray(key);

			addRow();
			for (int i=0; i<values.size(); i++) {
				setValue(getRowCnt()-1, i, (String)values.get(i));
			}
		}
	}

	public void addName(String name) {
		fieldNameIdx.put(name, Integer.valueOf(fieldName.size()));
		fieldName.add(name);
	}

	public void addName(String name[]) {
		for (int i=0; i<name.length; i++) {
			fieldNameIdx.put(name[i], Integer.valueOf(i));
			fieldName.add(name[i]);
		}
	}

	public void addRow(int value) {
		List<String> row = new ArrayList<String>();
		for (int i=0; i<value; i++) {
			row.add("");
		}
		addRow(row);
	}

	public void addRow() {
		addRow(fieldName.size());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void addRow(List value) {
		fieldValue.add(value);
	}

	public void addRow(ResultSet rs) throws Exception {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCnt = rsmd.getColumnCount();

		while (rs.next()) {
			List<String> row = new ArrayList<String>();

			for (int i=1; i<=columnCnt; i++) {
				if ("CLOB".equals(rsmd.getColumnTypeName(i)) || "BLOB".equals(rsmd.getColumnTypeName(i))) {
					row.add(getLob(rs.getCharacterStream(i)));
				} else {
					row.add(CommonUtil.nvl(rs.getString(i)));
				}
			}
			addRow(row);
		}
	}

	private String getLob(Reader reader) throws Exception {
		StringBuffer output = new StringBuffer();
		char[] buffer = new char[1024];
		int byteRead;
		String sReturn = "";
		
		if (reader != null) {
			while ((byteRead = reader.read(buffer, 0, 1024)) != -1) {
				output.append(buffer, 0, byteRead);
			}
			reader.close();
			sReturn = CommonUtil.nvl(output.toString());
		} else {
			sReturn = "";
		}

		return sReturn;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addColumn(String sColumn) throws Exception {
		if (CommonUtil.isEmpty(sColumn.trim())) {
			logger.error("Invalid Column Name : "+sColumn);
			throw new Exception("[DataSet : addColumn(String)] Exception : Invalid Column : "+sColumn);
		}
		addName(sColumn);

		for (int i=0; i<getRowCnt(); i++) {
			List row = (List)fieldValue.get(i);

			row.add("");
			fieldValue.set(i, row);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addColumn(String[] arrColumn) throws Exception {
		if (arrColumn.length < 1) {
			logger.error("Invalid Column Name : column name array");
			throw new Exception("[DataSet : addColumn(String[])] Exception : Invalid Column : column name array");
		}

		for (int i=0; i<arrColumn.length; i++) {
			addName(arrColumn[i]);
		}

		for (int j=0; j<getRowCnt(); j++) {
			List row = (List)fieldValue.get(j);

			for (int col=0; col<arrColumn.length; col++) {
				row.add("");
			}
			fieldValue.set(j, row);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addColumn(String sColumn, String sValue) throws Exception {
		if (CommonUtil.isEmpty(sColumn.trim())) {
			logger.error("Invalid Column Name : "+sColumn);
			throw new Exception("[DataSet : addColumn(String, String)] Exception : Invalid Column : "+sColumn);
		}
		addName(sColumn);

		if (getRowCnt() < 1) {
			addRow(new ArrayList());
		}

		for (int j=0; j<getRowCnt(); j++) {
			List row = (List)fieldValue.get(j);

			row.add(CommonUtil.nvl(sValue));
			fieldValue.set(j,row);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addColumn(String[] arrColumn, String[] arrColumnValue) throws Exception {
		if ((arrColumn.length < 1) || (arrColumn.length != arrColumnValue.length)) {
			logger.error("Invalid Column : column name array");
			throw new Exception("[DataSet : addColumn(String, String)] Exception : Invalid Column : column name array");
		}

		for (int i=0; i<arrColumn.length; i++) {
			addName(arrColumn[i]);
		}

		if (getRowCnt() < 1) {
			addRow(new ArrayList());
		}

		for (int j=0; j<getRowCnt(); j++) {
			List row = (List)fieldValue.get(j);

			for (int col=0; col<arrColumn.length; col++) {
				row.add(CommonUtil.nvl(arrColumnValue[col]));
			}
			fieldValue.set(j, row);
		}
	}

	public String getName(int idx) {
		return (String)fieldName.get(idx);
	}

	public String[] getNames() {
		return (String[])fieldName.toArray(new String[fieldName.size()]);
	}

	@SuppressWarnings("rawtypes")
	private List getRowAsList(int row) {
		return (List)fieldValue.get(row);
	}

	public Dto getRowAsDto(int row, Dto dto) throws Exception {
		String pkColNames[] = CommonUtil.split(dto.getFrwVarPrimaryKey(), ",");

		if (pkColNames != null) {
			for (int i=0; i<pkColNames.length; i++) {
				if (CommonUtil.isEmpty(getValue(row, CommonUtil.trim(pkColNames[i])))) {
					throw new Exception("[DataSet : getRowAsDto(int, Dto)] Exception : No value for PK Column");
				}
			}
		}
		dto.setValues(this);

		return dto;
	}

	@SuppressWarnings("unchecked")
	public String[] getRow(int row) throws Exception {
		if ((fieldValue.size() < 1) || (row > fieldValue.size())) {
			logger.error("Out of Row index : " + row);
			throw new Exception("[DataSet : getRow(int)] Exception : Out of Row index : " + row);
		}
		return (String[])getRowAsList(row).toArray(new String[getRowAsList(row).size()]);
	}

	public String getValue(String name) throws Exception {
		return getValue(0, name);
	}

	public String getValue(int col) throws Exception {
		return getValue(0, col);
	}

	public String getValue(int row, String name) throws Exception {
		Integer idx = (Integer)fieldNameIdx.get(name);

		if (idx == null) {return "";}

		if ((fieldValue.size() < 1) || (row > fieldValue.size())) {
			logger.error("Out of Row index : "+row);
			throw new Exception("[DataSet : getValue(int, String)] Exception : Out of Row index : "+row);
		}
		return CommonUtil.nvl(getValue(row, idx.intValue()));
	}

	public String getValue(int row, int col) {
		return CommonUtil.nvl((String)getRowAsList(row).get(col));
	}

	public int getRowIndex(int col, String value) throws Exception {
		int intRtn = -1;

		if (CommonUtil.isEmpty(value.trim())) {
			intRtn = -1;
		} else {
			if (getRowCnt() <= 0) {
				intRtn = -1;
			} else {
				for (int i=0; i<getRowCnt(); i++) {
					if (getValue(i, col).equals(value)) {
						intRtn = i;
						break;
					}
				}
			}
		}
		return intRtn;
	}

	public int getRowIndex(String colName, String value) throws Exception {
		int intRtn = -1;

		if (CommonUtil.isEmpty(value.trim())) {
			intRtn = -1;
		} else {
			if (getRowCnt() <= 0) {
				intRtn = -1;
			} else {
				for (int i=0; i<getRowCnt(); i++) {
					if (getValue(i, colName).equals(value)) {
						intRtn = i;
						break;
					}
				}
			}
		}
		return intRtn;
	}

	public int getColIndex(int row, String value) throws Exception {
		int intRtn = -1;

		if (CommonUtil.isEmpty(value.trim())) {
			intRtn = -1;
		} else {
			if (getColumnCnt() <= 0) {
				intRtn = -1;
			} else {
				for (int i=0; i<getColumnCnt(); i++) {
					if (getValue(row, i).equals(value)) {
						intRtn = i;
						break;
					}
				}
			}
		}
		return intRtn;
	}

	public int getColumnCnt() {
		return this.fieldName.size();
	}

	public int getRowCnt() {
		return this.fieldValue.size();
	}

	public void setValue(int col, String value) throws Exception {
		setValue(0, col, value);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setValue(int row, int col, String value) throws Exception {
		List lsTemp = getRowAsList(row);

		if (lsTemp == null || lsTemp.isEmpty()) {
			logger.error("Invalid Row index : " + row);
			throw new Exception("[DataSet : setValue(int, int, String)] Exception : Invalid Row index : " + row);
		}
		lsTemp.set(col, value);
	}

	public void setValue(String colName, String value) throws Exception {
		setValue(0, colName, value);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setValue(int row, String colName, String value) throws Exception {
		List lsTemp = getRowAsList(row);

		if (lsTemp == null || lsTemp.isEmpty()) {
			logger.error("Invalid Row index : " + row);
			throw new Exception("[DataSet : setValue(int, String, String)] Exception : Invalid Row index : " + row);
		}

		Integer idx = (Integer)fieldNameIdx.get(colName);

		if (idx == null) {
			logger.error("Invalid Column name : " + colName);
			throw new Exception("[DataSet : setValue(int, String, String)] Exception : Invalid Column name : " + colName);
		}
		lsTemp.set(idx.intValue(), value);
	}

	public void setValue(int col, Object value) throws Exception {
		setValue(0, col, value);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setValue(int row, int col, Object value) throws Exception {
		List lsTemp = getRowAsList(row);

		if (lsTemp == null || lsTemp.isEmpty()) {
			logger.error("Invalid Row index : " + row);
			throw new Exception("[DataSet : setValue(int, int, String)] Exception : Invalid Row index : " + row);
		}
		lsTemp.set(col, getString(value));
	}

	public void setValue(String colName, Object value) throws Exception {
		setValue(0, colName, value);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setValue(int row, String colName, Object value) throws Exception {
		List lsTemp = getRowAsList(row);

		if (lsTemp == null || lsTemp.isEmpty()) {
			logger.error("Invalid Row index : " + row);
			throw new Exception("[DataSet : setValue(int, String, String)] Exception : Invalid Row index : " + row);
		}

		Integer idx = (Integer)fieldNameIdx.get(colName);

		if (idx == null) {
			logger.error("Invalid Column name : " + colName);
			throw new Exception("[DataSet : setValue(int, String, String)] Exception : Invalid Column name : " + colName);
		}
		lsTemp.set(idx.intValue(), getString(value));
	}

	public void deleteRow(int row) throws Exception {
		if (fieldValue.size() < row) {
			logger.error("Invalid Row index : " + row);
			throw new Exception("[DataSet : delRow(int)] Exception : Invalid Row index : " + row);
		}
		fieldValue.remove(row);
	}

	public void deleteColumn(int col) throws Exception {
		if (fieldName.size() < col) {
			logger.error("Invalid Column index : " + col);
			throw new Exception("[DataSet : deleteColumn(int)] Exception : Invalid Column index : " + col);
		}
		fieldName.remove(col);

		for (int i=0; i<getRowCnt(); i++) {
			getRowAsList(i).remove(col);
		}
	}

	public void deleteColumn(String colName) throws Exception {
		String sNames = CommonUtil.toString(getNames(), ",");

		if (colName == null || "".equals(colName) || sNames.indexOf(colName) == -1) {
			logger.info("Skipped wrong column name : " + colName);
		} else {
			int iIdx = -1;

			for (int i=0; i<getColumnCnt(); i++) {
				if (colName.equals(getName(i))) {
					iIdx = i;
					break;
				}
			}
			fieldName.remove(iIdx);

			for (int i=0; i<getRowCnt(); i++) {
				getRowAsList(i).remove(iIdx);
			}
		}
	}

	public DataSet copyDataSet() throws Exception {
		DataSet ds = new DataSet();

		ds.addName(this.getNames());
		for (int i=0; i<this.getRowCnt(); i++) {
			ds.addRow(ds.getColumnCnt());
			for (int j=0; j<ds.getColumnCnt(); j++) {
				ds.setValue(ds.getRowCnt()-1, j, this.getValue(i, j));
			}
		}

		return ds;
	}

	public void copyFromDataSet(DataSet ds) throws Exception {
		this.addName(ds.getNames());
		for (int i=0; i<ds.getRowCnt(); i++) {
			this.addRow(this.getColumnCnt());
			for (int j=0; j<this.getColumnCnt(); j++) {
				this.setValue(this.getRowCnt()-1, j, ds.getValue(i, j));
			}
		}
	}

	private String getString(Object object) throws Exception {
		StringBuilder strBuilder = new StringBuilder();

		if (object != null) {
			if (object instanceof Clob) {
				Reader reader = ((Clob)object).getCharacterStream();
//				BufferedReader br = new BufferedReader(reader);
//				String lineStr;
//
//				while ((lineStr = br.readLine()) != null) {
//					strBuilder.append(lineStr);
//				}
//				br.close();
				strBuilder.append(getLob(reader));
			} else if (object instanceof Timestamp) {
				strBuilder.append(new SimpleDateFormat(ConfigUtil.getProperty("format.default.dateTime")).format(object));
			} else {
				strBuilder.append(object.toString());
			}
		}

		return strBuilder.toString();
	}

	@SuppressWarnings("rawtypes")
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		int cnt = fieldName.size();

		for (int i=0; i<cnt; i++) {
			buffer.append(getName(i));
			if (i+1 < cnt) {buffer.append(",");}
		}
		buffer.append("\n");
		for (int i=0; i<fieldValue.size(); i++) {
			List value = getRowAsList(i);
			cnt = value.size();

			for (int j=0; j<cnt; j++) {
				buffer.append(value.get(j));
				if (j+1 < cnt) {buffer.append(",");}
			}
			buffer.append("\n");
		}
		return buffer.toString();
	}

	@SuppressWarnings("rawtypes")
	public String toStringForJs() {
		StringBuffer buffer = new StringBuffer();
		int cnt = fieldName.size();

		for (int i=0; i<cnt; i++) {
			buffer.append(getName(i));
			if (i+1 < cnt) {buffer.append(ConfigUtil.getProperty("delimiter.data"));}
		}
		buffer.append(ConfigUtil.getProperty("delimiter.header.dataset"));
		for (int i=0; i<fieldValue.size(); i++) {
			List value = getRowAsList(i);
			cnt = value.size();

			for (int j=0; j<cnt; j++) {
				buffer.append(value.get(j));
				if (j+1 < cnt) {buffer.append(ConfigUtil.getProperty("delimiter.data"));}
			}
			if (i+1 < fieldValue.size()) {buffer.append(ConfigUtil.getProperty("delimiter.record"));}
		}
		return buffer.toString();
	}

	public String toXmlString() {
		StringBuffer sb = new StringBuffer();

		sb.append("<dataSetHeader>");
		for (int i=0; i<getColumnCnt(); i++) {
			sb.append("<header name=\"").append(getName(i)).append("\"/>");
		}
		sb.append("</dataSetHeader>");
		sb.append("<dataSetRow>");
		for (int i=0; i<getRowCnt(); i++) {
			sb.append("<dataSetRowItems>");
			for (int j=0; j<getColumnCnt(); j++) {
				sb.append("<rowItem name=\"").append(getName(j)).append("\" value=\"").append(getValue(i, j)).append("\"/>");
			}
			sb.append("</dataSetRowItems>");
		}
		sb.append("</dataSetRow>");

		return sb.toString();
	}

	public String toJsonString() {
		StringBuffer sb = new StringBuffer();

		sb.append("{");
		sb.append("\"dataSetHeader\":[");
		for (int i=0; i<getColumnCnt(); i++) {
			sb.append("\"").append(getName(i)).append("\"");
			if (i < getColumnCnt()-1) {
				sb.append(",");
			}
		}
		sb.append("],");
		sb.append("\"dataSetValue\":{");
		for (int i=0; i<getRowCnt(); i++) {
			sb.append("\"dataSetValueRow").append(i).append("\":[");
			for (int j=0; j<getColumnCnt(); j++) {
				sb.append("\"").append(getValue(i, j)).append("\"");
				if (j < getColumnCnt()-1) {
					sb.append(",");
				}
			}
			sb.append("]");
			if (i < getRowCnt()-1) {
				sb.append(",");
			} else {
				sb.append("");
			}
		}
		sb.append("}");
		sb.append("}");

		return sb.toString();
	}
}