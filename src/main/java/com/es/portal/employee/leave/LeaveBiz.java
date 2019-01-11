package com.es.portal.employee.leave;

import zebra.data.ParamEntity;

public interface LeaveBiz {
	public ParamEntity myLeaves(ParamEntity paramEntity) throws Exception;
	public ParamEntity getLeaveList(ParamEntity paramEntity) throws Exception;
	public ParamEntity newLeave(ParamEntity paramEntity) throws Exception;
}