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

	public String disclaimer() throws Exception {
		biz.doDefault(paramEntity);
		return "disclaimer";
	}

	public String privacy() throws Exception {
		biz.doDefault(paramEntity);
		return "privacy";
	}
}