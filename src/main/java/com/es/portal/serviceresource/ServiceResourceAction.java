/**************************************************************************************************
 * Project
 * Description
 * - Project Main Index
 *************************************************************************************************/
package com.es.portal.serviceresource;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseAction;

public class ServiceResourceAction extends BaseAction {
	@Autowired
	private ServiceResourceBiz biz;

	/*
	 * Main page
	 */
	public String disclaimer() throws Exception {
		biz.doDefault(paramEntity);
		return "disclaimer";
	}

	public String privacy() throws Exception {
		biz.doDefault(paramEntity);
		return "privacy";
	}

	/*
	 * Employee - Leave
	 */
	public String approveLeave() throws Exception {
		biz.approveLeave(paramEntity);
		return "approveLeave";
	}

	public String approveRejectLeaveRequest() throws Exception {
		try {
			biz.approveRejectLeaveRequest(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}
}