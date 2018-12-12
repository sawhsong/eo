package com.es.portal.common.module.bizservice.timesheet;

import com.es.portal.common.extend.BaseBiz;

import net.sf.json.JSONArray;
import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.data.QueryAdvisor;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;
import zebra.util.JsonUtil;
import zebra.wssupport.RestServiceSupport;

public class TimesheetBizServiceImpl extends BaseBiz implements TimesheetBizService {
	private String providerUrl = ConfigUtil.getProperty("webService.perci.url");
	private String acceptTypeHeader = "application/json";

	public DataSet getAssignmentListDataSet(ParamEntity paramEntity, String loginId) throws Exception {
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		DataSet assignmentList = new DataSet();
		String serviceUrl = "users/"+loginId+"/alltimesheets";
		String result = "";

		result = RestServiceSupport.get(providerUrl, serviceUrl, acceptTypeHeader, queryAdvisor);
		paramEntity.setObjectFromJsonString(result);
		assignmentList = JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("timesheetAssignmentList"));

		return assignmentList;
	}

	public DataSet getAssignmentInfoDataSet(DataSet assignmentList, String assignmentId) throws Exception {
		DataSet assignmentInfo = new DataSet();
		int rowIndex = -1;

		rowIndex = assignmentList.getRowIndex("assignmentId", assignmentId);

		assignmentInfo.addName(assignmentList.getNames());
		assignmentInfo.addRow();
		for (int i=0; i<assignmentList.getColumnCnt(); i++) {
			assignmentInfo.setValue(assignmentInfo.getRowCnt()-1, assignmentList.getName(i), assignmentList.getValue(rowIndex, i));
		}

		return assignmentInfo;
	}

	public DataSet getTimesheetPeriodDataSet(DataSet assignmentList, String assignmentId) throws Exception {
		DataSet timesheetPeriod = new DataSet();
		String jsonArrayString = "";
		int rowIndex = -1;

		rowIndex = assignmentList.getRowIndex("assignmentId", assignmentId);
		jsonArrayString = assignmentList.getValue(rowIndex, "timesheetPeriodDateList");
		timesheetPeriod = JsonUtil.getDataSetFromJsonArrayString(jsonArrayString);

		return timesheetPeriod;
	}

	public DataSet getTimesheetDetailListDataSet(ParamEntity paramEntity, String assignmentId, String startDate, String endDate) throws Exception {
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		DataSet timesheetDetailList = new DataSet();
		String serviceUrl = "", result = "";

		startDate = CommonUtil.remove(startDate, "/");
		endDate = CommonUtil.remove(endDate, "/");
		serviceUrl = "timesheets/"+assignmentId+"/details";

		queryAdvisor.addVariable("startDate", startDate);
		queryAdvisor.addVariable("endDate", endDate);

		result = RestServiceSupport.get(providerUrl, serviceUrl, acceptTypeHeader, queryAdvisor);
		paramEntity.setObjectFromJsonString(result);
		timesheetDetailList = JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("timesheetAssignmentList"));

		return timesheetDetailList;
	}
}