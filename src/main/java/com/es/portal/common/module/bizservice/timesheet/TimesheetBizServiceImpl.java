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

	public DataSet getPeriodDataSetByAssignmentId(DataSet assignmentList, String assignmentId) throws Exception {
		DataSet timesheetPeriod = new DataSet();
		String jsonArrayString = "";
		int rowIndex = -1;

		rowIndex = assignmentList.getRowIndex("assignmentId", assignmentId);
		jsonArrayString = assignmentList.getValue(rowIndex, "timesheetPeriodDateList");
		timesheetPeriod = JsonUtil.getDataSetFromJsonArrayString(jsonArrayString);

		return timesheetPeriod;
	}

	public DataSet getRateListDataSet(ParamEntity paramEntity) throws Exception {
		return JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("timesheetRateList"));
	}

	public DataSet getDayListDataSetByPeriod(ParamEntity paramEntity, String assignmentId, String startDate, String endDate) throws Exception {
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

	public DataSet getDayListDataSetAsCalendar(DataSet timesheetDayList) throws Exception {
		DataSet dayListAsCalendar = new DataSet();
		String delimiter = ConfigUtil.getProperty("delimiter.data");
		String header[] = new String[] {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

		dayListAsCalendar.addName(header);
		for (int i=0; i<timesheetDayList.getRowCnt(); i++) {
			String dayOfWeek = CommonUtil.getDayOfWeek(timesheetDayList.getValue(i, "workDate"), "dd/MM/yyyy");

			if (i == 0) {
				dayListAsCalendar.addRow();
			}

			for (int j=0; j<dayListAsCalendar.getColumnCnt(); j++) {
				String thisDay = dayListAsCalendar.getName(j);

				if (CommonUtil.equalsAnyIgnoreCase(dayOfWeek, thisDay)) {
					dayListAsCalendar.setValue(dayListAsCalendar.getRowCnt()-1, j,
						timesheetDayList.getValue(i, "workDate")+delimiter+
						timesheetDayList.getValue(i, "workDateFormatted")+delimiter+
						timesheetDayList.getValue(i, "totalHours")
					);

					if (CommonUtil.equalsAnyIgnoreCase(thisDay, "Sun")) {
						dayListAsCalendar.addRow();
					}
				}
			}
		}
		return dayListAsCalendar;
	}

	public DataSet getDailyDetailDataSet(DataSet timesheetDayList, String workDate) throws Exception {
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

	public DataSet updateDailyDetail(DataSet timesheetDayList, DataSet requestDataSet) throws Exception {
		DataSet updatedDailyDetail = new DataSet();
		String delimiter = ConfigUtil.getProperty("delimiter.data");
		String timesheetUnits = requestDataSet.getValue("timesheetUnits");
		String workDate = requestDataSet.getValue("workDate");
		String rowIdx , startTimeHH, startTimeMM, endTimeHH, endTimeMM, nonWorkedTimeHH, nonWorkedTimeMM = "";
		String dayListWorkDate, jsonString = "";
		String header[] = new String[] {"deleted", "description", "endTime", "hours", "nonWorkedTime", "preferred", "rateId", "rowId", "startTime", "timesheetLineId", "workDate"};
		int detailLength = CommonUtil.toInt(requestDataSet.getValue("detailLength"));
		double totalHours = 0;

		updatedDailyDetail.addName(header);
		for (int i=0; i<detailLength; i++) {
			rowIdx = delimiter+i;

			updatedDailyDetail.addRow();
			updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "deleted", CommonUtil.nvl(requestDataSet.getValue("deleted"+rowIdx), "N"));
			updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "description", CommonUtil.nvl(requestDataSet.getValue("description"+rowIdx), ""));
			updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "hours", CommonUtil.nvl(requestDataSet.getValue("hours"+rowIdx), "0"));
			updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "preferred", CommonUtil.nvl(requestDataSet.getValue("preferred"+rowIdx), ""));
			updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "rateId", CommonUtil.nvl(requestDataSet.getValue("rates"+rowIdx), ""));
			updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "rowId", CommonUtil.nvl(requestDataSet.getValue("rowId"+rowIdx), "-1"));
			updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "timesheetLineId", CommonUtil.nvl(requestDataSet.getValue("timesheetLineId"+rowIdx), "-1"));
			updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "workDate", workDate);

			if (CommonUtil.isIn(timesheetUnits, "HSE", "DSE")) {
				startTimeHH = CommonUtil.nvl(requestDataSet.getValue("startTimeHH"+rowIdx), "00");
				startTimeMM = CommonUtil.nvl(requestDataSet.getValue("startTimeMM"+rowIdx), "00");
				endTimeHH = CommonUtil.nvl(requestDataSet.getValue("endTimeHH"+rowIdx), "00");
				endTimeMM = CommonUtil.nvl(requestDataSet.getValue("endTimeMM"+rowIdx), "00");
				nonWorkedTimeHH = CommonUtil.nvl(requestDataSet.getValue("nonWorkedTimeHH"+rowIdx), "00");
				nonWorkedTimeMM = CommonUtil.nvl(requestDataSet.getValue("nonWorkedTimeMM"+rowIdx), "00");

				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "startTime", startTimeHH+":"+startTimeMM);
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "endTime", endTimeHH+":"+endTimeMM);
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "nonWorkedTime", nonWorkedTimeHH+":"+nonWorkedTimeMM);
			} else {
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "startTime", CommonUtil.nvl(requestDataSet.getValue("startTime"+rowIdx), ""));
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "endTime", CommonUtil.nvl(requestDataSet.getValue("endTime"+rowIdx), ""));
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "nonWorkedTime", CommonUtil.nvl(requestDataSet.getValue("nonWorkedTime"+rowIdx), ""));
			}

			if (CommonUtil.equals(updatedDailyDetail.getValue(updatedDailyDetail.getRowCnt()-1, "deleted"), "N")) {
				totalHours += CommonUtil.toInt(updatedDailyDetail.getValue(updatedDailyDetail.getRowCnt()-1, "hours"));
			}
		}

		for (int i=0; i<timesheetDayList.getRowCnt(); i++) {
			dayListWorkDate = timesheetDayList.getValue(i, "workDate");

			if (CommonUtil.equals(workDate, dayListWorkDate)) {
				jsonString = updatedDailyDetail.toJsonStringForEO();
				jsonString = "["+jsonString+"]";
				timesheetDayList.setValue(i, "timesheetDayDetailList", jsonString);
				timesheetDayList.setValue(i, "totalHours", CommonUtil.toString(totalHours));
				break;
			}
		}

		return timesheetDayList;
	}

	public boolean postTimesheet(DataSet timesheetDayList, DataSet requestDataSet) throws Exception {
		boolean result = true;
		DataSet timesheetPostM, timesheetPostD = new DataSet();
		String status = requestDataSet.getValue("status");
		String headerM[] = new String[] {"assignmentId", "timesheetId", "periodStartDate", "periodEndDate", "dueDate", "status", "timesheetUnits"};
		String headerD[] = new String[] {"timesheetLineId", "workDate", "rateId", "hours", "description", "deleted", "startTime", "endTime", "nonWorkedTime"};

		

		return result;
	}
}