/**************************************************************************************************
 * Framework Supporter - Download through web services
 * Description
 * - 
 *************************************************************************************************/
package zebra.actionsupport;

import java.io.InputStream;
import java.net.URLEncoder;

import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import zebra.base.Action;
import zebra.wssupport.RestServiceSupport;

public class RestServiceDownloadAction extends Action {
	private String contentType;
	private InputStream inputStream;
	private String contentDisposition;
	private long contentLength;

	public String execute() throws Exception {
		String repositoryPath = "", originalName = "", newName = "", webServiceUrl = "";
		MultipartBody multipartBody;
		InputStream inputStream;

		repositoryPath = requestDataSet.getValue("repositoryPath");
		newName = requestDataSet.getValue("newName");
		originalName = requestDataSet.getValue("originalName");
		webServiceUrl = requestDataSet.getValue("webServiceUrl");

		paramEntity.setObject("repositoryPath", repositoryPath);
		paramEntity.setObject("originalName", originalName);
		paramEntity.setObject("newName", newName);
		paramEntity.setObject("webServiceUrl", webServiceUrl);
		paramEntity.setObject("requestDataSet", paramEntity.getRequestDataSet());

		multipartBody = RestServiceSupport.postForFileDownload(webServiceUrl, paramEntity);
		inputStream = RestServiceSupport.getFileInputStreamFromMultipartBody(multipartBody);
		setContentLength(inputStream.available());
		setContentDisposition("attachment; filename="+URLEncoder.encode(originalName, "UTF-8"));
		setInputStream(inputStream);

		return SUCCESS;
	}

	/*!
	 * Accessors
	 */
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}
}