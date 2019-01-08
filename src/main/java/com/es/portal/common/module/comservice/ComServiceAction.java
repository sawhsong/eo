package com.es.portal.common.module.comservice;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseAction;

public class ComServiceAction extends BaseAction {
	@Autowired
	private ComServiceBiz biz;

	public String getStateCodeFromMeaning() throws Exception {
		try {
			biz.getStateCodeFromMeaning(paramEntity);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}
}