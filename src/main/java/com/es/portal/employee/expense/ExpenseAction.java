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

	public String getExpenseClaimList() throws Exception {
		try {
			biz.getExpenseClaimList(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}

	public String getDetail() throws Exception {
		biz.getDetail(paramEntity);
		return "leaveDetail";
	}

	public String newExpenseClaim() throws Exception {
		biz.getDetail(paramEntity);
		return "newLeave";
	}

	public String getUpdateLeaveRequest() throws Exception {
		biz.getDetail(paramEntity);
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
}