package com.es.portal.ipro.timesheet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseBiz;
import com.es.portal.common.module.bizservice.timesheet.TimesheetBizService;

import net.sf.json.JSONArray;
import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.data.QueryAdvisor;
import zebra.example.common.bizservice.framework.ZebraFrameworkBizService;
import zebra.exception.FrameworkException;
import zebra.util.ConfigUtil;
import zebra.util.JsonUtil;
import zebra.wssupport.RestServiceSupport;

public class TimesheetBizImpl extends BaseBiz implements TimesheetBiz {
	@Autowired
	private TimesheetBizService zebraFrameworkBizService;

	public ParamEntity mytimesheets(ParamEntity paramEntity) throws Exception {
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		HttpSession session = paramEntity.getSession();
		DataSet assignment = new DataSet();
		String providerUrl = ConfigUtil.getProperty("webService.perci.url");
		String result = "", serviceUrl = "";
		String LoginId = (String)session.getAttribute("LoginId");

		try {
			serviceUrl = "users/"+LoginId+"/alltimesheets";
			result = RestServiceSupport.get(providerUrl, serviceUrl, "application/json", queryAdvisor);

			paramEntity.setObjectFromJsonString(result);
			assignment = JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("timesheetAssignmentList"));
			paramEntity.setObject("assignmentList", assignment);

			session.setAttribute("assignmentListDataSet", assignment);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getAssignmentInfo(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		HttpSession session = paramEntity.getSession();
		DataSet assignment = new DataSet(), asgInfo = new DataSet();
		String assignmentId = dsRequest.getValue("assignmentId");
		int rowIndex = -1;

		try {
			assignment = (DataSet)session.getAttribute("assignmentListDataSet");
			rowIndex = assignment.getRowIndex("assignmentId", assignmentId);

			asgInfo.addName(assignment.getNames());
			asgInfo.addRow();
			for (int i=0; i<assignment.getColumnCnt(); i++) {
				asgInfo.setValue(asgInfo.getRowCnt()-1, assignment.getName(i), assignment.getValue(rowIndex, i));
			}
			paramEntity.setAjaxResponseDataSet(asgInfo);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getTimesheetPeriod(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		HttpSession session = paramEntity.getSession();
		DataSet assignment = new DataSet(), timesheet = new DataSet();
		String assignmentId = dsRequest.getValue("assignmentId");
		String jsonArrayString = "";
		int rowIndex = -1;

		try {
			assignment = (DataSet)session.getAttribute("assignmentListDataSet");
			rowIndex = assignment.getRowIndex("assignmentId", assignmentId);
			jsonArrayString = assignment.getValue(rowIndex, "timesheetPeriodDateList");
			timesheet = JsonUtil.getDataSetFromJsonArrayString(jsonArrayString);

			paramEntity.setAjaxResponseDataSet(timesheet);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getTimesheetDetail(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		HttpSession session = paramEntity.getSession();
		DataSet assignment = new DataSet();
		String providerUrl = ConfigUtil.getProperty("webService.perci.url");
		String result = "", serviceUrl = "";
		String LoginId = (String)session.getAttribute("LoginId");

		try {
			serviceUrl = "users/"+LoginId+"/alltimesheets";
			result = RestServiceSupport.get(providerUrl, serviceUrl, "application/json", queryAdvisor);

			paramEntity.setObjectFromJsonString(result);
			assignment = JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("timesheetAssignmentList"));
			paramEntity.setObject("assignmentList", assignment);

			session.setAttribute("assignmentListDataSet", assignment);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}
}