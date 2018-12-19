package com.es.portal.ipro.timesheet;

import zebra.data.ParamEntity;

public interface TimesheetBiz {
	public ParamEntity mytimesheets(ParamEntity paramEntity) throws Exception;
	public ParamEntity getAssignmentInfo(ParamEntity paramEntity) throws Exception;
	public ParamEntity getTimesheetPeriod(ParamEntity paramEntity) throws Exception;
	public ParamEntity getTimesheetDayList(ParamEntity paramEntity) throws Exception;
	public ParamEntity getTimesheetDailyDetail(ParamEntity paramEntity) throws Exception;
	public ParamEntity getTimesheetDailyDetailData(ParamEntity paramEntity) throws Exception;
	public ParamEntity doUpdateTimesheetDailyDetail(ParamEntity paramEntity) throws Exception;
}