package com.es.portal.ipro.timesheet;

import zebra.data.ParamEntity;

public interface TimesheetBiz {
	public ParamEntity myTimesheets(ParamEntity paramEntity) throws Exception;
	public ParamEntity getAssignmentInfo(ParamEntity paramEntity) throws Exception;
	public ParamEntity getPeriodByAssignmentId(ParamEntity paramEntity) throws Exception;
	public ParamEntity getDayListByPeriod(ParamEntity paramEntity) throws Exception;
	public ParamEntity refreshDayListByPeriod(ParamEntity paramEntity) throws Exception;
	public ParamEntity getDailyDetailScreen(ParamEntity paramEntity) throws Exception;
	public ParamEntity getDailyDetailData(ParamEntity paramEntity) throws Exception;
	public ParamEntity calculateTimeWorked(ParamEntity paramEntity) throws Exception;
	public ParamEntity updateDailyDetail(ParamEntity paramEntity) throws Exception;
	public ParamEntity postTimesheet(ParamEntity paramEntity) throws Exception;
}