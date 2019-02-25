package zebra.wssupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.activation.DataHandler;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.data.QueryAdvisor;
import zebra.util.CommonUtil;

public class RestServiceSupport {
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
	public static String get(String providerUrl, String serviceUrl, String acceptTypeHeader, QueryAdvisor queryAdvisor) throws Exception {
		if (CommonUtil.isBlank(providerUrl) || CommonUtil.isBlank(serviceUrl) || CommonUtil.isBlank(acceptTypeHeader)) {
			return "";
		}

		DataSet queryParam = queryAdvisor.getVariableDataSet();
		WebClient webClient = WebClient.create(providerUrl);
		webClient.path(serviceUrl);
		if (queryParam.getRowCnt() > 0) {
			for (int i=0; i<queryParam.getRowCnt(); i++) {
				webClient.query(queryParam.getValue(i, 0), queryParam.getValue(i, 1));
			}
		}
		Response wsResponse = webClient.accept(new String[] {acceptTypeHeader}).get();
		return (String)wsResponse.readEntity(String.class);
	}

	public static String post(String providerUrl, String serviceUrl, String acceptTypeHeader, ParamEntity paramEntity) throws Exception {
		if (CommonUtil.isBlank(providerUrl) || CommonUtil.isBlank(serviceUrl) || CommonUtil.isBlank(acceptTypeHeader) || paramEntity == null) {
			return "";
		}

		WebClient webClient = WebClient.create(providerUrl);
		Response wsResponse = webClient.path(serviceUrl).type(acceptTypeHeader).accept(new String[] {acceptTypeHeader}).post(paramEntity.toJsonString());
		return (String)wsResponse.readEntity(String.class);
	}

	public static String post(String providerUrl, String serviceUrl, String acceptTypeHeader, DataSet postDataSet) throws Exception {
		if (CommonUtil.isBlank(providerUrl) || CommonUtil.isBlank(serviceUrl) || CommonUtil.isBlank(acceptTypeHeader) || postDataSet == null) {
			return "";
		}

		WebClient webClient = WebClient.create(providerUrl);
		Response wsResponse = webClient.path(serviceUrl).type(acceptTypeHeader).accept(new String[] {acceptTypeHeader}).post(postDataSet.toJsonStringForEO());
		return (String)wsResponse.readEntity(String.class);
	}

	public static MultipartBody postForFileDownload(String providerUrl, String serviceUrl, ParamEntity paramEntity) throws Exception {
		WebClient webClient = WebClient.create(providerUrl);
		return webClient.path(serviceUrl).accept("multipart/mixed").post(paramEntity.toJsonString()).readEntity(MultipartBody.class);
	}

	@SuppressWarnings("rawtypes")
	public static String postAttachment(String providerUrl, String serviceUrl, String contentTypeHeader, String acceptTypeHeader, ParamEntity paramEntity, List attachmentList) throws Exception {
		if (CommonUtil.isBlank(providerUrl) || CommonUtil.isBlank(serviceUrl) || CommonUtil.isBlank(contentTypeHeader) || CommonUtil.isBlank(acceptTypeHeader) || paramEntity == null) {
			return "";
		}

		WebClient webClient = WebClient.create(providerUrl);
		Response wsResponse = webClient.path(serviceUrl).type(contentTypeHeader).accept(new String[] {acceptTypeHeader}).post(attachmentList);
		return (String)wsResponse.readEntity(String.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String postAttachment(String providerUrl, String serviceUrl, String contentTypeHeader, String acceptTypeHeader, ParamEntity paramEntity, DataSet fileDataSet) throws Exception {
		List attachmentList = new LinkedList();
		InputStream inputStream;

		if (CommonUtil.isBlank(providerUrl) || CommonUtil.isBlank(serviceUrl) || CommonUtil.isBlank(contentTypeHeader) || CommonUtil.isBlank(acceptTypeHeader) || paramEntity == null) {
			return "";
		}

		attachmentList.add(new Attachment("paramEntity", acceptTypeHeader, paramEntity.toJsonString()));
		for (int i=0; i<fileDataSet.getRowCnt(); i++) {
			inputStream = new FileInputStream(fileDataSet.getValue(i, "TEMP_PATH")+"/"+fileDataSet.getValue(i, "NEW_NAME"));
			attachmentList.add(new Attachment(fileDataSet.getValue(i, "FORM_TAG_NAME"), fileDataSet.getValue(i, "TYPE"), inputStream));
		}

		WebClient webClient = WebClient.create(providerUrl);
		Response wsResponse = webClient.path(serviceUrl).type(contentTypeHeader).accept(new String[] {acceptTypeHeader}).post(attachmentList);
		return (String)wsResponse.readEntity(String.class);
	}
	/*!
	 * EntityOnline - Begin
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String postAttachmentEO(String providerUrl, String serviceUrl, String contentTypeHeader, String acceptTypeHeader, DataSet postDataSet, DataSet fileDataSet) throws Exception {
		List attachmentList = new LinkedList();
		InputStream inputStream;

		if (CommonUtil.isBlank(providerUrl) || CommonUtil.isBlank(serviceUrl) || CommonUtil.isBlank(contentTypeHeader) || CommonUtil.isBlank(acceptTypeHeader) || postDataSet == null) {
			return "";
		}

		attachmentList.add(new Attachment("postDataSet", acceptTypeHeader, postDataSet.toJsonStringForEO()));
		for (int i=0; i<fileDataSet.getRowCnt(); i++) {
			inputStream = new FileInputStream(fileDataSet.getValue(i, "TEMP_PATH")+"/"+fileDataSet.getValue(i, "NEW_NAME"));
			attachmentList.add(new Attachment(fileDataSet.getValue(i, "ORIGINAL_NAME"), fileDataSet.getValue(i, "TYPE"), inputStream));
		}

		WebClient webClient = WebClient.create(providerUrl);
		Response wsResponse = webClient.path(serviceUrl).type(contentTypeHeader).accept(new String[] {acceptTypeHeader}).post(attachmentList);
		return (String)wsResponse.readEntity(String.class);
	}

	public static InputStream getFileDownload(String providerUrl, String serviceUrl, QueryAdvisor queryAdvisor) throws Exception {
		DataSet queryParam = queryAdvisor.getVariableDataSet();
		WebClient webClient = WebClient.create(providerUrl);

		webClient.path(serviceUrl);
		if (queryParam.getRowCnt() > 0) {
			for (int i=0; i<queryParam.getRowCnt(); i++) {
				webClient.query(queryParam.getValue(i, 0), queryParam.getValue(i, 1));
			}
		}
		Response wsResponse = webClient.accept(new String[] {MediaType.MULTIPART_FORM_DATA}).get();
		return (InputStream)wsResponse.readEntity(InputStream.class);
	}
	/*!
	 * EntityOnline - End
	 */

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