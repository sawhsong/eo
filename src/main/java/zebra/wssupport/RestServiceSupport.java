package zebra.wssupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.activation.DataHandler;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class RestServiceSupport {
	private static final String PROVIDER_URL = ConfigUtil.getProperty("webService.provider.url");

	/*!
	 * Server support
	 */
	@SuppressWarnings("unchecked")
	public static MultipartBody getMultipartBody(ParamEntity paramEntity, String fileFullPath, String fileName) throws Exception {
		return new MultipartBody(getAttachmentList(paramEntity, new File(fileFullPath), fileName));
	}

	@SuppressWarnings("unchecked")
	public static MultipartBody getMultipartBody(ParamEntity paramEntity, File file, String fileName) throws Exception {
		return new MultipartBody(getAttachmentList(paramEntity, file, fileName));
	}

	@SuppressWarnings({ "rawtypes" })
	public static List getAttachmentList(ParamEntity paramEntity, String fileFullPath, String fileName) throws Exception {
		return getAttachmentList(paramEntity, new File(fileFullPath), fileName);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getAttachmentList(ParamEntity paramEntity, File file, String fileName) throws Exception {
		List attachmentList = new LinkedList();

		attachmentList.add(new Attachment("paramEntity", "application/json", paramEntity.toJsonString()));
		attachmentList.add(new Attachment(fileName, "", new FileInputStream(file)));

		return attachmentList;
	}

	public static String getParamEntityFromMultipartBody(MultipartBody multipartBody) throws Exception {
		String rtn = "";

		if (multipartBody != null) {
			List<Attachment> attachmentList = multipartBody.getAllAttachments();

			for (Attachment attach : attachmentList) {
				// Attachment ID must be 'ParamEntity'
				if (CommonUtil.containsIgnoreCase(attach.getContentId(), "ParamEntity")) {
					rtn = (String)attach.getObject(String.class);
					break;
				}
			}
		}
		return rtn;
	}

	/*!
	 * Client support
	 */
	/**
	 * General post - no file upload
	 * @param serviceUrl
	 * @param acceptTypeHeader
	 * @param paramEntity
	 * @return
	 * @throws Exception
	 */
	public static String post(String serviceUrl, String acceptTypeHeader, ParamEntity paramEntity) throws Exception {
		if (CommonUtil.isBlank(serviceUrl) || CommonUtil.isBlank(acceptTypeHeader) || paramEntity == null) {
			return "";
		}

		WebClient webClient = WebClient.create(PROVIDER_URL);
		Response wsResponse = webClient.path(serviceUrl).accept(new String[] {acceptTypeHeader}).post(paramEntity.toJsonString());
		return (String)wsResponse.readEntity(String.class);
	}
	/**
	 * Post for file download
	 * @param serviceUrl
	 * @param paramEntity
	 * @return
	 * @throws Exception
	 */
	public static MultipartBody postForFileDownload(String serviceUrl, ParamEntity paramEntity) throws Exception {
		WebClient webClient = WebClient.create(PROVIDER_URL);
		return webClient.path(serviceUrl).accept("multipart/mixed").post(paramEntity.toJsonString()).readEntity(MultipartBody.class);
	}

	@SuppressWarnings("rawtypes")
	public static String postAttachment(String serviceUrl, String contentTypeHeader, String acceptTypeHeader, ParamEntity paramEntity, List attachmentList) throws Exception {
		if (CommonUtil.isBlank(serviceUrl) || CommonUtil.isBlank(contentTypeHeader) || CommonUtil.isBlank(acceptTypeHeader) || paramEntity == null) {
			return "";
		}

		WebClient webClient = WebClient.create(PROVIDER_URL);
		Response wsResponse = webClient.path(serviceUrl).type(contentTypeHeader).accept(new String[] {acceptTypeHeader}).post(attachmentList);
		return (String)wsResponse.readEntity(String.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String postAttachment(String serviceUrl, String contentTypeHeader, String acceptTypeHeader, ParamEntity paramEntity, DataSet fileDataSet) throws Exception {
		List attachmentList = new LinkedList();
		InputStream inputStream;

		if (CommonUtil.isBlank(serviceUrl) || CommonUtil.isBlank(contentTypeHeader) || CommonUtil.isBlank(acceptTypeHeader) || paramEntity == null) {
			return "";
		}

		attachmentList.add(new Attachment("paramEntity", acceptTypeHeader, paramEntity.toJsonString()));
		for (int i=0; i<fileDataSet.getRowCnt(); i++) {
			inputStream = new FileInputStream(fileDataSet.getValue(i, "TEMP_PATH")+"/"+fileDataSet.getValue(i, "NEW_NAME"));
			attachmentList.add(new Attachment(fileDataSet.getValue(i, "FORM_TAG_NAME"), fileDataSet.getValue(i, "TYPE"), inputStream));
		}

		WebClient webClient = WebClient.create(PROVIDER_URL);
		Response wsResponse = webClient.path(serviceUrl).type(contentTypeHeader).accept(new String[] {acceptTypeHeader}).post(attachmentList);
		return (String)wsResponse.readEntity(String.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getAttachmentList(DataSet fileDataSet) throws Exception {
		List attachmentList = new LinkedList();
		InputStream inputStream;

		for (int i=0; i<fileDataSet.getRowCnt(); i++) {
			inputStream = new FileInputStream(fileDataSet.getValue(i, "TEMP_PATH")+"/"+fileDataSet.getValue(i, "NEW_NAME"));
			attachmentList.add(new Attachment(fileDataSet.getValue(i, "FORM_TAG_NAME"), fileDataSet.getValue(i, "TYPE"), inputStream));
		}

		return attachmentList;
	}

	public static InputStream getFileInputStreamFromMultipartBody(MultipartBody multipartBody) throws Exception {
		if (multipartBody != null) {
			List<Attachment> attachmentList = multipartBody.getAllAttachments();

			for (Attachment attach : attachmentList) {
				// Attachment ID must be 'ParamEntity'
				if (!CommonUtil.containsIgnoreCase(attach.getContentId(), "ParamEntity")) {
					DataHandler dataHandler = attach.getDataHandler();
					return dataHandler.getInputStream();
				}
			}
		}
		return null;
	}

	public static String getFileNameFromMultipartBody(MultipartBody multipartBody) throws Exception {
		if (multipartBody != null) {
			List<Attachment> attachmentList = multipartBody.getAllAttachments();

			for (Attachment attach : attachmentList) {
				// Attachment ID must be 'ParamEntity'
				if (!CommonUtil.containsIgnoreCase(attach.getContentId(), "ParamEntity")) {
					return attach.getContentId();
				}
			}
		}
		return null;
	}
}