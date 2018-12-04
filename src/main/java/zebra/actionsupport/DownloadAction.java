/**************************************************************************************************
 * Framework Supporter - Download
 * Description
 * - 
 *************************************************************************************************/
package zebra.actionsupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;

import zebra.base.Action;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class DownloadAction extends Action {
	private String contentType;
	private InputStream inputStream;
	private String contentDisposition;
	private long contentLength;

	public String execute() throws Exception {
		String repositoryPath = "", originalName = "", newName = "";
		String delimiter = ConfigUtil.getProperty("delimiter.record");
		String repositoryPathArr[] = CommonUtil.split(requestDataSet.getValue("repositoryPath"), delimiter);
		String newNameArr[] = CommonUtil.split(requestDataSet.getValue("newName"), delimiter);
		String originalNameArr[] = CommonUtil.split(requestDataSet.getValue("originalName"), delimiter);

		/*!
		 * Caution : Some HTML objects are created for submission -> if the page is not refreshed those fields are submitted again
		 */
		repositoryPath = repositoryPathArr[repositoryPathArr.length - 1];
		newName = newNameArr[newNameArr.length - 1];
		originalName = originalNameArr[originalNameArr.length - 1];

		File file = new File(repositoryPath + "/" + newName);

		setContentLength(file.length());
		setContentDisposition("attachment; filename=" + URLEncoder.encode(originalName, "UTF-8"));
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