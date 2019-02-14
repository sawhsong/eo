package com.es.portal.employee.leave;

import zebra.data.ParamEntity;

public interface LeaveBiz {
	public ParamEntity myLeaves(ParamEntity paramEntity) throws Exception;
	public ParamEntity getLeaveList(ParamEntity paramEntity) throws Exception;
	public ParamEntity getLeaveDetail(ParamEntity paramEntity) throws Exception;
	public ParamEntity loadAccrual(ParamEntity paramEntity) throws Exception;
	public ParamEntity saveLeave(ParamEntity paramEntity) throws Exception;
	public ParamEntity calculateDuration(ParamEntity paramEntity) throws Exception;
}