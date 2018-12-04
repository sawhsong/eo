package zebra.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

import javax.activation.DataHandler;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.support.MessageSourceAccessor;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class BaseWebService {
	protected Logger logger = LogManager.getLogger(this.getClass());
	private SqlSessionTemplate sqlSessionTemplate;
	private SessionFactory sessionFactory;
	private MessageSourceAccessor messageSourceAccessor;
	protected ParamEntity paramEntity = new ParamEntity();

	protected SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected MessageSourceAccessor getMessageSourceAccessor() {
		return messageSourceAccessor;
	}

	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		this.messageSourceAccessor = messageSourceAccessor;
	}

	public ParamEntity getParamEntity() {
		return paramEntity;
	}

	public void setParamEntity(ParamEntity paramEntity) {
		this.paramEntity = paramEntity;
	}

	/*!
	 * getMessage() - being called by sub classes
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

	/*!
	 * For Web Service(REST, SOAP)
	 */
	protected void setWebserviceException(ParamEntity paramEntity, String code, Exception ex) {
		paramEntity.setSuccess(false);
		paramEntity.setMessage(code, getMessage(code)+" ["+ex.getMessage()+"]");
	}

	/*!
	 * REST Web Service Common method
	 */
	protected int saveFileFromMultipartBody(MultipartBody multipartBody, DataSet fileDataSet) throws Exception {
		int rtn = 0;

		if (multipartBody != null) {
			List<Attachment> attachmentList = multipartBody.getAllAttachments();

			for (Attachment attach : attachmentList) {
				// Attachment ID must be 'ParamEntity'
				if (!CommonUtil.containsIgnoreCase(attach.getContentId(), "ParamEntity")) {
					DataHandler dataHandler = attach.getDataHandler();
					InputStream inputStream = dataHandler.getInputStream();
					String contentId = attach.getContentId();
					File file = getFile(contentId, fileDataSet);
					OutputStream outputStream;

					if (file != null) {
						outputStream = new FileOutputStream(file);

						int read = 0;
						byte[] bytes = new byte[1024];
						while ((read = inputStream.read(bytes)) != -1) {
							outputStream.write(bytes, 0, read);
						}
						inputStream.close();
						outputStream.flush();
						outputStream.close();
						rtn++;
					}
				}
			}
		}
		return rtn;
	}

	private File getFile(String contentId, DataSet fileDataSet) throws Exception {
		for (int i=0; i<fileDataSet.getRowCnt(); i++) {
			String idValue = fileDataSet.getValue(i, "FORM_TAG_NAME");

			if (CommonUtil.equals(contentId, idValue)) {
				return new File(fileDataSet.getValue(i, "REPOSITORY_PATH")+"/"+fileDataSet.getValue(i, "NEW_NAME"));
			}
		}
		return null;
	}

	/*!
	 * SOAP Web Service Common method
	 */
	protected int saveFileFromDataHandlerList(List<DataHandler> dataHandlerList, DataSet fileDataSet) throws Exception {
		int rtn = 0;
		DataHandler dataHandler;
		OutputStream outputStream;
		InputStream inputStream;

		if (dataHandlerList != null) {
			for (int i=0; i<dataHandlerList.size(); i++) {
				dataHandler = dataHandlerList.get(i);
				inputStream = dataHandler.getInputStream();
				outputStream = new FileOutputStream(new File(fileDataSet.getValue(i, "REPOSITORY_PATH")+"/"+fileDataSet.getValue(i, "NEW_NAME")));
				int read = 0;
				byte[] bytes = new byte[1024];
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
				inputStream.close();
				outputStream.flush();
				outputStream.close();
				rtn++;
			}
		}

		return rtn;
	}
}