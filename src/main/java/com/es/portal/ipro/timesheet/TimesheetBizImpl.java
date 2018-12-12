package com.es.portal.ipro.timesheet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseBiz;
import com.es.portal.common.module.bizservice.timesheet.TimesheetBizService;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.exception.FrameworkException;

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

	public ParamEntity getTimesheetDetail(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		HttpSession session = paramEntity.getSession();
		DataSet timesheetDetailList = new DataSet();
		String assignmentId = dsRequest.getValue("assignmentId");
		String startDate = dsRequest.getValue("startDate");
		String endDate = dsRequest.getValue("endDate");

		try {
			timesheetDetailList = timesheetBizService.getTimesheetDetailListDataSet(paramEntity, assignmentId, startDate, endDate);
			paramEntity.setObject("timesheetDetailList", timesheetDetailList);
			session.setAttribute("timesheetDetailListDataSet", timesheetDetailList);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}
}