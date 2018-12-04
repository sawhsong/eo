package zebra.export;

import java.io.File;

import zebra.config.MemoryBean;
import zebra.data.DataSet;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public abstract class ExportHelper {
	protected final String TARGET_FILE_PATH = ConfigUtil.getProperty("path.dir.temp");
//	protected final String TARGET_FILE_PATH = (String)MemoryBean.get("applicationRealPath")+ConfigUtil.getProperty("path.dir.temp");
	protected final String FILE_NAME_PREFIX = CommonUtil.getSysdate("yyyyMMddHHmmss")+"_"+CommonUtil.uid()+"_"+"Export";
	private final String SOURCE_FILE_PATH = (String)MemoryBean.get("applicationRealPath")+"/"+ConfigUtil.getProperty("path.export.sourceFile");
	protected DataSet sourceDataSet;
	protected String fileExtention;
	protected String fileType;
	protected String fileName;
	protected String fileNameGenerated;
	protected String pageTitle;
	protected String columnHeader[];
	protected int pdfWidth;

	/**
	 * Accessors
	 */
	public DataSet getSourceDataSet() {
		return sourceDataSet;
	}

	public void setSourceDataSet(DataSet sourceDataSet) {
		this.sourceDataSet = sourceDataSet;
	}

	public String getFileExtention() {
		return fileExtention;
	}

	public void setFileExtention(String fileExtention) {
		this.fileExtention = fileExtention;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileNameGenerated() {
		return fileNameGenerated;
	}

	public void setFileNameGenerated(String fileNameGenerated) {
		this.fileNameGenerated = fileNameGenerated;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String[] getColumnHeader() {
		return columnHeader;
	}

	public void setColumnHeader(String[] columnHeader) {
		this.columnHeader = columnHeader;
	}

	public int getPdfWidth() {
		return pdfWidth;
	}

	public void setPdfWidth(int pdfWidth) {
		this.pdfWidth = pdfWidth;
	}

	/**
	 * Methods
	 */
	protected DataSet getDataSetToExport() throws Exception {
		if (!(columnHeader == null || columnHeader.length <= 0)) {
			DataSet dsToExport = new DataSet();
			String headers[];

			for (int i=0; i<columnHeader.length; i++) {
				for (int j=0; j<sourceDataSet.getColumnCnt(); j++) {
					if (CommonUtil.equalsIgnoreCase(sourceDataSet.getName(j), columnHeader[i])) {
						dsToExport.addName(sourceDataSet.getName(j));
						break;
					}
				}
			}

			headers = dsToExport.getNames();

			for (int i=0; i<sourceDataSet.getRowCnt(); i++) {
				dsToExport.addRow();
				for (int j=0; j<headers.length; j++) {
					for (int k=0; k<sourceDataSet.getColumnCnt(); k++) {
						if (CommonUtil.equalsIgnoreCase(headers[j], sourceDataSet.getName(k))) {
							dsToExport.setValue(dsToExport.getRowCnt()-1, headers[j], sourceDataSet.getValue(i, k));
							break;
						}
					}
				}
			}
			return dsToExport;
		} else {
			return sourceDataSet;
		}
	}

	protected File getSourceFile() {
		String sourceName = "";

		if (CommonUtil.containsIgnoreCase(fileType, "excel")) {
			sourceName = "name.source.export.html";
		} else if (CommonUtil.containsIgnoreCase(fileType, "pdf")) {
			sourceName = "name.source.export.html";
		} else if (CommonUtil.containsIgnoreCase(fileType, "html")) {
			sourceName = "name.source.export.html";
		}

		return new File(SOURCE_FILE_PATH+"/"+ConfigUtil.getProperty(sourceName));
	}

	/**
	 * Must be overridden
	 * @return
	 * @throws Exception
	 */
	public abstract File createFile() throws Exception;
}