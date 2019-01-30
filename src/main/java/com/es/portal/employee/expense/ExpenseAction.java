/**************************************************************************************************
 * Project
 * Description
 *	- 
 *************************************************************************************************/
package com.es.portal.employee.expense;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseAction;

public class ExpenseAction extends BaseAction {
	@Autowired
	private ExpenseBiz biz;

	public String submitexpenses() throws Exception {
		biz.getDefault(paramEntity);
		return "expenseList";
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
}