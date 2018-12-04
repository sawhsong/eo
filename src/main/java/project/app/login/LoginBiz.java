package project.app.login;

import zebra.data.ParamEntity;

public interface LoginBiz {
	public ParamEntity index(ParamEntity paramEntity) throws Exception;
	public ParamEntity exeLogin(ParamEntity paramEntity) throws Exception;
	public ParamEntity exeResetPassword(ParamEntity paramEntity) throws Exception;
	public ParamEntity exeRequestRegister(ParamEntity paramEntity) throws Exception;
	public ParamEntity getUserProfile(ParamEntity paramEntity) throws Exception;
	public ParamEntity exeUpdate(ParamEntity paramEntity) throws Exception;
}