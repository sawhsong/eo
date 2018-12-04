package zebra.export;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import zebra.data.DataSet;
import zebra.util.CommonUtil;

public class HtmlExportHelper extends ExportHelper {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(ExcelExportHelper.class);

	@Override
	public File createFile() throws Exception {
		File file = null, dir = null;
		String filePathName, contentString, exportDetails;
		OutputStreamWriter osWriter;

		if (sourceDataSet != null) {
			if (CommonUtil.isBlank(fileName)) {
				filePathName = TARGET_FILE_PATH+"/"+FILE_NAME_PREFIX+"."+fileExtention;
				setFileNameGenerated(FILE_NAME_PREFIX+"."+fileExtention);
			} else {
				filePathName = TARGET_FILE_PATH+"/"+FILE_NAME_PREFIX+"_"+fileName+"."+fileExtention;
				setFileName(fileName+"."+fileExtention);
				setFileNameGenerated(FILE_NAME_PREFIX+"_"+fileName+"."+fileExtention);
			}

			dir = new File(TARGET_FILE_PATH);
			if (!dir.isDirectory()) {
				dir.mkdirs();
			}

			file = new File(filePathName);
			osWriter = new OutputStreamWriter(new FileOutputStream(file, true), "utf-8");

			contentString = getSourceString();
			exportDetails = getExportDetails();
			contentString = CommonUtil.replace(contentString, "#EXPORT_DETAILS#", exportDetails);

			osWriter.write(contentString);
			osWriter.flush();
			osWriter.close();
		}
		return file;
	}

	private String getSourceString() throws Exception {
		BufferedReader bufferedReader;
		StringBuffer stringBuffer;
		String tempString, returnString;

		bufferedReader = new BufferedReader(new FileReader(getSourceFile()));
		stringBuffer = new StringBuffer();
		while ((tempString = bufferedReader.readLine()) != null) {
			stringBuffer.append(tempString + "\n");
		}

		returnString = stringBuffer.toString();
		bufferedReader.close();

		return returnString;
	}

	private String getExportDetails() throws Exception {
		DataSet dataSet = getDataSetToExport();
		StringBuffer str = new StringBuffer();

		// Title
		if (CommonUtil.isNotBlank(pageTitle)) {
			str.append("<table id=\"tblPageTitle\">").append("<tr>").append("<td>");
			str.append(pageTitle);
			str.append("</td>").append("</tr>").append("</table>");
		}

		// Column Header
		str.append("<table id=\"tblData\">");
		str.append("<thead>").append("<tr>");
		for (int i=0; i<dataSet.getColumnCnt(); i++) {
			str.append("<th>").append(dataSet.getName(i)).append("</th>");
		}
		str.append("</tr>").append("</thead>");

		// Data Rows
		str.append("<tbody>");
		for (int i=0; i<dataSet.getRowCnt(); i++) {
			str.append("<tr>");
			for (int j=0; j<dataSet.getColumnCnt(); j++) {
				str.append("<td>").append(dataSet.getValue(i, j)).append("</td>");
			}
			str.append("</tr>");
		}
		str.append("</tbody>");
		str.append("</table>");

		return str.toString();
	}
}