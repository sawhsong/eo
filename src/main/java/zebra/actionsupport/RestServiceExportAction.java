/**************************************************************************************************
 * Framework Supporter - Export through web services
 * Description
 * - 
 *************************************************************************************************/
package zebra.actionsupport;

import java.io.InputStream;
import java.net.URLEncoder;

import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import zebra.base.Action;
import zebra.wssupport.RestServiceSupport;

public class RestServiceExportAction extends Action {
	private String contentType;
	private InputStream inputStream;
	private String contentDisposition;
	private long contentLength;

	public String execute() throws Exception {
		MultipartBody multipartBody;
		InputStream inputStream;
		String fileName;

		paramEntity.setObject("fileType", requestDataSet.getValue("fileType"));
		paramEntity.setObject("dataRange", requestDataSet.getValue("dataRange"));
		paramEntity.setObject("requestDataSet", paramEntity.getRequestDataSet());

		multipartBody = RestServiceSupport.postForFileDownload(requestDataSet.getValue("webServiceUrl"), paramEntity);
		inputStream = RestServiceSupport.getFileInputStreamFromMultipartBody(multipartBody);
		fileName = RestServiceSupport.getFileNameFromMultipartBody(multipartBody);
		setContentLength(inputStream.available());
		setContentDisposition("attachment; filename="+URLEncoder.encode(fileName, "UTF-8"));
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