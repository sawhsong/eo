package com.es.portal.common.module.bizservice.webserviceclient;

import javax.ws.rs.core.MediaType;

import com.es.portal.common.extend.BaseBiz;

import net.sf.json.JSONArray;
import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.data.QueryAdvisor;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;
import zebra.util.JsonUtil;
import zebra.wssupport.RestServiceSupport;

public class WebServiceClientBizServiceImpl extends BaseBiz implements WebServiceClientBizService {
	private String providerUrl = ConfigUtil.getProperty("webService.perci.url");
	private String acceptTypeHeader = MediaType.APPLICATION_JSON;
	private String contentTypeHeader = MediaType.MULTIPART_FORM_DATA;
	private String hoursFormat = "##0.00";

	/*
	 * Common Services
	 */
	public DataSet getCommonLookupDataSet(String lookupType) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		DataSet lookupDataSet = new DataSet();
		String serviceUrl = "entity/lookups/"+lookupType;
		String result = "";

		result = RestServiceSupport.get(providerUrl, serviceUrl, acceptTypeHeader, queryAdvisor);
		lookupDataSet = JsonUtil.getDataSetFromJsonArrayString(result);

		return lookupDataSet;
	}

	/*
	 * Ipro - Timesheet
	 */
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

				if (CommonUtil.equalsIgnoreCase(dayOfWeek, thisDay)) {
					dayListAsCalendar.setValue(dayListAsCalendar.getRowCnt()-1, j,
						timesheetDayList.getValue(i, "workDate")+delimiter+
						timesheetDayList.getValue(i, "workDateFormatted")+delimiter+
						CommonUtil.getNumberMask(timesheetDayList.getValue(i, "totalHours"), hoursFormat)+delimiter+
						dailyDetail.getRowCnt()
					);

					if (CommonUtil.equalsIgnoreCase(thisDay, "Sun")) {
						if (timesheetDayList.getRowCnt()-1 != i) {
							dayListAsCalendar.addRow();
						}
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
			updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "hours", CommonUtil.nvl(CommonUtil.getNumberMask(requestDataSet.getValue("hours"+rowIdx), hoursFormat), "0.00"));
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
				totalHours += CommonUtil.toDouble(updatedDailyDetail.getValue(updatedDailyDetail.getRowCnt()-1, "hours"));
			}
		}

		for (int i=0; i<timesheetDayList.getRowCnt(); i++) {
			dayListWorkDate = timesheetDayList.getValue(i, "workDate");

			if (CommonUtil.equals(workDate, dayListWorkDate)) {
				jsonString = updatedDailyDetail.toJsonStringForEO();
				jsonString = "["+jsonString+"]";
				timesheetDayList.setValue(i, "timesheetDayDetailList", jsonString);
				timesheetDayList.setValue(i, "totalHours", CommonUtil.toString(totalHours, hoursFormat));
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
				postD.setValue(postD.getRowCnt()-1, "hours", (dailyDetailRowCnt > 1) ? CommonUtil.getNumberMask(dailyDetail.getValue(j, "hours"), hoursFormat) : CommonUtil.getNumberMask(timesheetDayList.getValue(i, "totalHours"), hoursFormat));
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

	/*
	 * Corporate - IPro
	 */
	public DataSet getIproListDataSet(ParamEntity paramEntity, String orgId) throws Exception {
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		DataSet corporateIproList = new DataSet();
		String serviceUrl = "corporate/"+orgId+"/ipros";
		String result = "";

		result = RestServiceSupport.get(providerUrl, serviceUrl, acceptTypeHeader, queryAdvisor);
		paramEntity.setObjectFromJsonString(result);
		corporateIproList = JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("corporateIproList"));

		return corporateIproList;
	}

	/*
	 * Employee - Leave
	 */
	public DataSet getLeaveAssignmentListDataSet(ParamEntity paramEntity, String personId) throws Exception {
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		DataSet assignmentList = new DataSet();
		String serviceUrl = "leave/"+personId+"/assignmentList";
		String result = "";

		result = RestServiceSupport.get(providerUrl, serviceUrl, acceptTypeHeader, queryAdvisor);
		paramEntity.setObjectFromJsonString(result);
		assignmentList = JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("leaveAssignmentList"));

		return assignmentList;
	}

	public DataSet getLeaveListDataSet(ParamEntity paramEntity, String personId, String assignmentId) throws Exception {
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		DataSet leaveList = new DataSet();
		String serviceUrl = "leave/"+personId+"/"+assignmentId+"/list";
		String result = "";

		result = RestServiceSupport.get(providerUrl, serviceUrl, acceptTypeHeader, queryAdvisor);
		paramEntity.setObjectFromJsonString(result);
		leaveList = JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("leaveList"));

		return leaveList;
	}

	public DataSet getAccrualListDataSet(ParamEntity paramEntity, String assignmentId) throws Exception {
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		DataSet accrualList = new DataSet();
		String serviceUrl = "leave/"+assignmentId+"/accruals";
		String result = "";

		result = RestServiceSupport.get(providerUrl, serviceUrl, acceptTypeHeader, queryAdvisor);
		paramEntity.setObjectFromJsonString(result);
		accrualList = JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("accrualList"));

		return accrualList;
	}

	public void getLeaveDetail(ParamEntity paramEntity, String leaveRequestId) throws Exception {
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		String serviceUrl = "leave/"+leaveRequestId+"/details";
		String result = "";

		result = RestServiceSupport.get(providerUrl, serviceUrl, acceptTypeHeader, queryAdvisor);
		paramEntity.setObjectFromJsonString(result);
	}

	public String postLeaveRequest(DataSet requestDataSet) throws Exception {
		DataSet postM = new DataSet(), postD = new DataSet();
		String serviceUrl = "", result = "";
		String delimiter = ConfigUtil.getProperty("delimiter.data");
		int detailLength = CommonUtil.toInt(requestDataSet.getValue("detailLength"));
		String leaveRequestId = requestDataSet.getValue("leaveRequestId");
		String headerM[] = new String[] {"leaveRequestId", "assignmentId", "startDate", "endDate", "leaveType", "leaveCategory", "duration", "durationUnit", "status", "reason", "dateDetails"};
		String headerD[] = new String[] {"detailId", "leaveRequestId", "leaveDate", "dateType", "hours", "description"};

		serviceUrl = "leave/apply";

		postM.addName(headerM);
		postM.addRow();
		postM.setValue(postM.getRowCnt()-1, "leaveRequestId", leaveRequestId);
		postM.setValue(postM.getRowCnt()-1, "assignmentId", requestDataSet.getValue("assignment"));
		postM.setValue(postM.getRowCnt()-1, "startDate", CommonUtil.removeString(requestDataSet.getValue("startDate"), "-"));
		postM.setValue(postM.getRowCnt()-1, "endDate", CommonUtil.removeString(requestDataSet.getValue("endDate"), "-"));
		postM.setValue(postM.getRowCnt()-1, "leaveType", requestDataSet.getValue("type"));
		postM.setValue(postM.getRowCnt()-1, "leaveCategory", requestDataSet.getValue("category"));
		postM.setValue(postM.getRowCnt()-1, "duration", requestDataSet.getValue("duration"));
		postM.setValue(postM.getRowCnt()-1, "durationUnit", CommonUtil.nvl(requestDataSet.getValue("durationUnits"), "H"));
		postM.setValue(postM.getRowCnt()-1, "status", "SU");
		postM.setValue(postM.getRowCnt()-1, "reason", requestDataSet.getValue("reason"));

		postD.addName(headerD);
		for (int i=0; i<detailLength; i++) {
			postD.addRow();

			postD.setValue(postD.getRowCnt()-1, "leaveRequestId", leaveRequestId);
			postD.setValue(postD.getRowCnt()-1, "leaveDate", CommonUtil.removeString(requestDataSet.getValue("date"+delimiter+i), "-"));
			postD.setValue(postD.getRowCnt()-1, "dateType", requestDataSet.getValue("dateType"+delimiter+i));
			postD.setValue(postD.getRowCnt()-1, "hours", requestDataSet.getValue("hours"+delimiter+i));
			postD.setValue(postD.getRowCnt()-1, "description", requestDataSet.getValue("description"+delimiter+i));
		}

		postM.setValue(postM.getRowCnt()-1, "dateDetails", "["+postD.toJsonStringForEO()+"]");

		result = RestServiceSupport.post(providerUrl, serviceUrl, acceptTypeHeader, postM);
		return CommonUtil.removeString(result, "\"");
	}

	public DataSet getDateDetail(ParamEntity paramEntity, String leaveRequestId, String assignmentId, String startDate, String endDate) throws Exception {
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		DataSet dateDetail = new DataSet();
		String serviceUrl = "leave/datedetails/";
		String result = "";

		queryAdvisor.addVariable("leaveRequestId", leaveRequestId);
		queryAdvisor.addVariable("assignmentId", assignmentId);
		queryAdvisor.addVariable("startDate", startDate);
		queryAdvisor.addVariable("endDate", endDate);

		result = RestServiceSupport.get(providerUrl, serviceUrl, acceptTypeHeader, queryAdvisor);
		paramEntity.setObjectFromJsonString(result);
		dateDetail = JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("dateDetails"));

		return dateDetail;
	}

	public String approveRejectLeaveRequest(DataSet requestDataSet) throws Exception {
		DataSet post = new DataSet();
		String serviceUrl = "", result = "";
		String leaveRequestId = requestDataSet.getValue("leaveRequestId");
		String mode = requestDataSet.getValue("mode");
		String header[] = new String[] {"leaveRequestId", "approverId", "comments", "approved", "approvRejectIpAddress"};

		serviceUrl = "leave/"+leaveRequestId+"/approve";

		post.addName(header);
		post.addRow();
		post.setValue(post.getRowCnt()-1, "leaveRequestId", leaveRequestId);
		post.setValue(post.getRowCnt()-1, "approverId", requestDataSet.getValue("approverId"));
		post.setValue(post.getRowCnt()-1, "comments", requestDataSet.getValue("approveRejectComments"));
		post.setValue(post.getRowCnt()-1, "approved", (CommonUtil.equalsIgnoreCase(mode, "approve")) ? "Y" : "N");
		post.setValue(post.getRowCnt()-1, "approvRejectIpAddress", requestDataSet.getValue("approveRejectIpAddress"));

		result = RestServiceSupport.post(providerUrl, serviceUrl, acceptTypeHeader, post);
		return CommonUtil.removeString(result, "\"");
	}

	/*
	 * Employee - Expense
	 */
	public DataSet getExpenseClaimListDataSet(ParamEntity paramEntity, String personId) throws Exception {
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		DataSet leaveList = new DataSet();
		String serviceUrl = "expense/"+personId+"/list";
		String result = "";

		result = RestServiceSupport.get(providerUrl, serviceUrl, acceptTypeHeader, queryAdvisor);
		paramEntity.setObjectFromJsonString(result);
		leaveList = JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("expenseList"));

		return leaveList;
	}

	public void getExpenseClaimDetailService(ParamEntity paramEntity, String expenseClaimId) throws Exception {
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		String serviceUrl = "expense/"+expenseClaimId+"/details";
		String result = "";

		result = RestServiceSupport.get(providerUrl, serviceUrl, acceptTypeHeader, queryAdvisor);
		paramEntity.setObjectFromJsonString(result);
	}

	public String postExpenseClaim(DataSet requestDataSet, DataSet fileDataSet) throws Exception {
		DataSet post = new DataSet();
		String serviceUrl = "", result = "";
		String header[] = new String[] {"expenseClaimId", "personId", "department", "expenseType", "dateOfClaim",
				"bsb", "accountName", "accountNumber", "amount", "gst", "description", "status",
				"approveRejectPersonId", "approveRejectDate", "approveRejectComments"};

		serviceUrl = "expense/save";

		post.addName(header);
		post.addRow();
		post.setValue(post.getRowCnt()-1, "expenseClaimId", requestDataSet.getValue("expenseClaimId"));
		post.setValue(post.getRowCnt()-1, "personId", requestDataSet.getValue("personId"));
		post.setValue(post.getRowCnt()-1, "department", requestDataSet.getValue("department"));
		post.setValue(post.getRowCnt()-1, "expenseType", requestDataSet.getValue("expenseType"));
		post.setValue(post.getRowCnt()-1, "dateOfClaim", CommonUtil.removeString(requestDataSet.getValue("dateOfClaim"), "-", "/"));
		post.setValue(post.getRowCnt()-1, "bsb", requestDataSet.getValue("bsb"));
		post.setValue(post.getRowCnt()-1, "accountName", requestDataSet.getValue("accountName"));
		post.setValue(post.getRowCnt()-1, "accountNumber", requestDataSet.getValue("accountNumber"));
		post.setValue(post.getRowCnt()-1, "amount", requestDataSet.getValue("amount"));
		post.setValue(post.getRowCnt()-1, "gst", requestDataSet.getValue("gst"));
		post.setValue(post.getRowCnt()-1, "description", requestDataSet.getValue("description"));
		post.setValue(post.getRowCnt()-1, "status", "SU");
		post.setValue(post.getRowCnt()-1, "approveRejectPersonId", requestDataSet.getValue("approveRejectPersonId"));
		post.setValue(post.getRowCnt()-1, "approveRejectDate", requestDataSet.getValue("approveRejectDate"));
		post.setValue(post.getRowCnt()-1, "approveRejectComments", requestDataSet.getValue("approveRejectComments"));

		result = RestServiceSupport.postAttachmentEO(providerUrl, serviceUrl, contentTypeHeader, acceptTypeHeader, post, fileDataSet);
		return CommonUtil.removeString(result, "\"");
	}

	public DataSet getAttachedFile(ParamEntity paramEntity, String expenseClaimId) throws Exception {
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		String serviceUrl = "expense/attachmentlist";
		String result = "";

		queryAdvisor.addVariable("expenseClaimId", expenseClaimId);

		result = RestServiceSupport.get(providerUrl, serviceUrl, acceptTypeHeader, queryAdvisor);
		paramEntity.setObjectFromJsonString(result);

		return JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("attachmentList"));
	}

	/*
	 * Login - User Profile
	 */
	public void getPersonProfileService(ParamEntity paramEntity, String personId) throws Exception {
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		String serviceUrl = "personprofile/"+personId;
		String result = "";

		result = RestServiceSupport.get(providerUrl, serviceUrl, acceptTypeHeader, queryAdvisor);
		paramEntity.setObjectFromJsonString(result);
	}

	public String postUserProfile(String personId, DataSet requestDataSet) throws Exception {
		DataSet post = new DataSet();
		String serviceUrl = "", result = "";
		String header[] = new String[] {"personId", "prefixCode", "firstName", "middleName", "surName", "preferredName", "dateOfBirth",
				"mobile", "landLine", "email", "street", "suburb", "stateCode", "postCode", "country",
				"emergencyContactName", "emergencyContactRelationship", "emergencyContactPhone", "emergencyContactEmail"};

		serviceUrl = "personprofile/";

		post.addName(header);
		post.addRow();
		post.setValue(post.getRowCnt()-1, "personId", personId);
		post.setValue(post.getRowCnt()-1, "prefixCode", requestDataSet.getValue("prefix"));
		post.setValue(post.getRowCnt()-1, "firstName", requestDataSet.getValue("firstName"));
		post.setValue(post.getRowCnt()-1, "middleName", requestDataSet.getValue("middleName"));
		post.setValue(post.getRowCnt()-1, "surName", requestDataSet.getValue("surname"));
		post.setValue(post.getRowCnt()-1, "preferredName", requestDataSet.getValue("preferredName"));
		post.setValue(post.getRowCnt()-1, "dateOfBirth", CommonUtil.replace(requestDataSet.getValue("dateOfBirth"), "-", ""));
		post.setValue(post.getRowCnt()-1, "mobile", requestDataSet.getValue("mobile"));
		post.setValue(post.getRowCnt()-1, "landLine", requestDataSet.getValue("landLine"));
		post.setValue(post.getRowCnt()-1, "email", requestDataSet.getValue("email"));
		post.setValue(post.getRowCnt()-1, "street", requestDataSet.getValue("street"));
		post.setValue(post.getRowCnt()-1, "suburb", requestDataSet.getValue("suburb"));
		post.setValue(post.getRowCnt()-1, "stateCode", requestDataSet.getValue("state"));
		post.setValue(post.getRowCnt()-1, "postCode", requestDataSet.getValue("postCode"));
		post.setValue(post.getRowCnt()-1, "country", requestDataSet.getValue("country"));
		post.setValue(post.getRowCnt()-1, "emergencyContactName", requestDataSet.getValue("emergencyContactName"));
		post.setValue(post.getRowCnt()-1, "emergencyContactRelationship", requestDataSet.getValue("emergencyContactRelationship"));
		post.setValue(post.getRowCnt()-1, "emergencyContactPhone", requestDataSet.getValue("emergencyContactPhone"));
		post.setValue(post.getRowCnt()-1, "emergencyContactEmail", requestDataSet.getValue("emergencyContactEmail"));

		result = RestServiceSupport.post(providerUrl, serviceUrl, acceptTypeHeader, post);
		return CommonUtil.removeString(result, "\"");
	}

	/*
	 * Index - Contact Us
	 */
	public String postContactUs(DataSet postDataSet) throws Exception {
		String result = "", serviceUrl = "website/contactus/";

		result = RestServiceSupport.post(providerUrl, serviceUrl, acceptTypeHeader, postDataSet);
		return CommonUtil.removeString(result, "\"");
	}
}