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
			if (session.getAttribute("timesheetDailyDetail_"+workDate) == null) {
				timesheetDailyDetail = timesheetBizService.getTimesheetDailyDetailDataSet((DataSet)session.getAttribute("timesheetDayListDataSet"), workDate);
			} else {
				timesheetDailyDetail = (DataSet)session.getAttribute("timesheetDailyDetail_"+workDate);
			}

			paramEntity.setAjaxResponseDataSet(timesheetDailyDetail);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity doUpdateTimesheetDailyDetail(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		HttpSession session = paramEntity.getSession();
		DataSet updatedDailyDetail = new DataSet();
		String delimiter = ConfigUtil.getProperty("delimiter.data");
		String workDate = dsRequest.getValue("workDate");
		String header[] = new String[] {"deleted", "description", "endTime", "hours", "nonWorkedTime", "preferred", "rateId", "rowId", "startTime", "timesheetLineId", "workDate"};
		int detailLength = CommonUtil.toInt(dsRequest.getValue("detailLength"));

		try {
			updatedDailyDetail.addName(header);
			for (int i=0; i<detailLength; i++) {
				updatedDailyDetail.addRow();
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "deleted", CommonUtil.nvl(dsRequest.getValue("deleted"+delimiter+i), "N"));
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "description", CommonUtil.nvl(dsRequest.getValue("description"+delimiter+i), ""));
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "endTime", CommonUtil.nvl(dsRequest.getValue("endTime"+delimiter+i), ""));
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "hours", CommonUtil.nvl(dsRequest.getValue("hours"+delimiter+i), "0"));
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "nonWorkedTime", CommonUtil.nvl(dsRequest.getValue("nonWorkedTime"+delimiter+i), ""));
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "preferred", CommonUtil.nvl(dsRequest.getValue("preferred"+delimiter+i), ""));
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "rateId", CommonUtil.nvl(dsRequest.getValue("rates"+delimiter+i), ""));
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "rowId", CommonUtil.nvl(dsRequest.getValue("rowId"+delimiter+i), "-1"));
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "startTime", CommonUtil.nvl(dsRequest.getValue("startTime"+delimiter+i), ""));
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "timesheetLineId", CommonUtil.nvl(dsRequest.getValue("timesheetLineId"+delimiter+i), "-1"));
				updatedDailyDetail.setValue(updatedDailyDetail.getRowCnt()-1, "workDate", workDate);
			}

			session.setAttribute("timesheetDailyDetail_"+workDate, updatedDailyDetail);

			paramEntity.setSuccess(true);
			paramEntity.setMessage("I801", getMessage("I801"));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}
}