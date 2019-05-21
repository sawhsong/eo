/**************************************************************************************************
 * Project
 * Description
 * - Project Main Index
 *************************************************************************************************/
package com.es.portal.app.index;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseAction;

public class IndexAction extends BaseAction {
	@Autowired
	private IndexBiz biz;

	public String index() throws Exception {
		biz.index(paramEntity);
		return SUCCESS;
	}

	public String dashboard() throws Exception {
		biz.dashboard(paramEntity);
		return "dashboard";
	}

	public String contactus() throws Exception {
		biz.contactus(paramEntity);
		return "contactus";
	}

	public String contactuspop() throws Exception {
		biz.contactuspop(paramEntity);
		return "contactuspop";
	}

	public String postContactUs() throws Exception {
		try {
			biz.postContactUs(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}
}