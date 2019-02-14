/**************************************************************************************************
 * Project
 * Description
 *	- 
 *************************************************************************************************/
package com.es.portal.employee.leave;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseAction;

public class LeaveAction extends BaseAction {
	@Autowired
	private LeaveBiz biz;

	public String myleaves() throws Exception {
		biz.myLeaves(paramEntity);
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

	public String getLeaveDetail() throws Exception {
		biz.getLeaveDetail(paramEntity);
		return "leaveDetail";
	}

	public String getUpdateLeaveRequest() throws Exception {
		biz.getLeaveDetail(paramEntity);
		return "updateLeave";
	}

	public String loadAccrual() throws Exception {
		try {
			biz.loadAccrual(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}

	public String saveLeave() throws Exception {
		try {
			biz.saveLeave(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}

	public String newLeave() throws Exception {
		biz.getLeaveDetail(paramEntity);
		return "newLeave";
	}

	public String calculateDuration() throws Exception {
		try {
			biz.calculateDuration(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}
}