package com.es.portal.employee.leaveadm;

import zebra.data.ParamEntity;

public interface LeaveAdmBiz {
	public ParamEntity getDefault(ParamEntity paramEntity) throws Exception;
	public ParamEntity getLeaveList(ParamEntity paramEntity) throws Exception;
	public ParamEntity doAction(ParamEntity paramEntity) throws Exception;
	public ParamEntity exeExport(ParamEntity paramEntity) throws Exception;
}