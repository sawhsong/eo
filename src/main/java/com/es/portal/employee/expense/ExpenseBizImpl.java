package com.es.portal.employee.expense;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseBiz;
import com.es.portal.common.module.bizservice.webserviceclient.WebServiceClientBizService;

import net.sf.json.JSONArray;
import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.exception.FrameworkException;
import zebra.util.CommonUtil;
import zebra.util.JsonUtil;

public class ExpenseBizImpl extends BaseBiz implements ExpenseBiz {
	@Autowired
	private WebServiceClientBizService wsClient;

	public ParamEntity getDefault(ParamEntity paramEntity) throws Exception {
		HttpSession session = paramEntity.getSession();
		String personId = CommonUtil.nvl((String)session.getAttribute("PersonIdForAdminTool"), (String)session.getAttribute("PersonId"));
		DataSet assignmentList = new DataSet();

		try {
			assignmentList = wsClient.getLeaveAssignmentListDataSet(paramEntity, personId);

			paramEntity.setObject("assignmentList", assignmentList);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getLeaveList(ParamEntity paramEntity) throws Exception {
		HttpSession session = paramEntity.getSession();
		DataSet dsRequest = paramEntity.getRequestDataSet();
		DataSet leaveList = new DataSet();
		String personId = CommonUtil.nvl((String)session.getAttribute("PersonIdForAdminTool"), (String)session.getAttribute("PersonId"));
		String assignmentId = CommonUtil.nvl(dsRequest.getValue("assignmentId"), "-1");

		try {
			leaveList = wsClient.getLeaveListDataSet(paramEntity, personId, assignmentId);

			paramEntity.setAjaxResponseDataSet(leaveList);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getLeaveDetail(ParamEntity paramEntity) throws Exception {
		HttpSession session = paramEntity.getSession();
		DataSet dsRequest = paramEntity.getRequestDataSet();
		DataSet leaveDetail = new DataSet(), assignmentList = new DataSet();
		String personId = CommonUtil.nvl((String)session.getAttribute("PersonIdForAdminTool"), (String)session.getAttribute("PersonId"));
		String leaveRequestId = dsRequest.getValue("leaveRequestId");
		String header[] = new String[] {"leaveRequestId", "assignmentId", "assignmentNumber", "assignmentName", "leaveType", "leaveTypeDesc", "leaveCategory", "leaveCategoryDesc",
				"startDate", "endDate", "duration", "durationUnit", "durationUnitDesc", "reason", "status", "statusDesc", "submittedDate", "approveRejectDate",
				"approveRejectPersonFullName", "approveRejectComments"};

		try {
			leaveDetail.addName(header);

			assignmentList = wsClient.getLeaveAssignmentListDataSet(paramEntity, personId);

			wsClient.getLeaveDetailService(paramEntity, leaveRequestId);
			paramEntity.setDataSetValueFromJsonResultset(leaveDetail);

			paramEntity.setObject("assignmentList", assignmentList);
			paramEntity.setObject("leaveTypeLookup", JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("leaveTypeList")));
			paramEntity.setObject("leaveCategoryLookup", JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("leaveCategoryList")));
			paramEntity.setObject("durationUnitLookup", JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("leaveDurationUnitList")));
			paramEntity.setObject("leaveDetail", leaveDetail);
			paramEntity.setObject("accrualList", JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("accrualList")));
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity loadAccrual(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		DataSet accrualList = new DataSet();
		String assignmentId = dsRequest.getValue("assignmentId");

		try {
			accrualList = wsClient.getAccrualListDataSet(paramEntity, assignmentId);

			paramEntity.setAjaxResponseDataSet(accrualList);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity saveLeave(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		String leaveRequestId = dsRequest.getValue("leaveRequestId");
		String result = "";

		try {
			result = wsClient.postLeaveRequest(leaveRequestId, dsRequest);

			if (!CommonUtil.startsWith(result, "2")) {
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