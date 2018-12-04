package zebra.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import zebra.export.ExcelExportHelper;
import zebra.export.ExportHelper;
import zebra.export.HtmlExportHelper;
import zebra.export.PdfExportHelper;

public class ExportUtil {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(ExportUtil.class);

	public static ExportHelper getExportHelper(String fileType) {
		ExportHelper helper = null;

		if (CommonUtil.containsIgnoreCase(fileType, "excel")) {
			helper = new ExcelExportHelper();
			helper.setFileType("excel");
			helper.setFileExtention("xlsx");
		} else if (CommonUtil.containsIgnoreCase(fileType, "pdf")) {
			helper = new PdfExportHelper();
			helper.setFileType("pdf");
			helper.setFileExtention("pdf");
		} else if (CommonUtil.containsIgnoreCase(fileType, "html")) {
			helper = new HtmlExportHelper();
			helper.setFileType("html");
			helper.setFileExtention("html");
		}

		return helper;
	}
}