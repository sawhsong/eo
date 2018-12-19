package com.es.portal.ipro.timesheet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseBiz;
import com.es.portal.common.module.bizservice.timesheet.TimesheetBizService;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.exception.FrameworkException;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class TimesheetBizImpl extends BaseBiz implements TimesheetBiz {
	@Autowired
	private TimesheetBizService timesheetBizService;

	public ParamEntity mytimesheets(ParamEntity paramEntity) throws Exception {
		HttpSession session = paramEntity.getSession();
		DataSet assignmentList = new DataSet();
		String loginId = (String)session.getAttribute("LoginId");

		try {
			assignmentList = timesheetBizService.getAssignmentListDataSet(paramEntity, loginId);
			paramEntity.setObject("assignmentList", assignmentList);
			session.setAttribute("assignmentListDataSet", assignmentList);
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
			assignmentInfo = timesheetBizService.getAssignmentInfoDataSet((DataSet)session.getAttribute("assignmentListDataSet"), assignmentId);

			paramEntity.setAjaxResponseDataSet(assignmentInfo);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getTimesheetPeriod(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		HttpSession session = paramEntity.getSession();
		DataSet timesheetPeriod = new DataSet();
		String assignmentId = dsRequest.getValue("assignmentId");

		try {
			timesheetPeriod = timesheetBizService.getTimesheetPeriodDataSet((DataSet)session.getAttribute("assignmentListDataSet"), assignmentId);

			paramEntity.setAjaxResponseDataSet(timesheetPeriod);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getTimesheetDayList(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		HttpSession session = paramEntity.getSession();
		DataSet timesheetDayList, dayListAsCalendar = new DataSet();
		String assignmentId = dsRequest.getValue("assignmentId");
		String startDateStr = dsRequest.getValue("startDate");
		String endDateStr = dsRequest.getValue("endDate");

		try {
			timesheetDayList = timesheetBizService.getTimesheetDayListDataSet(paramEntity, assignmentId, startDateStr, endDateStr);
			dayListAsCalendar = getDayListDataSetAsCalendar(timesheetDayList);

			paramEntity.setAjaxResponseDataSet(dayListAsCalendar);
			session.removeAttribute("timesheetDayListDataSetUpdated");
			session.setAttribute("timesheetDayListDataSet", timesheetDayList);
			session.setAttribute("timesheetRateListDataSet", timesheetBizService.getTimesheetRateListDataSet(paramEntity));
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity refreshTimesheetDayList(ParamEntity paramEntity) throws Exception {
		HttpSession session = paramEntity.getSession();
		DataSet dayListAsCalendar = new DataSet();

		try {
			dayListAsCalendar = getDayListDataSetAsCalendar((DataSet)session.getAttribute("timesheetDayListDataSetUpdated"));

			paramEntity.setAjaxResponseDataSet(dayListAsCalendar);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getTimesheetDailyDetail(ParamEntity paramEntity) throws Exception {
		HttpSession session = paramEntity.getSession();

		try {
			paramEntity.setObject("ratesDataSet", (DataSet)session.getAttribute("timesheetRateListDataSet"));
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getTimesheetDailyDetailData(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		HttpSession session = paramEntity.getSession();
		DataSet timesheetDayList, timesheetDailyDetail = new DataSet();
		String workDate = dsRequest.getValue("workDate");
		String delimiter = "";

		try {
			timesheetDayList = (DataSet)session.getAttribute("timesheetDayListDataSetUpdated");
			if (timesheetDayList == null) {
				timesheetDayList = (DataSet)session.getAttribute("timesheetDayListDataSet");
			}

			for (int i=0; i<timesheetDayList.getRowCnt(); i++) {
				String date = timesheetDayList.getValue(i, "workDate");
				for (int j=0; j<dsRequest.getColumnCnt(); j++) {
					String rqDate = dsRequest.getValue(j);
					if (CommonUtil.equals(date, rqDate)) {
						delimiter = CommonUtil.remove(dsRequest.getName(j), "workDate");
						timesheetDayList.setValue(i, "totalHours", dsRequest.getValue("totalHours"+delimiter));
					}
				}
			}

			session.setAttribute("timesheetDayListDataSetUpdated", timesheetDayList);
			timesheetDailyDetail = timesheetBizService.getTimesheetDailyDetailDataSet(timesheetDayList, workDate);

			paramEntity.setAjaxResponseDataSet(timesheetDailyDetail);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity updateTimesheetDailyDetail(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		HttpSession session = paramEntity.getSession();
		DataSet timesheetDayList, updatedDailyDetail = new DataSet();
		String delimiter = ConfigUtil.getProperty("delimiter.data");
		String workDate = dsRequest.getValue("workDate");
		String timesheetUnits = dsRequest.getValue("timesheetUnits");
		String rowIdx , startTimeHH, startTimeMM, endTimeHH, endTimeMM, nonWorkedTimeHH, nonWorkedTimeMM = "";
		String header[] = new String[] {"deleted", "description", "endTime", "hours", "nonWorkedTime", "preferred", "rateId", "rowId", "startTime", "timesheetLineId", "workDate"};
		int detailLength = CommonUtil.toInt(dsRequest.getValue("detailLength"));
		int totalHours = 0;

		try {
			timesheetDayList = (DataSet)session.getAttribute("timesheetDayListDataSetUpdated");
			if (timesheetDayList == null) {
				timesheetDayList = (DataSet)session.getAttribute("timesheetDayListDataSet");
			}

			updatedDailyDetail.addName(header);
			for (int i=0; i<detailLength; i++) {
				rowIdx = delimiter+i;

				updatedDailyDetail.addRow();
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "deleted", CommonUtil.nvl(dsRequest.getValue("deleted"+rowIdx), "N"));
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "description", CommonUtil.nvl(dsRequest.getValue("description"+rowIdx), ""));
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "hours", CommonUtil.nvl(dsRequest.getValue("hours"+rowIdx), "0"));
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "preferred", CommonUtil.nvl(dsRequest.getValue("preferred"+rowIdx), ""));
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "rateId", CommonUtil.nvl(dsRequest.getValue("rates"+rowIdx), ""));
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "rowId", CommonUtil.nvl(dsRequest.getValue("rowId"+rowIdx), "-1"));
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "timesheetLineId", CommonUtil.nvl(dsRequest.getValue("timesheetLineId"+rowIdx), "-1"));
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "workDate", workDate);

				if (CommonUtil.isIn(timesheetUnits, "HSE", "DSE")) {
					startTimeHH = CommonUtil.nvl(dsRequest.getValue("startTimeHH"+rowIdx), "00");
					startTimeMM = CommonUtil.nvl(dsRequest.getValue("startTimeMM"+rowIdx), "00");
					endTimeHH = CommonUtil.nvl(dsRequest.getValue("endTimeHH"+rowIdx), "00");
					endTimeMM = CommonUtil.nvl(dsRequest.getValue("endTimeMM"+rowIdx), "00");
					nonWorkedTimeHH = CommonUtil.nvl(dsRequest.getValue("nonWorkedTimeHH"+rowIdx), "00");
					nonWorkedTimeMM = CommonUtil.nvl(dsRequest.getValue("nonWorkedTimeMM"+rowIdx), "00");

					updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "startTime", startTimeHH+":"+startTimeMM);
					updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "endTime", endTimeHH+":"+endTimeMM);
					updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "nonWorkedTime", nonWorkedTimeHH+":"+nonWorkedTimeMM);
				} else {
					updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "startTime", CommonUtil.nvl(dsRequest.getValue("startTime"+rowIdx), ""));
					updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "endTime", CommonUtil.nvl(dsRequest.getValue("endTime"+rowIdx), ""));
					updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "nonWorkedTime", CommonUtil.nvl(dsRequest.getValue("nonWorkedTime"+rowIdx), ""));
				}

				if (CommonUtil.equals(updatedDailyDetail.getValue(updatedDailyDetail.getRowCnt()-1, "deleted"), "N")) {
					totalHours += CommonUtil.toInt(updatedDailyDetail.getValue(updatedDailyDetail.getRowCnt()-1, "hours"));
				}
			}

			timesheetBizService.updateTimesheetDailyDetail(timesheetDayList, updatedDailyDetail, workDate, totalHours);
			session.setAttribute("timesheetDayListDataSetUpdated", timesheetDayList);

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
	private DataSet getDayListDataSetAsCalendar(DataSet timesheetDayList) throws Exception {
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
}