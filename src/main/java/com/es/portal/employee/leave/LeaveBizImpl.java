package com.es.portal.employee.leave;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseBiz;
import com.es.portal.common.module.bizservice.webserviceclient.WebServiceClientBizService;

import net.sf.json.JSONArray;
import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.exception.FrameworkException;
import zebra.util.JsonUtil;

public class LeaveBizImpl extends BaseBiz implements LeaveBiz {
	@Autowired
	private WebServiceClientBizService wsClient;

	public ParamEntity myLeaves(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getLeaveList(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		DataSet iproList = new DataSet();
		String assignmentId = dsRequest.getValue("assignmentId");

		try {
			iproList = wsClient.getLeaveListDataSet(paramEntity, assignmentId);

			paramEntity.setAjaxResponseDataSet(iproList);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getLeaveDetail(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		DataSet leaveDetails = new DataSet();
		String leaveRequestId = dsRequest.getValue("leaveRequestId");
		String header[] = new String[] {"leaveRequestId", "assignmentId", "assignmentNumber", "leaveType", "leaveTypeDesc", "leaveCategory", "leaveCategoryDesc",
				"duration", "durationUnit", "durationUnitDesc", "startDate", "endDate", "reason", "status", "statusDesc"};

		try {
			leaveDetails.addName(header);

			wsClient.getLeaveDetailService(paramEntity, leaveRequestId);
			paramEntity.setDataSetValueFromJsonResultset(leaveDetails);

			paramEntity.setObject("leaveCategoryLookup", JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("leaveCategoryList")));
			paramEntity.setObject("leaveTypeLookup", JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("leaveTypeList")));
			paramEntity.setObject("durationUnitLookup", JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("durationUnitList")));
			paramEntity.setObject("leaveDetails", leaveDetails);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity newLeave(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}
}