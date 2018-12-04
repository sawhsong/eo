/**************************************************************************************************
 * Framework Supporter - Download
 * Description
 * - 
 *************************************************************************************************/
package zebra.actionsupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.activation.DataHandler;

import zebra.base.Action;
import zebra.config.MemoryBean;
import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.util.ConfigUtil;

public class SoapServiceDownloadAction extends Action {
	private String contentType;
	private InputStream inputStream;
	private String contentDisposition;
	private long contentLength;

	public String execute() throws Exception {
		paramEntity = (ParamEntity)request.getAttribute("paramEntity");

		DataSet requestDataSet = paramEntity.getRequestDataSet();
		DataHandler dataHandler = null;
		String appRealPath = (String)MemoryBean.get("applicationRealPath");
		String tempDir = ConfigUtil.getProperty("path.dir.temp");
		String originalName = "";
		File file;
		FileOutputStream outputStream;

		originalName = requestDataSet.getValue("originalName");
		dataHandler = (DataHandler)paramEntity.getObject("dataHandler");

		// DataHandler.inputStream is not available here. After creating a temp file from datahandler, get inputstream from a temp file generated
		file = new File(appRealPath+"/"+tempDir+"/"+requestDataSet.getValue("newName"));
		outputStream = new FileOutputStream(file);
		dataHandler.writeTo(outputStream);

		setContentLength(file.length());
		setContentDisposition("attachment; filename=" + URLEncoder.encode(originalName, "UTF-8"));
		setInputStream(new FileInputStream(file));

		outputStream.flush();
		outputStream.close();

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