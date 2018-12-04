/**************************************************************************************************
 * Framework Supporter - Export
 * Description
 * - 
 *************************************************************************************************/
package zebra.actionsupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;

import zebra.base.Action;
import zebra.data.ParamEntity;
import zebra.util.CommonUtil;

public class ExportAction extends Action {
	private String contentType;
	private InputStream inputStream;
	private String contentDisposition;
	private long contentLength;

	public String execute() throws Exception {
		paramEntity = (ParamEntity)request.getAttribute("paramEntity");

		String fileName = (String)paramEntity.getFileNameToExport();
		File file = (File)paramEntity.getFileToExport();

		if (CommonUtil.isBlank(fileName)) {
			fileName = file.getName();
		}

		setContentLength(file.length());
		setContentDisposition("attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		setInputStream(new FileInputStream(file));

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