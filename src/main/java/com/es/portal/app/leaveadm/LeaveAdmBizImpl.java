package com.es.portal.app.leaveadm;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseBiz;
import com.es.portal.common.module.bizservice.webserviceclient.WebServiceClientBizService;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.exception.FrameworkException;
import zebra.export.ExportHelper;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;
import zebra.util.ExportUtil;

public class LeaveAdmBizImpl extends BaseBiz implements LeaveAdmBiz {
	@Autowired
	private WebServiceClientBizService wsClient;

	public ParamEntity getDefault(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getLeaveList(ParamEntity paramEntity) throws Exception {
		DataSet leaveList = new DataSet();

		try {
			leaveList = wsClient.getLeaveListAdmDataSet(paramEntity);

			paramEntity.setAjaxResponseDataSet(leaveList);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity doAction(ParamEntity paramEntity) throws Exception {
		int result = -1;

		try {
			result = wsClient.doLeaveAdmAction(paramEntity);

			if (result <= 0) {
				throw new FrameworkException("E801", getMessage("E801", paramEntity));
			}

			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity exeExport(ParamEntity paramEntity) throws Exception {
		DataSet leaveRequestList = new DataSet();
		ExportHelper exportHelper;
		String columnHeader[], fileHeader[];
		String pageTitle, fileName;

		try {
			pageTitle = "Leave Request List";
			fileName = "LeaveRequestList-"+CommonUtil.getSysdate(ConfigUtil.getProperty("format.date.java"));
			columnHeader = new String[]{"fullName", "leaveTypeDesc", "leaveCategory", "assignmentName", "duration", "durationUnitDesc", "startDate", "endDate", "statusDesc", "submittedDate", "approveRejectDate"};
			fileHeader = new String[]{"Name", "Type", "Category", "Assignment Name", "Duration", "Duration Unit", "Start Date", "End Date", "Status", "Date Submitted", "Date Processed"};

			leaveRequestList = wsClient.getLeaveListAdmDataSet(paramEntity);

			exportHelper = ExportUtil.getExportHelper("Excel");
			exportHelper.setPageTitle(pageTitle);
			exportHelper.setFileName(fileName);
			exportHelper.setColumnHeader(columnHeader);
			exportHelper.setFileHeader(fileHeader);
			exportHelper.setSourceDataSet(leaveRequestList);

			paramEntity.setSuccess(true);
			paramEntity.setFileToExport(exportHelper.createFile());
			paramEntity.setFileNameToExport(exportHelper.getFileName());
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}
}