package zebra.base;

import java.io.File;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.HttpParameters;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.dispatcher.multipart.UploadedFile;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.HttpParametersAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.context.support.MessageSourceAccessor;

import com.opensymphony.xwork2.ActionSupport;

import zebra.config.MemoryBean;
import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;
import zebra.util.FileUtil;

public class Action extends ActionSupport implements ServletContextAware, ServletRequestAware, ServletResponseAware, SessionAware, HttpParametersAware, ApplicationAware {
	protected Logger logger = LogManager.getLogger(getClass());

	protected MessageSourceAccessor messageSourceAccessor;
	protected ServletContext servletContext;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	@SuppressWarnings("rawtypes")
	protected Map applicationMap;

	protected Locale locale;
	protected String languageCode;
	protected boolean isMultipart;
	/*!
	 * From sajf - ParamEntity, Request Dataset
	 * All the variables in action and biz must be set in the paramEntity including process result code, message, requestDataSet and requestFileDataSet
	 * ParamEntity is the only one that is called by JSP page
	 */
	protected ParamEntity paramEntity = new ParamEntity();
	protected DataSet requestDataSet;
	protected DataSet searchCriteriaDataSet;
	protected DataSet requestFileDataSet;

	/*!
	 * 1. MessageSourceAccessor
	 */
	public MessageSourceAccessor getMessageSourceAccessor() {
		return messageSourceAccessor;
	}

	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		this.messageSourceAccessor = messageSourceAccessor;
	}

	/*!
	 * getMessage() - being called by sub classes
	 */
	public String getMessage(String messageCode) {
		return messageSourceAccessor.getMessage(messageCode, locale);
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

	/*!
	 * 2. ServletContext
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		logServletContext();
	}

	/*!
	 * 3. HttpServletRequest
	 */
	public void setServletRequest(HttpServletRequest servletRequest) {
		request = servletRequest;
		session = request.getSession();
		locale = servletRequest.getLocale();
		isMultipart = ServletFileUpload.isMultipartContent(servletRequest);
		logServletRequest();
	}

	/*!
	 * 4. HttpServletResponse
	 */
	public void setServletResponse(HttpServletResponse servletResponse) {
		response = servletResponse;
		logServletResponse();
	}

	/*!
	 * 5. HttpServletRequest Parameters
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setParameters(HttpParameters paramHttpParameters) {
		requestDataSet = new DataSet();
		searchCriteriaDataSet = new DataSet();

		String searchCriteriaElementSuffix = ConfigUtil.getProperty("etc.formElement.searchCriteriaSuffix");
		String autoSetSearchCriteria = ConfigUtil.getProperty("view.object.autoSetSearchCriteria");
		String recordDelimiter = ConfigUtil.getProperty("delimiter.record");

		try {
			for (Iterator iter = request.getParameterMap().entrySet().iterator(); iter.hasNext();) {
				Entry<String, String[]> entry = (Entry<String, String[]>)iter.next();
				String key = entry.getKey();
				String values[] = entry.getValue();
				String value = "";

				for (int i=0; i<values.length; i++) {
//					value += (CommonUtil.isEmpty(value)) ? HtmlUtil.stringToHtml(values[i]) : recordDelimiter + HtmlUtil.stringToHtml(values[i]);
					value += (CommonUtil.isEmpty(value)) ? values[i] : recordDelimiter + values[i];
				}

				if (key.indexOf(searchCriteriaElementSuffix) >= 0) {
					String valueStr = CommonUtil.toString(values, recordDelimiter);
					if (CommonUtil.indexOf(valueStr, recordDelimiter) == 0) {
						values = new String[] {};
					}
					searchCriteriaDataSet.addColumn(key, value);
//					searchCriteriaDataSet.addColumn(key, HtmlUtil.stringToHtml(CommonUtil.toString(values, recordDelimiter)));
//					searchCriteriaDataSet.addColumn(key, HtmlUtil.stringToHtml(value));

				} else {
					requestDataSet.addColumn(key, value);
//					requestDataSet.addColumn(key, HtmlUtil.stringToHtml(CommonUtil.toString(values, recordDelimiter)));
//					requestDataSet.addColumn(key, HtmlUtil.stringToHtml(value));
				}
			}

			if (CommonUtil.equalsIgnoreCase(autoSetSearchCriteria, "Y")) {
				request.setAttribute("searchCriteriaDataSet", searchCriteriaDataSet);
			}

			/*!
			 * Sets currentPage and maxRowsPerPage values for WebService
			 */
			paramEntity.setObject("currentPage", CommonUtil.toInt(CommonUtil.nvl(requestDataSet.getValue("txtCurrentPageForPagination"), "1")));
			paramEntity.setObject("maxRowsPerPage", CommonUtil.toInt(CommonUtil.nvl(requestDataSet.getValue("selMaxRowsPerPageSelectForPagenation"), CommonUtil.split(ConfigUtil.getProperty("view.data.maxRowsPerPage"), ConfigUtil.getProperty("delimiter.data"))[2])));

			setMultipartRequest();
			logRequestDataSet();
		} catch (Exception ex) {
			/*!
			 * Export Action - <result name="export" type="chain">
			 *    - when the next action is called, the value of parameter entry is not array -> error occurring
			 *    - but this can be ignored
			 */
//			logger.error(ex);
		}
	}

	/*!
	 * 6. Application Map
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void setApplication(Map application) {
		applicationMap = application;
		logApplication();
	}

	/*!
	 * 7. Session Map
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public void setSession(Map sessionMap) {
		logSession();

		// To use session in service layer(Biz)
		paramEntity.setSession(session);
		paramEntity.setRequest(request);
		paramEntity.setRequestDataSet(requestDataSet);
		paramEntity.setSearchCriteriaDataSet(searchCriteriaDataSet);
		paramEntity.setRequestFileDataSet(requestFileDataSet);

		logParamEntity();

		MemoryBean.set(session.getId(), session);
	}

	/*!
	 * Called from Menu Action(Ajax call)
	 */
	protected void setRequestAttribute(String attributeName, Object object) {
		request.setAttribute(attributeName, object);
	}

	@SuppressWarnings("rawtypes")
	private void setMultipartRequest() {
		try {
			if (isMultipart) {
//				String appRealPath = (String)MemoryBean.get("applicationRealPath");
//				String pathTemp = appRealPath + ConfigUtil.getProperty("path.dir.temp");
//				String pathUploadRepository = appRealPath + ConfigUtil.getProperty("path.dir.uploadRepository");

				String pathTemp = ConfigUtil.getProperty("path.dir.temp");
				String pathUploadRepository = ConfigUtil.getProperty("path.dir.uploadRepository");
				String dataSetHeader[] = {"FORM_TAG_NAME", "ORIGINAL_NAME", "NEW_NAME", "TEMP_PATH", "REPOSITORY_PATH", "TYPE", "SIZE", "ICON"};
				MultiPartRequestWrapper multipartWrapper = (MultiPartRequestWrapper)request;

				requestFileDataSet = new DataSet();
				requestFileDataSet.addName(dataSetHeader);

				for (Enumeration fileParameterNames = multipartWrapper.getFileParameterNames(); fileParameterNames != null && fileParameterNames.hasMoreElements();) {
					String inputName = (String)fileParameterNames.nextElement();
					String[] contentTypes = multipartWrapper.getContentTypes(inputName);

					if (CommonUtil.isNotEmpty(contentTypes)) {
						String[] fileNames = multipartWrapper.getFileNames(inputName);

						if (CommonUtil.isNotEmpty(fileNames)) {
							UploadedFile[] files = (UploadedFile[])multipartWrapper.getFiles(inputName);

							if (files != null && files.length > 0) {
								String newName = CommonUtil.getSysdate("yyyyMMddHHmmss") + "_" + CommonUtil.uid() + "_" + fileNames[0];
//								String newName = CommonUtil.getSysdate("yyyyMMddHHmmss") + "_" + CommonUtil.uid() + "_" + HtmlUtil.stringToHtml(fileNames[0]);
								File fileToSave = new File(pathTemp, newName);
								FileUtil.copyFile((File)(files[0].getContent()), fileToSave);

								requestFileDataSet.addRow();
								requestFileDataSet.setValue(requestFileDataSet.getRowCnt()-1, "FORM_TAG_NAME", inputName);
								requestFileDataSet.setValue(requestFileDataSet.getRowCnt()-1, "ORIGINAL_NAME", fileNames[0]);
								requestFileDataSet.setValue(requestFileDataSet.getRowCnt()-1, "NEW_NAME", newName);
								requestFileDataSet.setValue(requestFileDataSet.getRowCnt()-1, "TEMP_PATH", pathTemp);
								requestFileDataSet.setValue(requestFileDataSet.getRowCnt()-1, "REPOSITORY_PATH", pathUploadRepository);
								requestFileDataSet.setValue(requestFileDataSet.getRowCnt()-1, "TYPE", contentTypes[0]);
								requestFileDataSet.setValue(requestFileDataSet.getRowCnt()-1, "SIZE", files[0].length());
								requestFileDataSet.setValue(requestFileDataSet.getRowCnt()-1, "ICON", CommonUtil.getIconNameByFileType(contentTypes[0]));
							} else {
								logger.info(getMessage("I901"));
							}
						}
					}
				}

				logRequestFileDataSet();
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
	}

	/*!
	 * private methods - log
	 */
	@SuppressWarnings("rawtypes")
	private void logServletContext() {
		if (CommonUtil.equalsIgnoreCase(ConfigUtil.getProperty("log.debug.servletContext"), "Y")) {
			for (Enumeration emAttr = servletContext.getAttributeNames(); emAttr.hasMoreElements();) {
				String s = (String)emAttr.nextElement();
				logger.debug("servletContext.getAttribute[" + s + "] at setServletContext() : " + servletContext.getAttribute(s));
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private void logServletRequest() {
		if (CommonUtil.equalsIgnoreCase(ConfigUtil.getProperty("log.debug.requestAttr"), "Y")) {
			for (Enumeration emAttr = request.getAttributeNames(); emAttr.hasMoreElements();) {
				String s = (String)emAttr.nextElement();
				logger.debug("request.getAttribute[" + s + "] at setServletRequest() : " + request.getAttribute(s));
			}
		}

		if (CommonUtil.equalsIgnoreCase(ConfigUtil.getProperty("log.debug.requestHeader"), "Y")) {
			for (Enumeration emAttr = request.getHeaderNames(); emAttr.hasMoreElements();) {
				String s = (String)emAttr.nextElement();
				logger.debug("request.getHeader[" + s + "] at setServletRequest() : " + request.getHeader(s));
			}
		}
	}

	private void logServletResponse() {
	}

	private void logRequestDataSet() {
		if (CommonUtil.equalsIgnoreCase(ConfigUtil.getProperty("log.debug.requestDataSet"), "Y")) {
			logger.debug("[requestDataSet]\n" + requestDataSet.toString());
		}
	}

	private void logRequestFileDataSet() {
		if (CommonUtil.equalsIgnoreCase(ConfigUtil.getProperty("log.debug.requestFileDataSet"), "Y")) {
			logger.debug("[requestFileDataSet]\n" + requestFileDataSet.toString());
		}
	}

	@SuppressWarnings("unchecked")
	private void logApplication() {
		if (CommonUtil.equalsIgnoreCase(ConfigUtil.getProperty("log.debug.applicationMap"), "Y")) {
			applicationMap.forEach((key, object)->{
				logger.debug("applicationMap - [" + key + "] : " + object + "");
			});
		}
	}

	@SuppressWarnings("rawtypes")
	private void logSession() {
		if (CommonUtil.equalsIgnoreCase(ConfigUtil.getProperty("log.debug.session"), "Y")) {
			for (Enumeration sessionAttr = session.getAttributeNames(); sessionAttr.hasMoreElements();) {
				String name = (String)sessionAttr.nextElement();
				logger.debug("session - [" + name + "] : " + session.getAttribute(name) + "");
			}
		}
	}

	private void logParamEntity() {
		if (CommonUtil.equalsIgnoreCase(ConfigUtil.getProperty("log.debug.paramEntity"), "Y")) {
			logger.debug("[paramEntity]\n" + paramEntity.toString());
		}

		if (CommonUtil.equalsIgnoreCase(ConfigUtil.getProperty("log.debug.memoryBean"), "Y")) {
			logger.debug("[MemoryBean]\n" + MemoryBean.getObject().toString());
		}
	}

	/*!
	 * getter - called by jsp(do not change - very important!)
	 */
	public ParamEntity getParamEntity() {
		return paramEntity;
	}
}