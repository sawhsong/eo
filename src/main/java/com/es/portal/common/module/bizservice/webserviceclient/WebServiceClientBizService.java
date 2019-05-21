package com.es.portal.common.module.bizservice.webserviceclient;

import zebra.data.DataSet;
import zebra.data.ParamEntity;

public interface WebServiceClientBizService {
	/*
	 * Common Services
	 */
	public DataSet getCommonLookupDataSet(String lookupType) throws Exception;
	public DataSet getPersonByName(String loggedInUserEmpOrgId, String fullName) throws Exception;
	public DataSet getPersonByNumber(String loggedInUserEmpOrgId, String personNumber) throws Exception;

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
	 * Employee - Leave
	 */
	public DataSet getLeaveAssignmentListDataSet(ParamEntity paramEntity, String personId) throws Exception;
	public DataSet getLeaveAssignmentListForApproverDataSet(ParamEntity paramEntity, String personId) throws Exception;
	public DataSet getLeaveListDataSet(ParamEntity paramEntity, String personId, String assignmentId) throws Exception;
	public DataSet getAccrualListDataSet(ParamEntity paramEntity, String assignmentId) throws Exception;
	public void getLeaveDetail(ParamEntity paramEntity, String leaveRequestId) throws Exception;
	public String postLeaveRequest(DataSet requestDataSet) throws Exception;
	public DataSet getDateDetail(ParamEntity paramEntity, String leaveRequestId, String assignmentId, String startDate, String endDate) throws Exception;
	public String approveRejectLeaveRequest(DataSet requestDataSet) throws Exception;
	public DataSet getLeaveListAdmDataSet(ParamEntity paramEntity) throws Exception;
	public int doLeaveAdmAction(ParamEntity paramEntity) throws Exception;
	public String getPersonIdFromLeaveRequestId(ParamEntity paramEntity, String leaveRequestId) throws Exception;

	/*
	 * Employee - Expense
	 */
	public DataSet getExpenseClaimListDataSet(ParamEntity paramEntity, String personId) throws Exception;
	public void getExpenseClaimDetailService(ParamEntity paramEntity, String expenseClaimId) throws Exception;
	public String postExpenseClaim(DataSet requestDataSet, DataSet fileDataSet) throws Exception;
	public DataSet getAttachedFile(ParamEntity paramEntity, String expenseClaimId) throws Exception;

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