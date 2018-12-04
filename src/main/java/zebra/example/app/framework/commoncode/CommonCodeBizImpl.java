package zebra.example.app.framework.commoncode;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.data.QueryAdvisor;
import zebra.example.common.extend.BaseBiz;
import zebra.example.common.module.commoncode.ZebraCommonCodeManager;
import zebra.example.conf.resource.ormapper.dao.ZebraCommonCode.ZebraCommonCodeDao;
import zebra.example.conf.resource.ormapper.dto.oracle.ZebraCommonCode;
import zebra.exception.FrameworkException;
import zebra.export.ExportHelper;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;
import zebra.util.ExportUtil;

public class CommonCodeBizImpl extends BaseBiz implements CommonCodeBiz {
	@Autowired
	private ZebraCommonCodeDao zebraCommonCodeDao;

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
		String codeType = requestDataSet.getValue("commonCodeType");

		try {
			queryAdvisor.setPagination(true);
			queryAdvisor.addAutoFillCriteria(codeType, "code_type = '"+codeType+"'");

			paramEntity.setAjaxResponseDataSet(zebraCommonCodeDao.getActiveCommonCodeDataSet(queryAdvisor));
			paramEntity.setTotalResultRows(queryAdvisor.getTotalResultRows());
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
		HttpSession session = paramEntity.getSession();
		String delimiter = ConfigUtil.getProperty("delimiter.data");
		String codeType = CommonUtil.upperCase(requestDataSet.getValue("codeTypeMaster"));
		String processFrom = (String)paramEntity.getObject("processFrom");
		DataSet detailDataSet;
		int detailLength = CommonUtil.toInt(requestDataSet.getValue("detailLength"));
		int result = -1, masterDataRow = -1;
		ZebraCommonCode zebraCommonCode = new ZebraCommonCode();

		try {
			zebraCommonCode.setCodeType(codeType);
			zebraCommonCode.setCommonCode("0000000000");
			zebraCommonCode.setDescriptionEn(requestDataSet.getValue("descriptionEnMaster"));
			zebraCommonCode.setDescriptionKo(requestDataSet.getValue("descriptionKoMaster"));
			zebraCommonCode.setProgramConstants(codeType + "_0000000000");
			zebraCommonCode.setSortOrder("000");
			zebraCommonCode.setUseYn(CommonUtil.nvl(requestDataSet.getValue("useYnMaster"), "N"));
			zebraCommonCode.setDefaultYn(ZebraCommonCodeManager.getCodeByConstants("USE_YN_N"));
			zebraCommonCode.setInsertUserId((String)session.getAttribute("UserId"));
			if (CommonUtil.equalsIgnoreCase(processFrom, "update")) {
				detailDataSet = (DataSet)paramEntity.getObject("detailDataSet");
				masterDataRow = (int)paramEntity.getObject("masterDataRow");

				zebraCommonCode.setDefaultYn(detailDataSet.getValue(masterDataRow, "DEFAULT_YN"));
				zebraCommonCode.setInsertUserId(detailDataSet.getValue(masterDataRow, "INSERT_USER_ID"));
				zebraCommonCode.setInsertDate(CommonUtil.toDate(detailDataSet.getValue(masterDataRow, "INSERT_DATE")));
				zebraCommonCode.setUpdateUserId((String)session.getAttribute("UserId"));
				zebraCommonCode.setUpdateDate(CommonUtil.toDate(CommonUtil.getSysdate()));
			}

			result = zebraCommonCodeDao.insert(zebraCommonCode);
			if (result <= 0) {
				throw new FrameworkException("E801", getMessage("E801", paramEntity));
			}

			result = 0;
			for (int i=0; i<detailLength; i++) {
				String commonCode = requestDataSet.getValue("commonCodeDetail" + delimiter + i);

				zebraCommonCode.setCommonCode(commonCode);
				zebraCommonCode.setDescriptionEn(requestDataSet.getValue("descriptionEnDetail" + delimiter + i));
				zebraCommonCode.setDescriptionKo(requestDataSet.getValue("descriptionKoDetail" + delimiter + i));
				zebraCommonCode.setProgramConstants(codeType + "_" + CommonUtil.upperCase(commonCode));
				zebraCommonCode.setSortOrder(requestDataSet.getValue("sortOrderDetail" + delimiter + i));
				zebraCommonCode.setUseYn(CommonUtil.nvl(requestDataSet.getValue("useYnDetail" + delimiter + i), "N"));

				result += zebraCommonCodeDao.insert(zebraCommonCode);
			}

			if (result != detailLength) {
				throw new FrameworkException("E801", getMessage("E801", paramEntity));
			}

			paramEntity.setSuccess(true);
			paramEntity.setMessage("I801", getMessage("I801"));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity getDetail(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();

		try {
			paramEntity.setObject("resultDataSet", zebraCommonCodeDao.getCommonCodeDataSetByCodeType(requestDataSet.getValue("codeType")));

			paramEntity.setSuccess(true);
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

	public ParamEntity exeUpdate(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		String codeType = CommonUtil.upperCase(requestDataSet.getValue("codeTypeMaster"));
		DataSet detailDataSet;
		int result = -1;
		int masterDataRow = -1;

		try {
			detailDataSet = zebraCommonCodeDao.getCommonCodeDataSetByCodeType(codeType);
			masterDataRow = detailDataSet.getRowIndex("COMMON_CODE", "0000000000");

			result = zebraCommonCodeDao.delete(codeType);
			if (result <= 0) {
				throw new FrameworkException("E801", getMessage("E801", paramEntity));
			}

			paramEntity.setObject("processFrom", "update");
			paramEntity.setObject("masterDataRow", masterDataRow);
			paramEntity.setObject("detailDataSet", detailDataSet);
			paramEntity = exeInsert(paramEntity);

			paramEntity.setSuccess(true);
			paramEntity.setMessage("I801", getMessage("I801", paramEntity));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		
		return paramEntity;
	}

	public ParamEntity exeDelete(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		String codeType = requestDataSet.getValue("codeType");
		String chkForDel = requestDataSet.getValue("chkForDel");
		String[] codeTypes = CommonUtil.splitWithTrim(chkForDel, ConfigUtil.getProperty("delimiter.record"));
		int result = -1;

		try {
			if (CommonUtil.isBlank(codeType)) {
				result = zebraCommonCodeDao.delete(codeTypes);
			} else {
				result = zebraCommonCodeDao.delete(codeType);
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

	public ParamEntity exeExport(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		ExportHelper exportHelper;
		String dataRange = requestDataSet.getValue("dataRange");
		String codeType = requestDataSet.getValue("commonCodeType");

		try {
			String pageTitle = "Common Code List";
			String fileName = "CommonCodeList";
			String[] columnHeader = {"code_type", "common_code", "description_en", "program_constants"};

			exportHelper = ExportUtil.getExportHelper(requestDataSet.getValue("fileType"));
			exportHelper.setPageTitle(pageTitle);
			exportHelper.setColumnHeader(columnHeader);
			exportHelper.setFileName(fileName);
			exportHelper.setPdfWidth(1000);

			queryAdvisor.addAutoFillCriteria(codeType, "code_type = '"+codeType+"'");
			if (CommonUtil.containsIgnoreCase(dataRange, "all"))
				queryAdvisor.setPagination(false);
			else {
				queryAdvisor.setPagination(true);
			}

			exportHelper.setSourceDataSet(zebraCommonCodeDao.getActiveCommonCodeDataSet(queryAdvisor));

			paramEntity.setSuccess(true);
			paramEntity.setFileToExport(exportHelper.createFile());
			paramEntity.setFileNameToExport(exportHelper.getFileName());
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}
}