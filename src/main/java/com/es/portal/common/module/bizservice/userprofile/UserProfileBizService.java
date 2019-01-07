package com.es.portal.common.module.bizservice.userprofile;

import zebra.data.ParamEntity;

public interface UserProfileBizService {
	public void getPersonProfileService(ParamEntity paramEntity, String loginId) throws Exception;
}