package com.es.portal.serviceresource;

import zebra.data.ParamEntity;

public interface ServiceResourceBiz {
	/*
	 * Main page
	 */
	public ParamEntity doDefault(ParamEntity paramEntity) throws Exception;

	/*
	 * Employee - Leave
	 */
	public ParamEntity approveLeave(ParamEntity paramEntity) throws Exception;
	public ParamEntity approveRejectLeaveRequest(ParamEntity paramEntity) throws Exception;
	public ParamEntity getDateDetail(ParamEntity paramEntity) throws Exception;
}