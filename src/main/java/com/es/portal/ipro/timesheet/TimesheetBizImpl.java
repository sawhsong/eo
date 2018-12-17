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
		DataSet timesheetDayList = new DataSet(), dayListAsCalendar = new DataSet();
		String delimiter = ConfigUtil.getProperty("delimiter.data");
		String assignmentId = dsRequest.getValue("assignmentId");
		String startDateStr = dsRequest.getValue("startDate");
		String endDateStr = dsRequest.getValue("endDate");
		String header[] = new String[] {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

		try {
			timesheetDayList = timesheetBizService.getTimesheetDayListDataSet(paramEntity, assignmentId, startDateStr, endDateStr);

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

			paramEntity.setAjaxResponseDataSet(dayListAsCalendar);
			session.setAttribute("timesheetDayListDataSet", timesheetDayList);
			session.setAttribute("timesheetRateListDataSet", timesheetBizService.getTimesheetRateListDataSet(paramEntity));
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
		DataSet timesheetDailyDetail = new DataSet();
		String workDate = dsRequest.getValue("workDate");

		try {
			timesheetDailyDetail = timesheetBizService.getTimesheetDailyDetailDataSet((DataSet)session.getAttribute("timesheetDayListDataSet"), workDate);

			paramEntity.setAjaxResponseDataSet(timesheetDailyDetail);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}
}