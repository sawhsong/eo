package com.es.portal.common.module.bizservice.userprofile;

import com.es.portal.common.extend.BaseBiz;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.data.QueryAdvisor;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;
import zebra.wssupport.RestServiceSupport;

public class UserProfileBizServiceImpl extends BaseBiz implements UserProfileBizService {
	private String providerUrl = ConfigUtil.getProperty("webService.perci.url");
	private String acceptTypeHeader = "application/json";

	public void getPersonProfileService(ParamEntity paramEntity, String personId) throws Exception {
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		String serviceUrl = "personprofile/"+personId;
		String result = "";

		result = RestServiceSupport.get(providerUrl, serviceUrl, acceptTypeHeader, queryAdvisor);
		paramEntity.setObjectFromJsonString(result);
	}

	public String postUserProfile(String personId, DataSet requestDataSet) throws Exception {
		DataSet post = new DataSet();
		String serviceUrl = "", result = "";
		String header[] = new String[] {"personId", "prefixCode", "firstName", "middleName", "surName", "preferredName", "dateOfBirth",
				"mobile", "landLine", "email", "street", "suburb", "stateCode", "postCode", "country",
				"emergencyContactName", "emergencyContactRelationship", "emergencyContactPhone", "emergencyContactEmail"};

		serviceUrl = "personprofile/";

		post.addName(header);
		post.addRow();
		post.setValue(post.getRowCnt()-1, "personId", personId);
		post.setValue(post.getRowCnt()-1, "prefixCode", requestDataSet.getValue("prefix"));
		post.setValue(post.getRowCnt()-1, "firstName", requestDataSet.getValue("firstName"));
		post.setValue(post.getRowCnt()-1, "middleName", requestDataSet.getValue("middleName"));
		post.setValue(post.getRowCnt()-1, "surName", requestDataSet.getValue("surname"));
		post.setValue(post.getRowCnt()-1, "preferredName", requestDataSet.getValue("preferredName"));
		post.setValue(post.getRowCnt()-1, "dateOfBirth", CommonUtil.replace(requestDataSet.getValue("dateOfBirth"), "-", ""));
		post.setValue(post.getRowCnt()-1, "mobile", requestDataSet.getValue("mobile"));
		post.setValue(post.getRowCnt()-1, "landLine", requestDataSet.getValue("landLine"));
		post.setValue(post.getRowCnt()-1, "email", requestDataSet.getValue("email"));
		post.setValue(post.getRowCnt()-1, "street", requestDataSet.getValue("street"));
		post.setValue(post.getRowCnt()-1, "suburb", requestDataSet.getValue("suburb"));
		post.setValue(post.getRowCnt()-1, "stateCode", requestDataSet.getValue("state"));
		post.setValue(post.getRowCnt()-1, "postCode", requestDataSet.getValue("postCode"));
		post.setValue(post.getRowCnt()-1, "country", requestDataSet.getValue("country"));
		post.setValue(post.getRowCnt()-1, "emergencyContactName", requestDataSet.getValue("emergencyContactName"));
		post.setValue(post.getRowCnt()-1, "emergencyContactRelationship", requestDataSet.getValue("emergencyContactRelationship"));
		post.setValue(post.getRowCnt()-1, "emergencyContactPhone", requestDataSet.getValue("emergencyContactPhone"));
		post.setValue(post.getRowCnt()-1, "emergencyContactEmail", requestDataSet.getValue("emergencyContactEmail"));

		result = RestServiceSupport.post(providerUrl, serviceUrl, acceptTypeHeader, post);
		return CommonUtil.removeString(result, "\"");
	}
}