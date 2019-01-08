package com.es.portal.common.module.bizservice.userprofile;

import zebra.data.DataSet;
import zebra.data.ParamEntity;

public interface UserProfileBizService {
	public void getPersonProfileService(ParamEntity paramEntity, String personId) throws Exception;
	public String postUserProfile(String personId, DataSet requestDataSet) throws Exception;
}