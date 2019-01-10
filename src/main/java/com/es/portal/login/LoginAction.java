/**************************************************************************************************
 * Project
 * Description
 * - Login
 *************************************************************************************************/
package com.es.portal.login;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseAction;
import com.es.portal.conf.resource.ormapper.dto.oracle.HpPersonD;
import com.es.portal.conf.resource.ormapper.dto.oracle.SysUsers;

import zebra.config.MemoryBean;
import zebra.data.DataSet;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class LoginAction extends BaseAction {
	@Autowired
	private LoginBiz biz;

	public String userlogin() throws Exception {
		biz.index(paramEntity);
		return "loginPage";
	}

	public String forgottenUserId() throws Exception {
		biz.index(paramEntity);
		return "forgottenUserId";
	}

	public String forgottenPassword() throws Exception {
		biz.index(paramEntity);
		return "forgottenPassword";
	}

	public String login() throws Exception {
		try {
			biz.exeLogin(paramEntity);

			if (paramEntity.isSuccess()) {
				SysUsers sysUsers = (SysUsers)paramEntity.getObject("sysUsers");
				HpPersonD hpPersonD = (HpPersonD)paramEntity.getObject("hpPersonD");
				DataSet resultDataset = (DataSet)paramEntity.getObject("resultDataset");
				String userPortalType = sysUsers.getPortalSecurityRole();

				session.setAttribute("UserId", CommonUtil.toString(sysUsers.getUserId(), "#"));
				session.setAttribute("LoginId", sysUsers.getUserName()); // LoginId = UserName
				session.setAttribute("PersonId", CommonUtil.toString(sysUsers.getPersonId(), "#"));
				session.setAttribute("UserSurname", hpPersonD.getSurname());
				session.setAttribute("UserFirstName", hpPersonD.getFirstName());
				session.setAttribute("UserFullName", hpPersonD.getFullName());
				session.setAttribute("EmploymentOrgId", CommonUtil.toString(hpPersonD.getEmploymentCompanyOrgId(), "#"));
				session.setAttribute("SecurityRole", sysUsers.getPortalSecurityRole());
				session.setAttribute("StartupUrl", sysUsers.getStartupUrl());
				if (CommonUtil.contains(userPortalType, "CORPORATE")) {
					userPortalType = "Corporate";
				} else if (CommonUtil.contains(userPortalType, "IPRO")) {
					userPortalType = "IPro";
				} else {
					userPortalType = "";
				}
				session.setAttribute("UserPortalType", userPortalType);

				session.setAttribute("themeId", CommonUtil.nvl(CommonUtil.lowerCase(sysUsers.getPortalSkin()), ConfigUtil.getProperty("view.theme.default"))); // ThemeId = PortalSkin
				session.setAttribute("maxRowsPerPage", CommonUtil.split(ConfigUtil.getProperty("view.data.maxRowsPerPage"), ConfigUtil.getProperty("delimiter.data"))[2]);
				session.setAttribute("pageNumsPerPage", CommonUtil.split(ConfigUtil.getProperty("view.data.pageNumsPerPage"), ConfigUtil.getProperty("delimiter.data"))[0]);

				session.setAttribute("SysUsers", sysUsers);
				session.setAttribute("HpPersonD", hpPersonD);

				paramEntity.setAjaxResponseDataSet(resultDataset);
			}
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}
/*
	public String exeSendUserId() throws Exception {
		try {
			biz.exeSendUserId(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}

	public String exeRequestRegister() throws Exception {
		try {
			biz.exeRequestRegister(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}
*/
	public String getUserProfile() throws Exception {
		biz.getUserProfile(paramEntity);
		return "userProfile";
	}

	public String getUpdateUserProfile() throws Exception {
		biz.getUserProfile(paramEntity);
		return "updateUserProfile";
	}

	public String exeUpdateUserProfile() throws Exception {
		try {
			biz.exeUpdateUserProfile(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}

	public String controlAdminTool() throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		String flag = requestDataSet.getValue("flag");

		try {
			session.setAttribute("isVisibleAdminTool", flag);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}

	public String setSessionValuesForAdminTool() throws Exception {
		try {
			biz.setSessionValuesForAdminTool(paramEntity);

			if (paramEntity.isSuccess()) {
				SysUsers sysUsersForAdminTool = (SysUsers)paramEntity.getObject("sysUsersForAdminTool");
				HpPersonD hpPersonDForAdminTool = (HpPersonD)paramEntity.getObject("hpPersonDForAdminTool");
				String userPortalType = sysUsersForAdminTool.getPortalSecurityRole();

				session.setAttribute("UserIdForAdminTool", CommonUtil.toString(sysUsersForAdminTool.getUserId(), "#"));
				session.setAttribute("LoginIdForAdminTool", sysUsersForAdminTool.getUserName()); // LoginId = UserName
				session.setAttribute("PersonIdForAdminTool", CommonUtil.toString(sysUsersForAdminTool.getPersonId(), "#"));
				session.setAttribute("UserFullNameForAdminTool", hpPersonDForAdminTool.getFullName());
				session.setAttribute("EmpOrgIdForAdminTool", CommonUtil.toString(hpPersonDForAdminTool.getEmploymentCompanyOrgId(), "#"));
				session.setAttribute("SecurityRoleForAdminTool", sysUsersForAdminTool.getPortalSecurityRole());
				if (CommonUtil.contains(userPortalType, "CORPORATE")) {
					userPortalType = "Corporate";
				} else if (CommonUtil.contains(userPortalType, "IPRO")) {
					userPortalType = "IPro";
				} else {
					userPortalType = "";
				}
				session.setAttribute("UserPortalTypeForAdminTool", userPortalType);
				session.setAttribute("SysUsersForAdminTool", sysUsersForAdminTool);
				session.setAttribute("HpPersonDForAdminTool", hpPersonDForAdminTool);
			}
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}

	public String removeSessionValuesForAdminTool() throws Exception {
		try {
			session.removeAttribute("UserIdForAdminTool");
			session.removeAttribute("LoginIdForAdminTool");
			session.removeAttribute("PersonIdForAdminTool");
			session.removeAttribute("UserFullNameForAdminTool");
			session.removeAttribute("EmpOrgIdForAdminTool");
			session.removeAttribute("SecurityRoleForAdminTool");
			session.removeAttribute("SysUsersForAdminTool");
			session.removeAttribute("HpPersonDForAdminTool");
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}

	public String logout() throws Exception {
		MemoryBean.remove(session.getId());
		session.invalidate();
		System.gc();
		System.runFinalization();
		return "index";
	}
}