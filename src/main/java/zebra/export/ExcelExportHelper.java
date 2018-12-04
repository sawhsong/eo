package zebra.export;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import zebra.data.DataSet;
import zebra.util.CommonUtil;

public class ExcelExportHelper extends ExportHelper {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(ExcelExportHelper.class);

	@Override
	public File createFile() throws Exception {
		String filePathName;
		File file = null, dir = null;
		FileOutputStream fout;
		SXSSFWorkbook wb = new SXSSFWorkbook();
		SXSSFSheet sheet;

		if (sourceDataSet != null) {
			if (CommonUtil.isBlank(fileName)) {
				sheet = wb.createSheet();
				filePathName = TARGET_FILE_PATH+"/"+FILE_NAME_PREFIX+"."+fileExtention;
				setFileNameGenerated(FILE_NAME_PREFIX+"."+fileExtention);
			} else {
				sheet = wb.createSheet(fileName);
				filePathName = TARGET_FILE_PATH+"/"+FILE_NAME_PREFIX+"_"+fileName+"."+fileExtention;
				setFileName(fileName+"."+fileExtention);
				setFileNameGenerated(FILE_NAME_PREFIX+"_"+fileName+"."+fileExtention);
			}

			dir = new File(TARGET_FILE_PATH);
			if (!dir.isDirectory()) {
				dir.mkdirs();
			}

			file = new File(filePathName);
			fout = new FileOutputStream(file);

			createSheet(wb, sheet);

			wb.write(fout);
			wb.close();
			fout.close();
		}
		return file;
	}

	/*!
	 * Supporter Methods
	 */
	private void createSheet(SXSSFWorkbook wb, SXSSFSheet sheet) throws Exception {
		int rowIndex = 0;
		DataSet dataSet = getDataSetToExport();
		SXSSFRow row;
		SXSSFCell cell;
		Map<String, CellStyle> styles = createStyles(wb);
		PrintSetup printSetup = sheet.getPrintSetup();

		printSetup.setLandscape(true);
		sheet.setHorizontallyCenter(true);

		// Title
		if (CommonUtil.isNotBlank(pageTitle)) {
			rowIndex = 1;
			row = sheet.createRow(0);

			row.setHeightInPoints(20);

			cell = row.createCell(0);
			cell.setCellValue(pageTitle);
			cell.setCellStyle(styles.get("pageTitle"));
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, dataSet.getColumnCnt()-1));
		} else {
			rowIndex = 0;
		}

		// Column Header
		row = sheet.createRow(rowIndex);
		row.setHeightInPoints(17);
		for (int i=0; i<dataSet.getColumnCnt(); i++) {
			cell = row.createCell(i);
			cell.setCellValue(dataSet.getName(i));
			cell.setCellStyle(styles.get("columnHeader"));
//			sheet.trackColumnForAutoSizing(i);
		}

		// Data Rows
		for (int i=0; i<dataSet.getRowCnt(); i++) {
			row = sheet.createRow(i+rowIndex+1);
			row.setHeightInPoints(15);

			for (int j=0; j<dataSet.getColumnCnt(); j++) {
				cell = row.createCell(j);
				cell.setCellValue(dataSet.getValue(i, j));
				cell.setCellStyle(styles.get("dataRows"));

				if (i == (dataSet.getRowCnt()-1)) {
//					sheet.autoSizeColumn(j);
				}
			}
		}

		sheet.createFreezePane(0, rowIndex+1, 0, rowIndex+1);
	}

	private Map<String, CellStyle> createStyles(SXSSFWorkbook wb) throws Exception {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		CellStyle style;

		// Title
		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short)14);

		style = wb.createCellStyle();
		style.setFont(titleFont);
		styles.put("pageTitle", style);

		// Column Header
		Font headerFont = wb.createFont();
		headerFont.setFontHeightInPoints((short)10);

		style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		style.setFont(headerFont);
		style.setWrapText(true);
		styles.put("columnHeader", style);

		// Column Header
		Font dataFont = wb.createFont();
		dataFont.setFontHeightInPoints((short)10);

		style = wb.createCellStyle();
		style.setFont(dataFont);
		style.setWrapText(true);
		styles.put("dataRows", style);

		return styles;
	}
}