package zebra.example.app.framework.tablescript;

import org.springframework.beans.factory.annotation.Autowired;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.data.QueryAdvisor;
import zebra.example.common.bizservice.framework.ZebraFrameworkBizService;
import zebra.example.common.extend.BaseBiz;
import zebra.exception.FrameworkException;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class TableScriptBizImpl extends BaseBiz implements TableScriptBiz {
	@Autowired
	private ZebraFrameworkBizService zebraFrameworkBizService;

	public ParamEntity getDefault(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity getList(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();

		try {
			queryAdvisor.setPagination(false);
			queryAdvisor.setRequestDataSet(requestDataSet);

			paramEntity.setAjaxResponseDataSet(zebraFrameworkBizService.getScriptFileDataSet(requestDataSet));
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity getDetail(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();

		try {
			paramEntity.setObject("resultDataSet", zebraFrameworkBizService.getScriptFileDetailDataSet(requestDataSet.getValue("fileName")));

			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity getInsert(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity exeInsert(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		DataSet tableDetailDataSet = new DataSet();
		String delimiter = ConfigUtil.getProperty("delimiter.data");
		String tableName = requestDataSet.getValue("tableName");
		String tableDesc = requestDataSet.getValue("tableDescription");
		String dataSetHeader[] = {"TABLE_NAME", "TABLE_DESCRIPTION", "COLUMN_NAME", "DATA_TYPE", "DATA_LENGTH", "DATA_LENGTH_NUMBER", "DEFAULT_VALUE", "NULLABLE", "KEY_TYPE", "FK_TABLE_COLUMN", "COLUMN_DESCRIPTION"};
		int detailLength = CommonUtil.toInt(requestDataSet.getValue("detailLength"));
		int result = -1;

		try {
			tableDetailDataSet.addName(dataSetHeader);
			for (int i=0; i<detailLength; i++) {
				tableDetailDataSet.addRow();
				tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "TABLE_NAME", tableName);
				tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "TABLE_DESCRIPTION", tableDesc);
				tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "COLUMN_NAME", requestDataSet.getValue("columnName"+delimiter+i));
				tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "DATA_TYPE", requestDataSet.getValue("dataType"+delimiter+i));
				tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "DATA_LENGTH", requestDataSet.getValue("dataLength"+delimiter+i));
				tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "DATA_LENGTH_NUMBER", requestDataSet.getValue("dataLengthNumber"+delimiter+i));
				tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "DEFAULT_VALUE", requestDataSet.getValue("defaultValue"+delimiter+i));
				tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "NULLABLE", requestDataSet.getValue("nullable"+delimiter+i));
				tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "KEY_TYPE", requestDataSet.getValue("keyType"+delimiter+i));
				tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "FK_TABLE_COLUMN", requestDataSet.getValue("fkRef"+delimiter+i));
				tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "COLUMN_DESCRIPTION", requestDataSet.getValue("description"+delimiter+i));
			}
			tableDetailDataSet.addRow();
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "TABLE_NAME", tableName);
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "TABLE_DESCRIPTION", tableDesc);
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "COLUMN_NAME", "INSERT_USER_ID");
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "DATA_TYPE", "VARCHAR2");
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "DATA_LENGTH", "30");
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "NULLABLE", "Y");
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "COLUMN_DESCRIPTION", "Insert User UID");

			tableDetailDataSet.addRow();
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "TABLE_NAME", tableName);
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "TABLE_DESCRIPTION", tableDesc);
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "COLUMN_NAME", "INSERT_DATE");
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "DATA_TYPE", "DATE");
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "DEFAULT_VALUE", "SYSDATE");
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "NULLABLE", "Y");
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "COLUMN_DESCRIPTION", "Insert Date");

			tableDetailDataSet.addRow();
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "TABLE_NAME", tableName);
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "TABLE_DESCRIPTION", tableDesc);
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "COLUMN_NAME", "UPDATE_USER_ID");
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "DATA_TYPE", "VARCHAR2");
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "DATA_LENGTH", "30");
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "NULLABLE", "Y");
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "COLUMN_DESCRIPTION", "Update User UID");

			tableDetailDataSet.addRow();
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "TABLE_NAME", tableName);
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "TABLE_DESCRIPTION", tableDesc);
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "COLUMN_NAME", "UPDATE_DATE");
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "DATA_TYPE", "DATE");
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "NULLABLE", "Y");
			tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "COLUMN_DESCRIPTION", "Update Date");

			result = zebraFrameworkBizService.generateScriptFile(requestDataSet, tableDetailDataSet);
			if (result <= 0) {
				throw new FrameworkException("E801", getMessage("E801", paramEntity));
			}

			paramEntity.setSuccess(true);
			paramEntity.setMessage("I801", getMessage("I801"));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity getUpdate(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity = getDetail(paramEntity);

			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity exeDelete(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		String fileName = requestDataSet.getValue("fileName");
		String chkForDel = requestDataSet.getValue("chkForDel");
		String[] fileNames = CommonUtil.splitWithTrim(chkForDel, ConfigUtil.getProperty("delimiter.record"));
		int result = -1;

		try {
			if (CommonUtil.isBlank(fileName)) {
				result = zebraFrameworkBizService.deleteTableCreationScriptFiles(fileNames);
			} else {
				result = zebraFrameworkBizService.deleteTableCreationScriptFile(fileName);
			}

			if (result <= 0) {
				throw new FrameworkException("E801", getMessage("E801", paramEntity));
			}

			paramEntity.setSuccess(true);
			paramEntity.setMessage("I801", getMessage("I801", paramEntity));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity exeUpdate(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		DataSet tableDetailDataSet = new DataSet();
		String delimiter = ConfigUtil.getProperty("delimiter.data");
		String tableName = requestDataSet.getValue("tableName");
		String tableDesc = requestDataSet.getValue("tableDescription");
		String dataSetHeader[] = {"TABLE_NAME", "TABLE_DESCRIPTION", "COLUMN_NAME", "DATA_TYPE", "DATA_LENGTH", "DATA_LENGTH_NUMBER", "DEFAULT_VALUE", "NULLABLE", "KEY_TYPE", "FK_TABLE_COLUMN", "COLUMN_DESCRIPTION"};
		int detailLength = CommonUtil.toInt(requestDataSet.getValue("detailLength"));
		int result = -1;

		try {
			tableDetailDataSet.addName(dataSetHeader);
			for (int i=0; i<detailLength; i++) {
				tableDetailDataSet.addRow();
				tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "TABLE_NAME", tableName);
				tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "TABLE_DESCRIPTION", tableDesc);
				tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "COLUMN_NAME", requestDataSet.getValue("columnName"+delimiter+i));
				tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "DATA_TYPE", requestDataSet.getValue("dataType"+delimiter+i));
				tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "DATA_LENGTH", requestDataSet.getValue("dataLength"+delimiter+i));
				tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "DATA_LENGTH_NUMBER", requestDataSet.getValue("dataLengthNumber"+delimiter+i));
				tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "DEFAULT_VALUE", requestDataSet.getValue("defaultValue"+delimiter+i));
				tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "NULLABLE", requestDataSet.getValue("nullable"+delimiter+i));
				tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "KEY_TYPE", requestDataSet.getValue("keyType"+delimiter+i));
				tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "FK_TABLE_COLUMN", requestDataSet.getValue("fkRef"+delimiter+i));
				tableDetailDataSet.setValue(tableDetailDataSet.getRowCnt()-1, "COLUMN_DESCRIPTION", requestDataSet.getValue("description"+delimiter+i));
			}

			result = zebraFrameworkBizService.updateScriptFile(requestDataSet, tableDetailDataSet);
			if (result <= 0) {
				throw new FrameworkException("E801", getMessage("E801", paramEntity));
			}

			paramEntity.setSuccess(true);
			paramEntity.setMessage("I801", getMessage("I801"));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}
}