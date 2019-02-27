/**************************************************************************************************
 * Project
 * Description
 *	- 
 *************************************************************************************************/
package com.es.portal.employee.leaveadm;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseAction;

public class LeaveAdmAction extends BaseAction {
	@Autowired
	private LeaveAdmBiz biz;

	public String leavelist() throws Exception {
		biz.getDefault(paramEntity);
		return "leaveList";
	}

	public String getLeaveList() throws Exception {
		try {
			biz.getLeaveList(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}

	public String doAction() throws Exception {
		try {
			biz.doAction(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}

	public String exeExport() throws Exception {
		biz.exeExport(paramEntity);
		setRequestAttribute("paramEntity", paramEntity);
		return "export";
	}
}