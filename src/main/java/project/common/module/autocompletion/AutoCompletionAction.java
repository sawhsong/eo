package project.common.module.autocompletion;

import org.springframework.beans.factory.annotation.Autowired;

import project.common.extend.BaseAction;

public class AutoCompletionAction extends BaseAction {
	@Autowired
	private AutoCompletionBiz biz;

	public String getCommonCodeType() throws Exception {
		try {
			biz.getCommonCodeType(paramEntity);
		} catch (Exception ex) {
			return "ajaxResponse";
		} finally {
			setRequestAttribute("paramEntity", paramEntity);
		}
		return "ajaxResponse";
	}

	public String getUserId() throws Exception {
		try {
			biz.getUserId(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}

	public String getLoginId() throws Exception {
		try {
			biz.getLoginId(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}

	public String getUserName() throws Exception {
		try {
			biz.getUserName(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}

	public String getCurrencyCode() throws Exception {
		try {
			biz.getCurrencyCode(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}

	public String getCountryName() throws Exception {
		try {
			biz.getCountryName(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}

	public String getOrgName() throws Exception {
		try {
			biz.getOrgName(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}

	public String getAbn() throws Exception {
		try {
			biz.getAbn(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}

	public String getOrgId() throws Exception {
		try {
			biz.getOrgId(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}

	public String getEmployeeSurname() throws Exception {
		try {
			biz.getEmployeeSurname(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}

	public String getEmployeeGivenName() throws Exception {
		try {
			biz.getEmployeeGivenName(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}
}