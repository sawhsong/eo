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
		return "expenseDetail";
	}

	public String newExpenseClaim() throws Exception {
		biz.getDetail(paramEntity);
		return "newExpense";
	}

	public String saveExpenseClaim() throws Exception {
		try {
			biz.saveExpenseClaim(paramEntity);

			if (paramEntity.isSuccess()) {
				paramEntity.setObject("script", "parent.popup.close(); parent.doSearch();");
			} else {
				paramEntity.setObject("script", "history.go(-1);");
			}
		} catch (Exception ex) {
			paramEntity.setObject("script", "history.go(-1);");
		} finally {
			paramEntity.setObject("messageCode", paramEntity.getMessageCode());
			paramEntity.setObject("message", paramEntity.getMessage());
		}
		return "pageHandler";
	}

	public String getAttachedFile() throws Exception {
		try {
			biz.getAttachedFile(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}





	public String getUpdateLeaveRequest() throws Exception {
		biz.getDetail(paramEntity);
		return "updateLeave";
	}
}