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

	public ParamEntity getPeriodDetail(ParamEntity paramEntity, String assignmentId, String startDate, String endDate) throws Exception {
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		String serviceUrl = "", result = "";

		startDate = CommonUtil.remove(startDate, "/");
		endDate = CommonUtil.remove(endDate, "/");
		serviceUrl = "timesheets/"+assignmentId+"/details";

		queryAdvisor.addVariable("startDate", startDate);
		queryAdvisor.addVariable("endDate", endDate);
logger.debug("getTimesheetDayListDataSet : "+serviceUrl+"?startDate="+startDate+"&endDate="+endDate);
		result = RestServiceSupport.get(providerUrl, serviceUrl, acceptTypeHeader, queryAdvisor);
		paramEntity.setObjectFromJsonString(result);

		return paramEntity;
	}

	public DataSet getDayListDataSetByPeriod(ParamEntity paramEntity) throws Exception {
		return JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("timesheetDayList"));
	}

	public DataSet getPeriodDetailAsDataSet(ParamEntity paramEntity) throws Exception {
		DataSet periodDetail = new DataSet();

		periodDetail.addName(new String[] {"assignmentId", "timesheetId", "isDaily", "isWithStartEndTime", "noOfWeeks", "periodStartDate", "periodEndDate", "status"});
		periodDetail.addRow();
		periodDetail.setValue(periodDetail.getRowCnt()-1, "assignmentId", paramEntity.getObject("assignmentId"));
		periodDetail.setValue(periodDetail.getRowCnt()-1, "timesheetId", paramEntity.getObject("timesheetId"));
		periodDetail.setValue(periodDetail.getRowCnt()-1, "isDaily", paramEntity.getObject("isDaily"));
		periodDetail.setValue(periodDetail.getRowCnt()-1, "isWithStartEndTime", paramEntity.getObject("isWithStartEndTime"));
		periodDetail.setValue(periodDetail.getRowCnt()-1, "noOfWeeks", paramEntity.getObject("noOfWeeks"));
		periodDetail.setValue(periodDetail.getRowCnt()-1, "periodStartDate", paramEntity.getObject("periodStartDate"));
		periodDetail.setValue(periodDetail.getRowCnt()-1, "periodEndDate", paramEntity.getObject("periodEndDate"));
		periodDetail.setValue(periodDetail.getRowCnt()-1, "status", paramEntity.getObject("status"));

		return periodDetail;
	}

	public DataSet getRateListDataSet(ParamEntity paramEntity) throws Exception {
		return JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("timesheetRateList"));
	}

	public DataSet getDayListDataSetAsCalendar(DataSet timesheetDayList) throws Exception {
		DataSet dayListAsCalendar = new DataSet(), dailyDetail = new DataSet();
		String delimiter = ConfigUtil.getProperty("delimiter.data");
		String header[] = new String[] {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

		dayListAsCalendar.addName(header);
		for (int i=0; i<timesheetDayList.getRowCnt(); i++) {
			String dayOfWeek = CommonUtil.getDayOfWeek(timesheetDayList.getValue(i, "workDate"), "dd/MM/yyyy");

			dailyDetail = JsonUtil.getDataSetFromJsonArrayString(timesheetDayList.getValue(i, "timesheetDayDetailList"));

			if (i == 0) {
				dayListAsCalendar.addRow();
			}

			for (int j=0; j<dayListAsCalendar.getColumnCnt(); j++) {
				String thisDay = dayListAsCalendar.getName(j);

				if (CommonUtil.equalsAnyIgnoreCase(dayOfWeek, thisDay)) {
					dayListAsCalendar.setValue(dayListAsCalendar.getRowCnt()-1, j,
						timesheetDayList.getValue(i, "workDate")+delimiter+
						timesheetDayList.getValue(i, "workDateFormatted")+delimiter+
						timesheetDayList.getValue(i, "totalHours")+delimiter+
						dailyDetail.getRowCnt()
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
		String rowIdx = "", startTimeHH = "", startTimeMM = "", endTimeHH = "", endTimeMM = "", nonWorkedTimeHH = "", nonWorkedTimeMM = "";
		String dayListWorkDate = "", jsonString = "";
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

	public String postTimesheet(DataSet timesheetPeriodDetail, DataSet timesheetDayList, DataSet requestDataSet) throws Exception {
		DataSet dailyDetail = new DataSet(), postM = new DataSet(), postD = new DataSet();
		String serviceUrl = "", result = "";
		String timesheetUnits = requestDataSet.getValue("timesheetUnits");
		String dueDate = requestDataSet.getValue("dueDate");
		String status = requestDataSet.getValue("status");
		String headerM[] = new String[] {"assignmentId", "timesheetId", "periodStartDate", "periodEndDate", "dueDate", "status", "timesheetUnits", "timesheetDayDetailPostList"};
		String headerD[] = new String[] {"timesheetLineId", "workDate", "rateId", "hours", "description", "deleted", "startTime", "endTime", "nonWorkedTime"};

		serviceUrl = "timesheets/"+timesheetPeriodDetail.getValue("timesheetId")+"/save";

		postM.addName(headerM);
		postM.addRow();
		postM.setValue(postM.getRowCnt()-1, "assignmentId", timesheetPeriodDetail.getValue("assignmentId"));
		postM.setValue(postM.getRowCnt()-1, "timesheetId", timesheetPeriodDetail.getValue("timesheetId"));
		postM.setValue(postM.getRowCnt()-1, "periodStartDate", CommonUtil.remove(timesheetPeriodDetail.getValue("periodStartDate"), "/"));
		postM.setValue(postM.getRowCnt()-1, "periodEndDate", CommonUtil.remove(timesheetPeriodDetail.getValue("periodEndDate"), "/"));
		postM.setValue(postM.getRowCnt()-1, "dueDate", CommonUtil.remove(dueDate, "/"));
		postM.setValue(postM.getRowCnt()-1, "status", status);
		postM.setValue(postM.getRowCnt()-1, "timesheetUnits", timesheetUnits);

		postD.addName(headerD);
		for (int i=0; i<timesheetDayList.getRowCnt(); i++) {
			dailyDetail = JsonUtil.getDataSetFromJsonArrayString(timesheetDayList.getValue(i, "timesheetDayDetailList"));

			int dailyDetailRowCnt = dailyDetail.getRowCnt();
			for (int j=0; j<dailyDetailRowCnt; j++) {
				postD.addRow();

				postD.setValue(postD.getRowCnt()-1, "timesheetLineId", dailyDetail.getValue(j, "timesheetLineId"));
				postD.setValue(postD.getRowCnt()-1, "workDate", CommonUtil.remove(dailyDetail.getValue(j, "workDate"), "/"));
				postD.setValue(postD.getRowCnt()-1, "rateId", dailyDetail.getValue(j, "rateId"));
				postD.setValue(postD.getRowCnt()-1, "hours", (dailyDetailRowCnt > 1) ? dailyDetail.getValue(j, "hours") : timesheetDayList.getValue(i, "totalHours"));
				postD.setValue(postD.getRowCnt()-1, "description", dailyDetail.getValue(j, "description"));
				postD.setValue(postD.getRowCnt()-1, "deleted", dailyDetail.getValue(j, "deleted"));
				postD.setValue(postD.getRowCnt()-1, "startTime", dailyDetail.getValue(j, "startTime"));
				postD.setValue(postD.getRowCnt()-1, "endTime", dailyDetail.getValue(j, "endTime"));
				postD.setValue(postD.getRowCnt()-1, "nonWorkedTime", dailyDetail.getValue(j, "nonWorkedTime"));
			}
		}

		postM.setValue(postM.getRowCnt()-1, "timesheetDayDetailPostList", "["+postD.toJsonStringForEO()+"]");

		result = RestServiceSupport.post(providerUrl, serviceUrl, acceptTypeHeader, postM);
		return CommonUtil.removeString(result, "\"");
	}
}