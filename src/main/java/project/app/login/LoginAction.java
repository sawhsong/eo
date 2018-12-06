/**************************************************************************************************
 * Project
 * Description
 * - Login
 *************************************************************************************************/
package project.app.login;

import java.security.Principal;
import java.util.Collection;
import java.util.Iterator;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import project.common.extend.BaseAction;
import project.conf.resource.ormapper.dto.oracle.HpPersonD;
import project.conf.resource.ormapper.dto.oracle.SysUsers;
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
/*
	public String resetPassword() throws Exception {
		biz.index(paramEntity);
		return "resetPassword";
	}

	public String requestRegister() throws Exception {
		biz.index(paramEntity);
		return "requestRegister";
	}
*/
	public String login() throws Exception {
		try {
			biz.exeLogin(paramEntity);

			if (paramEntity.isSuccess()) {
				SysUsers sysUsers = (SysUsers)paramEntity.getObject("sysUsers");
				HpPersonD hpPersonD = (HpPersonD)paramEntity.getObject("hpPersonD");
				DataSet resultDataset = (DataSet)paramEntity.getObject("resultDataset");

				session.setAttribute("UserId", CommonUtil.toString(sysUsers.getUserId()));
				session.setAttribute("LoginId", sysUsers.getUserName()); // LoginId = UserName
				session.setAttribute("UserSurname", hpPersonD.getSurname());
				session.setAttribute("UserFirstName", hpPersonD.getFirstName());
				session.setAttribute("UserFullName", hpPersonD.getFullName());
				session.setAttribute("SecurityRole", sysUsers.getPortalSecurityRole());
				session.setAttribute("StartupUrl", sysUsers.getStartupUrl());
				session.setAttribute("themeId", CommonUtil.lowerCase(sysUsers.getPortalSkin())); // ThemeId = PortalSkin
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
	public String exeResetPassword() throws Exception {
		try {
			biz.exeResetPassword(paramEntity);
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

	public String getUserProfile() throws Exception {
		biz.getUserProfile(paramEntity);
		return "userProfile";
	}

	public String getUpdateUserProfile() throws Exception {
		biz.getUserProfile(paramEntity);
		return "updateUserProfile";
	}

	public String exeUpdate() throws Exception {
//		try {
//			biz.exeUpdate(paramEntity);
//		} catch (Exception ex) {
//			return "ajaxResponse";
//		} finally {
//			setRequestAttribute("paramEntity", paramEntity);
//		}
//		return "ajaxResponse";

		try {
			biz.exeUpdate(paramEntity);

			paramEntity.setObject("messageCode", paramEntity.getMessageCode());
			paramEntity.setObject("message", paramEntity.getMessage());
			if (paramEntity.isSuccess()) {
				paramEntity.setObject("action", "/login/logout.do");
				paramEntity.setObject("message", paramEntity.getMessage()+"<br/>"+getMessage("login.message.restart", paramEntity));
			} else {
				paramEntity.setObject("script", "history.go(-1);");
			}
		} catch (Exception ex) {
			paramEntity.setObject("script", "history.go(-1);");
		}

		return "pageHandler";
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

	public String logout() throws Exception {
		MemoryBean.remove(session.getId());
		session.invalidate();
		System.gc();
		System.runFinalization();
		return "index";
	}
*/
}