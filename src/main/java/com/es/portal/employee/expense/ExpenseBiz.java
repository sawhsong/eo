package com.es.portal.employee.expense;

import zebra.data.ParamEntity;

public interface ExpenseBiz {
	public ParamEntity getDefault(ParamEntity paramEntity) throws Exception;
	public ParamEntity getExpenseClaimList(ParamEntity paramEntity) throws Exception;
	public ParamEntity getDetail(ParamEntity paramEntity) throws Exception;
	public ParamEntity loadAccrual(ParamEntity paramEntity) throws Exception;
	public ParamEntity saveLeave(ParamEntity paramEntity) throws Exception;
}