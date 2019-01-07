package zebra.data;

import java.io.File;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import zebra.base.Dto;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class ParamEntity {
	private boolean isSuccess;
	@SuppressWarnings("rawtypes")
	private HashMap object;
	private HttpSession session;
	private HttpServletRequest request;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public Object getSessionAttribute(String attributeName) {
		return session.getAttribute(attributeName);
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Object getObject(String key) {
		if (object == null || CommonUtil.isBlank(key)) {return null;}
		if (!object.containsKey(key)) {return null;}

		return object.get(key);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setObject(String key, Object obj) {
		if (CommonUtil.isBlank(key) || obj == null) {return;}
		if (object == null) {object = new HashMap();}

		object.put(key, obj);
	}

	public void removeObject(String key) {
		if (object == null || CommonUtil.isBlank(key)) {return;}
		if (!object.containsKey(key)) {return;}

		object.remove(key);
	}

	@SuppressWarnings("unchecked")
	public void renameObject(String from, String to) {
		if (object == null || CommonUtil.isBlank(CommonUtil.nvl(from)) || CommonUtil.isBlank(CommonUtil.nvl(to))) {return;}
		if (!object.containsKey(from)) {return;}

		object.put(to, object.get(from));
		object.remove(from);
	}

	/**
	 * Reserved objects by ParamEntity
	 */
	public void setMessageCode(String messageCode) {
		setObject("preDefinedMessageCodeByParamEntity", messageCode);
	}

	public String getMessageCode() {
		return CommonUtil.nvl((String)getObject("preDefinedMessageCodeByParamEntity"));
	}

	public void setMessage(String message) {
		setObject("preDefinedMessageByParamEntity", message);
	}

	public void setMessage(String messageCode, String message) {
		setMessageCode(messageCode);
		setMessage(message);
	}

	public String getMessage() {
		return CommonUtil.nvl((String)getObject("preDefinedMessageByParamEntity"));
	}

	public void setRequestDataSet(DataSet requestDataSet) {
		setObject("preDefinedRequestDataSetByParamEntity", requestDataSet);
	}

	public DataSet getRequestDataSet() {
		return (DataSet)getObject("preDefinedRequestDataSetByParamEntity");
	}

	public void setSearchCriteriaDataSet(DataSet searchCriteriaDataSet) {
		setObject("preDefinedSearchCriteriaDataSetByParamEntity", searchCriteriaDataSet);
	}

	public DataSet getSearchCriteriaDataSet() {
		return (DataSet)getObject("preDefinedSearchCriteriaDataSetByParamEntity");
	}

	public void setRequestFileDataSet(DataSet requestFileDataSet) {
		setObject("preDefinedRequestFileDataSetByParamEntity", requestFileDataSet);
	}

	public DataSet getRequestFileDataSet() {
		return (DataSet)getObject("preDefinedRequestFileDataSetByParamEntity");
	}

	public void setTotalResultRows(int totalResultRows) {
		setObject("preDefinedTotalResultRowsByParamEntity", totalResultRows);
	}

	public void setAjaxResponseDataSet(DataSet ajaxResponseDataSet) {
		setObject("preDefinedAjaxResponseDataSetByParamEntity", ajaxResponseDataSet);
	}

	public DataSet getAjaxResponseDataSet() {
		return (DataSet)getObject("preDefinedAjaxResponseDataSetByParamEntity");
	}

	public void setFileToExport(File file) {
		setObject("preDefinedFileToExport", file);
	}

	public File getFileToExport() {
		return (File)getObject("preDefinedFileToExport");
	}

	public void setFileNameToExport(String fileName) {
		setObject("preDefinedFileNameToExport", fileName);
	}

	public String getFileNameToExport() {
		return (String)getObject("preDefinedFileNameToExport");
	}

	public int getTotalResultRows() {
		if (!object.containsKey("preDefinedTotalResultRowsByParamEntity")) {
			return 0;
		} else {
			return (getObject("preDefinedTotalResultRowsByParamEntity") instanceof String) ? CommonUtil.toInt((String)getObject("preDefinedTotalResultRowsByParamEntity")) : (int)getObject("preDefinedTotalResultRowsByParamEntity");
		}
	}
	/*!
	 * For WebService Client
	 * currentPage, maxRowsPerPage, pagination
	 */
	public void setCurrentPage(int currentPage) {
		setObject("currentPage", currentPage);
	}

	public int getCurrentPage() {
		if (getObject("currentPage") == null) {
			return 1;
		} else {
			return (getObject("currentPage") instanceof String) ? CommonUtil.toInt((String)getObject("currentPage")) : (int)getObject("currentPage");
		}
	}

	public void setMaxRowsPerPage(int maxRowsPerPage) {
		setObject("maxRowsPerPage", maxRowsPerPage);
	}

	public int getMaxRowsPerPage() {
		if (getObject("maxRowsPerPage") == null) {
			return 1;
		} else {
			return (getObject("maxRowsPerPage") instanceof String) ? CommonUtil.toInt((String)getObject("maxRowsPerPage")) : (int)getObject("maxRowsPerPage");
		}
	}

	public void setPagination(boolean pagination) {
		setObject("pagination", pagination);
	}

	public boolean isPagination() {
		if (getObject("pagination") == null) {
			return false;
		} else if (getObject("pagination") instanceof String) {
			return CommonUtil.equalsIgnoreCase((String)getObject("pagination"), "true");
		} else {
			return (boolean)getObject("pagination");
		}
	}

	/**
	 * QueryAdvisor
	 * 		- sets attributes for pagination(current_page, max_rows_per_page)
	 */
	public QueryAdvisor getQueryAdvisor() throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		DataSet dsRequest = getRequestDataSet();

		if (dsRequest != null) {
			queryAdvisor.setCurrentPage(CommonUtil.toInt(CommonUtil.nvl(dsRequest.getValue("txtCurrentPageForPagination"), "1")));
			queryAdvisor.setMaxRowsPerPage(CommonUtil.toInt(CommonUtil.nvl(dsRequest.getValue("selMaxRowsPerPageSelectForPagenation"), (String)getSessionAttribute("maxRowsPerPage"))));
		} else {	// For Web Services
			queryAdvisor.setCurrentPage(getCurrentPage());
			queryAdvisor.setMaxRowsPerPage(getMaxRowsPerPage());
			queryAdvisor.setPagination(isPagination());
		}

		return queryAdvisor;
	}

	public void setObjectFromXmlString(String xmlString) throws Exception {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xmlString)));
//		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(HtmlUtil.htmlToHexForXml(xmlString))));
		Element root = doc.getDocumentElement();
		String nodeName = root.getNodeName(), nodeValue = "";

		if (CommonUtil.equalsIgnoreCase(nodeName, "paramEntity")) {
			NodeList nodeList = root.getChildNodes();

			for (int i=0; i<nodeList.getLength(); i++) {
				Node node = nodeList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					nodeName = CommonUtil.nvl(node.getNodeName());
					nodeValue = CommonUtil.nvl(node.getTextContent());

					if (CommonUtil.equalsIgnoreCase(nodeName, "isSuccess")) {
						setSuccess(CommonUtil.equalsIgnoreCase(nodeValue, "true"));
					} else if (CommonUtil.equalsIgnoreCase(nodeName, "messageCode")) {
						setMessageCode(nodeValue);
					} else if (CommonUtil.equalsIgnoreCase(nodeName, "message")) {
						setMessage(nodeValue);
					} else {
						if (isDataSet(node)) {
							setObject(nodeName, new DataSet(node));
						} else {
							setObject(nodeName, nodeValue);
						}
					}
				}
			}
		}
	}

	public void setObjectFromJsonString(String jsonString) throws Exception {
		JSONObject jsonObject = (JSONObject)JSONSerializer.toJSON(jsonString);

		for (Object keys : jsonObject.keySet()) {
			String key = (String)keys;
			Object value = jsonObject.get(key);

			if (CommonUtil.equalsIgnoreCase(key, "isSuccess")) {
				setSuccess(jsonObject.getBoolean("isSuccess"));
			} else if (CommonUtil.equalsIgnoreCase(key, "messageCode")) {
				setMessageCode(jsonObject.getString("messageCode"));
			} else if (CommonUtil.equalsIgnoreCase(key, "message")) {
				setMessage(jsonObject.getString("message"));
			} else {
				if (value instanceof JSONObject) {
					if (isDataSet((JSONObject)value)) {
						setObject(key, new DataSet((JSONObject)value));
					} else {
						setObject(key, value);
					}
				} else {
					setObject(key, value);
				}
			}
		}
	}

	public void setDataSetValueFromJsonResultset(DataSet dataSet) throws Exception {
		if (dataSet != null && dataSet.getNames() != null) {
			dataSet.addRow();
			for (int i=0; i<dataSet.getColumnCnt(); i++) {
				dataSet.setValue(dataSet.getRowCnt()-1, dataSet.getName(i), getObject(dataSet.getName(i)));
			}
		}
	}

	private boolean isDataSet(Node node) {
		NodeList nodeList = node.getChildNodes();

		if (nodeList != null) {
			for (int i=0; i<nodeList.getLength(); i++) {
				Node child = nodeList.item(i);

				if (child.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element)child;

					if (CommonUtil.equalsIgnoreCase(element.getNodeName(), "dataSetHeader")) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private boolean isDataSet(JSONObject jsonObject) {
		JSONObject json = (JSONObject)jsonObject;
		return json.containsKey("dataSetHeader");
	}

	@SuppressWarnings("rawtypes")
	public String toString() {
		String rtn = "";
		String searchCriteriaDataSetDebugMode = ConfigUtil.getProperty("log.debug.searchCriteriaDataSet");

		rtn += "========== ParamEntity Log Start ==========\n";
		rtn += "ParamEntity Success Flag [isSuccess] : " + isSuccess + "\n";
		if (!(object == null || object.isEmpty())) {
			for (Iterator keys = object.keySet().iterator(); keys.hasNext();) {
				String key = (String)keys.next();

				if ("preDefinedSearchCriteriaDataSetByParamEntity".equalsIgnoreCase(key)) {
					if ("Y".equalsIgnoreCase(searchCriteriaDataSetDebugMode)) {
						rtn += "ParamEntity Object - [" + key + "] : " + object.get(key) + "\n";
					}
				} else {
					rtn += "ParamEntity Object - [" + key + "] : " + object.get(key) + "\n";
				}
				
			}
		}

		if (session != null) {
			for (Enumeration sessionAttr = session.getAttributeNames(); sessionAttr.hasMoreElements();) {
				String name = (String)sessionAttr.nextElement();
				rtn += "ParamEntity Session - [" + name + "] : " + this.session.getAttribute(name) + "\n";
			}
		}
		rtn += "\n";
		rtn += "========== ParamEntity Log End ==========\n";

		return rtn;
	}

	/*!
	 * Only return user-defined values
	 */
	@SuppressWarnings("rawtypes")
	public String toXmlString() throws Exception {
		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sb.append("<paramEntity>");
		sb.append("<isSuccess>"+isSuccess()+"</isSuccess>");
		sb.append("<messageCode>"+getMessageCode()+"</messageCode>");
		sb.append("<message>"+getMessage()+"</message>");

		if (!(object == null || object.isEmpty())) {
			for (Iterator keys = object.keySet().iterator(); keys.hasNext();) {
				String key = (String)keys.next();
				Object value;

				if (!CommonUtil.startsWith(key, "preDefined")) {
					value = object.get(key);

					if (value instanceof DataSet) {
						DataSet ds = (DataSet)value;

						sb.append("<"+key+">");
						sb.append(ds.toXmlString());
						sb.append("</"+key+">");
					} else if (value instanceof Dto) {
						Dto dto = (Dto)value;
						DataSet ds = dto.getDataSet();

						sb.append("<"+key+">");
						sb.append(ds.toXmlString());
						sb.append("</"+key+">");
					} else {
						sb.append("<"+key+">"+value+"</"+key+">");
					}
				}

				if (CommonUtil.equals(key, "preDefinedTotalResultRowsByParamEntity")) {
					sb.append("<"+key+">"+object.get(key)+"</"+key+">");
				}
			}
		}
		sb.append("</paramEntity>");

		return sb.toString();
	}

	/*!
	 * Only return user-defined values
	 */
	@SuppressWarnings("rawtypes")
	public String toJsonString() throws Exception {
		StringBuffer sb = new StringBuffer();

		sb.append("{");
		sb.append("\"isSuccess\":"+isSuccess()+",");
		sb.append("\"messageCode\":\""+getMessageCode()+"\",");
		sb.append("\"message\":\""+CommonUtil.remove(CommonUtil.remove(getMessage(), "\n"), "\"")+"\"");

		if (!(object == null || object.isEmpty())) {
			for (Iterator keys = object.keySet().iterator(); keys.hasNext();) {
				String key = (String)keys.next();
				Object value;

				if (!CommonUtil.startsWith(key, "preDefined")) {
					value = object.get(key);

					if (value instanceof DataSet) {
						DataSet ds = (DataSet)value;

						sb.append(",");
						sb.append("\""+key+"\":");
						sb.append(ds.toJsonString());
//						sb.append(HtmlUtil.stringToHtmlForJson(ds.toJsonString()));
					} else if (value instanceof Dto) {
						Dto dto = (Dto)value;
						DataSet ds = dto.getDataSet();

						sb.append(",");
						sb.append("\""+key+"\":");
						sb.append(ds.toJsonString());
//						sb.append(HtmlUtil.stringToHtmlForJson(ds.toJsonString()));
					} else {
						sb.append(",");
						sb.append("\""+key+"\":\""+value+"\"");
					}
				}

				if (CommonUtil.equals(key, "preDefinedTotalResultRowsByParamEntity")) {
					sb.append(",");
					sb.append("\""+key+"\":"+object.get(key));
				}
			}
		}

		sb.append("}");

		return sb.toString();
	}
}