package zebra.example.common.bizservice.framework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import zebra.config.MemoryBean;
import zebra.data.DataSet;
import zebra.example.common.extend.BaseBiz;
import zebra.example.common.module.commoncode.ZebraCommonCodeManager;
import zebra.example.conf.resource.ormapper.dao.Dummy.DummyDao;
import zebra.exception.FrameworkException;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;
import zebra.util.FileUtil;

public class ZebraFrameworkBizServiceImpl extends BaseBiz implements ZebraFrameworkBizService {
	@Autowired
	private DummyDao dummyDao;

	/*!
	 * DTO Generator
	 */
	public boolean generateDto(String systemType, DataSet requestDataSet, DataSet tableInfoDataSet) throws Exception {
		String projectPackage = CommonUtil.lowerCase(ConfigUtil.getProperty("name.package.project"));
		String frameworkPackage = CommonUtil.lowerCase(ConfigUtil.getProperty("name.package.framework"));
		String compilePath = "/target/alpaca";
		String tableName = requestDataSet.getValue("tableName");

		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String srcPath = rootPath + ConfigUtil.getProperty("path.sourceFile");
		String sourcePath = srcPath + "/" + ConfigUtil.getProperty("name.source.javaDto");
		String dbVendor = CommonUtil.lowerCase(ConfigUtil.getProperty("db.vendor"));

		String dtoProjectPath = rootPath + CommonUtil.replace(ConfigUtil.getProperty("path.common.dtoProject"), "#DB_VENDOR#", dbVendor);
		String dtoFrameworkPath = rootPath + CommonUtil.replace(ConfigUtil.getProperty("path.common.dtoFwk"), "#DB_VENDOR#", dbVendor);

		String projectName = "", targetPath = "", packageString = "", tempString = "", sourceString = "";
		String columns = "", primaryKey = "", dateColumn = "", numberColumn = "", clobColumn = "";
		String accessors = "", defaultColumn = "", defaultValue = "", hibernateMethods = "";
		String toString = "", toXmlString = "", toJsonString = "", dataDefault = "";
		int numberOfPK = -1;
		File targetFile;

		try {
			if (CommonUtil.equalsIgnoreCase(systemType, "Project")) {
				projectName = projectPackage;
				targetPath = dtoProjectPath;
			} else if (CommonUtil.equalsIgnoreCase(systemType, "Framework")) {
				projectName = frameworkPackage+".example";
				targetPath = dtoFrameworkPath;
			}

			FileUtil.createFolder(targetPath);

			targetFile = new File(targetPath+"/"+CommonUtil.toCamelCase(tableName)+".java");
			createEmptyFile(targetFile);

			BufferedReader bufferedReader = new BufferedReader(new FileReader(sourcePath));
			StringBuffer stringBuffer = new StringBuffer();
			while ((tempString = bufferedReader.readLine()) != null) {
				stringBuffer.append(tempString+"\n");
			}
			OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");
			sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");

			packageString = CommonUtil.replace(CommonUtil.remove(targetPath, rootPath+"src/main/java/"), "/", ".");

			toString = "String str = \"\";\n\n";
			toXmlString = "String str = \"\";\n\n";
			toJsonString = "String str = \"\";\n\n";

			for (int i=0; i<tableInfoDataSet.getRowCnt(); i++) {
				String dataType = tableInfoDataSet.getValue(i, "DATA_TYPE");
				String convertedDataType = getDataTypeString(dataType);
				String colNameUpperCamelCase = CommonUtil.toCamelCaseStartUpperCase(tableInfoDataSet.getValue(i, "COLUMN_NAME"));
				String colNameLowerCamelCase = CommonUtil.toCamelCaseStartLowerCase(tableInfoDataSet.getValue(i, "COLUMN_NAME"));

				columns += (CommonUtil.isBlank(columns) ? "" : "\t");
				columns += "private "+convertedDataType+" "+colNameLowerCamelCase+";\n";
				columns += "\tprivate String "+tableInfoDataSet.getValue(i, "COLUMN_NAME")+";\n";

				accessors += (CommonUtil.isBlank(accessors) ? "" : "\t");
				accessors += "public "+convertedDataType+" get"+colNameUpperCamelCase+"() {\n";
				accessors += "\t\treturn "+colNameLowerCamelCase+";\n";
				accessors += "\t}\n\n";
				accessors += "\tpublic void set"+colNameUpperCamelCase+"("+convertedDataType+" "+colNameLowerCamelCase+") throws Exception {\n";
				accessors += "\t\tthis."+colNameLowerCamelCase+" = "+colNameLowerCamelCase+";\n";
				if ((CommonUtil.equalsIgnoreCase(dataType, "NUMBER")) || (CommonUtil.equalsIgnoreCase(dataType, "DATE"))) {
					accessors += "\t\tsetValueFromAccessor(\""+tableInfoDataSet.getValue(i, "COLUMN_NAME")+"\", CommonUtil.toString("+colNameLowerCamelCase+"));\n";
				} else {
					accessors += "\t\tsetValueFromAccessor(\""+tableInfoDataSet.getValue(i, "COLUMN_NAME")+"\", "+colNameLowerCamelCase+");\n";
				}
				accessors += "\t}\n";
				if (i != (tableInfoDataSet.getRowCnt() - 1)) {
					accessors += "\n";
				}

				if (CommonUtil.containsIgnoreCase(tableInfoDataSet.getValue(i, "CONSTRAINT_TYPE"), "PK")) {
					numberOfPK++;
					primaryKey += CommonUtil.isBlank(primaryKey) ? tableInfoDataSet.getValue(i, "COLUMN_NAME") : ","+tableInfoDataSet.getValue(i, "COLUMN_NAME");
				}

				if (CommonUtil.equalsIgnoreCase(dataType, "DATE")) {
					dateColumn += CommonUtil.isBlank(dateColumn) ? tableInfoDataSet.getValue(i, "COLUMN_NAME") : ","+tableInfoDataSet.getValue(i, "COLUMN_NAME");
				}

				if (CommonUtil.equalsIgnoreCase(dataType, "NUMBER")) {
					numberColumn += CommonUtil.isBlank(numberColumn) ? tableInfoDataSet.getValue(i, "COLUMN_NAME") : ","+tableInfoDataSet.getValue(i, "COLUMN_NAME");
				}

				if (CommonUtil.equalsIgnoreCase(dataType, "CLOB")) {
					clobColumn += CommonUtil.isBlank(clobColumn) ? tableInfoDataSet.getValue(i, "COLUMN_NAME") : ","+tableInfoDataSet.getValue(i, "COLUMN_NAME");
				}

				dataDefault = CommonUtil.replace(tableInfoDataSet.getValue(i, "DATA_DEFAULT"), " ", "");
				if (!(CommonUtil.isBlank(dataDefault) || CommonUtil.equalsIgnoreCase(dataDefault, "''") || CommonUtil.equalsIgnoreCase(dataDefault, "'"))) {
					defaultColumn += CommonUtil.isBlank(defaultColumn) ? tableInfoDataSet.getValue(i, "COLUMN_NAME") : ","+tableInfoDataSet.getValue(i, "COLUMN_NAME");

					if (CommonUtil.equalsIgnoreCase(dataType, "CLOB")) {
						defaultValue += CommonUtil.isBlank(defaultValue) ? "EMPTY_CLOB()" : ",EMPTY_CLOB()";
					} else {
						defaultValue += CommonUtil.isBlank(defaultValue) ? CommonUtil.replace(dataDefault, "'", "") : ","+CommonUtil.replace(dataDefault, "'", "");
					}
				}

				toString += "\t\tstr += \""+colNameLowerCamelCase+" : \"+"+colNameLowerCamelCase+"+\"\\n\";\n";
				toXmlString += "\t\tstr += \"<column name=\\\""+colNameLowerCamelCase+"\\\" value=\\\"\"+"+colNameLowerCamelCase+"+\"\\\">\";\n";
				toJsonString += "\t\tstr += \"\\\""+colNameLowerCamelCase+"\\\":\\\"\"+"+colNameLowerCamelCase+"+\"\\\", \";\n";
			}

			if (numberOfPK > 0) {
				hibernateMethods += "public boolean equals(Object object) {\n";
				hibernateMethods += "\t\tif (!(object instanceof "+CommonUtil.toCamelCase(tableName)+")) {\n";
				hibernateMethods += "\t\t\treturn false;\n";
				hibernateMethods += "\t\t}\n";
				hibernateMethods += "\t\treturn true;\n";
				hibernateMethods += "\t}\n";
				hibernateMethods += "\n";
				hibernateMethods += "\tpublic int hashCode() {\n";
				hibernateMethods += "\t\treturn 1;\n";
				hibernateMethods += "\t}\n";
			}

			columns += "\tprivate String insertUserName;\n";
			columns += "\tprivate String INSERT_USER_NAME;\n";
			columns += "\tprivate String updateUserName;\n";
			columns += "\tprivate String UPDATE_USER_NAME;\n";

			accessors += "\n";
			accessors += "\tpublic String getInsertUserName() {\n";
			accessors += "\t\treturn insertUserName;\n";
			accessors += "\t}\n";

			accessors += "\n";
			accessors += "\tpublic void setInsertUserName(String insertUserName) throws Exception {\n";
			accessors += "\t\tthis.insertUserName = insertUserName;\n";
			accessors += "\t\tsetValueFromAccessor(\"INSERT_USER_NAME\", insertUserName);\n";
			accessors += "\t}\n";

			accessors += "\n";
			accessors += "\tpublic String getUpdateUserName() {\n";
			accessors += "\t\treturn updateUserName;\n";
			accessors += "\t}\n";

			accessors += "\n";
			accessors += "\tpublic void setUpdateUserName(String updateUserName) throws Exception {\n";
			accessors += "\t\tthis.updateUserName = updateUserName;\n";
			accessors += "\t\tsetValueFromAccessor(\"UPDATE_USER_NAME\", updateUserName);\n";
			accessors += "\t}\n";

			toString += "\t\tstr += \"insertUserName : \"+insertUserName+\"\\n\";\n";
			toString += "\t\tstr += \"updateUserName : \"+updateUserName+\"\\n\";\n";
			toXmlString += "\t\tstr += \"<column name=\\\"insertUserName\\\" value=\\\"\"+insertUserName+\"\\\">\";\n";
			toXmlString += "\t\tstr += \"<column name=\\\"updateUserName\\\" value=\\\"\"+updateUserName+\"\\\">\";\n";
			toJsonString += "\t\tstr += \"\\\"insertUserName\\\":\\\"\"+insertUserName+\"\\\", \";\n";
			toJsonString += "\t\tstr += \"\\\"updateUserName\\\":\\\"\"+updateUserName+\"\\\"\";\n";

			toString += "\n\t\treturn str;";
			toXmlString += "\n\t\treturn str;";
			toJsonString += "\n\t\treturn str;";

			sourceString = CommonUtil.replace(sourceString, "#TABLE_NAME#", tableName);
			sourceString = CommonUtil.replace(sourceString, "#TABLE_DESCRIPTION#", tableInfoDataSet.getValue("TABLE_DESCRIPTION"));
			sourceString = CommonUtil.replace(sourceString, "#PACKAGE#", packageString);
			sourceString = CommonUtil.replace(sourceString, "#PROJECT_NAME#", projectName);
			sourceString = CommonUtil.replace(sourceString, "#TABLE_NAME_CAMELCASE#", CommonUtil.toCamelCaseStartUpperCase(tableName));
			sourceString = CommonUtil.replace(sourceString, "#COLUMNS#", columns);
			sourceString = CommonUtil.replace(sourceString, "#FRW_VAR_PRIMARY_KEY#", primaryKey);
			sourceString = CommonUtil.replace(sourceString, "#FRW_VAR_DATE_COLUMN#", dateColumn);
			sourceString = CommonUtil.replace(sourceString, "#FRW_VAR_NUMBER_COLUMN#", numberColumn);
			sourceString = CommonUtil.replace(sourceString, "#FRW_VAR_CLOB_COLUMN#", clobColumn);
			sourceString = CommonUtil.replace(sourceString, "#FRW_VAR_DEFAULT_COLUMN#", defaultColumn);
			sourceString = CommonUtil.replace(sourceString, "#FRW_VAR_DEFAULT_VALUE#", defaultValue);
			sourceString = CommonUtil.replace(sourceString, "#ACCESSORS#", accessors);
			sourceString = CommonUtil.replace(sourceString, "#HIBERNATE_METHODS#", hibernateMethods);
			sourceString = CommonUtil.replace(sourceString, "#TO_STRING#", toString);
			sourceString = CommonUtil.replace(sourceString, "#TO_XML_STRING#", toXmlString);
			sourceString = CommonUtil.replace(sourceString, "#TO_JSON_STRING#", toJsonString);

			osWriter.write(sourceString);
			osWriter.flush();
			osWriter.close();
			bufferedReader.close();

			return true;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	public boolean generateHibernateDtoConfig(String systemType, DataSet requestDataSet, DataSet tableInfoDataSet) throws Exception {
		String compilePath = "/target/alpaca";
		String tableName = requestDataSet.getValue("tableName");

		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String srcPath = rootPath + ConfigUtil.getProperty("path.sourceFile");
		String sourcePath = srcPath + "/" + ConfigUtil.getProperty("name.source.hibernateDtoConfig");
		String dbVendor = CommonUtil.lowerCase(ConfigUtil.getProperty("db.vendor"));

		String dtoProjectPath = rootPath + CommonUtil.replace(ConfigUtil.getProperty("path.common.dtoProject"), "#DB_VENDOR#", dbVendor);
		String dtoFrameworkPath = rootPath + CommonUtil.replace(ConfigUtil.getProperty("path.common.dtoFwk"), "#DB_VENDOR#", dbVendor);
		String hibernateConfProjectPath = rootPath + CommonUtil.replace(ConfigUtil.getProperty("path.source.hibernateDtoConfProject"), "#DB_VENDOR#", dbVendor);
		String hibernateConfFrameworkPath = rootPath + CommonUtil.replace(ConfigUtil.getProperty("path.source.hibernateDtoConfFwk"), "#DB_VENDOR#", dbVendor);

		String autoFillString = ConfigUtil.getProperty("db.constants.autoFillString");
		String whereString = ConfigUtil.getProperty("db.constants.whereString");
		String orderByString = ConfigUtil.getProperty("db.constants.orderByString");
		String columnsToUpdateString = ConfigUtil.getProperty("db.constants.columnsToUpdateString");

		String packageString = "", targetPath = "", tempString = "", sourceString = "", dtoPath = "";
		String columns = "", primaryKey = "", qrySelectAll = "", qrySelectAllDetail = "", qryUpdateColumn = "";
		String qryDelete = "", qryInsertColumn = "", qryInsertValues = "", qryUpdate = "", qryUpdateDetails = "";
		int primaryKeyColumnCnt = 0;
		File targetFile;

		try {
			if (CommonUtil.equalsIgnoreCase(systemType, "Project")) {
				targetPath = hibernateConfProjectPath;
				dtoPath = dtoProjectPath;
			} else if (CommonUtil.equalsIgnoreCase(systemType, "Framework")) {
				targetPath = hibernateConfFrameworkPath;
				dtoPath = dtoFrameworkPath;
			}

			primaryKeyColumnCnt = getPrimaryKeyColumnCnt(tableInfoDataSet);

			targetFile = new File(targetPath + "/" + CommonUtil.toCamelCase(tableName) + ".hbm.xml");
			createEmptyFile(targetFile);

			BufferedReader bufferedReader = new BufferedReader(new FileReader(sourcePath));
			StringBuffer stringBuffer = new StringBuffer();
			while ((tempString = bufferedReader.readLine()) != null) {
				stringBuffer.append(tempString + "\n");
			}
			OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");
			sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");

			packageString = CommonUtil.replace(CommonUtil.remove(dtoPath, rootPath + "src/main/java/"), "/", ".");

			if (primaryKeyColumnCnt > 1) {
				primaryKey += "<composite-id>\n";
			}

			qrySelectAll += "SELECT ";

			qryInsertColumn += "INSERT INTO "+tableName+" (\n";
			qryInsertValues += "\t\tVALUES (\n";

			qryUpdate += "UPDATE "+tableName+"\n";
			qryUpdate += "\t\t   SET ";

			qryUpdateColumn += "UPDATE "+tableName+"\n";
			qryUpdateColumn += "\t\t   SET "+columnsToUpdateString+"\n";
			qryUpdateColumn += "\t\t WHERE 1 = 1\n";
			qryUpdateColumn += "\t\t "+whereString;

			qryDelete += "DELETE "+tableName+"\n";
			qryDelete += "\t\t WHERE 1 = 1\n";
			qryDelete += "\t\t   "+whereString;

			for (int i=0; i<tableInfoDataSet.getRowCnt(); i++) {
				String dataType = tableInfoDataSet.getValue(i, "DATA_TYPE");
				String dataLength = tableInfoDataSet.getValue(i, "DATA_LENGTH");
				String colNameOriginal = tableInfoDataSet.getValue(i, "COLUMN_NAME");
				String colNameLowerCamelCase = CommonUtil.toCamelCaseStartLowerCase(colNameOriginal);

				if (CommonUtil.containsIgnoreCase(tableInfoDataSet.getValue(i, "CONSTRAINT_TYPE"), "PK")) {
					if (primaryKeyColumnCnt > 1) {
						primaryKey += "\t\t\t<key-property name=\""+colNameLowerCamelCase+"\" column=\""+colNameOriginal+"\"";
						primaryKey += getXmlTypeProperty(dataType, dataLength);
						primaryKey += "/>\n";
					} else {
						primaryKey += "<id name=\""+colNameLowerCamelCase+"\" column=\""+colNameOriginal+"\"";
						primaryKey += getXmlTypeProperty(dataType, dataLength);
						primaryKey += "></id>";
					}
				} else {
					columns += (CommonUtil.isBlank(columns) ? "" : "\t\t");
					columns += "<property name=\""+colNameLowerCamelCase+"\" column=\""+colNameOriginal+"\"";
					columns += (CommonUtil.equalsIgnoreCase(tableInfoDataSet.getValue(i, "NULLABLE"), "N") ? " not-null=\"true\"" : "");
					columns += getXmlTypeProperty(dataType, dataLength);
					columns += "/>";
					columns += (i == tableInfoDataSet.getRowCnt()-1) ? "" : "\n";
				}

				if (CommonUtil.equalsIgnoreCase(dataType, "date")) {
					qrySelectAllDetail += CommonUtil.isBlank(qrySelectAllDetail) ? "TO_CHAR("+colNameOriginal+", 'yyyymmddhh24miss') AS "+colNameOriginal : "\t\t       TO_CHAR("+colNameOriginal+", 'yyyymmddhh24miss') AS "+colNameOriginal;
				} else {
					qrySelectAllDetail += CommonUtil.isBlank(qrySelectAllDetail) ? colNameOriginal : "\t\t       "+colNameOriginal;
				}
				qrySelectAllDetail += (i == tableInfoDataSet.getRowCnt() - 1) ? "\n" : ",\n";

				qryInsertColumn += "\t\t       "+colNameOriginal;
				qryInsertColumn += (i == tableInfoDataSet.getRowCnt()-1) ? "\n" : ",\n";

				qryInsertValues += "\t\t       ${"+colNameOriginal+"}";
				qryInsertValues += (i == tableInfoDataSet.getRowCnt()-1) ? "\n" : ",\n";

				if (!CommonUtil.containsIgnoreCase(tableInfoDataSet.getValue(i, "CONSTRAINT_TYPE"), "PK")) {
					qryUpdateDetails += CommonUtil.isBlank(qryUpdateDetails) ? colNameOriginal : "\t\t       "+colNameOriginal;
					qryUpdateDetails += " = ${"+colNameOriginal+"}";
					qryUpdateDetails += (i == tableInfoDataSet.getRowCnt()-1) ? "\n" : ",\n";
				}
			}

			if (primaryKeyColumnCnt > 1) {
				primaryKey += "\t\t</composite-id>";
			}

			if (CommonUtil.isBlank(primaryKey)) {
				primaryKey = "<id column=\"ROWID\" type=\"java.lang.String\"/>";
			}

			qrySelectAll += qrySelectAllDetail + "\t\t  FROM "+tableName+"\n";
			qrySelectAll += "\t\t WHERE 1 = 1\n";
			qrySelectAll += "\t\t "+autoFillString+"\n";
			qrySelectAll += "\t\t "+whereString+"\n";
			qrySelectAll += "\t\t "+orderByString+"\n";
			qrySelectAll += "\t\t "+"FOR UPDATE";

			qryInsertColumn += "\t\t       )\n";
			qryInsertValues += "\t\t       )";

			qryUpdate += qryUpdateDetails;
			qryUpdate += "\t\t WHERE 1 = 1\n";
			qryUpdate += "\t\t   " + whereString;

			sourceString = CommonUtil.replace(sourceString, "#TABLE_NAME#", tableName);
			sourceString = CommonUtil.replace(sourceString, "#TABLE_DESCRIPTION#", tableInfoDataSet.getValue("TABLE_DESCRIPTION"));
			sourceString = CommonUtil.replace(sourceString, "#PACKAGE#", packageString);
			sourceString = CommonUtil.replace(sourceString, "#TABLE_NAME_CAMELCASE#", CommonUtil.toCamelCaseStartUpperCase(tableName));
			sourceString = CommonUtil.replace(sourceString, "#TABLE_NAME_CAMELCASE_LOWER#", CommonUtil.toCamelCaseStartLowerCase(tableName));
			sourceString = CommonUtil.replace(sourceString, "#PK_COLUMN#", primaryKey);
			sourceString = CommonUtil.replace(sourceString, "#COLUMNS#", columns);
			sourceString = CommonUtil.replace(sourceString, "#QUERY_SELECT_ALL#", qrySelectAll);
			sourceString = CommonUtil.replace(sourceString, "#QUERY_INSERT#", qryInsertColumn + qryInsertValues);
			sourceString = CommonUtil.replace(sourceString, "#QUERY_UPDATE#", qryUpdate);
			sourceString = CommonUtil.replace(sourceString, "#QUERY_UPDATE_COLUMNS#", qryUpdateColumn);
			sourceString = CommonUtil.replace(sourceString, "#QUERY_DELETE#", qryDelete);

			osWriter.write(sourceString);
			osWriter.flush();
			osWriter.close();
			bufferedReader.close();

			return true;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	public boolean generateMybatisDtoMapper(String systemType, DataSet requestDataSet, DataSet tableInfoDataSet) throws Exception {
		String compilePath = "/target/alpaca";
		String tableName = requestDataSet.getValue("tableName");

		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String srcPath = rootPath + ConfigUtil.getProperty("path.sourceFile");
		String sourcePath = srcPath + "/" + ConfigUtil.getProperty("name.source.mybatisDtoMapper");
		String dbVendor = CommonUtil.lowerCase(ConfigUtil.getProperty("db.vendor"));

		String mybatisMapperProjectPath = rootPath + CommonUtil.replace(ConfigUtil.getProperty("path.source.mybatisDtoMapperClassProject"), "#DB_VENDOR#", dbVendor);
		String mybatisMapperFrameworkPath = rootPath + CommonUtil.replace(ConfigUtil.getProperty("path.source.mybatisDtoMapperClassFwk"), "#DB_VENDOR#", dbVendor);

		String targetPath = "", packageString = "", tempString = "", sourceString = "";
		File targetFile;

		try {
			if (CommonUtil.equalsIgnoreCase(systemType, "Project")) {
				targetPath = mybatisMapperProjectPath;
			} else if (CommonUtil.equalsIgnoreCase(systemType, "Framework")) {
				targetPath = mybatisMapperFrameworkPath;
			}
			targetPath += "/"+CommonUtil.toCamelCase(tableName);

			FileUtil.createFolder(targetPath);

			targetFile = new File(targetPath+"/"+CommonUtil.toCamelCase(tableName)+"Mapper.java");
			createEmptyFile(targetFile);

			BufferedReader bufferedReader = new BufferedReader(new FileReader(sourcePath));
			StringBuffer stringBuffer = new StringBuffer();
			while ((tempString = bufferedReader.readLine()) != null) {
				stringBuffer.append(tempString + "\n");
			}
			OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");
			sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");

			packageString = CommonUtil.replace(CommonUtil.remove(targetPath, rootPath + "src/main/java/"), "/", ".");

			sourceString = CommonUtil.replace(sourceString, "#TABLE_NAME#", tableName);
			sourceString = CommonUtil.replace(sourceString, "#TABLE_DESCRIPTION#", tableInfoDataSet.getValue("TABLE_DESCRIPTION"));
			sourceString = CommonUtil.replace(sourceString, "#PACKAGE#", packageString);
			sourceString = CommonUtil.replace(sourceString, "#TABLE_NAME_CAMELCASE#", CommonUtil.toCamelCaseStartUpperCase(tableName));

			osWriter.write(sourceString);
			osWriter.flush();
			osWriter.close();
			bufferedReader.close();

			return true;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	public boolean generateMybatisDtoMapperXml(String systemType, DataSet requestDataSet, DataSet tableInfoDataSet) throws Exception {
		String compilePath = "/target/alpaca";
		String tableName = requestDataSet.getValue("tableName");

		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String srcPath = rootPath + ConfigUtil.getProperty("path.sourceFile");
		String sourcePath = srcPath + "/" + ConfigUtil.getProperty("name.source.mybatisDtoMapperXml");
		String dbVendor = CommonUtil.lowerCase(ConfigUtil.getProperty("db.vendor"));

		String dtoProjectPath = rootPath + CommonUtil.replace(ConfigUtil.getProperty("path.common.dtoProject"), "#DB_VENDOR#", dbVendor);
		String dtoFrameworkPath = rootPath + CommonUtil.replace(ConfigUtil.getProperty("path.common.dtoFwk"), "#DB_VENDOR#", dbVendor);
		String mybatisMapperProjectPath = rootPath + CommonUtil.replace(ConfigUtil.getProperty("path.source.mybatisDtoMapperXmlProject"), "#DB_VENDOR#", dbVendor);
		String mybatisMapperFrameworkPath = rootPath + CommonUtil.replace(ConfigUtil.getProperty("path.source.mybatisDtoMapperXmlFwk"), "#DB_VENDOR#", dbVendor);

		String autoFillString = ConfigUtil.getProperty("db.constants.autoFillString");
		String whereString = ConfigUtil.getProperty("db.constants.whereString");
		String orderByString = ConfigUtil.getProperty("db.constants.orderByString");
		String columnsToUpdateString = ConfigUtil.getProperty("db.constants.columnsToUpdateString");

		String packageString = "", targetPath = "", tempString = "", sourceString = "", dtoPath = "";
		String qrySelectAll = "", qrySelectAllDetail = "", qryUpdateColumn = "";
		String dtoName = "", qryInsertWithDtoValues = "", qryUpdateWithDto = "", qryUpdateWithDtoDetails = "";
		String qryDelete = "", qryInsertColumn = "", qryInsertValues = "", qryUpdate = "", qryUpdateDetails = "";
		File targetFile;

		try {
			if (CommonUtil.equalsIgnoreCase(systemType, "Project")) {
				targetPath = mybatisMapperProjectPath;
				dtoPath = dtoProjectPath;
			} else if (CommonUtil.equalsIgnoreCase(systemType, "Framework")) {
				targetPath = mybatisMapperFrameworkPath;
				dtoPath = dtoFrameworkPath;
			}
			targetPath += "/"+CommonUtil.toCamelCase(tableName);

			FileUtil.createFolder(targetPath);

			targetFile = new File(targetPath+"/"+CommonUtil.toCamelCase(tableName)+"Mapper.xml");
			createEmptyFile(targetFile);

			BufferedReader bufferedReader = new BufferedReader(new FileReader(sourcePath));
			StringBuffer stringBuffer = new StringBuffer();
			while ((tempString = bufferedReader.readLine()) != null) {
				stringBuffer.append(tempString + "\n");
			}
			OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");
			sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");

			packageString = CommonUtil.replace(CommonUtil.remove(targetPath, rootPath + "src/main/resources/"), "/", ".");

			dtoName = CommonUtil.replace(CommonUtil.remove(dtoPath, rootPath + "src/main/java/"), "/", ".")+"."+CommonUtil.toCamelCase(tableName);

			qrySelectAll += "SELECT ";

			qryInsertColumn += "INSERT INTO "+tableName+" (\n";
			qryInsertValues += "\t\tVALUES (\n";
			qryInsertWithDtoValues += qryInsertValues;

			qryUpdate += "UPDATE "+tableName+"\n";
			qryUpdate += "\t\t   SET ";
			qryUpdateWithDto += qryUpdate;

			qryUpdateColumn += "UPDATE "+tableName+"\n";
			qryUpdateColumn += "\t\t   SET "+columnsToUpdateString+"\n";
			qryUpdateColumn += "\t\t WHERE 1 = 1\n";
			qryUpdateColumn += "\t\t "+whereString;

			qryDelete += "DELETE "+tableName+"\n";
			qryDelete += "\t\t WHERE 1 = 1\n";
			qryDelete += "\t\t "+whereString;

			for (int i=0; i<tableInfoDataSet.getRowCnt(); i++) {
				String colNameOriginal = tableInfoDataSet.getValue(i, "COLUMN_NAME");
				String colNameLowerCamelCase = CommonUtil.toCamelCaseStartLowerCase(colNameOriginal);
				String dataType = tableInfoDataSet.getValue(i, "DATA_TYPE");

				if (CommonUtil.equalsIgnoreCase(dataType, "date")) {
					qrySelectAllDetail += CommonUtil.isBlank(qrySelectAllDetail) ? "TO_CHAR("+colNameOriginal+", 'yyyymmddhh24miss') AS "+colNameOriginal : "\t\t       TO_CHAR("+colNameOriginal+", 'yyyymmddhh24miss') AS "+colNameOriginal;
				} else {
					qrySelectAllDetail += CommonUtil.isEmpty(qrySelectAllDetail) ? colNameOriginal : "\t\t       "+colNameOriginal;
				}
				qrySelectAllDetail += (i == tableInfoDataSet.getRowCnt()-1) ? "\n" : ",\n";

				qryInsertColumn += "\t\t       "+colNameOriginal;
				qryInsertColumn += (i == tableInfoDataSet.getRowCnt()-1) ? "\n" : ",\n";

				qryInsertValues += "\t\t       ${"+colNameOriginal+"}";
				qryInsertValues += (i == tableInfoDataSet.getRowCnt()-1) ? "\n" : ",\n";

				qryInsertWithDtoValues += "\t\t       #{"+colNameLowerCamelCase+",jdbcType="+getDataTypeStringForMyBatisXml(dataType)+"}";
				qryInsertWithDtoValues += (i == tableInfoDataSet.getRowCnt()-1) ? "\n" : ",\n";

				if (!CommonUtil.containsIgnoreCase(tableInfoDataSet.getValue(i, "CONSTRAINT_TYPE"), "PK")) {
					qryUpdateDetails += CommonUtil.isBlank(qryUpdateDetails) ? colNameOriginal : "\t\t       "+colNameOriginal;
					qryUpdateDetails += " = ${"+colNameOriginal+"}";
					qryUpdateDetails += (i == tableInfoDataSet.getRowCnt()-1) ? "\n" : ",\n";

					qryUpdateWithDtoDetails += CommonUtil.isBlank(qryUpdateWithDtoDetails) ? colNameOriginal : "\t\t       "+colNameOriginal;
					qryUpdateWithDtoDetails += " = #{"+colNameLowerCamelCase+",jdbcType="+getDataTypeStringForMyBatisXml(dataType)+"}";
					qryUpdateWithDtoDetails += (i == tableInfoDataSet.getRowCnt()-1) ? "\n" : ",\n";
				}
			}

			qrySelectAll += qrySelectAllDetail + "\t\t  FROM "+tableName+"\n";
			qrySelectAll += "\t\t WHERE 1 = 1\n";
			qrySelectAll += "\t\t "+autoFillString+"\n";
			qrySelectAll += "\t\t "+whereString+"\n";
			qrySelectAll += "\t\t "+orderByString+"\n";
			qrySelectAll += "\t\t "+"FOR UPDATE";

			qryInsertColumn += "\t\t       )\n";
			qryInsertValues += "\t\t       )";
			qryInsertWithDtoValues += "\t\t       )";

			qryUpdate += qryUpdateDetails;
			qryUpdate += "\t\t WHERE 1 = 1\n";
			qryUpdate += "\t\t " + whereString;
			qryUpdateWithDto += qryUpdateWithDtoDetails;
			qryUpdateWithDto += "\t\t WHERE 1 = 1\n";
			qryUpdateWithDto += "\t\t ${additionalAttributesForUpdateWithDto}";

			sourceString = CommonUtil.replace(sourceString, "#TABLE_NAME#", tableName);
			sourceString = CommonUtil.replace(sourceString, "#TABLE_DESCRIPTION#", tableInfoDataSet.getValue("TABLE_DESCRIPTION"));
			sourceString = CommonUtil.replace(sourceString, "#PACKAGE#", packageString);
			sourceString = CommonUtil.replace(sourceString, "#TABLE_NAME_CAMELCASE#", CommonUtil.toCamelCaseStartUpperCase(tableName));
			sourceString = CommonUtil.replace(sourceString, "#DTO_NAME#", dtoName);
			sourceString = CommonUtil.replace(sourceString, "#QUERY_SELECT_ALL#", qrySelectAll);
			sourceString = CommonUtil.replace(sourceString, "#QUERY_INSERT#", qryInsertColumn + qryInsertValues);
			sourceString = CommonUtil.replace(sourceString, "#QUERY_INSERT_WITH_DTO#", qryInsertColumn + qryInsertWithDtoValues);
			sourceString = CommonUtil.replace(sourceString, "#QUERY_UPDATE#", qryUpdate);
			sourceString = CommonUtil.replace(sourceString, "#QUERY_UPDATE_WITH_DTO#", qryUpdateWithDto);
			sourceString = CommonUtil.replace(sourceString, "#QUERY_UPDATE_COLUMNS#", qryUpdateColumn);
			sourceString = CommonUtil.replace(sourceString, "#QUERY_DELETE#", qryDelete);

			osWriter.write(sourceString);
			osWriter.flush();
			osWriter.close();
			bufferedReader.close();

			return true;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	public boolean generateDao(String systemType, DataSet requestDataSet, DataSet tableInfoDataSet) throws Exception {
		String compilePath = "/target/alpaca";
		String tableName = requestDataSet.getValue("tableName");

		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String srcPath = rootPath + ConfigUtil.getProperty("path.sourceFile");
		String sourcePath = srcPath + "/" + ConfigUtil.getProperty("name.source.javaDao");

		String daoProjectPath = rootPath + ConfigUtil.getProperty("path.common.daoProject");
		String daoFrameworkPath = rootPath + ConfigUtil.getProperty("path.common.daoFwk");

		String targetPath = "", packageString = "", tempString = "", sourceString = "";
		File targetFile;

		try {
			if (CommonUtil.equalsIgnoreCase(systemType, "Project")) {
				targetPath = daoProjectPath;
			} else if (CommonUtil.equalsIgnoreCase(systemType, "Framework")) {
				targetPath = daoFrameworkPath;
			}
			targetPath += "/"+CommonUtil.toCamelCase(tableName);

			FileUtil.createFolder(targetPath);

			targetFile = new File(targetPath+"/"+CommonUtil.toCamelCase(tableName)+"Dao.java");
			createEmptyFile(targetFile);

			BufferedReader bufferedReader = new BufferedReader(new FileReader(sourcePath));
			StringBuffer stringBuffer = new StringBuffer();
			while ((tempString = bufferedReader.readLine()) != null) {
				stringBuffer.append(tempString+"\n");
			}
			OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");
			sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");

			packageString = CommonUtil.replace(CommonUtil.remove(targetPath, rootPath+"src/main/java/"), "/", ".");

			sourceString = CommonUtil.replace(sourceString, "#TABLE_NAME#", tableName);
			sourceString = CommonUtil.replace(sourceString, "#TABLE_DESCRIPTION#", tableInfoDataSet.getValue("TABLE_DESCRIPTION"));
			sourceString = CommonUtil.replace(sourceString, "#PACKAGE#", packageString);
			sourceString = CommonUtil.replace(sourceString, "#TABLE_NAME_CAMELCASE#", CommonUtil.toCamelCaseStartUpperCase(tableName));

			osWriter.write(sourceString);
			osWriter.flush();
			osWriter.close();
			bufferedReader.close();

			return true;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	public boolean generateHDaoImpl(String systemType, DataSet requestDataSet, DataSet tableInfoDataSet) throws Exception {
		String projectPackage = CommonUtil.lowerCase(ConfigUtil.getProperty("name.package.project"));
		String frameworkPackage = CommonUtil.lowerCase(ConfigUtil.getProperty("name.package.framework"));
		String compilePath = "/target/alpaca";
		String tableName = requestDataSet.getValue("tableName");

		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String srcPath = rootPath + ConfigUtil.getProperty("path.sourceFile");
		String sourcePath = srcPath + "/" + ConfigUtil.getProperty("name.source.javaHDaoImpl");

		String daoProjectPath = rootPath + ConfigUtil.getProperty("path.common.daoProject");
		String daoFrameworkPath = rootPath + ConfigUtil.getProperty("path.common.daoFwk");

		String projectName = "", targetPath = "", packageString = "", tempString = "", sourceString = "";
		File targetFile;

		try {
			if (CommonUtil.equalsIgnoreCase(systemType, "Project")) {
				projectName = projectPackage;
				targetPath = daoProjectPath;
			} else if (CommonUtil.equalsIgnoreCase(systemType, "Framework")) {
				projectName = frameworkPackage+".example";
				targetPath = daoFrameworkPath;
			}
			targetPath += "/"+CommonUtil.toCamelCase(tableName);

			FileUtil.createFolder(targetPath);

			targetFile = new File(targetPath+"/"+CommonUtil.toCamelCase(tableName)+"HDaoImpl.java");
			createEmptyFile(targetFile);

			BufferedReader bufferedReader = new BufferedReader(new FileReader(sourcePath));
			StringBuffer stringBuffer = new StringBuffer();
			while ((tempString = bufferedReader.readLine()) != null) {
				stringBuffer.append(tempString+"\n");
			}
			OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");
			sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");

			packageString = CommonUtil.replace(CommonUtil.remove(targetPath, rootPath+"src/main/java/"), "/", ".");

			sourceString = CommonUtil.replace(sourceString, "#TABLE_NAME#", tableName);
			sourceString = CommonUtil.replace(sourceString, "#TABLE_DESCRIPTION#", tableInfoDataSet.getValue("TABLE_DESCRIPTION"));
			sourceString = CommonUtil.replace(sourceString, "#PACKAGE#", packageString);
			sourceString = CommonUtil.replace(sourceString, "#PROJECT_NAME#", projectName);
			sourceString = CommonUtil.replace(sourceString, "#TABLE_NAME_CAMELCASE#", CommonUtil.toCamelCaseStartUpperCase(tableName));

			osWriter.write(sourceString);
			osWriter.flush();
			osWriter.close();
			bufferedReader.close();

			return true;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	public boolean generateDaoImpl(String systemType, DataSet requestDataSet, DataSet tableInfoDataSet) throws Exception {
		String projectPackage = CommonUtil.lowerCase(ConfigUtil.getProperty("name.package.project"));
		String frameworkPackage = CommonUtil.lowerCase(ConfigUtil.getProperty("name.package.framework"));
		String compilePath = "/target/alpaca";
		String tableName = requestDataSet.getValue("tableName");

		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String srcPath = rootPath + ConfigUtil.getProperty("path.sourceFile");
		String sourcePath = srcPath + "/" + ConfigUtil.getProperty("name.source.javaDaoImpl");

		String daoProjectPath = rootPath + ConfigUtil.getProperty("path.common.daoProject");
		String daoFrameworkPath = rootPath + ConfigUtil.getProperty("path.common.daoFwk");

		String projectName = "", targetPath = "", packageString = "", tempString = "", sourceString = "";
		File targetFile;

		try {
			if (CommonUtil.equalsIgnoreCase(systemType, "Project")) {
				projectName = projectPackage;
				targetPath = daoProjectPath;
			} else if (CommonUtil.equalsIgnoreCase(systemType, "Framework")) {
				projectName = frameworkPackage+".example";
				targetPath = daoFrameworkPath;
			}
			targetPath += "/"+CommonUtil.toCamelCase(tableName);

			FileUtil.createFolder(targetPath);

			targetFile = new File(targetPath+"/"+CommonUtil.toCamelCase(tableName)+"DaoImpl.java");
			createEmptyFile(targetFile);

			BufferedReader bufferedReader = new BufferedReader(new FileReader(sourcePath));
			StringBuffer stringBuffer = new StringBuffer();
			while ((tempString = bufferedReader.readLine()) != null) {
				stringBuffer.append(tempString+"\n");
			}
			OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");
			sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");

			packageString = CommonUtil.replace(CommonUtil.remove(targetPath, rootPath+"src/main/java/"), "/", ".");

			sourceString = CommonUtil.replace(sourceString, "#TABLE_NAME#", tableName);
			sourceString = CommonUtil.replace(sourceString, "#TABLE_DESCRIPTION#", tableInfoDataSet.getValue("TABLE_DESCRIPTION"));
			sourceString = CommonUtil.replace(sourceString, "#PACKAGE#", packageString);
			sourceString = CommonUtil.replace(sourceString, "#PROJECT_NAME#", projectName);
			sourceString = CommonUtil.replace(sourceString, "#TABLE_NAME_CAMELCASE#", CommonUtil.toCamelCaseStartUpperCase(tableName));
			sourceString = CommonUtil.replace(sourceString, "#TABLE_NAME_CAMELCASE_LOWER#", CommonUtil.toCamelCaseStartLowerCase(tableName));

			osWriter.write(sourceString);
			osWriter.flush();
			osWriter.close();
			bufferedReader.close();

			return true;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	public boolean generateDaoMapper(String systemType, DataSet requestDataSet, DataSet tableInfoDataSet) throws Exception {
		String projectPackage = CommonUtil.lowerCase(ConfigUtil.getProperty("name.package.project"));
		String frameworkPackage = CommonUtil.lowerCase(ConfigUtil.getProperty("name.package.framework"));
		String compilePath = "/target/alpaca";
		String tableName = requestDataSet.getValue("tableName");

		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String srcPath = rootPath + ConfigUtil.getProperty("path.sourceFile");
		String sourcePath = srcPath + "/" + ConfigUtil.getProperty("name.source.javaDaoMapper");

		String daoProjectPath = rootPath + ConfigUtil.getProperty("path.common.daoProject");
		String daoFrameworkPath = rootPath + ConfigUtil.getProperty("path.common.daoFwk");

		String projectName = "", targetPath = "", packageString = "", tempString = "", sourceString = "";
		File targetFile;

		try {
			if (CommonUtil.equalsIgnoreCase(systemType, "Project")) {
				projectName = projectPackage;
				targetPath = daoProjectPath;
			} else if (CommonUtil.equalsIgnoreCase(systemType, "Framework")) {
				projectName = frameworkPackage+".example";
				targetPath = daoFrameworkPath;
			}
			targetPath += "/"+CommonUtil.toCamelCase(tableName);

			FileUtil.createFolder(targetPath);

			targetFile = new File(targetPath+"/"+CommonUtil.toCamelCase(tableName)+"DaoMapper.java");
			createEmptyFile(targetFile);

			BufferedReader bufferedReader = new BufferedReader(new FileReader(sourcePath));
			StringBuffer stringBuffer = new StringBuffer();
			while ((tempString = bufferedReader.readLine()) != null) {
				stringBuffer.append(tempString+"\n");
			}
			OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");
			sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");

			packageString = CommonUtil.replace(CommonUtil.remove(targetPath, rootPath+"src/main/java/"), "/", ".");

			sourceString = CommonUtil.replace(sourceString, "#TABLE_NAME#", tableName);
			sourceString = CommonUtil.replace(sourceString, "#TABLE_DESCRIPTION#", tableInfoDataSet.getValue("TABLE_DESCRIPTION"));
			sourceString = CommonUtil.replace(sourceString, "#PACKAGE#", packageString);
			sourceString = CommonUtil.replace(sourceString, "#PROJECT_NAME#", projectName);
			sourceString = CommonUtil.replace(sourceString, "#TABLE_NAME_CAMELCASE#", CommonUtil.toCamelCaseStartUpperCase(tableName));

			osWriter.write(sourceString);
			osWriter.flush();
			osWriter.close();
			bufferedReader.close();

			return true;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	public boolean generateHibernateQuery(String systemType, DataSet requestDataSet, DataSet tableInfoDataSet) throws Exception {
		String frameworkName = ConfigUtil.getProperty("name.framework");
		String compilePath = "/target/alpaca";
		String tableName = requestDataSet.getValue("tableName");

		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String srcPath = rootPath + ConfigUtil.getProperty("path.sourceFile");
		String sourcePath = srcPath + "/" + ConfigUtil.getProperty("name.source.hibernateQuery");
		String dbVendor = CommonUtil.lowerCase(ConfigUtil.getProperty("db.vendor"));

		String hibernateQueryProjectPath = rootPath + CommonUtil.replace(ConfigUtil.getProperty("path.source.hibernateQueryProject"), "#DB_VENDOR#", dbVendor);
		String hibernateQueryFrameworkPath = rootPath + CommonUtil.replace(ConfigUtil.getProperty("path.source.hibernateQueryFwk"), "#DB_VENDOR#", dbVendor);

		String targetPath = "", tempString = "", sourceString = "", fileNamePrefix = "";
		File targetFile;

		try {
			if (CommonUtil.equalsIgnoreCase(systemType, "Project")) {
				targetPath = hibernateQueryProjectPath;
				fileNamePrefix = "query-";
			} else if (CommonUtil.equalsIgnoreCase(systemType, "Framework")) {
				targetPath = hibernateQueryFrameworkPath;
				fileNamePrefix = "query-"+frameworkName+"-";
			}

			targetFile = new File(targetPath + "/" + fileNamePrefix + CommonUtil.toCamelCase(tableName) + ".hbm.xml");
			createEmptyFile(targetFile);

			BufferedReader bufferedReader = new BufferedReader(new FileReader(sourcePath));
			StringBuffer stringBuffer = new StringBuffer();
			while ((tempString = bufferedReader.readLine()) != null) {
				stringBuffer.append(tempString + "\n");
			}
			OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");
			sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");

			sourceString = CommonUtil.replace(sourceString, "#TABLE_NAME#", tableName);
			sourceString = CommonUtil.replace(sourceString, "#TABLE_DESCRIPTION#", tableInfoDataSet.getValue("TABLE_DESCRIPTION"));

			osWriter.write(sourceString);
			osWriter.flush();
			osWriter.close();
			bufferedReader.close();

			return true;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	public boolean generateMybatisQuery(String systemType, DataSet requestDataSet, DataSet tableInfoDataSet) throws Exception {
		String frameworkName = ConfigUtil.getProperty("name.framework");
		String compilePath = "/target/alpaca";
		String tableName = requestDataSet.getValue("tableName");

		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String srcPath = rootPath + ConfigUtil.getProperty("path.sourceFile");
		String sourcePath = srcPath + "/" + ConfigUtil.getProperty("name.source.mybatisQuery");
		String dbVendor = CommonUtil.lowerCase(ConfigUtil.getProperty("db.vendor"));

		String daoProjectPath = rootPath + ConfigUtil.getProperty("path.common.daoProject");
		String daoFrameworkPath = rootPath + ConfigUtil.getProperty("path.common.daoFwk");
		String mybatisQueryProjectPath = rootPath + CommonUtil.replace(ConfigUtil.getProperty("path.source.mybatisQueryProject"), "#DB_VENDOR#", dbVendor);
		String mybatisQueryFrameworkPath = rootPath + CommonUtil.replace(ConfigUtil.getProperty("path.source.mybatisQueryFwk"), "#DB_VENDOR#", dbVendor);

		String targetPath = "", tempString = "", packageString = "", sourceString = "", fileNamePrefix = "", daoPath = "";
		File targetFile;

		try {
			if (CommonUtil.equalsIgnoreCase(systemType, "Project")) {
				targetPath = mybatisQueryProjectPath;
				fileNamePrefix = "query-";
				daoPath = daoProjectPath+"/"+CommonUtil.toCamelCase(tableName);
			} else if (CommonUtil.equalsIgnoreCase(systemType, "Framework")) {
				targetPath = mybatisQueryFrameworkPath;
				fileNamePrefix = "query-"+frameworkName+"-";
				daoPath = daoFrameworkPath+"/"+CommonUtil.toCamelCase(tableName);
			}

			targetFile = new File(targetPath+"/"+fileNamePrefix+CommonUtil.toCamelCase(tableName)+"Mapper.xml");
			createEmptyFile(targetFile);

			BufferedReader bufferedReader = new BufferedReader(new FileReader(sourcePath));
			StringBuffer stringBuffer = new StringBuffer();
			while ((tempString = bufferedReader.readLine()) != null) {
				stringBuffer.append(tempString + "\n");
			}
			OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");
			sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");

			packageString = CommonUtil.replace(CommonUtil.remove(daoPath, rootPath+"src/main/java/"), "/", ".");

			sourceString = CommonUtil.replace(sourceString, "#TABLE_NAME#", tableName);
			sourceString = CommonUtil.replace(sourceString, "#TABLE_DESCRIPTION#", tableInfoDataSet.getValue("TABLE_DESCRIPTION"));
			sourceString = CommonUtil.replace(sourceString, "#PACKAGE#", packageString);
			sourceString = CommonUtil.replace(sourceString, "#TABLE_NAME_CAMELCASE#", CommonUtil.toCamelCaseStartUpperCase(tableName));

			osWriter.write(sourceString);
			osWriter.flush();
			osWriter.close();
			bufferedReader.close();

			return true;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	public boolean generateDaoSpringConfig(String systemType, DataSet requestDataSet, DataSet tableInfoDataSet) throws Exception {
		String projectName = ConfigUtil.getProperty("name.project");
		String frameworkName = ConfigUtil.getProperty("name.framework");
		String projectPath = ConfigUtil.getProperty("name.path.project");
		String frameworkPath = ConfigUtil.getProperty("name.path.framework");
		String compilePath = "/target/alpaca";
		String tableName = requestDataSet.getValue("tableName");

		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String srcPath = rootPath + ConfigUtil.getProperty("path.sourceFile");
		String sourcePath = srcPath + "/" + ConfigUtil.getProperty("name.source.daoSpringConf");

		boolean hibernateDaoImplProject = CommonUtil.toBoolean(CommonUtil.nvl(requestDataSet.getValue("hibernateDaoImplProject"), "N"));
		boolean mybatisDaoImplProject = CommonUtil.toBoolean(CommonUtil.nvl(requestDataSet.getValue("mybatisDaoImplProject"), "N"));
		boolean hibernateDaoImplFramework = CommonUtil.toBoolean(CommonUtil.nvl(requestDataSet.getValue("hibernateDaoImplFramework"), "N"));
		boolean mybatisDaoImplFramework = CommonUtil.toBoolean(CommonUtil.nvl(requestDataSet.getValue("mybatisDaoImplFramework"), "N"));

		String daoProjectPath = ConfigUtil.getProperty("path.common.daoProject");
		String daoFrameworkPath = ConfigUtil.getProperty("path.common.daoFwk");
		String daoSpringConfProjectPath = rootPath + ConfigUtil.getProperty("path.source.daoSpringConfProject");
		String daoSpringConfFrameworkPath = rootPath + ConfigUtil.getProperty("path.source.daoSpringConfFwk");

		String tableNameUpperCamelCase = CommonUtil.toCamelCaseStartUpperCase(tableName);
		String tableNameLowerCamelCase = CommonUtil.toCamelCaseStartLowerCase(tableName);
		String targetPath = "", tempString = "", sourceString = "", springConfFileName = "", existingFilePath = "";
		String confVariableName = "", daoPath = "";
		boolean isHibernateChecked = false, isMyBatisChecked = false;
		File targetFile, backupFile;

		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		Document document;
		Element rootElement;
		XPath xpath;
		DOMSource domSource;

		try {
			if (CommonUtil.equalsIgnoreCase(systemType, "Project")) {
				springConfFileName = "spring-"+projectName+"-conf-resource-ormapper-dao.xml";
				targetPath = daoSpringConfProjectPath;
				daoPath = CommonUtil.substringAfter(daoProjectPath, projectPath);
				confVariableName = "${name.package.project}";

				if (hibernateDaoImplProject) {
					isHibernateChecked = true;
				}

				if (mybatisDaoImplProject) {
					isMyBatisChecked = true;
				}
			} else if (CommonUtil.equalsIgnoreCase(systemType, "Framework")) {
				springConfFileName = "spring-"+frameworkName+"-conf-resource-ormapper-dao.xml";
				targetPath = daoSpringConfFrameworkPath;
				daoPath = CommonUtil.substringAfter(daoFrameworkPath, frameworkPath);
				confVariableName = "${name.package.framework}";

				if (hibernateDaoImplFramework) {
					isHibernateChecked = true;
				}

				if (mybatisDaoImplFramework) {
					isMyBatisChecked = true;
				}
			}
			FileUtil.createFolder(targetPath);

			daoPath = CommonUtil.replace(daoPath, "/", ".")+"."+CommonUtil.toCamelCase(tableName);

			targetFile = new File(targetPath+"/"+springConfFileName);
			backupFile = new File(targetPath+"/"+springConfFileName+".bak");
			if (!targetFile.exists()) {
				targetFile.createNewFile();

				BufferedReader bufferedReader = new BufferedReader(new FileReader(sourcePath));
				StringBuffer stringBuffer = new StringBuffer();
				while ((tempString = bufferedReader.readLine()) != null) {
					stringBuffer.append(tempString + "\n");
				}
				OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");
				sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");

				osWriter.write(sourceString);
				osWriter.flush();
				osWriter.close();
				bufferedReader.close();
			} else {
				if (!backupFile.exists()) {
					existingFilePath = targetFile.getAbsolutePath();
					Files.copy(Paths.get(existingFilePath), Paths.get(existingFilePath+".bak"), StandardCopyOption.REPLACE_EXISTING);
				}
			}

			document = docBuilder.parse(targetFile);
			rootElement = document.getDocumentElement();
			xpath = XPathFactory.newInstance().newXPath();
			domSource = new DOMSource(document);

			if (isHibernateChecked) {
				if (!isExistingHDao(document, tableNameLowerCamelCase+"Dao")) {
					Element beanElement = document.createElement("bean");

					beanElement.setAttribute("id", tableNameLowerCamelCase+"Dao");
					beanElement.setAttribute("name", tableNameLowerCamelCase+"Dao");
					beanElement.setAttribute("class", confVariableName+daoPath+"."+tableNameUpperCamelCase+"HDaoImpl");
					beanElement.setAttribute("parent", "baseHDao");
					beanElement.setAttribute("scope", "prototype");
					beanElement.setAttribute("init-method", "init");
					rootElement.appendChild(beanElement);

					Comment commentElement = document.createComment(tableNameUpperCamelCase+" DAO");
					beanElement.getParentNode().insertBefore(commentElement, beanElement);
				}
			}

			if (isMyBatisChecked) {
				if (!isExistingDao(document, tableNameLowerCamelCase+"Dao")) {
					Element beanElementDao = document.createElement("bean");
					Element beanElementMapper = document.createElement("bean");
					Element propertyInterface = document.createElement("property");
					Element propertyFactory = document.createElement("property");

					beanElementDao.setAttribute("id", tableNameLowerCamelCase+"Dao");
					beanElementDao.setAttribute("name", tableNameLowerCamelCase+"Dao");
					beanElementDao.setAttribute("class", confVariableName+daoPath+"."+tableNameUpperCamelCase+"DaoImpl");
					beanElementDao.setAttribute("parent", "namedParameterJdbcDao");
					beanElementDao.setAttribute("scope", "prototype");
					beanElementDao.setAttribute("init-method", "init");

					beanElementMapper.setAttribute("id", tableNameLowerCamelCase+"DaoMapper");
					beanElementMapper.setAttribute("name", tableNameLowerCamelCase+"DaoMapper");
					beanElementMapper.setAttribute("class", "org.mybatis.spring.mapper.MapperFactoryBean");

					propertyInterface.setAttribute("name", "mapperInterface");
					propertyInterface.setAttribute("value", confVariableName+daoPath+"."+tableNameUpperCamelCase+"DaoMapper");
					beanElementMapper.appendChild(propertyInterface);

					propertyFactory.setAttribute("name", "sqlSessionFactory");
					propertyFactory.setAttribute("ref", "sqlSessionFactory");
					beanElementMapper.appendChild(propertyFactory);

					rootElement.appendChild(beanElementDao);
					rootElement.appendChild(beanElementMapper);

					Comment commentElement = document.createComment(tableNameUpperCamelCase+" DAO");
					beanElementDao.getParentNode().insertBefore(commentElement, beanElementDao);
				}
			}

			if (isHibernateChecked || isMyBatisChecked) {
				StreamResult streamResult = new StreamResult(targetFile.getPath());

				NodeList nodeList = (NodeList)xpath.evaluate("//text()[normalize-space()='']", document, XPathConstants.NODESET);
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node node = nodeList.item(i);
					node.getParentNode().removeChild(node);
				}
				transformer.setOutputProperty("encoding", "UTF-8");
				transformer.setOutputProperty("omit-xml-declaration", "false");
				transformer.setOutputProperty("indent", "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "4");
				transformer.transform(domSource, streamResult);
			}

			return true;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	/*!
	 * Source Generator
	 */
	public boolean createJavaAction(DataSet requestDataSet) throws Exception {
		String compilePath = "/target/alpaca";
		String projectName = CommonUtil.lowerCase(ConfigUtil.getProperty("name.project"));
		String javaPath = requestDataSet.getValue("javaSourcePath");
		String menuPathStr = CommonUtil.lowerCase(CommonUtil.replace(requestDataSet.getValue("menuId"), ConfigUtil.getProperty("delimiter.data"), "/"));
		String menuId[] = CommonUtil.split(menuPathStr, "/");
		String rootMenuId = CommonUtil.lowerCase(menuId[0]);
		String thisMenuId = CommonUtil.lowerCase(menuId[1]);
		String menuName = requestDataSet.getValue("menuName");
		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String srcPath = rootPath + ConfigUtil.getProperty("path.sourceFile");
		String actionHandlerType = requestDataSet.getValue("actionHandlerType");
		String actionNameAjaxResponse = ConfigUtil.getProperty("name.source.javaAction.ajaxResponse");
		String actionNamePageHandler = ConfigUtil.getProperty("name.source.javaAction.pageHandler");
		String thisMenuIdUpperCamelCase = CommonUtil.toCamelCaseStartUpperCase(thisMenuId);
		String javaTargetpath = javaPath + "/" + rootMenuId + "/" + thisMenuId;
		String sourcePath, sourceString, packageString, tempString;
		File targetFile;

		try {
			if (CommonUtil.equalsIgnoreCase(actionHandlerType, "P")) {
				sourcePath = srcPath+"/"+actionNamePageHandler;
			} else {
				sourcePath = srcPath+"/"+actionNameAjaxResponse;
			}

			targetFile = new File(javaTargetpath+"/"+thisMenuIdUpperCamelCase+"Action.java");
			createEmptyFile(targetFile);

			BufferedReader bufferedReader = new BufferedReader(new FileReader(sourcePath));
			StringBuffer stringBuffer = new StringBuffer();
			while ((tempString = bufferedReader.readLine()) != null) {
				stringBuffer.append(tempString + "\n");
			}
			OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");
			sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");

			packageString = CommonUtil.replace(CommonUtil.remove(javaTargetpath, rootPath + "src/main/java/"), "/", ".");

			String menuUrl = rootMenuId + "/" + CommonUtil.remove(thisMenuId, rootMenuId);

			sourceString = CommonUtil.replace(sourceString, "#PROJECT_NAME#", projectName);
			sourceString = CommonUtil.replace(sourceString, "#PACKAGE_NAME#", packageString);
			sourceString = CommonUtil.replace(sourceString, "#MENU_ID_START_UPPER#", thisMenuIdUpperCamelCase);
			sourceString = CommonUtil.replace(sourceString, "#MENU_NAME#", menuName);
			sourceString = CommonUtil.replace(sourceString, "#MENU_PATH#", menuPathStr);
			sourceString = CommonUtil.replace(sourceString, "#MENU_URL#", menuUrl);

			osWriter.write(sourceString);
			osWriter.flush();
			osWriter.close();
			bufferedReader.close();

			return true;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	public boolean createJavaBiz(DataSet requestDataSet) throws Exception {
		String compilePath = "/target/alpaca";
		String projectName = CommonUtil.lowerCase(ConfigUtil.getProperty("name.project"));
		String javaPath = requestDataSet.getValue("javaSourcePath");
		String menuPathStr = CommonUtil.lowerCase(CommonUtil.replace(requestDataSet.getValue("menuId"), ConfigUtil.getProperty("delimiter.data"), "/"));
		String menuId[] = CommonUtil.split(menuPathStr, "/");
		String rootMenuId = CommonUtil.lowerCase(menuId[0]);
		String thisMenuId = CommonUtil.lowerCase(menuId[1]);
		String menuName = requestDataSet.getValue("menuName");
		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String srcPath = rootPath + ConfigUtil.getProperty("path.sourceFile");
		String srcBizFileName = ConfigUtil.getProperty("name.source.javaBiz");
		String thisMenuIdUpperCamelCase = CommonUtil.toCamelCaseStartUpperCase(thisMenuId);
		String javaTargetpath = javaPath + "/" + rootMenuId + "/" + thisMenuId;
		String sourcePath, tempString, sourceString, packageString;
		File targetFile;

		try {
			sourcePath = srcPath+"/"+srcBizFileName;

			targetFile = new File(javaTargetpath+"/"+thisMenuIdUpperCamelCase+"Biz.java");
			createEmptyFile(targetFile);

			BufferedReader bufferedReader = new BufferedReader(new FileReader(sourcePath));
			StringBuffer stringBuffer = new StringBuffer();
			while ((tempString = bufferedReader.readLine()) != null) {
				stringBuffer.append(tempString + "\n");
			}
			OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");
			sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");

			packageString = CommonUtil.replace(CommonUtil.remove(javaTargetpath, rootPath + "src/main/java/"), "/", ".");

			sourceString = CommonUtil.replace(sourceString, "#PROJECT_NAME#", projectName);
			sourceString = CommonUtil.replace(sourceString, "#PACKAGE_NAME#", packageString);
			sourceString = CommonUtil.replace(sourceString, "#MENU_ID_START_UPPER#", thisMenuIdUpperCamelCase);
			sourceString = CommonUtil.replace(sourceString, "#MENU_NAME#", menuName);

			osWriter.write(sourceString);
			osWriter.flush();
			osWriter.close();
			bufferedReader.close();

			return true;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	public boolean createJavaBizImpl(DataSet requestDataSet) throws Exception {
		String compilePath = "/target/alpaca";
		String projectName = CommonUtil.lowerCase(ConfigUtil.getProperty("name.project"));
		String javaPath = requestDataSet.getValue("javaSourcePath");
		String menuPathStr = CommonUtil.lowerCase(CommonUtil.replace(requestDataSet.getValue("menuId"), ConfigUtil.getProperty("delimiter.data"), "/"));
		String menuId[] = CommonUtil.split(menuPathStr, "/");
		String rootMenuId = CommonUtil.lowerCase(menuId[0]);
		String thisMenuId = CommonUtil.lowerCase(menuId[1]);
		String menuName = requestDataSet.getValue("menuName");
		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String srcPath = rootPath + ConfigUtil.getProperty("path.sourceFile");
		String srcBizImplFileName = ConfigUtil.getProperty("name.source.javaBizImpl");
		String thisMenuIdUpperCamelCase = CommonUtil.toCamelCaseStartUpperCase(thisMenuId);
		String thisMenuIdLowerCamelCase = CommonUtil.toCamelCaseStartLowerCase(thisMenuId);
		String javaTargetpath = javaPath + "/" + rootMenuId + "/" + thisMenuId;
		String sourcePath, tempString, sourceString, packageString;
		File targetFile;

		try {
			sourcePath = srcPath+"/"+srcBizImplFileName;

			targetFile = new File(javaTargetpath+"/"+thisMenuIdUpperCamelCase+"BizImpl.java");
			createEmptyFile(targetFile);

			BufferedReader bufferedReader = new BufferedReader(new FileReader(sourcePath));
			StringBuffer stringBuffer = new StringBuffer();
			while ((tempString = bufferedReader.readLine()) != null) {
				stringBuffer.append(tempString + "\n");
			}
			OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");
			sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");

			packageString = CommonUtil.replace(CommonUtil.remove(javaTargetpath, rootPath + "src/main/java/"), "/", ".");

			sourceString = CommonUtil.replace(sourceString, "#PROJECT_NAME#", projectName);
			sourceString = CommonUtil.replace(sourceString, "#PACKAGE_NAME#", packageString);
			sourceString = CommonUtil.replace(sourceString, "#MENU_ID_START_UPPER#", thisMenuIdUpperCamelCase);
			sourceString = CommonUtil.replace(sourceString, "#MENU_ID_START_LOWER#", thisMenuIdLowerCamelCase);
			sourceString = CommonUtil.replace(sourceString, "#MENU_NAME#", menuName);

			osWriter.write(sourceString);
			osWriter.flush();
			osWriter.close();
			bufferedReader.close();

			return true;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	public boolean createJspList(DataSet requestDataSet) throws Exception {
		String compilePath = "/target/alpaca";
		String isCreate = CommonUtil.nvl(requestDataSet.getValue("jspCreateList"));
		String pageType = requestDataSet.getValue("jspSubPageType");
		String jspPath = requestDataSet.getValue("jspSourcePath");
		String menuPathStr = CommonUtil.lowerCase(CommonUtil.replace(requestDataSet.getValue("menuId"), ConfigUtil.getProperty("delimiter.data"), "/"));
		String menuId[] = CommonUtil.split(menuPathStr, "/");
		String rootMenuId = CommonUtil.lowerCase(menuId[0]);
		String thisMenuId = CommonUtil.lowerCase(menuId[1]);
		String menuName = requestDataSet.getValue("menuName");
		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String srcPath = rootPath + ConfigUtil.getProperty("path.sourceFile");
		String thisMenuIdUpperCamelCase = CommonUtil.toCamelCaseStartUpperCase(thisMenuId);
		String jspTargetpath = jspPath + "/" + rootMenuId + "/" + thisMenuId;
		String jsSectionStringStart = "<script type=\"text/javascript\">", jsSectionStringEnd = "</script>", jsString = "";
		boolean isJsSection = false;
		String srcJspFileName, sourceString, tempString;
		File targetFile;

		try {
			if (CommonUtil.equalsIgnoreCase(isCreate, "Y")) {
				if (CommonUtil.equalsIgnoreCase(pageType, "Page")) {
					srcJspFileName = ConfigUtil.getProperty("name.source.jspListForPage");
				} else {
					srcJspFileName = ConfigUtil.getProperty("name.source.jspListForPop");
				}

				targetFile = new File(jspTargetpath+"/"+thisMenuIdUpperCamelCase+"List.jsp");
				createEmptyFile(targetFile);

				BufferedReader bufferedReader = new BufferedReader(new FileReader(srcPath + "/" + srcJspFileName));
				StringBuffer stringBuffer = new StringBuffer();
				while ((tempString = bufferedReader.readLine()) != null) {
					if (CommonUtil.equalsIgnoreCase(tempString, jsSectionStringStart)) {
						isJsSection = true;
					}

					if (CommonUtil.equalsIgnoreCase(tempString, jsSectionStringEnd)) {
						isJsSection = false;
					}

					if (isJsSection && !CommonUtil.equalsIgnoreCase(tempString, jsSectionStringStart)) {
						jsString += "\n"+tempString;
						continue;
					}

					stringBuffer.append(tempString + "\n");
				}
				OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");
				sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");

				String menuUrl = rootMenuId + "/" + CommonUtil.remove(thisMenuId, rootMenuId);

				sourceString = CommonUtil.replace(sourceString, "#MENU_ID_START_UPPER#", thisMenuIdUpperCamelCase);
				sourceString = CommonUtil.replace(sourceString, "#MENU_NAME#", menuName);
				sourceString = CommonUtil.replace(sourceString, "#MENU_URL#", menuUrl);
				sourceString = CommonUtil.replace(sourceString, "#THIS_MENU_ID#", thisMenuId);

				osWriter.write(sourceString);
				osWriter.flush();
				osWriter.close();
				bufferedReader.close();

				createJsSource(requestDataSet, thisMenuIdUpperCamelCase+"List.js", jsString);
			}

			return true;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	public boolean createJspDetail(DataSet requestDataSet) throws Exception {
		String compilePath = "/target/alpaca";
		String isCreate = CommonUtil.nvl(requestDataSet.getValue("jspCreateDetail"));
		String pageType = requestDataSet.getValue("jspSubPageType");
		String jspPath = requestDataSet.getValue("jspSourcePath");
		String menuPathStr = CommonUtil.lowerCase(CommonUtil.replace(requestDataSet.getValue("menuId"), ConfigUtil.getProperty("delimiter.data"), "/"));
		String menuId[] = CommonUtil.split(menuPathStr, "/");
		String rootMenuId = CommonUtil.lowerCase(menuId[0]);
		String thisMenuId = CommonUtil.lowerCase(menuId[1]);
		String menuName = requestDataSet.getValue("menuName");
		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String srcPath = rootPath + ConfigUtil.getProperty("path.sourceFile");
		String thisMenuIdUpperCamelCase = CommonUtil.toCamelCaseStartUpperCase(thisMenuId);
		String jspTargetpath = jspPath + "/" + rootMenuId + "/" + thisMenuId;
		String jsSectionStringStart = "<script type=\"text/javascript\">", jsSectionStringEnd = "</script>", jsString = "";
		boolean isJsSection = false;
		String srcJspFileName, targetFileSuffix, sourceString, menuUrl;
		File targetFile;

		try {
			if (CommonUtil.equalsIgnoreCase(isCreate, "Y")) {
				if (CommonUtil.equalsIgnoreCase(pageType, "Page")) {
					targetFileSuffix = "";
					srcJspFileName = ConfigUtil.getProperty("name.source.jspDetailPage");
				} else {
					targetFileSuffix = "Pop";
					srcJspFileName = ConfigUtil.getProperty("name.source.jspDetailPop");
				}

				targetFile = new File(jspTargetpath + "/" + thisMenuIdUpperCamelCase + "Detail" + targetFileSuffix + ".jsp");
				createEmptyFile(targetFile);

				BufferedReader bufferedReader = new BufferedReader(new FileReader(srcPath + "/" + srcJspFileName));
				StringBuffer stringBuffer = new StringBuffer();
				String tempString;
				while ((tempString = bufferedReader.readLine()) != null) {
					if (CommonUtil.equalsIgnoreCase(tempString, jsSectionStringStart)) {
						isJsSection = true;
					}

					if (CommonUtil.equalsIgnoreCase(tempString, jsSectionStringEnd)) {
						isJsSection = false;
					}

					if (isJsSection && !CommonUtil.equalsIgnoreCase(tempString, jsSectionStringStart)) {
						jsString += "\n"+tempString;
						continue;
					}

					stringBuffer.append(tempString + "\n");
				}
				OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");
				sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");

				menuUrl = rootMenuId + "/" + CommonUtil.remove(thisMenuId, rootMenuId);

				sourceString = CommonUtil.replace(sourceString, "#MENU_ID_START_UPPER#", thisMenuIdUpperCamelCase);
				sourceString = CommonUtil.replace(sourceString, "#MENU_NAME#", menuName);
				sourceString = CommonUtil.replace(sourceString, "#MENU_URL#", menuUrl);
				sourceString = CommonUtil.replace(sourceString, "#THIS_MENU_ID#", thisMenuId);

				osWriter.write(sourceString);
				osWriter.flush();
				osWriter.close();
				bufferedReader.close();

				createJsSource(requestDataSet, thisMenuIdUpperCamelCase + "Detail" + targetFileSuffix + ".js", jsString);
			}

			return true;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	public boolean createJspInsert(DataSet requestDataSet) throws Exception {
		String compilePath = "/target/alpaca";
		String isCreate = CommonUtil.nvl(requestDataSet.getValue("jspCreateInsert"));
		String pageType = requestDataSet.getValue("jspSubPageType");
		String jspPath = requestDataSet.getValue("jspSourcePath");
		String menuPathStr = CommonUtil.lowerCase(CommonUtil.replace(requestDataSet.getValue("menuId"), ConfigUtil.getProperty("delimiter.data"), "/"));
		String menuId[] = CommonUtil.split(menuPathStr, "/");
		String rootMenuId = CommonUtil.lowerCase(menuId[0]);
		String thisMenuId = CommonUtil.lowerCase(menuId[1]);
		String menuName = requestDataSet.getValue("menuName");
		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String srcPath = rootPath + ConfigUtil.getProperty("path.sourceFile");
		String thisMenuIdUpperCamelCase = CommonUtil.toCamelCaseStartUpperCase(thisMenuId);
		String jspTargetpath = jspPath + "/" + rootMenuId + "/" + thisMenuId;
		String jsSectionStringStart = "<script type=\"text/javascript\">", jsSectionStringEnd = "</script>", jsString = "";
		boolean isJsSection = false;
		String srcJspFileName, targetFileSuffix, sourceString, menuUrl;
		File targetFile;

		try {
			if (CommonUtil.equalsIgnoreCase(isCreate, "Y")) {
				if (CommonUtil.equalsIgnoreCase(pageType, "Page")) {
					targetFileSuffix = "";
					srcJspFileName = ConfigUtil.getProperty("name.source.jspInsertPage");
				} else {
					targetFileSuffix = "Pop";
					srcJspFileName = ConfigUtil.getProperty("name.source.jspInsertPop");
				}

				targetFile = new File(jspTargetpath + "/" + thisMenuIdUpperCamelCase + "Insert" + targetFileSuffix + ".jsp");
				createEmptyFile(targetFile);

				BufferedReader bufferedReader = new BufferedReader(new FileReader(srcPath + "/" + srcJspFileName));
				StringBuffer stringBuffer = new StringBuffer();
				String tempString;
				while ((tempString = bufferedReader.readLine()) != null) {
					if (CommonUtil.equalsIgnoreCase(tempString, jsSectionStringStart)) {
						isJsSection = true;
					}

					if (CommonUtil.equalsIgnoreCase(tempString, jsSectionStringEnd)) {
						isJsSection = false;
					}

					if (isJsSection && !CommonUtil.equalsIgnoreCase(tempString, jsSectionStringStart)) {
						jsString += "\n"+tempString;
						continue;
					}

					stringBuffer.append(tempString + "\n");
				}
				OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");
				sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");

				menuUrl = rootMenuId + "/" + CommonUtil.remove(thisMenuId, rootMenuId);

				sourceString = CommonUtil.replace(sourceString, "#MENU_ID_START_UPPER#", thisMenuIdUpperCamelCase);
				sourceString = CommonUtil.replace(sourceString, "#MENU_NAME#", menuName);
				sourceString = CommonUtil.replace(sourceString, "#MENU_URL#", menuUrl);
				sourceString = CommonUtil.replace(sourceString, "#THIS_MENU_ID#", thisMenuId);

				osWriter.write(sourceString);
				osWriter.flush();
				osWriter.close();
				bufferedReader.close();

				createJsSource(requestDataSet, thisMenuIdUpperCamelCase + "Insert" + targetFileSuffix + ".js", jsString);
			}

			return true;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	public boolean createJspUpdate(DataSet requestDataSet) throws Exception {
		String compilePath = "/target/alpaca";
		String isCreate = CommonUtil.nvl(requestDataSet.getValue("jspCreateUpdate"));
		String pageType = requestDataSet.getValue("jspSubPageType");
		String jspPath = requestDataSet.getValue("jspSourcePath");
		String menuPathStr = CommonUtil.lowerCase(CommonUtil.replace(requestDataSet.getValue("menuId"), ConfigUtil.getProperty("delimiter.data"), "/"));
		String menuId[] = CommonUtil.split(menuPathStr, "/");
		String rootMenuId = CommonUtil.lowerCase(menuId[0]);
		String thisMenuId = CommonUtil.lowerCase(menuId[1]);
		String menuName = requestDataSet.getValue("menuName");
		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String srcPath = rootPath + ConfigUtil.getProperty("path.sourceFile");
		String thisMenuIdUpperCamelCase = CommonUtil.toCamelCaseStartUpperCase(thisMenuId);
		String jspTargetpath = jspPath + "/" + rootMenuId + "/" + thisMenuId;
		String jsSectionStringStart = "<script type=\"text/javascript\">", jsSectionStringEnd = "</script>", jsString = "";
		boolean isJsSection = false;
		String srcJspFileName, targetFileSuffix, sourceString, menuUrl;
		File targetFile;

		try {
			if (CommonUtil.equalsIgnoreCase(isCreate, "Y")) {
				if (CommonUtil.equalsIgnoreCase(pageType, "Page")) {
					targetFileSuffix = "";
					srcJspFileName = ConfigUtil.getProperty("name.source.jspUpdatePage");
				} else {
					targetFileSuffix = "Pop";
					srcJspFileName = ConfigUtil.getProperty("name.source.jspUpdatePop");
				}
				targetFile = new File(jspTargetpath + "/" + thisMenuIdUpperCamelCase + "Update" + targetFileSuffix + ".jsp");
				createEmptyFile(targetFile);

				BufferedReader bufferedReader = new BufferedReader(new FileReader(srcPath + "/" + srcJspFileName));
				StringBuffer stringBuffer = new StringBuffer();
				String tempString;
				while ((tempString = bufferedReader.readLine()) != null) {
					if (CommonUtil.equalsIgnoreCase(tempString, jsSectionStringStart)) {
						isJsSection = true;
					}

					if (CommonUtil.equalsIgnoreCase(tempString, jsSectionStringEnd)) {
						isJsSection = false;
					}

					if (isJsSection && !CommonUtil.equalsIgnoreCase(tempString, jsSectionStringStart)) {
						jsString += "\n"+tempString;
						continue;
					}

					stringBuffer.append(tempString + "\n");
				}
				OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");
				sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");

				menuUrl = rootMenuId + "/" + CommonUtil.remove(thisMenuId, rootMenuId);

				sourceString = CommonUtil.replace(sourceString, "#MENU_ID_START_UPPER#", thisMenuIdUpperCamelCase);
				sourceString = CommonUtil.replace(sourceString, "#MENU_NAME#", menuName);
				sourceString = CommonUtil.replace(sourceString, "#MENU_URL#", menuUrl);
				sourceString = CommonUtil.replace(sourceString, "#THIS_MENU_ID#", thisMenuId);

				osWriter.write(sourceString);
				osWriter.flush();
				osWriter.close();
				bufferedReader.close();

				createJsSource(requestDataSet, thisMenuIdUpperCamelCase + "Update" + targetFileSuffix + ".js", jsString);
			}

			return true;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	public boolean createConfSpring(DataSet dsRequest) throws Exception {
		String compilePath = "/target/alpaca";
		String projectName = CommonUtil.lowerCase(ConfigUtil.getProperty("name.project"));
		String packageName = CommonUtil.lowerCase(ConfigUtil.getProperty("name.package.project"));
		String isCreate = CommonUtil.nvl(dsRequest.getValue("createSpring"));
		String javaPath = dsRequest.getValue("javaSourcePath");
		String targetPath = dsRequest.getValue("springConfigPath");
		String menuPathStr = CommonUtil.lowerCase(CommonUtil.replace(dsRequest.getValue("menuId"), ConfigUtil.getProperty("delimiter.data"), "/"));
		String menuId[] = CommonUtil.split(menuPathStr, "/");
		String rootMenuId = CommonUtil.lowerCase(menuId[0]);
		String thisMenuId = CommonUtil.lowerCase(menuId[1]);
		String menuName = dsRequest.getValue("menuName");
		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String srcPath = rootPath + ConfigUtil.getProperty("path.sourceFile");
		String srcFileName = ConfigUtil.getProperty("name.source.xmlMenuSpringConf");
		String thisMenuIdUpperCamelCase = CommonUtil.toCamelCaseStartUpperCase(thisMenuId);
		String thisMenuIdLowerCamelCase = CommonUtil.toCamelCaseStartLowerCase(thisMenuId);
		String javaTargetpath = javaPath + "/" + rootMenuId + "/" + thisMenuId;
		String sourceString, packageString;
		File targetFile;

		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		XPath xpath = XPathFactory.newInstance().newXPath();

		try {
			if (CommonUtil.equalsIgnoreCase(isCreate, "Y")) {
				targetFile = new File(targetPath + "/" + "spring-"+projectName+"-app-"+rootMenuId+".xml");

				if (!targetFile.exists()) {
					targetFile.createNewFile();

					BufferedReader bufferedReader = new BufferedReader(new FileReader(srcPath + "/" + srcFileName));
					StringBuffer stringBuffer = new StringBuffer();
					String tempString;
					while ((tempString = bufferedReader.readLine()) != null) {
						stringBuffer.append(tempString + "\n");
					}
					OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");
					sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");

					osWriter.write(sourceString);
					osWriter.flush();
					osWriter.close();
					bufferedReader.close();
				}

				Document document = docBuilder.parse(targetFile);
				Element rootElement = document.getDocumentElement();
				DOMSource domSource = new DOMSource(document);
				packageString = CommonUtil.replace(CommonUtil.replace(CommonUtil.remove(javaTargetpath, rootPath + "src/main/java/"), "/", "."), packageName, "${name.package.project}");

				Element beanElement = document.createElement("bean");
				beanElement.setAttribute("id", thisMenuIdLowerCamelCase + "Action");
				beanElement.setAttribute("name", thisMenuIdLowerCamelCase + "Action");
				beanElement.setAttribute("class", packageString + "." + thisMenuIdUpperCamelCase + "Action");
				rootElement.appendChild(beanElement);

				Comment commentElement = document.createComment(thisMenuIdUpperCamelCase + " - " + menuName);
				beanElement.getParentNode().insertBefore(commentElement, beanElement);

				beanElement = document.createElement("bean");
				beanElement.setAttribute("id", thisMenuIdLowerCamelCase + "Biz");
				beanElement.setAttribute("name", thisMenuIdLowerCamelCase + "Biz");
				beanElement.setAttribute("class", packageString + "." + thisMenuIdUpperCamelCase + "BizImpl");
				beanElement.setAttribute("parent", "baseBiz");
				rootElement.appendChild(beanElement);

				NodeList nodeList = (NodeList)xpath.evaluate("//text()[normalize-space()='']", document, XPathConstants.NODESET);
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node node = nodeList.item(i);
					node.getParentNode().removeChild(node);
				}
				transformer.setOutputProperty("encoding", "UTF-8");
				transformer.setOutputProperty("omit-xml-declaration", "false");
				transformer.setOutputProperty("indent", "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "4");

				StreamResult streamResult = new StreamResult(targetFile.getPath());
				transformer.transform(domSource, streamResult);
			}

			return true;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	public boolean createConfStruts(DataSet dsRequest) throws Exception {
		String compilePath = "/target/alpaca";
		String projectName = CommonUtil.lowerCase(ConfigUtil.getProperty("name.project"));
		String isCreate = CommonUtil.nvl(dsRequest.getValue("createStruts"));
		String pageType = dsRequest.getValue("jspSubPageType");
		String javaPath = dsRequest.getValue("javaSourcePath");
		String jspPath = dsRequest.getValue("jspSourcePath");
		String targetPath = dsRequest.getValue("strutsConfigPath");
		String menuPathStr = CommonUtil.lowerCase(CommonUtil.replace(dsRequest.getValue("menuId"), ConfigUtil.getProperty("delimiter.data"), "/"));
		String menuId[] = CommonUtil.split(menuPathStr, "/");
		String rootMenuId = CommonUtil.lowerCase(menuId[0]);
		String thisMenuId = CommonUtil.lowerCase(menuId[1]);
		String menuName = dsRequest.getValue("menuName");
		String menuNumber = CommonUtil.remove(thisMenuId, rootMenuId);
		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String srcPath = rootPath + ConfigUtil.getProperty("path.sourceFile");
		String srcFileName = ConfigUtil.getProperty("name.source.xmlMenuStrutsConf");
		String thisMenuIdUpperCamelCase = CommonUtil.toCamelCaseStartUpperCase(thisMenuId);
		String jspRelPath = CommonUtil.substringAfter(jspPath, "webapp");
		String javaTargetpath = javaPath + "/" + rootMenuId + "/" + thisMenuId;
		String sourceString, pageNameSuffix = "";

		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		XPath xpath = XPathFactory.newInstance().newXPath();

		try {
			if (CommonUtil.equalsIgnoreCase(isCreate, "Y")) {
				File regFile = new File(targetPath + "/" + "struts.xml");
				File targetFile = new File(targetPath + "/" + "struts-"+projectName+"-app-"+rootMenuId+".xml");

				if (!targetFile.exists()) {
					targetFile.createNewFile();

					BufferedReader bufferedReader = new BufferedReader(new FileReader(srcPath + "/" + srcFileName));
					StringBuffer stringBuffer = new StringBuffer();
					String tempString;
					while ((tempString = bufferedReader.readLine()) != null) {
						stringBuffer.append(tempString + "\n");
					}
					OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");
					sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");

					sourceString = CommonUtil.replace(sourceString, "#ROOT_MENU_ID#", rootMenuId);

					osWriter.write(sourceString);
					osWriter.flush();
					osWriter.close();
					bufferedReader.close();

					Document document = docBuilder.parse(regFile);
					Element rootElement = document.getDocumentElement();
					DOMSource domSource = new DOMSource(document);

					Element includeElement = document.createElement("include");
					includeElement.setAttribute("file", projectName+"/conf/struts/struts-"+projectName+"-app-"+rootMenuId+".xml");
					rootElement.appendChild(includeElement);

					DOMImplementation domImpl = document.getImplementation();
					DocumentType docType = domImpl.createDocumentType("doctype", "-//Apache Software Foundation//DTD Struts Configuration 2.5//EN", "http://struts.apache.org/dtds/struts-2.5.dtd");

					NodeList nodeListFormat = (NodeList)xpath.evaluate("//text()[normalize-space()='']", document, XPathConstants.NODESET);
					for (int i = 0; i < nodeListFormat.getLength(); i++) {
						Node node = nodeListFormat.item(i);
						node.getParentNode().removeChild(node);
					}
					transformer.setOutputProperty("encoding", "UTF-8");
					transformer.setOutputProperty("omit-xml-declaration", "false");
					transformer.setOutputProperty("indent", "yes");
					transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "4");
					transformer.setOutputProperty("doctype-public", docType.getPublicId());
					transformer.setOutputProperty("doctype-system", docType.getSystemId());

					StreamResult streamResult = new StreamResult(regFile.getPath());
					transformer.transform(domSource, streamResult);
				}

				Document document = docBuilder.parse(targetFile);
				Element rootElement = document.getDocumentElement();
				DOMSource domSource = new DOMSource(document);

				NodeList nodeList = rootElement.getChildNodes();
				for (int i=0; i<nodeList.getLength(); i++) {
					Node node = nodeList.item(i);
					if (node.getNodeType() == 1) {
						Element packageElement = (Element)node;
						String packageString = CommonUtil.replace(CommonUtil.remove(javaTargetpath, rootPath + "src/main/java/"), "/", ".");

						Element actionElement = document.createElement("action");
						actionElement.setAttribute("name", menuNumber + "/*");
						actionElement.setAttribute("method", "{1}");
						actionElement.setAttribute("class", packageString + "." + thisMenuIdUpperCamelCase + "Action");

						Element resultElement = document.createElement("result");
						resultElement.setAttribute("name", "list");
						resultElement.setAttribute("type", "debugDispatcherResult");
						resultElement.setTextContent(jspRelPath + "/" + menuPathStr + "/" + thisMenuIdUpperCamelCase + "List.jsp");
						actionElement.appendChild(resultElement);

						pageNameSuffix = (CommonUtil.equalsIgnoreCase(pageType, "Page")) ? ".jsp" : "Pop.jsp";
						resultElement = document.createElement("result");
						resultElement.setAttribute("name", "detail");
						resultElement.setAttribute("type", "debugDispatcherResult");
						resultElement.setTextContent(jspRelPath + "/" + menuPathStr + "/" + thisMenuIdUpperCamelCase + "Detail"+pageNameSuffix);
						actionElement.appendChild(resultElement);

						resultElement = document.createElement("result");
						resultElement.setAttribute("name", "insert");
						resultElement.setAttribute("type", "debugDispatcherResult");
						resultElement.setTextContent(jspRelPath + "/" + menuPathStr + "/" + thisMenuIdUpperCamelCase + "Insert"+pageNameSuffix);
						actionElement.appendChild(resultElement);

						resultElement = document.createElement("result");
						resultElement.setAttribute("name", "update");
						resultElement.setAttribute("type", "debugDispatcherResult");
						resultElement.setTextContent(jspRelPath + "/" + menuPathStr + "/" + thisMenuIdUpperCamelCase + "Update"+pageNameSuffix);
						actionElement.appendChild(resultElement);
						packageElement.appendChild(actionElement);

						Comment commentElement = document.createComment(thisMenuIdUpperCamelCase + " - " + menuName);
						actionElement.getParentNode().insertBefore(commentElement, actionElement);

						DOMImplementation domImpl = document.getImplementation();
						DocumentType docType = domImpl.createDocumentType("doctype", "-//Apache Software Foundation//DTD Struts Configuration 2.5//EN", "http://struts.apache.org/dtds/struts-2.5.dtd");

						NodeList nodeListFormat = (NodeList)xpath.evaluate("//text()[normalize-space()='']", document, XPathConstants.NODESET);
						for (int j = 0; j < nodeListFormat.getLength(); j++) {
							Node nodeFormat = nodeListFormat.item(j);
							nodeFormat.getParentNode().removeChild(nodeFormat);
						}
						transformer.setOutputProperty("encoding", "UTF-8");
						transformer.setOutputProperty("omit-xml-declaration", "false");
						transformer.setOutputProperty("indent", "yes");
						transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "4");
						transformer.setOutputProperty("doctype-public", docType.getPublicId());
						transformer.setOutputProperty("doctype-system", docType.getSystemId());

						StreamResult streamResult = new StreamResult(targetFile.getPath());
						transformer.transform(domSource, streamResult);
					}
				}
			}

			return true;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	public boolean createMessageFile(DataSet dsRequest) throws Exception {
		String compilePath = "/target/alpaca";
		String isCreate = CommonUtil.nvl(dsRequest.getValue("createMessage"));
		String targetPath = dsRequest.getValue("messageConfigPath");
		String menuPathStr = CommonUtil.lowerCase(CommonUtil.replace(dsRequest.getValue("menuId"), ConfigUtil.getProperty("delimiter.data"), "/"));
		String[] menuId = CommonUtil.split(menuPathStr, "/");
		String rootMenuId = CommonUtil.lowerCase(menuId[0]);
		String thisMenuId = CommonUtil.lowerCase(menuId[1]);
		String menuName = dsRequest.getValue("menuName");
		String tableName = dsRequest.getValue("tableName");
		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String srcPath = rootPath + ConfigUtil.getProperty("path.sourceFile");
		String srcFileName = ConfigUtil.getProperty("name.source.propMessage");
		String thisMenuIdUpperCamelCase = CommonUtil.toCamelCaseStartUpperCase(thisMenuId);
		DataSet dsLang = ZebraCommonCodeManager.getCodeDataSetByCodeType("LANGUAGE_TYPE");
		BufferedReader bufferedReader = null;
		StringBuffer stringBuffer;
		String sourceString = "", sourceStringWithTable = "", tempString;
		DataSet dsTableInfo;

		try {
			if (CommonUtil.equalsIgnoreCase(isCreate, "Y")) {
				targetPath = targetPath + "/" + rootMenuId + "/" + thisMenuId;
				String targetFileName = "app-" + rootMenuId + "-" + thisMenuId;
				File targetFile = new File(targetPath + "/" + targetFileName + ".properties");
				createEmptyFile(targetFile);

				OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");

				if (CommonUtil.isBlank(tableName)) {
					bufferedReader = new BufferedReader(new FileReader(srcPath + "/" + srcFileName));
					stringBuffer = new StringBuffer();
					while ((tempString = bufferedReader.readLine()) != null) {
						stringBuffer.append(tempString + "\n");
					}
					sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");
					sourceString = CommonUtil.replace(sourceString, "#MENU_ID_START_UPPER#", thisMenuIdUpperCamelCase);
					sourceString = CommonUtil.replace(sourceString, "#MENU_NAME#", menuName);
					sourceString = CommonUtil.replace(sourceString, "#THIS_MENU_ID#", thisMenuId);
				} else {
					String search = "\n# List - Search\n", grid = "\n# List - Data Grid\n", header = "\n# Detail, Insert, Update - Table Header\n", listEtc = "\n# List - Etc\n";
					dsTableInfo = dummyDao.getTableDetailDataSetByTableName(tableName);
					sourceStringWithTable += "#############################################################################################\n";
					sourceStringWithTable += "# Messages - "+thisMenuIdUpperCamelCase+" - "+menuName+"\n";
					sourceStringWithTable += "#   - \n";
					sourceStringWithTable += "#############################################################################################\n";
					sourceStringWithTable += "# Button\n";
					for (int i=0; i<dsTableInfo.getRowCnt(); i++) {
						String colName = dsTableInfo.getValue(i, "COLUMN_NAME");
						String colNameUpperWord = CommonUtil.toWordStartUpperCase(dsTableInfo.getValue(i, "COLUMN_NAME"));
						String colNameLowerCamelCase = CommonUtil.toCamelCaseStartLowerCase(dsTableInfo.getValue(i, "COLUMN_NAME"));

						if (CommonUtil.isIn(colName, "INSERT_DATE", "UPDATE_DATE", "INSERT_USER_ID", "UPDATE_USER_ID")) {
							continue;
						}

						search += thisMenuId+".search."+colNameLowerCamelCase+"="+colNameUpperWord+"\n";
						grid += thisMenuId+".grid."+colNameLowerCamelCase+"="+colNameUpperWord+"\n";
						header += thisMenuId+".header."+colNameLowerCamelCase+"="+colNameUpperWord+"\n";
					}
					sourceStringWithTable += search+grid+listEtc+header;
					sourceStringWithTable += "\n# Message, Comments";
					sourceString = sourceStringWithTable;
				}

				osWriter.write(sourceString);
				osWriter.flush();

				for (int i=0; i<dsLang.getRowCnt(); i++) {
					String lang = CommonUtil.lowerCase(dsLang.getValue(i, "COMMON_CODE"));
					String srcNameTemp = CommonUtil.substringBefore(srcFileName, ".");
					String srcNameExt = CommonUtil.substringAfter(srcFileName, ".");
					String srcFileNameByLang = srcNameTemp + "_" + lang + "." + srcNameExt;

					targetFileName = "app-" + rootMenuId + "-" + thisMenuId + "_" + lang;
					targetFile = new File(targetPath + "/" + targetFileName + ".properties");
					createEmptyFile(targetFile);

					osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");

					if (CommonUtil.isBlank(tableName)) {
						bufferedReader = new BufferedReader(new FileReader(srcPath + "/" + srcFileNameByLang));
						stringBuffer = new StringBuffer();
						while ((tempString = bufferedReader.readLine()) != null) {
							stringBuffer.append(tempString + "\n");
						}
						sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");
						sourceString = CommonUtil.replace(sourceString, "#MENU_ID_START_UPPER#", thisMenuIdUpperCamelCase);
						sourceString = CommonUtil.replace(sourceString, "#MENU_NAME#", menuName);
						sourceString = CommonUtil.replace(sourceString, "#THIS_MENU_ID#", thisMenuId);
					} else {
						sourceString = sourceStringWithTable;
					}

					osWriter.write(sourceString);
					osWriter.flush();
				}

				osWriter.close();
				if (bufferedReader != null) {bufferedReader.close();}
			}

			return true;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	/*!
	 * Table Creation Script
	 */
	public DataSet getScriptFileDataSet(DataSet requestDataSet) throws Exception {
		DataSet dataSet = new DataSet();
		String compilePath = "/target/alpaca";
		String systemSearched = requestDataSet.getValue("system");
		String tableNameSearched = requestDataSet.getValue("tableName");
		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		File fwkPath = new File(rootPath+"/"+ConfigUtil.getProperty("path.tablescript.framework"));
		File pjtPath = new File(rootPath+"/"+ConfigUtil.getProperty("path.tablescript.project"));
		File[] fwkFiles = fwkPath.listFiles();
		File[] pjtFiles = pjtPath.listFiles();

		dataSet.addName(new String[] {"TABLE_NAME", "DESCRIPTION", "FILE_NAME", "FILE_SIZE", "UPDATE_DATE_TIME"});

		if (CommonUtil.isIn(systemSearched, "All", "Framework")) {
			for (File file : fwkFiles) {
				String tableName = getTableNameFromTableCreationScript(file);
				String description = getDescriptionFromTableCreationScript(file);

				if (CommonUtil.isNotBlank(tableNameSearched)) {
					if (CommonUtil.containsIgnoreCase(tableName, tableNameSearched)) {
						dataSet.addRow();
						dataSet.setValue(dataSet.getRowCnt()-1, "TABLE_NAME", tableName);
						dataSet.setValue(dataSet.getRowCnt()-1, "DESCRIPTION", description);
						dataSet.setValue(dataSet.getRowCnt()-1, "FILE_NAME", file.getName());
						dataSet.setValue(dataSet.getRowCnt()-1, "FILE_SIZE", CommonUtil.toString(CommonUtil.ceil(CommonUtil.toDouble(CommonUtil.toString(file.length()))/1024, 1), "#,###"));
						dataSet.setValue(dataSet.getRowCnt()-1, "UPDATE_DATE_TIME", CommonUtil.toDateTimeString(file.lastModified()));
					}
				} else {
					dataSet.addRow();
					dataSet.setValue(dataSet.getRowCnt()-1, "TABLE_NAME", tableName);
					dataSet.setValue(dataSet.getRowCnt()-1, "DESCRIPTION", description);
					dataSet.setValue(dataSet.getRowCnt()-1, "FILE_NAME", file.getName());
					dataSet.setValue(dataSet.getRowCnt()-1, "FILE_SIZE", CommonUtil.toString(CommonUtil.ceil(CommonUtil.toDouble(CommonUtil.toString(file.length()))/1024, 1), "#,###"));
					dataSet.setValue(dataSet.getRowCnt()-1, "UPDATE_DATE_TIME", CommonUtil.toDateTimeString(file.lastModified()));
				}
			}
		}

		if (CommonUtil.isIn(systemSearched, "All", "Project")) {
			for (File file : pjtFiles) {
				String tableName = getTableNameFromTableCreationScript(file);
				String description = getDescriptionFromTableCreationScript(file);

				if (CommonUtil.isNotBlank(tableNameSearched)) {
					if (CommonUtil.containsIgnoreCase(tableName, tableNameSearched)) {
						dataSet.addRow();
						dataSet.setValue(dataSet.getRowCnt()-1, "TABLE_NAME", tableName);
						dataSet.setValue(dataSet.getRowCnt()-1, "DESCRIPTION", description);
						dataSet.setValue(dataSet.getRowCnt()-1, "FILE_NAME", file.getName());
						dataSet.setValue(dataSet.getRowCnt()-1, "FILE_SIZE", CommonUtil.toString(CommonUtil.ceil(CommonUtil.toDouble(CommonUtil.toString(file.length()))/1024, 1), "#,###"));
						dataSet.setValue(dataSet.getRowCnt()-1, "UPDATE_DATE_TIME", CommonUtil.toDateTimeString(file.lastModified()));
					}
				} else {
					dataSet.addRow();
					dataSet.setValue(dataSet.getRowCnt()-1, "TABLE_NAME", tableName);
					dataSet.setValue(dataSet.getRowCnt()-1, "DESCRIPTION", description);
					dataSet.setValue(dataSet.getRowCnt()-1, "FILE_NAME", file.getName());
					dataSet.setValue(dataSet.getRowCnt()-1, "FILE_SIZE", CommonUtil.toString(CommonUtil.ceil(CommonUtil.toDouble(CommonUtil.toString(file.length()))/1024, 1), "#,###"));
					dataSet.setValue(dataSet.getRowCnt()-1, "UPDATE_DATE_TIME", CommonUtil.toDateTimeString(file.lastModified()));
				}
			}
		}

		return dataSet;
	}

	public DataSet getScriptFileDetailDataSet(String fileName) throws Exception {
		String compilePath = "/target/alpaca";
		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String path = (CommonUtil.containsIgnoreCase(fileName, "zebra")) ? ConfigUtil.getProperty("path.tablescript.framework") : ConfigUtil.getProperty("path.tablescript.project");
		File file = new File(rootPath+"/"+path+"/"+fileName);
		String tableName = getTableNameFromTableCreationScript(file);
		String description = getDescriptionFromTableCreationScript(file);
		DataSet columnDataSet = getColumnDataSetFromTableCreationScript(file);

		for (int i=0; i<columnDataSet.getRowCnt(); i++) {
			columnDataSet.setValue(i, "TABLE_NAME", tableName);
			columnDataSet.setValue(i, "TABLE_DESCRIPTION", description);
			columnDataSet.setValue(i, "NULLABLE", CommonUtil.nvl(columnDataSet.getValue(i, "NULLABLE"), "Y"));
		}

		return columnDataSet;
	}

	public int generateScriptFile(DataSet requestDataSet, DataSet tableDetailDataSet) throws Exception {
		String compilePath = "/target/alpaca";
		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String system = requestDataSet.getValue("system");
		String path = (CommonUtil.equalsIgnoreCase(system, "framework")) ? ConfigUtil.getProperty("path.tablescript.framework") : ConfigUtil.getProperty("path.tablescript.project");
		String tableName = requestDataSet.getValue("tableName");
		String fileNameIndex = "", fileName = "";
		int result = 0;

		fileNameIndex = getNextFileNameIndexFromDirectory(rootPath+"/"+path);
		fileName = fileNameIndex+"_"+CommonUtil.upperCase(tableName)+".sql";
		result = generateScriptFile(requestDataSet, tableDetailDataSet, rootPath+"/"+path+"/"+fileName);

		return result;
	}

	public int updateScriptFile(DataSet requestDataSet, DataSet tableDetailDataSet) throws Exception {
		String compilePath = "/target/alpaca";
		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String system = requestDataSet.getValue("system");
		String path = (CommonUtil.equalsIgnoreCase(system, "framework")) ? ConfigUtil.getProperty("path.tablescript.framework") : ConfigUtil.getProperty("path.tablescript.project");
		String fileName = requestDataSet.getValue("fileName");
		int result = 0;

		result = generateScriptFile(requestDataSet, tableDetailDataSet, rootPath+"/"+path+"/"+fileName);

		return result;
	}

	public int deleteTableCreationScriptFile(String fileName) throws Exception {
		String compilePath = "/target/alpaca";
		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String path = (CommonUtil.containsIgnoreCase(fileName, "zebra")) ? ConfigUtil.getProperty("path.tablescript.framework") : ConfigUtil.getProperty("path.tablescript.project");
		File file = new File(rootPath+"/"+path+"/"+fileName);
		int result = 0;

		if (file.exists()) {
			file.delete();
			result++;
		}

		return result;
	}

	public int deleteTableCreationScriptFiles(String fileNames[]) throws Exception {
		String compilePath = "/target/alpaca";
		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String path = "";
		int result = 0;

		for (String fileName : fileNames) {
			path = (CommonUtil.containsIgnoreCase(fileName, "zebra")) ? ConfigUtil.getProperty("path.tablescript.framework") : ConfigUtil.getProperty("path.tablescript.project");
			File file = new File(rootPath+"/"+path+"/"+fileName);

			if (file.exists()) {
				file.delete();
				result++;
			}
		}

		return result;
	}

	/*!
	 * Data Migration
	 */
	public int exeGenerateTable(String sourceDb, String targetDb, String tableName, String tableNamePrefix) throws Exception {
		DataSet tableInfoDataSet;
		String sql = "create table ", columnName, dataType, srcTableName, tgtTableName;
		int result = 0;

		try {
			if (CommonUtil.equalsIgnoreCase(tableName, "users")) {
				srcTableName = "e5zg0_users";
				tgtTableName = tableNamePrefix+tableName;
			} else {
				srcTableName = tableName;
				tgtTableName = tableNamePrefix+tableName;
			}

			// source db table info
			dummyDao.setDataSourceName(sourceDb);
			tableInfoDataSet = dummyDao.getTableDetailDataSetByTableNameForAdditionalDataSource(srcTableName);

			sql += tgtTableName+" (";
			for (int i=0; i<tableInfoDataSet.getRowCnt(); i++) {
				columnName = tableInfoDataSet.getValue(i, "COLUMN_NAME");
				dataType = tableInfoDataSet.getValue(i, "DATA_TYPE");

				columnName = (CommonUtil.equalsIgnoreCase(columnName, "date")) ? tableName+"_"+columnName : columnName;
				sql += columnName+" "+getDataTypeStringForDataMigration(dataType)+", ";
			}
			sql += "insert_date date";
			sql += ") ";
			sql += "pctfree 20 pctused 80 tablespace "+ConfigUtil.getProperty("name.tableSpace.data")+" storage(initial 100k next 100k maxextents 2000 pctincrease 0)";

			// create table to target db
			dummyDao.resetDataSourceName();
			result = dummyDao.exeGenerateTable(sql);

			sql = "comment on table "+tgtTableName+" is 'Created for data migration on "+CommonUtil.getSysdate("yyyy-MM-dd HH:mm:ss")+"'";
			result += dummyDao.exeGenerateTable(sql);

			return result;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	public int deleteData(String targetDb, String tableName, String tableNamePrefix) throws Exception {
		try {
			dummyDao.resetDataSourceName();
			return dummyDao.deleteData("delete from "+tableNamePrefix+tableName);
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	public int insertData(String sourceDb, String targetDb, String tableName, String tableNamePrefix) throws Exception {
		DataSet dataSet, tableInfoDataSet;
		String selectColumn = "", insertColumn = "", sqlInsert = "", sqlSelect = "select ", srcTableName, tgtTableName;
		String insertColumns[];
		int result = 0;

		try {
			if (CommonUtil.equalsIgnoreCase(tableName, "users")) {
				srcTableName = "e5zg0_users";
				tgtTableName = tableNamePrefix+tableName;
			} else {
				srcTableName = tableName;
				tgtTableName = tableNamePrefix+tableName;
			}

			// source db table info
			dummyDao.setDataSourceName(sourceDb);
			tableInfoDataSet = dummyDao.getTableDetailDataSetByTableNameForAdditionalDataSource(srcTableName);

			for (int i=0; i<tableInfoDataSet.getRowCnt(); i++) {
				String dataType = getDataTypeForDataMigration(tableInfoDataSet.getValue(i, "DATA_TYPE"));

				if (CommonUtil.equalsIgnoreCase(dataType, "date")) {
					selectColumn += "date_format("+tableInfoDataSet.getValue(i, "COLUMN_NAME")+", '%Y%m%d%H%i%s') as "+tableInfoDataSet.getValue(i, "COLUMN_NAME");
				} else {
					selectColumn += tableInfoDataSet.getValue(i, "COLUMN_NAME");
				}

				if (i != tableInfoDataSet.getRowCnt()-1) {
					selectColumn += ",";
				}
			}

			// select data from source db
			dummyDao.setDataSourceName(sourceDb);
			dataSet = dummyDao.getDataSetBySQLQuery(sqlSelect+selectColumn+" from "+srcTableName);

			for (int i=0; i<dataSet.getColumnCnt(); i++) {
				insertColumn += CommonUtil.equalsIgnoreCase(tableInfoDataSet.getValue(i, "COLUMN_NAME"), "date") ? tableName+"_"+tableInfoDataSet.getValue(i, "COLUMN_NAME")+"," : tableInfoDataSet.getValue(i, "COLUMN_NAME")+",";
			}
			insertColumn += "insert_date";
			insertColumns = CommonUtil.split(insertColumn, ",");

			for (int i=0; i<dataSet.getRowCnt(); i++) {
				sqlInsert = "";
				sqlInsert += "insert into "+tgtTableName+" ("+insertColumn+") ";
				sqlInsert += "values (";
				for (int j=0; j<insertColumns.length; j++) {
					String colName = CommonUtil.equalsIgnoreCase(insertColumns[j], tableName+"_date") ? CommonUtil.remove(insertColumns[j], tableName+"_") : insertColumns[j];
					if (CommonUtil.equalsIgnoreCase(colName, "insert_date")) {continue;}
					int rowIndex = tableInfoDataSet.getRowIndex("COLUMN_NAME", colName);
					String dataType = getDataTypeForDataMigration(tableInfoDataSet.getValue(rowIndex, "DATA_TYPE"));
					String value = CommonUtil.replace(dataSet.getValue(i, colName), "'", "''");

					if (CommonUtil.equalsIgnoreCase(dataType, "date")) {
						if (CommonUtil.isBlank(value) || CommonUtil.equals(value, "00000000000000")) {
							sqlInsert += "null,";
						} else {
							sqlInsert += "to_date('"+value+"', 'yyyymmddhh24miss'),";
						}
					} else if (CommonUtil.equalsIgnoreCase(dataType, "number")) {
						if (CommonUtil.isBlank(value)) {
							sqlInsert += "null,";
						} else {
							sqlInsert += value+",";
						}
					} else {
						if (CommonUtil.isBlank(value)) {
							sqlInsert += "null,";
						} else {
							sqlInsert += "'"+value+"',";
						}
					}
				}

				sqlInsert += "sysdate";
				sqlInsert += ")";

				// insert into target db
				dummyDao.resetDataSourceName();
				result += dummyDao.insertData(sqlInsert);
			}
			return result;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	/*!
	 * Common for this service
	 */
	private int generateScriptFile(DataSet requestDataSet, DataSet detailDataSet, String fileName) throws Exception {
		int result = 0;
		String tableSpaceData = ConfigUtil.getProperty("name.tableSpace.data");
		String tableSpaceIndex = ConfigUtil.getProperty("name.tableSpace.index");
		String tableNameUpperCase = CommonUtil.upperCase(requestDataSet.getValue("tableName"));
		String tableNameLowerCase = CommonUtil.lowerCase(requestDataSet.getValue("tableName"));
		String tableDescription = requestDataSet.getValue("tableDescription");
		String sqlString = "", commentTable = "", commentData = "", blank = "    ", pkCol = "", ukCol = "", fkColRef = "", consString = "", dataSectionString = "";
		File file = new File(fileName);
		OutputStreamWriter osWriter;

		if (file.exists()) {
			dataSectionString = getDataSectionString(file);
		}

		createEmptyFile(file);
		osWriter = new OutputStreamWriter(new FileOutputStream(file, true), "utf-8");

		commentTable += "/**\n";
		commentTable += " * Table Name  : "+tableNameUpperCase+"\n";
		commentTable += " * Description : "+tableDescription+"\n";
		commentTable += " */\n";

		commentData += "/**\n";
		commentData += " * Table Name  : "+tableNameUpperCase+"\n";
		commentData += " * Data        : \n";
		commentData += " */\n";

		sqlString += "drop table "+tableNameLowerCase+" cascade constraints;\n";
		sqlString += "purge recyclebin;\n";
		sqlString += "\n";
		sqlString += "create table "+tableNameLowerCase+" (\n";
		for (int i=0; i<detailDataSet.getRowCnt(); i++) {
			String colName = CommonUtil.lowerCase(detailDataSet.getValue(i, "COLUMN_NAME"));
			String dataType = CommonUtil.lowerCase(detailDataSet.getValue(i, "DATA_TYPE"));
			String dataLength = CommonUtil.lowerCase(detailDataSet.getValue(i, "DATA_LENGTH"));
			String numDataLength = CommonUtil.removeString(detailDataSet.getValue(i, "DATA_LENGTH_NUMBER"), " ");
			String defaultVal = CommonUtil.upperCase(detailDataSet.getValue(i, "DEFAULT_VALUE"));
			String nullable = CommonUtil.lowerCase(detailDataSet.getValue(i, "NULLABLE"));
			String keyType = CommonUtil.lowerCase(detailDataSet.getValue(i, "KEY_TYPE"));
			String fkRef = CommonUtil.lowerCase(detailDataSet.getValue(i, "FK_TABLE_COLUMN"));
			String colDesc = detailDataSet.getValue(i, "COLUMN_DESCRIPTION");
			String comma = "";
			boolean hasDefaultVal = (CommonUtil.isNotBlank(defaultVal)) ? true : false;
			boolean isNullable = (CommonUtil.equalsIgnoreCase(nullable, "Y")) ? true : false;

			if (CommonUtil.equalsIgnoreCase(keyType, ZebraCommonCodeManager.getCodeByConstants("CONSTRAINT_TYPE_PK"))) {
				pkCol += (CommonUtil.isBlank(pkCol)) ? colName : ", "+colName;
			} else if (CommonUtil.equalsIgnoreCase(keyType, ZebraCommonCodeManager.getCodeByConstants("CONSTRAINT_TYPE_UK"))) {
				ukCol += (CommonUtil.isBlank(ukCol)) ? colName : ", "+colName;
			} else if (CommonUtil.equalsIgnoreCase(keyType, ZebraCommonCodeManager.getCodeByConstants("CONSTRAINT_TYPE_FK"))) {
				fkColRef += (CommonUtil.isBlank(fkColRef)) ? colName+","+fkRef : ";"+colName+","+fkRef;
			}

			comma = (!hasDefaultVal && isNullable) ? "," : "";

			sqlString += blank+CommonUtil.rightPad(colName, 32, " ");
			if (CommonUtil.equalsIgnoreCase(dataType, ZebraCommonCodeManager.getCodeByConstants("DOMAIN_DATA_TYPE_DATE")) ||
					CommonUtil.equalsIgnoreCase(dataType, ZebraCommonCodeManager.getCodeByConstants("DOMAIN_DATA_TYPE_CLOB"))) {
				sqlString += CommonUtil.rightPad(dataType+comma, 20, " ");
			} else if (CommonUtil.equalsIgnoreCase(dataType, ZebraCommonCodeManager.getCodeByConstants("DOMAIN_DATA_TYPE_NUMBER"))) {
				if (CommonUtil.isNotBlank(numDataLength)) {
					sqlString += CommonUtil.rightPad(dataType+"("+numDataLength+")"+comma, 20, " ");
				} else {
					sqlString += CommonUtil.rightPad(dataType+comma, 20, " ");
				}
			} else {
				sqlString += CommonUtil.rightPad(dataType+"("+dataLength+")"+comma, 20, " ");
			}

			if (!hasDefaultVal && isNullable) {
				sqlString += CommonUtil.rightPad("", 40, " ");
				sqlString += "-- "+colDesc+"\n";
			} else {
				if (hasDefaultVal) {
					comma = (isNullable) ? "," : "";
					if (CommonUtil.equalsIgnoreCase(dataType, ZebraCommonCodeManager.getCodeByConstants("DOMAIN_DATA_TYPE_DATE")) ||
							CommonUtil.equalsIgnoreCase(dataType, ZebraCommonCodeManager.getCodeByConstants("DOMAIN_DATA_TYPE_CLOB")) ||
							CommonUtil.equalsIgnoreCase(dataType, ZebraCommonCodeManager.getCodeByConstants("DOMAIN_DATA_TYPE_NUMBER"))) {
						sqlString += CommonUtil.rightPad("default "+CommonUtil.lowerCase(defaultVal)+comma, 25, " ");
					} else {
						sqlString += CommonUtil.rightPad("default "+"'"+CommonUtil.upperCase(defaultVal)+"'"+comma, 25, " ");
					}

					if (isNullable) {
						sqlString += CommonUtil.rightPad("", 15, " ");
						sqlString += "-- "+colDesc+"\n";
					} else {
						sqlString += CommonUtil.rightPad("not null,", 15, " ");
						sqlString += "-- "+colDesc+"\n";
					}
				} else {
					if (isNullable) {
						sqlString += CommonUtil.rightPad("", 25, " ");
						sqlString += CommonUtil.rightPad("", 15, " ");
						sqlString += "-- "+colDesc+"\n";
					} else {
						sqlString += CommonUtil.rightPad("", 25, " ");
						sqlString += CommonUtil.rightPad("not null,", 15, " ");
						sqlString += "-- "+colDesc+"\n";
					}
				}
			}
		}

		if (CommonUtil.isNotBlank(fkColRef)) {
			String fkInfo[] = CommonUtil.split(fkColRef, ";");
			for (String fkCol : fkInfo) {
				String fkCR[] = CommonUtil.split(fkCol, ",");
				String fkColumnName = fkCR[0];
				String fkRefInfo[] = CommonUtil.split(fkCR[1], ".");
				String fkRefTable = fkRefInfo[0];
				String fkRefColName = fkRefInfo[1];

				consString += (CommonUtil.isBlank(consString)) ?
						"\n"+blank+"constraint fk_"+CommonUtil.uid()+" foreign key("+fkColumnName+")"+" references "+fkRefTable+"("+fkRefColName+")" :
						",\n"+blank+"constraint fk_"+CommonUtil.uid()+" foreign key("+fkColumnName+")"+" references "+fkRefTable+"("+fkRefColName+")";
			}
		}

		if (CommonUtil.isNotBlank(pkCol)) {
			consString += (CommonUtil.isBlank(consString)) ?
					"\n"+blank+"constraint pk_"+CommonUtil.abbreviate(tableNameLowerCase, 27)+" primary key("+pkCol+")" :
					",\n"+blank+"constraint pk_"+CommonUtil.abbreviate(tableNameLowerCase, 27)+" primary key("+pkCol+")";
		}

		if (CommonUtil.isNotBlank(ukCol)) {
			consString += (CommonUtil.isBlank(consString)) ?
					"\n"+blank+"constraint uk_"+CommonUtil.uid()+" unique("+ukCol+")" : ",\n"+blank+"constraint uk_"+CommonUtil.uid()+" unique("+ukCol+")";
		}

		consString += "\n"+blank+"using index tablespace "+tableSpaceIndex+" storage(initial 50k next 50k pctincrease 0)\n";

		sqlString += consString;
		sqlString += ")\n";
		sqlString += "pctfree 20 pctused 80 tablespace "+tableSpaceData+" storage(initial 100k next 100k maxextents 2000 pctincrease 0);\n";
		sqlString += "\n";
		sqlString += "comment on table  "+CommonUtil.rightPad(tableNameLowerCase, 62, " ")+" is '"+tableDescription+"';\n";
		for (int i=0; i<detailDataSet.getRowCnt(); i++) {
			sqlString += "comment on column "+CommonUtil.rightPad(tableNameLowerCase+"."+CommonUtil.lowerCase(detailDataSet.getValue(i, "COLUMN_NAME")), 62, " ")+" is '"+detailDataSet.getValue(i, "COLUMN_DESCRIPTION")+"';\n";
		}

		sqlString = commentTable+sqlString;

		if (CommonUtil.isNotBlank(dataSectionString)) {
			commentData += dataSectionString;
		}

		sqlString += "\n\n"+commentData;

		osWriter.write(sqlString);
		osWriter.flush();
		osWriter.close();

		return ++result;
	}

	private String getDataSectionString(File file) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String searchString = "* Data", lineToSkip = "*/";
		String rtnString = "", tempString;
		boolean isDataSectionRow = false;

		while ((tempString = br.readLine()) != null) {
			if (CommonUtil.startsWithIgnoreCase(CommonUtil.trim(tempString), searchString)) {
				isDataSectionRow = true;
				continue;
			}

			if (isDataSectionRow && CommonUtil.startsWithIgnoreCase(CommonUtil.trim(tempString), lineToSkip)) {
				continue;
			}

			if (isDataSectionRow) {
				rtnString += (CommonUtil.isBlank(rtnString)) ? tempString : "\n"+tempString;
			}
		}
		br.close();

		return rtnString;
	}

	private String getNextFileNameIndexFromDirectory(String directory) throws Exception {
		String rtn = "", fileName = "";
		File folder = new File(directory);
		File files[];
		int index1 = 0, index2 = 0;

		if (folder.isDirectory()) {
			files = folder.listFiles();
			for (File file : files) {
				fileName = file.getName();
				fileName = CommonUtil.substringBefore(fileName, "_");
				index1 = CommonUtil.toInt(fileName);
				if (index1 > index2) {index2 = index1;}
			}
			index2++;
			rtn = CommonUtil.leftPad(CommonUtil.toString(index2), 3, "0");
		}

		return rtn;
	}

	private boolean isExistingHDao(Document document, String daoId) throws Exception {
		boolean isExist = false;
		NodeList nodeList = document.getElementsByTagName("bean");
		Node node;
		String idValue, parentValue;

		for (int i=0; i<nodeList.getLength(); i++) {
			node = nodeList.item(i);
			idValue = node.getAttributes().getNamedItem("id").getNodeValue();

			if (CommonUtil.contains(idValue, "Mapper")) {
				continue;
			}

			parentValue = node.getAttributes().getNamedItem("parent").getNodeValue();

			if (CommonUtil.equalsIgnoreCase(idValue, daoId) && CommonUtil.equalsIgnoreCase(parentValue, "baseHDao")) {
				isExist = true;
				break;
			}
		}

		return isExist;
	}

	private boolean isExistingDao(Document document, String daoId) throws Exception {
		boolean isExist = false;
		NodeList nodeList = document.getElementsByTagName("bean");
		Node node;
		String idValue, parentValue;

		for (int i=0; i<nodeList.getLength(); i++) {
			node = nodeList.item(i);
			idValue = node.getAttributes().getNamedItem("id").getNodeValue();

			if (CommonUtil.contains(idValue, "Mapper")) {
				continue;
			}

			parentValue = node.getAttributes().getNamedItem("parent").getNodeValue();

			if (CommonUtil.equalsIgnoreCase(idValue, daoId) && CommonUtil.equalsIgnoreCase(parentValue, "namedParameterJdbcDao")) {
				isExist = true;
				break;
			}
		}

		return isExist;
	}

	private String getDataTypeString(String value) {
		if (CommonUtil.contains(CommonUtil.upperCase(value), "NUMBER")) {
			return "double";
		} else if (CommonUtil.contains(CommonUtil.upperCase(value), "DATE")) {
			return "Date";
		} else {
			return "String";
		}
	}

	private String getDataTypeStringForDataMigration(String dataType) {
		if (CommonUtil.containsIgnoreCase(dataType, "int")) {
			return "number(12)";
		} else if (CommonUtil.containsIgnoreCase(dataType, "decimal")) {
			return "number(12,2)";
		} else if (CommonUtil.containsIgnoreCase(dataType, "date") || CommonUtil.containsIgnoreCase(dataType, "datetime")) {
			return "date";
		} else {
			return "varchar2(1000)";
		}
	}

	private String getDataTypeForDataMigration(String value) {
		if (CommonUtil.containsIgnoreCase(value, "int") || CommonUtil.containsIgnoreCase(value, "decimal")) {
			return "number";
		} else if (CommonUtil.containsIgnoreCase(value, "date") || CommonUtil.containsIgnoreCase(value, "datetime")) {
			return "date";
		} else {
			return "varchar2";
		}
	}

	private String getDataTypeStringForMyBatisXml(String value) {
		if (CommonUtil.contains(CommonUtil.upperCase(value), "NUMBER")) {
			return "NUMERIC";
		} else if (CommonUtil.contains(CommonUtil.upperCase(value), "DATE")) {
			return "TIME";
		} else if (CommonUtil.contains(CommonUtil.upperCase(value), "CLOB")) {
			return "CLOB";
		} else {
			return "VARCHAR";
		}
	}

	private void createEmptyFile(File file) throws Exception {
		if (file.exists()) {
			String path = file.getAbsolutePath();
			Path pathSrc = Paths.get(path);
			Path pathTarget = Paths.get(path+".bak");

			Files.move(pathSrc, pathTarget, StandardCopyOption.REPLACE_EXISTING);
		}

		file.createNewFile();
	}

	private int getPrimaryKeyColumnCnt(DataSet dsTableInfo) throws Exception {
		int cnt = 0;

		for (int i=0; i<dsTableInfo.getRowCnt(); i++) {
			if (CommonUtil.containsIgnoreCase(dsTableInfo.getValue(i, "CONSTRAINT_TYPE"), "PK")) {
				cnt++;
			}
		}

		return cnt;
	}

	private String getXmlTypeProperty(String dataType, String dataLength) {
		if (CommonUtil.equalsIgnoreCase(dataType, "NUMBER")) {
			return " type=\"java.lang.Double\"";
		} else if (CommonUtil.equalsIgnoreCase(dataType, "DATE")) {
			return " type=\"java.util.Date\"";
		} else if (CommonUtil.equalsIgnoreCase(dataType, "CLOB")) {
			return " type=\"java.lang.String\"";
		} else {
			return " type=\"java.lang.String\" length=\""+dataLength+"\"";
		}
	}

	private String getTableNameFromTableCreationScript(File file) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String searchString = "create table", separator = "(";
		String rtnString = "", tempString;

		while ((tempString = br.readLine()) != null) {
			if (CommonUtil.startsWithIgnoreCase(CommonUtil.trim(tempString), searchString)) {
				rtnString = CommonUtil.trim(CommonUtil.substringAfter(CommonUtil.substringBefore(tempString, separator), searchString));
				break;
			}
		}
		br.close();

		return CommonUtil.upperCase(rtnString);
	}

	private String getDescriptionFromTableCreationScript(File file) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String searchString = "* Description", separator = ":";
		String rtnString = "", tempString;

		while ((tempString = br.readLine()) != null) {
			if (CommonUtil.startsWithIgnoreCase(CommonUtil.trim(tempString), searchString)) {
				rtnString = CommonUtil.trim(CommonUtil.substringAfter(tempString, separator));
				break;
			}
		}
		br.close();

		return rtnString;
	}

	private DataSet getColumnDataSetFromTableCreationScript(File file) throws Exception {
		DataSet dataSet = new DataSet();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String dataSetHeader[] = {"TABLE_NAME", "TABLE_DESCRIPTION", "COLUMN_NAME", "DATA_TYPE", "DATA_LENGTH", "DEFAULT_VALUE", "NULLABLE", "KEY_TYPE", "FK_TABLE_COLUMN", "COLUMN_DESCRIPTION"};
		String breakStr = "pctfree", tblNameRowStr = "create table", keyRowStr = "constraint", fkStr = "foreign key", pkStr = "primary key", ukStr = "unique";
		String defValStr = "default", notNullStr = "not";
		String tempString;
		boolean isColInfoRow = false;

		dataSet.addName(dataSetHeader);
		while ((tempString = br.readLine()) != null) {
			boolean hasDefVal = false;
			int nullableIdx = -1;
			tempString = CommonUtil.trim(tempString);

			if (CommonUtil.isBlank(tempString)) {continue;}

			if (CommonUtil.startsWithIgnoreCase(tempString, breakStr)) {break;}

			if (CommonUtil.startsWithIgnoreCase(tempString, tblNameRowStr)) {
				isColInfoRow = true;
				continue;
			}

			if (CommonUtil.startsWithIgnoreCase(tempString, keyRowStr)) {
				String keyCol[], fkTable = "", fkCol = "";
				isColInfoRow = false;

				if (CommonUtil.containsIgnoreCase(tempString, fkStr)) {
					keyCol = CommonUtil.splitWithTrim(CommonUtil.substringBetween(tempString, "foreign key(", ") references"), ",");
					fkTable = CommonUtil.substringBetween(tempString, "references ", "(");
					fkCol = CommonUtil.substringAfter(tempString, "references ");
					fkCol = CommonUtil.substringBetween(fkCol, "(", ")");
					for (int i=0; i<keyCol.length; i++) {
						int rowIdx = dataSet.getRowIndex("COLUMN_NAME", CommonUtil.upperCase(keyCol[i]));
						dataSet.setValue(rowIdx, "KEY_TYPE", (CommonUtil.isBlank(dataSet.getValue(rowIdx, "KEY_TYPE"))) ? "FK" : dataSet.getValue(rowIdx, "KEY_TYPE")+", FK");
						dataSet.setValue(rowIdx, "FK_TABLE_COLUMN", CommonUtil.upperCase(fkTable+"."+fkCol));
					}
				}

				if (CommonUtil.containsIgnoreCase(tempString, pkStr)) {
					keyCol = CommonUtil.splitWithTrim(CommonUtil.substringBetween(tempString, "(", ")"), ",");
					for (int i=0; i<keyCol.length; i++) {
						int rowIdx = dataSet.getRowIndex("COLUMN_NAME", CommonUtil.upperCase(keyCol[i]));
						dataSet.setValue(rowIdx, "KEY_TYPE", (CommonUtil.isBlank(dataSet.getValue(rowIdx, "KEY_TYPE"))) ? "PK" : dataSet.getValue(rowIdx, "KEY_TYPE")+", PK");
					}
				}

				if (CommonUtil.containsIgnoreCase(tempString, ukStr)) {
					keyCol = CommonUtil.splitWithTrim(CommonUtil.substringBetween(tempString, "(", ")"), ",");
					for (int i=0; i<keyCol.length; i++) {
						int rowIdx = dataSet.getRowIndex("COLUMN_NAME", CommonUtil.upperCase(keyCol[i]));
						dataSet.setValue(rowIdx, "KEY_TYPE", (CommonUtil.isBlank(dataSet.getValue(rowIdx, "KEY_TYPE"))) ? "UK" : dataSet.getValue(rowIdx, "KEY_TYPE")+", UK");
					}
				}
			}

			if (isColInfoRow) {
				String strArr[] = CommonUtil.split(tempString);
				String dataType = getDbDataTypeString(strArr[1]);
				String dataLength = getDbDataLengthString(strArr[1]);

				dataSet.addRow();

				dataSet.setValue(dataSet.getRowCnt()-1, "COLUMN_NAME", CommonUtil.upperCase(strArr[0]));
				dataSet.setValue(dataSet.getRowCnt()-1, "DATA_TYPE", dataType);
				dataSet.setValue(dataSet.getRowCnt()-1, "DATA_LENGTH", dataLength);
				if (!CommonUtil.contains(strArr[1], ",")) {
					if (CommonUtil.containsIgnoreCase(strArr[2], defValStr)) {
						hasDefVal = true;

						if (CommonUtil.equalsIgnoreCase(dataType, "VARCHAR2")) {
							dataSet.setValue(dataSet.getRowCnt()-1, "DEFAULT_VALUE", CommonUtil.substringBetween(strArr[3], "'", "'"));
						} else {
							dataSet.setValue(dataSet.getRowCnt()-1, "DEFAULT_VALUE", CommonUtil.upperCase(CommonUtil.removeString(strArr[3], ",")));
						}
					}

					nullableIdx = (hasDefVal) ? 4 : 2;
					if (CommonUtil.containsIgnoreCase(strArr[nullableIdx], notNullStr)) {
						dataSet.setValue(dataSet.getRowCnt()-1, "NULLABLE", "N");
					}
				}

				dataSet.setValue(dataSet.getRowCnt()-1, "COLUMN_DESCRIPTION", CommonUtil.trim(CommonUtil.substringAfter(tempString, "--")));
			}
		}
		br.close();

		return dataSet;
	}

	public boolean createJsSource(DataSet requestDataSet, String fileName, String jsString) throws Exception {
		String compilePath = "/target/alpaca";
		String jspPath = requestDataSet.getValue("jspSourcePath");
		String menuPathStr = CommonUtil.lowerCase(CommonUtil.replace(requestDataSet.getValue("menuId"), ConfigUtil.getProperty("delimiter.data"), "/"));
		String menuId[] = CommonUtil.split(menuPathStr, "/");
		String rootMenuId = CommonUtil.lowerCase(menuId[0]);
		String thisMenuId = CommonUtil.lowerCase(menuId[1]);
		String menuName = requestDataSet.getValue("menuName");
		String rootPath = CommonUtil.remove((String)MemoryBean.get("applicationRealPath"), compilePath);
		String srcPath = rootPath + ConfigUtil.getProperty("path.sourceFile");
		String srcFileName = ConfigUtil.getProperty("name.source.js");
		String thisMenuIdUpperCamelCase = CommonUtil.toCamelCaseStartUpperCase(thisMenuId);
		String jspTargetpath = jspPath + "js" + "/" + rootMenuId + "/" + thisMenuId;
		String sourceString, tempString;
		File targetFile;

		try {
				targetFile = new File(jspTargetpath+"/"+fileName);
				createEmptyFile(targetFile);

				BufferedReader bufferedReader = new BufferedReader(new FileReader(srcPath + "/" + srcFileName));
				StringBuffer stringBuffer = new StringBuffer();
				while ((tempString = bufferedReader.readLine()) != null) {
					stringBuffer.append(tempString + "\n");
				}
				OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");
				sourceString = CommonUtil.removeEnd(stringBuffer.toString(), "\n");
				sourceString += jsString;

				String menuUrl = rootMenuId + "/" + CommonUtil.remove(thisMenuId, rootMenuId);

				sourceString = CommonUtil.replace(sourceString, "#MENU_ID_START_UPPER#", thisMenuIdUpperCamelCase);
				sourceString = CommonUtil.replace(sourceString, "#MENU_NAME#", menuName);
				sourceString = CommonUtil.replace(sourceString, "#MENU_URL#", menuUrl);
				sourceString = CommonUtil.replace(sourceString, "#THIS_MENU_ID#", thisMenuId);
				sourceString = CommonUtil.replace(sourceString, "#FILE_NAME#", fileName);

				osWriter.write(sourceString);
				osWriter.flush();
				osWriter.close();
				bufferedReader.close();

			return true;
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
	}

	private String getDbDataTypeString(String value) {
		if (CommonUtil.containsIgnoreCase(value, "date")) {
			return "DATE";
		} else if (CommonUtil.containsIgnoreCase(value, "number")) {
			return "NUMBER";
		} else if (CommonUtil.containsIgnoreCase(value, "clob")) {
			return "CLOB";
		} else {
			return "VARCHAR2";
		}
	}

	private String getDbDataLengthString(String value) {
		if (CommonUtil.containsIgnoreCase(value, "date")) {
			return "7";
		} else if (CommonUtil.containsIgnoreCase(value, "number")) {
			if (CommonUtil.containsIgnoreCase(value, "(")) {
				return CommonUtil.substringBetween(value, "(", ")");
			} else {
				return "";
			}
		} else if (CommonUtil.containsIgnoreCase(value, "clob")) {
			return "4000";
		} else {
			return CommonUtil.substringBetween(value, "(", ")");
		}
	}
}