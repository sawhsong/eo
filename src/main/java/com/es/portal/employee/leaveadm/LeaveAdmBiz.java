package com.es.portal.employee.leaveadm;

import zebra.data.ParamEntity;

public interface LeaveAdmBiz {
	public ParamEntity getDefault(ParamEntity paramEntity) throws Exception;
	public ParamEntity getLeaveList(ParamEntity paramEntity) throws Exception;
	public ParamEntity getLeaveDetail(ParamEntity paramEntity) throws Exception;
	public ParamEntity loadAccrual(ParamEntity paramEntity) throws Exception;
	public ParamEntity saveLeave(ParamEntity paramEntity) throws Exception;
	public ParamEntity getDateDetail(ParamEntity paramEntity) throws Exception;
}