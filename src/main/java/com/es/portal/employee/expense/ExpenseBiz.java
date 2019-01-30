package com.es.portal.employee.expense;

import zebra.data.ParamEntity;

public interface ExpenseBiz {
	public ParamEntity getDefault(ParamEntity paramEntity) throws Exception;
	public ParamEntity getLeaveList(ParamEntity paramEntity) throws Exception;
	public ParamEntity getLeaveDetail(ParamEntity paramEntity) throws Exception;
	public ParamEntity loadAccrual(ParamEntity paramEntity) throws Exception;
	public ParamEntity saveLeave(ParamEntity paramEntity) throws Exception;
}