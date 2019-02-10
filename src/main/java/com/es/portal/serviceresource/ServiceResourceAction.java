/**************************************************************************************************
 * Project
 * Description
 * - Project Main Index
 *************************************************************************************************/
package com.es.portal.serviceresource;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseAction;

import zebra.data.DataSet;
import zebra.util.CommonUtil;

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
		DataSet leaveDetail = (DataSet)paramEntity.getObject("leaveDetail");
		if (CommonUtil.equalsIgnoreCase(leaveDetail.getValue("status"), "SU")) {
			return "approveLeave";
		} else {
			return "alreadyProcessed";
		}
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