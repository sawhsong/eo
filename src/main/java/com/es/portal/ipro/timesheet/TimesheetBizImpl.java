package com.es.portal.ipro.timesheet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseBiz;
import com.es.portal.common.module.bizservice.webserviceclient.WebServiceClientBizService;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.exception.FrameworkException;
import zebra.util.CommonUtil;

public class TimesheetBizImpl extends BaseBiz implements TimesheetBiz {
	@Autowired
	private WebServiceClientBizService wsClient;

	public ParamEntity myTimesheets(ParamEntity paramEntity) throws Exception {
		HttpSession session = paramEntity.getSession();
		DataSet assignmentList = new DataSet();
		String loginId = CommonUtil.nvl((String)session.getAttribute("LoginIdForAdminTool"), (String)session.getAttribute("LoginId"));

		try {
			assignmentList = wsClient.getAssignmentListDataSet(paramEntity, loginId);
			session.setAttribute("assignmentListDataSetForTimesheet", assignmentList);
			paramEntity.setObject("assignmentList", assignmentList);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getAssignmentInfo(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		HttpSession session = paramEntity.getSession();
		DataSet assignmentInfo = new DataSet();
		String assignmentId = dsRequest.getValue("assignmentId");

		try {
			assignmentInfo = wsClient.getAssignmentInfoDataSet((DataSet)session.getAttribute("assignmentListDataSetForTimesheet"), assignmentId);

			paramEntity.setAjaxResponseDataSet(assignmentInfo);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getPeriodByAssignmentId(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		HttpSession session = paramEntity.getSession();
		DataSet timesheetPeriod = new DataSet();
		String assignmentId = dsRequest.getValue("assignmentId");

		try {
			timesheetPeriod = wsClient.getPeriodDataSetByAssignmentId((DataSet)session.getAttribute("assignmentListDataSetForTimesheet"), assignmentId);

			paramEntity.setAjaxResponseDataSet(timesheetPeriod);
			session.setAttribute("timesheetPeriodListDataSetByAssignmentId", timesheetPeriod);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getDayListByPeriod(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		HttpSession session = paramEntity.getSession();
		DataSet timesheetDayList = new DataSet(), dayListAsCalendar = new DataSet();
		String assignmentId = dsRequest.getValue("assignmentId");
		String startDateStr = dsRequest.getValue("startDate");
		String endDateStr = dsRequest.getValue("endDate");

		try {
			paramEntity = wsClient.getPeriodDetail(paramEntity, assignmentId, startDateStr, endDateStr);

			timesheetDayList = wsClient.getDayListDataSetByPeriod(paramEntity);
			dayListAsCalendar = wsClient.getDayListDataSetAsCalendar(timesheetDayList);

			paramEntity.setAjaxResponseDataSet(dayListAsCalendar);
			session.removeAttribute("timesheetDayListDataSetUpdated");
			session.setAttribute("timesheetDayListDataSet", timesheetDayList);
			session.setAttribute("timesheetPeriodDetailDataSet", wsClient.getPeriodDetailAsDataSet(paramEntity));
			session.setAttribute("timesheetRateListDataSet", wsClient.getRateListDataSet(paramEntity));
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity refreshDayListByPeriod(ParamEntity paramEntity) throws Exception {
		HttpSession session = paramEntity.getSession();
		DataSet dayListAsCalendar = new DataSet();

		try {
			dayListAsCalendar = wsClient.getDayListDataSetAsCalendar((DataSet)session.getAttribute("timesheetDayListDataSetUpdated"));

			paramEntity.setAjaxResponseDataSet(dayListAsCalendar);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getDailyDetailScreen(ParamEntity paramEntity) throws Exception {
		HttpSession session = paramEntity.getSession();

		try {
			paramEntity.setObject("ratesDataSet", (DataSet)session.getAttribute("timesheetRateListDataSet"));
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getDailyDetailData(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		HttpSession session = paramEntity.getSession();
		DataSet timesheetDayList = new DataSet(), timesheetDailyDetail = new DataSet();
		String workDate = dsRequest.getValue("workDate");

		try {
			timesheetDayList = getDayListDataSetFromSession(session);
			setDailyTotalHours(timesheetDayList, dsRequest);

			session.setAttribute("timesheetDayListDataSetUpdated", timesheetDayList);
			timesheetDailyDetail = wsClient.getDailyDetailDataSet(timesheetDayList, workDate);

			paramEntity.setAjaxResponseDataSet(timesheetDailyDetail);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity calculateTimeWorked(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		DataSet result = new DataSet(new String[] {"hoursWorked"});
		String hourMinutes = "";
		String startTimeHH = dsRequest.getValue("startTimeHH"), startTimeMM = dsRequest.getValue("startTimeMM");
		String endTimeHH = dsRequest.getValue("endTimeHH"), endTimeMM = dsRequest.getValue("endTimeMM");
		String nonWorkedTimeHH = dsRequest.getValue("nonWorkedTimeHH"), nonWorkedTimeMM = dsRequest.getValue("nonWorkedTimeMM");

		try {
			hourMinutes = CommonUtil.getTimeBetween(startTimeHH+":"+startTimeMM, endTimeHH+":"+endTimeMM, "HH:mm");
			hourMinutes = CommonUtil.getTimeBetween(nonWorkedTimeHH+":"+nonWorkedTimeMM, hourMinutes, "HH:mm");
			hourMinutes = CommonUtil.getHourlyUnits(hourMinutes);

			result.addRow();
			result.setValue(0, "hoursWorked", hourMinutes);

			paramEntity.setAjaxResponseDataSet(result);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity updateDailyDetail(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		HttpSession session = paramEntity.getSession();
		DataSet timesheetDayList;

		try {
			timesheetDayList = getDayListDataSetFromSession(session);

			wsClient.updateDailyDetail(timesheetDayList, dsRequest);
			session.setAttribute("timesheetDayListDataSetUpdated", timesheetDayList);

			paramEntity.setSuccess(true);
			paramEntity.setMessage("I801", getMessage("I801"));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity postTimesheet(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		HttpSession session = paramEntity.getSession();
		DataSet periodDetail = new DataSet(), timesheetDayList = new DataSet();
		String result = "";

		try {
			periodDetail = (DataSet)session.getAttribute("timesheetPeriodDetailDataSet");
			timesheetDayList = getDayListDataSetFromSession(session);
			setDailyTotalHours(timesheetDayList, dsRequest);

			result = wsClient.postTimesheet(periodDetail, timesheetDayList, dsRequest);

			if (!CommonUtil.startsWith(result, "2")) {
				throw new FrameworkException("E801", getMessage("E801", paramEntity));
			}

			session.removeAttribute("timesheetDayListDataSetUpdated");
			paramEntity.setSuccess(true);
			paramEntity.setMessage("I801", getMessage("I801"));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	/**
	 * Private Methods
	 */
	private DataSet getDayListDataSetFromSession(HttpSession session) throws Exception {
		DataSet timesheetDayList = new DataSet();

		timesheetDayList = (DataSet)session.getAttribute("timesheetDayListDataSetUpdated");
		if (timesheetDayList == null) {
			timesheetDayList = (DataSet)session.getAttribute("timesheetDayListDataSet");
		}

		return timesheetDayList;
	}

	private void setDailyTotalHours(DataSet timesheetDayList, DataSet requestDataSet) throws Exception {
		String hoursFormat = "##0.00";

		for (int i=0; i<timesheetDayList.getRowCnt(); i++) {
			String date = timesheetDayList.getValue(i, "workDate");
			for (int j=0; j<requestDataSet.getColumnCnt(); j++) {
				String rqDate = requestDataSet.getValue(j);
				if (CommonUtil.equals(date, rqDate)) {
					String delimiter = CommonUtil.remove(requestDataSet.getName(j), "workDate");
					timesheetDayList.setValue(i, "totalHours", CommonUtil.getNumberMask(requestDataSet.getValue("totalHours"+delimiter), hoursFormat));
				}
			}
		}
	}
}