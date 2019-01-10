package com.es.portal.common.module.bizservice.webserviceclient;

import zebra.data.DataSet;
import zebra.data.ParamEntity;

public interface WebServiceClientBizService {
	/*
	 * Ipro - Timesheet
	 */
	public DataSet getAssignmentListDataSet(ParamEntity paramEntity, String loginId) throws Exception;
	public DataSet getAssignmentInfoDataSet(DataSet assignmentList, String assignmentId) throws Exception;
	public DataSet getPeriodDataSetByAssignmentId(DataSet assignmentList, String assignmentId) throws Exception;
	public ParamEntity getPeriodDetail(ParamEntity paramEntity, String assignmentId, String startDate, String endDate) throws Exception;
	public DataSet getDayListDataSetByPeriod(ParamEntity paramEntity) throws Exception;
	public DataSet getPeriodDetailAsDataSet(ParamEntity paramEntity) throws Exception;
	public DataSet getRateListDataSet(ParamEntity paramEntity) throws Exception;
	public DataSet getDayListDataSetAsCalendar(DataSet timesheetDayList) throws Exception;
	public DataSet getDailyDetailDataSet(DataSet timesheetDayList, String workDate) throws Exception;
	public DataSet updateDailyDetail(DataSet timesheetDayList, DataSet requestDataSet) throws Exception;
	public String postTimesheet(DataSet timesheetPeriodDetail, DataSet timesheetDayList, DataSet requestDataSet) throws Exception;

	/*
	 * Corporate - IPro
	 */
	public DataSet getIproListDataSet(ParamEntity paramEntity, String orgId) throws Exception;

	/*
	 * Login - User Profile
	 */
	public void getPersonProfileService(ParamEntity paramEntity, String personId) throws Exception;
	public String postUserProfile(String personId, DataSet requestDataSet) throws Exception;

	/*
	 * Index - Contact Us
	 */
	public String postContactUs(DataSet postDataSet) throws Exception;
}