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
logger.debug("getAssignmentListDataSet : "+serviceUrl);
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

	public DataSet getTimesheetRateListDataSet(ParamEntity paramEntity) throws Exception {
		return JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("timesheetRateList"));
	}

	public DataSet getTimesheetDayListDataSet(ParamEntity paramEntity, String assignmentId, String startDate, String endDate) throws Exception {
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		DataSet timesheetDayList = new DataSet();
		String serviceUrl = "", result = "";

		startDate = CommonUtil.remove(startDate, "/");
		endDate = CommonUtil.remove(endDate, "/");
		serviceUrl = "timesheets/"+assignmentId+"/details";

		queryAdvisor.addVariable("startDate", startDate);
		queryAdvisor.addVariable("endDate", endDate);
logger.debug("getTimesheetDayListDataSet : "+serviceUrl+"?startDate="+startDate+"&endDate="+endDate);
		result = RestServiceSupport.get(providerUrl, serviceUrl, acceptTypeHeader, queryAdvisor);
		paramEntity.setObjectFromJsonString(result);
		timesheetDayList = JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("timesheetDayList"));

		return timesheetDayList;
	}

	public DataSet getTimesheetDailyDetailDataSet(DataSet timesheetDayList, String workDate) throws Exception {
		DataSet timesheetDailyDetail = new DataSet();
		String dayListWorkDate = "", jsonArrayString = "";

		for (int i=0; i<timesheetDayList.getRowCnt(); i++) {
			dayListWorkDate = timesheetDayList.getValue(i, "workDate");

			if (CommonUtil.equals(workDate, dayListWorkDate)) {
				jsonArrayString = timesheetDayList.getValue(i, "timesheetDayDetailList");
				timesheetDailyDetail = JsonUtil.getDataSetFromJsonArrayString(jsonArrayString);

				break;
			}
		}

		return timesheetDailyDetail;
	}
}