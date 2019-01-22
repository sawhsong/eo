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
		return "myLeave";
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

	public String newLeave() throws Exception {
		biz.newLeave(paramEntity);
		return "newLeave";
	}
}