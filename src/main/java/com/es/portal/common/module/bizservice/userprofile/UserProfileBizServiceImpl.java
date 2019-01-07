package com.es.portal.common.module.bizservice.userprofile;

import com.es.portal.common.extend.BaseBiz;

import zebra.data.ParamEntity;
import zebra.data.QueryAdvisor;
import zebra.util.ConfigUtil;
import zebra.wssupport.RestServiceSupport;

public class UserProfileBizServiceImpl extends BaseBiz implements UserProfileBizService {
	private String providerUrl = ConfigUtil.getProperty("webService.perci.url");
	private String acceptTypeHeader = "application/json";

	public void getPersonProfileService(ParamEntity paramEntity, String loginId) throws Exception {
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		String serviceUrl = "personprofile/"+loginId;
		String result = "";

		result = RestServiceSupport.get(providerUrl, serviceUrl, acceptTypeHeader, queryAdvisor);
		paramEntity.setObjectFromJsonString(result);
	}
}