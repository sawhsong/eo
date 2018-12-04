/**************************************************************************************************
 * Framework Supporter - Export
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
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class SoapServiceExportAction extends Action {
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
		String fileName = "DataExport_", fileExtension = "";
		File file;
		FileOutputStream outputStream;

		fileExtension = getFileExtension(requestDataSet.getValue("fileType"));
		fileName += requestDataSet.getValue("fileType")+"_Format_"+requestDataSet.getValue("dataRange")+"_DataRange_"+CommonUtil.getSysdate()+"."+fileExtension;
		dataHandler = (DataHandler)paramEntity.getObject("dataHandler");
		// DataHandler.inputStream is not available here. After creating a temp file from datahandler, get inputstream from a temp file generated
		file = new File(appRealPath+"/"+tempDir+"/"+fileName);
		outputStream = new FileOutputStream(file);
		dataHandler.writeTo(outputStream);

		setContentLength(file.length());
		setContentDisposition("attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
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

	private String getFileExtension(String fileType) {
		if (CommonUtil.containsIgnoreCase(fileType, "excel")) {
			return "xlsx";
		} else if (CommonUtil.containsIgnoreCase(fileType, "pdf")) {
			return "pdf";
		} else if (CommonUtil.containsIgnoreCase(fileType, "html")) {
			return "html";
		} else {
			return "txt";
		}
	}
}