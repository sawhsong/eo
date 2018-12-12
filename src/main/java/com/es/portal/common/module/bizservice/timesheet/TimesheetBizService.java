package com.es.portal.common.module.bizservice.timesheet;

import zebra.data.DataSet;
import zebra.data.ParamEntity;

public interface TimesheetBizService {
	public DataSet getAssignmentListDataSet(ParamEntity paramEntity, String loginId) throws Exception;
	public DataSet getAssignmentInfoDataSet(DataSet assignmentList, String assignmentId) throws Exception;
	public DataSet getTimesheetPeriodDataSet(DataSet assignmentList, String assignmentId) throws Exception;
	public DataSet getTimesheetDetailListDataSet(ParamEntity paramEntity, String assignmentId, String startDate, String endDate) throws Exception;
}