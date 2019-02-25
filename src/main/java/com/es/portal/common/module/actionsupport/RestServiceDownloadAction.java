package com.es.portal.common.module.actionsupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;

import zebra.base.Action;
import zebra.data.QueryAdvisor;
import zebra.util.ConfigUtil;
import zebra.wssupport.RestServiceSupport;

public class RestServiceDownloadAction extends Action {
	private String contentType;
	private InputStream inputStream;
	private String contentDisposition;
	private long contentLength;

	public String execute() throws Exception {
		String providerUrl = ConfigUtil.getProperty("webService.perci.url");
		String documentId = "", documentName = "", webServiceUrl = "";
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		InputStream inputStream;
		File file;

		documentId = requestDataSet.getValue("documentId");
		documentName = requestDataSet.getValue("documentName");
		webServiceUrl = requestDataSet.getValue("webServiceUrl");

		queryAdvisor.addVariable("documentId", documentId);
		queryAdvisor.addVariable("documentName", documentName);

		inputStream = RestServiceSupport.getFileDownload(providerUrl, webServiceUrl, queryAdvisor);
		file = saveFile(documentName, inputStream);
		setContentDisposition("attachment; filename="+URLEncoder.encode(documentName, "UTF-8"));
		setInputStream(new FileInputStream(file));
		setContentLength(file.length());

		return SUCCESS;
	}

	private static File saveFile(String name, InputStream inputStream) {
		String path = ConfigUtil.getProperty("path.dir.temp");
		File file = new File(path+"/"+name);
		try {
			if (file != null) {
				FileOutputStream outputStream = new FileOutputStream(file);

				int read = 0;
				byte[] bytes = new byte[1024];
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
				inputStream.close();
				outputStream.flush();
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
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