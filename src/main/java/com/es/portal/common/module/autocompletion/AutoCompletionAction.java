package com.es.portal.common.module.autocompletion;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseAction;

public class AutoCompletionAction extends BaseAction {
	@Autowired
	private AutoCompletionBiz biz;

	public String getSysUsersByLoginId() throws Exception {
		try {
			biz.getSysUsersByLoginId(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}

	public String getSysUsersByPersonId() throws Exception {
		try {
			biz.getSysUsersByPersonId(paramEntity);
		} catch (Exception ex) {
		}
		setRequestAttribute("paramEntity", paramEntity);
		return "ajaxResponse";
	}
}