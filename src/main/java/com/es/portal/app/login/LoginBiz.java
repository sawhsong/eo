package com.es.portal.app.login;

import zebra.data.ParamEntity;

public interface LoginBiz {
	public ParamEntity index(ParamEntity paramEntity) throws Exception;
	public ParamEntity exeLogin(ParamEntity paramEntity) throws Exception;
	public ParamEntity getUserProfile(ParamEntity paramEntity) throws Exception;
	public ParamEntity exeUpdateUserProfile(ParamEntity paramEntity) throws Exception;
/*
	public ParamEntity exeSendUserId(ParamEntity paramEntity) throws Exception;
	public ParamEntity exeRequestRegister(ParamEntity paramEntity) throws Exception;
*/
	public ParamEntity setSessionValuesForAdminTool(ParamEntity paramEntity) throws Exception;
}