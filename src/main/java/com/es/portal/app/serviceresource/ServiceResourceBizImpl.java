package com.es.portal.app.serviceresource;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseBiz;
import com.es.portal.common.module.bizservice.webserviceclient.WebServiceClientBizService;
import com.es.portal.common.module.commonlookup.CommonLookupManager;

import net.sf.json.JSONArray;
import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.exception.FrameworkException;
import zebra.util.CipherUtil;
import zebra.util.CommonUtil;
import zebra.util.JsonUtil;

public class ServiceResourceBizImpl extends BaseBiz implements ServiceResourceBiz {
	@Autowired
	private WebServiceClientBizService wsClient;

	/*
	 * Main page
	 */
	public ParamEntity doDefault(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setObject("resultDataSet", new DataSet());
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	/*
	 * Employee - Leave
	 */
	public ParamEntity approveLeave(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		String ids = dsRequest.getValue("id"), leaveRequestId = "", approverId = "";
		String decodedIds = CipherUtil.decode(ids);
		DataSet leaveDetail = new DataSet();
		String header[] = new String[] {"leaveRequestId", "assignmentId", "assignmentNumber", "assignmentName", "leaveType", "leaveTypeDesc", "leaveCategory", "leaveCategoryDesc",
				"startDate", "endDate", "duration", "durationUnit", "durationUnitDesc", "reason", "status", "statusDesc", "submittedDate", "approveRejectDate",
				"approveRejectPersonId", "approveRejectPersonFullName", "approveRejectComments"};

		try {
			if (CommonUtil.isBlank(decodedIds)) {
				paramEntity.setSuccess(true);
				return paramEntity;
			}

			leaveRequestId = CommonUtil.split(decodedIds, "_")[0];
			approverId = CommonUtil.split(decodedIds, "_")[1];

			leaveDetail.addName(header);

			wsClient.getLeaveDetail(paramEntity, leaveRequestId);

			paramEntity.setDataSetValueFromJsonResultset(leaveDetail);
			leaveDetail.setValue("approveRejectPersonId", approverId);

			paramEntity.setObject("leaveDetail", leaveDetail);
			paramEntity.setObject("durationUnit", CommonLookupManager.getLookupMeaning("LEAVE_DURATION", "H"));
			paramEntity.setObject("accrualList", JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("accrualList")));
			paramEntity.setObject("dateDetails", JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("dateDetails")));
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity approveRejectLeaveRequest(ParamEntity paramEntity) throws Exception {
		HttpServletRequest request = paramEntity.getRequest();
		DataSet dsRequest = paramEntity.getRequestDataSet();
		String result = "";

		try {
			dsRequest.addColumn("approveRejectIpAddress", request.getRemoteAddr());
			result = wsClient.approveRejectLeaveRequest(dsRequest);

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

	public ParamEntity getDateDetail(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		DataSet dateDetail = new DataSet();
		String leaveRequestId = dsRequest.getValue("leaveRequestId");
		String assignmentId = dsRequest.getValue("assignmentId");
		String startDate = CommonUtil.removeString(dsRequest.getValue("startDate"), "-", "/");
		String endDate = CommonUtil.removeString(dsRequest.getValue("endDate"), "-", "/");

		try {
			dateDetail = wsClient.getDateDetail(paramEntity, leaveRequestId, assignmentId, startDate, endDate);

			paramEntity.setAjaxResponseDataSet(dateDetail);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}
}